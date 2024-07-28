<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- <script>
	//페이지 로드 후 제이쿼리를 이용한 자바스크립트 실행
	$(document).ready(
			function() {
				var list = ${listJson}; // 서버에서 넘어온 데이터
				
			    // 리스트를 역순으로 정렬
			    list.reverse();

				let remainingPoint = 0;
				$.each(list, function(index, point) {
					var pointNum = point.point_num;
					remainingPoint = calculateRemainingPoint(point,
							remainingPoint); // calculateRemainingPoint는 잔여 포인트를 계산하는 함수
					updateRemainingPoint(pointNum, remainingPoint); // 테이블 열 업데이트
				});

				function calculateRemainingPoint(point, remainingPoint) {
					return remainingPoint + point.point_amount;
				}

				function updateRemainingPoint(pointNum, remainingPoint) {
					$('#remain_' + pointNum).text(remainingPoint);
				}
			});
</script> -->
<div class="container mt-4">
	<h4 class="mb-4">결제내역</h4>
	<div class="row justify-content-left main-content-container">
		<c:if test="${count == 0}">
		<div class="result-display">결제내역이 없습니다.</div>
		</c:if>
 		<c:if test="${count > 0}">
            <table class="table table-clean">
                <thead>
                    <tr>
                        <th>분류</th>
                        <th>일자</th>
                        <th>내역</th>
                        <th>결제액</th>
                        <th>기부액</th>
                        <th>사용포인트</th>
                        <th>결제상태</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 데이터 행 추가 -->
                    <c:forEach var="payment" items="${list}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${payment.type == 0}">정기기부</c:when>
                                    <c:when test="${payment.type == 1}">기부박스</c:when>
                                    <c:when test="${payment.type == 2}">챌린지</c:when>
                                    <c:when test="${payment.type == 3}">굿즈샵</c:when>
                                </c:choose>
                            </td>
                            <td>${payment.pay_date}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${payment.type == 0}"><a href="${pageContext.request.contextPath}/subscription/subscriptionDetail?sub_num=${payment.id}">[${payment.ref}] 카테고리 정기기부</a></c:when>
                                    <c:when test="${payment.type == 1}"><a href="#">[${payment.ref}] 기부박스 기부</a></c:when>
                                    <c:when test="${payment.type == 2}"><a href="#">[${payment.ref}] 챌린지 참여금</a></c:when>
                                    <c:when test="${payment.type == 3}"><a href="#">굿즈샵에서 상품 구매 [구매번호 : ${payment.id}]</a></c:when>
                                </c:choose>
                            </td>
                            <td><fmt:formatNumber value="${payment.price}" type="number" minFractionDigits="0" maxFractionDigits="0"/></td>
                            <td><fmt:formatNumber value="${payment.donation}" type="number" minFractionDigits="0" maxFractionDigits="0"/></td>
                            <td><fmt:formatNumber value="${payment.point}" type="number" minFractionDigits="0" maxFractionDigits="0"/>P</td>
                            <td>
                                <c:choose>
                                    <c:when test="${payment.status == 0}">결제완료</c:when>
                                    <c:otherwise>결제취소</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
		<div class="align-center">${page}</div>
 		</c:if>
	</div>
</div>