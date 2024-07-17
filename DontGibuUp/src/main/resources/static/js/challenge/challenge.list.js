$(function() {
	let rowCount = 3;
	let currentPage = 1;
	let loading = false;
	let hasMoreData = true;
	let chal_type = '';
	let keyword = '';
	let freqOrder = '';
	let order = '';
	var realIdx = 0;

	// 카테고리 링크 클릭 이벤트
	$('.category-link').on('click', function(e) {
		e.preventDefault();
		chal_type = $(this).data('category');
		hasMoreData = true;
		$('#output').empty();
		currentPage = 1;
		selectList(currentPage);
	});

	//인증 빈도 선택 이벤트
	$('.freqOrder').on('change', function() {
		freqOrder = $(this).val();
		hasMoreData = true;
		$('#output').empty();
		currentPage = 1;
		selectList(currentPage);
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
		currentPage = 1;
		selectList(currentPage);
	});

	//정렬 선택 이벤트
	$('.order').on('change', function() {
		order = $(this).val();
		hasMoreData = true;
		$('#output').empty();
		currentPage = 1;
		selectList(currentPage);
	});

	/*---------------------
	 * 챌린지 목록
	 *---------------------*/
	function selectList(page) {
		if (loading || !hasMoreData) return;

		loading = true;

		$.ajax({
			url: 'addlist',
			type: 'get',
			data: {
				pageNum: page,
				rowCount: rowCount,
				chal_type: chal_type,
				freqOrder: freqOrder,
				keyword: keyword,
				order: order,
				chal_sdate: 'list'
			},
			dataType: 'json',
			success: function(param) {

				if (page * rowCount >= param.count) {
					hasMoreData = false;
					const scrollTarget = document.querySelector('#scroll-target');					
					if (scrollTarget) {
						observer.unobserve(scrollTarget);
					}
				}
				//챌린지 목록 작업
				let output = '';
				$(param.list).each(function(index, item) {
					console.log(index+' : '+((page -1) * rowCount+index));
					console.log(index+' : '+param.count);
					realIdx = (page -1) * rowCount+index;
					let sdate = new Date(item.chal_sdate);
					let now = new Date();
					now.setHours(0, 0, 0, 0); // 시간 부분을 0으로 설정
					sdate.setHours(0, 0, 0, 0);
					if (sdate.getTime() >= now.getTime()) {
						output = '<span class="chal_listElement">';
						output += '<a href="detail?chal_num=' + item.chal_num + '">';
						output += '<ul class="listElement_content">';
						output += '<li>';
						if (item.chal_photo) {
							output += '<img src="' + pageContext + '/upload/' + item.chal_photo + '" width="100" height="40">'; //챌린지 썸네일
						} else {
							output += '<img src="' + pageContext + '/images/챌린지_기본이미지.jpg" width="100" height="40">'; //챌린지 썸네일 - 기본 이미지
						}
						output += '</li>';
						output += '<li>';
						output += '<span>' + item.chal_title + '</span>';
						output += '</li>';
						output += '<li>';
						if (item.mem_photo) {
							output += '<img src="' + pageContext + '/upload/' + item.mem_photo + '" width="20" height="20">'; //
						} else {
							output += '<img src="' + pageContext + '/images/basicProfile.png" width="20" height="20">'; //챌린지 썸네일 - 기본 이미지
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
				// 스크롤 타겟 추가 및 중복 방지
				const scrollTarget = document.querySelector('#scroll-target');
				if (scrollTarget) {
					observer.unobserve(scrollTarget);
					
					scrollTarget.remove();
					
					const dif = page * rowCount - param.count;									
					
					console.log(realIdx);
					console.log(param.count);
					console.log(realIdx == param.count-1);
					
					if (realIdx == param.count-1) {
						const target = document.createElement('div');
						target.id = 'scroll-target';
						document.getElementById('output').appendChild(target);
						if(dif == 1){							
							target.style.width = '170px';
							console.log(target);
						}
						else if(dif == 2){
							target.style.width = '390px';
							return;
						}						
					}
				}
				const target = document.createElement('div');
				target.id = 'scroll-target';
				document.getElementById('output').appendChild(target);
				observer.observe(target);

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

	/*---------------------
	 * 초기 데이터 호출
	 *---------------------*/
	selectList(1);

});