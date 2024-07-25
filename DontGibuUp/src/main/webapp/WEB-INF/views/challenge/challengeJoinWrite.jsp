<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>챌린지 참가</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <script type="text/javascript">
        var chalFreq = ${challengeVO.chal_freq};
        var chalTitle = "${challengeVO.chal_title}";
        var chalFee = ${challengeVO.chal_fee};
        var memberNick = "${member.mem_nick}";
        var memberEmail = "${member.email}";
        var memberPhone = "${member.phone}";
        var memberNum = "${member.mem_num}";
        var pageContextPath = "${pageContext.request.contextPath}";
        var chalNum = ${param.chal_num}; //챌린지 번호 가져오기
        var sdate = "${challengeVO.chal_sdate}"; //챌린지 시작 날짜 가져오기
    </script>
</head>
<body>
<h2>챌린지 참가</h2>
<div class="join-container">
    <div class="line">
        <c:if test="${empty challengeVO.chal_photo}">
        		<img src="${pageContext.request.contextPath}/images/챌린지_기본이미지.jpg" alt="챌린지 사진">
        </c:if>
        <c:if test="${!empty challengeVO.chal_photo}">
        	<img src="${pageContext.request.contextPath}/upload/${challengeVO.chal_photo}" alt="챌린지 사진">
        </c:if>
        <div class="text-content">
            <h3>${challengeVO.chal_title}</h3>
            <c:if test="${challengeVO.chal_freq == 0}">
        		<p>매일</p>
        	</c:if>        
        	<c:if test="${challengeVO.chal_freq != 0}">
        		<p>주 ${challengeVO.chal_freq}일</p>
        	</c:if>
            <p>${challengeVO.chal_sdate} ~ ${challengeVO.chal_edate}</p>
        </div>
    </div>
    <form:form id="challenge_join" enctype="multipart/form-data" modelAttribute="challengeJoinVO">
        <ul>
            <form:hidden path="chal_num" value="${param.chal_num}" />
            <li>
                <form:label path="dcate_num">기부 카테고리</form:label>
                <span id="charityInfo"></span>
                <span id="dcate_num_error" class="error-color" style="display:none;">기부 카테고리를 선택하세요.</span>
            </li>
            <li>
                <c:forEach var="category" items="${categories}">
                    <form:radiobutton path="dcate_num" value="${category.dcate_num}" label="${category.dcate_name}" data-charity="${category.dcate_charity}" onclick="showCharityInfo(this)"/>
                </c:forEach>
            </li>
            <br>
            <li class="result-details">
        		<p><span class="left">100% 성공</span> <span class="right"><span class="chal_fee_90"></span>원 + 추가 <span class="chal_fee_5"></span>p 환급, <span class="chal_fee_10"></span>원 기부</span></p>
        		<p><span class="left">90% 이상 성공</span> <span class="right"><span class="chal_fee_90"></span>원 환급, <span class="chal_fee_10"></span>원 기부</span></p>
        		<p><span class="left">90% 미만 성공</span> <span class="right">성공률만큼 환급, 나머지 기부</span></p>
    		</li>
        </ul>
        <br>
        <ul>
        	<li>참여금 <span id="chal_fee">${challengeVO.chal_fee}</span>원</li>
        	<li>보유 포인트 <span>( )p</span></li>
        	<li>사용할 포인트 <input type="number"></li>
        	<hr width="100%" size="1" noshade="noshade">
        	<li>결제금액 <span>( )원</span></li>
        </ul>
        <br>
        <div class="align-center">
            결제 조건 및 서비스 약관에 동의합니다
            <p>
            <button type="button" id="pay2">결제하기</button>
        </div>
    </form:form>
</div>
<script src="${pageContext.request.contextPath}/js/challenge/challenge.join.pay.js"></script>
</body>
</html>