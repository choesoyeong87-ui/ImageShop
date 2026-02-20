<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 범용 spring 태그라이브러리를 선언한다. -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image Shop</title>
<!-- 
<script type="text/javascript" src="/js/test.js"></script>
<link rel="stylesheet" href="/css/style.css">
 -->
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/common/menu.jsp"/>
	<div align="center">
	<h1>
		<!-- 메시지 프로퍼티 파일로부터 메시지 내용을 읽어온다. -->
		<spring:message code="common.homeWelcome" />

	</h1>
	<P>${serverTime}</P>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>
