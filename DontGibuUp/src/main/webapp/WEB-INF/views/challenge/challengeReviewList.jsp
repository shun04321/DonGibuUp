<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 후기 목록</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
</head>
<body>
<div class="review-list-container">
    <h2>[ ${challenge.chal_title} ] 후기</h2>
    <hr>
    <br>
    <hr>
    <div class="review-list">
        <c:forEach var="review" items="${reviewList}">
            <div class="review-item">
                <img src="${pageContext.request.contextPath}/upload/${review.mem_photo}" alt="프로필 사진" class="profile-img">
                <div class="review-content">
                    <div class="review-header">
                        <span class="nickname">${review.mem_nick}</span>
                        <span class="date">${review.chal_rev_date}</span>
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
                    <div class="review-text">${review.chal_rev_content}</div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>