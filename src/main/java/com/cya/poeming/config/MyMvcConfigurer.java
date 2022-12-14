package com.cya.poeming.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cya.poeming.interceptor.BeforeActionInterceptor;
import com.cya.poeming.interceptor.NeedAdminInterceptor;
import com.cya.poeming.interceptor.NeedLoginInterceptor;
import com.cya.poeming.interceptor.NeedLogoutInterceptor;

@Configuration
public class MyMvcConfigurer implements WebMvcConfigurer {
	
	// BeforeActionInterceptor 불러오기
		@Autowired
		BeforeActionInterceptor beforeActionInterceptor;
		// NeedLoginInterceptor 불러오기
		@Autowired
		NeedLoginInterceptor needLoginInterceptor;
		// NeedLogoutInterceptor 불러오기
		@Autowired
		NeedLogoutInterceptor needLogoutInterceptor;
		// NeedLogoutInterceptor 불러오기
		@Autowired
		NeedAdminInterceptor needAdminInterceptor;
		
		// 인터셉터 적용
		public void addInterceptors(InterceptorRegistry registry) {
			
			InterceptorRegistration ir;
			
			ir = registry.addInterceptor(beforeActionInterceptor);
			ir.addPathPatterns("/**");
			ir.addPathPatterns("/favicon.ico");
			ir.excludePathPatterns("/resource/**");
			ir.excludePathPatterns("/error");
			
			ir = registry.addInterceptor(needLoginInterceptor);
			ir.addPathPatterns("/usr/member/doLogout");
			ir.addPathPatterns("/usr/member/myPage");
			ir.addPathPatterns("/usr/member/checkPassword");
			ir.addPathPatterns("/usr/member/doCheckPassword");
			ir.addPathPatterns("/usr/member/modify");
			ir.addPathPatterns("/usr/member/doModify");
			ir.addPathPatterns("/usr/member/doWithdraw");
			ir.addPathPatterns("/usr/reply/doWrite");
			ir.addPathPatterns("/usr/reply/doDelete");
			ir.addPathPatterns("/usr/reply/modify");
			ir.addPathPatterns("/usr/reply/doModify");
			ir.addPathPatterns("/usr/article/write");
			ir.addPathPatterns("/usr/article/doWrite");
			ir.addPathPatterns("/usr/article/modify");
			ir.addPathPatterns("/usr/article/doModify");
			ir.addPathPatterns("/usr/article/doDelete");
			ir.addPathPatterns("/usr/article/myArticles");
			ir.addPathPatterns("/usr/article/myBookmarks");
			ir.addPathPatterns("/usr/reaction/doGoodReaction");
			ir.addPathPatterns("/usr/reaction/doBadReaction");
			ir.addPathPatterns("/usr/reaction/doCancelGoodReaction");
			ir.addPathPatterns("/usr/reaction/doCancelBadReaction");
			ir.addPathPatterns("/usr/reaction/doBookmark");
			ir.addPathPatterns("/usr/reaction/doCancelBookmark");
			ir.addPathPatterns("/usr/reaction/doReport");
			ir.addPathPatterns("/adm/**");
			ir.addPathPatterns("/adm/member/login");
			ir.addPathPatterns("/adm/member/doLogin");
			ir.addPathPatterns("/adm/member/findLoginId");
			ir.addPathPatterns("/adm/member/doFindLoginId");
			ir.addPathPatterns("/adm/member/findLoginPw");
			ir.addPathPatterns("/adm/member/doFindLoginPw");
			
			ir = registry.addInterceptor(needLogoutInterceptor);
			ir.addPathPatterns("/usr/member/login");
			ir.addPathPatterns("/usr/member/doLogin");
			ir.addPathPatterns("/usr/member/getLoginIdDup");
			ir.addPathPatterns("/usr/member/join");
			ir.addPathPatterns("/usr/member/doJoin");
			ir.addPathPatterns("/usr/member/findLoginId");
			ir.addPathPatterns("/usr/member/doFindLoginId");
			ir.addPathPatterns("/usr/member/findLoginPw");
			ir.addPathPatterns("/usr/member/doFindLoginPw");
			
			ir = registry.addInterceptor(needAdminInterceptor);
			ir.addPathPatterns("/adm/**");
			ir.addPathPatterns("/adm/member/login");
			ir.addPathPatterns("/adm/member/doLogin");
			ir.addPathPatterns("/adm/member/findLoginId");
			ir.addPathPatterns("/adm/member/doFindLoginId");
			ir.addPathPatterns("/adm/member/findLoginPw");
			ir.addPathPatterns("/adm/member/doFindLoginPw");
		}
}
