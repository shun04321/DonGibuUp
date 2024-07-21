<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<button type="button" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/dbox/${dboxNews.dbox_num}/content'">기부박스 소개</button>
<button type="button" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/dbox/${dboxNews.dbox_num}/donators'">기부현황</button>
<button type="button" class="btn btn-dark active" onclick="location.href='${pageContext.request.contextPath}/dbox/${dboxNews.dbox_num}/news'">소식</button>