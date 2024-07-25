<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- CSS FILES -->
    <link href="${pageContext.request.contextPath}/t1/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/t1/css/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/t1/css/templatemo-kind-heart-charity.css" rel="stylesheet">

 <section class="cta-section section-padding section-bg">
    <div class="container">
        <div class="row justify-content-center align-items-center">
            <div class="col-lg-5 col-12 ms-auto">
                <h2 class="mb-0">
                    Make an impact. <br> Save lives.
                </h2>
            </div>
            <div class="col-lg-5 col-12">
                <a href="#" class="me-4">Make a donation</a> <a href="#section_4" class="custom-btn btn smoothscroll">Become a volunteer</a>
            </div>
        </div>
    </div>
</section>
<div>


     <h2>관리자 - 구매내역</h2>
    <div>
        <input type="button" value="상품 목록" onclick="location.href='${pageContext.request.contextPath}/goods/list'">
        <input type="button" value="홈" onclick="location.href='${pageContext.request.contextPath}/main/main'">
    </div>
    <c:if test="${not empty purchaseList}">
        <table border="1">
            <thead>
                <tr>
                    <th>회원번호</th>
                    <th>상품명</th>
                    <th>사진</th>
                    <th>총액</th>
                    <th>구매일</th>
                    <th>결제 상태</th>
                    <th>배송 상태</th>
                    <th>상태 변경</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="purchase" items="${purchaseList}">
                    <tr>
                        <td class="align-center">${purchase.mem_num}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty purchase.item_name}">
                                    ${purchase.item_name}
                                </c:when>
                                <c:otherwise>
                                    장바구니 구매
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty purchase.item_photo}">
                                    <img src="${pageContext.request.contextPath}${purchase.item_photo}" alt="${purchase.item_name}" width="100" height="100">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/cart.png" alt="장바구니 구매" width="100" height="100">
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${purchase.amount}</td>
                        <td class="align-center"><fmt:formatDate value="${purchase.payDate}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${purchase.payStatus == 0}">
                                    결제완료
                                </c:when>
                                <c:when test="${purchase.payStatus == 2}">
                                    환불완료
                                </c:when>
                            </c:choose>
                        </td>
                        <td>${purchase.deliveryStatus}</td>
                        <td class="align-center">
                            <c:if test="${purchase.payStatus == 0}">
                                <form action="${pageContext.request.contextPath}/admin/updateDeliveryStatus" method="post">
                                    <input type="hidden" name="imp_uid" value="${purchase.imp_uid}">
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
                    <!-- 장바구니 구매 항목 표시 -->
                    <c:if test="${empty purchase.item_num}">
                        <c:forEach var="item" items="${purchase.cartItems}">
                            <tr>
                                <td></td>
                                <td>${item.item_name}</td>
                                <td><img src="${pageContext.request.contextPath}${item.item_photo}" alt="${item.item_name}" width="50" height="50"></td>
                                <td>${item.item_price * item.cart_quantity}</td>
                                <td>${item.cart_quantity}</td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<c:if test="${empty purchaseList}">
    <p>구매 내역 없음</p>
</c:if>