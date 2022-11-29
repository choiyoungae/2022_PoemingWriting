package com.cya.poeming.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionRepository {
	
	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point), 0) AS s
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
			</script>
						""")
	int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);
	
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`point` = 1;
			</script>
			""")
	int addGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`point` = -1;
			</script>
			""")
	int addBadReactionPoint(int actorId, String relTypeCode, int relId);

	@Select("""
			<script>
				SELECT COUNT(*)
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
				AND RP.`point` = 1;
			</script>
						""")
	int getGoodReactionPointByMemberId(int actorId, String relTypeCode, int relId);

	@Select("""
			<script>
				SELECT COUNT(*)
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
				AND RP.`point` = -1;
			</script>
						""")
	int getBadReactionPointByMemberId(int actorId, String relTypeCode, int relId);

	@Delete("""
			<script>
				DELETE FROM reactionPoint
				WHERE relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				AND memberId = #{actorId}
				AND `point` = 1;
			</script>
			""")
	void deleteGoodReactionPoint(int actorId, String relTypeCode, int relId);

	@Delete("""
			<script>
				DELETE FROM reactionPoint
				WHERE relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				AND memberId = #{actorId}
				AND `point` = -1;
			</script>
			""")
	void deleteBadReactionPoint(int actorId, String relTypeCode, int relId);
}
