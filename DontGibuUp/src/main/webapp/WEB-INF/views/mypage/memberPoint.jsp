<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
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
</script>
<div class="page-main">
	<h2>포인트</h2>
	<table>
		<thead>
			<tr>
				<th></th>
				<th>분류</th>
				<th>일자</th>
				<th>내역</th>
				<th>포인트</th>
				<th>잔여포인트</th>
			</tr>
		</thead>
		<tbody>
			<!-- 데이터 행 추가 -->
			<c:forEach var="point" items="${list}">
				<tr>
					<td><c:if
							test="${point.pevent_type >= 10 && point.pevent_type < 20}">
               적립
               </c:if> <c:if
							test="${point.pevent_type >= 20 && point.pevent_type < 30}">
               사용
               </c:if> <c:if
							test="${point.pevent_type >= 30 && point.pevent_type < 40}">
               환불
               </c:if> <c:if
							test="${point.pevent_type >= 40 && point.pevent_type < 50}">
               회수
               </c:if></td>
					<td>${point.point_date}</td>
					<td>${point.pevent_detail}</td>
					<td>${point.point_amount}</td>
					<td id="remain_${point.point_num}"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>