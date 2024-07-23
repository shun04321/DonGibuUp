<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<section class="cta-section section-padding section-bg">
	<div class="container">
		<div class="row justify-content-center align-items-center">

			<div class="col-lg-5 col-12 ms-auto">
				<h2 class="mb-0">
					Make an impact. <br> Save lives.
				</h2>
			</div>

			<div class="col-lg-5 col-12">
				<a href="#" class="me-4">Make a donation</a> <a href="#section_4"
					class="custom-btn btn smoothscroll">Become a volunteer</a>
			</div>

		</div>
	</div>
</section>

<div class="page-main">
	<h2>관리자 - 구매 내역</h2>
	<div>
	    <input type="button" value="상품 목록" onclick="location.href='${pageContext.request.contextPath}/goods/list'">
	    <input type="button" value="홈" onclick="location.href='${pageContext.request.contextPath}/main/main'">
	</div>
	<c:if test="${not empty purchaseList}">
	    <table class="striped-table">
	        <thead>
	            <tr>
	                <th>주문번호</th>
	                <th>회원번호</th>
	                <th>상품번호</th>
	                <th>상품명</th>
	                <th>결제금액</th>
	                <th>구매일</th>
	                <th>결제 상태</th>
	                <th>배송 상태</th>
	                <th>상태 변경</th>
	            </tr>
	        </thead>
	        <tbody>
	            <c:forEach var="purchase" items="${purchaseList}">
	                <tr>
	                    <td class="align-center">${purchase.purchaseNum}</td>
	                    <td class="align-center">${purchase.memNum}</td>
	                    <td class="align-center">${purchase.item_num}</td>
	                    <td class="align-center">${purchase.item_name}</td>
	                    <td class="align-center">${purchase.amount}</td>
	                    <td class="align-center"><fmt:formatDate value="${purchase.payDate}" pattern="yyyy-MM-dd" /></td>
	                    <td class="align-center">
	                        <c:choose>
	                            <c:when test="${purchase.payStatus == 0}">
	                                결제완료
	                            </c:when>
	                            <c:when test="${purchase.payStatus == 2}">
	                                환불완료
	                            </c:when>
	                        </c:choose>
	                    </td>
	                    <td class="align-center">${purchase.deliveryStatus}</td>
	                    <td class="align-center">
	                        <c:if test="${purchase.payStatus == 0}">
	                            <form action="${pageContext.request.contextPath}/admin/updateDeliveryStatus" method="post">
	                                <input type="hidden" name="purchaseNum" value="${purchase.purchaseNum}">
	                                <select name="deliveryStatus">
	                                    <option value="배송 준비 중" <c:if test="${purchase.deliveryStatus == '배송 준비 중'}">selected</c:if>>배송 준비 중</option>
	                                    <option value="배송 시작" <c:if test="${purchase.deliveryStatus == '배송 시작'}">selected</c:if>>배송 시작</option>
	                                    <option value="배송 중" <c:if test="${purchase.deliveryStatus == '배송 중'}">selected</c:if>>배송 중</option>
	                                    <option value="배송 완료" <c:if test="${purchase.deliveryStatus == '배송 완료'}">selected</c:if>>배송 완료</option>
	                                </select>
	                                <button type="submit">변경</button>
	                            </form>
	                        </c:if>
	                    </td>
	                </tr>
	            </c:forEach>
	        </tbody>
	    </table>
	</c:if>
	<c:if test="${empty purchaseList}">
	    <p>구매 내역 없음</p>
	</c:if>
</div>
