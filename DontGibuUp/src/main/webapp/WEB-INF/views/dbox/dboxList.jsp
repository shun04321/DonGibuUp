<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 목록 시작 -->
<script src="${pageContext.request.contextPath}/js/dbox/dbox.list.js"></script>

<section class="section-padding">
	<div class="container">
		<div class="row">

			<div class="col-lg-12 col-12 text-center mb-4">
				<h2>Our Causes</h2>
			</div>

			<div class="col-lg-4 col-md-6 col-12 mb-4 mb-lg-0">
				<div class="custom-block-wrap">
					<img
						src="${pageContext.request.contextPath}/t1/images/causes/group-african-kids-paying-attention-class.jpg"
						class="custom-block-image img-fluid" alt="">

					<div class="custom-block">
						<div class="custom-block-body">
							<h5 class="mb-3">Children Education</h5>

							<p>Lorem Ipsum dolor sit amet, consectetur adipsicing kengan
								omeg kohm tokito</p>

							<div class="progress mt-4">
								<div class="progress-bar w-75" role="progressbar"
									aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
							</div>

							<div class="d-flex align-items-center my-2">
								<p class="mb-0">
									<strong>Raised:</strong> $18,500
								</p>

								<p class="ms-auto mb-0">
									<strong>Goal:</strong> $32,000
								</p>
							</div>
						</div>

						<a href="#" class="custom-btn btn">Donate now</a>
					</div>
				</div>
			</div>

			<div class="col-lg-4 col-md-6 col-12 mb-4 mb-lg-0">
				<div class="custom-block-wrap">
					<img
						src="${pageContext.request.contextPath}/t1/images/causes/poor-child-landfill-looks-forward-with-hope.jpg"
						class="custom-block-image img-fluid" alt="">

					<div class="custom-block">
						<div class="custom-block-body">
							<h5 class="mb-3">Poverty Development</h5>

							<p>Sed leo nisl, posuere at molestie ac, suscipit auctor
								mauris. Etiam quis metus tempor</p>

							<div class="progress mt-4">
								<div class="progress-bar w-50" role="progressbar"
									aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
							</div>

							<div class="d-flex align-items-center my-2">
								<p class="mb-0">
									<strong>Raised:</strong> $27,600
								</p>

								<p class="ms-auto mb-0">
									<strong>Goal:</strong> $60,000
								</p>
							</div>
						</div>

						<a href="#" class="custom-btn btn">Donate now</a>
					</div>
				</div>
			</div>

			<div class="col-lg-4 col-md-6 col-12">
				<div class="custom-block-wrap">
					<img
						src="${pageContext.request.contextPath}/t1/images/causes/african-woman-pouring-water-recipient-outdoors.jpg"
						class="custom-block-image img-fluid" alt="">

					<div class="custom-block">
						<div class="custom-block-body">
							<h5 class="mb-3">Supply drinking water</h5>

							<p>Orci varius natoque penatibus et magnis dis parturient
								montes, nascetur ridiculus</p>

							<div class="progress mt-4">
								<div class="progress-bar w-100" role="progressbar"
									aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
							</div>

							<div class="d-flex align-items-center my-2">
								<p class="mb-0">
									<strong>Raised:</strong> $84,600
								</p>

								<p class="ms-auto mb-0">
									<strong>Goal:</strong> $100,000
								</p>
							</div>
						</div>

						<a href="#" class="custom-btn btn">Donate now</a>
					</div>
				</div>
			</div>

		</div>
	</div>
</section>

<div class="page-main">
	<%-- 카테고리 --%>
	<div id="category_output" class="align-center"></div>
	<%-- 기부박스 제안하기 버튼 --%>
	<div class="align-right">
		<button type="button" class="btn btn-dark"
			onclick="location.href='${pageContext.request.contextPath}/dbox/propose'">기부박스
			제안하기</button>
	</div>
	<%-- 검색 --%>
	<form action="dboxList" id="search_form" method="get">
		<ul class="search">
			<li><select name="keyfield" id="keyfield">
					<option value="1"
						<c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
					<option value="2"
						<c:if test="${param.keyfield == 2}">selected</c:if>>팀명</option>
					<option value="3"
						<c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
					<option value="4"
						<c:if test="${param.keyfield == 4}">selected</c:if>>제목+내용</option>
			</select></li>
			<li><input type="search" name="keyword" id="keyword"
				value="${param.keyword}"></li>
			<li><input type="submit" value="찾기"></li>
		</ul>
		<div class="align-right">
			<select id="order" name="order">
				<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순</option>
				<option value="2" <c:if test="${param.order == 2}">selected</c:if>>등록순</option>
			</select>
			<script type="text/javascript">
				$('#order').change(function(){
					location.href='list?category=${param.categoty}'
									 +'&keyfield='+$('#keyfield').val()
									 +'&keyword='+$('#keyword').val()
									 +'&order='+$('#order').val();
				});
			</script>
		</div>
	</form>

	<br>
	<%-- 목록반복 --%>
	<div class="row row-cols-1 row-cols-md-4 g-3" id="output"></div>
	<div id="loading" style="display: none;">
		<img src="${pageContext.request.contextPath}/images/loading.gif"
			width="30" height="30">
	</div>

	<div class="paging-button" style="display: none;">
		<input type="button" value="더보기">
	</div>
</div>
<!-- 목록 끝 -->