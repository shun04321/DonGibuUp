<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 상세</title>
    <style>
        .challenge-detail {
            width: 60%;
            margin: auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            font-family: Arial, sans-serif;
        }
        .challenge-header {
            text-align: center;
            margin-bottom: 20px;
        }
        .challenge-header img {
            width: 100%;
            height: auto;
            max-height: 200px;
            border-radius: 10px;
            object-fit: cover;
        }
        .challenge-info {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px;
        }
        .challenge-info .author-info {
            display: flex;
            align-items: center;
        }
        .challenge-info img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .challenge-info .details {
            flex-grow: 1;
        }
        .challenge-stats {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
            padding: 10px 0;
            border-top: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
            font-size: 14px;
        }
        .challenge-stats div {
            text-align: center;
            flex-grow: 1;
        }
        .challenge-stats div:not(:last-child) {
            border-right: 1px solid #ddd;
        }
        .challenge-content {
            margin-top: 20px;
        }
        .challenge-content h3 {
            margin-bottom: 10px;
            font-size: 18px;
        }
        .challenge-content p {
            margin-bottom: 20px;
            line-height: 1.6;
            font-size: 14px;
        }
    </style>
    <script type="text/javascript">
        const chalFreq = ${challenge.chal_freq}; // 챌린지 인증 빈도 변수
        const chalFee = ${challenge.chal_fee}; // 챌린지 참여금 변수
        const chalTitle = '${challenge.chal_title}'; // 챌린지 제목 변수
        const memberNick = '${challenge.mem_nick}'; // 작성자 닉네임 변수
        const memberEmail = '${member.email}'; // 작성자 이메일 변수
        const memberPhone = '${member.phone}'; // 작성자 전화번호 변수
        const memberNum = '${member.mem_num}'; // 작성자 번호 변수
        const pageContextPath = '${pageContext.request.contextPath}'; // 페이지 컨텍스트 경로
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/challenge/challenge.join.form.js"></script>
</head>
<body>
<h2>챌린지 상세</h2>
	<br>
	<div class="challenge-detail">
	    <div class="challenge-header">
	        <img src="${challenge.chal_photo}" alt="챌린지 사진">
	        <h2 class="align-left">${challenge.chal_title}</h2>
	    </div>
	    <div class="challenge-info">
	        <div class="author-info">
	            <img src="${challenge.mem_photo}" alt="작성자 사진">
	            <div class="details">
	                <p><strong>${challenge.mem_nick}</strong></p>
	            </div>
	        </div>
	        <button onclick="location.href='join?chal_num=${challenge.chal_num}'">참가하기</button>
	    </div>
	    <div class="challenge-stats">
	        <div>
	            <span>인증 빈도</span>
	            <p><span id="chal_freq"></span></p>
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