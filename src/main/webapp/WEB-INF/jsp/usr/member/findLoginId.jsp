<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <c:set var="pageTitle" value="Find LoginId" /> --%>
<%@ include file="../common/head.jspf"%>

<script>
	let MemberFindLoginId__submitDone = false;
	function MemberFindLoginId__submit(form) {
		if (MemberFindLoginId__submitDone) {
			alert('처리중입니다');
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요');
			form.name.focus();
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();
			return;
		}
		MemberFindLoginId__submitDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title lh-50px mb-20px">아이디 찾기</h1>
		<div class="white-board mt-10 dropShadow-black">
			<form class="table-box-type-1" method="POST" action="../member/doFindLoginId"
				onsubmit="MemberFindLoginId__submit(this) ; return false;" id="findLoginForm">
				<input type="hidden" name="afterFindLoginIdUri" value="${param.afterFindLoginIdUri}" />
				<table>
					<colgroup>
						<col width="500" />
					</colgroup>
	
					<tbody>
						<tr class="lh-50px">
							<th class="text-right-must pr-5">이름</th>
							<td class="text-left-must pl-5">
								<input class="w-96 textInput" name="name" type="text" placeholder="NAME" />
							</td>
						</tr>
						<tr class="lh-50px">
							<th class="text-right-must pr-5">이메일</th>
							<td class="text-left-must pl-5">
								<input class="w-96 textInput" name="email" type="text" placeholder="E-MAIL" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<button class="btn btn-active myGray-bgc" type="submit" form="findLoginForm">아이디 찾기</button>
			</div>
		</div>
			
		<div class="btns-wrap flex justify-center">
			<div class="under-btns">
				<a href="/usr/member/login" class="hover:underline text-lg" type="submit">로그인</a>
				<a href="/usr/member/findLoginPw" class="hover:underline text-lg two-btns-last" type="submit">비밀번호 찾기</a>
			</div>
		</div>
	</div>

<!-- 	<div class="container mx-auto btns"> -->
<!-- 		<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
<!-- 	</div> -->

</section>
<%@ include file="../common/foot.jspf"%>