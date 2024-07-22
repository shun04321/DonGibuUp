document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 필요한 초기 작업들
    let itemName = "${goods.item_name}";
    let itemPrice = "${goods.item_price}";
    let buyerName = "${sessionScope.user.mem_nick}";
    let itemNum = "${goods.item_num}";
    let pageContextPath = "${pageContext.request.contextPath}";  
});

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function buyNow() {
    IMP.init("imp63281573"); // 여기에 실제 IMP 코드 입력
    IMP.request_pay(
        {
            pg: "tosspayments", // 결제 대행사
            merchant_uid: "merchant_" + new Date().getTime(),
            name: itemName,
            pay_method: "card",
            amount: itemPrice,
            buyer_name: buyerName,
            currency: "KRW",
        },
        (rsp) => {
            if (!rsp.error_code) {
                // 결제 검증하기
                $.ajax({
                    url: pageContextPath + '/goods/paymentVerify/' + rsp.imp_uid,
                    method: 'POST',
                }).done(function(data) {
                    if (data.response.status == 'paid') {
                        // 결제 정보에 넣을 데이터 가공하기
                        let customData = JSON.parse(data.response.customData);

                         // 결제 정보 처리 및 완료하기
                        $.ajax({
                            url: pageContextPath + '/goods/purchaseComplete',
                            method: 'POST',
                            data: JSON.stringify({
                                imp_uid: rsp.imp_uid,
                                merchant_uid: rsp.merchant_uid,
                                amount: parseInt(data.response.amount, 10), // 정수로 변환
                                status: data.response.status,
                                item_num: parseInt(itemNum, 10), // 정수로 변환
                                item_name: itemName,
                                buyer_name: buyerName,
                            }),
                            contentType: 'application/json; charset=utf-8',
                            dataType: 'json',
                            success: function(param) {
                                if (param.result == 'success') {
                                    alert('구매가 완료되었습니다.');
                                    window.location.href = pageContextPath + '/goods/purchaseHistory';
                                } else {
                                    alert('구매 처리 중 오류가 발생했습니다.');
                                }
                            },
    error: function(xhr, status, error) {
        alert('구매 처리 오류 발생: ' + error);
    }
});
                    } else {
                        alert('결제 검증 중 오류가 발생했습니다.');
                    }
                }).fail(function(jqXHR, textStatus, errorThrown) {
					console.log('jqXHR:', jqXHR);
    				console.log('textStatus:', textStatus);
    				console.log('errorThrown:', errorThrown);
                    alert('결제 검증 요청 실패: ' + errorThrown);
                });
            } else {
                alert('결제에 실패했습니다: ' + rsp.error_msg);
            }
        }
    );
}