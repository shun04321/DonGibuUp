<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 후기 작성</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#chal_rev_content').on('input', function () {
                let content = $(this).val();
                let charCount = content.length;
                $('.char-count').text(charCount + ' / 최소 20자');
            });

            $('form').on('submit', function (e) {
                let isValid = true;
                if (!$('input[name="chal_rev_grade"]:checked').val()) {
                    isValid = false;
                    $('.rating-error').text('별점을 선택해주세요.');
                } else {
                    $('.rating-error').text('');
                }

                if ($('#chal_rev_content').val().length < 20) {
                    isValid = false;
                    $('.content-error').text('최소 20자 이상 입력해주세요.');
                } else {
                    $('.content-error').text('');
                }

                if (!isValid) {
                    e.preventDefault();
                }
            });
        });
    </script>
    <style>
        .rating-error, .content-error {
            color: red;
            margin-left: 10px;
        }
        .review-rating, .review-content{
            display: flex;
            align-items: center;
        }
        .review-rating .stars {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="review-container2">
    <div class="challenge-info3">
        <c:if test="${empty challenge.chal_photo}">
        		<img src="${pageContext.request.contextPath}/images/챌린지_기본이미지.jpg" alt="챌린지 사진" class="challenge-thumbnail">
        </c:if>
        <c:if test="${!empty challenge.chal_photo}">
        	<img src="${pageContext.request.contextPath}/upload/${challenge.chal_photo}" alt="챌린지 사진" class="challenge-thumbnail">
        </c:if>
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
			<span class="rating-error"></span>
		</div>
		<br>
        <div class="stars">
            <form:radiobutton path="chal_rev_grade" value="1" class="star" />★
            <form:radiobutton path="chal_rev_grade" value="2" class="star" />★
            <form:radiobutton path="chal_rev_grade" value="3" class="star" />★
            <form:radiobutton path="chal_rev_grade" value="4" class="star" />★
            <form:radiobutton path="chal_rev_grade" value="5" class="star" />★
        </div>
        <br><br>
        <div class="review-content">
            <label for="chal_rev_content">내용을 입력해 주세요</label>
            <span class="content-error"></span>
        </div>
        <br>
        <div class="review-content2">
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