<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="container mt-5mb-4" id="member_total_item">
    <div class="mb-3">
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
            <div class="total-content">${mem_point}</div>
        </div>
        <div class="total-menu col-12 col-sm-4 mb-3 mb-sm-0 border-right">
            <div class="total-label">총 기부횟수</div>
            <div class="total-content">${total_count}</div>
        </div>
        <div class="total-menu col-12 col-sm-4 border-right-last-none">
            <div class="total-label">누적 기부액</div>
            <div class="total-content">${total_amount}</div>
        </div>
    </div>
</section>