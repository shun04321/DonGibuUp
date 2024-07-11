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
					url: 'modifyMemPhoto',
					type: 'post',
					data: form_data,
					dataType: 'json',
					contentType: false,
					processData: false,
					success: function(param) {
						if (param.result == 'logout') {
							alert('로그인 후 사용하세요');
							location.href = "../login";
						} else if (param.result == 'success') {
							//교체된 이미지 저장
							photo_path = $('.my-photo').attr('src'); //다시 작업할 수도 있기 때문에 저장해둠
							//삭제버튼 보이게
							$('#photo_del').show();
						} else {
							alert('파일 전송 오류 발생');
						}
					},
					error: function() {
						alert('네트워크 오류 발생');
					}
				});
			} //end of if
		}); //end of onchange
	}); //photo_choice click 이벤트

	/*===============================
		  회원 프로필사진 삭제
	================================*/
	$('#photo_del').click(function() {
		let photo_del = $(this);
		//서버와 통신
		$.ajax({
			url: 'deleteMemPhoto',
			type: 'post',
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 사용하세요');
					location.href = "../login";
				} else if (param.result == 'success') {
					$('.my-photo').attr('src', contextPath + '/images/basicProfile.png'); //이미지 안보이게 처리
					photo_del.hide(); //삭제 버튼 안보이게
				} else {
					alert('파일 삭제 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
	});

  const yearSelect = $('#birth_year');
  const monthSelect = $('#birth_month');
  const daySelect = $('#birth_day');

  // 년도 옵션 추가
  const currentYear = new Date().getFullYear();
  for (let year = currentYear; year >= 1900; year--) {
    yearSelect.append(`<option value="${year}">${year}</option>`);
  }

  // 월 옵션 추가
  const months = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];
  $.each(months, function(index, month) {
    monthSelect.append(`<option value="${month}">${month}</option>`);
  });

  // 일 옵션 추가 (1일부터 31일까지)
  for (let day = 1; day <= 31; day++) {
    daySelect.append(`<option value="${day}">${day}</option>`);
  }

  // 폼 제출 시 처리
  $('#birthday-form').submit(function(event) {
    event.preventDefault();

    const selectedYear = yearSelect.val();
    const selectedMonth = monthSelect.val();
    const selectedDay = daySelect.val();

    // 선택된 값으로 생년월일 문자열 생성
    const birthday = `${selectedYear}-${selectedMonth.padStart(2, '0')}-${selectedDay.padStart(2, '0')}`;
    console.log('생일:', birthday);

    // 이후 원하는 작업 수행 (예: 서버로 전송 등)
  });

});