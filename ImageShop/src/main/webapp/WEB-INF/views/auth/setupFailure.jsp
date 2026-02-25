<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="/css/pink.css">
<div>
	<h2>
		<spring:message code="common.cannotSetupAdmin"/>
	</h2>
	<a href="/auth/login"><spring:message code="action.home" /></a>
</div>
