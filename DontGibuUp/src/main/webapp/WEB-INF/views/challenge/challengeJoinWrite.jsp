<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script type="text/javascript">
        let chalTitle = "${challengeVO.chal_title}";
        var chalFee = ${challengeVO.chal_fee};
        let memberNick = "${member.mem_nick}";
        let memberEmail = "${member.email}";
        let memberNum = "${member.mem_num}";
        let pageContextPath = "${pageContext.request.contextPath}";
        let s_date = "${challengeVO.chal_sdate}"; //챌린지 시작 날짜 가져오기
        let mem_point = ${mem_point};
</script>
<script src="${pageContext.request.contextPath}/js/challenge/challenge.join.pay.js"></script>
<h2>챌린지 참가</h2>
<div class="join-container">
	<div class="line">
		<c:if test="${empty challengeVO.chal_photo}">
			<img src="${pageContext.request.contextPath}/images/챌린지_기본이미지.jpg"
				alt="챌린지 사진">
		</c:if>
		<c:if test="${!empty challengeVO.chal_photo}">
			<img
				src="${pageContext.request.contextPath}/upload/${challengeVO.chal_photo}"
				alt="챌린지 사진">
		</c:if>
		<div class="text-content">
			<h3>${challengeVO.chal_title}</h3>
			<c:if test="${challengeVO.chal_freq == 0}">
				<p>매일</p>
			</c:if>
			<c:if test="${challengeVO.chal_freq != 0}">
				<p>주 ${challengeVO.chal_freq}일</p>
			</c:if>
			<p>${challengeVO.chal_sdate}~ ${challengeVO.chal_edate}</p>
		</div>
	</div>
	<form:form id="challenge_join" enctype="multipart/form-data" modelAttribute="challengeJoinVO">
		<ul>
			<form:hidden path="chal_num" value="${challengeJoinVO.chal_num}" />
			<li>
				<label for="dcate_num">기부 카테고리</label> 
				<span id="charityInfo"></span> 
				<span id="dcate_num_error" class="error-color" style="display: none;">기부 카테고리를 선택하세요.</span>
			</li>
			<li>
				<c:forEach var="category" items="${categories}">
					<form:radiobutton path="dcate_num" value="${category.dcate_num}" label="${category.dcate_name}"
						data-charity="${category.dcate_charity}"/>
				</c:forEach>
			</li>
			<br>
			<li class="result-details">
				<p>
					100% 성공 <span class="right"><span class="chal_fee_90"></span>p
					+ 추가 <span class="chal_fee_5"></span>p 환급, 
					<span class="chal_fee_10"></span>원 기부</span>
				</p>
				<p>
					90% 이상 성공 <span class="right"><span class="chal_fee_90"></span>p 환급, <span class="chal_fee_10"></span>원 기부</span>
				</p>
				<p>
					90% 미만 성공 <span class="right">성공률만큼 환급, 나머지 기부</span>
				</p>
			</li>
		</ul>
		<br>
		<ul>
			<li>참여금 <span class="chal_fee"></span>원</li>
			<li>보유 포인트 <span class="mem-point">${mem_point}</span>p</li>
			<li>사용할 포인트 <input type="text" class="used-point" value="0">p</li>
			<hr width="100%" size="1" noshade="noshade">
			<li>결제금액 <span class="final_fee"></span>원</li>
		</ul>
		<br>
		<div class="align-center">
			결제 조건 및 서비스 약관에 동의합니다
			<p>
				<button type="button" id="pay2">결제하기</button>
		</div>
	</form:form>
</div>