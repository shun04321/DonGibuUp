<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-main">
<h2>비밀번호 찾기</h2>
<form:form action="findPassword" method="get" id="member_password" modelAttribute="memberVO">
	<ul>
	<form:errors element="div" cssClass="form-error" />
		<li>
			<form:label path="mem_email">이메일</form:label>
			<form:input path="mem_email"/>
			<form:errors path="mem_email" cssClass="form-error"></form:errors>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">비밀번호 찾기</form:button>
	</div>
</form:form>

</div>