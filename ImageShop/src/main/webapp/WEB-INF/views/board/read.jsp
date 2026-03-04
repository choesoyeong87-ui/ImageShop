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
<title>Image Shop</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="/css/pink.css">
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/menu.jsp" />

	<div class="container" align="center">

		<h2>
			<spring:message code="board.header.read" />
		</h2>

		<form:form modelAttribute="board" id="boardForm">
			<form:hidden path="boardNo" id="boardNo" />

			<input type="hidden" id="page" name="page" value="${pgrq.page}">
			<input type="hidden" id="sizePerPage" name="sizePerPage"
				value="${pgrq.sizePerPage}">

			<table>
				<tr>
					<td><spring:message code="board.title" /></td>
					<td><form:input path="title" readonly="true" /></td>
				</tr>
				<tr>
					<td><spring:message code="board.writer" /></td>
					<td><form:input path="writer" readonly="true" /></td>
				</tr>
				<tr>
					<td><spring:message code="board.content" /></td>
					<td><form:textarea path="content" readonly="true" /></td>
				</tr>
			</table>

			<div class="button-group">
				<sec:authentication property="principal" var="principal" />

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<button type="button" id="btnEdit">수정</button>
					<button type="button" id="btnRemove">삭제</button>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_MEMBER')">
					<c:if test="${principal.username eq board.writer}">
						<button type="button" id="btnEdit">수정</button>
						<button type="button" id="btnRemove">삭제</button>
					</c:if>
				</sec:authorize>

				<button type="button" id="btnList">목록</button>
			</div>
		</form:form>

		<hr />

		<table class="data-table" style="width: 80%">
			<c:forEach items="${commentList}" var="comment">

				<tr id="comment-item-${comment.commentNo}">
					<td width="15%">${comment.userId}</td>

					<td width="55%"><c:choose>

							<c:when test="${param.targetNo eq comment.commentNo}">
								<form action="/comment/modify" method="post">
									<input type="hidden" name="commentNo"
										value="${comment.commentNo}"> <input type="hidden"
										name="boardNo" value="${board.boardNo}">

									<textarea name="content" style="width: 100%">${comment.content}</textarea>
									<div align="right">
										<button type="submit">저장</button>
										<a href="/board/read?boardNo=${board.boardNo}">취소</a>
									</div>
								</form>
							</c:when>

							<c:otherwise>
                ${comment.content}
            </c:otherwise>

						</c:choose></td>

					<td width="15%"><fmt:formatDate value="${comment.regDate}"
							pattern="yyyy-MM-dd" /></td>

					<td width="15%"><c:if
							test="${param.targetNo ne comment.commentNo}">

							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<a
									href="/board/read?boardNo=${board.boardNo}&targetNo=${comment.commentNo}#comment-item-${comment.commentNo}">
									수정 </a>

								<form action="/comment/remove" method="post"
									style="display: inline;">
									<input type="hidden" name="commentNo"
										value="${comment.commentNo}"> <input type="hidden"
										name="boardNo" value="${board.boardNo}">
									<button type="submit">삭제</button>
								</form>
							</sec:authorize>

							<sec:authorize access="hasRole('ROLE_MEMBER')">
								<sec:authentication property="principal.username"
									var="currentUserId" />
								<c:if test="${currentUserId eq comment.userId}">
									<sec:authorize access="!hasRole('ROLE_ADMIN')">
										<a
											href="/board/read?boardNo=${board.boardNo}&targetNo=${comment.commentNo}#comment-item-${comment.commentNo}">
											수정 </a>

										<form action="/comment/remove" method="post"
											style="display: inline;">
											<input type="hidden" name="commentNo"
												value="${comment.commentNo}"> <input type="hidden"
												name="boardNo" value="${board.boardNo}">
											<button type="submit">삭제</button>
										</form>
									</sec:authorize>
								</c:if>
							</sec:authorize>

						</c:if></td>

				</tr>

			</c:forEach>
		</table>

		<hr />

		<form:form modelAttribute="comment" id="commentRegisterForm"
			action="/comment/register" method="post">

			<input type="hidden" name="boardNo" value="${board.boardNo}" />

			<table>
				<tr>
					<td width="15%"><sec:authentication
							property="principal.username" var="loginId" /> <input
						type="text" name="userId" value="${loginId}" readonly /></td>
					<td width="65%"><form:textarea path="content" id="content" />
					</td>
					<td width="20%"><sec:authorize access="isAuthenticated()">
							<button type="submit">등록</button>
						</sec:authorize></td>
				</tr>
			</table>

		</form:form>

	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	<script>
		$(function() {

			$("#btnEdit").click(
					function() {
						let boardNo = $("#boardNo").val();
						let page = $("#page").val();
						let sizePerPage = $("#sizePerPage").val();
						location.href = "/board/modify?page=" + page
								+ "&sizePerPage=" + sizePerPage + "&boardNo="
								+ boardNo;
					});

			$("#btnRemove").click(
					function() {
						let boardNo = $("#boardNo").val();
						let page = $("#page").val();
						let sizePerPage = $("#sizePerPage").val();
						location.href = "/board/remove?page=" + page
								+ "&sizePerPage=" + sizePerPage + "&boardNo="
								+ boardNo;
					});

			$("#btnList").click(
					function() {
						let page = $("#page").val();
						let sizePerPage = $("#sizePerPage").val();
						location.href = "/board/list?page=" + page
								+ "&sizePerPage=" + sizePerPage;
					});

		});
	</script>

</body>
</html>