<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<div>
		<span>${chal_room_name} (👁️ ${count}명)</span>
		<span onclick="location.href='list'"> → </span>
		<span onclick="신고하기 모달창">🚨</span>
	</div>
	<div id="chatting_message"></div>
	<form id="chat_writeForm" enctype="mutipart/form-data">
		<input type="hidden" name="chal_num" id="chal_num" value="${chal_num}">	
	    <textarea rows="5" cols="40" name="chat_content" id="chat_content"></textarea>
		<input type="file" name="upload" id="upload">			    
		<div id="message_btn">
			<input type="submit" value="전송">
		</div>
	</form>
</div>