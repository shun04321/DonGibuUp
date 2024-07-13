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
	
	//submit 이벤트 발생시
	$('#step2').submit(function(){

		
		//사업자번호 유효성체크
		if($('input[name="dbox_team_type"]:checked').val()=='1' && !(/^[0-9]{10}$/).test($('#dbox_business_rnum').val())){
			alert('사업자번호는 숫자 10자리만 입력 가능합니다.');
			$('#dbox_business_rnum').val('').focus();
			return false;
		}
		//모금액 사용계획 유효성 체크
		let purpose = $('input[name="dboxBudget.dbox_budget_purpose"]');
		let price = $('input[name="dboxBudget.dbox_budget_price"]');
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
	
	//기부박스 사용계획
	
	//사용계획 입력 한줄 생성 함수
	function dboxBudgetAdd(bud_num){
		let output = '';
			output += '<div id="bud_num'+bud_num+'">';
			output += '<input type="text" name="dboxBudget.dbox_bud_purpose" placeholder="사용용도 및 산출근거" style="width:40%">';
			output += ' <input type="text" name="dboxBudget.dbox_bud_price" placeholder="금액(원)" style="width:20%">';
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
});