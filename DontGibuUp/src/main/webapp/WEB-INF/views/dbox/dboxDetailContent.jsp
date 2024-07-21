<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<img src="${pageContext.request.contextPath}/upload/dbox/${dbox.dbox_photo}"><br>
<br>
${dbox.dbox_content}<br>
<br>
<div style="border:1px solid;">
	<table>
		<c:forEach var="budget" items="${dboxBudget}">
		<tr>
			<td>
			${budget.dbox_bud_purpose}
			</td>
			<td>
			---------------${budget.dbox_bud_price}
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
