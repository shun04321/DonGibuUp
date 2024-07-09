<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- Step1 시작 -->
<div class="page-main">
	<form:form action="dboxProposeStep1" id="dboxProposeStep1" modelAttribute="dboxVO">	
		<form:errors element="div" cssClass="error-color"/><%-- 필드가 없는 에러메세지 --%>
		<ul>
			<%-- <li>
				<h3>카테고리 선택<span class="validation-check">*필수</span></h3>
				<form:label path="category"></form:label> <form:select
					path="category">
					<option disabled="disabled" selected>선택해주세요.</option>
					<form:option value="1">예시1</form:option>
					<form:option value="2">예시2</form:option>
					<form:option value="3">예시3</form:option>
					<form:option value="4">예시4</form:option>
				</form:select> <form:errors path="category" cssClass="error-color" />
			</li> --%>
			<li>
				<h3>모금 진행 일정과 과정</h3>
				<ul>
					<li>1.기부박스 제안</li>
					<li>2.기부박스 심사</li>
					<li>3.기부박스 진행</li>
					<li>4.기부박스 종료 및 후기 작성</li>
				</ul>
			</li>
			<li>
				<h3>돈기부업 기본 모금검토 기준</h3>
				<ul>
					<li>개인의 이익이 아닌 공익을 위한 모금이어야 합니다.</li>
					<li>프로젝트팀이 사업 수행 능력이나 경험이 있어야 합니다.</li>
					<li>사업집행에 대한 구체적 근거와 계획이 필요합니다.</li>
					<li>영리 목적의 프로젝트는 진행이 어렵습니다.</li>
				</ul>
			</li>
<%-- 			<li>
				<form:checkbox path="terms"/>모금검토 기준을 꼼꼼히 확인하였습니다.
				<form:checkbox path="terms"/>동일한 내용으로 이중모금을 진행하지 않겠습니다.
				<form:checkbox path="terms"/>투명한 기부금 집행을 위한 모금심사를 받겠습니다.
			</li> --%>
		</ul>	
		<div class="align-center">
		<input type="button" value="임시버튼(다음단계)" onclick="location.href='step2'">
			<form:button>다음 단계로</form:button>
		</div>
	</form:form>
</div>
<!-- Step1 끝 -->
