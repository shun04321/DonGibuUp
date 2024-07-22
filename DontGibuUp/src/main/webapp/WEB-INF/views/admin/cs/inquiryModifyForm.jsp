<!-- inquiryReplyModifyForm -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<form:form action="reply" modelAttribute="inquiryVO">
	<form:hidden path="inquiry_title" value="${inquiry.inquiry_title}"/>
	<form:hidden path="mem_num" value="${inquiry.mem_num}"/>
	<form:hidden path="inquiry_num" value="${inquiry.inquiry_num}"/>
	<div>
		<form:label path="inquiry_reply">답변</form:label>				
	</div>
	<form:textarea path="inquiry_reply" cols="60" rows="5"/>
	<form:errors path="inquiry_reply" cssClass="form-error"></form:errors>
	<div>
		<form:button>답변 수정</form:button>
	</div>
</form:form>
<script>
$(function() {
	$('#inquiry_reply').val($('#replyContent').text());
	$('#replyContent').hide();
	$('#replyInfo').hide();
});
</script>