let totalPrice;
let quantity = parseInt(document.getElementById('quantity').value); // 수량 가져오기

document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 필요한 초기 작업들
    let itemName = "${goods.item_name}";
    let itemPrice = "${goods.item_price}";
    let buyerName = "${sessionScope.member.mem_nick}";
    let itemNum = "${goods.item_num}";
    let pageContextPath = "${pageContext.request.contextPath}";  
});

function formatNumber(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function buyNow() {
    $('#staticBackdrop').modal('show');
    
    // keyup마다 payTotal 함수 실행
    $(document).on('keyup', '.calculate', function() {
        payTotal();
    });

    function payTotal() {
        let quantity = parseInt(document.getElementById('quantity').value); // 수량 가져오기
        totalPrice = itemPrice * quantity; // 총 가격 계산
        console.log('Initial totalPrice:', totalPrice);
        
        let point = parseInt($('#goods_do_point').val());
        let mem_point = parseInt($('#mem_point').val());
        
        console.log('Point:', point);
        console.log('Mem_point:', mem_point);
        
        if (point > mem_point) { // 포인트가 보유 포인트보다 많을 시 보유 포인트로 값이 변경
            $('#goods_do_point').val(mem_point);
            point = mem_point;
        }
        
        $('#no').empty(); // span 메세지 초기화
        
        if (totalPrice == point) { // 총금액과 포인트가 같을시 금액 0 원
            totalPrice = 0;
        } else if (totalPrice > point) { // price 입력, point 미입력시 price값이 결제금액
            totalPrice -= point;
        } else if (totalPrice < point) {
            totalPrice = 0;
            $('#no').append('<small>기부금액보다 포인트가 클 수 없습니다.</small>');
        } else { // 정상 입력시 결제금액 : price-point
            totalPrice -= point;
        }
        
        console.log('Final totalPrice:', totalPrice);
        
        mem_point -= point;
        // 결제금액 #,###으로 노출
        $('#pay_sum').text(totalPrice.toLocaleString());
        // input hidden값을 결제금액으로 설정
        $('#pay_price').val(totalPrice);
    }
}
/*==========================================
*				결제 실행
*==========================================*/
function confirmPurchase() {
	
	console.log('confirmPurchase function called');
        console.log('totalPrice before processing:', totalPrice); // 전역 totalPrice 값 확인
        console.log('Quantity in confirmPurchase:', quantity); // 전역 quantity 값 확인
	
    let totalPricef = totalPrice;
    let buyerName = "${sessionScope.member.mem_nick}";
    
    console.log('TotalPrice in confirmPurchase:', totalPricef);

	
		//1.전액 포인트 결제 시
		if (totalPricef === 0 && parseInt($('#goods_do_point').val()) > 0) {
        if (confirm('전액 포인트로 결제하시겠습니까?')) {
            $.ajax({
                url: pageContextPath + '/goods/purchaseComplete',
                method: 'POST',
                data: JSON.stringify({
                    imp_uid: null, // 포인트 결제 시에는 결제 UID가 필요 없음
                    merchant_uid: "dongibuup",
                    amount: 0,
                    pay_status:0,
                    item_num: parseInt(itemNum, 10), // 정수로 변환
                    item_name: itemName,
                    buyer_name: buyerName,
                    point_used: parseInt($('#goods_do_point').val(), 10), // 사용된 포인트
                    quantity: quantity // quantity 값 추가
                }),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(param) {
                    if (param.result == 'logout') {
                        alert('로그인 후 사용하세요.');
                    } else if (param.result == 'success') {
                        alert('포인트 결제가 완료되었습니다.');
                        location.href = pageContextPath + '/goods/purchaseHistory';
                    }
                },
                error: function() {
                    alert('포인트 결제 오류 발생');
                }
            });	
			}
		//2.결제금액이 1~99원일 경우
		}else if(totalPricef >=1 && totalPricef <=99){
			alert('결제는 100원 이상부터 가능합니다.');
		//3.결제금액이 100원 이상일 경우 결제창 실행
		}else{
    IMP.init("imp63281573"); // 여기에 실제 IMP 코드 입력
    IMP.request_pay(
        {
            pg: "tosspayments", // 결제 대행사
            merchant_uid: "merchant_" + new Date().getTime(),
            name: itemName,
            pay_method: "card",
            amount: totalPricef,
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
                                quantity: quantity // quantity 값 추가
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
        });
 }
}

document.getElementById('buy_now_button').addEventListener('click', buyNow);
document.getElementById('confirm_purchase_button').addEventListener('click', confirmPurchase);
