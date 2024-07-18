$(function() {
	$('#insert_faq').submit(function(event) {
		event.preventDefault();

		if ($('#faq_question').val().trim() == '' || $('#faq_answer').val().trim() == '') {

			if ($('#faq_question').val().trim() == '') {
				$('#question_check_msg').text('질문 내용을 입력해주세요');
				$('#question_check_msg').css('color', 'red');
			}
			if ($('#faq_answer').val().trim() == '') {
				$('#answer_check_msg').text('답변 내용을 입력해주세요');
				$('#answer_check_msg').css('color', 'red');
			}

			return false;
		}

		if ($('#faq_answer').val().trim() == '') {
			$('#answer_check_msg').text('답변 내용을 입력해주세요');
			return false;
		}

		//유효성체크 통과시
		let form_data = $('#insert_faq').serialize();
		$.ajax({
			url: '/insertFaq',
			type: 'post',
			data: form_data,
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 이용해주세요');
					location.href = contextPath + '/member/login';
				} else if (param.result == 'success') {
					$('#question_check_msg').text('');
					$('#answer_check_msg').text('');
					$('#answer_check_msg').css('color', 'black');
				} else if (param.result == 'noAuthority') {
					alert('관리자 권한이 없습니다');
					location.reload();
				} else {
					alert('질문 등록 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});




	});
});