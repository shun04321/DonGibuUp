<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-main">
	<h2>문의 내용</h2>
	<div id="inquiryDetails">
		<h3 id="inquiryTitle">${inquiry.inquiry_title}</h3>
		<div id="inquiryInfo">
			<div>
				<span>분류</span> <span id="inquiryCategoryText">
					   <c:if
									test="${inquiry.inquiry_category == 0}">
		               정기기부
		               </c:if> <c:if
									test="${inquiry.inquiry_category == 1}">
		               기부박스
		               </c:if> <c:if
									test="${inquiry.inquiry_category == 2}">
		               챌린지
		               </c:if> <c:if
									test="${inquiry.inquiry_category == 3}">
		               굿즈샵
		               </c:if> <c:if
									test="${inquiry.inquiry_category == 4}">
		               기타
		               </c:if>
				</span>
				<span>문의 등록일</span> <span id="inquiryDateText">${inquiry.inquiry_date}</span>
			</div>		
		</div>
		<!-- 파일 다운로드 뷰 넣기 -->
		<c:if test="${!empty inquiry.inquiry_filename}">
		<div><span>첨부파일</span> <a href="file?inquiry_num=${inquiry.inquiry_num}">${inquiry.inquiry_filename}</a></div>
		</c:if>
		<div id="inquiryContent">
			<p>${inquiry.inquiry_content}</p>
		</div>
		<c:if test="${empty inquiry.inquiry_reply}">
		<form:form action="reply" modelAttribute="inquiryVO">
			<form:hidden path="inquiry_title" value="${inquiry.inquiry_title}"/>
			<form:hidden path="mem_num" value="${inquiry.mem_num}"/>
			<form:hidden path="inquiry_num" value="${inquiry.inquiry_num}"/>
			<div>
				<form:label path="inquiry_reply">답변</form:label>				
			</div>
			<form:textarea path="inquiry_reply" cols="60" rows="5"/>
			<form:errors path="inquiry_reply" cssClass="form-error"></form:errors>
			<div>
				<form:button>답변 등록</form:button>
			</div>
		</form:form>
		</c:if>
		<c:if test="${!empty inquiry.inquiry_reply}">
		<div id="inquiryResponse">
			<p id="replyContent">${inquiry.inquiry_reply}</p>		
		</div>
		<div id="replyInfo">
			<span>답변일</span> <span id="inquiryRDateText">${inquiry.inquiry_rdate}</span>
			<button id="modifyBtn">수정하기</button>
		</div>
			<div id="formContainer"></div>
			    <script>
			    $(function() {
			        $('#modifyBtn').click(function() {
			            $.ajax({
			                url: 'modifyForm?inquiry_num=${inquiry.inquiry_num}',
			                type: 'GET',
			                success: function(data) {
			                    $('#formContainer').html(data);
			                }
			            });
			        });
			    });
			    </script>
		</c:if>
		<button onclick="location.href='../inquiry'">목록</button>
	</div>
</div>