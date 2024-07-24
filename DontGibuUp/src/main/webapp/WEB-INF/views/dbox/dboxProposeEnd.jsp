<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="text-center">
	<br><br><br><br>
	<h4>기부박스 제안이 완료되었습니다.</h4>
	<ul style="list-style:none;margin:auto;" class="col-3 text-start mt-3 mb-3">
		<li>제목 : ${dbox.dbox_title}</li>
		<li>팀명 : ${dbox.dbox_team_name}</li>
		<li>기부처 : <span class="badge text-bg-success mb-2"><img src="${pageContext.request.contextPath}/upload/${dbox.dcate_icon}" style="height:1rem;"> ${dbox.dcate_name}</span></li>
		<li>목표금액 : <fmt:formatNumber value="${dbox.dbox_goal}" pattern="#,###원"/></li>
		<li>기간 : ${dbox.dbox_sdate} ~ ${dbox.dbox_edate}</li>	
	</ul>
	<button type="button" class="btn btn-success col-3" onclick="location.href='${pageContext.request.contextPath}/dbox/${dbox_num}/example'">기부박스 제안 보러가기</button>
	<br><br><br><br><br>
</div>