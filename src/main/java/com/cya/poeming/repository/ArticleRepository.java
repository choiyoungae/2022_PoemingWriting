package com.cya.poeming.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cya.poeming.vo.Article;

@Mapper
public interface ArticleRepository {
	
	@Insert("""
			<script>
				INSERT INTO article
				SET regDate = NOW(),
				updateDate = NOW(),
				title = #{title},
				`body` = #{body},
				memberId = #{loginedMemberId},
				boardId = #{boardId}
			</script>
			""")
	public void writeArticle(int boardId, String title, String body, int loginedMemberId);
	
	@Select("""
			<script>
				SELECT A.*, M.nickname AS extra__writerName
				FROM article AS A 
				LEFT JOIN `member` AS M
				ON A.memberId = M.id
				WHERE A.id = #{id}
			</script>
			""")
	public Article getForPrintArticle(int id);

	@Select("""
			<script>
				SELECT A.*, M.nickname AS extra__writerName
				FROM article AS A
				LEFT JOIN `member` AS M
				ON A.memberId = M.id WHERE 1
				<if test="boardId != 0">
					AND A.boardId = #{boardId}
				</if>
				<if test="searchKeyword != ''">
					<choose>
						<when test="searchKeywordTypeCode == 'title'">
							AND A.title LIKE CONCAT('%', #{searchKeyword }, '%')
						</when>
						<when test="searchKeywordTypeCode == 'body'">
							AND A.body LIKE CONCAT('%', #{searchKeyword }, '%')
						</when>
						<otherwise>
							AND (
								A.title LIKE CONCAT('%', #{searchKeyword }, '%')
								OR A.body LIKE CONCAT('%', #{searchKeyword }, '%')
							)
						</otherwise>
					</choose>
				</if>
				ORDER BY A.id DESC
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
			</script>
			""")
	public List<Article> getForPrintArticles(int boardId, int limitStart, int limitTake, String searchKeywordTypeCode, String searchKeyword);

	@Delete("""
			<script>
				DELETE FROM article
				WHERE id = #{id}
			</script>
			""")
	public void deleteArticle(int id);

	@Update("""
			<script>
				UPDATE article
				<set>
					<if test="title != null and title != ''">title = #{title},</if>
					<if test="body != null and body != ''">`body` = #{body},</if>
					updateDate = NOW()
				</set>
				WHERE id = #{id}
			</script>
			""")
	public void modifyArticle(int id, String title, String body);

	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")
	public int getLastInsertId();

	@Select("""
			<script>
				SELECT COUNT(*) AS cnt
				FROM article AS A
				WHERE 1
				<if test="boardId != 0">
					AND A.boardId = #{boardId}
				</if>
				<if test="searchKeyword != ''">
					<choose>
						<when test="searchKeywordTypeCode == 'title'">
							AND A.title LIKE CONCAT('%', #{searchKeyword }, '%')
						</when>
						<when test="searchKeywordTypeCode == 'body'">
							AND A.body LIKE CONCAT('%', #{searchKeyword }, '%')
						</when>
						<otherwise>
							AND (
								A.title LIKE CONCAT('%', #{searchKeyword }, '%')
								OR A.body LIKE CONCAT('%', #{searchKeyword }, '%')
							)
						</otherwise>
					</choose>
				</if>
			</script>
			""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			<script>
				UPDATE article
				<set>
					hitCount = hitCount + 1
				</set>
				WHERE id = #{id}
			</script>
			""")
	public int increaseHitCount(int id);

	@Select("""
			<script>
				SELECT hitCount
				FROM article
				WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseGoodReactionPoint(int relId);
	
	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseBadReactionPoint(int relId);

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseGoodReactionPoint(int relId);

	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseBadReactionPoint(int relId);
	
	@Select("""
			<script>
				SELECT *
				FROM article
				WHERE id = #{id}
			</script>
						""")
	public Article getArticle(int id);

	@Update("""
			<script>
				UPDATE article
				SET bookmark = bookmark + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseBookmarkPoint(int relId);
	
	@Update("""
			<script>
				UPDATE article
				SET bookmark = bookmark - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseBookmarkPoint(int relId);
	
	@Select("""
			<script>
				SELECT A.*, B.name AS extra__boardName
				FROM article AS A 
				LEFT JOIN board AS B
				ON A.boardId = B.id
				WHERE A.memberId = #{memberId};
			</script>
			""")
	public List<Article> getForPrintArticlesByMemberId(int memberId);

	@Select("""
			<script>
				SELECT COUNT(*)
				FROM article
				WHERE memberId = #{memberId}
				ORDER BY id DESC;
			</script>
			""")
	public int getArticlesCountByMemberId(int memberId);

}
