<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/subscriptionMain.css" type="text/css">
<style>
    .carousel-item img {
        width: 100%; /* 이미지의 너비를 100%로 설정하여 컨테이너에 맞추기 */
        height: 200px; /* 이미지의 높이를 일정하게 설정 */
        object-fit: cover; /* 이미지 비율을 유지하면서 컨테이너를 채우도록 조정 */
</style>
<div class="container">
    <!-- 캐러셀 시작 -->
    <div id="carouselExample2" class="carousel slide carousel-fade" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <!-- 캐러셀 인디케이터 동적 생성 -->
            <c:forEach var="category" items="${list}" varStatus="loop">
                <c:if test="${!empty category.dcate_banner}">
                    <button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="${loop.index}" class="${loop.index == 0 ? 'active' : ''}" aria-current="${loop.index == 0 ? 'true' : 'false'}" aria-label="Slide ${loop.index + 1}"></button>
                </c:if>
            </c:forEach>
        </div>
        <div class="carousel-inner">
            <!-- 캐러셀 아이템 동적 생성 -->
            <c:forEach var="category" items="${list}" varStatus="loop">
                <c:if test="${!empty category.dcate_banner}">
                    <div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
                        <a href="../category/categoryDetail?dcate_num=${category.dcate_num}">
                            <img src="${pageContext.request.contextPath}/upload/${category.dcate_banner}" class="d-block w-100" alt="Slide ${loop.index + 1}">
                        </a>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample2" data-bs-slide="prev">
            <span class="carousel-control-prev-icon"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExample2" data-bs-slide="next">
            <span class="carousel-control-next-icon"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <!-- 캐러셀 끝 -->
</div>
<section class="section-padding">
	<div class="container">
		<div class="row">
			<div class="col-lg-10 col-12 text-center mx-auto">
				<h2 class="mb-5">마음이 움직이는 주제를 선택하세요.</h2>
			</div>

			<div class="col-lg-3 col-md-6 col-12 mb-4 mb-lg-0">
				<div
					class="featured-block d-flex justify-content-center align-items-center">
					<a href="#" class="d-block"> <img
						src="${pageContext.request.contextPath}/t1/images/icons/hands.png"
						class="featured-block-image img-fluid" alt="">

						<p class="featured-block-text">
							Become a <strong>volunteer</strong>
						</p>
					</a>
				</div>
			</div>

			<div class="col-lg-3 col-md-6 col-12 mb-4 mb-lg-0 mb-md-4">
				<div
					class="featured-block d-flex justify-content-center align-items-center">
					<a href="#" class="d-block"> <img
						src="${pageContext.request.contextPath}/t1/images/icons/heart.png"
						class="featured-block-image img-fluid" alt="">

						<p class="featured-block-text">
							<strong>Caring</strong> Earth
						</p>
					</a>
				</div>
			</div>

			<div class="col-lg-3 col-md-6 col-12 mb-4 mb-lg-0 mb-md-4">
				<div
					class="featured-block d-flex justify-content-center align-items-center">
					<a href="#" class="d-block"> <img
						src="${pageContext.request.contextPath}/t1/images/icons/receive.png"
						class="featured-block-image img-fluid" alt="">

						<p class="featured-block-text">
							Make a <strong>Donation</strong>
						</p>
					</a>
				</div>
			</div>

			<div class="col-lg-3 col-md-6 col-12 mb-4 mb-lg-0">
				<div
					class="featured-block d-flex justify-content-center align-items-center">
					<a href="#" class="d-block"> <img
						src="${pageContext.request.contextPath}/t1/images/icons/scholarship.png"
						class="featured-block-image img-fluid" alt="">

						<p class="featured-block-text">
							<strong>Scholarship</strong> Program
						</p>
					</a>
				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</section>