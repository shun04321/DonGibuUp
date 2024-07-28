<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 회원 관리 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/subscription/admin.subscription.js"></script>
<div class="page-main">
	<h2>환불신청 관리</h2>
	<div class="container">
		<div class="mb-4">
		</div>
	<div class="mb-3">
		<a href="refundRequest?">전체</a> |
		<a href="refundRequest?refund_status=0">처리중</a> |
		<a href="refundRequest?refund_status=1">승인</a> |
		<a href="refundRequest?refund_status=2">반려</a>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">표시할 환불신청이 없습니다</div>
	</c:if>
	<c:if test="${count > 0}">
	<div>총 ${count} 건의 레코드</div>
	<table class="striped-table">
		<tr>
			<th class="align-center">환불신청번호</th>
			<th class="align-center">유저번호</th>
			<th class="align-center">구분</th>
			<th class="align-center">결제액</th>
			<th class="align-center">환불사유</th> 
			<th class="align-center">신청일</th>
			<th class="align-center">환불일</th>
			<th class="align-center">환불상태</th>
		</tr>
		<c:forEach var="refund" items="${list}">
		<tr class="mem-item">
			<td class="align-center">${refund.refund_num}</td>
			<td class="align-center"><a href="detail?mem_num=${refund.mem_num}">${refund.mem_num}</a></td>
			<td class="align-center">
				<c:if test="${refund.payment_type == 0}">굿즈샵</c:if>
				<c:if test="${refund.payment_type == 1}">정기기부</c:if>
				<c:if test="${refund.payment_type == 2}">기부박스</c:if>			
			</td>
			<td class="align-center"><fmt:formatNumber value="${refund.amount}" type="number" pattern="#,##0"/>원</td>
			<td class="align-center">
				<c:choose>
					<c:when test="${refund.reason==0}">단순변심</c:when>
					<c:when test="${refund.reason==1}">결제오류</c:when>
					<c:when test="${refund.reason==2}">기타</c:when>
				</c:choose>
			</td>
			<td class="align-center">${refund.reg_date}</td>
			<td class="align-center" class="mem-'dstatus">
				<c:if test="${empty refund.refund_date}">--</c:if>
				<c:if test="${!empty refund.refund_date}">${refund.refund_date}</c:if>	
			</td>
			<td class="align-center">
				<c:choose>
					<c:when test="${refund.refund_status==0}">
						<input type="button" value="승인">
						<input type="button" value="반려">
					</c:when>
					<c:when test="${refund.refund_status==1}">승인</c:when>
					<c:when test="${refund.refund_status==2}">반려</c:when>
				</c:choose>
			</td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${page}</div>
	</c:if>
	</div>
</div>
<!-- 회원 목록 끝 -->