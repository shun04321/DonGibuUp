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
		<li>
			<form:label path="friend_rcode">추천인 코드</form:label>
			<form:input path="friend_rcode" />
			<span>*선택 입력</span>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">Signup</form:button>
		<input type="button" value="로그인" class="default-btn" onclick="location.href='login'">
	</div>
	<div class="align-center">
		<span>SNS로 간편하게 시작하기</span>
		<div>
			<a href="${pageContext.request.contextPath}/member/oauth/kakao"><img alt="카카오톡 간편 로그인" width="60" src="${pageContext.request.contextPath}/images/logo_sns/kakaotalk_logo.png"></a>
			<a href="${pageContext.request.contextPath}/member/oauth/naver"><img alt="네이버 간편 로그인" width="60" src="${pageContext.request.contextPath}/images/logo_sns/naver_logo.png"></a>
		</div>
	</div>
</form:form>

</div>