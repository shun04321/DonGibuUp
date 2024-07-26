document.addEventListener("DOMContentLoaded", function() {
	//기부 카테고리 자동 선택 없애기
	$('input[type="radio"]').prop('checked', false);

	// 참여금 및 환급/기부금 계산
	let chalFeeElement = document.querySelector('.chal_fee');
	let chalFee90Element = document.querySelectorAll('.chal_fee_90');
	let chalFee10Element = document.querySelectorAll('.chal_fee_10');
	let chalFee5Element = document.querySelectorAll('.chal_fee_5');

	if (chalFeeElement) {		
		var chalFee90 = (chalFee * 0.9).toFixed(0);
		var chalFee10 = (chalFee * 0.1).toFixed(0);
		var chalFee5 = (chalFee * 0.05).toFixed(0);

		chalFeeElement.innerText = formatNumber(chalFee);
		document.querySelector('.final_fee').textContent = formatNumber(chalFee);		

		chalFee90Element.forEach(function(e) {
			e.innerText = formatNumber(chalFee90);
		});
		chalFee10Element.forEach(function(e) {
			e.innerText = formatNumber(chalFee10);
		});
		chalFee5Element.forEach(function(e) {
			e.innerText = formatNumber(chalFee5);
		});
	}

	//chal_num 변수를 설정
	var chalNumElement = document.getElementById('chal_num');
	if (chalNumElement) {
		var chalNum = parseInt(chalNumElement.value, 10); // String을 숫자로 변환
	}
});

$(function() {
	//기부 카테고리 선택시 전달되는 기부처 표시
	$('input[type="radio"]').click(function() {
		$('.error-color').hide();
		let charityName = $(this).attr('data-charity');
		$('#charityInfo').text('기부금은 ' + charityName + '으로 전달').css('color', 'blue');
	});
	
	//사용할 포인트 및 최종결제 금액 확정
	$('.used-point').on('keyup',function(){
		let inputPoint = parseInt($(this).val());
		let totalPoint = parseInt($('.mem-point').text());
		//포인트에 문자열 입력 방지
		$(this).val($(this).val().replace(/[^0-9]/g, ''));
		//보유포인트 이상 입력시 최대 보유포인트로 변경
		if(inputPoint > totalPoint){
			$(this).val(totalPoint);
		}
		//최종 결제금액
		totalPayment($(this).val(),chalFee);
	});

	// 기부 카테고리 유효성 검사 후 결제
	$('#pay2').click(function() {
		if ($('input[name="dcate_num"]:checked').length < 1) {
			$('.error-color').show();
			return;
		}

		payAndEnroll2();
	});

	//기부 카테고리 유효성 검사 후 결제 (리더)
	$('#pay').click(function() {
		if ($('input[name="dcate_num"]:checked').length < 1) {
			$('.error-color').show();
			return;
		}
		$(window).off('beforeunload');
		payAndEnroll();
	});
});

function formatNumber(num) {
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function totalPayment(point,originalFee){
	var final_fee = parseInt(originalFee) - parseInt(point);
	if(final_fee <= 0 || isNaN(final_fee)) final_fee = parseInt(originalFee);
	$('.final_fee').text(formatNumber(final_fee));
}

function payAndEnroll2() {
	IMP.init("imp41500674");
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
			if (!rsp.error_code) {
				$.ajax({
					url: pageContextPath + '/challenge/paymentVerifyWrite/' + rsp.imp_uid,
					method: 'POST',
				}).done(function(data) {
					if (data.response.status == 'paid') {
						let customData = JSON.parse(data.response.customData);
						let dcate_num = $('input[name="dcate_num"]:checked').val();
						let chal_num = $('#chal_num').val(); // 숨겨진 필드에서 chal_num 가져오기

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
								alert('챌린지 신청 오류 발생: ' + error);
							}
						});
					} else if (data.response.status == 'failed') {
						alert('결제 위조 오류 발생!');
					} else {
						alert('결제 검증 중 오류가 발생했습니다.');
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert('결제 검증 요청 실패: ' + errorThrown);
				});
			} else {
				alert('결제에 실패했습니다: ' + rsp.error_msg);
			}
		}
	);
}

function payAndEnroll() {
	finalFee = $('.final_fee').val();
	usedPoints = $('.used-point').val();
	
	IMP.init("imp41500674");
	IMP.request_pay(
		{
			pg: "tosspayments",
			merchant_uid: "merchant_" + new Date().getTime(),
			name: chalTitle,
			pay_method: "card",
			amount: finalFee,
			buyer_name: memberNick,
			buyer_email: memberEmail,
			currency: "KRW",
			locale: "ko"		
		},
		(rsp) => {
			if (!rsp.error_code) {
				//결제 로직(리더): 결제 요청 -> 결제 검증 -> 결제 처리 및 완료 (REST API 이용)
				//결제 로직(회원): 사전 검증 -> 결제 요청 -> 사후 검증 -> 결제 처리 및 완료
				//OR 검증 구현 안하고 바로 처리하기

				//결제 검증하기
				$.ajax({
					url: '/challenge/paymentVerify/' + rsp.imp_uid,
					method: 'POST',
					data:JSON.stringify({chal_point:usedPoints}),
				}).done(function(data) {
					if (data.response.status == 'paid') {
						console.log('success');

						//결제 정보에 넣을 데이터 가공하기
						let dcate_num = $('input[type="radio"]').val();

						//결제 정보 처리 및 완료하기
						$.ajax({
							url: '/challenge/payAndEnroll',
							method: 'POST',
							data: JSON.stringify({
								od_imp_uid: rsp.imp_uid,
								chal_pay_price: data.response.amount,
								chal_point: usedPoints,
								chal_pay_status: 0,
								dcate_num: dcate_num
							}),
							contentType: 'application/json; charset=utf-8',
							dataType: 'json',
							success: function(param) {
								if (param.result == 'logout') {
									alert('로그인 후 사용해주세요.');
								} else if (param.result == 'success') {
									let sdate = new Date(param.sdate);
									let now = new Date();
									now.setHours(0, 0, 0, 0); // 시간 부분을 0으로 설정
									sdate.setHours(0, 0, 0, 0);
									if (sdate.getTime() == now.getTime()) {
										window.location.href = pageContextPath + '/challenge/join/list?status=on';
									} else if (sdate > now) {
										window.location.href = pageContextPath + '/challenge/join/list?status=pre';
									}
								}
							},
							error: function() {
								//챌린지 결제 취소하기!!메서드 넣기
								alert('챌린지 개설 오류 발생');
							}
						});
					} else if (data.response.status == 'failed') {
						alert('결제 위조 오류 발생!');
					} else {
						alert('오류가 발생했습니다.');
					}
				});
			} else {
				alert(`결제를 취소하셨습니다.`);
			}
		}
	);
}