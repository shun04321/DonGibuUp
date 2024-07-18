<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
<div class="page-main">
<h2>로그인</h2>
<form:form action="login" id="member_login" modelAttribute="memberVO">
	<ul>
	<form:errors element="div" cssClass="form-error" />
	<c:if test="${!empty error}"><div class="form-error">${error}</div></c:if>
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
		<form:button class="default-btn">Login</form:button>
		<input type="button" value="비밀번호 찾기" class="default-btn" onclick="location.href='findPassword'">
		<input type="button" value="회원가입" class="default-btn" onclick="location.href='signup'">
	</div>
	<div class="align-center">
		<span>SNS로 로그인하기</span>
		<div>
			<a href="${pageContext.request.contextPath}/member/oauth/kakao"><img alt="카카오톡 간편 로그인" width="60" src="${pageContext.request.contextPath}/images/logo_sns/kakaotalk_logo.png"></a>
			<a href="${pageContext.request.contextPath}/member/oauth/naver"><img alt="네이버 간편 로그인" width="60" src="${pageContext.request.contextPath}/images/logo_sns/naver_logo.png"></a>
		</div>
	</div>
</form:form>

</div>