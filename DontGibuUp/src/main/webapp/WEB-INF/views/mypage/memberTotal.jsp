<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<section class="container mt-5 mb-4">
	<div id="member_total_item" class="mb-1">
	    <div class="mb-1">
	        <div class="">
	            <c:if test="${!empty user.mem_photo}">
	            <img src="${pageContext.request.contextPath}/upload/${user.mem_photo}" class="rounded-circle my-image">&nbsp;${user.mem_nick}님
	            </c:if>
	            <c:if test="${empty user.mem_photo}">
	            <img src="${pageContext.request.contextPath}/images/basicProfile.png" class="rounded-circle my-image">&nbsp;${user.mem_nick}님
	            </c:if>
	        </div>
	        <div>${mem_nick}</div>
	    </div>
	    <div class="row text-center d-flex">
	        <div class="total-menu col-12 col-sm-4 mb-3 mb-sm-0 border-right">
	            <div class="total-label">포인트</div>
	            <div class="total-content" id="mem_point">${memberTotal.mem_point}</div>
	        </div>
	        <div class="total-menu col-12 col-sm-4 mb-3 mb-sm-0 border-right">
	            <div class="total-label">총 기부횟수</div>
	            <div class="total-content" id="total_count">${memberTotal.total_count}</div>
	        </div>
	        <div class="total-menu col-12 col-sm-4 border-right-last-none">
	            <div class="total-label">누적 기부액</div>
	            <div class="total-content" id="total_amount">${memberTotal.total_amount}</div>
	        </div>
	    </div>
	</div>
	<p class="ms-2"><small>*총 기부 횟수와 누적 기부액은 정기 기부, 기부 박스, 챌린지, 굿즈샵을 통해 이루어진 모든 기부 활동을 종합하여 표시됩니다.</small></p>
</section>