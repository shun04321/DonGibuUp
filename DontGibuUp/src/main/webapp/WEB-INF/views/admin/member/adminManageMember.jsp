<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 회원 관리 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div class="page-main">
	<h2>회원 관리</h2>
	<form action="list" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>회원번호</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>이메일</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>닉네임</option>
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
				<option value="1" <c:if test="${param.order == 1}">selected</c:if>>가입일</option>
				<option value="2" <c:if test="${param.order == 2}">selected</c:if>>이메일</option>
			</select>
			<script type="text/javascript">
				$('#order').change(function() {
							location.href = 'list?&keyfield='
											+ $('#keyfield').val()
											+ '&keyword='
											+ $('#keyword').val()
											+ '&order=' + $('#order').val();
								});
			</script>
		</div>
	</form>
	<c:if test="${count == 0}">
	<div class="result-display">표시할 회원이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
	<table class="striped-table">
		<tr>
			<th>회원번호</th>
			<th>이메일</th>
			<th>닉네임</th>
			<th>가입일</th>
			<th>계정상태</th>
			<th>관리</th>
		</tr>
		<c:forEach var="member" items="${list}">
		<tr>
			<td class="align-center">${member.mem_num}</td>
			<td class="align-left"><a href="detail?mem_num=$member.mem_num}">${member.mem_email}</a></td>
			<td class="align-center">${member.mem_nick}</td>
			<td class="align-center">${member.mem_date}</td>
			<td class="align-center">${member.mem_status}</td>
			<td class="align-center"><span>제명</span> <span>정지</span> <span>사용</span></td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${page}</div>
	</c:if>
</div>
<!-- 회원 목록 끝 -->