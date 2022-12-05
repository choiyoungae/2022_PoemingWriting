package com.cya.poeming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cya.poeming.service.ArticleService;
import com.cya.poeming.vo.Rq;

@Controller
public class UsrHomeController {
	@Autowired
	private Rq rq;
	@Autowired
	private ArticleService articleService;

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