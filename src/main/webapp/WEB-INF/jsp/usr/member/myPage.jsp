<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.cya.poeming.util.Ut"%>

<%-- <c:set var="pageTitle" value="MYPAGE" /> --%>
<%@ include file="../common/head.jspf" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title lh-50px mb-20px">내 정보</h1>
		<div class="white-board mt-10 dropShadow-black">
			<div class="table-box-type-1">
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
							<th>이름</th>
							<td>${rq.loginedMember.name }</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>${rq.loginedMember.nickname }</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>${rq.loginedMember.cellphoneNum }</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>${rq.loginedMember.email }</td>
						</tr>
					</tbody>
				</table>
			</div>
			
		</div>
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<a href="../member/checkPassword?replaceUri=${Ut.getUriEncoded('../member/modify')}"
				class="btn btn-active myGray-bgc"> 회원정보수정 </a>
			</div>
		</div>
		
		<div class="under-btns btns">
<!-- 			<button class="btn-text-link btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
			<a href="../article/myArticles"
			class="hover:underline text-lg"> 내가 쓴 글 보기 </a>
			<a href="../article/myBookmarks"
			class="hover:underline text-lg"> 내 책갈피 서재 보기 </a>
			<a href="../member/doWithdraw" onclick="if(confirm('${rq.getLoginedMember().getNickname()}님, 정말 탈퇴하시겠습니까?\n작성된 게시글들은 자동으로 삭제되지 않습니다.') == false) return false;"
			class="hover:underline text-lg withdraw-btn two-btns-last"> 탈퇴하기 </a>
		</div>
	
	</div>
	
</section>

<%@ include file="../common/foot.jspf" %>