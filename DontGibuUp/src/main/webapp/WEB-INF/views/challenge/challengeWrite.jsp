<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
	챌린지 개설하기
	<form:form action="write" id="challenger_register" enctype="multipart/form-data" modelAttribute="challengeVO">
		<ul>
			<li>
				<form:label path="chal_public">챌린지 유형</form:label>
				<form:radiobutton path="chal_public" value="0" label="공개"/>
				<form:radiobutton path="chal_public" value="1" label="비공개"/>
				<form:errors path="chal_public" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="chal_type">카테고리 선택</form:label>
				<div>
				<form:radiobutton path="chal_type" value="0" label="운동"/>
				<form:radiobutton path="chal_type" value="1" label="식습관"/>
				<form:radiobutton path="chal_type" value="2" label="생활"/>
				<form:radiobutton path="chal_type" value="3" label="정서"/>
				<form:radiobutton path="chal_type" value="4" label="취미"/>
				<form:radiobutton path="chal_type" value="5" label="환경"/>
				<form:radiobutton path="chal_type" value="6" label="공부"/>
				<form:radiobutton path="chal_type" value="7" label="기타"/>
				</div>
				<form:errors path="chal_type" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="chal_title">챌린지 제목</form:label>
				<form:input path="chal_title" placeholder="예) 1만보 걷기"/>
				<form:errors path="chal_title" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="chal_content">챌린지 소개</form:label>
				<form:textarea path="chal_content"/>
			</li>
			<li>
				<label>챌린지 기간</label>
				<form:label path="chal_sdate">시작일</form:label>
				<form:input type="date" path="chal_sdate"/>
				<form:errors path="chal_sdate" cssClass="error-color"/>
				<form:label path="chal_edate">종료일</form:label>
				<form:input type="date" path="chal_edate"/>
				<form:errors path="chal_edate" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="chal_freq">인증 빈도</form:label>
				<form:select path="chal_freq">
					<option disabled="disabled" selected>선택하세요</option>					
					<form:option value="0">매일</form:option>
					<form:option value="1">주1일</form:option>
					<form:option value="2">주2일</form:option>
					<form:option value="3">주3일</form:option>
					<form:option value="4">주4일</form:option>
					<form:option value="5">주5일</form:option>
					<form:option value="6">주6일</form:option>
				</form:select>
				<form:errors path="chal_freq" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="chal_verify">인증 방법</form:label>
				<form:textarea path="chal_verify"/>
				<form:errors path="chal_verify" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="chal_fee">참가비</form:label>
				<form:input path="chal_fee" placeholder="참가비는 1000원 이상으로 지정해야합니다."/>원
				<form:errors path="chal_fee" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="chal_max">최대 모집 인원</form:label>
				<form:input path="chal_max"/>명
				<form:errors path="chal_max" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="upload">대표사진</form:label>
				<input type="file" name="upload" id="upload"/>
				<div id="challProfile"></div>
				<script>
				/* 파일 이미지 미리보기 */
				</script>
			</li>
		</ul>
		<div class="align-center">
			<form:button>개설하기</form:button>
		</div>
	</form:form>
</div>
