<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!-- 게시판 글상세 시작 -->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판 글상세</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css" type="text/css">
<title>container</title>
</head>
<body>
<div id="main">
<div id="main_header">
		<tiles:insertAttribute name="header"/>
</div>
	<img src="${pageContext.request.contextPath}/upload/${category.dcate_banner}" id="banner">
	<div class="page-main">
		<h2>${category.dcate_name}</h2>
		<ul class="detail-info">
			<li>${category.dcate_charity} <br>
			</li>
		</ul>
		<div class="detail-content">${category.dcate_content}</div>
		<hr size="1" width="100%">
		<div class="align-right">
			<input type="button" value="수정"
				onclick="location.href='/category/updateCategory?dcate_num=${category.dcate_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<script>
			const delete_btn = document.getElementById('delete_btn');
			delete_btn.onclick=function(){
				const choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('/category/deleteCategory?dcate_num=${category.dcate_num}');
				}
			};
		</script>
			<input type="button" value="목록" onclick="location.href='categoryList'">
		</div>
	</div>
<tiles:insertAttribute name="modal"/>
<div id="main_footer">
		<tiles:insertAttribute name="footer"/>
</div>
</div>
</body>
</html>

