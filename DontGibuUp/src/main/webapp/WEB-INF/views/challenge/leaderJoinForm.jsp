<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script>
	let chalTitle = "${challenge.chal_title}";
	let chalFee = ${challenge.chal_fee};
	let memberNum = "${member.mem_num}";
	let memberNick = "${member.mem_nick}";
	let memberEmail = "${member.email}";
	//let memberPhone = "${member.phone}";
	let pageContextPath = "${pageContext.request.contextPath}";	
	let chalPhoto = "${challenge.chal_photo}";
</script>
<div class="container">
    <h2>챌린지 참가</h2>
    <div class="line">
    	<c:if test="${empty challenge.chal_photo}">
        		<img src="${pageContext.request.contextPath}/images/챌린지_기본이미지.jpg" alt="챌린지 사진">
        </c:if>
        <c:if test="${!empty challenge.chal_photo}">
        	<img src="${pageContext.request.contextPath}/upload/${challenge.chal_photo}" alt="챌린지 사진">
        </c:if>
        <div class="text-content">
        	<h3>${challenge.chal_title}</h3>
        	<c:if test="${challenge.chal_freq == 0}">
        		<p>매일</p>
        	</c:if>        
        	<c:if test="${challenge.chal_freq != 0}">
        		<p>주 ${challenge.chal_freq}일</p>
        	</c:if>
        	<p>${challenge.chal_sdate} ~ ${challenge.chal_edate}</p>
        </div>
    </div>
    <form:form action="leaderJoin" id="challenge_join" enctype="multipart/form-data" modelAttribute="challengeJoinVO">
        <ul>
            <li>
                <form:label path="dcate_num">기부 카테고리</form:label>
                <span class="error-color" style="display:none;">기부카테고리를 선택하세요</span>
            	<span id="charityInfo"></span>
            </li>
            <li>
                <c:forEach var="category" items="${categories}">
                    <form:radiobutton path="dcate_num" value="${category.dcate_num}" label="${category.dcate_name}" data-charity="${category.dcate_charity}"/>
                </c:forEach>
            </li>
            <li class="result-details">
        		<p>100% 성공 : <span class="chal_fee_90"></span>원 + 추가 (??)원 환급, <span class="chal_fee_10"></span>원 기부</p>
        		<p>90% 이상 성공 : <span class="chal_fee_90"></span>원 환급, <span class="chal_fee_10"></span>원 기부</p>
        		<p>90% 미만 성공 : 성공률만큼 환급, 나머지 기부</p>
            </li>
        </ul>
        <ul>
        	<li>참여금 <span id="chal_fee">${challenge.chal_fee}</span>원</li>
        	<li>보유 포인트 <span>포인트 표시할 예정</span></li>
        	<li>사용할 포인트 <input type="number"></li>
        	<hr width="100%" size="1" noshade="noshade">
        	<li>결제금액 <span>최종 금액 표시할 예정</span></li>
        </ul>
        <div class="align-center">
            결제 조건 및 서비스 약관에 동의합니다
            <button type="button" id="pay">결제하기</button>
        </div>
    </form:form>
</div>
<script>
	function handleBeforeUnload(e){
		e.preventDefault();
    	const url = '/challenge/deleteImage';
            
        // 전송할 데이터
        const data = new URLSearchParams();
        data.append('imagePath', pageContextPath + '/upload/'+chalPhoto);
            
        // 서버에 이미지 삭제 요청을 POST 방식으로 보냅니다.
        navigator.sendBeacon(url, data);	        
	}); 
	
	$(window).on('beforeunload', handleBeforeUnload);
</script>
<script src="${pageContext.request.contextPath}/js/challenge/challenge.join.pay.js"></script>