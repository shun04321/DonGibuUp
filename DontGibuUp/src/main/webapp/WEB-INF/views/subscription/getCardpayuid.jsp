<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        IMP.init("imp02085565");
        const onClickPay = async () => {
            IMP.request_pay({
                pg: "tosspayments.iamporttest_4",
                pay_method: "card", // 'card'만 지원됩니다.
                name: "최초인증결제",
                amout : "0", // 실제 승인은 발생되지 않고 오직 빌링키만 발급됩니다.
                customer_uid: "${payuidVO.pay_uid}", // 필수 입력.
                buyer_email: "${user.mem_email}",
                buyer_name: "${user.mem_name}",
                buyer_tel: "${user.mem_phone}",
                customer_id: "${user.mem_num}" // 고객사가 회원에게 부여한 고유 ID
            }, function (rsp) {
                if (rsp.success) {
                    alert('결제 수단을 등록했습니다.');
                    // 성공 시 로직
                } else {
                    alert('결제에 실패하였습니다. 에러내용: ' + rsp.error_msg);
                    // 실패 시 로직
                }
            });
        };

        // 페이지 로드 시 자동 실행
        onClickPay();
    });
</script>
