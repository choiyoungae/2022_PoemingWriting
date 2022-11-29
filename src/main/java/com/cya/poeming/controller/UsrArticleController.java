package com.cya.poeming.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.poeming.service.ArticleService;
import com.cya.poeming.service.BoardService;
import com.cya.poeming.service.ReactionService;
import com.cya.poeming.service.ReplyService;
import com.cya.poeming.util.Ut;
import com.cya.poeming.vo.Article;
import com.cya.poeming.vo.Board;
import com.cya.poeming.vo.Reply;
import com.cya.poeming.vo.ResultData;
import com.cya.poeming.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReactionService reactionService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/article/write")
	public String showAdd(HttpSession httpSession) {
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doAdd(int boardId, String title, String body, String replaceUri) {
		
		if (Ut.isEmpty(title)) {
			return rq.jsHistoryBack("제목을 입력해주세요");
		}
		if (Ut.isEmpty(body)) {
			return rq.jsHistoryBack("내용을 입력해주세요");
		}
		
		if(boardId == 1) {
			if(rq.getLoginedMember().getAuthLevel() != 7) {
				return rq.jsHistoryBack("작성 권한이 없습니다.");
			}
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(boardId, title, body, rq.getLoginedMemberId());

		int id = (int) writeArticleRd.getData1();

		if (Ut.isEmpty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}
		
		return rq.jsReplace(Ut.f("%d번 글이 작성되었습니다.", id), replaceUri);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "title, body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword) {

		Board board = boardService.getBoardById(boardId);
		
		if(board == null) {
			return rq.jsHistoryBack("존재하지 않는 게시판입니다.");
		}

		int itemsInAPage = 10;

		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId, itemsInAPage, page, searchKeywordTypeCode, searchKeyword);
		
		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		model.addAttribute("articles", articles);
		model.addAttribute("board", board);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("pagesCount", (int)Math.ceil((double)articlesCount/itemsInAPage));
		model.addAttribute("page", page);

		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id, @RequestParam(defaultValue = "1") int replyPage) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);
		
		ResultData actorCanMakeReactionRd = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), "article", id);
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());
		
		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();
			
			if (sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			} else {
				model.addAttribute("actorCanCancelBadReaction", true);
			}
			
		}
		
		ResultData actorCanMakeBookmarkRd = reactionService.actorCanMakeBookmark(rq.getLoginedMemberId(), id);
		model.addAttribute("actorCanMakeBookmark", actorCanMakeBookmarkRd.isSuccess());
		
		// 댓글 리스팅 관련
		int repliesInAPage = 5;
		
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMember(), "article", id, repliesInAPage, replyPage);
		
		int repliesCount = replyService.getRepliesCount(id);

		model.addAttribute("replies", replies);
		model.addAttribute("repliesCount", repliesCount);
		model.addAttribute("replyPagesCount", (int)Math.ceil((double)repliesCount/repliesInAPage));
		model.addAttribute("replyPage", replyPage);
		
		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if(article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		
		if(article.getMemberId() != rq.getLoginedMemberId()) {
			return rq.jsHistoryBack("해당 게시물에 대한 권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		return rq.jsReplace(Ut.f("%d번 게시물이 삭제되었습니다.", id), "../article/list?boardId=1");
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if(article == null) {
			return rq.jsHistoryBack(Ut.f("%d번은 존재하지 않는 게시물입니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if(article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return rq.jsReplace(Ut.f("%d번 게시물을 수정했습니다", id), Ut.f("../article/detail?id=%d", id));
	}
	
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData<Integer> doIncreaseHitCountRd(int id) {
		ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);
		
		if(increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}
		
		ResultData<Integer> rd = ResultData.newData(increaseHitCountRd, "hitCount",
				articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}
}
