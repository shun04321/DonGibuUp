<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('.faq-question-text').on('click', function() {
            $(this).next('.faq-answer-text').toggle(); // 클릭된 질문의 다음 형제 요소인 faq-answer-text를 토글
        });
    });
</script>
<section class="news-detail-header-section text-center">
	<div class="section-overlay"></div>

	<div class="container">
		<div class="row">

			<div class="col-lg-12 col-12">
				<h1 class="text-white">News Listing</h1>
			</div>

		</div>
	</div>
</section>
<div class="page-main">
	<h2>자주 하는 질문 (FAQ)</h2>
	<div class="align-right">
		<button onclick="location.href='inquiry'">1:1 문의하기</button>
	</div>
	<div>
		<a href="faqlist?">전체</a> | <a href="faqlist?category=0">정기기부</a> | <a
			href="faqlist?category=1">기부박스</a> | <a href="faqlist?category=2">챌린지</a>
		| <a href="faqlist?category=3">굿즈샵</a> | <a href="faqlist?category=4">기타</a>
	</div>
	<c:if test="${empty list}">
		<div class="result-display">등록된 질문이 없습니다.</div>
	</c:if>
	<c:if test="${!empty list}">
		<ul id="faq_list">
			<c:forEach var="faq" items="${list}">
				<li class="user-faq-item">
					<h4 class="faq-question-text">Q. ${faq.faq_question}</h4>
					<div class="faq-answer-text" style="display: none">A.
						${faq.faq_answer}</div>
				</li>
			</c:forEach>
		</ul>
	</c:if>
</div>
