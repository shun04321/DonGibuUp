<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 참가 목록</title>
</head>
<body>
<h2>
    <c:choose>
        <c:when test="${status == 'pre'}">시작 전 챌린지</c:when>
        <c:when test="${status == 'on'}">참가중 챌린지</c:when>
        <c:when test="${status == 'post'}">완료된 챌린지</c:when>
    </c:choose>
</h2>
<table border="1">
    <thead>
        <tr>
            <th>챌린지 제목</th>
            <th>시작일</th>
            <th>종료일</th>
            <th>참가비</th>
            <th>기부처</th>
            <th>상세보기</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="challengeJoin" items="${list}">
            <tr>
                <td>${challengeJoin.chal_title}</td>
                <td>${challengeJoin.chal_sdate}</td>
                <td>${challengeJoin.chal_edate}</td>
                <td>${challengeJoin.chal_fee}</td>
                <td>${challengeJoin.dcate_charity}</td>
                <td><a href="${pageContext.request.contextPath}/challenge/joinDetail?chal_joi_num=${challengeJoin.chal_joi_num}">상세보기</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>