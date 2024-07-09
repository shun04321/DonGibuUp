<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!-- 게시판 글상세 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<div class="page-main">
	<h2>${category.dcate_name}</h2>
	<ul class="detail-info">
		<li>
			${category.dcate_charity}
			<br>
		</li>
	</ul>
	<div class="detail-content">
		${category.dcate_content}
	</div>
	<hr size="1" width="100%">
	<div class="align-right">
		<input type="button" value="수정"
		   onclick="location.href='/category/updateCategory?dcate_num=${category.dcate_num}'">
		<input type="button" value="삭제" id="delete_btn">
		<script>
			const delete_btn = document.getElementById('delete_btn');
			delete_btn.onclick=function(){
				const choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('/category/deleteCategory?dcate_num=${category.dcate_num}');
				}
			};
		</script>   
		<input type="button" value="목록"
		                 onclick="location.href='list'">
	</div>
	<div class="pay-button">
	<button id="paybutton">정기기부 신청</button>
		<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
		<script type="text/javascript">
		   		 IMP.init("imp02085565");
		   		 const button = document.getElementById("paybutton");
		    	const onClickPay = async () => {
		        const amount = "5000";
		        const name = "구미 요양원 정기기부";
		
		        IMP.request_pay({
		            pg: 'kakaopay',  // 결제 수단
		            pay_method: 'card',  // 결제 방법
		            merchant_uid: 'order_no_0001',  // 주문 번호
		            name: '주문명:결제테스트',  // 주문명
		            amount: 1000,  // 금액
		            buyer_email: 'buyer@example.com',  // 구매자 이메일
		            buyer_name: '구매자이름',  // 구매자 이름
		            buyer_tel: '010-1234-5678',  // 구매자 전화번호
		            buyer_addr: '서울특별시 강남구 삼성동',  // 구매자 주소
		            buyer_postcode: '123-456',  // 구매자 우편번호
		        }, function (rsp) {
		            if (rsp.success) {
		                alert('결제가 완료되었습니다.');
		                // 성공 시 로직
		            } else {
		                alert('결제에 실패하였습니다. 에러내용: ' + rsp.error_msg);
		                // 실패 시 로직
		            }
		        });
		    	}
		    	button.addEventListener("click", onClickPay);
		    	
		</script>
	</div>
</div>



