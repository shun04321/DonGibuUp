$(function(){
	let rowCount=10;
	let currentPage;
	let count;
	/* ===========================
	 * 		기부박스 목록
	 * =========================== */	
	function selectList(pageNum){
		currentPage = pageNum;
		
		//서버와 통신
		$.ajax({
			url:'dboxList',
			type:'get',
			data:{pageNum:pageNum,rowCount:rowCount},
			dataType:'json',
			beforeSend:function(){
				$('#loading').show();//로딩 이미지 표시
			},
			complete:function(){
				//success와 error 콜백이 호출된 후에 호출
				$('#loading').hide();//로딩 이미지 숨김
			},
			success:function(param){
				count = param.count;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="col">';
					output += '		<a href="../dbox/'+item.dbox_num+'/content">';
					output += '   	<div class="card h-100" style="width:15rem">';
					output += '     	<img src="../upload/dbox/'+item.dbox_photo+'" class="card-img-top" style="height:10.5rem">';
					output += '         	<div class="card-body">';
					output += '       			<h5 class="card-title">'+item.dbox_title+'</h5>';
					output += '       			<h6 class="card-subtitle mb-2 text-body-secondary">'+item.dcate_name+'</h6>';
					output += '       			<p class="card-text">'+item.dbox_team_name+'</p>';
					output += '				</div>';
					output += '		</div>';
					output += '		</a>';
					output += '	  </div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				
				console.log("currentPage:"+currentPage);
				console.log("count:"+count);
				console.log("rowCount:"+rowCount);
				
				//paging button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	/* ===========================
	 * 초기 데이터(목록) 호출
	 * =========================== */		
	selectList(1);
});