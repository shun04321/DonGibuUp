document.addEventListener("DOMContentLoaded", function () {
    $('#refundButton').click(function () {
        let impUid = $('#impUid').val();
        let amount = $('#amount').val();
        let reason = $('#reason').val();

        if (!impUid || !amount || !reason) {
            alert('모든 필드를 입력해주세요.');
            return;
        }

        initiateRefund(impUid, amount, reason);
    });
});

function initiateRefund(impUid, amount, reason) {
    $.ajax({
        url: '/goods/refund',
        method: 'POST',
        data: JSON.stringify({
            impUid: impUid,
            amount: amount,
            reason: reason
        }),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (response) {
            alert('환불 성공: ' + response);
        },
        error: function (xhr) {
            alert('환불 실패: ' + xhr.responseText);
        }
    });
}
