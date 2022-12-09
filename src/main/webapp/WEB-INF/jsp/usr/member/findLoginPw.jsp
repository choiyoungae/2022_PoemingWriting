<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <c:set var="pageTitle" value="Find LoginPw" /> --%>
<%@ include file="../common/head.jspf"%>

<script>
	let MemberFindLoginPw__submitDone = false;
	function MemberFindLoginPw__submit(form) {
		if (MemberFindLoginPw__submitDone) {
			alert('처리중입니다');
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요');
			form.loginId.focus();
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();
			return;
		}
		MemberFindLoginPw__submitDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title lh-50px mb-20px">비밀번호 찾기</h1>
		<div class="white-board mt-10 dropShadow-black">
			<form class="table-box-type-1" method="POST" action="../member/doFindLoginPw"
				onsubmit="MemberFindLoginPw__submit(this) ; return false;" id="findPasswordForm">
				<input type="hidden" name="afterFindLoginPwUri" value="${param.afterFindLoginPwUri}" />
				<table class="formTable">
					<colgroup>
						<col width="500" />
					</colgroup>
	
					<tbody>
						<tr>
							<th>아이디</th>
							<td>
								<input class="w-96 textInput" name="loginId" type="text" placeholder="ID" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input class="w-96 textInput" name="email" type="text" placeholder="E-MAIL" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<button class="btn btn-active myGray-bgc" type="submit" form="findPasswordForm">비밀번호 찾기</button>
			</div>
		</div>
			
		<div class="btns-wrap flex justify-center">
			<div class="under-btns">
				<a href="/usr/member/login" class="hover:underline text-lg" type="submit">로그인</a>
				<a href="/usr/member/findLoginId" class="hover:underline text-lg two-btns-last" type="submit">아이디 찾기</a>
			</div>
		</div>
	</div>

<!-- 	<div class="container mx-auto btns"> -->
<!-- 		<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
<!-- 	</div> -->

</section>
<%@ include file="../common/foot.jspf"%>