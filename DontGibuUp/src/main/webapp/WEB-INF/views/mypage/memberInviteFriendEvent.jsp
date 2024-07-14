<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.2/kakao.min.js"
  integrity="sha384-TiCUE00h649CAMonG018J2ujOgDKW/kVWlChEuu4jK2vxfAAD0eZxzCKakxg55G4" crossorigin="anonymous"></script>
<div class="page-main">
	<h2>친구초대</h2>
	<div>
		<h1>친구 초대하면 나도 친구도 3,000 포인트</h1>
		<p>지금 돈기부업에 친구를 초대해보세요! 초대한 회원님과 초대받은 친구에게 모두 3,000원 포인트를 드려요.</p>
	</div>
	<div>
		<label>내 추천인 코드</label> <span>${rcode}</span>
	</div>
	<div>
		<h4>지금 친구 초대하기</h4>
		<img src="${pageContext.request.contextPath}/images/revent_banner.png" width="500"><br>
		<img src="${pageContext.request.contextPath}/images/hyperlink.png"
			width="30" id="link_sharing_btn" class="clickable-image"
			data-link="/member/signup?rcode=${rcode}"> 
		<img src="${pageContext.request.contextPath}/images/talk.png" width="30"
			id="kakaotalk_sharing_btn" class="clickable-image"
			data-link="${pageContext.request.contextPath}/some/other/link">
	</div>
</div>

<script>
$(document).ready(function() {
	const currentUrl = window.location.href;
	const baseUrl = currentUrl.split('/member')[0]; // '/member' 이전의 부분을 추출
	console.log(baseUrl);
 
	// Kakao SDK 초기화
    Kakao.init('ef6bf16762f39e5d13695f11fefd4e44'); // 사용하려는 앱의 JavaScript 키 입력

    // 클립보드 복사 이벤트
    $('#link_sharing_btn').click(function() {
        let linkToCopy = $(this).data('link');
        navigator.clipboard.writeText(baseUrl + linkToCopy)
            .then(function() {
                alert('링크가 클립보드에 복사되었습니다.');
            })
            .catch(function(err) {
                alert('링크 복사 중 오류가 발생했습니다.');
            });
    });
	
	$('#kakaotalk_sharing_btn').click(function() {
		shareMessage();
	});
	
	
	// 카카오톡 메시지 보내기
	function shareMessage() {
	    Kakao.Share.sendDefault({
	        objectType: 'feed',
	        content: {
	          title: '${user.mem_nick}님이 돈기부업에 초대하셨어요',
	          description: '도전과 기부를 동시에! 돈기부업에서 자기계발 챌린지로 기부에 동참하세요',
	          imageUrl: 'https://ifh.cc/g/OpBxQn.png',
	          link:  {
	            mobileWebUrl: baseUrl + '/member/signup?rcode=${rcode}',
	            webUrl: baseUrl + '/member/signup?rcode=${rcode}',
	          }
	        },
	        buttons: [
	          {
	            title: '지금 3,000 포인트 받기',
	            link: {
	              mobileWebUrl: baseUrl + '/member/signup?rcode=${rcode}',
	              webUrl: baseUrl + '/member/signup?rcode=${rcode}',
	            }
	          }  
	        ]
	      });
	}
});
</script>
