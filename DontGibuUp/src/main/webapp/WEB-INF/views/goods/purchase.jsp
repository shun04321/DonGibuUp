<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>상품 결제</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <script src="${pageContext.request.contextPath}/js/goodsPurchase.js"></script>
</head>
<body>
    <h1>상품 결제 페이지</h1>
    <button id="payButton">결제하기</button>
</body>
</html>
