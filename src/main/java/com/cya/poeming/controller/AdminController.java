package com.cya.poeming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cya.poeming.service.MemberService;
import com.cya.poeming.vo.Member;
import com.cya.poeming.vo.Rq;

@Controller
public class AdminController {
	@Autowired
	private Rq rq;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/adm/home/main")
	public String showMain(Model model) {
		
		Member loginedMember = memberService.getMemberById(rq.getLoginedMemberId());
		
		if(loginedMember != null) {
			boolean isAdminLogined = memberService.getMemberById(rq.getLoginedMemberId()).getAuthLevel() == 7;
			model.addAttribute("isAdminLogined", isAdminLogined);
			
		}
		
		return "adm/home/main";
	}
	
	@RequestMapping("/adm/member/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") String authLevel,
			@RequestParam(defaultValue = "loginId,name,nickname") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		int membersCount = memberService.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 7;
		int pagesCount = (int) Math.ceil((double) membersCount / itemsInAPage);

		List<Member> members = memberService.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				itemsInAPage, page);

		model.addAttribute("authLevel", authLevel);
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);

		model.addAttribute("members", members);
		model.addAttribute("membersCount", membersCount);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("page", page);

		return "adm/member/list";
	}
	
}