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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.resoneuronance.campus.web.bo.StudentBO;
import com.resoneuronance.campus.web.bo.TeacherBO;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.util.Constants;

@Controller
public class StudentAndroidController implements Constants {

	private StudentBO studentBo;
	private TeacherBO teacherBo;
	 
	public StudentBO getStudentBo() {
		return studentBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "studentBO")
	public void setStudentBo(StudentBO studentBo) {
		this.studentBo = studentBo;
	}
	
	public TeacherBO getTeacherBo() {
		return teacherBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "teacherBO")
	public void setTeacherBo(TeacherBO teacherBO) {
		this.teacherBo = teacherBO;
	}
	
	@RequestMapping(value = "/shareStudentRegId", method= RequestMethod.POST)
	public @ResponseBody String shareRegId(@RequestParam(value = REG_ID) String regId,ModelMap model) {
		studentBo.storeRegId(regId);
		return RESPONSE_OK;
	}
	
	@RequestMapping(value = "/loginStudentAndroid", method= RequestMethod.POST)
	public @ResponseBody String loginStudentAndroid(@RequestParam(value = STUDENT_OBJECT) String student,@RequestParam(value = COLLEGE_NAME_ATTR) String collegeName) {
		Student st = new Gson().fromJson(student, Student.class);
		System.out.println("Got Student :" + st.getEmail());
		if(studentBo.login(st, collegeName)) {
			return new Gson().toJson(studentBo.getCurrentStudent());
		}
		return RESPONSE_INVALID;
	}
	
	@RequestMapping(value = "/downloadStudentDocument", method= RequestMethod.GET)
	public void  downloadDocument(@RequestParam("id") int id,HttpServletResponse response, ModelMap model) {
		InputStream is =  null;
		String responseString = "";
		try {
			is = teacherBo.downloadDocument(id);
			//responseString = IOUtils.toString(is);
			//IOUtils.closeQuietly(is);
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
	
}
