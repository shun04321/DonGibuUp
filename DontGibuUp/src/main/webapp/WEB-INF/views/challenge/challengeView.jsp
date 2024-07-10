<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
챌린지 상세
<div>
	<ul>
		<li>
			<img src="">
		</li>
		<li>
			<c:if test="${!empty user}">
				<button onclick="location.href='join?chal_num=${challenge.chal_num}'">참가하기</button>
			</c:if>		
		</li>
	</ul>
</div>