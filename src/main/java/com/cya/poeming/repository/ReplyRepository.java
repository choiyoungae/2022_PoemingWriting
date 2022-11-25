package com.cya.poeming.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cya.poeming.vo.Reply;

@Mapper
public interface ReplyRepository {
	
	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`body` = #{body};	
			</script>
			""")
	void writeArticle(int actorId, String relTypeCode, int relId, String body);
	
	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")

	int getLastInsertId();

	@Select("""
			<script>
				SELECT R.*, M.nickname AS extra__writerName
				FROM reply AS R
				LEFT JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relId = #{relId}
				AND R.relTypeCode = #{relTypeCode}
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
			</script>
			""")
	List<Reply> getForPrintReplies(String relTypeCode, int relId, int limitStart, int limitTake);

	@Select("""
			<script>
				SELECT COUNT(*)
				FROM reply
				WHERE relId = #{relId}
				AND relTypeCode = #{relTypeCode}
			</script>
			""")
	int getRepliesCount(String relTypeCode, int relId);

	@Select("""
			<script>
				SELECT R.*, M.nickname AS extra__writerName
				FROM reply AS R 
				LEFT JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.id = #{id}
			</script>
			""")
	Reply getForPrintReply(int id);

	@Delete("""
			<script>
				DELETE FROM reply
				WHERE id = #{id}
			</script>
			""")
	void deleteReply(int id);

	@Update("""
			UPDATE reply
			SET updateDate = NOW(),
			`body` = #{body}
			WHERE id = #{id}
			""")
	void modifyReply(int id, String body);

	@Update("""
			<script>
				UPDATE reply
				SET goodReactionPoint = goodReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	int increaseGoodReactionPoint(int relId);
	
	@Update("""
			<script>
				UPDATE reply
				SET badReactionPoint = badReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseBadReactionPoint(int relId);

	@Update("""
			<script>
				UPDATE reply
				SET goodReactionPoint = goodReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseGoodReactionPoint(int relId);

	@Update("""
			<script>
				UPDATE reply
				SET badReactionPoint = badReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseBadReactionPoint(int relId);
}
