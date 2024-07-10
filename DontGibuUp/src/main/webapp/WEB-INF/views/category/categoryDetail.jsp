<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    

<!-- 게시판 글상세 시작 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시판 글상세</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
</head>
<body>
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
            <input type="button" value="목록" onclick="location.href='list'">
        </div>
        <div class="pay-button">
            <button id="paybutton">정기기부</button>
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
</body>
</html>

