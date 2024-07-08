<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-main">
<h2>로그인</h2>
<form:form action="login" id="member_login" modelAttribute="memberVO">
	<ul>
	<form:errors element="div" cssClass="form-error" />
		<li>
			<form:label path="mem_email">이메일</form:label>
			<form:input path="mem_email"/>
			<form:errors path="mem_email" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:label path="mem_pw">비밀번호</form:label>
			<form:password path="mem_pw"/>
			<form:errors path="mem_pw" cssClass="form-error"></form:errors>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">로그인</form:button>
		<input type="button" value="계정찾기" class="default-btn" onclick="location.href=findAccount">
		<input type="button" value="회원가입" class="default-btn" onclick="location.href='${pageContext.request.contextPath}/main/main'">
	</div>
</form:form>

</div>