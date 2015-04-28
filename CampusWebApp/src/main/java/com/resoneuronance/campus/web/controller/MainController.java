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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.domain.College;
import com.resoneuronance.campus.web.util.FileUtils;

@Controller
public class MainController {

	private CollegeBO collegeBo;
	

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

		if(collegeBo.getCurrentCollege()!=null) {
			model.addAttribute("departments", collegeBo.getCurrentDepartments());
			return "home";
		}
		
		if (collegeBo.login(college)) {
			System.out.println("Id after:" +college.getId());
			model.addAttribute("college", collegeBo.getCurrentCollege());
			model.addAttribute("departments", collegeBo.getCurrentDepartments());
			System.out.println(collegeBo.getCurrentDepartments().get(0).getName());
			return "home";
		}
		model.addAttribute("loginError", "Invalid username or password!");
		model.addAttribute("colleges", collegeBo.getCollegeNames());
		return "login";

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init(ModelMap model) {

		System.out.println("Here in Init!");
		// model.addAttribute("college",
		// collegeBo.getColleges().get(collegeBo.getColleges().size()-1));
		model.addAttribute("college", new College());
		model.addAttribute("colleges", collegeBo.getCollegeNames());
		return "admin_profile";

	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam CommonsMultipartFile[] fileUpload,ModelMap model) throws Exception {
		
		String resultMsg= "";
		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile aFile : fileUpload) {
				System.out.println("Uploading file: " + aFile.getOriginalFilename());
				resultMsg= FileUtils.uploadExcel(aFile.getInputStream(),collegeBo);
				break;
			}
		}
		model.addAttribute("college", collegeBo.getCurrentCollege());
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("departments", collegeBo.getCurrentDepartments());
		return "home";
	}
	
	@RequestMapping(value = "/{id}", method= RequestMethod.POST)
	public String deleteDepartment(@PathVariable String id, ModelMap model) {
		
		System.out.println("ID:" + id);
		collegeBo.deleteDepartment(id);
		model.addAttribute("college", collegeBo.getCurrentCollege());
		model.addAttribute("resultMsg", "");
		model.addAttribute("departments", collegeBo.getCurrentDepartments());
		return "home";
	}
	
}
