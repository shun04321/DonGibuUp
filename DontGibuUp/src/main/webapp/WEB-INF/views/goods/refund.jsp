<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>상품 환불</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/goodsRefund.js"></script>
</head>
<body>
    <h1>상품 환불 페이지</h1>
    <label for="impUid">결제 UID:</label>
    <input type="text" id="impUid" name="impUid"><br><br>
    <label for="amount">환불 금액:</label>
    <input type="number" id="amount" name="amount"><br><br>
    <label for="reason">환불 사유:</label>
    <input type="text" id="reason" name="reason"><br><br>
    <button id="refundButton">환불하기</button>
</body>
</html>
