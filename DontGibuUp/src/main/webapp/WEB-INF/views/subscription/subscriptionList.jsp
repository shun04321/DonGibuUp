<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 게시판 목록 시작 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
/* item_subscribe를 테두리가 있는 박스로 만듭니다. */
.item_subscribe {
	text-align: center; /* 텍스트 가운데 정렬 */
	width: 600px;
	border: 1px solid #ccc;
	border-radius: 10px; /* 둥근 테두리 */
	padding: 10px;
	margin: 10px 0;
	display: flex;
	flex-direction: column;
	align-items: center;
	background-color: #f9f9f9; /* 배경 색상 추가 */
}
.item_subscribe img {
	width: 30px; /* 아이콘 크기 조정 */
	height: 30px;
}
.cont-item {
	display: flex;
	flex-direction: column;
	width: 100%;
}
.info-item {
	display: flex;
	justify-content: space-between;
	padding-top: 5px;
	margin-top: 5px;
}
.info-item dt, .info-item dd {
	margin: 0;
}
.info-item dt {
	flex: 1;
	text-align: left;
	padding-left: 50px;
}
.info-item dd {
	flex: 1;
	text-align: right;
	padding-right: 50px;
}
.header-item {
	margin-top:5px;
	padding-bottom:10px;
	display: flex;
	width: 100%;
	justify-content: space-between;
	align-items: center;
	border-bottom: 1px solid #ccc;
}
.header-item dt {
	padding-left: 50px;
	display: flex;
	align-items: center;
}
.header-item dt img {
	margin-right: 10px; /* 아이콘과 텍스트 사이의 간격 */
}
.header-item dd {
	margin-left: auto;
	padding-right: 50px;
}
hr {

}
</style>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        // 모든 sub_price 요소를 가져옵니다.
        document.querySelectorAll('.sub-price').forEach(function(element) {
            var price = parseInt(element.innerText, 10);
            if (!isNaN(price)) {
                // 가격을 천 단위로 구분하여 포맷합니다.
                element.innerText = price.toLocaleString() + '원';
            }
        });
    });
</script>
<div class="page-main">
	<h3>나의 정기기부 목록</h3>
	<c:if test="${count == 0}">
		<div class="result-display">표시할 정기기부 현황이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
		<c:forEach var="subscription" items="${list}">
			<div class="item_subscribe">			
				<dl class="header-item">
						<dt>
							<a href="${pageContext.request.contextPath}/subscription/subscriptionDetail?sub_num=${subscription.sub_num}">
								<img src="${pageContext.request.contextPath}/upload/${subscription.donationCategory.dcate_icon}" alt="기부처 아이콘">
								${subscription.donationCategory.dcate_name} / ${subscription.donationCategory.dcate_charity}
							</a>
						</dt>
					<dd>
						<a href="${pageContext.request.contextPath}/subscription/subscriptionDetail?sub_num=${subscription.sub_num}">
							<c:if test="${subscription.sub_status==0}">
								상태 : 기부 진행중 >
							</c:if>
							<c:if test="${subscription.sub_status==1}">
								상태 : 기부 중단 >
							</c:if>
						</a>
					</dd>
				</dl>
				<div class="cont-item">					
					<dl class="info-item">
						<dt>기부금액</dt>
						<dd>${subscription.sub_price}원</dd>
					</dl>
					<dl class="info-item">
						<dt>결제일</dt>
						<dd> ${subscription.sub_ndate}일</dd>
					</dl>
					<dl class="info-item">
						<dt>결제방법</dt>
						<dd>
							<c:choose>
								<c:when test="${subscription.sub_method == 'card'}">
									카드 / ${subscription.card_nickname}
								</c:when>
								<c:when test="${subscription.sub_method == 'easy_pay'}">
									간편결제 / ${subscription.easypay_method}
								</c:when>
								<c:otherwise>
									알 수 없음
								</c:otherwise>
							</c:choose>
						</dd>
					</dl>
				</div>
			</div>
		</c:forEach>
	</c:if>
</div>
