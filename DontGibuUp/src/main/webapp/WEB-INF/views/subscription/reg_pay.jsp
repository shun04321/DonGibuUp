<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/subscription/reg_pay.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reg_pay.css" type="text/css">
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
							<input type="hidden" name="card_nickname" id="card_nickname">
							<input type="hidden" name="sub_date" id="sub_date"/>	
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
									<input type="text" class="form-control" id="newCardname" class="newCard" placeholder="새 카드 별명">
								</div>
							</div>

							<!-- 간편 결제 수단 라디오 버튼 -->
							<div class="form-group easypay-container" style="display: none;">
								<label>간편 결제</label><br>
								<div class="easypay_methods">
									<label class="easypay_method" for="kakao"> <form:radiobutton
											path="easypay_method" id="kakao" value="kakao" /><img
										src="../upload/카카오페이 로고.jpg" width="40" style="border-radius:25%">										
									</label> <label class="easypay_method" for="payco"> <form:radiobutton
											path="easypay_method" id="payco" value="payco" /><img
										src="../upload/페이코 로고.jpg" width="40" style="border-radius:25%">
									</label>
								</div>
							</div>
							 <!-- 오늘 날짜와 결제 일자 안내 -->
                            <div style="margin-top: 10px;">
                            	매월 <span id="paymentDateInfo" style="color:red; font-size:15 px;"></span>일에 정기 결제가 이루어집니다.
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
</body>
</html>

