package com.cya.poeming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.poeming.repository.ReplyRepository;
import com.cya.poeming.util.Ut;
import com.cya.poeming.vo.Member;
import com.cya.poeming.vo.Reply;
import com.cya.poeming.vo.ResultData;

@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private ReactionService reactionService;

	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeArticle(actorId, relTypeCode, relId, body);
		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", "댓글이 등록되었습니다", "id", id);
	}

	public List<Reply> getForPrintReplies(Member actor, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(relTypeCode, relId);
		
		for (Reply reply : replies) {
			updateForPrintData(actor, reply);
		}
		
		return replies;
	}

	private void updateForPrintData(Member actor, Reply reply) {
		if(actor == null) {
			return;
		}
		
		ResultData actorCanDeleteRd = actorCanDelete(actor, reply);
		reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actor, reply);
		reply.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
		
		// 댓글 리액션 관련
		ResultData actorCanMakeReplyReactionRd = reactionService.actorCanMakeReaction(actor.getId(), "reply", reply.getId());
		reply.setExtra__actorCanMakeReplyReaction(actorCanMakeReplyReactionRd.isSuccess());
		
		if(actor.getId() == reply.getMemberId()) {
			reply.setExtra__actorCanMakeReplyReaction(false);
			
		}
		
		if (actorCanMakeReplyReactionRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReplyReactionRd.getData1();
			
			if (sumReactionPointByMemberId > 0) {
				reply.setExtra__actorCanCancelGoodReplyReaction(true);
			} else {
				reply.setExtra__actorCanCancelBadReplyReaction(true);
			}
			
		}
	}

	public ResultData actorCanDelete(Member actor, Reply reply) {
		if(reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
		}
		
		if(reply.getMemberId() != actor.getId()) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "삭제 가능");
	}

	public ResultData actorCanModify(Member actor, Reply reply) {
		if(reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
		}
		
		if(reply.getMemberId() != actor.getId()) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "수정 가능");
	}

	public int getRepliesCount(int id) {
		return replyRepository.getRepliesCount("article", id);
	}

	public Reply getForPrintReply(Member actor, int id) {
		Reply reply = replyRepository.getForPrintReply(id);
		
		updateForPrintData(actor, reply);
		
		return reply;
	}

	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);
		
		return ResultData.from("S-1", "댓글을 삭제했습니다");
	}

	public ResultData modifyReply(int id, String body) {
		replyRepository.modifyReply(id, body);
		
		return ResultData.from("S-1", "댓글을 수정했습니다");
	}

	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRowsCount = replyRepository.increaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "좋아요 증가", "affectedRowsCount", affectedRowsCount);
	}
	
	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRowsCount = replyRepository.increaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "별로예요 증가", "affectedRowsCount", affectedRowsCount);
	}
	
	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRowsCount = replyRepository.decreaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "좋아요 감소", "affectedRowsCount", affectedRowsCount);
		
	}

	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRowsCount = replyRepository.decreaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "별로예요 감소", "affectedRowsCount", affectedRowsCount);
		
	}
}
