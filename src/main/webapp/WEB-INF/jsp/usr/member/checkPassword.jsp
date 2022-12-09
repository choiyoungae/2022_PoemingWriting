<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <c:set var="pageTitle" value="checkPassword" /> --%>
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title lh-50px mb-20px">비밀번호 확인</h1>
		<div class="white-board mt-10 dropShadow-black">
			<form class="table-box-type-1" method="POST" action="../member/doCheckPassword" id="checkPasswordForm">
				<input type="hidden" name="replaceUri" value="${param.replaceUri }" />
				<table class="formTable">
					<colgroup>
						<col width="500" />
					</colgroup>
	
					<tbody>
						<tr>
							<th>아이디</th>
							<td>${rq.loginedMember.loginId }</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td>
								<input name="loginPw" required="required" class="w-96 textInput"
									type="password" placeholder="비밀번호를 입력해주세요"
								/>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<button class="btn btn-active myGray-bgc" type="submit" form="checkPasswordForm">확인</button>
			</div>
		</div>
	</div>

<!-- 	<div class="container mx-auto btns"> -->
<!-- 		<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
<!-- 	</div> -->

</section>

<%@ include file="../common/foot.jspf"%>