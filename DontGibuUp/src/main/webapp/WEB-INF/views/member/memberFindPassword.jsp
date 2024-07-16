<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member/member.findpassword.js"></script>
<div class="page-main">
<h2>비밀번호 재발급</h2>
<form action="findPasswordResult" method="get" id="member_password">
    <ul>
        <li>
            <label for="mem_email">이메일</label>
            <input type="text" id="mem_email" name="mem_email" maxlength="50"/>
            <span id="email_check_msg" style="color:red;"><c:if test="${!empty email_msg}">${email_msg}</c:if></span>
        </li>
    </ul>
    <div class="align-center">
        <button type="submit" id="findpw_btn" class="default-btn">비밀번호 재발급</button>
    </div>
</form>
</div>