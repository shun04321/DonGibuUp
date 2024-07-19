$(function() {
	$('.updatePointBtn').click(function () {
		let point_input = $(this).closest('td').find('.member-point');
		let mem_point = point_input.val();
		let mem_num = $(this).data('num');
		
		$.ajax({
			url:'updatePoint',
			data: {mem_num:mem_num, mem_point:mem_point},
			type: 'post',
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 이용해주세요');
					location.href = contextPath + '/member/login';
				} else if (param.result == 'success') {
					point_input.val(mem_point);
				} else if (param.result == 'noAuthority') {
					alert('관리자 권한이 없습니다');
					location.reload();
				} else {
					alert('포인트 수정 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
		console.log(mem_point);
	});
});