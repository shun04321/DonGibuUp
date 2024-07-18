<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/admin/admin.faq.js"></script>
<div class="page-main">
	<h2>자주 하는 질문 (FAQ)</h2>
	<div>
		<a href="faq?">전체</a> |
		<a href="faq?category=0">정기기부</a> |
		<a href="faq?category=1">기부박스</a> |
		<a href="faq?category=2">챌린지</a> |
		<a href="faq?category=3">굿즈샵</a> |
		<a href="faq?category=4">기타</a>
	</div>
	<c:if test="${empty list}">
	<div class="result-display">등록된 질문이 없습니다.</div>
	</c:if>
	<c:if test="${!empty list}">
		<ul>
		<c:forEach var="faq" items="${list}">
			<li class="faq-item">
				<h4>Q.${faq.faq_question}</h4>
				<div>A.${faq.faq_answer}</div>
				<div class="align-right">
					<button>수정</button>
					<button onclick="delete?faq_num=${faq.faq_num}">삭제</button>				
				</div>
			</li>
		</c:forEach>
		</ul>
	</c:if>
	<form id="insert_faq">
		<input id="faq_question" name="faq_question" type="text" placeholder="질문">
		<span id="question_check_msg"></span>
		<textarea id="faq_answer" name="faq_answer" rows="5" cols="60" placeholder="내용"></textarea>
		<span id="answer_check_msg"></span>
		<div class="align-right">
			<input type="submit" value="등록">	
		</div>
	</form>
	<c:if test="${!empty list}">
	<div class="align-center">${page}</div>
	</c:if>
</div>
