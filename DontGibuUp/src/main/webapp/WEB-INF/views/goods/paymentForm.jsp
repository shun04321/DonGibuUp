<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>환불 정보 입력</title>
</head>
<body>
    <h1>환불 정보 입력</h1>
    <form action="${pageContext.request.contextPath}/goods/refund" method="post">
        <label for="impUid">Imp UID:</label>
        <input type="text" id="impUid" name="impUid"><br><br>
        
        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount"><br><br>
        
        <label for="reason">Reason:</label>
        <input type="text" id="reason" name="reason"><br><br>
        
        <input type="submit" value="환불">
    </form>
</body>
</html>