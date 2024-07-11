<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--상품 목록 출력-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

<div class="page-main">
	<h2>상품 목록</h2>
	<div>
		<a href="list">전체</a> <a href="list?category=1">자바</a> | <a
			href="list?category=2">데이터베이스</a> | <a href="list?category=3">자바스크립트</a>
		| <a href="list?category=4">기타</a> |
	</div>
	<form action="list" id="search_form" method="get">
		<input type="hidden" name="category" value="${param.category}">
		<ul class="search">
			<li><select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">select</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield == 2}">select</c:if>>ID
						+ 별명</option>
					<option value="3" <c:if test="${param.keyfield == 3}">select</c:if>>내용</option>
					<option value="4" <c:if test="${param.keyfield == 4}">select</c:if>>제목+
						내용</option>
			</select></li>
			<li><input type="search" name="keyword" id="keyword"
				value="${param.keyword}"></li>
			<li><input type="submit" value="찾기"></li>
		</ul>
		<div class="align-right">
			<select id="order" name="order">
				<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순</option>
				<option value="2" <c:if test="${param.order == 2}">selected</c:if>>조회수</option>
				<option value="3" <c:if test="${param.order == 3}">selected</c:if>>좋아요</option>
				<option value="4" <c:if test="${param.order == 4}">selected</c:if>>댓글수</option>
			</select>
			<script type="text/javascript">
				$('#order')
						.change(
								function() {
									location.href = 'list?category=${param.category}&keyfield='
											+ $('#keyfield').val()
											+ '&keyword='
											+ $('#keyword').val()
											+ '&order=' + $('#order').val();
								});
			</script>
			<c:if test="${!empty user}">
				<input type="button" value="상품 등록" onclick="location.href='write'">
			</c:if>
		</div>
	</form>
	<c:if test="${count ==0}">
		<div class="result-display">표시할 상품이 없습니다.</div>
	</c:if>
	<form id="goodsForm" action="/add-to-cart" method="post">
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
        </tr>
    </thead>
    <tbody>
            <c:forEach var="goods" items="${list}">
                    <tr>
                        <td class="align-center">${goods.item_num}</td>
                        <td class="align-center">
                            <img src="${pageContext.request.contextPath}${goods.item_photo}" class="my-photo" width="100px" height="100px">
                        </td>
                        <td class="align-center">${goods.dcate_num}</td>
                        <td class="align-left">
                            <a href="detail?item_num=${goods.item_num}">${goods.item_name}</a>
                        </td>
                        <td class="align-center">${goods.item_price}</td>
                        <td class="align-center">${goods.item_stock}</td>
                    </tr>
             </c:forEach>
	</tbody>
	</table>
  
  </c:if>
  </form>

	
</div>