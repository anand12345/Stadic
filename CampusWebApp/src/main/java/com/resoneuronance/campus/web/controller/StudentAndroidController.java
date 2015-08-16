package com.resoneuronance.campus.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.resoneuronance.campus.web.bo.StudentBO;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.util.Constants;

public class StudentAndroidController implements Constants {

	private StudentBO studentBo;
	 
	public StudentBO getStudentBo() {
		return studentBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "studentBO")
	public void setStudentBo(StudentBO studentBo) {
		this.studentBo = studentBo;
	}
	
	@RequestMapping(value = "/shareRegId", method= RequestMethod.POST)
	public @ResponseBody String tagTeachers(@RequestParam(value = REG_ID) String regId,ModelMap model) {
		//studentBo.saveRegId(regId);
		studentBo.storeRegId(regId);
		return RESPONSE_OK;
	}
	
	@RequestMapping(value = "/loginStudentAndroid", method= RequestMethod.POST)
	public @ResponseBody String loginStudentAndroid(@RequestParam(value = STUDENT_OBJECT) String student,@RequestParam(value = COLLEGE_NAME_ATTR) String collegeName,ModelMap model) {
		//studentBo.saveRegId(regId);
		Student st = new Gson().fromJson(student, Student.class);
		if(studentBo.login(st, collegeName)) {
			return new Gson().toJson(studentBo.getCurrentStudent());
		}
		return RESPONSE_INVALID;
	}
	
}
