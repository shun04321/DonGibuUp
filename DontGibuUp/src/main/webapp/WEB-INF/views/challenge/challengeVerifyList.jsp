<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>챌린지 인증내역</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/challenge.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/challenge/challenge.verify.js"></script>
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
<h2>챌린지 인증내역</h2>
<div class="challenge-summary">
    <div class="challenge-info">
    	<img src="<c:url value='/images/${challenge.chal_photo}'/>" alt="챌린지 썸네일" class="challenge-thumbnail">
	    <div class="challenge-info">
	        <div class="details">
	            <h3>${challenge.chal_title}</h3>
	            <button class="detail-button" onclick="location.href='${pageContext.request.contextPath}/challenge/detail?chal_num=${challenge.chal_num}'">상세보기</button>
	        </div>
	    </div>
    	<div class="challenge-stats">
            <div class="challenge-stat-item">
                <span>인증 빈도</span>
                <span>${chalFreq}회</span>
            </div>
            <div class="challenge-stat-item">
                <span>기간</span>
                <span>${chal_sdate} ~ ${chal_edate}</span>
            </div>
            <div class="challenge-stat-item1">
                <span>달성률</span>
                <span>${achievementRate}%</span>
            </div>
            <div class="challenge-stat-item2">
                <span>인증 성공</span>
                <span>${successCount}회</span>
            </div>
            <div class="challenge-stat-item2">
                <span>인증 실패</span>
                <span>${failureCount}회</span>
            </div>
            <div class="challenge-stat-item2">
                <span>남은 인증</span>
                <span>${remainingCount}회</span>
            </div>
            <div class="challenge-stat-item2">
		    <c:choose>
		        <c:when test="${status == 'post'}">
		            <!-- 완료된 챌린지의 경우 버튼 숨김 -->
		        </c:when>
		        <c:when test="${hasTodayVerify}">
		            <button class="disabled-button" disabled>오늘 인증 완료</button>
		        </c:when>
		        <c:when test="${hasCompletedWeeklyVerify}">
		            <button class="disabled-button" disabled>이번주 인증 완료</button>
		        </c:when>
		        <c:otherwise>
		            <button class="active-button" onclick="location.href='${pageContext.request.contextPath}/challenge/verify/write?chal_joi_num=${chal_joi_num}&status=${status}'">인증하기</button>
		        </c:otherwise>
		    </c:choose>
            </div>            
        </div>
    </div>
</div>

<div class="challenge-verify-list">
    <c:forEach var="verify" items="${verifyList}">
        <div class="challenge-verify-card">
            <img src="<c:url value='/images/${verify.chal_ver_photo}'/>" alt="인증 사진">
            <div class="content">
                <div class="date-status">
                    <span class="date">${verify.chal_reg_date}</span>
                    <c:choose>
                        <c:when test="${verify.chal_ver_status == 0}">
                            <span class="status success">성공</span>
                        </c:when>
                        <c:when test="${verify.chal_ver_status == 1}">
                            <span class="status failure">실패</span>
                        </c:when>
                    </c:choose>
                </div>
                <div id="content-${verify.chal_ver_num}" class="comment">${verify.chal_content}</div>
                <div id="edit-form-${verify.chal_ver_num}" class="edit-form" style="display:none;">
                    <textarea id="textarea-${verify.chal_ver_num}">${verify.chal_content}</textarea>
                </div>
            </div>
            <c:if test="${status != 'post'}">
                <div class="buttons">
                    <button id="edit-button-${verify.chal_ver_num}" onclick="toggleEditSave(${verify.chal_ver_num})">수정</button>
                    <c:if test="${verify.chal_reg_date == today}">
                        <button onclick="deleteVerify(${verify.chal_ver_num})">삭제</button>
                    </c:if>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>
</body>
</html>