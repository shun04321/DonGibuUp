$(function() {
	let originalContent;
	let currentItem;
	let modifyingFaqNum;
	const urlParams = new URLSearchParams(window.location.search);
	const category = urlParams.get('category');
	console.log(category);
    updateRadioButtons(category);

	//faq 등록
	$('#insert_faq').submit(function(event) {
		event.preventDefault();

		if ($('#faq_question').val().trim() == '' || $('#faq_answer').val().trim() == '' || !$('input[name="faq_category"]:checked').val()) {

			if ($('#faq_question').val().trim() == '') {
				$('#question_check_msg').text('질문 내용을 입력해주세요');
				$('#question_check_msg').css('color', 'red');
			}
			if ($('#faq_answer').val().trim() == '') {
				$('#answer_check_msg').text('답변 내용을 입력해주세요');
				$('#answer_check_msg').css('color', 'red');
			}

			// 라디오 버튼 유효성 검사
			if (!$('input[name="faq_category"]:checked').val()) {
				$('#category_check_msg').text('카테고리를 선택해주세요');
				$('#category_check_msg').css('color', 'red');
			}

			return false;
		}

		let faq_question = $('#faq_question').val().trim();
		let faq_answer = $('#faq_answer').val().trim();
		let faq_category = $('input[name="faq_category"]:checked').val();


		//유효성체크 통과시
		let form_data = $('#insert_faq').serialize();
		$.ajax({
			url: 'insertFaq',
			type: 'post',
			data: form_data,
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 이용해주세요');
					location.href = contextPath + '/member/login';
				} else if (param.result == 'success') {
					$('#question_check_msg').text('');
					$('#answer_check_msg').text('');
					$('#category_check_msg').text('');

					let faq_num = param.faq_num;

					let faq_category_text = '';
					if (faq_category == 0) {
						faq_category_text = '정기기부';
					} else if (faq_category == 1) {
						faq_category_text = '기부박스';
					} else if (faq_category == 2) {
						faq_category_text = '챌린지';
					} else if (faq_category == 3) {
						faq_category_text = '굿즈샵';
					} else if (faq_category == 4) {
						faq_category_text = '기타';
					}

					let output = '';
					output += '<li class="faq-item">';
					output += `<div class="faq-category-text">${faq_category_text}</div>`;
					output += `<h4 class="faq-question-text">Q. ${faq_question}</h4>`;
					output += `<div class="faq-answer-text">A. ${faq_answer}</div>`;
					output += '<div class="align-right">';
					output += `<button class="modifyBtn" data-num="${faq_num}">수정</button>`;
					output += ` <button class="deleteBtn" data-num="${faq_num}">삭제</button>`;
					output += '</div>';
					output += '</li>';

					$('#faq_list').append(output);

					$('#faq_question').val('');
					$('#faq_answer').val('');
					$('input[name="faq_category"]').prop('checked', false);

				} else if (param.result == 'noAuthority') {
					alert('관리자 권한이 없습니다');
					location.reload();
				} else {
					alert('질문 등록 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
	}); //end-of-submit

	//faq 수정 폼
	$(document).on('click', '.modifyBtn', function() {
		// 현재 수정 중인 요소가 있을 경우, 취소 처리
		if (currentItem) {
			initModifyForm(modifyingFaqNum);
		}

		var faqItem = $(this).closest('.faq-item');

		// 원래 상태를 저장
		originalContent = faqItem.html();
		currentItem = faqItem;

		let faq_category = faqItem.find('.faq-category-text').text();
		let faq_question = faqItem.find('.faq-question-text').text().substring(2);
		let faq_answer = faqItem.find('.faq-answer-text').text().substring(2);
		let faq_num = $(this).data('num');

		modifyingFaqNum = faq_num;

		let modifyUI = `
				        <ul>
				            <li class="faq-category-text">
								${faq_category}
				            </li>
				            <li>
				            	<input type="hidden" id="mfaq_num" value="${faq_num}"> 
				            </li>
				            <li>
				                <input id="mfaq_question" type="text" placeholder="질문" value="${faq_question}">
				                <span id="mquestion_check_msg"></span>    
				            </li>
				            <li>
				                <textarea id="mfaq_answer" rows="5" cols="60" placeholder="내용">${faq_answer}</textarea>
				                <span id="manswer_check_msg"></span>
				            </li>
				            <li class="align-right">
				                <input class="processModifyBtn" type="button" value="수정">    
				                <button id="cancel_${faq_num}" class="cancelBtn">취소</button>
				            </li>
				        </ul>`;

		faqItem.html(modifyUI);

	})

	//faq  수정
	$(document).on('click', '.processModifyBtn', function() {
		var faqItem = $(this).closest('.faq-item');

		//유효성 체크
		if ($('#mfaq_question').val().trim() == '' || $('#mfaq_answer').val().trim() == '') {

			if ($('#mfaq_question').val().trim() == '') {
				$('#mquestion_check_msg').text('질문 내용을 입력해주세요');
				$('#mquestion_check_msg').css('color', 'red');
			}
			if ($('#mfaq_answer').val().trim() == '') {
				$('#manswer_check_msg').text('답변 내용을 입력해주세요');
				$('#manswer_check_msg').css('color', 'red');
			}

			return false;
		}

		let faq_question = $('#mfaq_question').val().trim();
		let faq_answer = $('#mfaq_answer').val().trim();
		let faq_category = faqItem.find('.faq-category-text').text().trim();
		let faq_num = $('#mfaq_num').val();

		modifyingFaqNum = faq_num;

		//유효성체크 통과시
		$.ajax({
			url: 'modifyFaq',
			type: 'post',
			data: { faq_num: faq_num, faq_question: faq_question, faq_answer: faq_answer },
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 이용해주세요');
					location.href = contextPath + '/member/login';
				} else if (param.result == 'success') {

					let output = '';
					output += `<div class="faq-category-text">${faq_category}</div>`;
					output += `<h4 class="faq-question-text">Q. ${faq_question}</h4>`;
					output += `<div class="faq-answer-text">A. ${faq_answer}</div>`;
					output += '<div class="align-right">';
					output += `<button class="modifyBtn" data-num="${faq_num}">수정</button>`;
					output += ` <button class="deleteBtn" data-num="${faq_num}">삭제</button>`;
					output += '</div>';


					// 현재 수정 중인 요소를 초기화
					currentItem = null;

					faqItem.html(output);

				} else if (param.result == 'noAuthority') {
					alert('관리자 권한이 없습니다');
					location.reload();
				} else {
					alert('질문 등록 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
	});

	// FAQ 수정 취소
	$(document).on('click', '.cancelBtn', function() {
		// 원래 상태로 복원
		if (currentItem) {
			currentItem.html(originalContent);
			currentItem = null;
		}
	});

	// 삭제
	$(document).on('click', '.deleteBtn', function() {
		let $deleteButton = $(this); // 클릭된 버튼을 변수에 저장
		let faq_num = $deleteButton.data('num');

		$.ajax({
			url: 'deleteFaq',
			type: 'post',
			data: { faq_num: faq_num },
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 이용해주세요');
					location.href = contextPath + '/member/login';
				} else if (param.result == 'success') {
					// 저장한 변수 사용
					$deleteButton.closest('.faq-item').remove(); // .faq-item 요소를 삭제
				} else if (param.result == 'noAuthority') {
					alert('관리자 권한이 없습니다');
					location.reload();
				} else {
					alert('질문 삭제 오류 발생');
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
			}
		});
	});
	
	
});

function initModifyForm(modifyingFaqNum) {
	$(`#cancel_${modifyingFaqNum}`).trigger('click');
	modifyingFaqNum = '';
}

function updateRadioButtons(category) {
    // 모든 radio-option 요소를 가져와서 초기화
    $('.radio-option').each(function() {
        var input = $(this).find('input[type="radio"]');
        
        if (category === null || category === undefined) {
            // 카테고리 값이 null 또는 undefined일 때 아무 것도 선택되지 않도록 설정
            input.prop('checked', false);
            $(this).show(); // 기본적으로 모든 라디오 버튼을 표시
        } else if (input.val() == category) {
            // 카테고리 값이 특정 값일 때 해당 라디오 버튼을 선택
            input.prop('checked', true);
            $(this).show(); // 선택된 버튼은 표시
        } else {
            // 카테고리 값이 다른 값일 때 해당 라디오 버튼을 숨김
            $(this).hide(); 
        }
    });
}
