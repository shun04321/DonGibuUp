$(function(){
	//기부박스 대표이미지
	let photo_path = $('#preview').attr('src');
	$('#dbox_photo_file').change(function(){
		
		my_photo = this.files[0]; //선택한 이미지 저장
		if (!my_photo) { //선택하려다 취소한 경우
			$('#preview').attr('src', photo_path);
			return;
		}

		if (my_photo.size > 1024 * 1024) {
			alert(Math.round(my_photo.size / 1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
			$('#preview').attr('src', photo_path);
			$('#dbox_photo_file').val('');
			return;
		}

		//이미지 미리보기 처리
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);

		reader.onload = function() {
			$('#preview').attr('src', reader.result);
		};
	});
});