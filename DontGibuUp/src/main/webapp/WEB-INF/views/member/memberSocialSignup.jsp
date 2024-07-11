<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member/member.signup.js"></script>
<h2>회원가입</h2>
<form:form action="kakao" id="member_social_signup"  modelAttribute="memberVO">
	<input type="hidden" name="mem_social_id" value="${memberVO.mem_social_id}"/>
	<input type="hidden" name="mem_email" value="${memberVO.mem_email}" />
	<ul>
		<li>
			<label for="mem_email">이메일*</label>
			<span>${memberVO.mem_email}</span>
		</li>
		<li>
			<form:label path="mem_nick">닉네임*</form:label>
			<form:input path="mem_nick"/>
			<span id="nick_check_msg"></span>
			<form:errors path="mem_nick" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:label path="friend_rcode">추천인 코드(선택)</form:label>
			<form:input path="friend_rcode" />
			<form:errors path="friend_rcode" cssClass="form-error"></form:errors>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">Signup</form:button>
	</div>
</form:form>
</div>
