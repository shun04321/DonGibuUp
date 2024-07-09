<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
	챌린지 개설하기
	<form:form action="write" id="challenger_register" 
				enctype="multipart/form-data" modelAttribute="challengeVO">
		<ul>
			<li>
				<form:label path="">챌린지 유형</form:label>
				<form:radiobutton path="" value="0" label="공개"/>
				<form:radiobutton path="" value="1" label="비공개"/>
			</li>
		</ul>
	</form:form>
</div>
