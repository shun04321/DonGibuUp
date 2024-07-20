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

<div class="page-main">
	<h2>상품 목록</h2>
	<div>
		<a href="list">전체</a> <a href="list?dcate_num=1">노약자</a> | <a
			href="list?dcate_num=2">청소년</a> |
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
                    location.href = 'list?category=${param.category}&keyfield=' + $('#keyfield').val() + '&keyword=' + $('#keyword').val() + '&order=' + $('#order').val();
                });
            </script>

			<c:if
				test="${sessionScope.user != null && sessionScope.user.mem_status == 9}">
				<input type="button" value="상품 등록" onclick="location.href='write'">
			</c:if>
		</div>
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
					<th>카테고리</th>
					<th width="400">상품명</th>
					<th>가격</th>
					<th>재고</th>
					<c:if
						test="${sessionScope.user != null && sessionScope.user.mem_status == 9}">
						<th>관리자</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="goods" items="${list}">
					<c:if
						test="${sessionScope.user != null && sessionScope.user.mem_status == 9 || goods.item_status == 1}">
						<tr>
							<td class="align-center">${goods.item_num}</td>
							<td class="align-center"><img
								src="${pageContext.request.contextPath}${goods.item_photo}"
								class="my-photo" width="100px" height="100px"></td>
							<td class="align-center"><c:choose>
									<c:when test="${goods.dcate_num == 1}">
                        노약자
                    </c:when>
									<c:when test="${goods.dcate_num == 2}">
                        청소년
                    </c:when>
									<c:otherwise>
                        기타
                    </c:otherwise>
								</c:choose></td>
							<td class="align-left"><a
								href="detail?item_num=${goods.item_num}">${goods.item_name}</a></td>
							<td class="align-center">${goods.item_price}</td>
							<td class="align-center">${goods.item_stock}</td>
							<c:if
								test="${sessionScope.user != null && sessionScope.user.mem_status == 9}">
								<td class="align-center"><a
									href="${pageContext.request.contextPath}/goods/update?item_num=${goods.item_num}">상품정보
										변경</a>
									<form action="${pageContext.request.contextPath}/goods/delete"
										method="post" style="display: inline;">
										<input type="hidden" name="item_num" value="${goods.item_num}">
										<input type="submit" value="삭제"
											onclick="return confirm('정말로 삭제하시겠습니까?');">
									</form></td>
							</c:if>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>
