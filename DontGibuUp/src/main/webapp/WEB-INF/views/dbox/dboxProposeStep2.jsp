<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!-- Step2 시작 -->
<div class="page-main">
	<form:form action="step2" id="step2" modelAttribute="dboxVO">	
		<form:errors element="div" cssClass="error-color"/><%-- 필드가 없는 에러메세지 --%>
		<ul>
			<%-- 팀 유형 --%>
			<li>
				<form:label path="dbox_team_name"><h3>팀 유형<span class="validation-check">*필수</span></h3></form:label>
				<form:radiobutton path="dbox_team_type" value="1"/>기관<br>
				<form:radiobutton path="dbox_team_type" value="2"/>개인
			</li>
			<%-- 팀명 --%>
			<li>
				<form:label path="dbox_team_name"><h3>팀명<span class="validation-check">*필수</span></h3></form:label>
				<form:input path="dbox_team_name" placeholder="팀명을 작성해주세요"/>
				<form:errors path="dbox_team_name" cssClass="form-error"></form:errors>				
			</li>
			<!-- 팀 유형=기관일 시, 사업자등록번호  -->
			<li>
				<form:label path="dbox_business_rnum"><h3>사업자등록번호(기관)</h3></form:label>
				<form:input path="dbox_business_rnum" placeholder="숫자10자리"/>		
				<form:errors path="dbox_business_rnum" cssClass="form-error"></form:errors>				
			</li>
			<%-- 팀 대표이미지 등록 --%>
			<li>
				<form:label path="dbox_team_photo"><h3>팀 대표이미지 등록</h3></form:label>
				<p>
				팀의 로고나 대표할 수 있는 이미지로 등록해주세요.<br>
				*미등록시 기본 이미지가 노출됩니다.
				</p>
				<input type="file" name="dbox_team_photo" id="dbox_team_photo">
			</li>
			<%-- 프로젝트 팀 소개 --%>
			<li>
				<form:label path="dbox_team_detail"><h3>프로젝트 팀 소개<span class="validation-check">*필수</span></h3></form:label>
				<span>0/500</span><br>
				<form:textarea path="dbox_team_detail"  placeholder="프로젝트 팀 소개 문구 1~3줄 적어주세요" cssStyle="width:90%;height:50px;"/>
				<form:errors path="dbox_team_detail" cssClass="form-error"></form:errors>				
			</li>
			<%-- 계좌번호 (은행선택,예금주명,계좌번호) --%>
			<li>
				<form:label path="dbox_bank"><h3>계좌번호<span class="validation-check">*필수</span></h3></form:label>
				<form:select path="dbox_bank">
					<option disabled="disabled" selected>은행 선택</option>
					<form:option value="1">국민은행</form:option>
					<form:option value="2">신한은행</form:option>
					<form:option value="3">하나은행</form:option>
					<form:option value="4">우리은행</form:option>
				</form:select>
				<form:errors path="dbox_bank" cssClass="error-color"/>

				<form:input path="dbox_account_name" placeholder="예금주명" cssStyle="width:15%;"/>
				<form:errors path="dbox_account_name" cssClass="form-error"></form:errors>	
				
				<form:input path="dbox_account" placeholder="계좌번호" cssStyle="width:30%;"/>
				<form:errors path="dbox_account" cssClass="form-error"></form:errors>	
			</li>
			<%-- 기부박스 희망기간 --%>
			<li>
				<form:label path="dbox_sdate"><h3>기부박스 희망기간<span class="validation-check">*필수</span></h3></form:label>
				<form:input path="dbox_sdate" type="date"/> ~
				<form:errors path="dbox_sdate" cssClass="form-error"></form:errors>	
				<form:input path="dbox_edate" type="date"/>
				<form:errors path="dbox_edate" cssClass="form-error"></form:errors>				
			</li>
			<%-- 모금액 사용 계획 --%>
			<li>
				<form:label path=""><h3>모금액 사용 계획<span class="validation-check">*필수</span></h3></form:label>
				<p>
				세부내역을 구체적으로 입력해주세요.<br>
				아래 사항 위반시 심사를 통해 환수 조치 될 수 있습니다.<br><br>
				
				 · 사용계획을 허위로 기재하거나 근거가 불충분한 경우<br>
				 · 동일한 스토리 및 예산 항목으로 이중 모금 진행시<br>
				 · 모금액을 입금 받기 전 과거 지출한 내용을 포함한 경우
				</p>
<%-- 				<form:input path="" placeholder="사용용도 및 산출근거"/>		
				<form:errors path="" cssClass="form-error"></form:errors>				
				<form:input path="" placeholder="금액(원)"/>		
				<form:errors path="" cssClass="form-error"></form:errors>	 --%>			
			</li>
			<%-- 심사위원에게 남길 말 --%>
			<li>
				<form:label path="dbox_comment"><h3>심사위원에게 남길 말</h3></form:label>
				<span>0/1000</span><br>
				<form:textarea path="dbox_comment" placeholder="심사위원에게 남길 말을 적어주세요" cssStyle="width:90%;height:50px;"/>
				<form:errors path="dbox_comment" cssClass="form-error"></form:errors>				
			</li>
			<%-- 자료 첨부 --%>
			<li>
				<h3>자료 첨부</h3>
				<form:label path="dbox_business_plan">세부사업계획서<span class="validation-check"><b>*필수</b></span></h3></form:label><br>
				<input type="file" name="dbox_business_plan" id="dbox_business_plan"><br>
				<form:label path="dbox_budget_data">금액 책정 근거자료</form:label><br>
				<input type="file" name="dbox_budget_data" id="dbox_budget_data">
			</li>
		</ul>	
		<div class="align-center">
			<form:button>다음 단계로</form:button>
		</div>
	</form:form>
</div>
<!-- Step2 끝 -->
