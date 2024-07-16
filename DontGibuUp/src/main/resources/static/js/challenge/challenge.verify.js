function showEditForm(chal_ver_num) {
    $.ajax({
        url: contextPath + '/challenge/verify/detail',
        type: 'GET',
        data: { chal_ver_num: chal_ver_num },
        success: function(response) {
            $('#content-' + chal_ver_num).hide();
            $('#edit-form-' + chal_ver_num).html(response).show();
        },
        error: function(xhr, status, error) {
            alert('수정 폼을 로드하는 중 오류가 발생했습니다.');
        }
    });
}

function updateContent(chal_ver_num) {
    const newContent = $('#textarea-' + chal_ver_num).val();

    $.ajax({
        url: contextPath + '/challenge/verify/update',
        type: 'POST',
        data: { chal_ver_num: chal_ver_num, chal_content: newContent },
        success: function(response) {
            $('#content-' + chal_ver_num).text(newContent).show();
            $('#edit-form-' + chal_ver_num).hide();
        },
        error: function(xhr, status, error) {
            alert('인증 내용 수정 중 오류가 발생했습니다.');
        }
    });
}

function hideEditForm(chal_ver_num) {
    $('#edit-form-' + chal_ver_num).hide();
    $('#content-' + chal_ver_num).show();
}

function deleteVerify(chal_ver_num) {
    if (confirm('인증을 삭제하시겠습니까?')) {
        $.ajax({
            url: contextPath + '/challenge/verify/delete',
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