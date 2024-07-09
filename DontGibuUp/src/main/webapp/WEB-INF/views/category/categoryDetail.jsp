<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!-- 게시판 글상세 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<div class="page-main">
	<h2>${category.dcate_name}</h2>
	<ul class="detail-info">
		<li>
			${category.dcate_charity}
			<br>
		</li>
	</ul>
	<div class="detail-content">
		${category.dcate_content}
	</div>
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
		<input type="button" value="목록"
		                 onclick="location.href='list'">
	</div>
</div>



