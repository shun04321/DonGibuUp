<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script>
    let itemName = "${goods.item_name}";
    let itemPrice = "${goods.item_price}";
    let buyerName = "${sessionScope.user.mem_nick}";
    let itemNum = "${goods.item_num}";
    let pageContextPath = "${pageContext.request.contextPath}";  
    
    function addToCart() {
        var form = document.getElementById('cartForm');
        form.submit();
    }
</script>

<div class="page-main">
    <h2>${goods.item_name}</h2>
    <ul class="detail-info">
        <li><img src="${pageContext.request.contextPath}${goods.item_photo}" width="300" height="300" class="my-photo2"></li>
        <li>재고: ${goods.item_stock}<br> 카테고리: ${goods.dcate_num}<br> 가격: ${goods.item_price}<br> 수량: <input type="number" id="cart_quantity" name="cart_quantity" value="1" min="1" max="${goods.item_stock}"></li>
    </ul>
    <div class="detail-content">${goods.item_detail}</div>
    <div>
        <input type="button" value="목록" onclick="location.href='list'">

        <form id="purchaseForm" method="post">
            <input type="hidden" name="merchantUid" value="${goods.item_num}_${System.currentTimeMillis()}">
            <input type="hidden" name="amount" value="${goods.item_price}">
            <input type="button" value="구매하기" onclick="buyNow()">
        </form>

        <form id="cartForm" action="${pageContext.request.contextPath}/cart/insert" method="post">
            <input type="hidden" name="item_num" value="${goods.item_num}">
            <c:if test="${not empty sessionScope.user}">
                <input type="hidden" name="mem_num" value="${sessionScope.user.mem_num}">
            </c:if>
            <label for="cart_quantity">수량:</label>
            <input type="number" id="cart_quantity" name="cart_quantity" value="1" min="1" max="${goods.item_stock}">
            <input type="button" value="장바구니 담기" onclick="addToCart()">
        </form>
    </div>
    <hr size="1" width="100%">
</div>
<script src="${pageContext.request.contextPath}/js/goods/purchase.js"></script>
