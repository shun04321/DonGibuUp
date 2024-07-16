<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>결제하기</title>
</head>
<body>
    <h1>결제 정보 입력</h1>
    <form action="${pageContext.request.contextPath}/payments/request" method="post">
        <label for="merchantUid">Merchant UID:</label>
        <input type="text" id="merchantUid" name="merchantUid"><br>
        
        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount"><br>
        
        <label for="cardNumber">Card Number:</label>
        <input type="text" id="cardNumber" name="cardNumber"><br>
        
        <label for="expiry">Expiry:</label>
        <input type="text" id="expiry" name="expiry"><br>
        
        <label for="birth">Birth:</label>
        <input type="text" id="birth" name="birth"><br>
        
        <label for="pwd2digit">Password (2 digit):</label>
        <input type="password" id="pwd2digit" name="pwd2digit"><br>
        
        <input type="submit" value="결제하기">
    </form>
</body>
</html>