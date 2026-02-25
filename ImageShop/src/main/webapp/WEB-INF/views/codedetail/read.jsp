<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="/css/pink.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<h2>
	<spring:message code="codedetail.header.read" />
</h2>
<form:form modelAttribute="codeDetail">
	<form:hidden path="groupCode"/>
	<table>
		<tr>
			<td><spring:message code="codedetail.groupCode" /></td>
			<td><form:select path="groupCode" items="${groupCodeList}"
					itemValue="value" itemLabel="label" disabled="true"/></td>
			<td><font color="red"><form:errors path="groupCode" /></font></td>
		</tr>
		<tr>
			<td><spring:message code="codedetail.codeValue" /></td>
			<td><form:input path="codeValue" readonly="true"/></td>
			<td><font color="red"><form:errors path="codeValue" /></font></td>
		</tr>
		<tr>
			<td><spring:message code="codedetail.codeName" /></td>
			<td><form:input path="codeName" readonly="true"/></td>
			<td><font color="red"><form:errors path="codeName" /></font></td>
		</tr>
	</table>
</form:form>
<div>
	<button type="submit" id="btnEdit">
		<spring:message code="action.edit" />
	</button>
	<button type="submit" id="btnRemove">
		<spring:message code="action.remove" />
	</button>
	<button type="submit" id="btnList">
		<spring:message code="action.list" />
	</button>
</div>
<script>
	<!--html코드가 document로 객체가 메모리에 로딩이 완료되었으면 이벤트를 걸겠다-->
		$(document).ready(function() {
			<!--for 객체 찾기-->
			let formObj = $("#codeDetail");
			<!--전송버튼을 클릭할때 작동되는 핸들러임/서버로 전송-->
			$("#btnEdit").on("click", function() {
				formObj.attr("action","/codedetail/modify");
				formObj.attr("method","get");
				formObj.submit();
			});
			<!--목록버튼을 클릭했을때 작동되는 핸들러임-->
			
			$("#btnRemove").on("click", function() {
				formObj.attr("action","/codedetail/remove");
				formObj.attr("method","get");
				formObj.submit();
			});
			
			$("#btnList").on("click", function() {
				<!--서버에페이지요청 http://localhost:8080/codegroup/list-->
				self.location = "/codedetail/list";
			});
		});
	</script>
