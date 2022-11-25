package com.cya.poeming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.poeming.service.ReactionPointService;
import com.cya.poeming.vo.ResultData;
import com.cya.poeming.vo.Rq;

@Controller
public class UsrReactionPointController {
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData addGoodReactionPointRd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(addGoodReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData addBadReactionPointRd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(addBadReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/doCancelGoodReaction")
	@ResponseBody
	public String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData deleteGoodReactionPointRd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(deleteGoodReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/doCancelBadReaction")
	@ResponseBody
	public String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}
		
		ResultData deleteBadReactionPointRd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace(deleteBadReactionPointRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/checkActorMakeReplyReaction")
	@ResponseBody
	public boolean checkActorMakeReplyReaction(int id) {
		
		if(rq.getLoginedMember() == null) {
			return false;
		}
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), "reply", id);

		return actorCanMakeReactionRd.isSuccess();
	}
	
	@RequestMapping("/usr/reactionPoint/checkActorCanCancelReplyReaction")
	@ResponseBody
	public boolean checkActorCanCancelReplyReaction(int relId, String reaction) {
		
		if(rq.getLoginedMember() == null) {
			return false;
		}
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), "reply", relId);

		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();
			
			if (sumReactionPointByMemberId > 0) {
				if(reaction.equals("good")) {
					return true;
				}
			} else {
				if(reaction.equals("bad")) {
					return true;
				}
			}
			
		}
		
		return false;
	}
}