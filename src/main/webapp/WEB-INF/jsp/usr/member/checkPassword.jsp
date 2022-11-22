<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="checkPassword" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="POST" action="../member/doCheckPassword">
			<input type="hidden" name="replaceUri" value="${param.replaceUri }" />
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th>아이디</th>
						<td>${rq.loginedMember.loginId }</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<input name="loginPw" required="required" class="w-full input input-bordered max-w-xs"
								placeholder="비밀번호를 입력해주세요"
							/>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<button class="btn btn-active btn-ghost" type="submit" value="확인">확인</button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>

	<div class="container mx-auto btns">
		<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
	</div>

</section>

<%@ include file="../common/foot.jspf"%>