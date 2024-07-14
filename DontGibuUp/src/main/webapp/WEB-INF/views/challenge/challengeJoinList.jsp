<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 현황</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/WEB-INF/views/challenge/challenge.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        var contextPath = "${pageContext.request.contextPath}";
        var status = "${status}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/challenge/challenge.join.list.js"></script>
</head>
<body>
<div id="challengeContainer">
    <div class="month-section">
        <h2 class="align-left">
            <span id="prevMonth" style="cursor:pointer;">&lt;</span>
            <span id="currentMonth" data-month="${currentMonth}">${currentMonth.replace("-", "년 ")}월</span>
            <span id="nextMonth" style="cursor:pointer;">&gt;</span>
        </h2>
        <div class="challenge-list">
            <c:forEach var="entry" items="${challengesByMonth}">
                <c:forEach var="challengeJoin" items="${entry.value}">
                    <div class="challenge-card">
                        <div class="card-header">
                            <h3>${challengeJoin.chal_title}</h3>
                            <div class="view-detail">
                                <c:choose>
                                    <c:when test="${status == 'pre'}"><a href="#" onclick="deleteChallenge(${challengeJoin.chal_joi_num})">챌린지취소</a></c:when>
                                    <c:when test="${status == 'on'}"><a href="${pageContext.request.contextPath}/challenge/verify/list?chal_joi_num=${challengeJoin.chal_joi_num}">인증내역</a> <a href="#">단체톡방</a></c:when>
                                    <c:when test="${status == 'post'}"><a href="${pageContext.request.contextPath}/challenge/verify/list?chal_joi_num=${challengeJoin.chal_joi_num}">인증내역</a> <a href="#">후기작성</a></c:when>
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
                                <span>0p</span>
                            </div>
                            <div>
                                <span>기부금</span>
                                <span>${challengeJoin.chal_fee}원</span>
                            </div>                
                            <div>
                                <span>기부처</span>
                                <span>${challengeJoin.dcate_charity}</span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>