		$(document).ready(function () {
            $('#chal_rev_content').on('input', function () {
                let content = $(this).val();
                let charCount = content.length;
                $('.char-count').text(charCount + ' / 최소 20자');
            });

            $('form').on('submit', function (e) {
                let isValid = true;
                if (!$('input[name="chal_rev_grade"]:checked').val()) {
                    isValid = false;
                    $('.rating-error').text('별점을 선택해주세요.');
                } else {
                    $('.rating-error').text('');
                }

                if ($('#chal_rev_content').val().length < 20) {
                    isValid = false;
                    $('.content-error').text('최소 20자 이상 입력해주세요.');
                } else {
                    $('.content-error').text('');
                }

                if (!isValid) {
                    e.preventDefault();
                }
            });
        });