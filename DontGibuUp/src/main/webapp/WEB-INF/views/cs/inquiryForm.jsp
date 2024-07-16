<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<h2>1:1문의</h2>
<form:form action="inquiry" id="inquiry" enctype="multipart/form-data" modelAttribute="inquiryVO">
	<ul>
	<form:hidden path="mem_num" value="${user.mem_num}" />
		<li>
			<form:label path="inquiry_category">카테고리</form:label>
			<form:select path="inquiry_category">
            	<c:forEach var="category" items="${inquiry_category}">
            		<c:choose>
                        <c:when test="${category.key == ''}">
                            <form:option value="${category.key}" selected="selected">${category.value}</form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="${category.key}">${category.value}</form:option>
                        </c:otherwise>
                    </c:choose>
            	</c:forEach>
        	</form:select>
        	<form:errors path="inquiry_category" cssClass="form-error"></form:errors>
		</li>
		<li>
			<form:input path="inquiry_title" placeholder="문의 제목" maxlength="20"/>
			<form:errors path="inquiry_title" cssClass="form-error"></form:errors>
		</li>
		<li>
			<label for="upload">파일 업로드</label>
			<input type="file" name="upload" id="upload">
		</li>
		<li>
			<form:textarea path="inquiry_content" placeholder="문의 내용" maxlength="1333"/>
			<form:errors path="inquiry_content" cssClass="form-error"></form:errors>
		</li>
	</ul>
	<div class="align-center">
		<form:button class="default-btn">문의하기</form:button>
	</div>
</form:form>