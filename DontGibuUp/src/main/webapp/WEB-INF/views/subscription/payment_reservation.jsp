<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        IMP.init("imp02085565");

        // 초기화된 변수
        let pg = "";
        let pay_method = "";
	
        let cardNickname = "${payuidVO.card_nickname}"; // JSP 변수 사용
        let easypay_method = "${payuidVO.easypay_method}"; // JSP 변수 사용

        // payuidVO.card_nickname 값이 null이 아니고 빈 문자열이 아닌 경우
        if (cardNickname !== null && cardNickname !== "") {
            pg = "tosspayments.iamporttest_4";
            pay_method = "card";
        } else if (easypay_method === "kakao") {
            pg = "kakaopay.TCSUBSCRIP";
        } else if (easypay_method === "payco") {
            pg = "payco.AUTOPAY";
            pay_method = "EASY_PAY";
        }

        const onClickPay = async () => {
            IMP.request_pay({
                pg: pg,
                pay_method: pay_method,
                customer_uid: "${payuidVO.pay_uid}", // 필수 입력
                amount: ${subscriptionVO.sub_price},
                name: "정기기부 결제 예약",           
                customer_id: "${user.mem_num}", // 고객사가 회원에게 부여한 고유 ID
                schedules: 
                	[{ merchant_uid: "${subscriptionVO.sub_num}",
                      schedule_at: 1821106274,
                      currency: "KRW",
                      buyer_name: "${user.mem_name}",
                      buyer_email: "${user.mem_email}",
                      buyer_tel: "${user.mem_phone}",                      
                    }]
            }, function (rsp) {
                if (rsp.success) {
                    alert('정기결제 등록에 성공했습니다.');
                    location.href = '/category/detail?dcate_num='+${subscriptionVO.dcate_num};
                } else {
                    alert('결제 예약 실패이유 : '+rsp.error_msg);
                    location.href= '/category/detail?dcate_num='+${subscriptionVO.dcate_num};
                }
            });
        };

        // 페이지 로드 시 자동 실행
        onClickPay();
    });
</script>