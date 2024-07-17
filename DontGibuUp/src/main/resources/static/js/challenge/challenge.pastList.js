$(function() {
	let rowCount = 3;
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
				chal_sdate:'pastList'
			},
			dataType: 'json',
			success: function(param) {
				console.log(rowCount);
				console.log(param.count);
				if (page * rowCount >= param.count) {
					hasMoreData = false;
					const scrollTarget = document.querySelector('#scroll-target');
					if (scrollTarget) {
						observer.unobserve(scrollTarget);
						scrollTarget.remove();
					}
				}
				//챌린지 목록 작업
				let output = '';
				$(param.list).each(function(index, item) {

					let edate = new Date(item.chal_edate);
					let now = new Date();
					if (edate < now) {
						output = '<span class="chal_listElement">';
						output += '<a href="detail?chal_num=' + item.chal_num + '">';
						output += '<ul>';
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
						output += item.chal_sdate+'~'+item.chal_edate;
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