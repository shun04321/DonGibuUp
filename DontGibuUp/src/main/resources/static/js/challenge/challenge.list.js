$(function() {
	let rowCount = 9;
	let currentPage = 1;
	let loading = false;
	let hasMoreData = true;
	let chal_type = '';
	let keyword = '';
	let freqOrder = '';
	let order = '';

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
					observer.unobserve(document.querySelector('#scroll-target'));
				}

				//챌린지 목록 작업
				let output = '';
				$(param.list).each(function(index, item) {
    			
					let sdate = new Date(item.chal_sdate);
					let now = new Date();
					now.setHours(0, 0, 0, 0); // 시간 부분을 0으로 설정
					sdate.setHours(0, 0, 0, 0);
					if (sdate > now) {
						output = '<span>';
						output += '<a href="detail?chal_num=' + item.chal_num + '">';
						output += '<ul>';
						output += '<li>';
						//output += '<img src="">'; //챌린지 썸네일
						output += '</li>';
						output += '<li>';
						output += '<span>' + item.chal_title + '</span>';
						output += '</li>';
						output += '<li>';
						//output += '<img src="../member/">'; //프로필 사진
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
						if (sdate.getTime() == now.getTime()) {
							output += '<span>오늘부터 시작</span>';
						} else if (sdate > now) {
							let difDays = (sdate - now) / (1000 * 60 * 60 * 24);
							output += '<span>' + Math.floor(difDays) + '일 뒤 시작</span>';
						}
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
	
});