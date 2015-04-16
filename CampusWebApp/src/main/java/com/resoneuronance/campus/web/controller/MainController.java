package com.resoneuronance.campus.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.domain.College;

@Controller
public class MainController {
	
	private CollegeBO collegeBo;
	
	public CollegeBO getCollegeBo() {
		return collegeBo;
	}

	 @Autowired(required=true)
	 @Qualifier(value="collegeBO")
	public void setCollegeBo(CollegeBO collegeBo) {
		this.collegeBo = collegeBo;
	}

	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public String welcome(@ModelAttribute("college")College college, ModelMap model) {
		
		collegeBo.addNew(college);
		model.addAttribute("college", collegeBo.getColleges().get(collegeBo.getColleges().size()-1));
		return "home";

	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init(ModelMap model) {
		
		System.out.println("Here in Init!");
		model.addAttribute("college", collegeBo.getColleges().get(collegeBo.getColleges().size()-1));
		return "home";

	}

}
