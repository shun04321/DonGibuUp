<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container">
<div class="custom-block-body">
	<!-- 제목 -->
	<span class="badge text-bg-success mb-3"><img src="${pageContext.request.contextPath}/upload/${dbox.dcate_icon}" style="height:1rem;">${dbox.dcate_name}</span>
	<h5 class="mb-3">${dbox.dbox_title}</h5>
	<!-- 팀 이름 -->
	<p>${dbox.dbox_team_name}</p>
	<!-- 목표금액, 달성률 -->
	<div class="align-items-center my-2">
		<h4 class="text-end">
			<strong>목표금액 : </strong>${dbox.dbox_goal}원
		</h4>
		<p class="text-end">
			<strong>달성률 : </strong>${dbox.dbox_goal}%
		</p>
	</div>
	<!-- 달성률 바 -->
	<div class="progress" role="progressbar" aria-label="" aria-valuenow="${dbox.dbox_goal}" aria-valuemin="0" aria-valuemax="100">
			<div class="progress-bar progress-bar-striped bg-success" style="width:${dbox.dbox_goal}%"></div>
	</div>
</div>
<!-- 기부하기 버튼 -->
<div class="text-center">
	<button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#staticBackdrop">기부하기</button>
</div>
	<!-- Modal 창 -->
	<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<form>
					<div class="modal-header">
						<h5 class="modal-title">기부하기</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<div class="modal-body">
						<div class="mb-3">
						  <label for="dbox_pay" class="form-label">기부금액 입력</label>
						  <input type="text" class="form-control" id="dbox_do_price" placeholder="기부할 금액을 입력해주세요.">
						</div>
						<div class="mb-3">
						  <label for="dbox_pay" class="form-label">포인트 입력</label>
						  <input type="text" class="form-control" id="dbox_do_point" placeholder="사용할 포인트를 입력해주세요.">
						</div>
					</div>
					<div class="modal-footer text-center">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
						<button type="submit" class="btn btn-success">기부하기</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
</div>


				