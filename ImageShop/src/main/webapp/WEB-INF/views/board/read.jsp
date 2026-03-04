<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image Shop | CodeGroup</title>
<!-- <script type="text/javascript" src="/js/test.js"></script> -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="/css/pink.css">

</head>
<body>
	<!-- jsp:include는 동적처리방식 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />

	<div class="container" align="center">
		<h2>
			<spring:message code="board.header.read" />
		</h2>
		<form:form modelAttribute="board" id="boardForm">
			<form:hidden path="boardNo" />
			<!-- 현재 페이지 번호와 페이징 크기를 숨겨진 필드 요소를 사용하여 전달한다. -->
			<input type="hidden" id="page" name="page" value="${pgrq.page}">
			<input type="hidden" id="sizePerPage" name="sizePerPage"
				value="${pgrq.sizePerPage}">
			<table>
				<tr>
					<td><spring:message code="board.title" /></td>
					<td><form:input path="title" readonly="true" /></td>
					<td><font color="red"><form:errors path="title" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="board.writer" /></td>
					<td><form:input path="writer" readonly="true" /></td>
					<td><font color="red"><form:errors path="writer" /></font></td>
				</tr>
				<tr>
					<td><spring:message code="board.content" /></td>
					<td><form:textarea path="content" readonly="true" /></td>
					<td><font color="red"><form:errors path="content" /></font></td>
				</tr>
			</table>
			<!-- 사용자정보를 가져온다. -->
			<div class="button-group">
				<sec:authentication property="principal" var="principal" />
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<button type="button" id="btnEdit">
						<spring:message code="action.edit" />
					</button>
					<button type="button" id="btnRemove">
						<spring:message code="action.remove" />
					</button>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_MEMBER')">
					<c:if test="${principal.username eq board.writer}">
						<button type="button" id="btnEdit">
							<spring:message code="action.edit" />
						</button>
						<button type="button" id="btnRemove">
							<spring:message code="action.remove" />
						</button>
					</c:if>
				</sec:authorize>

				<button type="button" id="btnList">
					<spring:message code="action.list" />
				</button>

			</div>
		</form:form>
		<!-- comment -->
		<form:form modelAttribute="comment" id="commentListForm">
			<input type="hidden" name="boardNo" value="${board.boardNo}" />
			<table>
				<c:choose>
					<c:when test="${empty commentList}">
						<tr>
							<td colspan="3"><spring:message code="common.listEmpty" /></td>
						</tr>
					</c:when>

					<c:otherwise>
						<c:forEach items="${commentList}" var="comment">
							<tr>
								<td align="center">${comment.userId}</td>
								<td align="left">${comment.content}</td>
								<td align="center"><fmt:formatDate
										pattern="yyyy-MM-dd HH:mm" value="${comment.regDate}" /></td>

								<td align="left"><sec:authorize
										access="hasRole('ROLE_MEMBER')">
										<c:if test="${customuser.username eq comment.userId}">
											<a
												href="/comment/remove?commentNo=${comment.commentNo}
										&boardNo=${board.boardNo}">삭제</a>
										</c:if>
									</sec:authorize></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</form:form>
		<!-- 입력폼 -->
		<form:form modelAttribute="comment" id="commentRegisterForm"
			action="/comment/register" method="post">
			<input type="hidden" name="boardNo" value="${board.boardNo}" />
			<table>


				<tr>
					<td><sec:authentication property="principal.username"
							var="loginId" /> <input type="text" value="${loginId}"
						name="userId" value="${comment.userId}" readonly /></td>

					<td align="left"><label for="content">내용</label> <form:textarea
							path="content" id="content" /></td>
					<td align="left"><sec:authorize access="isAuthenticated()">
							<button type="button" id="btnRegister">등록</button>
						</sec:authorize></td>
					<td align="left"><sec:authorize access="isAuthenticated()">
							<button type="button" id="btnRegister">삭제</button>
						</sec:authorize></td>

					<form action="/comment/remove" method="post">
						<input type="hidden" name="commentNo" value="${comment.commentNo}">
						<input type="hidden" name="boardNo" value="${board.boardNo}">
						<button type="submit" id="">삭제</button>
					</form>


				</tr>


			</table>
		</form:form>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script>
		$(document).ready(
				function() {
					// form의 id를 명시적으로 지정하여 찾는 것이 더 안전합니다.
					let formObj = $("#commentRegisterForm");

					$("#btnEdit").on(
							"click",
							function() {
								let boardNo = $("#boardNo").val();
								let page = $("#page").val();
								let sizePerPage = $("#sizePerPage").val();
								self.location = "/board/modify?page=" + page
										+ "&sizePerPage=" + sizePerPage
										+ "&boardNo=" + boardNo;
							});
					$("#btnRemove").on(
							"click",
							function() {
								let boardNo = $("#boardNo").val();
								let page = $("#page").val();
								let sizePerPage = $("#sizePerPage").val();
								self.location = "/board/remove?page=" + page
										+ "&sizePerPage=" + sizePerPage
										+ "&boardNo=" + boardNo;
							});
					$("#btnList").on(
							"click",
							function() {
								let page = $("#page").val();
								let sizePerPage = $("#sizePerPage").val();
								self.location = "/board/list?page=" + page
										+ "&sizePerPage=" + sizePerPage;
							});
					$("#btnRegister").on("click", function() {
						formObj.submit();
					});
				});
	</script>
</body>
</html>