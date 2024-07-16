$(function() {
	let rowCount = 9;
	let currentPage = 1;
	let loading = false;
	let hasMoreData = true;
	let chal_type = '';
	let keyword = '';
	let freqOrder = '';
	let order = '';
	
	// 카테고리 링크 클릭 이벤트
	$('.category-link').on('click', function(e) {
		e.preventDefault();
		chal_type = $(this).data('category');
		hasMoreData = true;
		$('#output').empty();
		selectList(1);
	});

	//인증 빈도 선택 이벤트
	$('.freqOrder').on('change', function() {
		freqOrder = $(this).val();
		hasMoreData = true;
		$('#output').empty();
		selectList(1);
	});

	//검색 이벤트
	$('#searchTitle').on('submit', function(e) {
		e.preventDefault();
		keyword = $(this).find('input[type="search"]').val();
		if (keyword == '') {
			alert('검색어를 입력하세요');
			return false;
		}

		hasMoreData = true;
		$('#output').empty();
		selectList(1);
	});
	
	//정렬 선택 이벤트
	$('.order').on('change',function(){
		order = $(this).val();
		hasMoreData = true;
		$('#output').empty();
		selectList(1);
	});
	
	/*---------------------
	 * 챌린지 목록
	 *---------------------*/
	function selectList(currentPage) {
		if (loading || !hasMoreData) return;
		
		loading = true;
		
		$.ajax({
			url: 'addlist',
			type: 'get',
			data: {
				pageNum: currentPage,
				rowCount: rowCount,
				chal_type: chal_type,
				freqOrder: freqOrder,
				keyword: keyword,
				order: order
			},
			dataType: 'json',
			success: function(param) {
				if (currentPage * param.list.length >= param.count) {
					hasMoreData = false;
					const scrollTarget = document.querySelector('#scroll-target');
					if (scrollTarget) {
						observer.unobserve(scrollTarget);
					}
				}

				//챌린지 목록 작업
				let output = '';
				$(param.list).each(function(index, item) {
    			
					let edate = new Date(item.chal_edate);
					let now = new Date();
					now.setHours(0, 0, 0, 0); // 시간 부분을 0으로 설정
					sdate.setHours(0, 0, 0, 0);
					if (edate < now) {
						output = '<span class="chal_listElement">';
						output += '<a href="detail?chal_num=' + item.chal_num + '">';
						output += '<ul>';
						output += '<li>';
						if(item.chal_photo){
							output += '<img src="'+pageContext+'/upload/'+item.chal_photo+'" width="100" height="40">'; //챌린지 썸네일
						}else{
							output += '<img src="'+pageContext+'/images/챌린지_기본이미지.jpg" width="100" height="40">'; //챌린지 썸네일 - 기본 이미지
						}
						output += '</li>';
						output += '<li>';
						output += '<span>' + item.chal_title + '</span>';
						output += '</li>';
						output += '<li>';
						if(item.mem_photo){
							output += '<img src="'+pageContext+'/upload/'+item.mem_photo+'" width="20" height="20">'; //
						}else{
							output += '<img src="'+pageContext+'/images/basicProfile.png" width="20" height="20">'; //챌린지 썸네일 - 기본 이미지
						}
						output += '<span>' + item.mem_nick + '</span>';
						output += '</li>';
						output += '<li>';
						if (item.chal_freq == 0) {
							output += '<span>매일</span>';
						} else if (item.chal_freq == 1) {
							output += '<span>주1일</span>';
						} else if (item.chal_freq == 2) {
							output += '<span>주2일</span>';
						} else if (item.chal_freq == 3) {
							output += '<span>주3일</span>';
						} else if (item.chal_freq == 4) {
							output += '<span>주4일</span>';
						} else if (item.chal_freq == 5) {
							output += '<span>주5일</span>';
						} else if (item.chal_freq == 6) {
							output += '<span>주6일</span>';
						}
						output += '</li>';

						output += '<li>';
						output += item.chal_sdate+'~'+item.chal_edate;
						output += '</li>';
						output += '</ul>';
						output += '</a>';
						output += '</span>';																		
					}
					$('#output').append(output);
				});				
				
				$('#output').append($('#scroll-target'));
				
				loading = false;

			},
			error: function() {
				loading = false;
				alert('네트워크 오류');
			}
		});
	}
	
	const observer = new IntersectionObserver((entries, observer) => {
		entries.forEach(entry => {
			if (entry.isIntersecting && hasMoreData) {
				currentPage++;
				selectList(currentPage);
			}
		});
	}, {
		root: null,
		rootMargin: '0px',
		threshold: 1.0
	});	
	
	const target = document.createElement('div');
	target.id = 'scroll-target';
	document.getElementById('output').appendChild(target);
	observer.observe(target);


	/*---------------------
	 * 초기 데이터 호출
	 *---------------------*/
	selectList(1);
	
});