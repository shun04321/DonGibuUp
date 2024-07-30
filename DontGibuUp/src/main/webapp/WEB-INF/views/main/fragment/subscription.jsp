<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- section 내에 들어갈 내용을 div 내에 작성해주세요 --%>
<%-- section의 높이는 컨텐츠의 길이에 따라 자동 조정됩니다 --%>
<%-- 메인 페이지의 css 파일은 /DontGibuUp/src/main/resources/static/css/main.css 파일 내에 있습니다. --%>
<%-- css 설정이 필요하면 해당 파일 내의 적절한 섹션에서 수정해주세요 (git 충돌 주의) --%>
<link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;700&display=swap" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<style>
	.carousel-item img {
	width: 100%;
	height: 300px;
	object-fit: cover;
}
</style>
    <div class="title-style">나의 도움이 필요한 기부처는?</div>
	<!-- 캐러셀 시작 -->
	<div id="carouselExample2" class="carousel slide carousel-fade"
		data-bs-ride="carousel">
		<div class="carousel-indicators">
					<button type="button" data-bs-target="#carouselExample2"
						data-bs-slide-to="${loop.index}"
						class="${loop.index == 0 ? 'active' : ''}"
						aria-current="${loop.index == 0 ? 'true' : 'false'}"
						aria-label="Slide ${loop.index + 1}"></button>
		</div>
		<div class="carousel-inner">
			<div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
					<img
					src="${pageContext.request.contextPath}/upload/${category.dcate_banner}"
					class="d-block w-100" alt="Slide ${loop.index + 1}">
			</div>
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
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>