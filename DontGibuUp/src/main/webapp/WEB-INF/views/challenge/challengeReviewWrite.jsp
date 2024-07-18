<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 후기 작성</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/WEB-INF/views/challenge/challengeReview.css">
</head>
<body>
<div class="review-container">
    <div class="challenge-info">
        <img src="${pageContext.request.contextPath}/upload/${challenge.chal_photo}" alt="챌린지 썸네일" class="challenge-thumbnail">
        <div class="challenge-details">
            <h2>${challenge.chal_title}</h2>
            <p>주 ${challenge.chal_freq}일</p>
            <p>${challenge.chal_sdate} ~ ${challenge.chal_edate}</p>
        </div>
    </div>
    <form:form action="${pageContext.request.contextPath}/challenge/review/write" method="post" modelAttribute="challengeReviewVO">
        <form:hidden path="chal_num" value="${chal_num}"/>
        <div class="review-rating">
            <label for="chal_rev_grade">챌린지에 만족하셨나요?</label>
            <div class="stars">
                <form:radiobutton path="chal_rev_grade" value="1" class="star" />★
                <form:radiobutton path="chal_rev_grade" value="2" class="star" />★
                <form:radiobutton path="chal_rev_grade" value="3" class="star" />★
                <form:radiobutton path="chal_rev_grade" value="4" class="star" />★
                <form:radiobutton path="chal_rev_grade" value="5" class="star" />★
            </div>
        </div>
        <div class="review-content">
            <label for="chal_rev_content">내용을 입력해 주세요</label>
            <form:textarea path="chal_rev_content" rows="5" cols="50" placeholder="후기 작성시 ( )p가 지급됩니다."></form:textarea>
            <div class="char-count">
                0 / 최소 20자
            </div>
        </div>
        <div class="submit-button">
            <input type="submit" value="등록"/>
        </div>
    </form:form>
</div>
</body>
</html>