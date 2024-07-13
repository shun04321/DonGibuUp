$(function(){
	
	$('input[name="dbox_team_type"]').on('change',function(){
		
		if($(this).val()=='1'){
			//기관 선택시 사업자 등록번호 노출  
			let output= '';
				output += '<label><h3>사업자등록번호(기관)</h3></label>';
				output += '<input type="text" id="dbox_business_rnum" name="dbox_business_rnum" placeholder="숫자 10자리">';
				output += '<span class="form-error"></span>';		
			$('#dbox_business').html(output);
			$('#dbox_business').show();
			
		}else{
			$('#dbox_business').hide();	
		}
	});
	
	$('#step2').submit(function(){
		if($('input[name="dbox_team_type"]:checked').length==0){
			alert('기관/개인을 꼭 선택해주세요.');
			return false;
		}
		if($('input[name="dbox_team_type"]:checked').val()=='1' && $('#dbox_business_rnum').val().trim()==''){
			alert('사업자번호는 숫자 10자리만 입력 가능합니다.');
			$('#dbox_business_rnum').val('').focus();
			return false;
		}
		
	});
	
});