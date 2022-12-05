package com.cya.poeming.repository;

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

}
