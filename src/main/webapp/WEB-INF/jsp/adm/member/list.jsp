<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <c:set var="pageTitle" value="${board.name }" /> --%>
<%@ include file="../common/head.jspf" %>

<script>
$(document).ready(function() {
	$('.hoverLine').hover(function() {
		$(this).children().addClass('myGray-bgc-hover');
	}, function() {			
		$(this).children().removeClass('myGray-bgc-hover');
	})
	
	$('.checkbox-all-member-id').change(function() {
		const $all = $(this);
		const allChecked = $all.prop('checked');
		$('.checkbox-member-id').prop('checked', allChecked);
	});
	$('.checkbox-member-id').change(function() {
		const checkboxMemberIdCount = $('.checkbox-member-id').length;
		const checkboxMemberIdCheckedCount = $('.checkbox-member-id:checked').length;
		const allChecked = checkboxMemberIdCount == checkboxMemberIdCheckedCount;
		$('.checkbox-all-member-id').prop('checked', allChecked);
	});
	
	$('.btn-delete-selected-members').click(function() {
		const values = $('.checkbox-member-id:checked').map((index, el) => el.value).toArray();
		if ( values.length == 0 ) {
	 		alert('삭제할 회원을 선택 해주세요.');
	 		return;
		}
		if ( confirm('정말 삭제하시겠습니까?') == false ) {
		return;
		}
		document['do-delete-members-form'].ids.value = values.join(',');
		document['do-delete-members-form'].submit();
	});
})
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title">관리자 페이지 - 회원 리스트</h1>
		<div class="flex">
			<div class="lh-50px items-baseline">
				회원 수 : <span class="badge myGray-bgc">${membersCount }명</span>
			</div>
			<div class="flex-grow"></div>
			<form class="flex">

				<select data-value="${authLevel }" name="authLevel" class="select select-bordered myGray-border">
					<option disabled="disabled">회원 타입</option>
					<option value="3">일반</option>
					<option value="7">관리자</option>
					<option value="0">전체</option>

				</select>
				<select data-value="${searchKeywordTypeCode }" name="searchKeywordTypeCode" class="ml-2 select select-bordered myGray-border">
					<option disabled="disabled">검색 타입</option>
					<option value="loginId">아이디</option>
					<option value="name">이름</option>
					<option value="nickname">닉네임</option>
					<option value="loginId,name,nickname">전체</option>
				</select>


				<input name="searchKeyword" type="text" class="w-96 searchInput myGray-border ml-1"
					maxlength="20" value="${param.searchKeyword }" />
				<button class="btn btn-active btn-ghost circle-btn myGray-bgc" type="submit" value="검색" >
					<i class="fa-solid fa-magnifying-glass"></i>
				</button>
			</form>
		</div>
		<div class="table-box-type-1 mt-3 white-board dropShadow-black">
			<table class="table table-fixed w-full">
				<colgroup>
					<col width="100" />
					<col />
					<col />
					<col />
					<col />
					<col />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" class="checkbox-all-member-id" /></th>
						<th>번호</th>
						<th>가입일</th>
						<th>수정일</th>
						<th>아이디</th>
						<th>이름</th>
						<th>닉네임</th>
					</tr>
				</thead>

				<tbody class="text-base">
					<c:forEach var="member" items="${members }">
						<tr class="hoverLine">
							<td><input type="checkbox" class="checkbox-member-id" value="${member.id }" /></td>
							<td>${member.id}</td>
							<td>${member.forPrintType1RegDate}</td>
							<td>${member.forPrintType1UpdateDate}</td>
							<td>${member.loginId}</td>
							<td>${member.name}</td>
							<td>${member.nickname}</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
		
		<div class="absolute mt-5">
			<button class="btn btn-error btn-delete-selected-members">선택된 회원 탈퇴</button>
		</div>

		<form hidden method="POST" name="do-delete-members-form" action="../member/doDeleteMembers">
			<input type="hidden" name="ids" value="" />
			<input type="hidden" name="replaceUri" value="${rq.currentUri}" />
		</form>
		
		<div class="mt-5 flex justify-center">
			<div class="btn-group">
				<!-- 현재 페이지 번호의 앞뒤에 몇 개의 페이지 번호를 보여줄지 결정 -->
				<c:set var="pageMenuLen" value="4" />
				<!-- 페이지 번호의 시작을 지정 -->
				<c:set var="startPageNum" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" />
				<!-- 페이지 번호의 끝을 지정 -->
				<c:set var="endPageNum" value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount }" />
				<!-- 페이지 주소 설정(검색 결과에 대한 페이지 처리를 위해) -->
				<c:set var="pageBaseUri" value="?boardId=${param.boardId }" />
				<c:set var="pageBaseUri" value="${pageBaseUri }&searchKeywordTypeCode=${param.searchKeywordTypeCode }" />
				<c:set var="pageBaseUri" value="${pageBaseUri }&searchKeyword=${param.searchKeyword }" />
				
				<c:if test="${startPageNum != 1 }">
					<a class="pageChangeBtn left" href="${pageBaseUri }&page=${1 }">◀◀</a>
					<a class="pageChangeBtn left" href="${pageBaseUri }&page=${page - pageMenuLen < 1 ? 1 : page - pageMenuLen }">◀</a>
				</c:if>
				<!-- 페이지 번호 표시 -->
				<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">
					<a href="${pageBaseUri }&page=${i }">
						<p class="btn btn-ghost page-btn ${page == i ? 'page-btn-active myGray-bgc' : '' }">
							${i }
						</p>
					</a>
				</c:forEach>
				<c:if test="${endPageNum != pagesCount }">
					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${page + pageMenuLen > pagesCount ? pagesCount : page + pageMenuLen }">▶</a>
					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${pagesCount }">▶▶</a>
				</c:if>
			</div>
		</div>
	</div>
</section>
	
<%@ include file="../common/foot.jspf" %>