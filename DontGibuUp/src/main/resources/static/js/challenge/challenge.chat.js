$(function() {
	//채팅 메시지 입력하기
	$('#chat_content').keydown(function(e) {
		if (e.keyCode == 13 && !e.shiftKey) {
			$('#chat_writeForm').trigger(e);
		}
	});

	$('#chat_writeForm').submit(function(e) {
		if ($('#chat_content').val().trim() == '' && $('#fileUpload')[0].files.length == 0) {
			alert('전송할 내용이 없습니다.');
			$('#chat_content').val('').focus();
			return false;
		}
		if ($('#chat_content').val().length > 300) {
			alert('작성가능 글자수(300자)를 초과했습니다.');
			$('#chat_content').focus();
			return false;
		}
		//작성 글자수/300으로 남은 글자 수 표시하기

		let formData = new FormData(this);

		$.ajax({
			url: 'chalWriteChat',
			type: 'post',
			data: formData,
			processData: false,
			contentType: false,
			success: function(param) {
				if(param.result == 'logout'){
					alert('로그인해야 사용할 수 있습니다.');
				}else if(param.result = 'success'){
					//폼 초기화
					$('#chat_content').val('');
					$('#fileUpload').val('');
				}else{
					alert('채팅 메시지 등록 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류');
			}
		});

		e.preventDefault();


	});//end of ajax
});
