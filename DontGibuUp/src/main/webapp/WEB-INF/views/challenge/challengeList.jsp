<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/challenge/challenge.list.js"></script>
<script>
	let pageContext = "${pageContext.request.contextPath}";
</script>

<div class="nanum">
	<div class="align-center">
		<a href="list">참가 가능한 챌린지 목록</a> <a href="pastList">지난 챌린지 목록</a>
	</div>
	<div class="align-center">
		<a href="#" class="category-link" data-category="">전체</a>
		<c:forEach var="cate" items="${categories}" varStatus="status">
			<a href="#" class="category-link" data-category="${cate.ccate_num}">${cate.ccate_name}</a>
		</c:forEach>
	</div>
	<form id="searchTitle">
		<ul class="align-right">
			<li>
				<%-- 인기순 -> 좋아요 완료시 적용 가능, 참여인원수 -> 참가 인원 생성후 적용 가능 --%> 
				<select class="order" name="order">
					<option value="0" <c:if test="${param.order == 0}">selected</c:if>>최신순</option>
					<option value="1" <c:if test="${param.order == 1}">selected</c:if>>인기순</option>
					<option value="2" <c:if test="${param.order == 2}">selected</c:if>>시작일순</option>
					<option value="3" <c:if test="${param.order == 3}">selected</c:if>>참여인원순</option>
				</select>
			</li>
			<li class="align-left"><select class="freqOrder" name="freqOrder">
					<option value=""
						<c:if test="${empty param.freqOrder}">selected</c:if>>전체
						인증빈도</option>
					<option value="0"
						<c:if test="${param.freqOrder == 0}">selected</c:if>>매일</option>
					<option value="1"
						<c:if test="${param.freqOrder == 1}">selected</c:if>>주1일</option>
					<option value="2"
						<c:if test="${param.freqOrder == 2}">selected</c:if>>주2일</option>
					<option value="3"
						<c:if test="${param.freqOrder == 3}">selected</c:if>>주3일</option>
					<option value="4"
						<c:if test="${param.freqOrder == 4}">selected</c:if>>주4일</option>
					<option value="5"
						<c:if test="${param.freqOrder == 5}">selected</c:if>>주5일</option>
					<option value="6"
						<c:if test="${param.freqOrder == 6}">selected</c:if>>주6일</option>
			</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}" placeholder="제목을 입력하세요">
				<input type="submit" value="찾기">
			</li>
		</ul>
	</form>
	<div>
		<c:if test="${!empty user}">
			<input type="button" value="챌린지 개설하기" onclick="location.href='write'">
		</c:if>
	</div>
	
	<section class="section-padding">
		<div class="container">
			<div id="output" class="row"></div>
		</div>
	</section>

</div>