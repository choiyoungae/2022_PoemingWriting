<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="ADMIN" />
<%@ include file="../common/head.jspf" %>

<section class="mt-8">
	<div class="container mx-auto">
		<c:if test="${!isAdminLogined }">
			<div>관리자 전용 페이지입니다. 일반 고객님들은 상단의 HOME 버튼을 통해 시스템 메인 페이지로 이동하실 수 있습니다.</div>
		</c:if>
		
		<c:if test="${isAdminLogined }">
			<div class="mt-10">
				관리자님, 안녕하세요
			</div>
			
			<div class="mt-10">
				<h3>회원 목록</h3>
				<ul>
					<li></li>
				</ul>
			</div>
		</c:if>
	</div>
	
	
</section>

<%@ include file="../common/foot.jspf" %>