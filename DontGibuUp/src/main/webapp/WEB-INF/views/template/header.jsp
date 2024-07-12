<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상단 시작 -->
<h2 class="align-center">
	<c:if test="${empty user || user.mem_status != 9}">
	<a href="${pageContext.request.contextPath}/main/main">Don Gibu Up</a>
	</c:if>
	<c:if test="${!empty user && user.mem_status == 9}">
	<a href="${pageContext.request.contextPath}/main/admin">Don Gibu Up</a>
	</c:if>
</h2>
<div class="align-right">
	<a href="${pageContext.request.contextPath}/board/list">게시판</a>
	<a href="${pageContext.request.contextPath}/goods/list">굿즈샵</a>
	<a href="${pageContext.request.contextPath}/dbox/list">기부박스</a>
	<a href="${pageContext.request.contextPath}/subscription/subscriptionMain">정기기부</a>
	<a href="${pageContext.request.contextPath}/challenge/list">챌린지</a>
    <c:if test="${!empty user}">
	    <a href="${pageContext.request.contextPath}/challenge/write">챌린지 개설하기</a>
	    <a href="${pageContext.request.contextPath}/member/myPage">MY페이지</a>
	    <c:if test="${!empty user.mem_photo}">
	    <img src="${pageContext.request.contextPath}/upload/${user.mem_photo}" width="25" height="25" class="my-photo">
	    </c:if>
	    <span>${user.mem_nick}</span>
	    <c:if test="${empty user.mem_photo}">
	    <img src="${pageContext.request.contextPath}/images/basicProfile.png" width="25" height="25" class="my-photo">
	    </c:if>
	    <a href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
    </c:if>
	<c:if test="${empty user}">
	<a href="${pageContext.request.contextPath}/member/signup">회원가입</a>
	<a href="${pageContext.request.contextPath}/member/login">로그인</a>
	</c:if>	
	<a href="${pageContext.request.contextPath}/main/main">홈으로</a>
</div>
<!-- 상단 끝 -->




