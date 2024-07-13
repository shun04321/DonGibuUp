<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 현황</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/WEB-INF/views/challenge/challenge.css">
</head>
<body>
<h2 class="align-left">
    <c:choose>
        <c:when test="${status == 'pre'}">시작 전 챌린지</c:when>
        <c:when test="${status == 'on'}">참가중인 챌린지</c:when>
        <c:when test="${status == 'post'}">완료된 챌린지</c:when>
    </c:choose>
</h2>
<div class="challenge-list">
    <c:forEach var="challengeJoin" items="${list}">
        <div class="challenge-card">
            <div class="card-header">
                <h3>${challengeJoin.chal_title}</h3>
                <div class="view-detail">
                	<c:choose>
			        <c:when test="${status == 'pre'}"><a href="#">챌린지취소</a></c:when>
			        <c:when test="${status == 'on'}"><a href="#">인증내역</a> <a href="#">단체톡방</a></c:when>
			        <c:when test="${status == 'post'}"><a href="#">인증내역</a> <a href="#">후기작성</a></c:when>
			    </c:choose>

                </div>
            </div>
            <p>${challengeJoin.chal_sdate} - ${challengeJoin.chal_edate}</p>
            <div class="details">
                <div>
                    <span>참여금</span>
                    <span>${challengeJoin.chal_fee}원</span>
                </div>
                <div>
                    <span>환급포인트</span>
                    <span>( )p</span>
                </div>
                <div>
                    <span>기부금</span>
                    <span>( )원</span>
                </div>                
                <div>
                    <span>기부처</span>
                    <span>${challengeJoin.dcate_charity}</span>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>