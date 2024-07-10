<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>
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

<form:form action="modify" id="member_modify" modelAttribute="memberVO">
	<ul>
		<li>
			<form:label path="mem_email">이메일</form:label>
			<form:input path="mem_email"/>
		</li>
		<li>
			<form:label path="mem_nick">닉네임</form:label>
			<form:input path="mem_nick"/>
			<form:errors path="mem_nick" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:label path="mem_name">이름</form:label>
			<form:input path="mem_name"/>
			<form:errors path="mem_name" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:label path="mem_phone">전화번호</form:label>
			<form:input path="mem_phone"/>
			<form:errors path="mem_phone" cssClass="form-error"></form:errors>
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
