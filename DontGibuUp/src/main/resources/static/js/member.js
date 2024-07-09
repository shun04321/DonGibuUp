$(function() {
	/*===============================
		  회원 프로필사진 수정
	================================*/
	$('#photo_choice').click(function() {
		$('#mem_photo').click();

		//처음 화면에 보여지는 이미지 읽기
		let photo_path = $('.my-photo').attr('src');

		//파일 선택 이벤트 연결
		$('#mem_photo').change(function() {
			my_photo = this.files[0]; //선택한 이미지 저장
			if (!my_photo) { //선택하려다 취소한 경우
				$('.my-photo').attr('src', photo_path);
				return;
			}

			if (my_photo.size > 1024 * 1024) {
				alert(Math.round(my_photo.size / 1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
				$('.my-photo').attr('src', photo_path);
				$(this).val('');
				return;
			}

			//이미지 미리보기 처리
			const reader = new FileReader();
			reader.readAsDataURL(my_photo);

			reader.onload = function() {
				$('.my-photo').attr('src', reader.result);
			};
			
			//선택한 사진이 있으면 ajax 요청
			if (this.files[0]) {
				const form_data = new FormData();
				form_data.append('upload', this.files[0]);
				
				//서버와 통신
				$.ajax({
					url:'modifyMemPhoto',
					type: 'post',
					data: form_data,
					dataType: 'json',
					contentType: false,
					processData: false,
					success: function(param) {
						if (param.result == 'logout') {
							alert('로그인 후 사용하세요');
							location.href="../login";
						} else if (param.result == 'success') {
							//교체된 이미지 저장
							photo_path = $('.my-photo').attr('src'); //다시 작업할 수도 있기 때문에 저장해둠
						} else {
							alert('파일 전송 오류 발생');
						}
					},
					error: function() {
						alert('네트워크 오류 발생');
					}
				});
			}
		});

	}); //photo_choic click 이벤트
});