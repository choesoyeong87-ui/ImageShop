<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="/css/pink.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<h2>
	<spring:message code="user.header.modify" />
</h2>
<form:form modelAttribute="member" action="/user/modify2" method="post">
	<form:hidden path="userNo" />
	<form:hidden path="userId" />
	<table class="user_table">
		<tr>
			<td><spring:message code="user.userId" /></td>
			<td><form:input path="userId"/></td>
			<td><font color="red"><form:errors path="userId" /></font></td>
		</tr>
		
		<tr>
			<td><spring:message code="user.userName" /></td>
			<td><form:input path="userName"/></td>
			<td><font color="red"><form:errors path="userName" /></font></td>
		</tr>
		<tr>
			<td><spring:message code="user.job" /></td>
			<td><form:select path="job" items="${jobList}" itemValue="value"
					itemLabel="label"/></td>
			<td><font color="red"><form:errors path="job" /></font></td>
		</tr>
		<tr>
                    <td><spring:message code="user.auth" /> - 1</td>
                    <td><form:select path="authList[0].auth">
                            <form:option value="" label="=== 선택해 주세요 ===" />
                            <form:option value="ROLE_USER" label="사용자" />
                            <form:option value="ROLE_MEMBER" label="회원" />
                            <form:option value="ROLE_ADMIN" label="관리자" />
                        </form:select></td>
                </tr>
                <tr>
                    <td><spring:message code="user.auth" /> - 2</td>
                    <td><form:select path="authList[1].auth" >
                            <form:option value="" label="=== 선택해 주세요 ===" />
                            <form:option value="ROLE_USER" label="사용자" />
                            <form:option value="ROLE_MEMBER" label="회원" />
                            <form:option value="ROLE_ADMIN" label="관리자" />
                        </form:select></td>
                </tr>
                <tr>
                    <td><spring:message code="user.auth" /> - 3</td>
                    <td><form:select path="authList[2].auth" >
                            <form:option value="" label="=== 선택해 주세요 ===" />
                            <form:option value="ROLE_USER" label="사용자" />
                            <form:option value="ROLE_MEMBER" label="회원" />
                            <form:option value="ROLE_ADMIN" label="관리자" />
                        </form:select></td>
                </tr>
		
	</table>
</form:form>
<div>
	<button type="button" id="btnModify">
		<spring:message code="action.modify" />
	</button>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<button type="button" id="btnList">
			<spring:message code="action.list" />
		</button>
	</sec:authorize>
</div>
<script>
	$(document).ready(function() {
		let formObj = $("#member");
		
		
		$("#btnModify").on("click", function() {
			formObj.submit();
		});

		$("#btnList").on("click", function() {
			self.location = "/user/list";
		});
	});
</script>
