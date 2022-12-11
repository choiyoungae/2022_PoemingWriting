package com.cya.poeming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.poeming.service.ArticleService;
import com.cya.poeming.service.ReplyService;
import com.cya.poeming.util.Ut;
import com.cya.poeming.vo.Article;
import com.cya.poeming.vo.Reply;
import com.cya.poeming.vo.ResultData;
import com.cya.poeming.vo.Rq;

@Controller
public class UsrReplyController {
	@Autowired
	private Rq rq;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doAdd(String relTypeCode, int relId, String body, String replaceUri) {

		if (Ut.isEmpty(relTypeCode)) {
			return rq.jsHistoryBack("relTypeCode을(를) 입력해주세요.");
		}

		if (Ut.isEmpty(relId)) {
			return rq.jsHistoryBack("relId을(를) 입력해주세요.");
		}

		if (Ut.isEmpty(body)) {
			return rq.jsHistoryBack("body을(를) 입력해주세요.");
		}


		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);
		int id = writeReplyRd.getData1();
		
		if (Ut.isEmpty(replaceUri)) {
			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}

		}
		
		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {
		
		if(Ut.isEmpty(id)) {
			return rq.jsHistoryBack("id가 없습니다.");
		}
		
		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);
		
		if(reply == null) {
			return rq.jsHistoryBack(Ut.f("존재하지 않는 댓글입니다.", id));
		}
		
		if (reply.isExtra__actorCanDelete() == false) {
			return rq.jsHistoryBack("해당 댓글을 삭제할 권한이 없습니다.");
		}
		
		ResultData deleteReplyRd = replyService.deleteReply(id);
		
		if (Ut.isEmpty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}

		}

		return rq.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/modify")
	public String showModify(Model model, int id) {
		
		if(Ut.isEmpty(id)) {
			return rq.jsHistoryBack("id가 없습니다.");
		}

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);
		
		if(reply == null) {
			return rq.jsHistoryBack(Ut.f("존재하지 않는 댓글입니다.", id));
		}
		if (reply.isExtra__actorCanModify() == false) {
			return rq.jsHistoryBack("해당 댓글을 수정할 권한이 없습니다.");
		}
		
		String relDataTitle = null;
		String relDataBody = null;

		switch (reply.getRelTypeCode()) {
		case "article":
			Article article = articleService.getArticle(reply.getRelId());
			relDataTitle = article.getTitle();
			relDataTitle = article.getBody();
			break;
		}

		model.addAttribute("reply", reply);
		model.addAttribute("relDataTitle", relDataTitle);
		
		return "usr/reply/modify";
	}

	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(int id, String body, String replaceUri) {

		if (Ut.isEmpty(id)) {
			return rq.jsHistoryBack("id가 없습니다.");
		}

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);

		if (reply == null) {
			return rq.jsHistoryBack(Ut.f("존재하지 않는 댓글입니다.", id));
		}

		if (reply.isExtra__actorCanModify() == false) {
			return rq.jsHistoryBack("해당 댓글을 삭제할 권한이 없습니다.");
		}

		if (Ut.isEmpty(body)) {
			return rq.jsHistoryBack("내용을 입력해주세요.");
		}

		ResultData modifyReplyRd = replyService.modifyReply(id, body);

		if (Ut.isEmpty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}

		}

		return rq.jsReplace(modifyReplyRd.getMsg(), replaceUri);
	}
	
}