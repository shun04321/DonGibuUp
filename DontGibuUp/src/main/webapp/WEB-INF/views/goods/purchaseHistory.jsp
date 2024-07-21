<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Purchase History</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.iamport.kr/v2/iamport.js"></script>
    <script src="${pageContext.request.contextPath}/js/goods/refund.js"></script>
</head>
<body>
    <h2>Purchase History</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Item Name</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Purchase Date</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="purchase" items="${purchaseList}">
                <tr>
                    <td>${purchase.item_name}</td>
                    <td>${purchase.amount}</td>
                    <td>${purchase.status}</td>
                    <td>${purchase.payDate}</td>
                    <td>
                        <button type="button" onclick="requestRefund('${purchase.imp_uid}', '환불 요청 사유')">Refund</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>