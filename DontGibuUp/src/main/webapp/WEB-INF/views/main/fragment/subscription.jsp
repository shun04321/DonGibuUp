<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- section 내에 들어갈 내용을 div 내에 작성해주세요 --%>
<%-- section의 높이는 컨텐츠의 길이에 따라 자동 조정됩니다 --%>
<%-- 메인 페이지의 css 파일은 /DontGibuUp/src/main/resources/static/css/main.css 파일 내에 있습니다. --%>
<%-- css 설정이 필요하면 해당 파일 내의 적절한 섹션에서 수정해주세요 (git 충돌 주의) --%>
<style>
    .image {
        width: 310px;
        height: 450px;
        border: 1px solid black;
        border-radius: 5px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.2em;
        text-align: center;
    }
    .coolSomething {
        width: 450px;
        height: 450px;
        border: 1px solid black;
        border-radius: 5px;
        padding: 10px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        font-size: 1.1em;
        line-height: 1.5;
    }
    .container {
        display: flex;
        gap: 10px; /* 이미지와 coolSomething 사이의 간격 */
    }
    .title-style {
        font-size: 1.5em;
        margin-bottom: 10px;
    }
</style>
<div>
    <div class="title-style">정기후원이란?</div>
    <small>정기후원은 매월 일정한 금액을 후원하는 방법으로, 후원금은 선택한 분야에서 지원이 시급한 이웃에게 전달되어 지속적으로<br> 도움을 줄 수 있습니다.</small>
    <p style="white-space:pre-line"></p>
    <div class="container">
        <div class="image">
            아주 멋진 이미지
        </div>
        <div class="coolSomething">
            <h3>정기기부의 혜택</h3>
            <ul>
                <li>꾸준한 지원으로 지속적인 변화 가능</li>
                <li>기부금 영수증 발급으로 세제 혜택</li>
                <li>후원자 소식지 제공</li>
            </ul>
            <h3>실제 사례</h3>
            <p>
                여러분의 기부로 많은 사람들이 도움을 받고 있습니다. 예를 들어, 정기기부 덕분에 아이들은 교육을 받을 수 있고, 가정은 안정된 삶을 누리고 있습니다.
            </p>
            <h3>기부 방법</h3>
            <p>
                정기기부를 시작하는 것은 매우 간단합니다. 아래 버튼을 클릭하여 기부를 시작해보세요.
            </p>
            <button onclick="location.href='/subscription/subscriptionMain'">정기기부 시작하기</button>
        </div>
    </div>
</div>
