<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="/css/codegroup.css">
<link rel="stylesheet" href="/css/white.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<!-- jsp:include는 동적처리방식 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <jsp:include page="/WEB-INF/views/common/menu.jsp" />
<h2>
	<spring:message code="auth.header.logout" />
</h2>

<form method="post" action="/auth/logout">
	<table>
		
		<tr>
			<td align="center"><button>
					<spring:message code="action.logout" />
				</button></td>
		</tr>
	</table>
	<sec:csrfInput />
</form>


