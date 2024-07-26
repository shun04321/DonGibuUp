$(function() {
	const customFormatter = new Intl.NumberFormat('en-US', {
	    style: 'decimal',
	    minimumFractionDigits: 0,
	    maximumFractionDigits: 0,
	});
	
	$.ajax({
		url: 'memberTotal',
		type: 'get',
		dataType: 'json',
		success: function(param) {
			if (param.result == "logout") {
				alert('로그인 후 사용하세요');
                location.href = "../login";
			} else if (param.result == "success") {
				$('#mem_point').text(customFormatter.format(param.memberTotal.mem_point));
				$('#total_count').text(param.memberTotal.total_count);
				$('#total_amount').text(customFormatter.format(param.memberTotal.total_amount));
			} else {
				alert('회원정보 불러오기 오류 발생');
			}
		},
		error: function() {
			alert('네트워크 오류 발생');
		}
	})
});