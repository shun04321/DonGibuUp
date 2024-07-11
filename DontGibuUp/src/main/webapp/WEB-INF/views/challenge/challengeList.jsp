<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/challenge.list.js"></script>
<div>
	<h2>챌린지 목록</h2>
	<form action="list" id="challenge_list">
		<ul>
			<li>
				<label for="t0">전체</label>
				<input type="radio" name="chal_type" id= "t0" value="8">
			</li>
			<li>
				<c:forEach var="cate" items="${categories}" varStatus="status">
					<label for="t${status.count}">${cate.ccate_name}</label>
					<input type="radio" name="chal_type" id= "t${status.count}" value="${cate.ccate_num}">
				</c:forEach>				
			</li>
		</ul>
			
	</form>
	<span id="output"></span> 
</div>