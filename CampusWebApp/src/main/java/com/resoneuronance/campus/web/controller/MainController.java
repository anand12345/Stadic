package com.resoneuronance.campus.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.bo.StudentBO;
import com.resoneuronance.campus.web.bo.TeacherBO;
import com.resoneuronance.campus.web.bo.domain.StudentTeacher;
import com.resoneuronance.campus.web.domain.AutocompleteTag;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.domain.UserSession;
import com.resoneuronance.campus.web.domain.UserType;
import com.resoneuronance.campus.web.util.Constants;
import com.resoneuronance.campus.web.util.FileUtils;

@SessionAttributes("session")
@Controller
public class MainController implements Constants {

	private CollegeBO collegeBo;
	private String resultMsg;
	private TeacherBO teacherBo;

	public TeacherBO getTeacherBo() {
		return teacherBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "teacherBO")
	public void setTeacherBo(TeacherBO teacherBO) {
		this.teacherBo = teacherBO;
	}
	

	public CollegeBO getCollegeBo() {
		return collegeBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "collegeBO")
	public void setCollegeBo(CollegeBO collegeBo) {
		this.collegeBo = collegeBo;
	}

	List<AutocompleteTag> data = new ArrayList<AutocompleteTag>();
	
	private StudentBO studentBo;
	 
	public StudentBO getStudentBo() {
		return studentBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "studentBO")
	public void setStudentBo(StudentBO studentBo) {
		this.studentBo = studentBo;
	}

