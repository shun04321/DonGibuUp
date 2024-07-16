<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Refund Request</title>
</head>
<body>
    <h2>환불 요청</h2>
    <form action="${pageContext.request.contextPath}/payments/refund" method="post">
        <label for="impUid">Imp UID:</label>
        <input type="text" id="impUid" name="impUid" required><br><br>

        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount" required><br><br>

        <label for="reason">Reason:</label>
        <input type="text" id="reason" name="reason" required><br><br>

        <button type="submit">환불 요청</button>
    </form>
</body>
</html>