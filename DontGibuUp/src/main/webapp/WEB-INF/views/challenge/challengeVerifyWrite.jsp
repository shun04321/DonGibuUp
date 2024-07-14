<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 인증</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
</head>
<body>
<h2>챌린지 인증</h2>
<div class="container">
    <form:form id="challenge_verify" enctype="multipart/form-data" modelAttribute="challengeVerifyVO">
        <form:hidden path="chal_joi_num"/>
        <div class="form-section">
            <form:label path="chal_content">인증 한줄평</form:label>
            <form:textarea path="chal_content" rows="5" cols="50"/>
        </div>
        <div class="form-section">
            <form:label path="upload">인증 사진</form:label>
            <form:input type="file" path="upload"/>
        </div>
        <div class="align-center">
            <button type="submit">인증하기</button>
        </div>
    </form:form>
</div>
</body>
</html>