<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/dbox/dbox.list.js"></script>

<!-- 목록 시작 -->
<div>
	<button type="button" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/dbox/propose'">기부박스 제안하기</button>
</div>
<br>
<%-- 목록반복 --%>
<div class="row row-cols-1 row-cols-md-4 g-3" id="output"></div>
<div id="loading" style="display:none;">
	<img src="${pageContext.request.contextPath}/images/loading.gif" width="30" height="30">
</div>

<div class="paging-button" style="display:none;">
	<input type="button" value="더보기">
</div>

<!-- 목록 끝 -->