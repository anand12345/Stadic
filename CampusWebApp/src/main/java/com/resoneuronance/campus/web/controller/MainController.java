package com.resoneuronance.campus.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.bo.StudentBO;
import com.resoneuronance.campus.web.domain.AutocompleteTag;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.domain.UserSession;
import com.resoneuronance.campus.web.domain.UserType;
import com.resoneuronance.campus.web.util.Constants;

@SessionAttributes("session")
@Controller
public class MainController implements Constants {

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

	
	@RequestMapping(value = "/initAndroid", method = RequestMethod.GET)
	public String initAndroid(@RequestParam(REG_ID) String regId, ModelMap model) {

		System.out.println("Here in Init!");
		String screen = prepareLoginPage(model);
		model.addAttribute(REG_ID, regId);
		return screen;

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
		model.addAttribute(STUDENT_OBJECT,new Student());
		model.addAttribute(TEACHER_OBJECT,new Teacher());
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
	public RedirectView logout(ModelMap model,SessionStatus status) {
		model.remove("session");
		status.setComplete();
		return new RedirectView(REDIRECT_URL);
	}

	private String checkSessionAvailability(ModelMap model) {
		if(model.get("session")==null) {
			return null;
		}
		UserSession session = (UserSession) model.get("session");
		if(UserType.ADMIN.equals(session.getType())) {
			College college = (College) session.getSessionObject();
			collegeBo.login(college);
			AdminController.initAdminDashboard(model,collegeBo);
			return ADMIN_PROFILE_PAGE;
		}
		else if(UserType.STUDENT.equals(session.getType())) {
			com.resoneuronance.campus.web.bo.domain.Student student = (com.resoneuronance.campus.web.bo.domain.Student) session.getSessionObject();
			studentBo.prepareCurrentStudent(student);
			model.addAttribute(STUDENT_OBJECT, studentBo.getCurrentStudent());
			return STUDENT_PROFILE_PAGE;
		}
		return null;
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
		model.addAttribute(TEACHER_OBJECT, new Teacher());
		model.addAttribute(STUDENT_OBJECT, new Student());
		if(TEACHER_OBJECT.equals(type)) {
			model.addAttribute("register", "registerTeacher");
			model.addAttribute("action", "teacherRegistration");
		}
		else if(STUDENT_OBJECT.equals(type)) {
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
}
