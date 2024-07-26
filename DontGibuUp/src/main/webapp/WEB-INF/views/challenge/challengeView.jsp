<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 현재 날짜를 캡처 --%>
<%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String currentDate = sdf.format(new java.util.Date());
    request.setAttribute("currentDate", currentDate);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 상세</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script>
        var contextPath = '<%= request.getContextPath() %>';
    </script>
    <script src="${pageContext.request.contextPath}/js/challenge/challenge.fav.js"></script>
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
        <div class="challenge-info2">
        <div class="author-info">
        	<h2 class="align-left">${challenge.chal_title}</h2>
        </div>
        	<div>
        		<button id="likeBtn" data-num="${challenge.chal_num}">♡</button>
        		<span id="output_fcount"></span>
        	</div>
        </div>
    </div>
    <div class="challenge-info2">
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
            <c:when test="${currentDate > challenge.chal_edate}">
                <button disabled>챌린지 마감</button>
            </c:when>
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
            <p><span style="color: blue;">${formattedFee}</span>원</p>
        </div>
        <div>
            <span>모집 인원</span>
            <p><span style="color: red;">${currentParticipants}명</span> / ${challenge.chal_max}명</p>
        </div>
    </div>
    <c:if test="${reviewCount > 0}">
    <hr>
    <!-- 챌린지 후기 -->
    <div class="review-summary">
        <h3>참가자 후기</h3>
        <span class="rating-stars">★</span>
        <span class="rating-value">${averageRating}</span>
        <span class="review-count">(${reviewCount}개)</span>
        <div class="btn-all-reviews">
            <button onclick="location.href='${pageContext.request.contextPath}/challenge/review/list?chal_num=${challenge.chal_num}'">모두보기</button>
        </div>
    </div>
    <div class="review-container">
        <c:forEach var="review" items="${reviewList}" begin="0" end="2">
            <div class="review-item">
                <div class="review-content">
                    <div class="review-header">
                    <span class="rating">
                            <c:forEach begin="1" end="5" varStatus="status">
                                <c:choose>
                                    <c:when test="${status.index <= review.chal_rev_grade}">
                                        ★
                                    </c:when>
                                    <c:otherwise>
                                        ☆
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </span>
                    </div>
                    <div>
                    	<span class="nickname">${review.mem_nick}</span>
                    	<span class="date">${review.chal_rev_date}</span>      
                    </div>           
                     <div class="review-text">
                        <c:choose>
                            <c:when test="${fn:length(review.chal_rev_content) > 12}">
                                ${fn:substring(review.chal_rev_content, 0, 13)}..
                            </c:when>
                            <c:otherwise>
                                ${review.chal_rev_content}
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <hr>
    </c:if>
    <!-- 챌린지 상세 내용 -->
    <c:if test="${not empty challenge.chal_content}">
        <div class="challenge-content">
            <h3>이런 분들께 추천합니다</h3>
            <p class="align-center">${challenge.chal_content}</p>
        </div>
    </c:if>
    <div class="challenge-content">
        <h3>이렇게 인증해주세요</h3>
        <p class="align-center">${challenge.chal_verify}</p>
    </div>
    <div class="align-right" onclick="location.href='/cs/report?report_source=1&chal_num=${challenge.chal_num}&reported_mem_num=${challenge.mem_num}'" style="cursor:pointer">
    	🚨신고하기
    </div>
</div>
</body>
</html>