package com.cya.poeming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.poeming.repository.ArticleRepository;
import com.cya.poeming.util.Ut;
import com.cya.poeming.vo.Article;
import com.cya.poeming.vo.Member;
import com.cya.poeming.vo.ResultData;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
		
	private void updateForPrintData(int actorId, Article article) {
		if(article == null) {
			return;
		}
		
		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
	}

	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);
		
		updateForPrintData(actorId, article);
		
		return article;
	}

	public List<Article> getForPrintArticles(int actorId, int boardId, int itemsInAPage, int page, String searchKeywordTypeCode, String searchKeyword) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		List<Article> articles = articleRepository.getForPrintArticles(boardId, limitStart, limitTake, searchKeywordTypeCode, searchKeyword);
		
		for (Article article : articles) {
			updateForPrintData(actorId, article);
		}
		
		return articles;
	}
	
	public ResultData<Integer> writeArticle(int boardId, String title, String body, int loginedMemberId) {
		articleRepository.writeArticle(boardId, title, body, loginedMemberId);
		
		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다", id), "id", id);
	}
	
	public void deleteArticle(int id) {
		
		articleRepository.deleteArticle(id);
	}
	
	public ResultData<Article> modifyArticle(int id, String title, String body) {
		
		articleRepository.modifyArticle(id, title, body);
		
		Article article = getForPrintArticle(0, id);
		
		return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다.", id), "article", article);
	}

	public ResultData actorCanModify(int loginedMemberId, Article article) {
		
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "수정 가능");
	}
	
	public ResultData actorCanDelete(int actorId, Article article) {
		
		if(article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
		}
		
		if(article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "삭제 가능");
	}

	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public ResultData<Integer> increaseHitCount(int id) {
		int affectedRowsCount = articleRepository.increaseHitCount(id);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "조회수 증가", "affectedRowsCount", affectedRowsCount);
	}

	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}

	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.increaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "좋아요 증가", "affectedRowsCount", affectedRowsCount);
	}

	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.increaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "별로예요 증가", "affectedRowsCount", affectedRowsCount);
	}

	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.decreaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "좋아요 감소", "affectedRowsCount", affectedRowsCount);
		
	}

	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRowsCount = articleRepository.decreaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "별로예요 감소", "affectedRowsCount", affectedRowsCount);
		
	}
	
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public ResultData increaseBookmarkPoint(int relId) {
		int affectedRowsCount = articleRepository.increaseBookmarkPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "책갈피 증가", "affectedRowsCount", affectedRowsCount);
	}

	public ResultData decreaseBookmarkPoint(int relId) {
		int affectedRowsCount = articleRepository.decreaseBookmarkPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "책갈피 감소", "affectedRowsCount", affectedRowsCount);
	}

	public List<Article> getForPrintArticlesByMemberId(int memberId, int itemsInAPage, int page) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		List<Article> articles = articleRepository.getForPrintArticlesByMemberId(memberId, limitStart, limitTake);
		
		return articles;
	}

	public int getArticlesCountByMemberId(int memberId) {
		return articleRepository.getArticlesCountByMemberId(memberId);
	}

	public List<Article> getForPrintBookmarkedArticlesByMemberId(int memberId, int itemsInAPage, int page) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		List<Article> articles = articleRepository.getForPrintBookmarkedArticlesByMemberId(memberId, limitStart, limitTake);
		
		return articles;
	}

	public int getBookmarkedArticlesCountByMemberId(int memberId) {
		return articleRepository.getBookmarkedArticlesCountByMemberId(memberId);
	}

	public List<Article> getLimitedArticlesByBoardId(int boardId, int count) {
		return articleRepository.getLimitedArticlesByBoardId(boardId, count);
	}

	public int getReportedArticlesCount() {
		return articleRepository.getReportedArticlesCount();
	}

	public List<Article> getForPrintReportedMembers(int itemsInAPage, int page) {
		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		return articleRepository.getForPrintReportedMembers(limitStart, limitTake);
	}

	public void deleteArticles(List<Integer> articleIds) {
		for (int articleId : articleIds) {
			Article article = articleRepository.getForPrintArticle(articleId);

			if (article != null) {
				articleRepository.deleteArticle(article.getId());
			}
		}
	}
}
