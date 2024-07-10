$(function() {
	let pw_checked = 0;
	/*===============================
		마이페이지 - 비밀번호 변경
	================================*/
	//현재 비밀번호 체크
	$('#current_pw').on('blur', function() {
		const currentPassword = $(this).val();

		$.ajax({
			url: 'checkPassword',
			method: 'POST',
			data: { currentPassword: currentPassword },
			dataType: 'json',
			success: function(param) {
				const feedbackElement = $('#password_feedback');
				const inputElement = $('#current_pw');
				feedbackElement.show();
				if (param.result == "matched") {
					pw_checked = 1;
					feedbackElement.text('비밀번호가 일치합니다.');
					feedbackElement.css('color', 'green');

					inputElement.css('border-color', 'green');
				} else if (param.result == "notMatched" && currentPassword != "") {
					pw_checked = 0;
					feedbackElement.text('비밀번호가 일치하지 않습니다.');
					feedbackElement.css('color', 'red');

					inputElement.css('border-color', 'red');
					inputElement.val('');
				} else if (currentPassword == "") {
					pw_checked = 0;
					feedbackElement.text('');
					feedbackElement.css('color', 'black');
				
					inputElement.css('border-color', 'black');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
	});

	//현재 비밀번호 다시 입력시 알림 메세지 없애기
	$('#current_pw').on('keyup', function() {
		const feedbackElement = $('#password_feedback');
		const inputElement = $('#current_pw');
		pw_checked = 0;
		feedbackElement.text('');
		feedbackElement.css('color', 'black');

		inputElement.css('border-color', 'black');
	});
});