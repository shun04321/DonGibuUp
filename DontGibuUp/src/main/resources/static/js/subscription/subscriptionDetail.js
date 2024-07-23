document.addEventListener("DOMContentLoaded", function() {
    function addMonthToDateString(dateStr) {
        let [year, month, day] = dateStr.split('-').map(num => parseInt(num, 10));
        
        if (month < 12) {
            month += 1;
        } else {
            month = 1;
            year += 1;
        }

        month = month.toString().padStart(2, '0');
        day = day.toString().padStart(2, '0');

        return year+"-"+month+"-"+day;
    }
    
    if (/^\d{4}-\d{2}-\d{2}$/.test(subPayDate)) {
        let nextPayDate = addMonthToDateString(subPayDate);
        document.querySelectorAll('.next-pay-date').forEach(function(element) {
            element.textContent = nextPayDate;
        });
    } else {
        console.error("Invalid date format:", subPayDate);
        document.querySelectorAll('.next-pay-date').forEach(function(element) {
            element.textContent = "날짜 오류";
        });
    }

    $('.modify-btn').on('click', function() {
    	if (!confirm('정말 해지하시겠습니까?')) {
            return; // 사용자가 취소 버튼을 클릭한 경우 아무 동작도 하지 않음
        }
        var subNum = $(this).data('num');
        var action = $(this).val() === '해지하기' ? 'cancel' : 'start';

        $.ajax({
            url: '/subscription/updateSub_status',
            type: 'POST',
            data: {
                sub_num: subNum
            },
            dataType: 'json',
            success: function(param) {
                if (param.result === 'logout') {
                    alert('로그인 후 사용해주세요');
                } else if (param.result === 'success') {
                    alert('정기기부가 중지 되었습니다.');

                    // 버튼 텍스트 및 상태 변경
                    $('.modify-btn').each(function() {
                        $(this).val('해지된 정기기부');
                        $(this).prop('disabled', true);
                        location.reload();
                    });
                } else {
                    alert('정기기부 중지 오류 발생');
                }
            },
            error: function() {
                alert('네트워크 오류 발생');
            }
        });
    });
});