<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 목록 시작 -->
<script src="${pageContext.request.contextPath}/js/dbox/dbox.list.js"></script>

<section class="section-padding nanum">
	<div class="container">
		<div class="row">
			<%-- 기부박스 제안하기 버튼 --%>
			<div class="text-end mt-2 mb-2">
				<button type="button" class="btn btn-outline-dark btn-lg" onclick="location.href='${pageContext.request.contextPath}/dbox/propose'">기부박스 제안하기</button>
			</div>
			<%-- 카테고리 --%>
			<div id="category_output" class="text-center"></div>
			<%-- 검색 --%>
			<form action="list" id="search_form" method="get">
				<input type="hidden" name="category" value="${param.category}">
					<div class="row justify-content-center mb-2 mt-2">
						<!-- keyfield -->
						<div class="col-md-2">
							<select name="keyfield" id="keyfield" class="form-select">
								<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
								<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>팀명</option>
								<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
								<option value="4" <c:if test="${param.keyfield == 4}">selected</c:if>>제목+내용</option>
							</select>
						</div>
						<!-- keyword -->
						<div class="col-md-4">
							<input type="search" name="keyword" id="keyword" value="${param.keyword}" class="form-control">
						</div>
						
						<div class="col-md-2">
							<input type="submit" value="찾기" class="btn btn-success">
						</div>							
					</div>
					<!-- order -->
					<div class="col-md-2 float-end">
						<select class="form-select form-select-sm" aria-label="Small select example" id="order">
							<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순</option>
							<option value="2" <c:if test="${param.order == 2}">selected</c:if>>등록순</option>
						</select>
					</div>
			</form>
			
			<%-- 목록반복 --%>
			<div class="row row-cols-1 row-cols-md-4 g-3" id="output"></div>
			<div id="loading" style="display:none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif" width="30" height="30">
			</div>
			<div class="paging-button text-center" style="display:none;margin-top:3rem">
				<button type="button" class="btn btn-success col-4">더보기</button>
			</div>
		</div>
	</div>
</section>
<!-- 목록 끝 -->