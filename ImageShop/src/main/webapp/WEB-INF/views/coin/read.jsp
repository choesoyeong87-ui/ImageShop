<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="/css/pink.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<h2>
	<spring:message code="item.header.read" />
</h2>

<form:form modelAttribute="item" action="/item/read"
	enctype="multipart/form-data" method="post">
	<form:hidden path="itemId" />
	

	<table>
		<tr>
			<td><spring:message code="item.itemName" /></td>
			<td><form:input path="itemName" readonly="true"/></td>
			<td><font color="red"><form:errors path="itemName" /></font></td>
		</tr>
		<tr>
			<td><spring:message code="item.itemPrice" /></td>
			<td><form:input path="price" readonly="true"/>&nbsp;원</td>
			<td><font color="red"><form:errors path="price" /></font></td>
		</tr>
		<tr>
			<td><spring:message code="item.picture" /></td>
			<td><img src="picture?itemId=${item.itemId}" width="210"></td>
		</tr>
		<tr>
			<td><spring:message code="item.preview" /></td>
			<td><img src="display?itemId=${item.itemId}" width="210"></td>
		</tr>
		

		<tr>

			<td><spring:message code="item.itemDescription" /></td>
			<td><form:textarea path="description" /></td>
			<td><form:errors path="description" /></td>
		</tr>
	</table>
</form:form>

<div>
	

	<button type="button" id="btnList">
		<spring:message code="action.list" />
	</button>
</div>
<script>
	$(document).ready(function() {
		var formObj = $("#item");
		

		$("#btnList").on("click", function() {
			self.location = "/item/list";
		});
	});
</script>
