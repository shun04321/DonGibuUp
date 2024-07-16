function deleteVerify(chal_ver_num) {
    if (confirm('인증을 삭제하시겠습니까?')) {
        $.ajax({
            url: '/challenge/verify/delete',
            type: 'POST',
            data: { chal_ver_num: chal_ver_num },
            success: function(response) {
                alert('인증이 삭제되었습니다.');
                location.reload();  // 페이지를 새로고침하여 삭제된 내용을 반영
            },
            error: function(xhr, status, error) {
                alert('인증 삭제 중 오류가 발생했습니다.');
            }
        });
    }
}