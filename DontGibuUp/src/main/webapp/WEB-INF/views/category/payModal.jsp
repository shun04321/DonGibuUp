<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<style type="text/css">
#radioForm {
	display: grid;
	place-items: center; /* 수직 및 수평 중앙 정렬 */
	height: 100vh; /* 높이 설정 */
}
</style>
<div class="pay-button">
		<button id="paybutton" class="btn btn-info" data-bs-toggle="modal"
			data-bs-target="#staticBackdrop">정기기부</button>
</div>
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">
					<img
						src="${pageContext.request.contextPath}/upload/${category.dcate_icon}"
						width="30"><span>${category.dcate_name}</span>
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<form id="radioForm" action="" method="post" style="width: 465px;">
					<div class="form-check">
						<label class="form-check-label" for="radio1"> 유기견보호소 지원 </label> <input
							class="form-check-input" type="radio" id="radio1" name="radio"
							value="option1">
					</div>
					<div class="form-check">
						<label class="form-check-label" for="radio2"> 유기견보호소 지원 </label> <input
							class="form-check-input" type="radio" id="radio2" name="radio"
							value="option2">
					</div>
					<div class="form-check">
						<label class="form-check-label" for="radio3"> 유기견보호소 지원 </label> <input
							class="form-check-input" type="radio" id="radio3" name="radio"
							value="option3">
					</div>
					<button type="submit" class="btn btn-primary">기부 시작하기</button>
				</form>
			</div>
		</div>
	</div>
</div>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>