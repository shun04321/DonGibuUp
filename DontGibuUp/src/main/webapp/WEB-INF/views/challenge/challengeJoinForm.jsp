<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <h2>챌린지 참가하기</h2>
    <div class="line">
        <img src="<c:url value='/images/${challengeVO.chal_photo}' />" alt="${challengeVO.chal_title}" />
        <h3>${challengeVO.chal_title}</h3>
        <p>${challengeVO.chal_freq}</p>
        <p>${challengeVO.chal_sdate} ~ ${challengeVO.chal_edate}</p>
        
        <p>참여금 <span id="chal_fee">${challengeVO.chal_fee}</span>원</p>
        <p>100% 성공 : <span class="chal_fee_90"></span>원 + 추가 (??)원 환급, <span class="chal_fee_10"></span>원 기부</p>
        <p>90% 이상 성공 : <span class="chal_fee_90"></span>원 환급, <span class="chal_fee_10"></span>원 기부</p>
        <p>90% 미만 성공 : 성공률만큼 환급, 나머지 기부</p>
    </div>
    <form:form action="${pageContext.request.contextPath}/challenge/join" id="challenge_join" enctype="multipart/form-data" modelAttribute="challengeJoinVO">
        <ul>
            <form:hidden path="chal_num" value="${challengeJoinVO.chal_num}"/>
            <li>
                <form:label path="dcate_num">기부 카테고리</form:label>
                <c:forEach var="category" items="${categories}">
                    <form:radiobutton path="dcate_num" value="${category.dcate_num}" label="${category.dcate_name}" data-charity="${category.dcate_charity}" onclick="showCharityInfo(this)"/>
                </c:forEach>
                <form:errors path="dcate_num" cssClass="error-color"/>
            </li>
            <li>
                <label>기부처:</label>
                <span id="charityInfo"></span>
            </li>
        </ul>
        <div class="align-center">
            결제 조건 및 서비스 약관에 동의합니다
            <form:button>결제하기</form:button>
        </div>
    </form:form>
</div>

<script>
    function showCharityInfo(radioElement) {
        var charityInfo = radioElement.getAttribute('data-charity');
        document.getElementById('charityInfo').innerText = charityInfo || '';
    }

    function formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }

    document.addEventListener("DOMContentLoaded", function() {
        let chalFeeElement = document.getElementById('chal_fee');
        let chalFee90Element = document.querySelectorAll('.chal_fee_90');
        let chalFee10Element = document.querySelectorAll('.chal_fee_10');

        if (chalFeeElement) {
            var chalFee = parseInt(chalFeeElement.innerText.replace(/,/g, ''), 10);
            var chalFee90 = (chalFee * 0.9).toFixed(0);
            var chalFee10 = chalFee - chalFee90;

            chalFeeElement.innerText = formatNumber(chalFee);
            
            chalFee90Element.forEach(function(e){
                e.innerText = formatNumber(chalFee90);
            });
            chalFee10Element.forEach(function(e){
                e.innerText = formatNumber(chalFee10);
            });
        }
    });
</script>