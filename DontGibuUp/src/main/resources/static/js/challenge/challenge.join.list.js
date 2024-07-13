$(document).ready(function() {
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
});