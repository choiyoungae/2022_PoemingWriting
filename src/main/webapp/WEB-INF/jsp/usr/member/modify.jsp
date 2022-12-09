<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <c:set var="pageTitle" value="MEMBER MODIFY" /> --%>
<%@ include file="../common/head.jspf" %>

<script>
let MemberModify__submitDone = false;
function MemberModify__submit(form) {
	if (MemberModify__submitDone) {
		alert('처리중입니다');
		return;
	}
	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPwConfirm.value.length > 0) {
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('비밀번호 확인을 입력해주세요');
			form.loginPwConfirm.focus();
			return;
		}
		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('비밀번호가 일치하지 않습니다');
			form.loginPw.focus();
			return;
		}
	}
	form.name.value = form.name.value.trim();
	if (form.name.value.length == 0) {
		alert('이름을 입력해주세요');
		form.name.focus();
		return;
	}
	form.nickname.value = form.nickname.value.trim();
	if (form.nickname.value.length == 0) {
		alert('닉네임을 입력해주세요');
		form.nickname.focus();
		return;
	}
	form.cellphoneNum.value = form.cellphoneNum.value.trim();
	if (form.cellphoneNum.value.length == 0) {
		alert('전화번호를 입력해주세요');
		form.cellphoneNum.focus();
		return;
	}
	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		alert('이메일을 입력해주세요');
		form.email.focus();
		return;
	}
	MemberModify__submitDone = true;
	form.submit();
}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title lh-50px mb-20px">내 정보 수정</h1>
		<div class="white-board mt-10 dropShadow-black">
			<form class="table-box-type-1" method="POST" action="../member/doModify"
				onsubmit="MemberModify__submit(this); return false;" id="memberModifyForm">
				<input type="hidden" name="memberModifyAuthKey" value="${param.memberModifyAuthKey }" />
				<table class="tableForm">
					<colgroup>
						<col width="500" />
					</colgroup>
	
					<tbody>
						<tr>
							<th>가입일</th>
							<td>${rq.loginedMember.regDate }</td>
						</tr>
						<tr>
							<th>아이디</th>
							<td>${rq.loginedMember.loginId }</td>
						</tr>
						<tr>
							<th>새 비밀번호</th>
							<td>
								<input name="loginPw" class="w-96 textInput" placeholder="NEW PASSWORD" />
							</td>
						</tr>
						<tr>
							<th>새 비밀번호 확인</th>
							<td>
								<input name="loginPwConfirm" class="w-96 textInput" placeholder="NEW PASSWORD CONFIRM" />
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>
								<input value="${rq.loginedMember.name }" name="name" class="w-96 textInput"
									placeholder="NAME"
								/>
							</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>
								<input value="${rq.loginedMember.nickname }" name="nickname" class="w-96 textInput"
									placeholder="NICKNAME"
								/>
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input value="${rq.loginedMember.cellphoneNum }" name="cellphoneNum" class="w-96 textInput"
									placeholder="PHONE NUMBER" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input value="${rq.loginedMember.email }" name="email" class="w-96 textInput"
									placeholder="E-MAIL" />
							</td>
						</tr>
					</tbody>
	
				</table>
			</form>
		</div>
		
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<button class="btn btn-active myGray-bgc" type="submit" form="memberModifyForm">수정</button>
			</div>
		</div>
		
<!-- 		<div class="btns"> -->
<!-- 			<button class="btn-text-link btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
<!-- 		</div> -->
	
	</div>
	
</section>

<%@ include file="../common/foot.jspf" %>