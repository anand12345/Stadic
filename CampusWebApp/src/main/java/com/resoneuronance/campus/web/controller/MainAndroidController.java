package com.resoneuronance.campus.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.resoneuronance.campus.web.bo.CollegeBO;

@Controller
public class MainAndroidController {

	private CollegeBO collegeBo;

	public CollegeBO getCollegeBo() {
		return collegeBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "collegeBO")
	public void setCollegeBo(CollegeBO collegeBo) {
		this.collegeBo = collegeBo;
	}
	
	@RequestMapping(value = "/getAllColleges", method= RequestMethod.POST)
	public @ResponseBody String tagTeachers(ModelMap model) {
		String colleges = new Gson().toJson(collegeBo.getCollegeNames());
		return colleges;
	}

}
