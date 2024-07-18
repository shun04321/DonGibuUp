<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div class="page-main">
	<h2>자주 하는 질문 (FAQ)</h2>
	<div>
		<a href="faq?">전체</a> |
		<a href="faq?status=0">정기기부</a> |
		<a href="faq?status=1">기부박스</a> |
		<a href="faq?status=2">챌린지</a> |
		<a href="faq?status=3">굿즈샵</a> |
		<a href="faq?status=4">기타</a>
	</div>
	<c:if test="${empty list}">
	<div class="result-display">등록된 질문이 없습니다.</div>
	</c:if>
	<c:if test="${!empty list}">

	<div class="align-center">${page}</div>
	</c:if>
</div>
