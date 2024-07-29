<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Added JSTL formatting library -->
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
<link href="${pageContext.request.contextPath}/t1/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/t1/css/bootstrap-icons.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/t1/css/templatemo-kind-heart-charity.css"
	rel="stylesheet">
<style>
.price {
    color: red;
}

.stock {
    color: blue;
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
                    location.href = 'list?category=${param.category}&keyfield=' + $('#keyfield'].val() + '&keyword=' + $('#keyword'].val() + '&order=' + $('#order'].val());
                });
            </script>

			<c:if
				test="${sessionScope.user != null && sessionScope.user.mem_status == 9}">
				<input type="button" value="상품 등록" onclick="location.href='write'">
				<input type="button" value="구매 관리"
					onclick="location.href='${pageContext.request.contextPath}/admin/purchaseList'">
			</c:if>

			<c:if test="${sessionScope.user != null}">
				<input type="button" value="내 구매내역"
					onclick="location.href='purchaseHistory'">
				<input type="button" value="장바구니"
					onclick="location.href='${pageContext.request.contextPath}/cart/list'">
			</c:if>
		</div>
	</form>
	<c:if test="${count == 0}">
		<div class="result-display">표시할 상품이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
		<div class="product-list">
			<c:forEach var="goods" items="${list}">
				<c:if
					test="${sessionScope.user != null && sessionScope.user.mem_status == 9 || goods.item_status == 1}">
					<div class="product-item">
						<img src="${pageContext.request.contextPath}${goods.item_photo}"
							alt="${goods.item_name}">
						<div class="product-details">
							<span>카테고리: <c:choose>
									<c:when test="${goods.dcate_num == 1}">독거노인기본생활 지원</c:when>
									<c:when test="${goods.dcate_num == 2}">청년 고립 극복 지원</c:when>
									<c:when test="${goods.dcate_num == 3}">유기동물 구조와 보호</c:when>
									<c:when test="${goods.dcate_num == 4}">미혼모(한부모가정)</c:when>
									<c:when test="${goods.dcate_num == 5}">해외 어린이 긴급구호</c:when>
									<c:when test="${goods.dcate_num == 6}">위기가정 아동지원</c:when>
									<c:when test="${goods.dcate_num == 7}">쓰레기 문제 해결</c:when>
									<c:when test="${goods.dcate_num == 8}">장애 어린이 재활 지원</c:when>
									<c:otherwise>알 수 없는 카테고리</c:otherwise>
								</c:choose>
							</span> <span class="product-name">상품명: <a
								href="detail?item_num=${goods.item_num}">${goods.item_name}</a></span>
							<span class="price">가격: <fmt:formatNumber value="${goods.item_price}"
									type="number" groupingUsed="true" /> 원
							</span> <span class="stock">재고: <fmt:formatNumber value="${goods.item_stock}"
									type="number" /> ea
							</span>
						</div>
						<c:if test="${sessionScope.user != null && sessionScope.user.mem_status == 9}">
    <div class="product-controls">
        <a href="${pageContext.request.contextPath}/goods/update?item_num=${goods.item_num}" class="default-btn btn-large">상품정보 변경</a>
        <form action="${pageContext.request.contextPath}/goods/delete" method="post" style="display: inline;">
            <input type="hidden" name="item_num" value="${goods.item_num}">
            <input type="submit" value="삭제" onclick="return confirm('정말로 삭제하시겠습니까?');" class="default-btn btn-large">
        </form>
    </div>
</c:if>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:if>
</div>
