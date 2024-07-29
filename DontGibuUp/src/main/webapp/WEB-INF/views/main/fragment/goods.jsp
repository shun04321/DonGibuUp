<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- section 내에 들어갈 내용을 div 내에 작성해주세요 --%>
<%-- section의 높이는 컨텐츠의 길이에 따라 자동 조정됩니다 --%>
<%-- 메인 페이지의 css 파일은 /DontGibuUp/src/main/resources/static/css/main.css 파일 내에 있습니다. --%>
<%-- css 설정이 필요하면 해당 파일 내의 적절한 섹션에서 수정해주세요 (git 충돌 주의) --%>
<!-- 최신 상품 섹션 시작 -->
<div id="latestGoods" class="container mt-5">
    <h5 class="mt-5 mb-3">최신 상품</h5>
    <div class="goods-item">
        <div class="card">
            <img src="${pageContext.request.contextPath}${todayGoods.item_photo}" class="card-img-top" alt="${todayGoods.item_name}">
            <div class="card-body">
                <p class="card-category">카테고리: ${todayGoods.dcate_num}</p>
                <h5 class="card-title">${todayGoods.item_name}</h5>
                <p class="card-price">가격: ${todayGoods.item_price}원</p>
                <p class="card-stock">재고: ${todayGoods.item_stock}ea</p>
                <p class="card-status">상태: ${todayGoods.item_status}</p>
            </div>
        </div>
    </div>
</div>
<!-- 최신 상품 섹션 끝 -->
<style>
#latestGoods {
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 8px;
    text-align: center; /* 중앙 정렬 */
}

#latestGoods h5 {
    color: #17a2b8;
    font-weight: bold;
    margin-bottom: 20px;
}

.goods-item {
    text-align: center;
    display: inline-block; /* 인라인 블록으로 변경 */
    width: 100%;
    margin: 0;
}

.goods-item .card {
    border: 1px solid #ddd;
    border-radius: 8px;
    max-width: 300px;
    margin: 0 auto;
    padding: 0;
}

.goods-item .card-img-top {
    width: 100%;
    height: auto;
    object-fit: cover;
    margin: 0;
    padding: 0;
}

.goods-item .card-body {
    padding: 10px;
    text-align: left; /* 왼쪽 정렬 */
}

.goods-item .card-category,
.goods-item .card-title,
.goods-item .card-price,
.goods-item .card-stock,
.goods-item .card-status {
    font-size: 1rem;
    color: #555;
    margin: 5px 0;
}

.goods-item .card-title {
    font-size: 1.25rem;
    font-weight: bold;
    color: #333;
}
</style>
