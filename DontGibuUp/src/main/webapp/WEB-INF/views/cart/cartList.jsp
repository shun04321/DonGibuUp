<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상품 목록 출력 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/goods/cart.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>

<div id="contextPath" data-context-path="${pageContext.request.contextPath}"></div>

<script type="text/javascript">
function deleteSelectedCarts() {
    var selectedCarts = [];
    $('input[name="cart_num"]:checked').each(function() {
        selectedCarts.push($(this).val());
    });

    if (selectedCarts.length === 0) {
        alert('삭제할 항목을 선택해주세요.');
        return;
    }

    $.ajax({
        url: $('#contextPath').data('context-path') + '/cart/deleteSelected',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(selectedCarts),
        success: function(response) {
            if(response === 'success') {
                alert('선택된 항목들이 장바구니에서 삭제되었습니다.');
                location.reload();
            } else {
                alert('삭제 실패. 다시 시도해주세요.');
            }
        },
        error: function() {
            alert('에러가 발생했습니다. 다시 시도해주세요.');
        }
    });
}

function updateCartQuantity(cart_num) {
    var newQuantity = $('#cart_quantity_' + cart_num).val();

    $.ajax({
        url: $('#contextPath').data('context-path') + '/cart/updateQuantity',
        type: 'POST',
        data: {
            cart_num: cart_num,
            cart_quantity: newQuantity
        },
        success: function(response) {
            if (response === 'success') {
                alert('수량이 업데이트되었습니다.');
                location.reload();
            } else {
                alert('업데이트 실패. 다시 시도해주세요.');
            }
        },
        error: function() {
            alert('에러가 발생했습니다. 다시 시도해주세요.');
        }
    });
}
</script>

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
            <th>선택</th>
            <th>사진</th>
            <th>장바구니번호</th>
            <th width="400">상품명</th>
            <th>가격</th>
            <th>재고</th>
            <th>담은 수량</th>
            <th>수량 업데이트</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="cart" items="${list}">
            <tr>
                <td class="align-center">
                    <input type="checkbox" name="cart_num" value="${cart.cart_num}">
                </td>
                <td class="align-center">
                    <img src="${pageContext.request.contextPath}${cart.goods.item_photo}" class="my-photo" width="100px" height="100px">
                </td>
                <td class="align-center">${cart.cart_num}</td>
                <td class="align-left">
                    <a href="detail?item_num=${cart.goods.item_num}">${cart.goods.item_name}</a>
                </td>
                <td class="align-center">
                    <span id="price_${cart.cart_num}">${cart.goods.item_price}</span>
                </td>
                <td class="align-center">${cart.goods.item_stock}</td>
                <td class="align-center">
                    <input type="number" id="cart_quantity_${cart.cart_num}" value="${cart.cart_quantity}" min="1">
                </td>
                <td class="align-center">
                    <button type="button" onclick="updateCartQuantity(${cart.cart_num})">수량 변경</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<div align="center">
    <button type="button" onclick="deleteSelectedCarts()">선택 항목 삭제</button>
    <button type="button" id="purchaseButton">선택 항목 구매</button>
</div>
<div align="center">${page}</div>
</c:if>
</div>
