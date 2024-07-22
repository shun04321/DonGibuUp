<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
/* 스타일 정의 */
.item_subscribe {
    text-align: center;
    width: 600px;
    padding: 10px;
    margin: 10px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: white;
}

.item_subscribe img {
    width: 30px;
    height: 30px;
}

.cont-item {
    display: flex;
    flex-direction: column;
    width: 100%;
}

.info-item {
    display: flex;
    justify-content: space-between;
    padding-top: 5px;
    margin-top: 5px;
}

.info-item dt, .info-item dd {
    margin: 0;
}

.info-item dt {
    flex: 1;
    text-align: left;
    padding-left: 50px;
    font-size: 14px;
}

.info-item dd {
    flex: 1;
    text-align: right;
    padding-right: 50px;
    font-size: 14px;
}

.header-item {
    margin-top: 5px;
    padding-bottom: 10px;
    display: flex;
    width: 100%;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #ccc;
}

.header-item dt {
    padding-left: 50px;
    display: flex;
    align-items: center;
}

.header-item dt img {
    margin-right: 10px;
}

.header-item dd {
    margin-left: auto;
    padding-right: 50px;
}

.modify-btn {
    width: 600px;
    height: 45px;
    border: 0;
}
</style>

<div class="item_subscribe">
    <dl class="header-item">
        <dt>
            <a href="${pageContext.request.contextPath}/subscription/subscriptionDetail?sub_num=${subscription.sub_num}">
                <img src="${pageContext.request.contextPath}/upload/${category.dcate_icon}" alt="기부처 아이콘">
                ${category.dcate_name} / ${category.dcate_charity}
            </a>
        </dt>
        <dd>
            <a href="${pageContext.request.contextPath}/subscription/subscriptionDetail?sub_num=${subscription.sub_num}">
                <c:if test="${subscription.sub_status == 0}">
                    상태 : 기부 진행중 >
                </c:if>
                <c:if test="${subscription.sub_status == 1}">
                    상태 : 기부 중단 >
                </c:if>
            </a>
        </dd>
    </dl>
    <div class="cont-item">
        <dl class="info-item">
            <dt>
                시작일 ${reg_date}<br>
                기간 ${reg_date} ~ <span id="next-pay-date"></span>
            </dt>
            <dd>
                이번 결제 ${subscription.sub_price}원 (결제일 ${sub_paydate})<br>
                다음 결제 ${subscription.sub_price}원 (결제일 <span id="next-pay-date"></span>)
            </dd>
        </dl>
    </div>
</div>

<c:if test="${subscription.sub_status == 0}">
    <input type="button" value="해지하기" class="modify-btn">
</c:if>
<c:if test="${subscription.sub_status == 1}">
    <input type="button" value="다시 기부 시작" class="modify-btn">
</c:if>

<script>
document.addEventListener("DOMContentLoaded", function() {
    function addMonthToDateString(dateStr) {
        // 날짜 문자열을 연도, 월, 일로 분리
        let [year, month, day] = dateStr.split('-').map(num => parseInt(num, 10));
        console.log("year: " + year + ", month: " + month + ", day: " + day);

        // 월이 12보다 작은 경우
        if (month < 12) {
            month += 1; // 월을 1 증가
        } else {
            // 월이 12인 경우, 다음 해의 1월로 설정
            month = 1;
            year += 1;
        }

        // 월을 2자리 형식으로 변환
        month = month.toString().padStart(2, '0');
        console.log("Updated month:", month);

        // 결과 문자열 생성
        // day 값이 2자리 형식으로 유지되도록 변환
        day = day.toString().padStart(2, '0');
        console.log("Updated day:", day);

        let nextPayDate = `${year}-${month}-${day}`;
        console.log("Next Pay Date:", nextPayDate);

        return nextPayDate;
    }

    // 서브 결제일 문자열 가져오기
    let subPayDate = "${sub_paydate}".trim(); // 문자열 앞뒤 공백 제거
    console.log("Original sub_paydate:", subPayDate);

    if (/^\d{4}-\d{2}-\d{2}$/.test(subPayDate)) {
        let nextPayDate = addMonthToDateString(subPayDate); // 다음 결제일 계산
        console.log("Next Pay Date:", nextPayDate);
        document.getElementById('next-pay-date').textContent = nextPayDate;
    } else {
        console.error("Invalid date format:", subPayDate);
        document.getElementById('next-pay-date').textContent = "날짜 오류";
    }
});
</script>


