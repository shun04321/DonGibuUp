<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Subscription Form</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>
<style>
	.payment-methods, .easypay-methods {
		display: flex;
		gap: 10px;
	}
	.payment-method, .easypay-method {
		padding: 10px 20px;
		border: 1px solid #ccc;
		border-radius: 5px;
		background-color: white;
		cursor: pointer;
		transition: background-color 0.3s;
		display: flex;
		align-items: center;
	}
	.payment-method.selected, .easypay-method.selected {
		background-color: #007bff;
		color: white;
		border-color: #007bff;
	}
	.payment-method input[type="radio"], .easypay-method input[type="radio"] {
		display: none;
	}
	
	button[type="submit"]{
		margin-left: 150px;
		padding : 0px 10px;
	}
	
	div{
		margin-bottom: 10px;
	}
	
</style>
</head>
<body>
	<div class="container">
		<div class="pay-button">
			<button id="paybutton" class="btn btn-info" data-bs-toggle="modal"
				data-bs-target="#staticBackdrop">정기기부 신청</button>
		</div>
		<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">
							<img
								src="${pageContext.request.contextPath}/upload/${category.dcate_icon}"
								width="30"> <span>${category.dcate_name}</span>
						</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<div class="modal-body">
						<form:form id="registerSubscription"
							modelAttribute="subscriptionVO" action="registerSubscription"
							method="post" style="width: 465px;">
							<input type="hidden" name="dcate_num"
								value="${category.dcate_num}">
							<!-- 익명 여부 체크박스 -->
							<div class="form-group">
								<label>익명 여부</label>
								<div class="form-check">
									<form:checkbox class="form-check-input" id="anonymousCheck"
										path="sub_annoy" />
									<label class="form-check-label" for="anonymousCheck">익명으로 기부</label>
								</div>
							</div>
							<!-- 기부자 이름 입력 필드 -->
							<div class="form-group">
								<label for="sub_name">기부자 이름</label>
								<form:input type="text" class="form-control" id="sub_name"
									path="sub_name" placeholder="성함이나 별명을 적어주세요." />
							</div>

							<!-- 기부 금액 라디오 버튼 -->
							<div class="form-group">
								<label>기부 금액</label>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio1"
										path="sub_price" value="3000" />
									<label class="form-check-label" for="radio1"> 유기견보호소
										3,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio2"
										path="sub_price" value="5000" />
									<label class="form-check-label" for="radio2"> 유기견보호소
										5,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio3"
										path="sub_price" value="10000" />
									<label class="form-check-label" for="radio3"> 유기견보호소
										10,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio4"
										path="sub_price" value="20000" />
									<label class="form-check-label" for="radio4"> 유기견보호소
										20,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio4"
										path="sub_price" value="30000" />
									<label class="form-check-label" for="radio4"> 유기견보호소
										30,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio5"
										path="sub_price" value="50000" />
									<label class="form-check-label" for="radio5"> 유기견보호소
										50,000원/월 지원 </label>
								</div>
							</div>
							<!-- 결제 수단 라디오 버튼 -->
							<div class="form-group">
								<label>결제 수단</label><br>
								<div class="payment-methods">
									<label class="payment-method" for="card">
										<form:radiobutton path="sub_method" id="card" value="card" />카드
									</label>
									<label class="payment-method" for="easy-pay">
										<form:radiobutton path="sub_method" id="easy-pay" value="easy-pay" />간편결제
									</label>
								</div>
							</div>
							
							<!-- 간편 결제 수단 라디오 버튼 -->
							<div class="form-group easypay-container" style="display: none;">
								<label>간편 결제</label><br>
								<div class="easypay-methods">
									<label class="easypay-method" for="kakao">
										<form:radiobutton path="easypay_method" id="kakao" value="카카오" /><img src="../upload/카카오 페이 로고.png" width="60">
									</label>
									<label class="easypay-method" for="toss">
										<form:radiobutton path="easypay_method" id="toss" value="toss" /><img src="../upload/토스 페이 로고.jpg" width="60">
									</label>
									<label class="easypay-method" for="naver">
										<form:radiobutton path="easypay_method" id="naver" value="네이버" /><img src="../upload/네이버 페이 로고.png" width="60">
									</label>
								</div>
							</div>
							<!-- 기부 날짜 선택 필드 -->
							<div class="form-group">
								<label for="sub_ndate">기부 날짜 선택</label>
									<form:input path="sub_ndate" id="sub_ndate" name="sub_ndate"/>
								<small id="dateHelp" class="form-text text-muted">
									매월 지정된 날짜에 기부가 이루어집니다.
									(1일에서 28일까지만 지정가능).
								</small>
							</div>
							
							<!-- 기부 시작하기 버튼 -->
							<button type="submit" class="btn btn-primary" style="margin-top: 10px;">기부 시작하기</button>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function () {
			$('.payment-method').click(function () {
				var radio = $(this).find('input[type="radio"]');
				if (radio.length) {
					radio.prop('checked', true);
					$('.payment-method').removeClass('selected');
					$(this).addClass('selected');
					
					if (radio.val() === 'easy-pay') {
						$('.easypay-container').slideDown();
					} else {
						$('.easypay-container').slideUp();
						$('.easypay-methods input[type="radio"]').prop('checked', false);
						$('.easypay-method').removeClass('selected');
					}
				}
			});
			//오늘 날짜 구하기
			function getCurrentDate() {
	            var today = new Date();
	            var dd = String(today.getDate()).padStart(2, '0'); // 일자
	            var mm = String(today.getMonth() + 1).padStart(2, '0'); // 월 (January is 0!)
	            var yyyy = today.getFullYear(); // 년도
	            return yyyy + '-' + mm + '-' + dd;
	        }

	        // 오늘 날짜를 기본으로 설정
	        $('#sub_date').val(getCurrentDate());

			$('.easypay-method').click(function () {
				var radio = $(this).find('input[type="radio"]');
				if (radio.length) {
					radio.prop('checked', true);
					$('.easypay-method').removeClass('selected');
					$(this).addClass('selected');
				}
			});
			
			
			// 익명 여부 체크박스 상태 변경 시 이벤트 처리
			$('#anonymousCheck').change(function () {
				if ($(this).is(':checked')) {
					$('#sub_name').prop('disabled', true); // 기부자 이름 입력 필드 비활성화
				} else {
					$('#sub_name').prop('disabled', false); // 기부자 이름 입력 필드 활성화
				}
			}); 
		});
	</script>
</body>
</html>
