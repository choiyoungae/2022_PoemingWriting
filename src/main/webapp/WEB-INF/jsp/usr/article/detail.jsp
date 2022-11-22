<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf" %>
<%@ include file="../common/toastUiEditorLib.jspf" %>

<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView'
		
		if(localStorage.getItem(localStorageKey)) {
			return;
		}
		
		localStorage.setItem(localStorageKey, true);

		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
			
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
			
		}, 'json');
	}
	
	$(function() {
		ArticleDetail__increaseHitCount();
	})
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				
				<tbody>
					<tr>
						<th>번호</th>
						<td>${article.id }</td>
					</tr>
					<tr>
						<th>작성날짜</th>
						<td>${article.regDate }</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>${article.updateDate }</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${article.extra__writerName }</td>
					</tr>
					<tr>
						<th>조회수</th>
						<td>
							<span class='article-detail__hit-count'>${article.hitCount }</span>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<div class="toast-ui-viewer">
								<script type="text/x-template">${article.body}</script>
							</div>
						</td>
					</tr>
					<tr>
						<th>추천</th>
						<td>
							<span>${article.goodReactionPoint }</span>
							
							<c:if test="${actorCanMakeReaction }">
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }"
								 class="btn btn-outline btn-xs">좋아요 👍</a>
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }"
								 class="btn btn-outline btn-xs">별로예요 👎</a>
							</c:if>
							
							<c:if test="${actorCanCancelGoodReaction}">
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doCancelGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri} "
								 class="btn btn-xs">좋아요 👍</a>
								<span>&nbsp;</span>
								<a onclick="alert(this.title); return false;" title="좋아요를 먼저 취소해주세요" href="#"
								 class="btn btn-outline btn-xs">별로예요👎</a>
							</c:if>
							
							<c:if test="${actorCanCancelBadReaction}">
								<span>&nbsp;</span>
								<a onclick="alert(this.title); return false;" title="싫어요를 먼저 취소해주세요" href="#"
								 class="btn btn-outline btn-xs">좋아요👍</a>
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doCancelBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
								 class="btn btn-xs">별로예요 👎</a>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="btns">
			<c:if test="${empty param.listUri}">
				<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			</c:if>
			<c:if test="${not empty param.listUri}">
				<a class="btn-text-link btn btn-active btn-ghost" href="${param.listUri }">뒤로가기</a>
			</c:if>
			<c:if test="${article.extra__actorCanModify }">
				<a class="btn-text-link btn btn-ghost" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn-text-link btn btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	
	</div>
	
</section>

<!-- 댓글 부분 -->
<section class="mt-5">
	<div class="container mx-auto px-3">
		<h2>댓글 작성</h2>
		<c:if test="${rq.logined }">
			<form class="table-box-type-1" method="POST" action="../reply/doWrite" onsubmit="ReplyWrite__submitForm(this); return false;">
				<input type="hidden" name="relTypeCode" value="article" />
				<input type="hidden" name="relId" value="${article.id }" />
				<input type="hidden" name="replaceUri" value="${rq.currentUri }" />
				<table class="table w-full">
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>작성자</th>
							<td>${rq.loginedMember.nickname }</td>
						</tr>
						<tr>
							<th>내용</th>
							<td>
								<textarea style="resize: none;" class="textarea textarea-bordered w-full" type="text" name="body"
									placeholder="댓글을 입력해주세요" /></textarea>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button class="btn btn-active btn-ghost" type="submit">댓글작성</button>
							</td>
						</tr>
					</tbody>

				</table>
			</form>
		</c:if>
		<c:if test="${rq.notLogined }">
			<a class="btn-text-link btn  btn-ghost" href="${rq.loginUri}">로그인</a> 후 이용해주세요
		</c:if>
		
		<h2 class="mt-5">댓글 목록</h2>
		<div class="table-box-type-1">
			<table class="table w-full">
				<colgroup>
					<col width="50" />
					<col width="100" />
					<col width="100" />
					<col width="140" />
					<col width="50" />
					<col width="50" />
				</colgroup>
				
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>작성자</th>
						<th>내용</th>
						<th>추천</th>
						<th>비고</th>
					</tr>
				</thead>
		
				<tbody>
					<c:forEach var="reply" items="${replies }" varStatus="status">
						<tr>
							<td>${(replyPage-1)*5 + status.count }</td>
							<td>${reply.forPrintType1RegDate }</td>
							<td>${reply.extra__writerName }</td>
							<td>${reply.getForPrintBody() }</td>
							<td>${reply.goodReactionPoint }</td>
							<td>
								<c:if test="${reply.extra__actorCanModify }">
									<a class="btn-text-link" href="../reply/modify?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">수정</a>
								</c:if>
								<c:if test="${reply.extra__actorCanDelete }">
									<a class="btn-text-link" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" 
									href="../reply/doDelete?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${repliesCount == 0 }">
						<tr>
							<td colspan="6">댓글이 존재하지 않습니다.</td>
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
				<c:set var="startPageNum" value="${replyPage - pageMenuLen >= 1 ? replyPage - pageMenuLen : 1 }" />
				<!-- 페이지 번호의 끝을 지정 -->
				<c:set var="endPageNum" value="${replyPage + pageMenuLen <= replyPagesCount ? replyPage + pageMenuLen : replyPagesCount }" />
				<!-- 페이지 주소 설정(검색 결과에 대한 페이지 처리를 위해) -->
				<c:set var="pageBaseUri" value="?id=${param.id }" />
				
				<c:if test="${startPageNum != 1 }">
					<a class="pageChangeBtn left" href="${pageBaseUri }&replyPage=${1 }">◀◀</a>
					<a class="pageChangeBtn left" href="${pageBaseUri }&replyPage=${replyPage - pageMenuLen < 1 ? 1 : replyPage - pageMenuLen }">◀</a>
				</c:if>
				<!-- 페이지 번호 표시 -->
				<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">
					<a href="${pageBaseUri }&replyPage=${i }">
						<p class="btn btn-ghost ${replyPage == i ? 'btn-active' : '' }">
							${i }
						</p>
					</a>
				</c:forEach>
				<c:if test="${endPageNum != replyPagesCount }">
					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${replyPage + pageMenuLen > replyPagesCount ? replyPagesCount : replyPage + pageMenuLen }">▶</a>
					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${replyPagesCount }">▶▶</a>
				</c:if>
			</div>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf" %>