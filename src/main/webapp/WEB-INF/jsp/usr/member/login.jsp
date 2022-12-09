<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <c:set var="pageTitle" value="LOGIN" /> --%>
<%@ include file="../common/head.jspf" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title lh-50px mb-20px">로그인</h1>
		<div class="white-board mt-10 dropShadow-black">
			<form class="table-box-type-1" method="POST" action="../member/doLogin" id="loginForm">
				<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
				<table class="formTable">
					<colgroup>
						<col width="500" />
					</colgroup>
					
					<tbody>
						<tr>
							<th>아이디</th>
							<td>
								<input class="w-96 textInput" type="text" name="loginId" placeholder="ID" />
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td>
								<input class="w-96 textInput" type="password" name="loginPw" placeholder="PASSWORD" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<button class="btn btn-active myGray-bgc" type="submit" form="loginForm">로그인</button>
			</div>
		</div>
			
		<div class="btns-wrap flex justify-center">
			<div class="under-btns">
				<a href="${rq.findLoginIdUri }" class="hover:underline text-lg" type="submit">아이디 찾기</a>
				<a href="${rq.findLoginPwUri }" class="hover:underline text-lg" type="submit">비밀번호 찾기</a>
				<a href="../member/join" class="hover:underline text-lg" type="submit">회원가입</a>
			</div>
		</div>
		
<!-- 		<div class="container mx-auto btns"> -->
<!-- 			<button class="btn-text-link btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
<!-- 		</div> -->
	
	</div>
	
	
</section>
	
<%@ include file="../common/foot.jspf" %>