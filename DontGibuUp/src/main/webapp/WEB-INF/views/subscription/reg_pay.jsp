<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

button[type="submit"] {
	margin-left: 150px;
	padding: 0px 10px;
}

div {
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
							<input type="hidden" name="mem_num"
								value="${user.mem_num}" id="mem_num">
							<!-- 익명 여부 체크박스 -->
							<div class="form-group">
								<label>익명 여부</label>
								<div class="form-check">
									<form:checkbox class="form-check-input" id="anonymousCheck"
										path="sub_annoy" />
									<label class="form-check-label" for="anonymousCheck">익명으로
										기부</label>
								</div>
							</div>
							<!-- 기부자 이름 입력 필드 -->
							<div class="form-group">
								<label for="sub_name">기부자명</label>
								<form:input type="text" class="form-control" id="sub_name"
									path="sub_name" value="${user.mem_nick}"/>
							</div>

							<!-- 기부 금액 라디오 버튼 -->
							<div class="form-group">
								<label>기부 금액</label>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio1"
										path="sub_price" value="3000" />
									<label class="form-check-label" for="radio1"> ${category.dcate_charity}
										3,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio2"
										path="sub_price" value="5000" />
									<label class="form-check-label" for="radio2"> ${category.dcate_charity}
										5,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio3"
										path="sub_price" value="10000" />
									<label class="form-check-label" for="radio3"> ${category.dcate_charity}
										10,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio4"
										path="sub_price" value="20000" />
									<label class="form-check-label" for="radio4"> ${category.dcate_charity}
										20,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio4"
										path="sub_price" value="30000" />
									<label class="form-check-label" for="radio4"> ${category.dcate_charity}
										30,000원/월 지원 </label>
								</div>
								<div class="form-check">
									<form:radiobutton class="form-check-input" id="radio5"
										path="sub_price" value="50000" />
									<label class="form-check-label" for="radio5"> ${category.dcate_charity}
										50,000원/월 지원 </label>
								</div>
								<form:errors path="sub_price" cssClass="text-danger"/>
							</div>
							
							<!-- 결제 수단 라디오 버튼 -->
							<div class="form-group">
								<label>결제 수단</label><br>
								<div class="payment-methods">
									<label class="payment-method" for="card"> <form:radiobutton
											path="sub_method" id="card" value="card"/>카드
									</label> <label class="payment-method" for="easy_pay"> <form:radiobutton
											path="sub_method" id="easy_pay" value="easy_pay" />간편결제
									</label>
								</div>
							</div>

							<!-- 이미 등록된 카드 목록, 새 카드 등록 -->
							<div id="card-options"  style="display: none;">
								<c:forEach var="card" items="${list}">
									<c:if test="${!empty card.card_nickname}">
										<div>
											<input type="radio" class="oldCard"
												name="selectedCard" value="${card.card_nickname}"><label
												for="card_${card.card_nickname}"> ${card.card_nickname}</label>
										</div>
									</c:if>
								</c:forEach>
								<div>
									<input type="radio" id="newCard" name="selectedCard" 
										value="newCard"> <label for="newCard">새 카드 등록</label>
								</div>
								<!-- 새 카드 별명 입력 필드 -->
								<div id="newCardNickname" style="display: none;">
									<input type="text" class="form-control" id="newCardname" name="card_nickname" class="newCard" placeholder="새 카드 별명">
								</div>
							</div>

							<!-- 간편 결제 수단 라디오 버튼 -->
							<div class="form-group easypay-container" style="display: none;">
								<label>간편 결제</label><br>
								<div class="easypay-methods">
									<label class="easypay-method" for="kakao"> <form:radiobutton
											path="easypay_method" id="kakao" value="kakao" /><img
										src="../upload/카카오페이 로고.jpg" width="40" style="border-radius:25%">										
									</label> <label class="easypay-method" for="payco"> <form:radiobutton
											path="easypay_method" id="payco" value="payco" /><img
										src="../upload/페이코 로고.jpg" width="40" style="border-radius:25%">
									</label>
								</div>
							</div>


							<!-- 기부 시작하기 버튼 -->
							<button type="submit" class="btn btn-primary"
								style="margin-top: 10px;">기부 시작하기</button>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
	$(document).ready(function () {
		// 익명 여부 체크박스 상태 변경 시 이벤트 처리
		$('#anonymousCheck').change(function() {
				if ($(this).is(':checked')) {
					$('#sub_name').val('익명');
				} else {
					$('#sub_name').val('${user.mem_nick}');
				}
			});

		// 결제 수단 변경 시 이벤트 처리
	    $('.payment-method').click(function() {
	        var radio = $(this).find('input[type="radio"]');
	        if (radio.length) {
	            radio.prop('checked', true);
	            $('.payment-method').removeClass('selected');
	            $(this).addClass('selected');
	            
	            if (radio.val() === 'easy_pay') { //간편결제 선택시
	                $('#card-options').slideUp();
	                $('.easypay-container').slideDown();
	                // 선택된 카드의 체크 해제
	                $('input[name="selectedCard"]').prop('checked', false);
	                $('#newCardname').val('');
	                $('#newCardNickname').slideUp();
	            } else { 		//카드 결제 선택시
	                $('.easypay-container').slideUp();
	                $('#card-options').slideDown();
	             // 선택된 이지페이 수단 체크 해제
	                $('.easypay-method').removeClass('selected');
	            }
	        }
	    });

	    // 새 카드 등록 클릭 시 이벤트 처리 
		$('#newCard').click(function() {
                    $('#newCardNickname').slideDown();
        $('.oldCard').click(function(){
                    $('#newCardNickname').slideUp(function() {
                        $('#newCardname').val('');
                    });
				});
			});
	    
		$('.easypay-method').click(function() {
			var radio = $(this).find('input[type="radio"]');
			if (radio.length) {
				radio.prop('checked', true);
				$('.easypay-method').removeClass('selected');
				$(this).addClass('selected');
			}
		});

			//조건체크
			$('#registerSubscription').submit(function(event) {
				// 기부 금액 체크박스 중 하나가 선택되었는지 확인
				if (!$("input[name='sub_price']").is(":checked")) {
					alert("기부 금액을 선택해주세요.");
					return false;
				}
				if (!$("input[name='sub_method']").is(":checked")) {
					alert("결제수단을 선택해주세요");
					return false;
				}
				if (!$("input[type='checkbox']").is(":checked")) {
					if ($('#sub_name').val().trim() == '') {
						alert("익명 기부 선택이나 기부하실 이름을 입력해주세요.");
						return false;
					}
				}
				// 간편결제를 선택했으나, 간편결제 플랫폼을 선택하지 않은 경우
				if($('#easy_pay').is(":checked") && !$("input[name='easypay_method']").is(":checked")){
					alert('사용하실 간편결제 플랫폼을 선택해주세요.');
					return false;
				}
				// 카드를 선택했으나, 어떤 카드를 사용할지 선택하지 않은 경우
				if($('#card').is(":checked") && !$("input[name='selectedCard']").is(":checked")){
					alert('사용하실 카드를 선택해주세요.');
					return false;
				}
			});

			$('#paybutton').click(function() {
				// mem_num의 값을 가져옴
				var mem_num = $('#mem_num').val();
				// 만약 mem_num이 비어있으면(로그인되어 있지 않으면)
				if (!mem_num) {
					// 로그인 페이지로 리다이렉트
					window.location.href = '/member/login'; // 로그인 페이지의 URL로 수정 필요
				} else {
					// mem_num이 존재하면 정상적으로 기부 시작하기 로직 실행
					$('#staticBackdrop').modal('show'); // 모달 창 열기 등
				}
			});
		});
	</script>
</body>
</html>

