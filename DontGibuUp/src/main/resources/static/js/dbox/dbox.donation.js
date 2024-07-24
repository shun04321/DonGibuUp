$(function(){
	
	
	const button = document.getElementById('imp_donation');
	const form = document.getElementById('dbox_donation');

	//결제금액 총합
	$(document).on('keyup','.caculate',function(){
		payTotal();
	});
	function payTotal(){
		let total = 0;
		let pt = parseInt($('#dbox_do_point').val());
		let pr = parseInt($('#dbox_do_price').val());
		$('#no').empty();
		if(isNaN(pr) && !isNaN(pt)){
			total = 0;
		}else if(isNaN(pr) && isNaN(pt)){
			total = 0;
		}else if(!isNaN(pr) && isNaN(pt)) {
			total =  pr;
		}else if(pr < pt){
			total = 0;
			$('#no').append('<small>기부금액보다 포인트가 클 수 없습니다.</small>');
		}else{
			total = pr-pt;			
		}
		
		$('#pay_sum').text(total.toLocaleString());
		$('#pay_price').val(total);
	}
	
	const onClickPay = async () =>{
        const dbox_num = document.getElementById('dbox_num').value;
        const price = document.getElementById('dbox_do_price').value;
        const comment = document.getElementById('dbox_do_comment').value;
       	
        let point = document.getElementById('dbox_do_point').value;
       	//포인트 미입력시 0으로 설정
       	if (point==null || point==''){
			point='0';
		}
       	
       	let annony = 0;
       	if($('.annony:checked').length==1){
			annony = 1;
		}  
		let pay_price =$('#pay_price').val();
		
		console.log("dbox_num : " + dbox_num);
		console.log("pay_price : " + pay_price);
		console.log("price : " + price);
		console.log("point : " + point);
		console.log("comment : " + comment);
		console.log("annony : " + annony);
		
        IMP.init("imp04826433");
		IMP.request_pay({
			pg:"tosspayments.iamporttest_3",
			pay_method:"card",
			amount:pay_price,
			name:"기부하기",
			merchant_uid:"merchant_" + new Date().getTime(),
			currency:"KRW"
		}, function(response){
			if(!response.error_code){
				$.ajax({
					url:'/dbox/payment/' + response.imp_uid,
					method: 'POST',
				}).done(function(data){
					if(data.response.status=='paid'){
						form.submit();
						$.ajax({
							url:'/dbox/donation',
							method:'POST',
							data:JSON.stringify({
								dbox_num:dbox_num,
	 							pay_price:data.response.amount,
	 							price:price,
	 							point:point,
								dbox_imp_uid:response.imp_uid,
	 							comment:comment,
								pay_status:0,
								annony:annony,
							}),
							contentType:'application/json; charset=utf-8',
							dataType:'json',
							success: function(param){
								if(param.result == 'logout'){
									alert('로그인 후 사용하세요.');
								}else if(param.result == 'success'){
									alert('기부 하기 결제가 완료되었습니다.');
								}
							},
							error:function(xhr, status, error){
								alert('기부하기 오류 발생 : ' + error)
							}
						});
					}else if(data.response.status=='failed'){
						alert('결제 위조 오류');
					}else{
						alert('결제 검증 오류');
					}
				}).fail(function(jqXHR,textStatus,errorThrown){
					alert('결제 검증 요청 실패 : ' + errorThrown)
				});
			}else{
				alert('결제에 실패했습니다. : ' + response.error_msg)
			}
			
		});
	};
	
	button.addEventListener("click", onClickPay);
});
 