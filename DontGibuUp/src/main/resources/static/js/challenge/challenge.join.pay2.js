document.addEventListener("DOMContentLoaded", function() {
    // 기부 카테고리 자동 선택 해제
    $('input[type="radio"]').prop('checked', false);

    // 참여금 및 환급/기부금 계산
    let chalFeeElement = document.getElementById('chal_fee');
    let chalFee90Element = document.querySelectorAll('.chal_fee_90');
    let chalFee10Element = document.querySelectorAll('.chal_fee_10');

    if (chalFeeElement) {
        var chalFee = parseInt(chalFeeElement.innerText.replace(/,/g, ''), 10);
        var chalFee90 = (chalFee * 0.9).toFixed(0);
        var chalFee10 = chalFee - chalFee90;

        chalFeeElement.innerText = formatNumber(chalFee);

        chalFee90Element.forEach(function(e) {
            e.innerText = formatNumber(chalFee90);
        });
        chalFee10Element.forEach(function(e) {
            e.innerText = formatNumber(chalFee10);
        });
    }
    
   // chal_num 변수를 설정
    var chalNumElement = document.getElementById('chal_num');
    if (chalNumElement) {
        var chalNum = parseInt(chalNumElement.value, 10); // String을 숫자로 변환
    }
});

// 기부 카테고리 선택 시 전달되는 기부처 표시
$('input[type="radio"]').click(function() {
    $('.error-color').hide();
    let charityName = $(this).attr('data-charity');
    $('#charityInfo').text('기부금은 ' + charityName + '으로 전달').css('color', 'blue');
});

// 기부 카테고리 유효성 검사 후 결제
$('#pay').click(function() {
    if ($('input[name="dcate_num"]:checked').length < 1) {
        $('.error-color').show();
        return;
    }

    payAndEnroll();
});

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function payAndEnroll() {
    IMP.init("imp41500674");

    console.log("결제 요청 준비 중...");

    IMP.request_pay(
        {
            pg: "tosspayments",
            merchant_uid: "merchant_" + new Date().getTime(),
            name: chalTitle,
            pay_method: "card",
            amount: chalFee,
            buyer_name: memberNick,
            buyer_email: memberEmail,
            currency: "KRW",
            custom_data: { usedPoints: 500 },
        },
        (rsp) => {
            console.log("결제 요청 응답 받음:", rsp);

            if (!rsp.error_code) {
                console.log("결제 성공, 서버에 검증 요청 중...");

                $.ajax({
                    url: pageContextPath + '/challenge/paymentVerifyWrite/' + rsp.imp_uid,
                    method: 'POST',
                }).done(function(data) {
                    console.log("결제 검증 응답 받음:", data);

                    if (data.response.status == 'paid') {
                        let customData = JSON.parse(data.response.customData);
                        let dcate_num = $('input[name="dcate_num"]:checked').val();
                        let chal_num = $('#chal_num').val(); // 숨겨진 필드에서 chal_num 가져오기

                        console.log("결제 검증 성공, 서버에 참가 정보 저장 요청 중...");

                        $.ajax({
                            url: pageContextPath + '/challenge/payAndEnrollWrite',
                            method: 'POST',
                            data: JSON.stringify({
                                od_imp_uid: rsp.imp_uid,
                                chal_pay_price: data.response.amount,
                                chal_point: customData.usedPoints,
                                chal_pay_status: 0,
                                dcate_num: dcate_num,
                                chal_num: chal_num, // 챌린지 번호 포함
                                sdate: sdate // 챌린지 시작 날짜 포함
                            }),
                            contentType: 'application/json; charset=utf-8',
                            dataType: 'json',
                            success: function(param) {
                                console.log("참가 정보 저장 응답 받음:", param);
								console.log(param.result);
								
                                if (param.result == 'logout') {
                                    alert('로그인 후 사용해주세요.');
                                } else if (param.result == 'success') {
                                    let sdate = new Date(param.sdate);
                                    let now = new Date();
                                    now.setHours(0, 0, 0, 0);
                                    sdate.setHours(0, 0, 0, 0);
                                    if (sdate.getTime() == now.getTime()) {
                                        window.location.href = pageContextPath + '/challenge/join/list?status=on';
                                    } else if (sdate > now) {
                                        window.location.href = pageContextPath + '/challenge/join/list?status=pre';
                                    }
                                } else {
                                    alert('참가 정보 저장 중 오류가 발생했습니다.');
                                }
                            },
                            error: function(xhr, status, error) {
                                console.error("참가 정보 저장 오류:", error);
                                alert('챌린지 신청 오류 발생: ' + error);
                            }
                        });
                    } else if (data.response.status == 'failed') {
                        console.error("결제 검증 실패:", data.response.failReason);
                        alert('결제 위조 오류 발생!');
                    } else {
                        console.error("결제 검증 중 오류 발생");
                        alert('결제 검증 중 오류가 발생했습니다.');
                    }
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    console.error("결제 검증 요청 실패:", textStatus, errorThrown);
                    alert('결제 검증 요청 실패: ' + errorThrown);
                });
            } else {
                console.error("결제 오류:", rsp.error_msg);
                alert('결제에 실패했습니다: ' + rsp.error_msg);
            }
        }
    );
}