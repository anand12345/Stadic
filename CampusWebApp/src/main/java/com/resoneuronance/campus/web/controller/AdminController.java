package com.resoneuronance.campus.web.controller;

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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.domain.UserSession;
import com.resoneuronance.campus.web.domain.UserType;
import com.resoneuronance.campus.web.util.Constants;
import com.resoneuronance.campus.web.util.FileUtils;

@SessionAttributes("session")
@Controller
public class AdminController implements Constants {
	
	private CollegeBO collegeBo;
	private String resultMsg;

	public CollegeBO getCollegeBo() {
		return collegeBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "collegeBO")
	public void setCollegeBo(CollegeBO collegeBo) {
		this.collegeBo = collegeBo;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RedirectView welcome(@ModelAttribute(COLLEGE_OBJECT) College college,ModelMap model) {

		if (collegeBo.login(college)) {
			initAdminDashboard(model,collegeBo);
			//System.out.println(collegeBo.getCurrentDepartments().get(0).getName());
			return new RedirectView(ADMIN_DASHBOARD_URL);
		}
		model.addAttribute(LOGIN_ERROR, "Invalid username or password!");
		return new RedirectView(REDIRECT_URL);

	}

	public static void initAdminDashboard(ModelMap model,CollegeBO collegeBO) {
		College currentCollege = collegeBO.getCurrentCollege();
		model.addAttribute(COLLEGE_OBJECT, currentCollege);
		UserSession session = new UserSession();
		session.setSessionObject(currentCollege);
		session.setType(UserType.ADMIN);
		model.addAttribute(SESSION_OBJECT, session);
		model.addAttribute("studentsCount", collegeBO.getCurrentStudents().size() + " students");
		model.addAttribute("teachersCount", collegeBO.getCurrentTeachers().size() + " teachers");
		model.addAttribute("departmentsCount", collegeBO.getCurrentDepartments().size() + " departments");
	}

	@RequestMapping(value = "/admin.htm", method = RequestMethod.GET)
	public String initAdmin(ModelMap model) {
		initAdminDashboard(model,collegeBo);
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
	public RedirectView editTeacher(@ModelAttribute(TEACHER_OBJECT) Teacher teacher,ModelMap model){
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
	public RedirectView addTeacher(@ModelAttribute(TEACHER_OBJECT) Teacher teacher,ModelMap model) throws Exception {
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
}
