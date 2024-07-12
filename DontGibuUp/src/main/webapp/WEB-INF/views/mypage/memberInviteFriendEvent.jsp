<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<div class="page-main">
<h2>친구초대</h2>
<div>
	<h1>친구 초대하면 나도 친구도 3,000 포인트</h1>
	<p>지금 돈기부업에 친구를 초대해보세요! 초대한 회원님과 초대받은 친구에게 모두 3,000원 포인트를 드려요.</p>
</div>
<div>
	<label>내 추천인 코드</label>
	<span>${rcode}</span>
</div>
<div>
	<h4>지금 친구 초대하기</h4>
	<img src="${pageContext.request.contextPath}/images/hyperlink.png" width="30" class="clickable-image" data-link="/member/signup?rcode=${rcode}">
	<img src="${pageContext.request.contextPath}/images/talk.png" width="30" class="clickable-image" data-link="${pageContext.request.contextPath}/some/other/link">
</div>
</div>

<script>
$(document).ready(function() {
	const currentUrl = window.location.href;
	const baseUrl = currentUrl.split('/member')[0]; // '/member' 이전의 부분을 추출
    // 이미지 클릭 이벤트
    $('.clickable-image').click(function() {
        // 이미지가 가지고 있는 링크 가져오기
        let linkToCopy = $(this).data('link');
        
        // 클립보드에 복사하기
        navigator.clipboard.writeText(baseUrl + linkToCopy)
            .then(function() {
                alert('링크가 클립보드에 복사되었습니다.');
            })
            .catch(function(err) {
                alert('링크 복사 중 오류가 발생했습니다.');
            });
    });
});
</script>