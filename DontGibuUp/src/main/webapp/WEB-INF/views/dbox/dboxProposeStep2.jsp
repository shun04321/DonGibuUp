<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- Step2 시작 -->
<div class="page-main">
	<form:form action="step2" id="step2" modelAttribute="dboxVO">	
		<form:errors element="div" cssClass="error-color"/><%-- 필드가 없는 에러메세지 --%>
		<ul>
			<li>
				<form:label path="dbox_team_name"><h3>팀명<span class="validation-check">*필수</span></h3></form:label>
				<form:input path="dbox_team_name"/>
				<form:errors path="dbox_team_name" cssClass="form-error"></form:errors>				
			</li>
		</ul>	
		<div class="align-center">
			<form:button>다음 단계로</form:button>
		</div>
	</form:form>
</div>
<!-- Step2 끝 -->
