<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 게시판 글 상세 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<script>
function addToCart() {
    document.getElementById('cartForm').submit();
}

function buyNow() {
    var quantity = document.getElementById('cart_quantity').value;
    alert(quantity + '개를 구매합니다.');
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

		<input type="button" value="구매하기" onclick="buyNow()">

		<form id="cartForm"
			action="${pageContext.request.contextPath}/cart/insert" method="post">
			<input type="hidden" name="item_num" value="${goods.item_num}">
			<input type="hidden" name="mem_num" value="${sessionScope.user.mem_num}"> 
			<label for="cart_quantity">수량:</label> 
			<input type="number" id="cart_quantity" name="cart_quantity" value="1" min="1"
			max="${goods.item_stock}"> <input type="submit" value="장바구니 담기">
		</form>
	</div>
	<hr size="1" width="100%">
</div>