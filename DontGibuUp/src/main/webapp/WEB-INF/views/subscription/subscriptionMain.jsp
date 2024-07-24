<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/subscriptionMain.css"
	type="text/css">
    <style>
        .carousel-item img {
            width: 100%;
            height: 300px;
            object-fit: cover;
        }

        #icon {
            width: 180px;
            height: 200px;
        }

        .accordion-section {
            max-width: 800px;
            margin: 20px;
        }

        .accordion-item {
            border-radius: 5px;
            margin-bottom: 10px;
            background-color: #grey;
        }

        .accordion-header {
            padding: 15px;
            padding-left : 0px;
            cursor: pointer;
            font-size: 1.5em; /* 제목 글자 크기 조정 */
            color: #000;
            background-color: #grey; 배경색 설정 */
            color: white; /* 글자색 설정 */
            transition: background-color 0.3s ease;
        }

        .accordion-header:hover {
            background-color: #grey; /* 마우스 오버 시 색상 변화 */
        }

        .accordion-body {
            padding: 15px;
            display: none;
            background-color: #ffffff; /* 본문 배경색 */
            border-top: 1px solid #ddd;
        }

        .accordion-body.open {
            display: block;
        }

        /* 추가적인 스타일 조정 */
        .align-right {
            float: right;
            font-size: 1.2em; /* 화살표 크기 조정 */
            line-height: 1em; /* 줄 높이 조정 */
        }
        .header-hr{
	       border: none; /* 기본 테두리 제거 */
	       height: 3px; /* 선의 두께 설정 */
	       background-color: black; /* 선의 색상 설정 */
	       margin: 20px 0; /* 상하 여백 설정 (선의 위와 아래 여백) */
	       width:100%;
        }
        
        .item-hr{
	        width:100%;
	        border: none; /* 기본 테두리 제거 */
	        height: 1px; /* 선의 두께 설정 */
	        background-color: black; /* 선의 색상 설정 */
	        margin: 20px 20px; /* 상하 여백 설정 (선의 위와 아래 여백) */
        }
    </style>
<div class="container">
	<!-- 캐러셀 시작 -->
	<div id="carouselExample2" class="carousel slide carousel-fade"
		data-bs-ride="carousel">
		<div class="carousel-indicators">
			<!-- 캐러셀 인디케이터 동적 생성 -->
			<c:forEach var="category" items="${list}" varStatus="loop">
				<c:if test="${!empty category.dcate_banner}">
					<button type="button" data-bs-target="#carouselExample2"
						data-bs-slide-to="${loop.index}"
						class="${loop.index == 0 ? 'active' : ''}"
						aria-current="${loop.index == 0 ? 'true' : 'false'}"
						aria-label="Slide ${loop.index + 1}"></button>
				</c:if>
			</c:forEach>
		</div>
		<div class="carousel-inner">
			<!-- 캐러셀 아이템 동적 생성 -->
			<c:forEach var="category" items="${list}" varStatus="loop">
				<c:if test="${!empty category.dcate_banner}">
					<div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
						<a
							href="../category/categoryDetail?dcate_num=${category.dcate_num}">
							<img
							src="${pageContext.request.contextPath}/upload/${category.dcate_banner}"
							class="d-block w-100" alt="Slide ${loop.index + 1}">
						</a>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<button class="carousel-control-prev" type="button"
			data-bs-target="#carouselExample2" data-bs-slide="prev">
			<span class="carousel-control-prev-icon"></span> <span
				class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button"
			data-bs-target="#carouselExample2" data-bs-slide="next">
			<span class="carousel-control-next-icon"></span> <span
				class="visually-hidden">Next</span>
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
			<c:forEach var="category" items="${list}" varStatus="loop">
				<div class="col-lg-3 col-md-6 col-12 mb-4 mb-lg-0">
					<div
						class="featured-block d-flex justify-content-center align-items-center">
						<a
							href="../category/categoryDetail?dcate_num=${category.dcate_num}">
							<img src="${pageContext.request.contextPath}/upload/${category.dcate_icon}"
							class="featured-block-image img-fluid" id="icon"
							alt="Slide ${loop.index + 1}">
							<p class="featured-block-text">
								<span class="dcate_name">${category.dcate_name}</span>
							</p>
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="container">
		<h2>
			<a href="/cs/faqlist?category=0">자주 묻는 질문</a>
		</h2>
		<hr class="header-hr">
		 <div class="container">
        <section class="accordion-section">
            <div class="accordion-item">
                <div class="accordion-header">정기 기부를 중단하고 싶어요<div class="align-right">▼</div></div>
                <div class="accordion-body">마이페이지 내 매달기부 메뉴를 선택하여 중단하고 싶은 주제를 선택하신 뒤, 해지하기를 진행해주세요.<br>해지하기 선택 시 다음 달 결제부터 기부금 결제가 중단됩니다.</div>
            </div>
            <hr class="item-hr">
            <div class="accordion-item">
                <div class="accordion-header">기부하고 싶은 주제가 없어요<div class="align-right">▼</div></div>
                <div class="accordion-body">매달기부에서는 기부자님들의 관심과 공감대를 바탕으로 다양한 상품을 지속적으로 발굴하려고 <br> 노력 중이에요.  돈기부업 채널을 추가하면 상품 추가 소식을 제일 먼저 알려드릴게요.</div>
            </div>
            <hr class="item-hr">
            <div class="accordion-item">
                <div class="accordion-header">기부하기 싫은데요?<div class="align-right">▼</div></div>
                <div class="accordion-body">진짜 어쩌라고</div>
            </div>
        </section>
    </div>
	</div>
	<script>
    document.addEventListener('DOMContentLoaded', function() {
        const headers = document.querySelectorAll('.accordion-header');

        headers.forEach(header => {
            header.addEventListener('click', function() {
                // Toggle the active class
                const body = this.nextElementSibling;
                
                // Slide up all other accordion bodies
                document.querySelectorAll('.accordion-body').forEach(b => {
                    if (b !== body) {
                        b.classList.remove('open');
                        b.style.maxHeight = null;
                    }
                });

                // Slide down the clicked accordion body
                if (body.classList.contains('open')) {
                    body.classList.remove('open');
                    body.style.maxHeight = null;
                    this.querySelector('.align-right').innerText = '▼';
                } else {
                    body.classList.add('open');
                    body.style.maxHeight = body.scrollHeight + 'px';
                    this.querySelector('.align-right').innerText = '▲';
                }
            });
        });
    });
</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</section>