<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>챌린지 참가하기</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
</head>
<body>
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
    <form:form id="challenge_join" enctype="multipart/form-data" modelAttribute="challengeJoinVO">
        <ul>
            <form:hidden path="chal_num"/>
            <li>
                <form:label path="dcate_num">기부 카테고리</form:label>
                <c:forEach var="category" items="${categories}">
                    <form:radiobutton path="dcate_num" value="${category.dcate_num}" label="${category.dcate_name}" data-charity="${category.dcate_charity}" onclick="showCharityInfo(this)"/>
                </c:forEach>
                <span id="dcate_num_error" class="error-color" style="display:none;">기부 카테고리를 선택하세요.</span>
            </li>
            <li>
                <label>기부처:</label>
                <span id="charityInfo"></span>
            </li>
        </ul>
        <div class="align-center">
            결제 조건 및 서비스 약관에 동의합니다
            <button type="button" onclick="validateAndPay()">결제하기</button>
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

    function validateAndPay() {
        var dcateNum = document.querySelector('input[name="dcate_num"]:checked');
        var errorSpan = document.getElementById('dcate_num_error');

        if (!dcateNum) {
            errorSpan.style.display = 'inline';
            return false;
        } else {
            errorSpan.style.display = 'none';
        }
        
        requestPay();
    }

    function requestPay() {
        const userCode = "imp41500674"; // 클라이언트 키
        IMP.init(userCode); // 모듈 초기화

        IMP.request_pay({
            pg: "tosspayments", // PG사 설정
            merchant_uid: "merchant_" + new Date().getTime(), // 고유 주문번호
            name: "${challengeVO.chal_title}",
            pay_method: "card",
            escrow: false,
            amount: document.getElementById('chal_fee').innerText.replace(/,/g, ''),
            tax_free: 3000,
            buyer_name: "${member.mem_nick}",
            buyer_email: "${member.email}",
            buyer_tel: "${member.phone}",
            buyer_addr: "성수이로 20길 16",
            buyer_postcode: "04783",
            notice_url: "${pageContext.request.contextPath}/api/v1/payments/notice",
            confirm_url: "${pageContext.request.contextPath}/api/v1/payments/confirm",
            currency: "KRW",
            locale: "ko",
            custom_data: { userId: "${member.mem_num}" },
            display: { card_quota: [0, 6] },
            appCard: false,
            useCardPoint: false,
            bypass: {
                tosspayments: {
                    useInternationalCardOnly: false // 영어 결제창 활성화
                }
            }
        }, function (rsp) {
        	console.log("결제 응답 객체: ", rsp); // 응답 객체 전체를 콘솔에 출력하여 디버깅

            if (rsp.success) {
                var form = document.getElementById('challenge_join');
                var imp_uid_input = document.createElement('input');
                imp_uid_input.setAttribute('type', 'hidden');
                imp_uid_input.setAttribute('name', 'od_imp_uid');
                imp_uid_input.setAttribute('value', rsp.imp_uid);
                form.appendChild(imp_uid_input);

                var amount_input = document.createElement('input');
                amount_input.setAttribute('type', 'hidden');
                amount_input.setAttribute('name', 'chal_pay_price');
                amount_input.setAttribute('value', rsp.paid_amount);
                form.appendChild(amount_input);

                form.submit();
            } else {
            	console.error('결제 실패 응답: ', rsp); // 실패 응답을 콘솔에 출력
                alert('결제에 실패하였습니다. 에러 내용: ' + (rsp.error_msg || '알 수 없는 오류'));
            	
                // 디버깅을 위한 추가 메시지 출력
                if (rsp.error_msg) {
                    console.error('에러 메시지: ', rsp.error_msg);
                } else {
                    console.error('알 수 없는 오류 발생');
                }
            }
        });
    }
</script>
</body>
</html>