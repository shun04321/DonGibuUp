$(function(){
	//팀 유형 선택 이벤트 발생시
	$('input[name="dbox_team_type"]').on('change',function(){
		if($(this).val()=='1'){
			//기관 선택시 사업자 등록번호 노출  
			let output= '';
				output += '<label><h3>사업자등록번호<span class="validation-check">*필수</span></h3></label>';
				output += '<input type="text" id="dbox_business_rnum" name="dbox_business_rnum" placeholder="숫자 10자리">';
				output += '<span class="form-error"></span>';		
			$('#dbox_business').html(output);
			$('#dbox_business').show();
			
		}else{
			$('#dbox_business').hide();	
		}
	});
	
	//팀 대표이미지
	let photo_path = $('#preview').attr('src');
	$('#dbox_team_photo_file').change(function(){
		
		my_photo = this.files[0]; //선택한 이미지 저장
		if (!my_photo) { //선택하려다 취소한 경우
			$('#preview').attr('src', photo_path);
			return;
		}

		if (my_photo.size > 1024 * 1024) {
			alert(Math.round(my_photo.size / 1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
			$('#preview').attr('src', photo_path);
			$('#dbox_team_photo_file').val('');
			return;
		}

		//이미지 미리보기 처리
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);

		reader.onload = function() {
			$('#preview').attr('src', reader.result);
		};
	});
	
	//팀 소개
	$(document).on('keyup','#dbox_team_detail',function(){
		//입력한 글자수 세팅
		let inputLength = $(this).val().length;
		let remain = 500 - inputLength;
		remain +='/500';
		
		if(inputLength > 500){
			$(this).val($(this).val().substring(0,500));
		}
		
		//문서 객체에 반영
		$('#team_detail_letter').text(remain);		
	});
	
	
	//기부박스 사용계획
	//사용계획 입력 한줄 생성 함수
	function dboxBudgetAdd(bud_num){
		let output = '';
			output += '<div id="bud_num'+bud_num+'">';
			output += '<input type="text" name="dboxBudgets[' + (bud_num - 1) + '].dbox_bud_purpose" class="bud_purpose" placeholder="사용용도 및 산출근거" style="width:40%">';
			output += ' <input type="text" name="dboxBudgets[' + (bud_num - 1) + '].dbox_bud_price" class="bud_price" placeholder="금액(원)" style="width:20%">';
			output += ' <input type="button" data-bud_num="'+bud_num+'" class="bud_delete_btn" value="삭제"><br>';
			output += '</div>';
		$('#dbox_budget').append(output);
	}
	
	//첫 줄은 기본적으로 생성	
	dboxBudgetAdd(1);
	//지출항목 추가 버튼 누를시
	let cnt=2;
	$('#dbox_budget_add').on('click',function(){
		dboxBudgetAdd(cnt);
		cnt++;
	});
	
	//사용계획 삭제
	$(document).on('click','.bud_delete_btn',function(){
		let bud_num = $(this).attr('data-bud_num');
		$('#bud_num'+bud_num).remove();
	});
	
	//사용계획 총합
	$(document).on('keyup','.bud_price',function(){
		budgetTotal();
	});
	function budgetTotal(){
		let total = 0;
		$('.bud_price').each(function(){
			if($(this).val().trim()=='' || $(this).val()==null){
				total += 0;
			}else{
				add = parseInt($(this).val());
				if(isNaN(add)){
					total += 0;
				}else{
					total += add;
				}
			}
		});
		$('#budget_sum').text(total.toLocaleString());
		$('#dbox_goal').val(total);
	}
	//심사위원에게 남길 말
	$(document).on('keyup','#dbox_comment',function(){
		//입력한 글자수 세팅
		let inputLength = $(this).val().length;
		let remain = 1000 - inputLength;
		remain +='/1000';
		
		if(inputLength > 1000){
			$(this).val($(this).val().substring(0,1000));
		}
		
		//문서 객체에 반영
		$('#comment_letter').text(remain);		
	});
	//submit 이벤트 발생시
	$('#step2').submit(function(){

		
		//사업자번호 유효성체크
		if($('input[name="dbox_team_type"]:checked').val()=='1' && !(/^[0-9]{10}$/).test($('#dbox_business_rnum').val())){
			alert('사업자번호는 숫자 10자리만 입력 가능합니다.');
			$('#dbox_business_rnum').val('').focus();
			return false;
		}
		//모금액 사용계획 유효성 체크
		let purpose = $('.bud_purpose');
		let price = $('.bud_price');
		//유효성체크 플래그
		let valid = true;
		
		purpose.each(function(){
			if($(this).val().trim()=='' || !(/^.{0,50}$/).test($(this).val())){
				alert('사용용도 및 산출근거를 작성해주세요.(최대50자)');
				$(this).focus();
				valid=false;
				return false;
			}
		});
		
		if(!valid) return false;
		
		price.each(function(){
			if($(this).val().trim()=='' || !(/^[0-9]{1,9}$/).test($(this).val())){
				alert('금액을 입력해주세요.(숫자만,최대999,999,999원)');
				$(this).focus();
				valid=false;
				return false;
			}		
		});
		
		if(!valid) return false;
		
	});//end of submit
});