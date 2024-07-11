<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member/member.password.js"></script>
<div class="page-main">
<h2>비밀번호 수정</h2>
<form action="changePassword" method="post" id="change_password">
	<ul>
		<li>
			<label>현재 비밀번호</label>
			<input type="password" id="current_pw" name="current_pw"/>
			<span id="password_feedback" style="display:none;"></span>
		</li>
		<li>
			<label>새 비밀번호</label>
			<input type="password" id="mem_pw" name="mem_pw"/>
		</li>
		<li>
			<label>비밀번호 확인</label>
			<input type="password" id="check_mem_pw"/>
		</li>
	</ul>
	<div class="align-center">
		<input type="submit" class="default-btn" value="수정">
	</div>
</form>
</div>