<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div align="right">
	<table>
		<tr>
		<!-- 회원가입메뉴 -->
			<td width="80"><a href="/user/register"> <spring:message
						code="header.joinMember" /></a></td>
		<!-- 코드그룹관리메뉴 -->
			<td width="120"><a href="/codegroup/list"><spring:message
						code="menu.codegroup.list" /></a></td>
			<!-- 코드 관리메뉴 -->
			<td width="120"><a href="/codedetail/register"><spring:message
						code="menu.codedetail.list" /></a></td>
		</tr>
		</tr>
	</table>
</div>
<hr>
