package com.cya.poeming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cya.poeming.service.MemberService;
import com.cya.poeming.vo.Member;
import com.cya.poeming.vo.Rq;

@Controller
public class UsrAdminController {
	@Autowired
	private Rq rq;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/home/admin")
	public String showMain(Model model) {
		
		Member loginedMember = memberService.getMemberById(rq.getLoginedMemberId());
		
		if(loginedMember != null) {
			boolean isAdminLogined = memberService.getMemberById(rq.getLoginedMemberId()).getAuthLevel() == 7;
			model.addAttribute("isAdminLogined", isAdminLogined);
			
		}
		
		return "usr/home/admin";
	}
	
}