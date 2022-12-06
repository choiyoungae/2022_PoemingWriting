package com.cya.poeming.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cya.poeming.service.ArticleService;
import com.cya.poeming.vo.Article;
import com.cya.poeming.vo.Rq;

@Controller
public class UsrHomeController {
	@Autowired
	private Rq rq;
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/home/main")
	public String showMain(Model model) {
		
		List<Article> noticeArticles = articleService.getLimitedArticlesByBoardId(1, 3);
		List<Article> poemingArticles = articleService.getLimitedArticlesByBoardId(2, 3);
		List<Article> writingArticles = articleService.getLimitedArticlesByBoardId(3, 3);
		
		model.addAttribute("noticeArticles", noticeArticles);
		model.addAttribute("poemingArticles", poemingArticles);
		model.addAttribute("writingArticles", writingArticles);
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