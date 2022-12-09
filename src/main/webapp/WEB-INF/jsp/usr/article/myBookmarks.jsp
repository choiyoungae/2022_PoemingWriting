<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <c:set var="pageTitle" value="책갈피 글" /> --%>
<%@ include file="../common/head.jspf" %>

<script>
$(document).ready(function() {
	$('.bookmarkedArticle').hover(function() {
		$(this).addClass('goUp');
	}, function() {			
		$(this).removeClass('goUp');
	})
	
})
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3 relative">
		<h1 class="subPage-title lh-50px mb-20px">내 책갈피 서재</h1>
		
		<div class="flex justify-center absolute right-0 myBookmarks-btns">
			<div class="btn-group">
				<!-- 현재 페이지 번호의 앞뒤에 몇 개의 페이지 번호를 보여줄지 결정 -->
				<c:set var="pageMenuLen" value="4" />
				<!-- 페이지 번호의 시작을 지정 -->
				<c:set var="startPageNum" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" />
				<!-- 페이지 번호의 끝을 지정 -->
				<c:set var="endPageNum" value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount }" />
				
				<c:if test="${startPageNum != 1 }">
					<a class="pageChangeBtn left" href="?page=${1 }">◀◀</a>
					<a class="pageChangeBtn left" href="?page=${page - pageMenuLen < 1 ? 1 : page - pageMenuLen }">◀</a>
				</c:if>
				<!-- 페이지 번호 표시 -->
				<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">
					<a href="?page=${i }">
						<p class="btn btn-ghost page-btn ${page == i ? 'page-btn-active myGray-bgc' : '' }">
							${i }
						</p>
					</a>
				</c:forEach>
				<c:if test="${endPageNum != pagesCount }">
					<a class="pageChangeBtn right" href="?page=${page + pageMenuLen > pagesCount ? pagesCount : page + pageMenuLen }">▶</a>
					<a class="pageChangeBtn right" href="?page=${pagesCount }">▶▶</a>
				</c:if>
			</div>
		</div>
		
		<div class="bookmarkedArticle-wrapper white-board flex flex-wrap dropShadow-black">
			<c:forEach var="article" items="${articles }">
				<a class="bookmarkedArticle dropShadow-black" href="${rq.getArticleDetailUriFromArticleList(article) }">
					<p class="bookmarkedArticle-title">${article.getForPrintType1Title() }</p>
				</a>
			</c:forEach>
			<c:if test="${articlesCount == 0 }">
				<div class="noArticleSign">게시글이 존재하지 않습니다.</div>
			</c:if>
		</div>
	</div>
</section>
	
<%@ include file="../common/foot.jspf" %>