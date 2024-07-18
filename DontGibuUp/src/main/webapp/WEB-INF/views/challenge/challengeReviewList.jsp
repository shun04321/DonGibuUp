<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 후기 목록</title>
</head>
<body>
<h2>챌린지 후기 목록</h2>
<div>
    <c:forEach var="review" items="${reviewList}">
        <div>
            <c:if test="${empty review.mem_photo}">
                <img src="${pageContext.request.contextPath}/images/basicProfile.png" alt="프로필 사진">
            </c:if>
            <c:if test="${!empty review.mem_photo}">
                <img src="${pageContext.request.contextPath}/upload/${review.mem_photo}" alt="프로필 사진">
            </c:if>
            <p><strong>${review.mem_nick}</strong></p>
            <p>${review.chal_rev_date}</p>
            <p>별점: ${review.chal_rev_grade}</p>
            <p>${review.chal_rev_content}</p>
        </div>
    </c:forEach>
</div>
</body>
</html>