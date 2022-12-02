<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="책갈피 글" />
<%@ include file="../common/head.jspf" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="bookmarkedArticle-wrapper flex flex-wrap dropShadow-black">
			<c:forEach var="article" items="${articles }">
				<a class="bookmarkedArticle dropShadow-black" href="${rq.getArticleDetailUriFromArticleList(article) }">
					<p class="bookmarkedArticle-title">${article.getForPrintType1Title() }</p>
				</a>
			</c:forEach>
			<c:if test="${articlesCount == 0 }">
				<div class="noArticleSign">게시글이 존재하지 않습니다.</div>
			</c:if>
		</div>
		
<!-- 		<div class="mt-3 flex justify-center"> -->
<!-- 			<div class="btn-group"> -->
<!-- 				현재 페이지 번호의 앞뒤에 몇 개의 페이지 번호를 보여줄지 결정 -->
<%-- 				<c:set var="pageMenuLen" value="4" /> --%>
<!-- 				페이지 번호의 시작을 지정 -->
<%-- 				<c:set var="startPageNum" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" /> --%>
<!-- 				페이지 번호의 끝을 지정 -->
<%-- 				<c:set var="endPageNum" value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount }" /> --%>
<!-- 				페이지 주소 설정(검색 결과에 대한 페이지 처리를 위해) -->
<%-- 				<c:set var="pageBaseUri" value="?boardId=${param.boardId }" /> --%>
<%-- 				<c:set var="pageBaseUri" value="${pageBaseUri }&searchKeywordTypeCode=${param.searchKeywordTypeCode }" /> --%>
<%-- 				<c:set var="pageBaseUri" value="${pageBaseUri }&searchKeyword=${param.searchKeyword }" /> --%>
				
<%-- 				<c:if test="${startPageNum != 1 }"> --%>
<%-- 					<a class="pageChangeBtn left" href="${pageBaseUri }&page=${1 }">◀◀</a> --%>
<%-- 					<a class="pageChangeBtn left" href="${pageBaseUri }&page=${page - pageMenuLen < 1 ? 1 : page - pageMenuLen }">◀</a> --%>
<%-- 				</c:if> --%>
<!-- 				페이지 번호 표시 -->
<%-- 				<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1"> --%>
<%-- 					<a href="${pageBaseUri }&page=${i }"> --%>
<%-- 						<p class="btn btn-ghost ${page == i ? 'btn-active' : '' }"> --%>
<%-- 							${i } --%>
<!-- 						</p> -->
<!-- 					</a> -->
<%-- 				</c:forEach> --%>
<%-- 				<c:if test="${endPageNum != pagesCount }"> --%>
<%-- 					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${page + pageMenuLen > pagesCount ? pagesCount : page + pageMenuLen }">▶</a> --%>
<%-- 					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${pagesCount }">▶▶</a> --%>
<%-- 				</c:if> --%>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</section>
	
<%@ include file="../common/foot.jspf" %>