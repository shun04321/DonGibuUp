<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member/member.info.js"></script>
<div class="page-main">
<h2>회원정보 수정</h2>
<div>
	<div>
		<c:if test="${!empty user.mem_photo}">
			<img src="${pageContext.request.contextPath}/upload/${user.mem_photo}" width="100" height="100" class="my-photo">
		</c:if>
		<c:if test="${empty user.mem_photo}">
			<img src="${pageContext.request.contextPath}/images/basicProfile.png" width="100" height="100" class="my-photo">
		</c:if>
			<button id="photo_del" <c:if test="${empty user.mem_photo}">style="display:none;"</c:if>>삭제</button>
	</div>
	<div>
		<input type="file" id="mem_photo" accept="image/gif,image/png,image/jpeg" style="display:none;">
		<button id="photo_choice">찾아보기</button>
	</div>
</div>

<form:form action="updateMember" id="member_update" modelAttribute="memberVO">
	<ul>
		<li>
			<label for="mem_email">이메일</label>
			<input id="mem_email" value="${memberVO.mem_email}" disabled/>
		</li>
		<li>
			<form:label path="mem_nick">닉네임</form:label>
			<form:input path="mem_nick" />
			<span id="nick_check_msg"></span>
			<form:errors path="mem_nick" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:label path="mem_name">이름</form:label>
			<form:input path="mem_name"/>
			<form:errors path="mem_name" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:label path="mem_phone">전화번호</form:label>
		    <input type="text" id="phone1" style="width:100px;" required disabled value="010">
		    -
		    <input type="text" id="phone2" maxlength="4" style="width:100px;">
		    -
		    <input type="text" id="phone3" maxlength="4" style="width:100px;">
		    <form:hidden path="mem_phone"/>
		    <form:errors path="mem_phone" cssClass="form-error"></form:errors>
		</li>
		<li>
			<label>생년월일</label>
		    <label for="birth_year" style="width:20px;">년</label>
		    <select id="birth_year" name="birth_year"></select>
		
		    <label for="birth_month" style="width:20px;">월</label>
		    <select id="birth_month" name="birth_month"></select>
		
		    <label for="birth_day" style="width:20px;">일</label>
		    <select id="birth_day" name="birth_day"></select>
		    <form:hidden path="mem_birth"/>
		    <form:errors path="mem_birth" cssClass="form-error"></form:errors>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">수정</form:button>
	</div>
	<div>
		<button>회원 탈퇴하기</button>
	</div>
</form:form>
</div>
