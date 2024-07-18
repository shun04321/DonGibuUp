<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div>
    <h2>Refund Page</h2>
    <form id="refundForm" method="post" action="${pageContext.request.contextPath}/goods/refund">
        <input type="text" id="imp_uid" name="imp_uid" placeholder="Imp UID" required>
        <textarea id="reason" name="reason" placeholder="Reason for refund" required></textarea>
        <button type="submit">Refund</button>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/goods/refund.js"></script>
