<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member/member.password.js"></script>
<div class="page-main">
<h2>비밀번호 수정</h2>
<form:form action="changePassword" id="change_password" modelAttribute="memberVO">
	<ul>
		<li>
			<label>현재 비밀번호</label>
			<input type="password" id="current_pw" name="current_pw"/>
			<span id="password_feedback" style="display:none;"></span>
		</li>
		<li>
			<form:label path="mem_pw">새 비밀번호</form:label>
			<form:password path="mem_pw" placeholder="영문,숫자 혼합 8~12자리"/>
			<form:errors path="mem_pw" cssClass="form-error"></form:errors>
		</li>
		<li>
			<label for="cpw">비밀번호 확인</label>
			<input type="password" id="check_pw">
			<%-- 비밀번호/확인 일치여부 체크 --%>
			<span id="check_pw_msg"></span>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">수정</form:button>
	</div>
</form:form>
</div>