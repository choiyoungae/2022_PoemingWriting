package com.cya.poeming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.poeming.service.ReactionService;
import com.cya.poeming.vo.ResultData;
import com.cya.poeming.vo.Rq;

@Controller
public class UsrReactionController {
	@Autowired
	private ReactionService reactionService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reaction/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData addGoodReactionPointRd = reactionService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(addGoodReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reaction/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData addBadReactionPointRd = reactionService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(addBadReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reaction/doCancelGoodReaction")
	@ResponseBody
	public String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData deleteGoodReactionPointRd = reactionService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(deleteGoodReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reaction/doCancelBadReaction")
	@ResponseBody
	public String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData deleteBadReactionPointRd = reactionService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(deleteBadReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reaction/doBookmark")
	@ResponseBody
	public String doBookmark(int relId, String replaceUri) {
		
		ResultData actorCanMakeBookmarkRd = reactionService.actorCanMakeBookmark(rq.getLoginedMemberId(), relId);

		if (actorCanMakeBookmarkRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeBookmarkRd.getMsg());
		}
		
		ResultData addBookmarkRd = reactionService.addBookmark(rq.getLoginedMemberId(), relId);

		return rq.jsReplace(addBookmarkRd.getMsg(), replaceUri);	
	}
	
	@RequestMapping("/usr/reaction/doCancelBookmark")
	@ResponseBody
	public String doCancelBookmark(int relId, String replaceUri) {
		
		ResultData actorCanMakeBookmarkRd = reactionService.actorCanMakeBookmark(rq.getLoginedMemberId(), relId);

		if (actorCanMakeBookmarkRd.isSuccess()) {
			return rq.jsHistoryBack(actorCanMakeBookmarkRd.getMsg());
		}
		
		ResultData deleteBookmarkRd = reactionService.deleteBookmark(rq.getLoginedMemberId(), relId);
		
		return rq.jsReplace(deleteBookmarkRd.getMsg(), replaceUri);
	}
	
//	댓글 좋아요 기능 시도중
//	@RequestMapping("/usr/reaction/checkActorMakeReplyReaction")
//	@ResponseBody
//	public boolean checkActorMakeReplyReaction(int id) {
//		
//		if(rq.getLoginedMember() == null) {
//			return false;
//		}
//		
//		ResultData actorCanMakeReactionRd = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), "reply", id);
//
//		return actorCanMakeReactionRd.isSuccess();
//	}
//	
//	@RequestMapping("/usr/reaction/checkActorCanCancelReplyReaction")
//	@ResponseBody
//	public boolean checkActorCanCancelReplyReaction(int relId, String reaction) {
//		
//		if(rq.getLoginedMember() == null) {
//			return false;
//		}
//		
//		ResultData actorCanMakeReactionRd = reactionService.actorCanMakeReaction(rq.getLoginedMemberId(), "reply", relId);
//
//		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
//			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();
//			
//			if (sumReactionPointByMemberId > 0) {
//				if(reaction.equals("good")) {
//					return true;
//				}
//			} else {
//				if(reaction.equals("bad")) {
//					return true;
//				}
//			}
//			
//		}
//		
//		return false;
//	}
}