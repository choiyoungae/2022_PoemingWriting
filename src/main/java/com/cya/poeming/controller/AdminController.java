package com.cya.poeming.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.poeming.service.ArticleService;
import com.cya.poeming.service.MemberService;
import com.cya.poeming.util.Ut;
import com.cya.poeming.vo.Article;
import com.cya.poeming.vo.Member;
import com.cya.poeming.vo.Rq;

@Controller
public class AdminController {
	@Autowired
	private Rq rq;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ArticleService articleService;
	
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

		int itemsInAPage = 5;
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
	
	@RequestMapping("/adm/member/doDeleteMembers")
	@ResponseBody
	public String doDeleteMembers(@RequestParam(defaultValue = "") String ids,
			@RequestParam(defaultValue = "/adm/member/list") String replaceUri) {
		List<Integer> memberIds = new ArrayList<>();

		for (String idStr : ids.split(",")) {
			memberIds.add(Integer.parseInt(idStr));
		}

		memberService.deleteMembers(memberIds);

		return Ut.jsReplace("해당 회원(들)이 삭제되었습니다.", replaceUri);
	}
	
	@RequestMapping("/adm/article/reportedList")
	public String showReportedList(Model model, @RequestParam(defaultValue = "1") int page) {

		int reportedArticlesCount = articleService.getReportedArticlesCount();

		int itemsInAPage = 5;
		int pagesCount = (int) Math.ceil((double) reportedArticlesCount / itemsInAPage);

		List<Article> reportedArticles = articleService.getForPrintReportedMembers(itemsInAPage, page);

		model.addAttribute("reportedArticles", reportedArticles);
		model.addAttribute("reportedArticlesCount", reportedArticlesCount);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("page", page);

		return "adm/article/reportedList";
	}
	
	@RequestMapping("/adm/article/doDeleteArticles")
	@ResponseBody
	public String doDeleteArticles(@RequestParam(defaultValue = "") String ids,
			@RequestParam(defaultValue = "/adm/article/reportedList") String replaceUri) {
		List<Integer> articleIds = new ArrayList<>();

		for (String idStr : ids.split(",")) {
			articleIds.add(Integer.parseInt(idStr));
		}

		articleService.deleteArticles(articleIds);

		return Ut.jsReplace("해당 게시물(들)이 삭제되었습니다.", replaceUri);
	}
	
}