	MainController() {
		// init data for testing
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RedirectView welcome(@ModelAttribute("college") College college, ModelMap model) {

		if (collegeBo.login(college)) {
			System.out.println("Id after:" +college.getId());
			initAdminDashboard(model);
			//System.out.println(collegeBo.getCurrentDepartments().get(0).getName());
			return new RedirectView(ADMIN_DASHBOARD_URL);
		}
		model.addAttribute("loginError", "Invalid username or password!");
		return new RedirectView(REDIRECT_URL);

	}

	private void initAdminDashboard(ModelMap model) {
		College currentCollege = collegeBo.getCurrentCollege();
		model.addAttribute("college", currentCollege);
		UserSession session = new UserSession();
		session.setSessionObject(currentCollege);
		session.setType(UserType.ADMIN);
		model.addAttribute("session", session);
		model.addAttribute("studentsCount", collegeBo.getCurrentStudents().size() + " students");
		model.addAttribute("teachersCount", collegeBo.getCurrentTeachers().size() + " teachers");
		model.addAttribute("departmentsCount", collegeBo.getCurrentDepartments().size() + " departments");
	}

	@RequestMapping(value = "/admin.htm", method = RequestMethod.GET)
	public String initAdmin(ModelMap model) {
		initAdminDashboard(model);
		return ADMIN_PROFILE_PAGE;
	}

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init(ModelMap model) {

		System.out.println("Here in Init!");
		String screen = prepareLoginPage(model);
		return screen;

	}

	private String prepareLoginPage(ModelMap model) {
		String screen = checkSessionAvailability(model);
		if(StringUtils.isNotEmpty(screen)) {
			return screen;
		}
		model.addAttribute("college", new College());
		model.addAttribute("colleges", collegeBo.getCollegeNames());
		model.addAttribute(STUDENT_OBEJECT,new Student());
		model.addAttribute(TEACHER_OBEJECT,new Teacher());
		prepareCollegeNameTags(collegeBo.getColleges());
		return LOGIN_PAGE;
	}
	
	private void prepareCollegeNameTags(List<College> colleges) {
		if(CollectionUtils.isEmpty(colleges)) {
			return;
		}
		data = new ArrayList<AutocompleteTag>();
		for(College college:colleges) {
			data.add(new AutocompleteTag(college.getId(), college.getName()));
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model,SessionStatus status) {
		model.remove("session");
		status.setComplete();
		model.addAttribute("college", new College());
		model.addAttribute("colleges", collegeBo.getCollegeNames());
		return LOGIN_PAGE;
	}

	private String checkSessionAvailability(ModelMap model) {
		if(model.get("session")==null) {
			return null;
		}
		UserSession session = (UserSession) model.get("session");
		if(UserType.ADMIN.equals(session.getType())) {
			College college = (College) session.getSessionObject();
			collegeBo.login(college);
			initAdminDashboard(model);
			return ADMIN_PROFILE_PAGE;
		}
		return ADMIN_PROFILE_PAGE;
	}

	@RequestMapping(value = "/uploadDepartments", method = RequestMethod.POST)
	public RedirectView uploadDepartments(@RequestParam CommonsMultipartFile[] departmentsFile,ModelMap model) throws Exception {
		
		RedirectView view = new RedirectView(ADD_DEPARTMENT_URL);
		if (departmentsFile != null && departmentsFile.length > 0) {
			for (CommonsMultipartFile aFile : departmentsFile) {
				System.out.println("Uploading file: " + aFile.getOriginalFilename());
				resultMsg= FileUtils.uploadDepartmentsExcel(aFile.getInputStream(),collegeBo);
				break;
			}
		}
		return view;
	}
	
	@RequestMapping(value = "/departmentadd", method = RequestMethod.GET)
	public String initDepartment(ModelMap model) throws Exception {
		model.addAttribute("department", new Department());
		return DEPARTMENT_ADD_PAGE;
	}
	
	
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	public RedirectView addDepartment(@ModelAttribute("department") Department dept,ModelMap model) throws Exception {
		collegeBo.addDepartment(dept);
		resultMsg = "Added department " + dept.getName();
		return new RedirectView(ADD_DEPARTMENT_URL);
	}
	
	@RequestMapping(value = "/addDepartment.htm", method= RequestMethod.GET)
	public String redirectDepartment(ModelMap model) {
		
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		model.addAttribute("departments", collegeBo.getCurrentDepartments());
		model.addAttribute("department", new Department());
		return ADD_DEPARTMENT_PAGE;
	}
	
	@RequestMapping(value="/initDepartmentEdit",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody String initEditDepartment(int id){
		System.out.println("id =>" + id);
		Department department = collegeBo.getDepartment(id);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(department);
	}
	
	@RequestMapping(value = "/{id}.department", method= RequestMethod.POST)
	public RedirectView deleteDepartment(@PathVariable String id, ModelMap model) {
		
		System.out.println("ID:" + id);
		collegeBo.deleteDepartment(id);
		resultMsg = "Deleted the department successfully!";
		return new RedirectView(ADD_DEPARTMENT_URL);
	}
	
	@RequestMapping(value="/editDepartment",method=RequestMethod.POST)
	public RedirectView editDepartment(@ModelAttribute("department") Department department,ModelMap model){
		collegeBo.editDepartment(department);
		resultMsg = "Updated department successfully!";
		return new RedirectView(ADD_DEPARTMENT_URL);
	}
	
	@RequestMapping(value = "/uploadTeachers", method = RequestMethod.POST)
	public RedirectView uploadTeachers(@RequestParam CommonsMultipartFile[] teachersFile,ModelMap model) throws Exception {
		
		RedirectView view = new RedirectView(ADD_TEACHER_URL);
		if (teachersFile != null && teachersFile.length > 0) {
			for (CommonsMultipartFile aFile : teachersFile) {
				System.out.println("Uploading file: " + aFile.getOriginalFilename());
				resultMsg= FileUtils.uploadTeachersExcel(aFile.getInputStream(),collegeBo);
				break;
			}
		}
		return view;
	}
	
	@RequestMapping(value = "/addTeacher.htm", method= RequestMethod.GET)
	public String redirectTeacher(ModelMap model) {
		
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		model.addAttribute("teachers", collegeBo.getCurrentTeachers());
		return "addTeacher";
	}
	
	@RequestMapping(value="/initTeacherEdit",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody String initEditTeacher(int id){
		System.out.println("id =>" + id);
		Teacher teacher = collegeBo.getTeacher(id);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(teacher);
	}
	
	@RequestMapping(value="/editTeacher",method=RequestMethod.POST)
	public RedirectView editTeacher(@ModelAttribute(TEACHER_OBEJECT) Teacher teacher,ModelMap model){
		collegeBo.editTeacher(teacher);
		resultMsg = "Updated teacher successfully!";
		return new RedirectView(ADD_TEACHER_URL);
	}
	
	@RequestMapping(value = "/{id}.teacher", method= RequestMethod.POST)
	public RedirectView deleteTeacher(@PathVariable String id, ModelMap model) {
		
		System.out.println("ID:" + id);
		collegeBo.deleteTeacher(id);
		resultMsg = "Deleted the teacher successfully!";
		return new RedirectView(ADD_TEACHER_URL);
	}
	
	
	@RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
	public RedirectView addTeacher(@ModelAttribute(TEACHER_OBEJECT) Teacher teacher,ModelMap model) throws Exception {
		collegeBo.addTeacher(teacher);
		resultMsg = "Added teacher " + teacher.getName();
		return new RedirectView(ADD_TEACHER_URL);
	}
	
	@RequestMapping(value = "/uploadStudents", method = RequestMethod.POST)
	public RedirectView uploadStudents(@RequestParam CommonsMultipartFile[] studentsFile,ModelMap model) throws Exception {
		
		RedirectView view = new RedirectView(ADD_STUDENT_URL);
		if (studentsFile != null && studentsFile.length > 0) {
			for (CommonsMultipartFile aFile : studentsFile) {
				System.out.println("Uploading file: " + aFile.getOriginalFilename());
				resultMsg= FileUtils.uploadStudentsExcel(aFile.getInputStream(),collegeBo);
				break;
			}
		}
		return view;
	}
	
	@RequestMapping(value = "/addStudent.htm", method= RequestMethod.GET)
	public String redirectStudent(ModelMap model) {
		
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		model.addAttribute("students", collegeBo.getCurrentStudents());
		model.addAttribute("student", new Student());
		return ADD_STUDENT_PAGE;
	}
	
	@RequestMapping(value = "/studentadd", method = RequestMethod.GET)
	public String initStudent(ModelMap model) throws Exception {
		model.addAttribute("student", new Student());
		return "studentadd";
	}
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public RedirectView addStudent(@ModelAttribute("student") Student student,ModelMap model) throws Exception {
		collegeBo.addStudent(student);
		resultMsg = "Added student " + student.getName();
		return new RedirectView(ADD_STUDENT_URL);
	}
	
	@RequestMapping(value = "/{id}.student", method= RequestMethod.POST)
	public RedirectView deleteStudent(@PathVariable String id, ModelMap model) {
		
		System.out.println("ID:" + id);
		collegeBo.deleteStudent(id);
		resultMsg = "Deleted the student successfully!";
		return new RedirectView(ADD_STUDENT_URL);
	}
	
	@RequestMapping(value="/initStudentEdit",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody String initEdituser(int id){
		System.out.println("id =>" + id);
		Student student = collegeBo.getStudent(id);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(student);
	}
	
	@RequestMapping(value="/editStudent",method=RequestMethod.POST)
	public RedirectView initEdituser(@ModelAttribute("student") Student student,ModelMap model){
		collegeBo.editStudent(student);
		resultMsg = "Updated student successfully!";
		return new RedirectView(ADD_STUDENT_URL);
	}
	
	@RequestMapping(value = "/getColleges", method = RequestMethod.GET)
	public @ResponseBody
	List<AutocompleteTag> getTags(@RequestParam String tagName) {
 
		System.out.println("Getting the colleges for.." + tagName);
		List<AutocompleteTag> simulateSearchResult = simulateSearchResult(tagName);
		System.out.println("Results =>" + simulateSearchResult.size());
		return simulateSearchResult;
 
	}
	
	@RequestMapping(value = "/registration.htm", method = RequestMethod.GET)
	public String initResgiter(@RequestParam("type") String type,ModelMap model) {
		model.addAttribute(TEACHER_OBEJECT, new Teacher());
		model.addAttribute(STUDENT_OBEJECT, new Student());
		if(TEACHER_OBEJECT.equals(type)) {
			model.addAttribute("register", "registerTeacher");
			model.addAttribute("action", "teacherRegistration");
		}
		else if(STUDENT_OBEJECT.equals(type)) {
			model.addAttribute("register", "registerStudent");
			model.addAttribute("action", "studentRegistration");
		}
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		resultMsg = null;
		return REGISTRATION_PAGE;
	}
	
 
	private List<AutocompleteTag> simulateSearchResult(String tagName) {
 
		List<AutocompleteTag> result = new ArrayList<AutocompleteTag>();
 
		// iterate a list and filter by tagName
		for (AutocompleteTag tag : data) {
			if (StringUtils.containsIgnoreCase(tag.getTagName(), tagName)) {
				result.add(tag);
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/teacherRegistration", method = RequestMethod.POST)
	public RedirectView registerTeacher(@ModelAttribute(TEACHER_OBEJECT) Teacher teacher,@RequestParam("collegeName") String college,ModelMap model) {
		
		RedirectView view = new RedirectView();
		if(!teacherBo.registerTeacher(teacher, college)) {
			view.setUrl(REGISTRATION_URL + "?type=teacher");
			resultMsg = "Your email is not authorized! Please contact your college admin.";
		}
		else {
			view.setUrl(REDIRECT_URL);
			resultMsg = "Registered successfully!";
		}
		return view;
	}
	
	@RequestMapping(value = "/teacherLogin", method = RequestMethod.POST)
	public RedirectView teacherLogin(@ModelAttribute(TEACHER_OBEJECT) Teacher teacher,@RequestParam("collegeName") String college,ModelMap model) {
		RedirectView view = new RedirectView();
		if(!teacherBo.login(teacher, college)) {
			view.setUrl(REDIRECT_URL);
			resultMsg = "Incorrect credentials!";
		}
		else {
			view.setUrl(TEACHER_PROFILE_URL);
		}
		return view;
	}
	
	@RequestMapping(value = "/tagTeacherToDepartment", method = RequestMethod.POST)
	public RedirectView tagTeacherToDepartment(@ModelAttribute(TEACHER_OBEJECT) com.resoneuronance.campus.web.bo.domain.Teacher teacher,ModelMap model) {
		RedirectView view = new RedirectView(TEACHER_PROFILE_URL);
		teacherBo.tagDepartmentToTeacher(teacher);
		return view;
	}
	
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public RedirectView notify(Notification notification,ModelMap model,@RequestParam("file") MultipartFile file ) {
		RedirectView view = new RedirectView();
		try {
			teacherBo.notify(notification,file);
			if("Teacher".equals(notification.getType())) {
				view.setUrl(TEACHER_PROFILE_URL);
			}
			else {
				view.setUrl(TEACHER_DEPARTMENT_URL);
			}
		} catch (IOException e) {
			resultMsg = "Error in uploading the document!";
		}
		return view;
	}
	
	@RequestMapping(value = "/curriculars.htm", method= RequestMethod.GET)
	public String showCurricularNotifications(ModelMap model) {
		
		model.addAttribute(TEACHER_OBEJECT, teacherBo.getCurrentTeacher());
		return CURRICULAR_NOTIFICATIONS_PAGE;
	}
	
	@RequestMapping(value = "/coCurriculars.htm", method= RequestMethod.GET)
	public String showCoCurricularNotifications(ModelMap model) {
		
		model.addAttribute(TEACHER_OBEJECT, teacherBo.getCurrentTeacher());
		return CO_CURRICULAR_NOTIFICATIONS_PAGE;
	}
	
	@RequestMapping(value = "/{id}.delete", method= RequestMethod.POST)
	public RedirectView deleteNorification(@PathVariable("id") int id,ModelMap model) {
		
		teacherBo.deleteNotification(id);
		resultMsg = "Deleted notification successfully!";
		RedirectView view = new RedirectView(CURRICULAR_NOTIFICATIONS_URL);
		return view ;
	}
	
	@RequestMapping(value = "/downloadDocument/{id}", method= RequestMethod.POST)
	public void  downloadDocument(@PathVariable("id") int id,HttpServletResponse response, ModelMap model) {
		InputStream is =  null;
		try {
			is = teacherBo.downloadDocument(id);
			if(is!=null) {
		    	  IOUtils.copy(is, response.getOutputStream());
		    	  //response.setContentType("APPLICATION/OCTET-STREAM");   
		    	  response.setHeader("Content-Disposition","attachment; filename=\"notification\"");   
		      	  response.flushBuffer();
		      }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/department.{id}", method= RequestMethod.GET)
	public RedirectView loadDepartment(@PathVariable("id") int id,ModelMap model) {
		
		teacherBo.loadDepartment(id);
		RedirectView view = new RedirectView(TEACHER_DEPARTMENT_URL);
		return view ;
	}
	
	@RequestMapping(value = "/teacherDepartmentPage.htm", method= RequestMethod.GET)
	public String departmentNotifications(ModelMap model) {
		
		if(teacherBo.getCurrentTeacher().getCurrentDepartment() == null) {
			return TEACHER_PROFILE_PAGE;
		}
		model.addAttribute(TEACHER_OBEJECT,teacherBo.getCurrentTeacher());
		return TEACHER_DEPARTMENT_PAGE ;
	}
	
	@RequestMapping(value = "/teacherProfile.htm", method= RequestMethod.GET)
	public String teacherProfile(ModelMap model) {
		
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		model.addAttribute(TEACHER_OBEJECT, teacherBo.getCurrentTeacher());
		return TEACHER_PROFILE_PAGE;
	}
	
	
	@RequestMapping(value = "/studentRegistration", method = RequestMethod.POST)
	public RedirectView registerStudent(@ModelAttribute(STUDENT_OBEJECT) Student student,@RequestParam("collegeName") String college,
			@RequestParam("departmentName") String departmentName,ModelMap model) {
		
		RedirectView view = new RedirectView();
		if(!studentBo.registerStudent(student, college, departmentName)) {
			view.setUrl(REGISTRATION_URL + "?type=student");
			resultMsg = "Your email is not authorized! Please contact your college admin.";
		}
		else {
			view.setUrl(REDIRECT_URL);
			resultMsg = "Registered successfully!";
		}
		return view;
	}
	
	@RequestMapping(value = "/studentLogin", method = RequestMethod.POST)
	public RedirectView teacherLogin(@ModelAttribute(STUDENT_OBEJECT) Student student,@RequestParam("collegeName") String college,ModelMap model) {
		RedirectView view = new RedirectView();
		if(!studentBo.login(student, college)) {
			view.setUrl(REDIRECT_URL);
			resultMsg = "Incorrect credentials!";
		}
		else {
			view.setUrl(STUDENT_PROFILE_URL);
		}
		return view;
	}
	
	@RequestMapping(value = "/selectTeachers.htm", method= RequestMethod.GET)
	public String selectTeachers(ModelMap model) {
		
		model.addAttribute("teachers", studentBo.getCurrentTeachers());
		return TEACHER_SELECT_PAGE;
	}
	
	@RequestMapping(value = "/selectTeachers", method= RequestMethod.POST)
	public String tagTeachers(int [] teacherIds,ModelMap model) {
		
		studentBo.tagTeachers(teacherIds);
		return STUDENT_PROFILE_PAGE;
	}
	
	@RequestMapping(value = "/showNotifications", method= RequestMethod.POST)
	public RedirectView showNotifications(StudentTeacher teacher,ModelMap model) {
		
		studentBo.getCurrentStudent().setCurrentTeacher(teacher);
		return new RedirectView(STUDENT_TEACHER_NOTIFICATIONS_URL);
	}
	
	@RequestMapping(value = "/showNotifications.htm", method= RequestMethod.GET)
	public String refreshNotifications(ModelMap model) {
		studentBo.updateTeacherNotification(studentBo.getCurrentStudent().getCurrentTeacher());
		studentBo.updateStudentToTeacherMapping(studentBo.getCurrentStudent().getCurrentTeacher());
		model.addAttribute(STUDENT_TEACHER_OBEJECT,studentBo.getCurrentStudent().getCurrentTeacher());
		model.addAttribute(STUDENT_OBEJECT, studentBo.getCurrentStudent());
		System.out.println("Teacher notifications refreshed..");
		return STUDENT_TEACHER_NOTIFICATIONS_PAGE;
	}
	
	@RequestMapping(value = "/teachersList.htm", method= RequestMethod.GET)
	public String teachersList(int [] teacherIds,ModelMap model) {
		
		studentBo.prepareStudentTeachers();
		model.addAttribute(STUDENT_OBEJECT, studentBo.getCurrentStudent());
		return TEACHERS_LIST_PAGE;
	}
	
	@RequestMapping(value = "/studentProfile.htm", method= RequestMethod.GET)
	public String studentProfile(ModelMap model) {
		
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		model.addAttribute(STUDENT_OBEJECT, studentBo.getCurrentStudent());
		return STUDENT_PROFILE_PAGE;
	}
	
	//TODO : To be removed later
	
	/*@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String temp(@PathVariable String name, ModelMap model) {

		System.out.println("Showing " + name);
		return name;

	}*/
	
}
