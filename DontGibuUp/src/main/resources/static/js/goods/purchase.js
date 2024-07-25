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
    $('#staticBackdrop').modal('show');
    
    // keyup마다 payTotal 함수 실행
    $(document).on('keyup', '.calculate', function() {
        payTotal();
    });

    

    function payTotal() { 
		let quantity = parseInt(document.getElementById('quantity').value); // 수량 가져오기
   		let totalPrice = itemPrice * quantity; // 총 가격 계산  
        let point = parseInt($('#goods_do_point').val());
        let mem_point = parseInt($('#mem_point').val());
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
			totalPrice=0;
            $('#no').append('<small>기부금액보다 포인트가 클 수 없습니다.</small>');
        } else { // 정상 입력시 결제금액 : price-point
            totalPrice -= point;            
        }
        mem_point -= point;
        // 결제금액 #,###으로 노출
        $('#pay_sum').text(totalPrice.toLocaleString());
        // input hidden값을 결제금액으로 설정
        $('#pay_price').val(totalPrice);
    }
}

function confirmPurchase() {
    let totalPrice = parseInt($('#pay_price').val());
    let itemName = "${goods.item_name}";
    let buyerName = "${sessionScope.user.mem_nick}";
    let itemNum = "${goods.item_num}";
    let pageContextPath = "${pageContext.request.contextPath}";  


	/*//1.전액 포인트 결제 시
		if(price >0 && point==price){
			if(confirm('전액 포인트로 기부하시겠습니까?')){
				$.ajax({//전액 포인트 기부 ajax
					url:'/dbox/donation',
					method:'POST',
					data:JSON.stringify({/전액 포인트 결제시 imp_uid를 Mapper에서 등록제외시켜야함.
						dbox_num:dbox_num,//기부박스 번호
						price:price,//기부금액
						point:point,//포인트
						comment:comment,//남길말
						pay_status:0,//결제완료:0
						annony:annony,//익명
					}),
					contentType:'application/json; charset=utf-8',
					dataType:'json',
					success: function(param){
						if(param.result == 'logout'){
							alert('로그인 후 사용하세요.');
						}else if(param.result == 'success'){
							alert('기부 하기가 완료되었습니다.');
							form.submit();
						}
					},
					error:function(){
						alert('기부하기 오류 발생');
					}
*/


    IMP.init("imp63281573"); // 여기에 실제 IMP 코드 입력
    IMP.request_pay(
        {
            pg: "tosspayments", // 결제 대행사
            merchant_uid: "merchant_" + new Date().getTime(),
            name: itemName,
            pay_method: "card",
            amount: totalPrice,
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

document.getElementById('buy_now_button').addEventListener('click', buyNow);
document.getElementById('confirm_purchase_button').addEventListener('click', confirmPurchase);
