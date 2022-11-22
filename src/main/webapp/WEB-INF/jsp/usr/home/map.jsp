<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="도서관 위치 보기" />

<%@ include file="../common/head.jspf" %>

<div id="map" style="width:100%;height:600px;" class="flex container mx-auto"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92cf62efa6faf388f2640e4c37dc6025"></script>
<script>
	var container = document.getElementById('map');
	var options = {
		center: new kakao.maps.LatLng(33.450701, 126.570667),
		level: 3
	};

	var map = new kakao.maps.Map(container, options);
</script>

<%@ include file="../common/foot.jspf" %>