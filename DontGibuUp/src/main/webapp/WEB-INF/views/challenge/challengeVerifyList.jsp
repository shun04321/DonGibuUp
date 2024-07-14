<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 인증내역</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
</head>
<body>
<h2>챌린지 인증내역</h2>
<div class="challenge-list">
    <c:forEach var="verify" items="${verifyList}">
        <div class="challenge-card">
            <p>인증일: ${verify.chal_reg_date}</p>
            <p>상태: 
                <c:choose>
                    <c:when test="${verify.chal_ver_status == 0}">인증 완료</c:when>
                    <c:when test="${verify.chal_ver_status == 1}">인증 실패</c:when>
                </c:choose>
            </p>
            <p>한줄평: ${verify.chal_content}</p>
            <img src="<c:url value='/images/${verify.chal_ver_photo}'/>" alt="인증 사진">
        </div>
    </c:forEach>
</div>
<div class="align-center">
    <button onclick="location.href='${pageContext.request.contextPath}/challenge/verify/write?chal_joi_num=${chal_joi_num}'">인증하기</button>
</div>
</body>
</html>