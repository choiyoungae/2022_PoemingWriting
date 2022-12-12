package com.cya.poeming.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cya.poeming.vo.Member;

@Mapper
public interface MemberRepository {
	
	@Insert("""
			<script>
				INSERT INTO `member`
				SET regDate = NOW(),
				updateDate = NOW(),
				loginId = #{loginId},
				loginPw = #{loginPw},
				`name` = #{name},
				nickname = #{nickname},
				cellphoneNum = #{cellphoneNum},
				email = #{email}
			</script>
			""")
	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")
	public int getLastInsertId();

	@Select("""
			<script>
				SELECT *
				FROM `member`
				WHERE id = #{id}
			</script>
			""")
	public Member getMemberById(int id);
	
	@Select("""
			<script>
				SELECT COUNT(*)
				FROM `member`
				WHERE loginId = #{loginId};
			</script>
			""")
	public int isLoginIdDup(String loginId);

	@Select("""
			<script>
				SELECT COUNT(*)
				FROM `member`
				WHERE `name` = #{name}
				AND email = #{email};
			</script>
			""")
	public int isNameAndEmailDup(String name, String email);

	@Select("""
			<script>
				SELECT *
				FROM `member`
				WHERE loginId = #{loginId}
			</script>
			""")
	public Member getMemberByLoginId(String loginId);

	@Update("""
			<script>
				UPDATE `member`
				<set>	
					updateDate = NOW(),
					<if test="loginPw != null">
						loginPw = #{loginPw},
					</if>
					<if test="name != null">
						name = #{name},
					</if>
					<if test="nickname != null">
						nickname = #{nickname},
					</if>
					<if test="cellphoneNum != null">
						cellphoneNum = #{cellphoneNum},
					</if>
					<if test="email != null">
						email = #{email}
					</if>
				</set>
				WHERE id = #{id};
			</script>
			""")
	public void modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email);

	@Select("""
			<script>
				SELECT *
				FROM `member` AS M
				WHERE M.name = #{name}
				AND M.email = #{email}
			</script>
			""")
	public Member getMemberByNameAndEmail(String name, String email);

	@Update("""
			<script>
				UPDATE `member`
				SET delStatus = 1
				WHERE id = #{actorId};
			</script>			
			""")
	public void withdrawMember(int actorId);
	
	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM `member` AS M
			WHERE delStatus = 0
			<if test="authLevel != 0">
				AND M.authLevel = #{authLevel}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'loginId'">
						AND M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND M.name LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.name LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
							)
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	int getMembersCount(String authLevel, String searchKeywordTypeCode, String searchKeyword);

	@Select("""
			<script>
				SELECT M.*
				FROM `member` AS M
				WHERE delStatus = 0
				<if test="authLevel != 0">
					AND M.authLevel = #{authLevel}
				</if>
				<if test="searchKeyword != ''">
					<choose>
						<when test="searchKeywordTypeCode == 'loginId'">
							AND M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<when test="searchKeywordTypeCode == 'name'">
							AND M.name LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<when test="searchKeywordTypeCode == 'nickname'">
							AND M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<otherwise>
							AND (
								M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
								OR M.name LIKE CONCAT('%', #{searchKeyword}, '%')
								OR M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
								)
						</otherwise>
					</choose>
				</if>
				ORDER BY M.id DESC
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
			</script>
			""")
	List<Member> getForPrintMembers(String authLevel, String searchKeywordTypeCode, String searchKeyword,
			int limitStart, int limitTake);

	@Select("""
			<script>
				UPDATE `member`
				<set>
					updateDate = NOW(),
					delStatus = 1,
					delDate = NOW(),
				</set>
				WHERE id = #{id}
			</script>
			""")
	void deleteMember(int id);
}
