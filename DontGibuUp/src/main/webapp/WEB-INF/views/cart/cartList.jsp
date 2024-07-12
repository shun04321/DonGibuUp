<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상품 목록 출력 -->
<script type="text/javascript"
    src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

<div class="page-main">
    <h2>장바구니</h2>
    <form action="list" id="search_form" method="get">
        <input type="hidden" name="dcate_num" value="${param.dcate_num}">
    </form>
    <c:if test="${count == 0}">
        <div class="result-display">표시할 상품이 없습니다.</div>
    </c:if>
    <c:if test="${count > 0}">
    <table class="striped-table">
        <thead>
            <tr>
                <th>상품번호</th>
                <th>사진</th>
                <th>장바구니번호</th>
                <th width="400">상품명</th>
                <th>가격</th>
                <th>재고</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cart" items="${list}">
                <tr>
                    <td class="align-center">${cart.goods.item_num}</td>
                    <td class="align-center">
                        <img src="${pageContext.request.contextPath}${cart.goods.item_photo}" class="my-photo" width="100px" height="100px">
                    </td>
                    <td class="align-center">${cart.cart_num}</td>
                    <td class="align-left">
                        <a href="detail?item_num=${cart.goods.item_num}">${cart.goods.item_name}</a>
                    </td>
                    <td class="align-center">${cart.goods.item_price}</td>
                    <td class="align-center">${cart.goods.item_stock}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
 	  <div align="center">${page}</div>
    </c:if>
</div>