<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 상세</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
</head>
<body>
<h2>챌린지 상세</h2>
<br>
<div class="challenge-detail">
    <div class="challenge-header">
        <c:if test="${empty challenge.chal_photo}">
        		<img src="${pageContext.request.contextPath}/images/챌린지_기본이미지.jpg" alt="챌린지 사진">
        </c:if>
        <c:if test="${!empty challenge.chal_photo}">
        	<img src="${pageContext.request.contextPath}/upload/${challenge.chal_photo}" alt="챌린지 사진">
        </c:if>
        <h2 class="align-left">${challenge.chal_title}</h2>
    </div>
    <div class="challenge-info">
        <div class="author-info">
        	<c:if test="${empty challenge.mem_photo}">
        		<img src="${pageContext.request.contextPath}/images/basicProfile.png" alt="작성자 프사">
        	</c:if>
        	<c:if test="${!empty challenge.mem_photo}">
        		<img src="${pageContext.request.contextPath}/upload/${challenge.mem_photo}" alt="작성자 프사">
        	</c:if>
            <div class="details">
                <p><strong>${challenge.mem_nick}</strong></p>
            </div>
        </div>
        <c:choose>
            <c:when test="${isJoined}">
                <button disabled>참가중</button>
            </c:when>
            <c:otherwise>
                <button onclick="location.href='join/write?chal_num=${challenge.chal_num}'">참가하기</button>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="challenge-stats">
        <div>
            <span>인증 빈도</span>
            <c:if test="${challenge.chal_freq == 0}">
        		<p>매일</p>
        	</c:if>
        	<c:if test="${challenge.chal_freq != 0}">
        		<p>주 ${challenge.chal_freq}일</p>
        	</c:if>
        </div>
        <div>
            <span>기간</span>
            <p>${challenge.chal_sdate} ~ ${challenge.chal_edate}</p>
        </div>
        <div>
            <span>참여금</span>
            <p><span style="color: blue;">${challenge.chal_fee}</span>원</p>
        </div>
        <div>
            <span>모집 인원</span>
            <p><span style="color: red;">( )명</span> / ${challenge.chal_max}명</p>
        </div>
    </div>
    <div class="challenge-content">
        <h3>이런 분들께 추천합니다</h3>
        <p class="align-center">${challenge.chal_content}</p>
        <h3>이렇게 인증해주세요</h3>
        <p class="align-center">${challenge.chal_verify}</p>
    </div>
</div>
</body>
</html>