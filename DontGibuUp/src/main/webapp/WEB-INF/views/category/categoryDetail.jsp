<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 게시판 글상세 시작 -->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판 글상세</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>container</title>
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
</head>
	<img src="${pageContext.request.contextPath}/upload/${category.dcate_banner}" height="330" width="100%">
	<div class="page-main">
		<h2>${category.dcate_name}</h2>
		<ul class="detail-info">
			<li>${category.dcate_charity} <br>
			</li>
		</ul>
		<div class="detail-content">${category.dcate_content}</div>
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
			<input type="button" value="목록" onclick="location.href='list'">
		</div>
	</div>
	<div class="pay-button">
		<button id="paybutton" class="btn btn-info" data-bs-toggle="modal"
			data-bs-target="#staticBackdrop">정기기부</button>
	</div>
	<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<img src="${pageContext.request.contextPath}/upload/${category.dcate_icon}" width="30"><span>${category.dcate_name}</span>
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<form id="radioForm" action="" method="post" style="width: 465px;">
						<div class="form-check">
							<label class="form-check-label" for="radio1"> 유기견보호소 지원 </label>
							<input class="form-check-input" type="radio" id="radio1"
								name="radio" value="option1">
						</div>
						<div class="form-check">
							<label class="form-check-label" for="radio2"> 유기견보호소 지원 </label>
							<input class="form-check-input" type="radio" id="radio2"
								name="radio" value="option2">
						</div>
						<div class="form-check">
							<label class="form-check-label" for="radio3"> 유기견보호소 지원 </label>
							<input class="form-check-input" type="radio" id="radio3"
								name="radio" value="option3">
						</div>
						<button type="submit" class="btn btn-primary">기부 시작하기</button>
					</form>
				</div>
			</div>
		</div>
	</div>


	<script src="https://cdn.portone.io/v2/browser-sdk.js"></script>

	<div class="portone-ui-container" data-portone-ui-type="PAYPAL_RT">
		<!-- 여기에 PG사 전용 버튼이 그려집니다 -->
	</div>

	<script>
PortOne.loadIssueBillingKeyUI({
  uiType: "PAYPAL_RT",
  storeId: "store-3f9ebc91-a91b-4783-8f05-da9c39896c3a",
  channelKey: "channel-key-4bd84ea8-2ed3-4536-8636-526b6856a072",
  billingKeyMethod: "PAYPAL",
});
</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</html>

