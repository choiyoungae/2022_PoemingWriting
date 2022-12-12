package com.cya.poeming.vo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.cya.poeming.service.MemberService;
import com.cya.poeming.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isAjax;
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		
		paramMap = Ut.getParamMap(req);

		this.session = req.getSession();

		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		
		String requestUri = req.getRequestURI();

		boolean isAjax = requestUri.endsWith("Ajax");

		if (isAjax == false) {
			if (paramMap.containsKey("ajax") && paramMap.get("ajax").equals("Y")) {
				isAjax = true;
			} else if (paramMap.containsKey("isAjax") && paramMap.get("isAjax").equals("Y")) {
				isAjax = true;
			}
		}
		if (isAjax == false) {
			if (requestUri.contains("/get")) {
				isAjax = true;
			}
		}
		this.isAjax = isAjax;
	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack(msg));
	}
	
	public void printReplaceJs(String msg, String uri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, uri));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

	public boolean isNotLogined() {
		return !isLogined;
	}

	public String jsHistoryBack(String resultCode, String msg) {
		msg = String.format("[%s] %s", resultCode, msg);
		return Ut.jsHistoryBack(msg);
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}

	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		return currentUri;
	}

	public String getEncodedCurrentUri() {

		return Ut.getUriEncoded(getCurrentUri());
	}

	public String getAfterLoginUri() {
		String requestUri = req.getRequestURI();
		
		// 로그인 후 다시 돌아가면 안되는 URL
		switch(requestUri) {
		case "/usr/member/login" :
		case "/usr/member/join" :
		case "/usr/member/findLoginId" :
		case "/usr/member/findLoginPw" :
			return Ut.getUriEncoded(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}
		
		return getEncodedCurrentUri();
	}
	
	public String getJoinUri() {
		return "/usr/member/join?afterLoginUri=" + getAfterLoginUri();
	}

	public String getLoginUri() {
		return "/usr/member/login?afterLoginUri=" + getAfterLoginUri();
	}
	
	public String getLogoutUri() {
		String requestUri = req.getRequestURI();
		
		System.err.println(requestUri);
//		switch (requestUri) {
//		case "/usr/article/write":
//		case "/usr/article/modify":
//			return "../member/doLogout?afterLogoutUri=" + "/";
//		}

		return "/usr/member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}

	public String getAfterLogoutUri() {
		String requestUri = req.getRequestURI();
		// 로그아웃 후 다시 돌아가면 안되는 URL
		switch (requestUri) {
		case "/adm/home/main":
		case "/adm/member/list":
		case "/adm/member/doDeleteMembers":
			return Ut.getUriEncoded(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}
		
		return getEncodedCurrentUri();
	}
	
	public String getArticleDetailUriFromArticleList(Article article) {
		return "/usr/article/detail?id=" + article.getId() + "&listUri=" + getEncodedCurrentUri();
	}
	
	public String getFindLoginIdUri() {
		return "/usr/member/findLoginId?afterFindLoginIdUri=" + getAfterFindLoginIdUri();
	}

	public String getFindLoginPwUri() {
		return "/usr/member/findLoginPw?afterFindLoginPwUri=" + getAfterFindLoginPwUri();
	}

	public String getAfterFindLoginIdUri() {
		return getEncodedCurrentUri();
	}

	public String getAfterFindLoginPwUri() {
		return getEncodedCurrentUri();
	}
	
	public String getTodayDate() {
		Date nowDate = new Date();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd"); 
    	// 원하는 데이터 포맷 지정
		String strNowDate = simpleDateFormat.format(nowDate); 
    	// 지정한 포맷으로 변환 
		return strNowDate;
	}
	
	public boolean isAdmin() {
		if (isLogined == false) {
			return false;
		}

		return loginedMember.isAdmin();
	}
}
