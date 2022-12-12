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
	
	$('.checkbox-all-article-id').change(function() {
		const $all = $(this);
		const allChecked = $all.prop('checked');
		$('.checkbox-article-id').prop('checked', allChecked);
	});
	$('.checkbox-article-id').change(function() {
		const checkboxArticleIdCount = $('.checkbox-article-id').length;
		const checkboxArticleIdCheckedCount = $('.checkbox-article-id:checked').length;
		const allChecked = checkboxArticleIdCount == checkboxArticleIdCheckedCount;
		$('.checkbox-all-article-id').prop('checked', allChecked);
	});
	
	$('.btn-delete-selected-articles').click(function() {
		const values = $('.checkbox-article-id:checked').map((index, el) => el.value).toArray();
		if ( values.length == 0 ) {
	 		alert('삭제할 게시물을 선택 해주세요.');
	 		return;
		}
		if ( confirm('정말 삭제하시겠습니까?') == false ) {
		return;
		}
		document['do-delete-articles-form'].ids.value = values.join(',');
		document['do-delete-articles-form'].submit();
	});
})
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title">관리자 페이지 - 신고된 게시물 리스트</h1>
		<div class="flex">
			<div class="lh-50px items-baseline">
				신고된 게시물 수 : <span class="badge">${reportedArticlesCount }개</span>
			</div>
			<div class="flex-grow"></div>
		</div>
		<div class="white-board mt-10 dropShadow-black">
			<div class="table-box-type-1 mt-3">
				<table class="table table-fixed w-full">
					<colgroup>
						<col width="100" />
						<col width="80" />
						<col />
						<col width="200" />
						<col width="200" />
						<col width="200" />
					</colgroup>
					
					<thead>
						<tr>
							<th><input type="checkbox" class="checkbox-all-article-id" /></th>
							<th class="myGray">번호</th>
							<th class="myGray">제목</th>
							<th class="myGray">작성자</th>
							<th class="myGray">작성일</th>
							<th class="myGray">신고사유</th>
						</tr>
					</thead>
	
					<tbody>
					<c:forEach var="article" items="${reportedArticles }">
						<tr class="hoverLine">
							<td><input type="checkbox" class="checkbox-article-id" value="${article.id }" /></td>
							<td>${article.id }</td>
							<td><a href="${rq.getArticleDetailUriFromArticleList(article) }">${article.title }</a></td>
							<td>${article.extra__writerName }</td>
							<!-- 오늘 게시글이면 시간 보이기 -->
							<c:if test="${rq.getTodayDate().equals(article.getForPrintType2RegDate())}">
								<td>${article.getForPrintType3RegDate() }</td>								
							</c:if>
							<!-- 오늘 게시글 아니면 날짜 보이기 -->
							<c:if test="${!rq.getTodayDate().equals(article.getForPrintType2RegDate())}">
								<td>${article.getForPrintType2RegDate() }</td>
							</c:if>
							<td>${article.getReportedReason()}</td>
						</tr>
					</c:forEach>
					<c:if test="${articlesCount == 0 }">
						<tr>
							<td colspan="6">게시글이 존재하지 않습니다.</td>
						</tr>
					</c:if>
				</tbody>
	
				</table>
			</div>
		</div>
		
		<div class="absolute mt-5">
			<button class="btn btn-error btn-delete-selected-articles">선택된 게시물 삭제</button>
		</div>

		<form hidden method="POST" name="do-delete-articles-form" action="../article/doDeleteArticles">
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