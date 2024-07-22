<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="text-center mt-5 mb-5">
	<img src="${pageContext.request.contextPath}/upload/dbox/${dbox.dbox_photo}" class="img-fluid"><br>
</div>

<button type="button" class="btn btn-outline-success" onclick="location.href='${pageContext.request.contextPath}/dbox/${dbox.dbox_num}/content'">기부박스 소개</button>
<button type="button" class="btn btn-outline-success active" onclick="location.href='${pageContext.request.contextPath}/dbox/${dbox.dbox_num}/donators'">기부현황</button>
<button type="button" class="btn btn-outline-success" onclick="location.href='${pageContext.request.contextPath}/dbox/${dbox.dbox_num}/news'">소식</button>
<table>
	<c:forEach var="donators" items="${dboxDonations}">
	${donations.mem_nick}
	<img src="${pageContext.request.contextPath}/upload/${donations.mem_photo}"><br>
	${donations.dbox_do_price+donations.dbox_do_point}원 기부
	${donations.dbox_do_comment}
	${donations.dbox_do_reg_date}	
	</c:forEach>
</table>