$(document).ready(function() {
	let subUrl=window.location.href.split('keyword=')[1].split('&order=')[0];
	console.log('href : ' + window.location.href.split('keyword=')[1].split('&order=')[0])
	 // 체크박스 상태 변경 시 updateURL 함수 호출
    $('.form-check-input').change(function() {
        subUrl = updateURL();
    });
	//최신순, 오래된순
	$('#order').change(function() {
		var finalUrl = 'dboxAdmin?' +
	                   'keyfield=' + $('#keyfield').val() +
	                   '&keyword=' + $('#keyword').val() +
	                   subUrl +
	                   '&order=' + $('#order').val();

	    // 페이지 이동
	    location.href = finalUrl;
	});
    // 체크된 체크박스들의 값을 수집하여 URL 파라미터로 추가하는 함수입니다.
	function updateURL() {
	    var checkedValues = [];
	
	    // 각 체크박스를 순회하며 체크된 상태인지 확인하여 배열에 추가합니다.
	    $('.form-check-input:checked').each(function() {
	        checkedValues.push($(this).val());
	    });
	
	    // URL 파라미터를 문자열로 변환합니다.
	    var params = '';
	    if (checkedValues.length > 0) {
	        params = '&status=' + checkedValues.join('&status=');
	    }
	
	    // 콘솔에 파라미터 출력 (테스트용)
	    console.log(params);
	
	    return params; // 파라미터 문자열을 반환합니다.
	}
	//찾기 (submit)이벤트 발생시
	$('#search_form').submit(function(){
	    
	});
});