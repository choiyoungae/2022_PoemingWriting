<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <c:set var="pageTitle" value="MAIN" /> --%>
<%@ include file="../common/head.jspf" %>

<script>
$(document).ready(function() {
	$('.hoverLine-gray').hover(function() {
		$(this).children().addClass('myGray-bgc-hover');
	}, function() {			
		$(this).children().removeClass('myGray-bgc-hover');
	})
	
	$('.hoverLine-pink').hover(function() {
		$(this).children().addClass('myPink-bgc-hover');
	}, function() {			
		$(this).children().removeClass('myPink-bgc-hover');
	})
	
	$('.hoverLine-green').hover(function() {
		$(this).children().addClass('myGreen-bgc-hover');
	}, function() {			
		$(this).children().removeClass('myGreen-bgc-hover');
	})
})
</script>

<section class="mt-8">
	<div class="container mx-auto">
		<!-- 공지사항 게시판 -->
		<div class="table-box-type-1 main-table-wrapper white-board dropShadow-black mb-5">
			<a href="/usr/article/list?boardId=1"><h1 class="subPage-title text-center mb-10 myGray">공지사항</h1></a>
			<table class="table w-full">
				<colgroup>
					<col width="80" />
					<col />
					<col width="200" />
					<col width="200" />
					<col width="80" />
					<col width="80" />
				</colgroup>
				
				<thead>
					<tr>
						<th class="myGray">번호</th>
						<th class="myGray">제목</th>
						<th class="myGray">작성자</th>
						<th class="myGray">작성일</th>
						<th class="myGray">조회</th>
						<th class="myGray">책갈피</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="article" items="${noticeArticles }">
						<tr class="hoverLine-gray hoverLine">
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
							<td>
								<span class='article-list__hit-count'>${article.hitCount }</span>
							</td>
							<td>${article.bookmark}</td>
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
		
		<!-- 창작시 게시판 -->
		<div class="table-box-type-1 main-table-wrapper main-subPage white-board dropShadow-pink">
			<a href="/usr/article/list?boardId=2"><h1 class="subPage-title text-center mb-10 myPink">창작시</h1></a>
			<table class="table w-full">
				<colgroup>
					<col />
					<col width="200" />
					<col width="200" />
					<col width="80" />
				</colgroup>
				
				<thead>
					<tr>
						<th class="myPink">제목</th>
						<th class="myPink">작성자</th>
						<th class="myPink">작성일</th>
						<th class="myPink">책갈피</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="article" items="${poemingArticles }">
						<tr class="hoverLine-pink hoverLine">
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
							<td>${article.bookmark}</td>
						</tr>
					</c:forEach>
					<c:if test="${articlesCount == 0 }">
						<tr>
							<td colspan="4">게시글이 존재하지 않습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		
		<!-- 창작글 게시판 -->
		<div class="table-box-type-1 main-table-wrapper main-subPage white-board dropShadow-green">
			<a href="/usr/article/list?boardId=3"><h1 class="subPage-title text-center mb-10 myGreen">창작글</h1></a>
			<table class="table w-full">
				<colgroup>
					<col />
					<col width="200" />
					<col width="200" />
					<col width="80" />
				</colgroup>
				
				<thead>
					<tr>
						<th class="myGreen">제목</th>
						<th class="myGreen">작성자</th>
						<th class="myGreen">작성일</th>
						<th class="myGreen">책갈피</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="article" items="${writingArticles }">
						<tr class="hoverLine-green hoverLine">
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
							<td>${article.bookmark}</td>
						</tr>
					</c:forEach>
					<c:if test="${articlesCount == 0 }">
						<tr>
							<td colspan="4">게시글이 존재하지 않습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	
</section>

<%@ include file="../common/foot.jspf" %>