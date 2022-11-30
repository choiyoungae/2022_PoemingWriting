package com.cya.poeming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.poeming.repository.ReactionRepository;
import com.cya.poeming.vo.ResultData;

@Service
public class ReactionService {
	@Autowired
	private ReactionRepository reactionRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;

	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if(actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요.");
		}
		
		int sumReactionPointByMemberId = reactionRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "리액션을 할 수 없습니다", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		
		return ResultData.from("S-1", "리액션을 할 수 있습니다.");
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		
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
		reactionRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		
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
		reactionRepository.deleteGoodReactionPoint(actorId, relTypeCode, relId);
		
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
		reactionRepository.deleteBadReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
		case "reply":
			replyService.decreaseBadReactionPoint(relId);
		}
		
		return ResultData.from("S-1", "별로예요 취소 처리 되었습니다");
	}

	public ResultData actorCanMakeBookmark(int actorId, int relId) {
		if(actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요.");
		}
		
		int checkBookmarkByMemberId = reactionRepository.checkBookmarkByMemberId(actorId, relId);

		if (checkBookmarkByMemberId != 0) {
			return ResultData.from("F-2", "책갈피 할 수 없습니다", "checkBookmarkByMemberId", checkBookmarkByMemberId);
		}
		
		return ResultData.from("S-1", "책갈피 할 수 있습니다.");
	}

	public ResultData addBookmark(int actorId, int relId) {
		reactionRepository.addBookmark(actorId, relId);
		articleService.increaseBookmarkPoint(relId);
		
		return ResultData.from("S-1", "책갈피 처리 완료");
	}

	public ResultData deleteBookmark(int actorId, int relId) {
		reactionRepository.deleteBookmark(actorId, relId);
		articleService.decreaseBookmarkPoint(relId);
		
		return ResultData.from("S-1", "책갈피 취소 처리 완료");
	}

	public ResultData actorCanMakeReport(int actorId, int relId) {
		if(actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요.");
		}
		
		int checkReportByMemberId = reactionRepository.checkReportByMemberId(actorId, relId);

		if (checkReportByMemberId != 0) {
			return ResultData.from("F-2", "신고할 수 없습니다", "checkReportByMemberId", checkReportByMemberId);
		}
		
		return ResultData.from("S-1", "신고할 수 있습니다.");
	}

	public ResultData doReport(int relId, int reportedMemberId, int reportingMemberId, int reason) {
		reactionRepository.doReport(relId, reportedMemberId, reportingMemberId, reason);
		
		return ResultData.from("S-1", "신고 처리 완료");
	}

}
