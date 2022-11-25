package com.cya.poeming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.poeming.repository.ReactionPointRepository;
import com.cya.poeming.vo.ResultData;

@Service
public class ReactionPointService {
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;

	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if(actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요.");
		}
		
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "리액션을 할 수 없습니다", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		
		return ResultData.from("S-1", "리액션을 할 수 있습니다.");
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
		case "reply":
			replyService.increaseGoodReactionPoint(relId);
		}
		
		return ResultData.from("S-1", "좋아요 처리 완료");
	}

	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		case "reply":
			replyService.increaseBadReactionPoint(relId);
		}
		
		return ResultData.from("S-1", "별로예요 처리 완료");
	}

	public ResultData deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteGoodReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.decreaseGoodReactionPoint(relId);
			break;
		case "reply":
			replyService.decreaseGoodReactionPoint(relId);
		}
		
		return ResultData.from("S-1", "좋아요 취소 처리 되었습니다");
	}

	public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteBadReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
		case "reply":
			replyService.decreaseBadReactionPoint(relId);
		}
		
		return ResultData.from("S-1", "별로예요 취소 처리 되었습니다");
	}
}
