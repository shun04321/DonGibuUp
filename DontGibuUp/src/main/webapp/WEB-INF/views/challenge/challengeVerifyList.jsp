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
	let user_joi_num = ${chal_joi_num};
	let rowCount = 1;
	let pageSize = 10;
	var currentPage;
</script>
<h2>챌린지 인증내역</h2>
<div class="challenge-summary">
	<div class="challenge-info">
		<img src="<c:url value='/images/${challenge.chal_photo}'/>"
			class="challenge-thumbnail responsive-image" alt="챌린지 썸네일">
		<div class="challenge-info">
			<div class="details">
				<h3>${challenge.chal_title}</h3>
				<button class="detail-button"
					onclick="location.href='${pageContext.request.contextPath}/challenge/detail?chal_num=${challenge.chal_num}'">상세보기</button>
			</div>
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
			<div id="verify_content"></div>						
		</div>
</div>
<script type="text/javascript">	
	//초기 데이터(나의 인증 현황) 호출
	getVerify(1);
	
	//나의 인증 현황 클릭 이벤트
 	$('#verify_my_states').on('click',function(){
 		$('#verify_content').empty();
 		chal_joi_num = user_joi_num;
		getVerify(1);
	});
	
 	//인증 현황 페이지 버튼 클릭 이벤트(본인)
	$(document).on('click','.pageBtn.verifytrue',function(){
		$('#verify_content').empty();
		//페이지 번호를 읽어들임
		currentPage = $(this).attr('data-page');
		//목록 호출
		getVerify(currentPage);
	});
	
 	//인증 현황 페이지 버튼 클릭 이벤트(타인)
	$(document).on('click','.pageBtn.verifyfalse',function(){
		$('#verify_content').empty();
		//페이지 번호를 읽어들임
		currentPage = $(this).attr('data-page');
		//목록 호출
		getVerify(currentPage);
	});
 	
 	//참가자 목록 클릭 이벤트
	$('#join_member_list').on('click',function(){
		$('#verify_content').empty();
		chal_joi_num = user_joi_num;
		getItems(1);
	});
	
 	//참가자 목록 페이지 버튼 클릭 이벤트
	$(document).on('click','.pageBtn.join',function(){
		$('#verify_content').empty();
		//페이지 번호를 읽어들임
		currentPage = $(this).attr('data-page');
		//목록 호출
		getItems(currentPage);
	});
 	
	//타인의 인증 현황 클릭 이벤트
 	$(document).on('click','.each_verify_list',function(e){
 		e.preventDefault();
 		$('#verify_content').empty();
		chal_joi_num = $(this).attr('href').split('chal_joi_num=')[1];
 		getVerify(1);
 	});
	
	//타인의 인증 현황 돌아가기 이벤트
	$(document).on('click','.others_verify_list',function(e){
		e.preventDefault();
		$('#verify_content').empty();
		chal_joi_num = user_joi_num;
		getItems(1);
	});
		
 	//참가자 목록을 불러오는 메서드
	function getItems(currentPage){
		$.ajax({
			url:'joinMemberList',
			type:'get',
			data:{chal_num:chal_num,chal_joi_num:chal_joi_num,pageNum:currentPage,rowCount:rowCount},
			dataType:'json',
			success:function(param){
				let output = '';
				output += '<div class="memberList">';
				$(param.list).each(function(index,item){
					output += '<div class="joinMem_container">';
					if (item.mem_photo) {
						output += '<img class="joinMem responsive-image" src="' + contextPath + '/upload/' + item.mem_photo + '" width="40" height="40">'; //회원 프로필
					} else {
						output += '<img class="joinMem responsive-image" src="' + contextPath + '/images/basicProfile.png" width="40" height="40">'; //회원 프로필 기본 이미지
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
				$('#verify_content').append(output);				
				$('#verify_content').append(setPage(param.count,'join'));												
			},
			error:function(){
				alert('네트워크 오류');
			}
		});	
	}
	
 	//참가자 인증 현황을 불러오는 메서드
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
				if(!param.isUser){
					output += `<div class="memberInfo">`;
					if (param.member.mem_photo) {
						output += '<img class="joinMem responsive-image" src="' + contextPath + '/upload/' + param.member.mem_photo + '" width="40" height="40">'; //회원 프로필
					} else {
						output += '<img class="joinMem responsive-image" src="' + contextPath + '/images/basicProfile.png" width="40" height="40">'; //회원 프로필 기본 이미지
					}
					output += `<span class="joinMem">\${param.member.mem_nick}</span>
										 <a href="joinMemberList" class="others_verify_list"> > </a>
										 </div>
					`;							
				}
				if(param.count == 0){
					output += '<div>표시할 정보가 없습니다.</div>';
				}else{					
					$(param.list).each(function(index,item){
						let reg_date = new Date(item.chal_reg_date);
						reg_date.setHours(0,0,0,0);						
						output += '<div class="challenge-verify-card">';
						output += '<img src="'+contextPath+'/upload/'+item.chal_ver_photo+'" width="100" height="50" class="responsive-image">';	
						output += '<div class="content">';
						output += '<div class="date-status">';
						output += '<span class="date">'+item.chal_reg_date+'</span>';	
						if(item.chal_ver_status == 0){
							output += `<span class="status success">성공</span>`;
						}else if(item.chal_ver_status == 1){
							output += `<span class="status failure">실패</span>`;
						}
						if(item.chal_content){
							output += '<div id="content-'+item.chal_ver_num+'" class="comment">'+item.chal_content+'</div>';
						}else{
							output += '<div id="content-'+item.chal_ver_num+'" class="comment"></div>';
						}	
						output += '</div>';
						output += '</div>';
						if(!param.isUser){
							output += '<button type="button">제보</button>';
						}else{
							//수정 폼
							output += '<div id="edit-form-'+item.chal_ver_num+'" class="edit-form" style="display: none;">';
							output += '<textarea id="textarea-'+item.chal_ver_num+'">'+item.chal_content+'</textarea>';
							output += '</div>';
							//수정/삭제 버튼 생성							
							if(reg_date.getTime() == now.getTime()){								
								output += `<button id="edit-button-\${item.chal_ver_num}"
									onclick="toggleEditSave(\${item.chal_ver_num})">수정</button>`;	
								output += `<button onclick="deleteVerify(\${item.chal_ver_num})">삭제</button>`;
							}														
						}						
						output += '</div>';								
					});					
				}
				$('#verify_content').html(output);
				$('#verify_content').append(setPage(param.count,'verify'+param.isUser));
			},
			error:function(){
				alert('네트워크 오류');
			}
		});
	}
 	//페이징 처리를 하는 메서드
	function setPage(totalItem,method){
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
			pageInfo += '<span class="pageBtn '+method+'" data-page='+(startPage-1)+'>[이전]</span>';
		}

		for(var i=startPage;i<=endPage;i++){
			pageInfo += '<span class="pageBtn '+method+'" data-page='+i+'>'+i+'</span>';
		}

		if(endPage < totalPage){
			pageInfo += '<span class="pageBtn '+method+'" data-page='+(startPage+pageSize)+'>[다음]</span>';
		}

		return pageInfo;
	}

</script>