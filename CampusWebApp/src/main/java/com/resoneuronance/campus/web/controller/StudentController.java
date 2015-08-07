package com.resoneuronance.campus.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.resoneuronance.campus.web.bo.StudentBO;
import com.resoneuronance.campus.web.bo.domain.StudentDepartment;
import com.resoneuronance.campus.web.bo.domain.StudentTeacher;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.StudentRegID;
import com.resoneuronance.campus.web.domain.UserSession;
import com.resoneuronance.campus.web.domain.UserType;
import com.resoneuronance.campus.web.util.Constants;

@SessionAttributes("session")
@Controller
public class StudentController implements Constants {
	
	private StudentBO studentBo;
	private String resultMsg;
	 
	public StudentBO getStudentBo() {
		return studentBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "studentBO")
	public void setStudentBo(StudentBO studentBo) {
		this.studentBo = studentBo;
	}

	@RequestMapping(value = "/studentRegistration", method = RequestMethod.POST)
	public RedirectView registerStudent(@ModelAttribute(STUDENT_OBJECT) Student student,@RequestParam("collegeName") String college,
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
	public RedirectView teacherLogin(@RequestParam(REG_ID) String regId,@ModelAttribute(STUDENT_OBJECT) Student student,@RequestParam("collegeName") String college,ModelMap model) {
		RedirectView view = new RedirectView();
		if(!studentBo.login(student, college,regId)) {
			view.setUrl(REDIRECT_URL);
			resultMsg = "Incorrect credentials!";
		}
		else {
			view.setUrl(STUDENT_PROFILE_URL);
			createStudentSession(model);
		}
		return view;
	}

	private void createStudentSession(ModelMap model) {
		UserSession session = new UserSession();
		session.setSessionObject(studentBo.getCurrentStudent());
		session.setType(UserType.STUDENT);
		model.addAttribute("session", session);
	}
	
	@RequestMapping(value = "/selectTeachers.htm", method= RequestMethod.GET)
	public String selectTeachers(ModelMap model) {
		
		model.addAttribute("teachers", studentBo.getCurrentTeachers());
		return TEACHER_SELECT_PAGE;
	}
	
	@RequestMapping(value = "/selectDepartments.htm", method= RequestMethod.GET)
	public String selectDepartments(ModelMap model) {
		
		model.addAttribute("departments", studentBo.getCurrentDepartments());
		return DEPARTMENT_SELECT_PAGE;
	}
	
	@RequestMapping(value = "/selectDepartments", method= RequestMethod.POST)
	public RedirectView tagDepartments(int [] departmentIds,ModelMap model) {
		
		studentBo.tagDepartments(departmentIds);
		return new RedirectView(STUDENT_PROFILE_URL);
	}
	
	@RequestMapping(value = "/selectTeachers", method= RequestMethod.POST)
	public RedirectView tagTeachers(int [] teacherIds,ModelMap model) {
		
		studentBo.tagTeachers(teacherIds);
		return new RedirectView(STUDENT_PROFILE_URL);
	}
	
	@RequestMapping(value = "/showDepartmentNotifications", method= RequestMethod.POST)
	public RedirectView showNotifications(StudentDepartment department,ModelMap model) {
		
		studentBo.getCurrentStudent().setCurrentDepartment(department);
		return new RedirectView(STUDENT_DEPARTMENT_NOTIFICATIONS_URL);
	}
	
	@RequestMapping(value = "/showTeacherNotifications", method= RequestMethod.POST)
	public RedirectView showNotifications(StudentTeacher teacher,ModelMap model) {
		
		studentBo.getCurrentStudent().setCurrentTeacher(teacher);
		return new RedirectView(STUDENT_TEACHER_NOTIFICATIONS_URL);
	}
	
	@RequestMapping(value = "/showTeacherNotifications.htm", method= RequestMethod.GET)
	public String refreshTeacherNotifications(ModelMap model) {
		studentBo.updateTeacherNotifications(studentBo.getCurrentStudent().getCurrentTeacher());
		studentBo.updateStudentToTeacherMapping(studentBo.getCurrentStudent().getCurrentTeacher());
		model.addAttribute(STUDENT_TEACHER_OBEJECT,studentBo.getCurrentStudent().getCurrentTeacher());
		model.addAttribute(STUDENT_OBJECT, studentBo.getCurrentStudent());
		System.out.println("Teacher notifications refreshed..");
		return STUDENT_TEACHER_NOTIFICATIONS_PAGE;
	}
	
	@RequestMapping(value = "/showDepartmentNotifications.htm", method= RequestMethod.GET)
	public String refreshDepartmentNotifications(ModelMap model) {
		studentBo.updateDepartmentNotifications(studentBo.getCurrentStudent().getCurrentDepartment());
		studentBo.updateStudentToDepartmentMapping(studentBo.getCurrentStudent().getCurrentDepartment());
		model.addAttribute(STUDENT_DEPARTMENT_OBEJECT,studentBo.getCurrentStudent().getCurrentDepartment());
		model.addAttribute(STUDENT_OBJECT, studentBo.getCurrentStudent());
		System.out.println("Department notifications refreshed..");
		return STUDENT_DEPARTMENT_NOTIFICATIONS_PAGE;
	}
	
	@RequestMapping(value = "/teachersList.htm", method= RequestMethod.GET)
	public String teachersList(ModelMap model) {
		
		studentBo.prepareStudentTeachers();
		model.addAttribute(STUDENT_OBJECT, studentBo.getCurrentStudent());
		return TEACHERS_LIST_PAGE;
	}
	
	@RequestMapping(value = "/departmentsList.htm", method= RequestMethod.GET)
	public String departmentsList(ModelMap model) {
		
		studentBo.prepareStudentDepartments();
		model.addAttribute(STUDENT_OBJECT, studentBo.getCurrentStudent());
		return DEPARTMENTS_LIST_PAGE;
	}
	
	@RequestMapping(value = "/studentProfile.htm", method= RequestMethod.GET)
	public String studentProfile(ModelMap model) {
		
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		model.addAttribute(STUDENT_OBJECT, studentBo.getCurrentStudent());
		return STUDENT_PROFILE_PAGE;
	}
	
	/*Android specific URLs*/
	
	@RequestMapping(value = "/shareRegId", method= RequestMethod.POST)
	public @ResponseBody String tagTeachers(@RequestParam(value = REG_ID) String regId,ModelMap model) {
		//studentBo.saveRegId(regId);
		StudentRegID id = new Gson().fromJson(regId, StudentRegID.class);
		System.out.println(id.getRegId());
		return "Done!";
	}
	
	@RequestMapping(value = "/loginStudentAndroid", method= RequestMethod.POST)
	public @ResponseBody String loginStudentAndroid(@RequestParam(value = "student") String student,ModelMap model) {
		//studentBo.saveRegId(regId);
		Student st = new Gson().fromJson(student, Student.class);
		System.out.println(st.getEmail());
		return "Done!";
	}
	
}
