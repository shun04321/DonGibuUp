<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- MyPage 메뉴 시작 -->
<ul class="side-bar">
	<li class="side-menu">
		<div>나의 정보</div>
        <ul class="side-menu-sub">
            <li><a href="${pageContext.request.contextPath}/member/myPage/memberInfo">회원정보 수정</a></li>
            <c:if test="${user.mem_reg_type == 1}">
            <li><a href="${pageContext.request.contextPath}/member/myPage/changePassword">비밀번호 수정</a></li>
            </c:if>
            <li><a href="${pageContext.request.contextPath}/member/myPage/inviteFriendEvent">친구초대</a></li>
            <li><a href="${pageContext.request.contextPath}/member/myPage/point">포인트</a></li>
        </ul>
	</li>
	<li class="side-menu">
		<div>기부</div>
		<ul class="side-menu-sub">
			<li>정기기부</li>
			<li>기부박스</li>
		</ul>
	</li>
	<li class="side-menu">
		<div>챌린지</div>
		<ul class="side-menu-sub">
	        <li><a href="${pageContext.request.contextPath}/challenge/join/list?status=pre">시작 전 챌린지</a></li>
	        <li><a href="${pageContext.request.contextPath}/challenge/join/list?status=on">참가중인 챌린지</a></li>
	        <li><a href="${pageContext.request.contextPath}/challenge/join/list?status=post">완료된 챌린지</a></li>
		</ul>
	</li>
	<li class="side-menu">
		<div>주문</div>
		<ul class="side-menu-sub">
			<li>주문/배송조회</li>
			<li><a href="${pageContext.request.contextPath}/cart/list">장바구니</a></li>
		</ul>
	</li>
	<li class="side-menu">
		<div>문의/신고</div>
	</li>
</ul>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- MyPage 메뉴 끝 -->