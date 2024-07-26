<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- 게시판 목록 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div class="page-main">
	<h2>기부 카테고리 및 기부처 목록</h2>
	<div class="align-right">
		<input type="button" value="기부 카테고리 및 기부처 등록" onclick="location.href='/category/insertCategory'">                 
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">표시할 게시물이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
	<table class="striped-table">
		<tr>
			<th>아이콘</th>
			<th>번호</th>
			<th>카테고리명</th>
			<th>기부처</th>
		</tr>
		<c:forEach var="dcate" items="${list}">
			<tr>
				<td class="align-center"><img src="${pageContext.request.contextPath}/upload/${dcate.dcate_icon}" alt="기부처 아이콘" width="30"></td>
				<td class="align-center">${dcate.dcate_num}</td>
				<td class="align-center"><a href="${pageContext.request.contextPath}/category/categoryDetail?dcate_num=${dcate.dcate_num}">${dcate.dcate_name}</a></td>
				<td class="align-center">${dcate.dcate_charity}</td>
			</tr>
		</c:forEach>
	</table>
	<div class="align-center">${page}</div>
	</c:if>	
</div>