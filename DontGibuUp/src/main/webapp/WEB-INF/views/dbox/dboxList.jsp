<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 목록 시작 -->
<script src="${pageContext.request.contextPath}/js/dbox/dbox.list.js"></script>
<div class="page-main">
	<%-- 카테고리 --%>
	<div id="category_output" class="align-center"></div>
	<%-- 기부박스 제안하기 버튼 --%>
	<div class="align-right">
		<button type="button" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/dbox/propose'">기부박스 제안하기</button>
	</div>
	<%-- 검색 --%>
	<form action="dboxList" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>팀명</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
					<option value="4" <c:if test="${param.keyfield == 4}">selected</c:if>>제목+내용</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="찾기">
			</li>
		</ul>
		<div class="align-right">
			<select id="order" name="order">
				<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순</option>
				<option value="2" <c:if test="${param.order == 2}">selected</c:if>>등록순</option>
			</select>
			<script type="text/javascript">
				$('#order').change(function(){
					location.href='list?category=${param.categoty}'
									 +'&keyfield='+$('#keyfield').val()
									 +'&keyword='+$('#keyword').val()
									 +'&order='+$('#order').val();
				});
			</script>
		</div>
	</form>
	
	<br>
	<%-- 목록반복 --%>
	<div class="row row-cols-1 row-cols-md-4 g-3" id="output"></div>
	<div id="loading" style="display:none;">
		<img src="${pageContext.request.contextPath}/images/loading.gif" width="30" height="30">
	</div>
	
	<div class="paging-button" style="display:none;">
		<input type="button" value="더보기">
	</div>
</div>
<!-- 목록 끝 -->