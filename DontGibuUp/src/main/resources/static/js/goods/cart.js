document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 필요한 초기 작업들
    let itemName = "${goods.item_name}";
    let itemPrice = "${goods.item_price}";
    let buyerName = "${sessionScope.user.mem_nick}";
    let itemNum = "${goods.item_num}";
    let pageContextPath = document.getElementById('contextPath').dataset.contextPath; 

   if (document.getElementById("purchaseButton")) {
        document.getElementById("purchaseButton").addEventListener("click", function() {
            purchaseSelectedCarts(pageContextPath); // 수정된 부분
        });
    }
});

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function purchaseSelectedCarts(pageContextPath) {
    var selectedCarts = [];
    $('input[name="cart_num"]:checked').each(function() {
        var cartNum = $(this).val();
        var quantity = $('#cart_quantity_' + cartNum).val();
        var price = parseInt($('#price_' + cartNum).text().replace(/,/g, ''), 10);
        selectedCarts.push({ item_num: parseInt(cartNum, 10), cart_quantity: parseInt(quantity, 10), price: price });
    });

    if (selectedCarts.length == 0) {
        alert('구매할 항목을 선택해주세요.');
        return;
    }

    let totalAmount = 0;
    selectedCarts.forEach(cart => {
        totalAmount += cart.price * cart.cart_quantity;
    });

    if (totalAmount <= 0) {
        alert('결제 금액이 0보다 커야 합니다.');
        return;
    }

    console.log("Total Amount: ", totalAmount);
    console.log("Selected Carts: ", selectedCarts);

    IMP.init("imp63281573");
    IMP.request_pay(
        {
            pg: "tosspayments",
            merchant_uid: "merchant_" + new Date().getTime(),
            name: "장바구니 구매",
            pay_method: "card",
            amount: totalAmount,
            buyer_name: "${sessionScope.user.mem_nick}",
            currency: "KRW",
        },
        (rsp) => {
             console.log("Payment Response: ", rsp);
            if (!rsp.error_code) {
                $.ajax({
                    url: pageContextPath + '/goods/paymentVerify/' + rsp.imp_uid, // 수정된 부분
                    method: 'POST',
                }).done(function(data) {
                    console.log("Verification Response: ", data);
                    if (data.response.status == 'paid') {
                        $.ajax({
                            url: pageContextPath + '/goods/purchaseFromCart', // 수정된 부분
                            method: 'POST',
                            data: JSON.stringify({
                                imp_uid: rsp.imp_uid,
                                merchant_uid: rsp.merchant_uid,
                                amount: parseInt(data.response.amount, 10),
                                status: data.response.status,
                                item_name: "장바구니 구매",
                                buyer_name: "${sessionScope.user.mem_nick}",
                                cart_items: selectedCarts,
                            }),
                            contentType: 'application/json; charset=utf-8',
                            dataType: 'json',
                            success: function(param) {
                                console.log("Purchase Complete Response: ", param);
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