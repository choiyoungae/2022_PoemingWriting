package com.cya.poeming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cya.poeming.repository.MemberRepository;
import com.cya.poeming.util.Ut;
import com.cya.poeming.vo.Member;
import com.cya.poeming.vo.ResultData;

@Service
public class MemberService {
	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	@Value("${custom.siteName}")
	private String siteName;

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private AttrService attrService;
	@Autowired
	private MailService mailService;

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		// 로그인 아이디 중복 체크
		if(memberRepository.isLoginIdDup(loginId) != 0) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}
		
		// 이름 + 이메일 중복 체크
		if(memberRepository.isNameAndEmailDup(name, email) != 0) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}
		
		loginPw = Ut.sha256(loginPw);
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다", "id", id);
	}

	public Member getMemberById(int id) {
		
		return memberRepository.getMemberById(id);
	}
	
	public Member getMemberByLoginId(String loginId) {
		
		return memberRepository.getMemberByLoginId(loginId);
	}

	public ResultData modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		loginPw = Ut.sha256(loginPw);
		memberRepository.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		
		return ResultData.from("S-1", "회원정보가 수정되었습니다");
	}
	
	public String genMemberModifyAuthKey(int actorId) {
		String memberModifyAuthKey = Ut.getTempPassword(10);

		attrService.setValue("member", actorId, "extra", "memberModifyAuthKey", memberModifyAuthKey,
				Ut.getDateStrLater(60 * 5));

		return memberModifyAuthKey;
	}

	public ResultData checkMemberModifyAuthKey(int actorId, String memberModifyAuthKey) {
		String saved = attrService.getValue("member", actorId, "extra", "memberModifyAuthKey");
		
		if(!saved.equals(memberModifyAuthKey)) {
			return ResultData.from("F-1", "일치하지 않거나 만료된 코드입니다.");
		}
		
		return ResultData.from("S-1", "정상 코드입니다.");
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
	
	public ResultData notifyTempLoginPwByEmailRd(Member actor) {
		String title = "[" + siteName + "] 임시 패스워드입니다.";
		String tempPassword = Ut.getTempPassword(6);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteMainUri + "/usr/member/login\" target=\"_blank\">로그인 하러가기</a>";

		ResultData sendResultData = mailService.send(actor.getEmail(), title, body);

		if (sendResultData.isFail()) {
			return sendResultData;
		}

		setTempPassword(actor, tempPassword);

		return ResultData.from("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다.");
	}

	private void setTempPassword(Member actor, String tempPassword) {
		memberRepository.modifyMember(actor.getId(), Ut.sha256(tempPassword), null, null, null, null);
	}

	public void withdrawMember(int actorId) {
		memberRepository.withdrawMember(actorId);
	}
}
