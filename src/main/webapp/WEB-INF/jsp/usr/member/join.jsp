<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <c:set var="pageTitle" value="JOIN" /> --%>
<%@ include file="../common/head.jspf"%>

<!-- lodash debounce -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>

<script>
	let submitJoinFormDone = false;
	let validLoginId = "";
	
	function submitJoinForm(form) {
		if (submitJoinFormDone) {
			alert('처리중입니다');
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value == 0) {
			alert('아이디를 입력해주세요');
			form.loginId.focus();
			return;
		}
		if (form.loginId.value != validLoginId) {
			alert('사용할 수 없는 아이디입니다!');
			form.loginId.focus();
			return;
		}
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value == 0) {
			alert('비밀번호를 입력해주세요');
			form.loginPw.focus();
			return;
		}
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value == 0) {
			alert('비밀번호 확인을 입력해주세요');
			form.loginPwConfirm.focus();
			return;
		}
		if (form.loginPwConfirm.value != form.loginPw.value) {
			alert('비밀번호가 일치하지 않습니다');
			form.loginPw.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value == 0) {
			alert('이름을 입력해주세요');
			form.name.focus();
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value == 0) {
			alert('닉네임을 입력해주세요');
			form.nickname.focus();
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value == 0) {
			alert('이메일을 입력해주세요');
			form.email.focus();
			return;
		}
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value == 0) {
			alert('전화번호를 입력해주세요');
			form.cellphoneNum.focus();
			return;
		}
		submitJoinFormDone = true;
		form.submit();
	}
	
	function checkLoginIdDup(el) {
		const form = $(el).closest('form').get(0);
		
		if (form.loginId.value.length == 0) {
			validLoginId = '';
			return;
		}
		if (validLoginId == form.loginId.value){
			return;
		}
		
		$('.loginId-msg').removeClass('myGreen');
		$('.loginId-msg').removeClass('myPink');
		$('.loginId-msg').html('<div>확인중...</div>');
		
		$.get('../member/getLoginIdDup', {
			isAjax : 'Y',
			loginId : form.loginId.value
		}, function(data) {
			if(data.resultCode == 'S-1') {
				$('.loginId-msg').addClass('myGreen');
			}
			if(data.resultCode == 'F-A2') {				
				$('.loginId-msg').addClass('myPink');
			}
			$('.loginId-msg').html('<div>' + data.msg + '</div>');
			if (data.success) {
				validLoginId = data.data1;
			} else {
				validLoginId = '';
			}
			if (data.resultCode == 'F-B'){
				alert(data.msg);
				location.replace('/');
			}
		}, 'json');
	}
	
	function checkLoginPwConfirmed(el) {
		const form = $(el).closest('form').get(0);
		
		if (form.loginPw.value.length == 0 && form.loginPwConfirm.value.length == 0) {
			return;
		}
		
		if(form.loginPw.value != form.loginPwConfirm.value) {
			$('.loginPwConfirm-msg').html('<div class="myPink">불일치합니다.</div>');
		} else {
			$('.loginPwConfirm-msg').html('<div class="myGreen">일치합니다.</div>');
		}
	}
	
	const checkLoginIdDupDebounced = _.debounce(checkLoginIdDup,300); 
	const checkLoginPwConfirmedDebounced = _.debounce(checkLoginPwConfirmed, 300);
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title lh-50px mb-20px">회원가입</h1>
		<div class="white-board mt-10 dropShadow-black">
			<form class="table-box-type-1" id="joinForm" name="joinForm" method="POST" action="../member/doJoin" onsubmit="submitJoinForm(this); return false;">
				<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
				<table class="tableForm">
					<colgroup>
						<col width="500" />
					</colgroup>
	
					<tbody>
						<tr>
							<th>아이디</th>
							<td>
								<input name="loginId" class="w-96 textInput" placeholder="ID"
								onkeyup="checkLoginIdDupDebounced(this);" autocomplete="off" />
								<div class="loginId-msg"></div>
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td>
								<input name="loginPw" type="password" class="w-96 textInput" placeholder="PASSWORD" />
							</td>
						</tr>
						<tr>
							<th>비밀번호 확인</th>
							<td>
								<input name="loginPwConfirm" class="w-96 textInput" placeholder="PASSWORD CONFIRM"
								onkeyup="checkLoginPwConfirmedDebounced(this);" type="password" autocomplete="off" />
								<div class="loginPwConfirm-msg"></div>
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>
								<input name="name" class="w-96 textInput" placeholder="NAME" />
							</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>
								<input name="nickname" class="w-96 textInput" placeholder="NICKNAME" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input name="cellphoneNum" class="w-96 textInput" placeholder="PHONE NUMBER" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input name="email" class="w-96 textInput" placeholder="E-MAIL" />
							</td>
						</tr>
					</tbody>
	
				</table>
			</form>
		</div>
		
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<button class="btn btn-active myGray-bgc" type="submit" form="joinForm">회원가입</button>
			</div>
		</div>
	</div>

<!-- 	<div class="container mx-auto btns"> -->
<!-- 		<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
<!-- 	</div> -->

</section>
<%@ include file="../common/foot.jspf"%>