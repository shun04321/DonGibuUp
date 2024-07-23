<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 게시판 목록 시작 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/subscriptionList.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/subscription/subscriptionList.js"></script>
<div class="page-container">
    <div class="tabs">
        <button class="tab-button active" onclick="openTab(event, 'subscriptionList')">나의 정기기부 목록</button>
        <button class="tab-button" onclick="openTab(event, 'paymentHistory')">정기기부 결제내역</button>
    </div>
    <div id="subscriptionList" class="tab-content active">
        <h3>나의 정기기부 목록</h3>
        <c:if test="${count == 0}">
            <div class="result-display">표시할 정기기부 현황이 없습니다.</div>
        </c:if>
        <c:if test="${count > 0}">
            <c:forEach var="subscription" items="${list}">
                <div class="item_subscribe">
                    <dl class="header-item">
                        <dt>
                            <a href="${pageContext.request.contextPath}/subscription/subscriptionDetail?sub_num=${subscription.sub_num}">
                                <img src="${pageContext.request.contextPath}/upload/${subscription.donationCategory.dcate_icon}" alt="기부처 아이콘">
                                ${subscription.donationCategory.dcate_name} / ${subscription.donationCategory.dcate_charity}
                            </a>
                        </dt>
                        <dd>
                            <a href="${pageContext.request.contextPath}/subscription/subscriptionDetail?sub_num=${subscription.sub_num}">
                                <c:if test="${subscription.sub_status==0}">
                                    상태 : 기부 진행중 >
                                </c:if>
                                <c:if test="${subscription.sub_status==1}">
                                    상태 : 기부 중단 >
                                </c:if>
                            </a>
                        </dd>
                    </dl>
                    <div class="cont-item">
                        <dl class="info-item">
                            <dt>기부금액</dt>
                            <dd>${subscription.sub_price}원</dd>
                        </dl>
                        <dl class="info-item">
                            <dt>결제일</dt>
                            <dd>${subscription.sub_ndate}일</dd>
                        </dl>
                        <dl class="info-item">
                            <dt>결제방법</dt>
                            <dd>
                                <c:choose>
                                    <c:when test="${subscription.sub_method == 'card'}">
                                        카드 / ${subscription.card_nickname}
                                    </c:when>
                                    <c:when test="${subscription.sub_method == 'easy_pay'}">
                                        간편결제 / ${subscription.easypay_method}
                                    </c:when>
                                    <c:otherwise>
                                        알 수 없음
                                    </c:otherwise>
                                </c:choose>
                            </dd>
                        </dl>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <div id="paymentHistory" class="tab-content">
        <h3>정기기부 결제내역</h3>
        <!-- 결제내역 리스트를 여기에 추가 -->
        <div class="result-display">결제내역 리스트를 여기에 추가합니다.</div>
    </div>
</div>
