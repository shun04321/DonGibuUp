const frequencyMap = {
    0: "매일",
    1: "주 1일",
    2: "주 2일",
    3: "주 3일",
    4: "주 4일",
    5: "주 5일",
    6: "주 6일"
};

document.addEventListener("DOMContentLoaded", function() {
    // 챌린지 인증 빈도 설정
    document.getElementById('chal_freq').innerText = frequencyMap[chalFreq] || chalFreq;

    // 참여금 및 환급/기부금 계산
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

function showCharityInfo(radioElement) {
    var charityInfo = radioElement.getAttribute('data-charity');
    var postPosition = getPostPosition(charityInfo);
    document.getElementById('charityInfo').innerText = "기부금은 " + charityInfo + postPosition + " 전달";
    document.getElementById('dcate_num_error').style.display = 'none';
}

function getPostPosition(charity) {
    const lastChar = charity.charAt(charity.length - 1);
    const lastCharCode = lastChar.charCodeAt(0);
    const hasJongseong = (lastCharCode - 0xac00) % 28 > 0;
    return hasJongseong ? '으로' : '로';
}

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

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
        name: chalTitle,
        pay_method: "card",
        escrow: false,
        amount: chalFee,
        tax_free: 3000,
        buyer_name: memberNick,
        buyer_email: memberEmail,
        buyer_tel: memberPhone,
        buyer_addr: "성수이로 20길 16",
        buyer_postcode: "04783",
        notice_url: pageContextPath + "/api/v1/payments/notice",
        confirm_url: pageContextPath + "/api/v1/payments/confirm",
        currency: "KRW",
        locale: "ko",
        custom_data: { userId: memberNum },
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