<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- section 내에 들어갈 내용을 div 내에 작성해주세요 --%>
<%-- section의 높이는 컨텐츠의 길이에 따라 자동 조정됩니다 --%>
<%-- 메인 페이지의 css 파일은 /DontGibuUp/src/main/resources/static/css/main.css 파일 내에 있습니다. --%>
<%-- css 설정이 필요하면 해당 파일 내의 적절한 섹션에서 수정해주세요 (git 충돌 주의) --%>
<style>
    .container {
        display: flex;
        gap: 10px; /* 이미지와 coolSomething 사이의 간격 */
    }

</style>
<div>
    <div class="title-style">나의 도움이 필요한 모금함은?</div>
    <p style="white-space:pre-line"></p>
    <div class="container">
      <c:forEach var="category" items="${list}" varStatus="loop">
      	
      </c:forEach>
    </div>
</div>
