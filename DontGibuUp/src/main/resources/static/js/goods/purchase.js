document.addEventListener("DOMContentLoaded", function () {
    // 결제 버튼 클릭 시 결제 처리
    $('#payButton').click(function () {
        if ($('input[name="dcate_num"]:checked').length < 1) {
            $('.error-color').show();
            return;
        }
        initiatePayment();
    });
});

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function initiatePayment() {
    IMP.init("imp71075330"); // 가맹점 식별코드

    IMP.request_pay({
        pg: "tosspayments",
        merchant_uid: "merchant_" + new Date().getTime(),
        name: itemName,
        pay_method: "card",
        escrow: false,
        amount: itemPrice,
        buyer_name: buyerName,
        buyer_email: buyerEmail,
        currency: "KRW",
        locale: "ko",
        custom_data: { usedPoints: 500 },
        appCard: false,
        useCardPoint: false,
        bypass: {
            tosspayments: {
                useInternationalCardOnly: false,
            },
        },
    }, function (rsp) {
        if (!rsp.error_code) {
            verifyPayment(rsp.imp_uid);
        } else {
            alert('결제를 취소하셨습니다.');
        }
    });
}

function verifyPayment(imp_uid) {
    $.ajax({
        url: '/goods/paymentVerify/' + imp_uid,
        method: 'POST',
    }).done(function (data) {
        if (data.response.status === 'paid') {
            processPayment(data);
        } else {
            alert('결제 위조 오류 발생!');
        }
    });
}

function processPayment(data) {
    let customData = JSON.parse(data.response.customData);
    let dcate_num = $('input[name="dcate_num"]:checked').val();

    $.ajax({
        url: '/goods/payAndEnroll',
        method: 'POST',
        data: JSON.stringify({
            od_imp_uid: data.response.imp_uid,
            pay_amount: data.response.amount,
            used_points: customData.usedPoints,
            pay_status: 0,
            dcate_num: dcate_num
        }),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (param) {
            if (param.result === 'logout') {
                alert('로그인 후 사용해주세요.');
            } else if (param.result === 'success') {
                alert('결제가 성공적으로 완료되었습니다.');
                window.location.href = '/goods/list';
            }
        },
        error: function () {
            alert('결제 처리 중 오류가 발생했습니다.');
        }
    });
}
