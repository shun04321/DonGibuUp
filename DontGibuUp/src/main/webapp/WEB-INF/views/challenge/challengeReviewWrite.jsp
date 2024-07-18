<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 후기 작성</title>
</head>
<body>
<h2>챌린지 후기 작성</h2>
<form:form action="${pageContext.request.contextPath}/challenge/review/write" method="post" modelAttribute="challengeReviewVO">
    <form:hidden path="chal_num" value="${chal_num}"/>
    <div>
        <label for="chal_rev_grade">별점:</label>
        <form:select path="chal_rev_grade">
            <form:option value="1">1점</form:option>
            <form:option value="2">2점</form:option>
            <form:option value="3">3점</form:option>
            <form:option value="4">4점</form:option>
            <form:option value="5">5점</form:option>
        </form:select>
    </div>
    <div>
        <label for="chal_rev_content">내용:</label>
        <form:textarea path="chal_rev_content" rows="5" cols="50"></form:textarea>
    </div>
    <div>
        <input type="submit" value="작성"/>
    </div>
</form:form>
</body>
</html>