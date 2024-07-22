$(function() {
    // Hover 이벤트 핸들러
    $('#notify_icon').hover(
        function() {
		let mem_num = $(this).data('memnum');
            $.ajax({
                url: '/member/getNotification',
                type: 'GET',
                dataType: 'json',
                success: function(param) {
			            var notifyList = param.list;
			            
			            $('#notify_icon_list').empty();
			            
			            // 리스트가 존재하는지 확인
			            if (Array.isArray(notifyList)) {
			                // 반복문을 통해 리스트 항목을 생성하고 추가
			                notifyList.forEach(function(item) {
			                    // 각 항목으로 <li> 요소 생성
			                    var listItem = $(`<li class="px-2 py-1 border-bottom border-1" id="item-${item.not_num}"><a href="${item.not_url}"><small>${item.not_message}</small></a></li>`)
			                        
			                    // 생성한 <li> 요소를 <ul>에 추가
			                    $('#notify_icon_list').append(listItem);
			                });
			            } else {
			                console.error('Invalid data format received.');
			            }
                },
                error: function() {
                    alert('네트워크 오류 발생');
                }
            });
        },
    );
});
