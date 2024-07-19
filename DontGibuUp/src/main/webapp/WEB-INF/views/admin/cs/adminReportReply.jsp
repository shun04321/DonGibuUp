<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-main">
	<h2>문의 내용</h2>
	<div id="reportDetails">
		<div id="reportInfo">
			<div>
				<span>종류</span> <span id="reportCategoryText">
					<c:if test="${report.report_type == 1}">스팸/광고</c:if>
					<c:if test="${report.report_type == 2}">폭력/위협</c:if>
					<c:if test="${report.report_type == 3}">혐오발언/차별</c:if>
					<c:if test="${report.report_type == 4}">음란물/부적절한 콘텐츠</c:if>
					<c:if test="${report.report_type == 5}">챌린지 인증</c:if>
				</span>
				<span>신고 등록일</span> <span id="reportDateText">${report.report_date}</span>
			</div>		
		</div>
		<!-- 파일 다운로드 뷰 넣기 -->
		<c:if test="${!empty report.report_filename}">
		<div><span>첨부파일</span> <a href="file?report_num=${report.report_num}">${report.report_filename}</a></div>
		</c:if>
		<div id="reportContent">
			<p>${report.report_content}</p>
		</div>
		<c:if test="${empty report.report_reply}">
		<form:form action="reply" modelAttribute="reportVO">
			<form:hidden path="report_num" value="${report.report_num}"/>
			<form:radiobutton path="report_status" value="1" id="report_status_1" />
	        <label for="report_status_1">승인</label><br>
			<form:radiobutton path="report_status" value="2" id="report_status_2" />
	        <label for="report_status_2">반려</label><br>
			<div>
				<form:label path="report_reply">답변</form:label>				
			</div>
			<form:textarea path="report_reply" cols="60" rows="5"/>
			<form:errors path="report_reply" cssClass="form-error"></form:errors>
			<div>
				<form:button>답변 등록</form:button>
			</div>
		</form:form>
		</c:if>
		<c:if test="${!empty report.report_reply}">
		<div id="reportResponse">
			<p id="replyContent">${report.report_reply}</p>		
		</div>
		<div id="replyInfo">
			<span>답변일</span> <span id="reportRDateText">${report.report_rdate}</span>
			<button id="modifyBtn">수정하기</button>
		</div>
			<div id="formContainer"></div>
			    <script>
			    $(function() {
			        $('#modifyBtn').click(function() {
			            $.ajax({
			                url: 'modifyForm?report_num=${report.report_num}',
			                type: 'GET',
			                success: function(data) {
			                    $('#formContainer').html(data);
			                }
			            });
			        });
			    });
			    </script>
		</c:if>
		<button onclick="location.href='../report'">목록</button>
	</div>
</div>