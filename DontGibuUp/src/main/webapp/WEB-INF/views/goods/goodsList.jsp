<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--상품 목록 출력-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

<div class="page-main">
	<h2>상품 목록</h2>
	<div>
		<c:if test="${!empty user}">
			<input type="button" value="상품 등록" onclick="location.href='write'">
		</c:if>
	</div>
	<c:if test="${count ==0}">
		<div class="result-display">표시할 상품이 없습니다.</div>
	</c:if>
	
</div>