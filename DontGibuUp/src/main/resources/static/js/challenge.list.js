$(function(){
	let rowCount = 9;
	let currentPage=1;
	let loading = false;
	let hasMoreData = true;
	
	/*---------------------
	 * 챌린지 목록
	 *---------------------*/
	window.onscroll = function() {
    	if (hasMoreData && (window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        	currentPage++;
    		selectList(currentPage);
        }
    };
  
	function selectList(currentPage){
		
		$.ajax({
			url:'addlist',
			type:'get',
			data:{pageNum:currentPage,rowCount:rowCount},
			dataType:'json',
			success:function(param){
				
				loading = true;
				
				if(currentPage > param.count){
					loading = false;
					hasMoreData = false;	
					return;
				}

				//챌린지 목록 작업
				$(param.list).each(function(index,item){
					let sdate = new Date(item.chal_sdate);
					let now = new Date();
					if(sdate > now){
						let output = '<div>';
						output += '<a href="detail?chal_num='+item.chal_num+'">';
						output += '<ul>';
						output += '<li>';
						//output += '<img src="">'; //챌린지 썸네일
						output += '</li>';
						output += '<li>';
						output += '<span>'+item.chal_title+'</span>';
						output += '</li>';
						output += '<li>';
						//output += '<img src="../member/">'; //프로필 사진
						output += '<span>'+item.mem_nick+'</span>';					
						output += '</li>';
						output += '<li>';
						if(item.chal_freq == 0){
							output += '<span>매일</span>';
						}else if(item.chal_freq == 1){
							output += '<span>주1일</span>';
						}else if(item.chal_freq == 2){
							output += '<span>주2일</span>';
						}else if(item.chal_freq == 3){
							output += '<span>주3일</span>';
						}else if(item.chal_freq == 4){
						output += '<span>주4일</span>';
						}else if(item.chal_freq == 5){
							output += '<span>주5일</span>';
						}else if(item.chal_freq == 6){
							output += '<span>주6일</span>';
						}										
						output += '</li>';
						
						output += '<li>';
						if(sdate == now){
							output += '<span>오늘부터 시작</span>';
						}else if(sdate > now){
							let difDays = (sdate - now)/(1000 * 60 * 60 * 24);
							output += '<span>'+Math.floor(difDays)+'일 뒤 시작</span>';
						}
						output += '</li>';
						output += '</ul>';
						output += '</a>';
						output += '</div>';
						
						$('#output').append(output);
					}
					
				});
				loading = false;
									
			},
			error:function(){
				loading = false;	
				alert('네트워크 오류');
			}
		});
	}
	
	/*---------------------
	 * 초기 데이터 호출
	 *---------------------*/
	selectList(1);
});