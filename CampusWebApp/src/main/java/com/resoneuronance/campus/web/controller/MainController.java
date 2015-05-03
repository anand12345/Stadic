package com.resoneuronance.campus.web.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.domain.UserSession;
import com.resoneuronance.campus.web.domain.UserType;
import com.resoneuronance.campus.web.util.FileUtils;

@SessionAttributes("session")
@Controller
public class MainController {

	private static final String REDIRECT_URL = "/pages/";
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

	/*
	 * @RequestMapping(value = "/main", method = RequestMethod.POST) public
	 * String welcome(@ModelAttribute("college")College college, ModelMap model)
	 * {
	 * 
	 * collegeBo.addNew(college); model.addAttribute("college",
	 * collegeBo.getColleges().get(collegeBo.getColleges().size()-1)); return
	 * "home";
	 * 
	 * }
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String welcome(@ModelAttribute("college") College college, ModelMap model) {

		if (collegeBo.login(college)) {
			System.out.println("Id after:" +college.getId());
			College currentCollege = collegeBo.getCurrentCollege();
			model.addAttribute("college", currentCollege);
			UserSession session = new UserSession();
			session.setSessionObject(currentCollege);
			session.setType(UserType.ADMIN);
			model.addAttribute("session", session);
			//System.out.println(collegeBo.getCurrentDepartments().get(0).getName());
			return "admin_profile";
		}
		model.addAttribute("loginError", "Invalid username or password!");
		model.addAttribute("colleges", collegeBo.getCollegeNames());
		return "login";

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init(ModelMap model) {

		System.out.println("Here in Init!");
		
		String screen = checkSessionAvailability(model);
		if(StringUtils.isNotEmpty(screen)) {
			return screen;
		}
		model.addAttribute("college", new College());
		model.addAttribute("colleges", collegeBo.getCollegeNames());
		return "login";

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		model.remove("session");
		model.addAttribute("college", new College());
		model.addAttribute("colleges", collegeBo.getCollegeNames());
		return "login";
	}

	private String checkSessionAvailability(ModelMap model) {
		if(model.get("session")==null) {
			return null;
		}
		UserSession session = (UserSession) model.get("session");
		if(UserType.ADMIN.equals(session.getType())) {
			College college = (College) session.getSessionObject();
			collegeBo.login(college);
			model.addAttribute("college", collegeBo.getCurrentCollege());
			return "admin_profile";
		}
		return "admin_profile";
	}

	@RequestMapping(value = "/uploadDepartments", method = RequestMethod.POST)
	public RedirectView uploadDepartments(@RequestParam CommonsMultipartFile[] departmentsFile,ModelMap model) throws Exception {
		
		RedirectView view = new RedirectView("department.htm");
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
		return "departmentadd";
	}
	
	
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	public RedirectView addDepartment(@ModelAttribute("department") Department dept,ModelMap model) throws Exception {
		collegeBo.addDepartment(dept);
		resultMsg = "Added department " + dept.getName();
		return new RedirectView("department.htm");
	}
	
	@RequestMapping(value = "/department.htm", method= RequestMethod.GET)
	public String redirectDepartment(ModelMap model) {
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("departments", collegeBo.getCurrentDepartments());
		return "department";
	}
	
	@RequestMapping(value = "/{id}", method= RequestMethod.POST)
	public RedirectView deleteDepartment(@PathVariable String id, ModelMap model) {
		
		System.out.println("ID:" + id);
		collegeBo.deleteDepartment(id);
		resultMsg = "Deleted the department successfully!";
		return new RedirectView("department.htm");
	}
	
	@RequestMapping(value = "/uploadTeachers", method = RequestMethod.POST)
	public RedirectView uploadTeachers(@RequestParam CommonsMultipartFile[] teachersFile,ModelMap model) throws Exception {
		
		RedirectView view = new RedirectView("teacher.htm");
		if (teachersFile != null && teachersFile.length > 0) {
			for (CommonsMultipartFile aFile : teachersFile) {
				System.out.println("Uploading file: " + aFile.getOriginalFilename());
				resultMsg= FileUtils.uploadTeachersExcel(aFile.getInputStream(),collegeBo);
				break;
			}
		}
		return view;
	}
	
	@RequestMapping(value = "/teacher.htm", method= RequestMethod.GET)
	public String redirectTeacher(ModelMap model) {
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("teachers", collegeBo.getCurrentTeachers());
		return "teacher";
	}
	
	@RequestMapping(value = "/{id}.teacher", method= RequestMethod.POST)
	public RedirectView deleteTeacher(@PathVariable String id, ModelMap model) {
		
		System.out.println("ID:" + id);
		collegeBo.deleteTeacher(id);
		resultMsg = "Deleted the teacher successfully!";
		return new RedirectView("teacher.htm");
	}
	
	@RequestMapping(value = "/teacheradd", method = RequestMethod.GET)
	public String initTeacher(ModelMap model) throws Exception {
		model.addAttribute("teacher", new Teacher());
		return "teacheradd";
	}
	
	
	@RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
	public RedirectView addTeacher(@ModelAttribute("teacher") Teacher teacher,ModelMap model) throws Exception {
		collegeBo.addTeacher(teacher);
		resultMsg = "Added teacher " + teacher.getName();
		return new RedirectView("teacher.htm");
	}
	
	@RequestMapping(value = "/uploadStudents", method = RequestMethod.POST)
	public RedirectView uploadStudents(@RequestParam CommonsMultipartFile[] studentsFile,ModelMap model) throws Exception {
		
		RedirectView view = new RedirectView("student.htm");
		if (studentsFile != null && studentsFile.length > 0) {
			for (CommonsMultipartFile aFile : studentsFile) {
				System.out.println("Uploading file: " + aFile.getOriginalFilename());
				resultMsg= FileUtils.uploadStudentsExcel(aFile.getInputStream(),collegeBo);
				break;
			}
		}
		return view;
	}
	
	@RequestMapping(value = "/student.htm", method= RequestMethod.GET)
	public String redirectStudent(ModelMap model) {
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("students", collegeBo.getCurrentStudents());
		return "student";
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
		return new RedirectView("student.htm");
	}
	
	@RequestMapping(value = "/{id}.student", method= RequestMethod.POST)
	public RedirectView deleteStudent(@PathVariable String id, ModelMap model) {
		
		System.out.println("ID:" + id);
		collegeBo.deleteStudent(id);
		resultMsg = "Deleted the student successfully!";
		return new RedirectView("student.htm");
	}
	
	
	//TODO : To be removed later
	
	/*@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String temp(@PathVariable String name, ModelMap model) {

		System.out.println("Showing " + name);
		return name;

	}*/
	
}
