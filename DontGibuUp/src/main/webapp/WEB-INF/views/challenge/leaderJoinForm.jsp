<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <h2>챌린지 참가하기</h2>
    <div>
        <img src="<c:url value='/images/${challenge.chal_photo}' />" alt="${challenge.chal_title}" />
        <h3>${challenge.chal_title}</h3>
        <p>${challenge.chal_freq}</p>
        <p>${challenge.chal_sdate} ~ ${challenge.chal_edate}</p>
        
        <p>참여금 <span id="chal_fee">${challenge.chal_fee}</span>원</p>
        <p>100% 성공 : <span class="chal_fee_90"></span>원 + 추가 (??)원 환급, <span class="chal_fee_10"></span>원 기부</p>
        <p>90% 이상 성공 : <span class="chal_fee_90"></span>원 환급, <span class="chal_fee_10"></span>원 기부</p>
        <p>90% 미만 성공 : 성공률만큼 환급, 나머지 기부</p>
    </div>
    <form:form action="${pageContext.request.contextPath}/challenge/join" id="challenge_join" enctype="multipart/form-data" modelAttribute="challengeJoinVO">
        <ul>
            <form:hidden path="chal_joi_status" value="0"/>
            <form:hidden path="chal_joi_date"/>
            <li>
                <form:label path="dcate_num">기부 카테고리</form:label>
                <form:select path="dcate_num" onchange="showCharityInfo(this)">
                    <option disabled="disabled" selected>선택하세요</option>
                    <c:forEach var="category" items="${categories}">
                        <form:option value="${category.dcate_num}" data-charity="${category.dcate_charity}">${category.dcate_name}</form:option>
                    </c:forEach>
                </form:select>
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
		setChallengePointRules();
    function showCharityInfo(selectElement) {
        var selectedOption = selectElement.options[selectElement.selectedIndex];
        var charityInfo = selectedOption.getAttribute('data-charity');
        document.getElementById('charityInfo').innerText = charityInfo || '';
    }

    function formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }

    function setChallengePointRules() {
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

        // 오늘 날짜 설정
        var today = new Date();
        var day = ('0' + today.getDate()).slice(-2);
        var month = ('0' + (today.getMonth() + 1)).slice(-2);
        var year = today.getFullYear();
        var todayString = year + '-' + month + '-' + day;
        document.querySelector('input[name="chal_joi_date"]').value = todayString;
    }
</script>