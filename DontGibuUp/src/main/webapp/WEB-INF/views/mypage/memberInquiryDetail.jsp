<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<div id="inquiryResponse">
			<c:if test="${empty inquiry.inquiry_reply}">
			<p>아직 답변이 등록되지 않았습니다.</p>
			</c:if>
			<c:if test="${!empty inquiry.inquiry_reply}">
			<p>${inquiry.inquiry_reply}</p>
			<div><span>답변일</span> <span id="inquiryRDateText">${inquiry.inquiry_rdate}</span></div>
			</c:if>
		</div>
			<c:if test="${empty inquiry.inquiry_reply}">
			<button id="editButton" onclick="location.href='modify?inquiry_num=${inquiry.inquiry_num}'">수정</button>
			</c:if>
			<button id="deleteButton">삭제</button>
			<script>
				$('#deleteButton').click(function() {
					if (confirm("삭제 하시겠습니까?")) {
					    alert("문의가 삭제되었습니다");
					    location.href='delete?inquiry_num=${inquiry.inquiry_num}'
					}
				});
			</script>
	</div>
</div>