<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 게시판 글 상세 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<script>
function addToCart() {
    var isLoggedIn = ${not empty sessionScope.user ? 'true' : 'false'};
    if (!isLoggedIn) {
        alert('로그아웃 상태입니다. 로그인 해주세요.');
        window.location.href = "${pageContext.request.contextPath}/member/login"; // 로그인 페이지로 리디렉션
    } else {
        document.getElementById('cartForm').submit();
    }
}

function buyNow() {
    var quantity = document.getElementById('cart_quantity').value;
    alert(quantity + '개를 구매합니다.');
    document.getElementById('purchaseForm').submit();
}
</script>
<div class="page-main">
    <h2>${goods.item_name}</h2>
    <ul class="detail-info">
        <li><img
            src="${pageContext.request.contextPath}${goods.item_photo}"
            width="300" height="300" class="my-photo2"></li>
        <li>재고: ${goods.item_stock}<br> 카테고리: ${goods.dcate_num}<br>
            가격: ${goods.item_price}<br> 수량: <input type="number"
            id="cart_quantity" name="cart_quantity" value="1" min="1"
            max="${goods.item_stock}">
        </li>
    </ul>
    <div class="detail-content">${goods.item_detail}</div>
    <div>
        <input type="button" value="목록" onclick="location.href='list'">

        <form id="purchaseForm" action="${pageContext.request.contextPath}/goods/purchase" method="post">
            <input type="hidden" name="merchantUid" value="${goods.item_num}_${System.currentTimeMillis()}">
            <input type="hidden" name="amount" value="${goods.item_price}">
            <input type="hidden" name="cardNumber" value="CARD_NUMBER_HERE"> <!-- 실제 카드번호 입력 -->
            <input type="hidden" name="expiry" value="EXPIRY_HERE"> <!-- 실제 만료일 입력 -->
            <input type="hidden" name="birth" value="BIRTH_HERE"> <!-- 실제 생년월일 입력 -->
            <input type="hidden" name="pwd2digit" value="PWD2_HERE"> <!-- 실제 카드 비밀번호 2자리 입력 -->
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