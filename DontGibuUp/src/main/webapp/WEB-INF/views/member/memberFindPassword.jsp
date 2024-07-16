<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member/member.findpassword.js"></script>
<div class="page-main">
<h2>비밀번호 찾기</h2>
<form action="findPasswordResult" method="get" id="member_password">
    <ul>
        <li>
            <label for="mem_email">이메일</label>
            <input type="text" id="mem_email" name="mem_email" maxlength="16"/>
            <span id="email_check_msg"></span>
        </li>
    </ul>
    <div class="align-center">
        <button type="button" id="findpw_btn" class="default-btn">비밀번호 찾기</button>
    </div>
</form>
</div>