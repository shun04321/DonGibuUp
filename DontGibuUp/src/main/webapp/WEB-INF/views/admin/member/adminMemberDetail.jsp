<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!-- 관리자 회원 상세 시작 -->
<div class="page-main">
 	<h2>관리자 회원 상세</h2><br>
 		<button type="button" class="btn btn-outline-success mt-2 mb-2" onclick="location.href='/admin/manageMember'">목록</button>
 	<h5>기부박스 정보</h5>
 	<div class="shadow-sm p-3 mb-5 bg-body-tertiary rounded">

	 	등급 :  	
		<c:if test="${member.auth_num == 1}">
		기부흙
		</c:if>
		<c:if test="${member.auth_num == 2}">
		기부씨앗
		</c:if>
		<c:if test="${member.auth_num == 3}">
		기부새싹
		</c:if>
		<c:if test="${member.auth_num == 4}">
		기부꽃
		</c:if>
		<c:if test="${member.auth_num == 5}">
		기부나무
		</c:if>
		<c:if test="${member.auth_num == 6}">
		기부숲
		</c:if>

		<br>
		<b>등급변경</b><br>
		<form action="Change" id="auth_form" method="get">
			<input type="hidden" name="mem_num" value="${member.mem_num}">
			<!-- 기부흙 -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input status" type="radio" name="member_auth" value="1"
			  	<c:if test="${member.auth_num == 1}">checked</c:if>
			  >
			  <label for="auth1">
			  	<img src="/images/auth/auth1.png" class="rounded-circle my-image">&nbsp;기부흙
			  </label>
			</div>
			<!-- 기부씨앗 -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input status" type="radio" name="member_auth" value="2"
			  	<c:if test="${member.auth_num == 2}">checked</c:if>
			  >
			  <label for="auth2">
			  	<img src="/images/auth/auth2.png" class="rounded-circle my-image">&nbsp;기부씨앗
			  </label>
			</div>
			<!-- 기부새싹 -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input status" type="radio" name="member_auth" value="3"
			  	<c:if test="${member.auth_num == 3}">checked</c:if>
			  >
			  <label for="auth3">
			  	<img src="/images/auth/auth3.png" class="rounded-circle my-image">&nbsp;기부새싹
			  </label>
			</div>
			<!-- 기부꽃 -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input status" type="radio" name="member_auth" value="4"
			  	<c:if test="${member.auth_num == 4}">checked</c:if>
			  >
			  <label for="auth4">
			  	<img src="/images/auth/auth4.png" class="rounded-circle my-image">&nbsp;기부꽃
			  </label>
			</div>
			<!-- 기부나무 -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input status" type="radio" name="member_auth" value="5"
			  	<c:if test="${member.auth_num == 5}">checked</c:if>
			  >
			  <label for="auth5">
			  	<img src="/images/auth/auth5.png" class="rounded-circle my-image">&nbsp;기부나무
			  </label>
			</div>
			<!-- 기부숲 -->
			<div class="form-check form-check-inline">
			  <input class="form-check-input status" type="radio" name="member_auth" value="6"
			  	<c:if test="${member.auth_num == 6}">checked</c:if>
			  >
			  <label for="auth6">
			  	<img src="/images/auth/auth6.png" class="rounded-circle my-image">&nbsp;기부숲
			  </label>
			</div>
			<button type="submit" class="btn btn-outline-success mt-2">변경</button>
	 	</form>
	 	<script>
	 	$('#auth_form').submit(function(){
	 		if(!confirm('변경하시겠습니까?')){
	 			return false;
	 	});
	 	</script>
	</div>
 	
 	<h5>신청자 정보</h5>
 	<div class="shadow-sm p-3 mb-5 bg-body-tertiary rounded">
 		회원 번호 : ${member.mem_num}<br>
	 	닉네임 : 	<c:if test="${!empty member.mem_photo}">
                <img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" class="rounded-circle my-image">&nbsp;${member.mem_nick}&nbsp;님<br>
                </c:if>
                <c:if test="${empty member.mem_photo}">
                <img src="${pageContext.request.contextPath}/images/basicProfile.png" class="rounded-circle my-image">&nbsp;${member.mem_nick}&nbsp;님<br>
                </c:if>
	 	상태 : 
			<c:if test="${member.mem_status == 0}">
			탈퇴회원
			</c:if>
			<c:if test="${member.mem_status == 1}">
			정지회원
			</c:if>
			<c:if test="${member.mem_status == 2}">
			일반회원
			</c:if>
			<c:if test="${member.mem_status == 9}">
			관리자
			</c:if>
	 	<br>
	 	이메일 : ${member.mem_email}<br>	
 	</div>
</div>
<!-- 관리자 회원 상세 끝 -->