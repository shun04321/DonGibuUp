/*$(document).on('click','.chal_join',function(e){
	e.preventDefault();
	let chal_num = $(this).data('chal-num');
	let chal_joi_num = $(this).data('chal-joi-num');
	let status = $(this).data('status');
	$.ajax({
		url:'list',
		type:'post',
		data:JSON.stringify({
			chal_num:chal_num,
			chal_joi_num:chal_joi_num,
			status:status
		}),
		contentType:'application/json',
		success:function(rsp){
			if(rsp.result == 'logout'){
				window.location.replace(contextPath+'/member/login');
			}else if(rsp.result == 'success'){
				window.location.href='../verify/list';
			}
		},
		error:function(){
			alert('네트워크 오류');
		}
	});
});*/

$(document).on('click','.chal_talk',function(e){
	e.preventDefault();
	let chal_num = $(this).data('chal-num');
	$.ajax({
		url:'joinChal_chat',
		type:'post',
		data:{chal_num:chal_num},
		success:function(rsp){
			if(rsp.result == 'logout'){
				window.location.replace(contextPath+'/member/login');
			}else if(rsp.result == 'success'){
				window.open('chal_chatDetail','Popup','width="400" height="800"');
			}
		},
		error:function(){
			alert('네트워크 오류');
		}
	});
});

function loadChallenges(month) {
	$.ajax({
		url: contextPath + '/challenge/join/list',
		data: { status: status, month: month },
		success: function(response) {
			$('#challengeContainer').html($(response).find('#challengeContainer').html());
			updateEventListeners(); // Update event listeners after AJAX load
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}

function deleteChallenge(chalJoiNum) {
	if (!confirm('챌린지를 취소하시겠습니까?')) {
		return;
	}

	$.ajax({
		url: contextPath + '/challenge/join/delete',
		type: 'POST',
		data: { chal_joi_num: chalJoiNum },
		success: function(response) {
			alert('챌린지가 취소되었습니다.');
			location.reload(); // 페이지 새로고침
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
			alert('챌린지 취소 중 오류가 발생했습니다.');
		}
	});
}

function updateEventListeners() {
	$('#prevMonth').off('click').on('click', function() {
		const currentMonth = $('#currentMonth').data('month');
		const date = new Date(currentMonth + "-01");
		date.setMonth(date.getMonth() - 1);
		const newMonth = date.toISOString().slice(0, 7);
		$('#currentMonth').data('month', newMonth);
		loadChallenges(newMonth);
	});

	$('#nextMonth').off('click').on('click', function() {
		const currentMonth = $('#currentMonth').data('month');
		const date = new Date(currentMonth + "-01");
		date.setMonth(date.getMonth() + 1);
		const newMonth = date.toISOString().slice(0, 7);
		$('#currentMonth').data('month', newMonth);
		loadChallenges(newMonth);
	});
}

updateEventListeners(); // Initial call to set event listeners

// Make deleteChallenge function globally accessible
window.deleteChallenge = deleteChallenge;