<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
	let contextPath = ${pageContext.request.contextPath}
</script>
<script src="${pageContext.request.contextPath}/js/challenge/challenge.chat.js"></script>
<div>
	<div>
		<span>${chal_room_name}</span> <span>(👁️ ${count}명)</span>
		<span onclick="신고하기 모달창">🚨</span>	
	</div>
	<div id="chatting_message"></div>
	<div class="사진 이미지 미리보기"></div>
	<form id="chat_writeForm" enctype="multipart/form-data">
		<input type="hidden" name="chal_num" id="chal_num" value="${chal_num}">	
	  <textarea rows="5" cols="40" name="chat_content" id="chat_content"></textarea>
	  <br>
	  <!-- <div class="file-upload-wrapper">
        <input type="file" name="upload" class="file-input" id="fileUpload" accept="image/*">
        <div class="custom-button" id="customButton">📁 </div>
    </div> -->			
    <input type="file" name="upload" class="file-input" id="fileUpload" accept="image/*">    
		<input type="submit" value="전송">
	</form>
</div>
