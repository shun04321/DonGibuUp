<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div>
    <h2>Refund Page</h2>
    <form id="refundForm">
        <input type="text" id="imp_uid" name="imp_uid" placeholder="Imp UID" required>
        <input type="text" id="reason" name="reason" placeholder="Reason for Refund" required>
        <button type="button" onclick="requestRefund(document.getElementById('imp_uid').value, document.getElementById('reason').value)">Request Refund</button>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.iamport.kr/v2/iamport.js"></script>
<script src="${pageContext.request.contextPath}/js/goods/refund.js"></script>