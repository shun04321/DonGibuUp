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
<div class="container">
    <div class="header">
        <h2>[	${chal_title}	]</h2>
    </div>
    <form:form id="challenge_verify" enctype="multipart/form-data" modelAttribute="challengeVerifyVO">
        <form:hidden path="chal_joi_num"/>
        
        <div class="form-section">
            <label>인증 방법</label>
            <p>${chal_verify}</p>
        </div>
        
        <div class="form-section">
            <form:label path="upload">인증 사진<span class="mandatory">*</span></form:label>
            <form:input type="file" path="upload"/>
            <form:errors path="upload" cssClass="error"/>
        </div>
        
        <div class="form-section">
            <form:label path="chal_content">한줄평</form:label>
            <form:textarea path="chal_content" rows="5" placeholder="오늘 인증은 어떠셨나요?"/>
        </div>
        
        <div class="align-center">
            <button type="submit">등록</button>
        </div>
    </form:form>
</div>
</body>
</html>