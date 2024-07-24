<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="text-center" style="min-height:500px;">
	<br><br><br><br><br><br><br><br>
	<h4>기부하기 완료</h4>
	<br>
	<div>
		<span class="badge text-bg-success mb-2"><img src="${pageContext.request.contextPath}/upload/${dbox.dcate_icon}" style="height:1rem;"> ${dbox.dcate_name}</span>[${dbox.dbox_title}]에 기부해주셔서 감사합니다.<br>
	</div>
	<br>
	<button type="button" class="btn btn-success" onclick="location.href='${pageContext.request.contextPath}/dbox/${dbox.dbox_num}/content'">기부한 기부박스로 이동</button>
</div>