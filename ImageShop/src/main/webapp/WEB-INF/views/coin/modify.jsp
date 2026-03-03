<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="/css/pink.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<h2>
	<spring:message code="item.header.modify" />
</h2>

<form:form modelAttribute="item" action="/item/modify"
	enctype="multipart/form-data" method="post">
	<form:hidden path="itemId" />
	<form:hidden path="pictureUrl" />
	<form:hidden path="previewUrl" />

	<table>
		<tr>
			<td><spring:message code="item.itemName" /></td>
			<td><form:input path="itemName" /></td>
			<td><font color="red"><form:errors path="itemName" /></font></td>
		</tr>
		<tr>
			<td><spring:message code="item.itemPrice" /></td>
			<td><form:input path="price" />&nbsp;원</td>
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

			<td><spring:message code="item.itemFile" /></td>
			<td><input type="file" name="picture" /></td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="item.itemPreviewFile" /></td>
			<td><input type="file" name="preview" /></td>
			<td></td>

		</tr>

		<tr>

			<td><spring:message code="item.itemDescription" /></td>
			<td><form:textarea path="description" /></td>
			<td><form:errors path="description" /></td>
		</tr>
	</table>
</form:form>

<div>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<button type="button" id="btnModify">
			<spring:message code="action.modify" />
		</button>
	</sec:authorize>

	<button type="button" id="btnList">
		<spring:message code="action.list" />
	</button>
</div>
<script>
	$(document).ready(function() {
		var formObj = $("#item");
		$("#btnModify").on("click", function() {
			formObj.submit();
		});

		$("#btnList").on("click", function() {
			self.location = "/item/list";
		});
	});
</script>
