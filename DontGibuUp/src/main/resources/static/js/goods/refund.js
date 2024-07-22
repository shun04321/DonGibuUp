document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 필요한 초기 작업들
    let pageContextPath = contextPath;
});

function requestRefund(impUid, reason) {
    let pageContextPath = contextPath;

    $.ajax({
        url: pageContextPath + '/goods/refund',
        method: 'POST',
        data: JSON.stringify({ imp_uid: impUid, reason: reason }),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function(response) {
            if (response.result === 'success') {
                alert('환불이 성공적으로 처리되었습니다.');
                // 페이지를 새로고침하거나 환불 상태를 업데이트할 수 있습니다.
                location.reload();
            } else if (response.result === 'logout') {
                alert('로그인이 필요합니다.');
                window.location.href = pageContextPath + '/login';
            } else {
                alert('환불 처리 중 오류가 발생했습니다: ' + response.message);
            }
        },
        error: function(xhr, status, error) {
            alert('환불 처리 오류 발생: ' + error);
        }
    });
}
