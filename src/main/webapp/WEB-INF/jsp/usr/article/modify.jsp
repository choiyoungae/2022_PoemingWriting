<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <c:set var="pageTitle" value="ARTICLE MODIFY" /> --%>
<%@ include file="../common/head.jspf" %>
<%@ include file="../common/toastUiEditorLib.jspf" %>

<script>
	let ArticleModify__submitDone = false;
	function ArticleModify__submit(form) {
		if (ArticleModify__submitDone) {
			return;
		}
		
		const editor = $(form).find('.toast-ui-editor').data('.data-toast-editor');
		const markdown = editor.getMarkdown().trim();
		
		if(markdown.length == 0) {
			alert('내용을 입력해주세요');
			editor.focus();
			return;
		}
		form.body.value = markdown;
		ArticleModify__submitDone = true;
		
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<h1 class="subPage-title">글 수정</h1>
		<div class="white-board mt-10 dropShadow-black">
			<form class="table-box-type-1" id="articleModifyForm" method="POST" action="../article/doModify" onsubmit="ArticleModify__submit(this); return false;">
				<input type="hidden" name="id" value="${article.id }" />
				<input type="hidden" name="body" />
				<table class="tableForm">
					<colgroup>
						<col width="200" />
					</colgroup>
					
					<tbody>
<!-- 						<tr> -->
<!-- 							<th>번호</th> -->
<%-- 							<td>${article.id }</td> --%>
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<th>작성날짜</th> -->
<%-- 							<td>${article.regDate }</td> --%>
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<th>수정날짜</th> -->
<%-- 							<td>${article.updateDate }</td> --%>
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<th>작성자</th> -->
<%-- 							<td>${article.extra__writerName }</td> --%>
<!-- 						</tr> -->
						<tr>
							<th>제목</th>
							<td><input class="w-full" type="text" name="title" value="${article.title }" placeholder="제목을 입력해주세요." /></td>
						</tr>
						<tr>
							<th>내용</th>
							<td>
								<div class="toast-ui-editor writingArea">
									<script type="text/x-template">${article.body}</script>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="btns-wrap flex justify-center my-5">
			<div>
				<button class="btn btn-active myGray-bgc" type="submit" form="articleModifyForm">수정</button>
			</div>
		</div>
		
<!-- 		<div class="btns"> -->
<!-- 			<button class="btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button> -->
<!-- 		</div> -->
	
	</div>
	
	
</section>
	
<%@ include file="../common/foot.jspf" %>