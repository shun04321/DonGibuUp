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
});