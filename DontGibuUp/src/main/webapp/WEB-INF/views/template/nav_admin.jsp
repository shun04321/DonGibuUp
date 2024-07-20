<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Admin 메뉴 시작 -->
<ul class="side-bar">
	<li class="side-menu"><a href="${pageContext.request.contextPath}/admin/manageMember">회원 관리</a></li>
	<li class="side-menu">챌린지 관리</li>
	<li class="side-menu">기부박스 관리</li>
	<li class="side-menu">굿즈 관리</li>
	<li class="side-menu">결제 관리</li>
	<li class="side-menu"><a href="${pageContext.request.contextPath}/category/categoryList">카테고리 관리</a></li>
	<li class="side-menu"><a href="${pageContext.request.contextPath}/admin/managePoint">포인트 관리</a></li>
	<li class="side-menu"><a href="${pageContext.request.contextPath}/admin/cs/faq">FAQ 관리</a></li>
	<li class="side-menu"><a href="${pageContext.request.contextPath}/admin/cs/inquiry">1:1문의 관리</a></li>
	<li class="side-menu"><a href="${pageContext.request.contextPath}/admin/cs/report">신고 관리</a></li>
	<li class="side-menu">통계</li>
</ul>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- Admin 메뉴 끝 -->