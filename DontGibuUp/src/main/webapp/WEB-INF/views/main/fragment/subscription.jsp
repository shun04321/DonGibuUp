<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- section 내에 들어갈 내용을 div 내에 작성해주세요 --%>
<%-- section의 높이는 컨텐츠의 길이에 따라 자동 조정됩니다 --%>
<%-- 메인 페이지의 css 파일은 /DontGibuUp/src/main/resources/static/css/main.css 파일 내에 있습니다. --%>
<%-- css 설정이 필요하면 해당 파일 내의 적절한 섹션에서 수정해주세요 (git 충돌 주의) --%>
<style>
	.image{
		width: 310px;
		height : 450px;
		border: 1px solid black;
		border-radius : 5px;
	}
	.coolSomething{
		width: 450px;
		height : 450px;
		border: 1px solid black;
		border-radius : 5px;
	}
	.container{
        display: flex;
        gap: 10px; /* 이미지와 coolSomething 사이의 간격 */
    }
	}
</style>
<div>
	<div class="title-style">나의 도움이 필요한 사람들은?</div>
	<small>정기후원은 매월 일정한 금액을 후원하는 방법으로, 후원금은 선택한 분야에서 지원이 시급한 이웃에게 전달되어 지속적으로 도움을 줄 수 있습니다.</small>
	<p style="white-space:pre-line"></p>
	<div class="container">
		<div class="image">
			아주 멋진 이미지
		</div>
		<div class="coolSomething">
			아주 멋진 글
		</div>
	</div>
</div>