<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
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
    flex-direction: column; /* 세로로 정렬 */
    width: 100%;
    text-align: left; /* 왼쪽 정렬 */
}

.info-item {
    display: flex;
    margin-top: 5px;
    white-space: normal; /* 줄바꿈 허용 */
}

.info-item dt, .info-item dd {
    margin: 0;
    text-align: left; /* 왼쪽 정렬 */
}


.info-item dt {
    flex: 1;
    text-align: left;
    font-size: 14px;
    padding-top: 30px;
}

.info-item dd {
    flex: 1;
    text-align: left;
    padding-right: 20px; /* 간격 조절 */
    font-size: 14px;
    padding-top: 30px;
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
    padding-left: 20px; /* 간격 조절 */
    display: flex;
    align-items: center;
}

.header-item dt img {
    margin-right: 10px;
}

.header-item dd {
    margin-left: auto;
    padding-right: 20px; /* 간격 조절 */
}

.modify-btn {
     width: 600px;
     height: 45px;
     border: 0;
     background-color: #007bff;
     color: white;
     font-size: 16px;
     cursor: pointer;
}
.reg_date{
	padding-left : 10px;
}

.focus {
    font-size: 23px; /* 폰트 크기를 기본보다 작게 조절 */
    text-decoration: underline; /* 밑줄 추가 */
    color: #ff6600; /* 강조 색상 (원하는 색상으로 조절) */
}

.small{
	font-size: 10px;
	text-decoration: underline; /* 밑줄 추가 */
    color: #ff6600; /* 강조 색상 (원하는 색상으로 조절) */
}
</style>

<div class="item_subscribe">
    <dl class="header-item">
        <dt>
            <img src="${pageContext.request.contextPath}/upload/${category.dcate_icon}" alt="기부처 아이콘">
            ${category.dcate_name} / ${category.dcate_charity} &nbsp;
            <c:if test="${subscription.sub_status == 0}">
                에<span class="focus">정기 기부중</span> 입니다.
            </c:if>

        </dt>
        <dd>
            <c:if test="${subscription.sub_status == 0}">
                상태 : 기부 진행중 >
            </c:if>
             <c:if test="${subscription.sub_status == 1}">
                <span class="small"> 해지됨 (${cancel_date})</span>
            </c:if>
        </dd>
    </dl>
    <div class="cont-item">
        <dl class="info-item">
            <dt>
                시작일 <span class="reg_date">${reg_date}</span>
                <br><br>
                기간 <span class="reg_date">${reg_date}</span> ~ 
                <c:if test="${subscription.sub_status == 0}">
                    <span class="next-pay-date"></span>
                </c:if>
                <c:if test="${subscription.sub_status == 1}">
                    ${cancel_date}
                </c:if>
            </dt>
            <dd>
               	 이번 결제&nbsp;&nbsp;<fmt:formatNumber value="${subscription.sub_price}" type="number"/>원<br><br>
                <c:if test="${subscription.sub_status == 0}">
                 다음 결제&nbsp;&nbsp;<fmt:formatNumber value="${subscription.sub_price}" type="number"/>원 (결제일 <span class="next-pay-date"></span>)
                </c:if>
                <c:if test="${subscription.sub_status == 1}">
                    다음 결제 --
                </c:if>
            </dd>
        </dl>
    </div>
</div>

<c:if test="${subscription.sub_status == 0}">
    <input type="button" value="해지하기" class="modify-btn" data-num="${subscription.sub_num}">
</c:if>
<c:if test="${subscription.sub_status == 1}">
    <input type="button" value="해지된 정기기부" class="modify-btn" disabled="disabled">
</c:if>

<script>
document.addEventListener("DOMContentLoaded", function() {
    function addMonthToDateString(dateStr) {
        let [year, month, day] = dateStr.split('-').map(num => parseInt(num, 10));
        
        if (month < 12) {
            month += 1;
        } else {
            month = 1;
            year += 1;
        }

        month = month.toString().padStart(2, '0');
        day = day.toString().padStart(2, '0');

        return `${year}-${month}-${day}`;
    }

    let subPayDate = "${sub_paydate}".trim();
    let cancelDate = "${cancel_date}".trim();
    
    if (/^\d{4}-\d{2}-\d{2}$/.test(subPayDate)) {
        let nextPayDate = addMonthToDateString(subPayDate);
        document.querySelectorAll('.next-pay-date').forEach(function(element) {
            element.textContent = nextPayDate;
        });
    } else {
        console.error("Invalid date format:", subPayDate);
        document.querySelectorAll('.next-pay-date').forEach(function(element) {
            element.textContent = "날짜 오류";
        });
    }

    $('.modify-btn').on('click', function() {
        var subNum = $(this).data('num');
        var action = $(this).val() === '해지하기' ? 'cancel' : 'start';

        $.ajax({
            url: '/subscription/updateSub_status',
            type: 'POST',
            data: {
                sub_num: subNum
            },
            dataType: 'json',
            success: function(param) {
                if (param.result === 'logout') {
                    alert('로그인 후 사용해주세요');
                } else if (param.result === 'success') {
                    alert('정기기부가 중지 되었습니다.');

                    // 버튼 텍스트 및 상태 변경
                    $('.modify-btn').each(function() {
                        $(this).val('해지된 정기기부');
                        $(this).prop('disabled', true);
                        init(this);
                    });
                } else {
                    alert('정기기부 중지 오류 발생');
                }
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
        });
    });
});
</script>




