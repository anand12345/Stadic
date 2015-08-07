package com.resoneuronance.campus.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.resoneuronance.campus.web.bo.TeacherBO;
import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.Teacher;
import com.resoneuronance.campus.web.util.Constants;

@SessionAttributes("session")
@Controller
public class TeacherController implements Constants {
	
	private TeacherBO teacherBo;
	private String resultMsg;

	public TeacherBO getTeacherBo() {
		return teacherBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "teacherBO")
	public void setTeacherBo(TeacherBO teacherBO) {
		this.teacherBo = teacherBO;
	}

	@RequestMapping(value = "/teacherRegistration", method = RequestMethod.POST)
	public RedirectView registerTeacher(@ModelAttribute(TEACHER_OBJECT) Teacher teacher,@RequestParam("collegeName") String college,ModelMap model) {
		
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
	public RedirectView teacherLogin(@ModelAttribute(TEACHER_OBJECT) Teacher teacher,@RequestParam("collegeName") String college,ModelMap model) {
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
	public RedirectView tagTeacherToDepartment(@ModelAttribute(TEACHER_OBJECT) com.resoneuronance.campus.web.bo.domain.Teacher teacher,ModelMap model) {
		RedirectView view = new RedirectView(TEACHER_PROFILE_URL);
		teacherBo.tagDepartmentToTeacher(teacher);
		return view;
	}
	
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public RedirectView notify(Notification notification,ModelMap model,@RequestParam("file") MultipartFile file ) {
		RedirectView view = new RedirectView();
		view.setUrl(TEACHER_PROFILE_URL);
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
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value = "/curriculars.htm", method= RequestMethod.GET)
	public String showCurricularNotifications(ModelMap model) {
		
		model.addAttribute(TEACHER_OBJECT, teacherBo.getCurrentTeacher());
		return CURRICULAR_NOTIFICATIONS_PAGE;
	}
	
	@RequestMapping(value = "/coCurriculars.htm", method= RequestMethod.GET)
	public String showCoCurricularNotifications(ModelMap model) {
		
		model.addAttribute(TEACHER_OBJECT, teacherBo.getCurrentTeacher());
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
		model.addAttribute(TEACHER_OBJECT,teacherBo.getCurrentTeacher());
		return TEACHER_DEPARTMENT_PAGE ;
	}
	
	@RequestMapping(value = "/teacherProfile.htm", method= RequestMethod.GET)
	public String teacherProfile(ModelMap model) {
		
		model.addAttribute(RESULT_MESSAGE, resultMsg);
		model.addAttribute(TEACHER_OBJECT, teacherBo.getCurrentTeacher());
		return TEACHER_PROFILE_PAGE;
	}
	
	
}
