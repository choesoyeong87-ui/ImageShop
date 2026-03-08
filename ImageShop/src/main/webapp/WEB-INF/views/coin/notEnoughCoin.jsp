<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="/css/pink.css">
<div>
	<h2>
		<spring:message code="coin.header.chargeCoin" />
	</h2>

	<p>
		<spring:message code="coin.notEnoughCoin" />
	</p>

	<a href="/coin/charge"><spring:message code="coin.charge" /></a>
</div>
