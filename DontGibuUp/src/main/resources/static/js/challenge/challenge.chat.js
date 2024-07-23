$(function() {
	/*----------------------
	 * 웹 소켓 연결
	 *----------------------*/


	/*----------------------
	 * 채팅하기
	 *----------------------*/
	//채팅 메시지 입력하기
	$('#chat_content').keydown(function(e) {
		if (e.keyCode == 13 && !e.shiftKey) {
			$('#chat_writeForm').trigger(e);
		}
	});

	//채팅 메시지,파일 전송하기
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
				if (param.result == 'logout') {
					alert('로그인 후 채팅 가능합니다.');
				} else if (param.result = 'success') {
					//폼 초기화
					$('#chat_content').val('');
					$('#fileUpload').val('');
					//웹 소켓으로 바꿀 예정
					readChat();
				} else {
					alert('채팅 메시지 등록 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류');
			}
		});//end of ajax
		e.preventDefault();
	});

	//채팅 메시지 읽기
	function readChat() {
		$.ajax({
			url: 'chalReadChat',
			type: 'get',
			data: { chal_num: $('#chal_num').val() },
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 채팅 가능합니다.')
				} else if (param.result == 'success') {
					console.log('success');
					//채팅창 UI 초기화
					$('#chatting_message').empty();

					let chat_date = '';
					//채팅메시지 보여지게 하기	
					$(param.chatList).each(function(index, item) {
						let output = '';
						//날짜 표시
						if (chat_date != item.chat_date.split(' ')[0]) {
							chat_date = item.chat_date.split(' ')[0];
							output += '<div class="날짜 표시 css 지정"><span>' + chat_date + '</span></div>';
						}

						//메시지 표시
						if (item.chat_content != null && item.chat_filename != null) {//메시지,이미지 모두 있는 경우

						} else if (item.chat_content != null) {//메시지만 있는 경우
							readContent(param,item);
						} else {//이미지만 있는 경우

						}

						//문서 객체에 추가
						$('#chatting_message').append(output);
						//스크롤을 하단에 위치시킴
						$('#chatting_message').scrollTop($("#chatting_message")[0].scrollHeight);
					});
				} else {
					alert('채팅 오류 발생');
				}
			},
			error: function() {
				alert('채팅 통신 오류 발생!');
			}
		});
	}

	function readContent(param,item) {
		let sub_output = '';
		if (item.chat_content.indexOf('@{common}@') >= 0) {
			//챌린지 시작, 신규 회원 입장 메시지
			sub_output += '<div class="greeting-message">';
			sub_output += item.chat_content.substring(0, item.message.indexOf('@{common}@'));
			sub_output += '</div>';
		} else {
			//일반 메시지
			if (item.mem_num != param.mem_num) {
				sub_output += '<div class="to-position">';
				sub_output += '<div class="space-photo">';
				if(item.mem_photo != null){
					sub_output += `<img src="${contextPath}/upload/${item.mem_photo}" width="40" height="40" class="my-photo">`;
				}else{
					sub_output += `<img src="${contextPath}/image/basicProfile.png" width="40" height="40" class="my-photo">`;
				}				
				sub_output += '</div><div class="space-message">';
				sub_output += item.id;
			}else{
				sub_output += '<div class="from-position">';
			}
			sub_output += '<div class="item">';
			sub_output +=  + ' <span>' + item.chal_content.replace(/\r\n/g, '<br>').replace(/\r/g, '<br>').replace(/\n/g, '<br>') + '</span>';
			sub_output += '</div>';
			//안 읽은 사람수, 작성 시간 추출
			sub_output += `<span>
						    <div>${item.chat_readCount}</div>
						    <div>${item.chat_date.split(' ')[1]}</div>
						   </span>`;
			sub_output += '</div><div class="space-clear"></div>';
			sub_output += '</div>';
		}
	}

	function readImage(item) {
		let sub_output = '';

	}

	readChat();
});
