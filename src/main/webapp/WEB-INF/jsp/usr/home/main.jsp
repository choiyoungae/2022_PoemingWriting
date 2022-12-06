<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="MAIN" />
<%@ include file="../common/head.jspf" %>

<section class="mt-8">
	<div class="container mx-auto">
		<div class="table-box-type-1">
			<table class="table w-full">
				<colgroup>
					<col width="80" />
					<col />
					<col width="140" />
					<col width="140" />
					<col width="80" />
					<col width="80" />
				</colgroup>
				
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회</th>
						<th>책갈피</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="article" items="${noticeArticles }">
						<tr class="hover">
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
	</div>
	
</section>

<%@ include file="../common/foot.jspf" %>