<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

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
<!-- CSS FILES -->
    <link href="${pageContext.request.contextPath}/t1/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/t1/css/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/t1/css/templatemo-kind-heart-charity.css" rel="stylesheet">
    <style>
.product-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center; /* Center-align items */
    gap: 15px; /* 상품 간의 간격을 줄임 */
}

.product-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 10px;
    width: 23%; /* Adjusted width to make items fit in a row */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #fff;
    transition: transform 0.2s;
    margin: 10px; /* Added margin for better spacing */
}

.product-item img {
    width: 130px; /* 이미지 크기를 조금 더 키움 */
    height: 130px;
    border-radius: 50%;
    margin-bottom: 10px;
}

.product-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.product-details {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

.product-controls {
    margin-top: 10px;
}
.category-buttons {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-bottom: 20px;
}

.btn-category {
    display: inline-block;
    padding: 10px 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f5f5f5;
    text-decoration: none;
    color: #333;
    transition: background-color 0.3s, color 0.3s;
}

.btn-category:hover {
    background-color: #333;
    color: #fff;
}
</style>
<div class="page-main">
    <h2>상품 목록</h2>
    <div class="category-buttons">
        <a href="list" class="btn-category">전체</a>
        <c:forEach var="entry" items="${categories}">
            <a href="list?dcate_num=${entry.key}" class="btn-category">${entry.value}</a>
        </c:forEach>
    </div>
    <form action="list" id="search_form" method="get">
        <input type="hidden" name="dcate_num" value="${dcate_num}">
        <ul class="search">
            <li><select name="keyfield" id="keyfield">
                    <option value="1"
                        <c:if test="${param.keyfield == 1}">selected</c:if>>상품명</option>
            </select></li>
            <li><input type="search" name="keyword" id="keyword"
                value="${param.keyword}"></li>
            <li><input type="submit" value="찾기"></li>
        </ul>
        <div class="align-right">
            <script type="text/javascript">
                $('#order').change(function() {
                    location.href = 'list?category=${param.category}&keyfield=' + $('#keyfield').val() + '&keyword=' + $('#keyword').val() + '&order=' + $('#order').val());
                });
            </script>

            <c:if test="${sessionScope.user != null && sessionScope.user.mem_status == 9}">
                <input type="button" value="상품 등록" onclick="location.href='write'">
                <input type="button" value="구매 관리" onclick="location.href='${pageContext.request.contextPath}/admin/purchaseList'">
            </c:if>
            
            <c:if test="${sessionScope.user != null}">
                <input type="button" value="내 구매내역" onclick="location.href='purchaseHistory'">
            </c:if>
        </div>
    </form>
    <c:if test="${count == 0}">
        <div class="result-display">표시할 상품이 없습니다.</div>
    </c:if>
    <c:if test="${count > 0}">
        <div class="product-list">
            <c:forEach var="goods" items="${list}">
                <c:if test="${sessionScope.user != null && sessionScope.user.mem_status == 9 || goods.item_status == 1}">
                    <div class="product-item">
                        <img src="${pageContext.request.contextPath}${goods.item_photo}" alt="${goods.item_name}">
                        <div class="product-details">
                            <span>상품번호: ${goods.item_num}</span>
                            <span>카테고리: <c:out value="${categories[goods.dcate_num]}"/></span>
                            <span>상품명: <a href="detail?item_num=${goods.item_num}">${goods.item_name}</a></span>
                            <span>가격: ${goods.item_price}</span>
                            <span>재고: ${goods.item_stock}</span>
                        </div>
                        <c:if test="${sessionScope.user != null && sessionScope.user.mem_status == 9}">
                            <div class="product-controls">
                                <a href="${pageContext.request.contextPath}/goods/update?item_num=${goods.item_num}">상품정보 변경</a>
                                <form action="${pageContext.request.contextPath}/goods/delete" method="post" style="display: inline;">
                                    <input type="hidden" name="item_num" value="${goods.item_num}">
                                    <input type="submit" value="삭제" onclick="return confirm('정말로 삭제하시겠습니까?');">
                                </form>
                            </div>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </c:if>
</div>