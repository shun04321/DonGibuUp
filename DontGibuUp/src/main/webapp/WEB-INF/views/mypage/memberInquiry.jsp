<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div class="page-main">
	<h2>1:1문의</h2>
	<c:if test="${empty list}">
	<div class="result-display">표시할 게시물이 없습니다.</div>
	</c:if>
	<c:if test="${!empty list}">
	<table>
		<thead>
			<tr>
				<th>분류</th>
				<th>일자</th>
				<th>제목</th>
				<th>답변</th>
			</tr>
		</thead>
		<tbody>
			<!-- 데이터 행 추가 -->
			<c:forEach var="inquiry" items="${list}">
				<tr>
					<td><c:if
							test="${inquiry.inquiry_category == 0}">
               정기기부
               </c:if> <c:if
							test="${inquiry.inquiry_category == 1}">
               기부박스
               </c:if> <c:if
							test="${inquiry.inquiry_category == 2}">
               챌린지
               </c:if> <c:if
							test="${inquiry.inquiry_category == 3}">
               굿즈샵
               </c:if> <c:if
							test="${inquiry.inquiry_category == 4}">
               기타
               </c:if></td>
					<td>${inquiry.inquiry_date}</td>
					<td>${inquiry.inquiry_title}</td>
					<td><c:if test="${!empty inquiry.inquiry_reply}">
					답변확인
					</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
</div>