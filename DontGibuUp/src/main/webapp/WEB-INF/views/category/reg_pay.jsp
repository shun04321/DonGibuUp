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
							<input type="hidden" name="dcate_num" value="${category.dcate_num}">
							<!-- 익명 여부 체크박스 -->
							<div class="form-group">
								<label>익명 여부</label>
								<div class="form-check">
									<form:checkbox class="form-check-input" id="anonymousCheck"
										path="sub_annoy"/>
									<label class="form-check-label" for="anonymousCheck">
										익명으로 기부 </label>
								</div>
							</div>
							<!-- 기부자 이름 입력 필드 -->
							<div class="form-group">
								<label for="sub_name">기부자 이름</label>
								<form:input type="text" class="form-control" id="sub_name"
									path="sub_name" placeholder="기부자 이름" />
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
							</div>

							<!-- 기부 시작하기 버튼 -->
							<button type="submit" class="btn btn-primary">기부 시작하기</button>
						</form:form>

					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
        $(document).ready(function() {
            $('#anonymousCheck').change(function() {
                if ($(this).is(':checked')) {
                    $('#sub_name').prop('disabled', true);
                } else {
                    $('#sub_name').prop('disabled', false);
                }
            });
        });
    </script>
</body>
</html>
