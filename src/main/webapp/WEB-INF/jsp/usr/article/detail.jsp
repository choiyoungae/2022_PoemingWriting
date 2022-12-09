<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <c:set var="pageTitle" value="ARTICLE DETAIL" /> --%>
<%@ include file="../common/head.jspf" %>
<%@ include file="../common/toastUiEditorLib.jspf" %>

<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	// 게시글 조회수 관련
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
	
	function toggleReportForm() {
		$('.reportFormWrap').toggleClass('hidden');
		$('.report-form-btn').toggleClass('btn-active');
		$('.report-word').toggleClass('hidden');
		$('.report-close-icon').toggleClass('hidden');
	}
	
	function isFormHidden() {
		if($('.reportFormWrap').hasClass('hidden')) {
			return true;
		}
		return false;
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="white-board mt-10 dropShadow-black">
			<h1 class="subPage-title text-center mb-10">${article.title }</h1>
			<div class="table-box-type-1">
				<table class="tableForm">
					<colgroup>
						<col width="100" />
					</colgroup>
					
					<tbody>
						<tr>
							<th>작성자</th>
							<td>${article.extra__writerName }</td>
							<th>작성날짜</th>
							<td>${article.regDate }</td>
							<th>조회수</th>
							<td>
								<span class='article-detail__hit-count'>${article.hitCount }</span>
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td colspan="5">
								<div class="toast-ui-viewer text-center">
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
									<a href="/usr/reaction/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }"
									 class="btn btn-outline btn-xs">좋아요 👍</a>
									<span>&nbsp;</span>
									<a href="/usr/reaction/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }"
									 class="btn btn-outline btn-xs">별로예요 👎</a>
								</c:if>
								
								<c:if test="${actorCanCancelGoodReaction}">
									<span>&nbsp;</span>
									<a href="/usr/reaction/doCancelGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri} "
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
									<a href="/usr/reaction/doCancelBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
									 class="btn btn-xs">별로예요 👎</a>
								</c:if>
							</td>
							<th></th>
							<td></td>
							<th>책갈피</th>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="btns">
			<c:if test="${empty param.listUri}">
				<button class="btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			</c:if>
			<c:if test="${not empty param.listUri}">
				<a class="btn btn-ghost" href="${param.listUri }">뒤로가기</a>
			</c:if>
			<c:if test="${!article.extra__actorCanDelete }">
				<a class="btn btn-ghost report-form-btn" onclick="toggleReportForm()">
					<p class="report-word">신고</p>
					<p class="report-close-icon hidden"><i class="fa-solid fa-xmark"></i></p>
				</a>
				<div class="reportFormWrap hidden">
					<form name="report" action="../reaction/doReport">
						<input type="hidden" name="relId" value="${article.id }" />
						<input type="hidden" name="reportedMemberId" value="${article.memberId }" />
						<label class="report-label"><input type="radio" name="reason" value="1" required />스팸홍보/도배글</label>
						<label class="report-label"><input type="radio" name="reason" value="2" />음란물</label>
						<label class="report-label"><input type="radio" name="reason" value="3" />불법정보/저작권 침해</label>
						<label class="report-label"><input type="radio" name="reason" value="4" />욕설/생명경시/명예훼손/혐오/차별적 표현</label>
<%-- 						<input type="hidden" name="replaceUri" value="${rq.encodedCurrentUri }" /> --%>
						<button class="btn btn-ghost btn-active report-btn" type="submit" value="신고" >신고하기</button>
					</form>
				</div>
				<c:if test="${!rq.isLogined() }">
					<a class="btn btn-ghost" href="../reaction/doBookmark?relId=${article.id }&replaceUri=${rq.encodedCurrentUri}">
						<i class="fa-regular fa-bookmark"></i>
					</a>
				</c:if>
				<c:if test="${rq.isLogined() }">
					<c:if test="${actorCanMakeBookmark }">
						<a class="btn btn-ghost" href="../reaction/doBookmark?relId=${article.id }&replaceUri=${rq.encodedCurrentUri}">
							<i class="fa-regular fa-bookmark"></i>
						</a>
					</c:if>
					<c:if test="${!actorCanMakeBookmark }">
						<a class="btn btn-ghost" href="../reaction/doCancelBookmark?relId=${article.id }&replaceUri=${rq.encodedCurrentUri}">
							<i class="fa-solid fa-bookmark"></i>
						</a>
					</c:if>
				</c:if>
			</c:if>
			<c:if test="${article.extra__actorCanModify }">
				<a class="btn btn-ghost" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	
	</div>
	
</section>

<!-- 댓글 부분 -->
<section class="mt-5">
	<div class="container mx-auto px-3">
		<div class="white-board-reply mt-10 dropShadow-black">
			<div class="table-box-type-1 mt-5">
				<table class="table w-full">
					<colgroup>
						<col width="80" />
						<col width="100" />
						<col />
						<col width="50" />
						<col width="50" />
					</colgroup>
					
	<!-- 				<thead> -->
	<!-- 					<tr> -->
	<!-- 						<th>날짜</th> -->
	<!-- 						<th>작성자</th> -->
	<!-- 						<th>내용</th> -->
	<!-- 						<th>추천</th> -->
	<!-- 						<th>비고</th> -->
	<!-- 					</tr> -->
	<!-- 				</thead> -->
			
					<tbody>
						<c:forEach var="reply" items="${replies }">
							<tr>
								<td>${reply.forPrintType2RegDate }</td>
								<td>${reply.extra__writerName }</td>
								<td>${reply.getForPrintBody() }</td>
								<td>👍${reply.goodReactionPoint }</td>
								<td>
									<!-- 자신이 쓴 댓글일 경우 -->
									<c:if test="${reply.extra__actorCanModify }">
										<a href="../reply/modify?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">수정</a>
									</c:if>
									<c:if test="${reply.extra__actorCanDelete }">
										<a onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" 
										href="../reply/doDelete?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">삭제</a>
									</c:if>
									
									<!-- 자신이 쓴 댓글이 아닐 경우 -->
									<c:if test="${reply.extra__actorCanMakeReplyReaction }">
										<span>&nbsp;</span>
										<a href="/usr/reaction/doGoodReaction?relTypeCode=reply&relId=${reply.id }&replaceUri=${rq.encodedCurrentUri }"
										 class="btn btn-outline btn-xs">좋아요 👍</a>
										<span>&nbsp;</span>
										<a href="/usr/reaction/doBadReaction?relTypeCode=reply&relId=${reply.id }&replaceUri=${rq.encodedCurrentUri }"
										 class="btn btn-outline btn-xs">별로예요 👎</a>
									</c:if>
									
									<c:if test="${reply.extra__actorCanCancelGoodReplyReaction }">
										<span>&nbsp;</span>
										<a href="/usr/reaction/doCancelGoodReaction?relTypeCode=reply&relId=${reply.id}&replaceUri=${rq.encodedCurrentUri} "
										 class="btn btn-xs">좋아요 👍</a>
										<span>&nbsp;</span>
										<a onclick="alert(this.title); return false;" title="좋아요를 먼저 취소해주세요" href="#"
										 class="btn btn-outline btn-xs">별로예요👎</a>
									</c:if>
									
									<c:if test="${reply.extra__actorCanCancelBadReplyReaction }">
										<span>&nbsp;</span>
										<a onclick="alert(this.title); return false;" title="싫어요를 먼저 취소해주세요" href="#"
										 class="btn btn-outline btn-xs">좋아요👍</a>
										<span>&nbsp;</span>
										<a href="/usr/reaction/doCancelBadReaction?relTypeCode=reply&relId=${reply.id}&replaceUri=${rq.encodedCurrentUri}"
										 class="btn btn-xs">별로예요 👎</a>
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
			
			<c:if test="${rq.logined }">
				<form class="table-box-type-1" method="POST" action="../reply/doWrite" onsubmit="ReplyWrite__submitForm(this); return false;">
					<input type="hidden" name="relTypeCode" value="article" />
					<input type="hidden" name="relId" value="${article.id }" />
					<input type="hidden" name="replaceUri" value="${rq.currentUri }" />
					<table class="table w-full">
						<colgroup>
							<col width="200" />
							<col/>
							<col width="200" />
						</colgroup>
	
						<tbody>
							<tr>
								<th>${rq.loginedMember.nickname }</th>
								<td>
									<textarea style="resize: none;" class="textarea textarea-bordered w-full" type="text" name="body"
										placeholder="댓글을 입력해주세요" /></textarea>
								</td>
								<td>
									<button class="btn btn-active btn-ghost" type="submit">댓글작성</button>
								</td>
							</tr>
						</tbody>
	
					</table>
				</form>
			</c:if>
			<c:if test="${rq.notLogined }">
				<a class="btn  btn-ghost" href="${rq.loginUri}">로그인</a> 후 이용해주세요
			</c:if>
		</div>
		
<!-- 		<div class="mt-3 flex justify-center"> -->
<!-- 			<div class="btn-group"> -->
<!-- 				현재 페이지 번호의 앞뒤에 몇 개의 페이지 번호를 보여줄지 결정 -->
<%-- 				<c:set var="pageMenuLen" value="4" /> --%>
<!-- 				페이지 번호의 시작을 지정 -->
<%-- 				<c:set var="startPageNum" value="${replyPage - pageMenuLen >= 1 ? replyPage - pageMenuLen : 1 }" /> --%>
<!-- 				페이지 번호의 끝을 지정 -->
<%-- 				<c:set var="endPageNum" value="${replyPage + pageMenuLen <= replyPagesCount ? replyPage + pageMenuLen : replyPagesCount }" /> --%>
<!-- 				페이지 주소 설정(검색 결과에 대한 페이지 처리를 위해) -->
<%-- 				<c:set var="pageBaseUri" value="?id=${param.id }" /> --%>
				
<%-- 				<c:if test="${startPageNum != 1 }"> --%>
<%-- 					<a class="pageChangeBtn left" href="${pageBaseUri }&replyPage=${1 }">◀◀</a> --%>
<%-- 					<a class="pageChangeBtn left" href="${pageBaseUri }&replyPage=${replyPage - pageMenuLen < 1 ? 1 : replyPage - pageMenuLen }">◀</a> --%>
<%-- 				</c:if> --%>
<!-- 				페이지 번호 표시 -->
<%-- 				<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1"> --%>
<%-- 					<a href="${pageBaseUri }&replyPage=${i }"> --%>
<%-- 						<p class="btn btn-ghost ${replyPage == i ? 'btn-active' : '' }"> --%>
<%-- 							${i } --%>
<!-- 						</p> -->
<!-- 					</a> -->
<%-- 				</c:forEach> --%>
<%-- 				<c:if test="${endPageNum != replyPagesCount }"> --%>
<%-- 					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${replyPage + pageMenuLen > replyPagesCount ? replyPagesCount : replyPage + pageMenuLen }">▶</a> --%>
<%-- 					<a class="pageChangeBtn right" href="${pageBaseUri }&page=${replyPagesCount }">▶▶</a> --%>
<%-- 				</c:if> --%>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</section>

<%@ include file="../common/foot.jspf" %>