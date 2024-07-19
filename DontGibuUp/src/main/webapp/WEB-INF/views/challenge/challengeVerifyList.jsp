<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/challenge/challenge.verify.js"></script>
<script>
	let contextPath = '${pageContext.request.contextPath}';
	let chal_num = ${challenge.chal_num};
	let chal_joi_num = ${chal_joi_num};
	let rowCount = 1;
	let pageSize = 10;
	var currentPage;
</script>
<h2>챌린지 인증내역</h2>
<div class="challenge-summary">
	<div class="challenge-info">
		<img src="<c:url value='/images/${challenge.chal_photo}'/>"
			class="challenge-thumbnail" alt="챌린지 썸네일">
		<div class="challenge-info">
			<div class="details">
				<h3>${challenge.chal_title}</h3>
				<button class="detail-button"
					onclick="location.href='${pageContext.request.contextPath}/challenge/detail?chal_num=${challenge.chal_num}'">상세보기</button>
			</div>
		</div>
		<div class="challenge-stats">
			<div class="challenge-stat-item">
				<span>인증 빈도</span>
				<c:if test="${chalFreq == 0}">
					<span>매일</span>
				</c:if>
				<c:if test="${chalFreq != 0}">
					<span>주 ${chalFreq}일</span>
				</c:if>
			</div>
			<div class="challenge-stat-item">
				<span>기간</span> <span>${chal_sdate} ~ ${chal_edate}</span>
			</div>
			<div class="challenge-stat-item1">
				<span>달성률</span> <span>${achievementRate}%</span>
			</div>
			<div class="challenge-stat-item2">
				<span>인증 성공</span> <span>${successCount}회</span>
			</div>
			<div class="challenge-stat-item2">
				<span>인증 실패</span> <span>${failureCount}회</span>
			</div>
			<div class="challenge-stat-item2">
				<span>남은 인증</span> <span>${remainingCount}회</span>
			</div>
			<div class="challenge-stat-item2">
				<c:choose>
					<c:when test="${status == 'post'}">
						<!-- 완료된 챌린지의 경우 버튼 숨김 -->
					</c:when>
					<c:when test="${hasTodayVerify}">
						<button class="disabled-button" disabled>오늘 인증 완료</button>
					</c:when>
					<c:when test="${hasCompletedWeeklyVerify}">
						<button class="disabled-button" disabled>이번주 인증 완료</button>
					</c:when>
					<c:otherwise>
						<button class="active-button"
							onclick="location.href='${pageContext.request.contextPath}/challenge/verify/write?chal_joi_num=${chal_joi_num}&status=${status}'">인증하기</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="challenge-verify-stats">
			<div class="verify-stats-nav">
				<span id="verify_my_states">나의 인증 현황</span> 
				<span id="join_member_list">참가자 인증 현황</span>
			</div>
			<div id="verify_content">
				<div class="challenge-verify-list">
				<c:if test="${count == 0}">
					<div>표시할 정보가 없습니다.</div>
				</c:if>
				<c:if test="${count > 0}">
				<c:forEach var="verify" items="${verifyList}">
					<div class="challenge-verify-card">
						<img src="<c:url value='/images/${verify.chal_ver_photo}'/>"
							alt="인증 사진">
						<div class="content">
							<div class="date-status">
								<span class="date">${verify.chal_reg_date}</span>
								<c:choose>
									<c:when test="${verify.chal_ver_status == 0}">
										<span class="status success">성공</span>
									</c:when>
									<c:when test="${verify.chal_ver_status == 1}">
										<span class="status failure">실패</span>
									</c:when>
								</c:choose>
							</div>
							<div id="content-${verify.chal_ver_num}" class="comment">${verify.chal_content}</div>
							<div id="edit-form-${verify.chal_ver_num}" class="edit-form" style="display: none;">
								<textarea id="textarea-${verify.chal_ver_num}">${verify.chal_content}</textarea>
							</div>
						</div>
						<c:if test="${status != 'post'}">
							<div class="buttons">
								<button id="edit-button-${verify.chal_ver_num}"
									onclick="toggleEditSave(${verify.chal_ver_num})">수정</button>
								<c:if test="${verify.chal_reg_date == today}">
									<button onclick="deleteVerify(${verify.chal_ver_num})">삭제</button>
								</c:if>
							</div>
						</c:if>
					</div>
				</c:forEach>
					<div class="align-center">${page}</div>
				</c:if>				
				</div>
				<div class="paging-btn"></div>
			</div>			
			<div id="loading" style="display:none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif">
			</div>			
		</div>
	</div>
