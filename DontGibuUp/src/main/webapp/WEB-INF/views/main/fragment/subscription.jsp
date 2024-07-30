<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- section 내에 들어갈 내용을 div 내에 작성해주세요 --%>
<%-- section의 높이는 컨텐츠의 길이에 따라 자동 조정됩니다 --%>
<%-- 메인 페이지의 css 파일은 /DontGibuUp/src/main/resources/static/css/main.css 파일 내에 있습니다. --%>
<%-- css 설정이 필요하면 해당 파일 내의 적절한 섹션에서 수정해주세요 (git 충돌 주의) --%>
<link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;700&display=swap" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<style>
    .carousel-item img {
        width: 100%;
        height: 300px;
        object-fit: cover;
    }

    .title-style1 {
        font-size: 24px;
        font-family: 'Quicksand', sans-serif;
        text-align: center;
        color: white;
        background-color: black;
        padding: 10px 20px;
        border-radius: 50px; /* 알약 모양을 만들기 위한 둥근 테두리 */
        display: inline-block;
        margin: 20px auto;
        white-space: nowrap; /* 줄바꿈 방지 */
        font-weight: 700; /* 글자 굵기를 더 굵게 설정 */
    }

    .title-style1 span {
        display: inline-block;
        vertical-align: middle;
    }

    .title-style1 .highlight {
        color: yellow;
    }
</style>

<div class="title-style1">
    <span class="highlight">가장 쉬운</span>
    <span> 정기기부</span>
</div>

<!-- 캐러셀 시작 -->
<div id="carouselExample2" class="carousel slide carousel-fade" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="1" aria-label="Slide 2"></button>
         <button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="1" aria-label="Slide 3"></button>
          <button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="1" aria-label="Slide 4"></button>
    </div>
    <div class="carousel-inner">
        <div class="carousel-item active">
          	<div class="align-left">
          		
          	</div>
          	<div class="align-right"></div>
        </div>
        <div class="carousel-item">
            
        </div>
        <div class="carousel-item">
            
        </div>
        <div class="carousel-item">
            
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample2" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExample2" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>
<!-- 캐러셀 끝 -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
