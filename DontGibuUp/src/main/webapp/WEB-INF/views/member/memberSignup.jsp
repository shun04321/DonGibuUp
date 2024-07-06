<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-main">
<h2>회원가입</h2>
<form:form action="signup" id="member_signup" modelAttribute="memberVO">
	<ul>
		<li>
			<form:label path="mem_email">이메일</form:label>
			<form:input path="mem_email"/>
			<%--이메일 중복체크하기--%>
			<span id="email_dup_message"></span>
			<form:errors path="mem_email" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:label path="mem_pw">비밀번호</form:label>
			<form:password path="mem_pw"/>
			<form:errors path="mem_pw" cssClass="form-error"></form:errors>
		</li>
		<li>
			<label for="cpw">비밀번호 확인</label>
			<input type="password" id="check_pw">
			<%-- 비밀번호/확인 일치여부 체크 --%>
			<span id="check_pw"></span>
		</li>
		<li>
			<form:label path="mem_nick">닉네임</form:label>
			<form:input path="mem_nick"/>
			<form:errors path="mem_nick" cssClass="form-error"></form:errors>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">가입</form:button>
		<input type="button" value="홈으로" class="default-btn" onclick="location.href='${pageContext.request.contextPath}/main/main'">
	</div>
</form:form>

</div>