<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div align="right">
	<table>
		<tr>
			<td width="80"><a href="/"><spring:message
						code="header.home" /></a></td>

			<!-- 비회원-->
			<sec:authorize access="!isAuthenticated()">
				<!-- 회원게시판리스트-->
				<td width="120"><a href="/board/list">회원게시판리스트</a></td>
			</sec:authorize>
			<!-- 공지사항리스트 -->
			<td width="120"><a href="/notice/list"><spring:message
						code="menu.notice.member" /></a></td>
			<!-- 회원(인가: 관리자, 회원, 메니저)  -->
			<sec:authorize access="isAuthenticated()">
				<!-- 상품 리스트 -->
				<td width="120"><a href="/item/list">상품리스트</a></td>


				<!-- 인증완료, (인가: 관리자)일때 들어갈 메뉴  -->
				<div class="mainmenu">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<!-- 코드그룹관리메뉴 -->
						<td width="120"><a href="/codegroup/list"><spring:message
									code="menu.codegroup.list" /></a></td>
						<!-- 코드관리메뉴 -->
						<td width="120"><a href="/codedetail/list"><spring:message
									code="menu.codedetail.list" /></a></td>
						<!-- 회원 관리 메뉴 -->
						<td width="120"><a href="/user/list"><spring:message
									code="menu.user.admin" /></a></td>
						<!-- 회원게시판리스트 -->
						<td width="120"><a href="/board/list?page=1&sizePerPage=10">회원게시판리스트</a></td>
						<td width="120"><a href="/notice/list"><spring:message
									code="menu.notice.member" /></a></td>
						<!-- 공지사항 리스트 -->
						<td width="120"><a href="/item/list"><spring:message
									code="menu.item.member" /></a></td>

					</sec:authorize>
				</div>

				<!-- 인증완료,(인가: 관리자)일때 들어가는 메뉴 -->
				<sec:authorize access="hasRole('ROLE_MEMBER')">
					<!-- 회원게시판리스트 -->
					<td width="120"><a href="/board/list?page=1&sizePerPage=10">회원게시판리스트</a></td>
					<td width="120"><a href="/notice/list"><spring:message
								code="menu.notice.member" /></a></td>
					<!-- 공지사항 리스트 -->
					<td width="120"><a href="/item/list"><spring:message
								code="menu.item.member" /></a></td>

					<!-- 코인 충전과 충전 내역을 메뉴에 추가한다. -->
					<td width="120"><a href="/coin/charge"><spring:message
								code="menu.coin.charge" /></a></td>
					<td width="120"><a href="/coin/list"><spring:message
								code="menu.coin.list" /></a></td>
					<!-- 구매 상품과 구매 내역을 메뉴에 추가한다. -->
					<td width="120"><a href="/useritem/list"><spring:message
								code="menu.useritem.list" /></a></td>
					<td width="120"><a href="/coin/listPay"><spring:message
								code="menu.coin.listPay" /></a></td>
				</sec:authorize>
			</sec:authorize>
		</tr>
	</table>
</div>
<hr>
