<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jspf" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="flex justify-between items-center">
			<div class="articleCountNum">게시물 갯수 : ${articlesCount }개</div>
			<div>
				<form class="table-box-type-1" method="POST" action="../article/list">
					<input type="hidden" name="boardId" value="${param.boardId }" />
					<select class="select select-bordered lh-48px" name="searchKeywordTypeCode">
						<option disabled>검색</option>
						<option value="title">제목</option>
						<option value="body">내용</option>
						<option value="title,body">제목 + 내용</option>
					</select>
					<input class="w-96 searchInput lh-48px" type="text" name="searchKeyword" placeholder="검색할 내용을 입력해주세요" value="${param.searchKeyword }" />
					<button class="btn btn-ghost lh-48px" type="submit" value="검색" />검색</button>
				</form>
			</div>
			<div>
				<a class="mb-2 btn btn-active btn-ghost" href="../article/write">게시물 작성하기</a>
			</div>
		</div>
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
					<c:forEach var="article" items="${articles }">
						<tr class="hover">
							<td>${article.id }</td>
							<td><a href="${rq.getArticleDetailUriFromArticleList(article) }">${article.title }</a></td>
							<td>${article.extra__writerName }</td>
							<c:choose>							
								<!-- 오늘 게시글이면 시간 보이기 -->
								<c:when test="${rq.getTodayDate().equals(article.getForPrintType2RegDate())}">
									<td>${article.getForPrintType3RegDate() }</td>								
								</c:when>
								<!-- 오늘 게시글 아니면 날짜 보이기 -->
								<c:otherwise>
									<td>${article.getForPrintType2RegDate() }</td>
								</c:otherwise>
							</c:choose>
							<td>
								<span class='article-list__hit-count'>${article.hitCount }</span>
							</td>
							<td>${article.bookmarked}</td>
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
		<div class="mt-3 flex justify-center">
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
						<p class="btn btn-ghost ${page == i ? 'btn-active' : '' }">
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