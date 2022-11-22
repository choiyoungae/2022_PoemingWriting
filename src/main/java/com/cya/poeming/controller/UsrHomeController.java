package com.cya.poeming.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.poeming.vo.Rq;

@Controller
public class UsrHomeController {
	private Rq rq;
	
	public UsrHomeController(Rq rq) {
		this.rq = rq;
	}

	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}
	
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main";
	}
	
	@RequestMapping("usr/home/map")
	String showMap() {
		return "usr/home/map";
	}
}