</div>
<script type="text/javascript">	

	//나의 인증 현황 클릭 이벤트
 	$('#verify_my_states').on('click',function(e){
 		e.preventDefault();
 		$('.challenge-verify-list').hide();
 		$('#verify_content').empty();
		/* $('.memberList').empty();
		$('.paging-btn').empty();
		$('.each_verify_list').empty(); */
		getVerify(1);
	}); 

 	//참가자 인증 현황 클릭 이벤트
	$('#join_member_list').on('click',function(e){
		e.preventDefault();
		$('.challenge-verify-list').hide();
		$('#verify_content').empty();
		//$('.memberList').empty();
		getItems(1);
	});
 	
 	//타인의 인증 현황 클릭 이벤트
 	$(document).on('click','.each_verify_list',function(e){
 		e.preventDefault();
 		$('#verify_content').empty();
 		//$('.memberList').empty();
		//$('.paging-btn').empty();
		chal_joi_num = $(this).attr('href').split('chal_joi_num=')[1];
 		getVerify(1);
 	});
	
 	//참가자 인증 현황 페이지 버튼 클릭 이벤트
	$(document).on('click','.pageBtn',function(e){
		e.preventDefault();
		$('.memberList').empty();
		//페이지 번호를 읽어들임
		currentPage = $(this).attr('data-page');
		//목록 호출
		getItems(currentPage);
	});
	
 	//참가자 인증 현황 목록을 불러오는 메서드
	function getItems(currentPage){
		$.ajax({
			url:'joinMemberList',
			type:'get',
			data:{chal_num:chal_num,pageNum:currentPage,rowCount:rowCount},
			dataType:'json',
			success:function(param){
				let output = '';
				output += '<div class="memberList">';
				$(param.list).each(function(index,item){
					output += '<div class="joinMem_container">';
					if (item.mem_photo) {
						output += '<img class="joinMem" src="' + contextPath + '/upload/' + item.mem_photo + '" width="40" height="40">'; //챌린지 썸네일
					} else {
						output += '<img class="joinMem" src="' + contextPath + '/images/basicProfile.png" width="40" height="40">'; //챌린지 썸네일 - 기본 이미지
					}
					output += '<span class="joinMem">';
					output += item.mem_nick;
					output += '</span>';
					output += '<span class="joinMem arrow">';
					output += '<a href="verifyMemberList?chal_joi_num='+item.chal_joi_num+'" class="each_verify_list"> > </a>';
					output += '</span>';
					output += '</div>';
				});	
				output += '</div>';
				$('#verify_content').html(output);
				
				setPage(param.count);
			},
			error:function(){
				alert('네트워크 오류');
			}
		});	
	}
	
	function getVerify(currentPage){
		$.ajax({
			url:'verifyMemberList',
			type:'get',
			data:{chal_joi_num:chal_joi_num,pageNum:currentPage,rowCount:rowCount},
			dataType:'json',
			success:function(param){
				let output = '';
				let now = new Date();
				now.setHours(0, 0, 0, 0);
				//if(param.mem_num)
				if(param.count == 0){
					output += '<div>표시할 정보가 없습니다.</div>';
				}else{
					$(param.list).each(function(index,item){
						console.log('param.list >>'+param.count);
						let reg_date = new Date(item.chal_reg_date);
						reg_date.setHours(0,0,0,0);
						output += '<div class="challenge-verify-card">';
						output += '<img src="'+contextPath+'/upload/'+item.chal_ver_photo+'" width="100" height="50">';	
						output += '<div class="content">';
						output += '<div class="date-status">';
						output += '<span class="date">'+item.chal_reg_date+'</span>';	
						if(item.chal_ver_status == 0){
							output += `<span class="status success">성공</span>`;
						}else if(item.chal_ver_status == 1){
							output += `<span class="status failure">실패</span>`;
						}
						output += '<div id="content-'+item.chal_ver_num+'" class="comment">'+item.chal_content+'</div>'
						output += '<div id="edit-form-'+item.chal_ver_num+'" class="edit-form" style="display: none;">';
						output += '<textarea id="textarea-'+item.chal_ver_num+'">'+item.chal_content+'</textarea>';
						output += '</div>';	
						output += '<button type="button">제보</button>';
						output += '</div>';								
					});					
				}
				$('#verify_content').html(output);
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	}
 	//페이징 처리를 하는 메서드
	function setPage(totalItem){
		$('.paging-btn').empty();
		
		if(totalItem == 0){
			return;
		}
		
		let totalPage = Math.ceil(totalItem/rowCount);
		
		if(currentPage == undefined || currentPage == ''){
			currentPage = 1;
		}
		
		//현재 페이지가 전체 페이지 수보다 크면 전체 페이지로 설정
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		
		//시작 페이지와 마지막 페이지 값 구하기
		var startPage = Math.floor((currentPage-1)/pageSize)*pageSize + 1;
		var endPage = startPage + pageSize - 1;
		
		//마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if(endPage > totalPage){
			endPage = totalPage;
		}
		
		let pageInfo = '';
		
		if(startPage>pageSize){
			pageInfo += '<span class="pageBtn" data-page='+(startPage-1)+'>[이전]</span>';
		}

		for(var i=startPage;i<=endPage;i++){
			pageInfo += '<span class="pageBtn" data-page='+i+'>'+i+'</span>';
		}

		if(endPage < totalPage){
			pageInfo += '<span class="pageBtn" data-page='+(startPage+pageSize)+'>[다음]</span>';;
		}

		$('.paging-btn').html(pageInfo);
	}

</script>