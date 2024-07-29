<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 기부박스 상태 관리 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div class="page-main">
 	<h2>기부박스 관리</h2>
 	
 	기간 : ${dbox.dbox_sdate} ~ ${dbox.dbox_edate}<br>
 	등록일 : ${dbox.dbox_rdate}<br>
 	상태 : 
	<c:if test="${dbox.dbox_status == 0}">
	<span style="color:blue">신청완료</span>
	</c:if>
	<c:if test="${dbox.dbox_status == 1}">
	<span style="color:magenta">심사완료</span>
	</c:if>
	<c:if test="${dbox.dbox_status == 2}">
	<span style="color:red">신청반려</span>
	</c:if>
	<c:if test="${dbox.dbox_status == 3}">
	<b>진행중</b>
	</c:if>
	<c:if test="${dbox.dbox_status == 4}">
	진행완료
	</c:if>
	<c:if test="${dbox.dbox_status == 5}">
	<span style="color:red">진행중단</span>
	</c:if><br>
 	
 	팀 : 
 	<span class="badge">
	<c:if test="${dbox.dbox_team_type == 1}">
	기관
	</c:if>
	<c:if test="${dbox.dbox_team_type == 2}">
	개인/단체
	</c:if>
	</span>
	${dbox.dbox_team_name}<br>
	
 	팀 설명 : ${dbox.dbox_team_detail}<br>
 	팀 사진<br>
 	<img src="${pageContext.request.contextPath}/upload/dbox/${dbox.dbox_team_photo}" width="100"><br>
 	
 	
 	<c:if test="${dbox.dbox_team_type == 1}">
	사업자 번호 : ${dbox.dbox_business_rnum}<br>
	</c:if>
	사업계획서 : ${dbox.dbox_business_plan}<br>
	금액책정 근거자료 : ${dbox.dbox_budget_data}<br>
 	계좌 : [${dbox.dbox_bank}] ${dbox.dbox_account} / ${dbox.dbox_account_name}<br>
 	남길 말 : ${dbox.dbox_comment}<br>
 	
 	
 	<h4>예시</h4>
</div>
<!-- 기부박스 상태 관리 끝 -->