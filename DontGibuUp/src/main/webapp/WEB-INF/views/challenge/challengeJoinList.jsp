<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	let contextPath = "${pageContext.request.contextPath}";
	let status = "${status}"; 
</script>
<div id="challengeContainer">
	<div class="month-section">
		<h2 class="align-left">
			<span id="prevMonth" style="cursor: pointer;">&lt;</span> <span
				id="currentMonth" data-month="${currentMonth}">${currentMonth.replace("-", "년 ")}월</span>
			<span id="nextMonth" style="cursor: pointer;">&gt;</span>
		</h2>
		<div class="challenge-list">
			<c:forEach var="entry" items="${challengesByMonth}">
				<c:forEach var="challengeData" items="${entry.value}">
					<c:set var="challengeJoin" value="${challengeData.challengeJoin}" />
					<div class="challenge-card">						
						<div class="card-header">
							<h3>${challengeJoin.chal_title}</h3>
							<c:if test="${challengeData.achieveRate >= 90}">
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
									fill="currentColor" class="bi bi-patch-check"
									viewBox="0 0 16 16">
								  <path fill-rule="evenodd"
										d="M10.354 6.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7 8.793l2.646-2.647a.5.5 0 0 1 .708 0" />
								  <path
										d="m10.273 2.513-.921-.944.715-.698.622.637.89-.011a2.89 2.89 0 0 1 2.924 2.924l-.01.89.636.622a2.89 2.89 0 0 1 0 4.134l-.637.622.011.89a2.89 2.89 0 0 1-2.924 2.924l-.89-.01-.622.636a2.89 2.89 0 0 1-4.134 0l-.622-.637-.89.011a2.89 2.89 0 0 1-2.924-2.924l.01-.89-.636-.622a2.89 2.89 0 0 1 0-4.134l.637-.622-.011-.89a2.89 2.89 0 0 1 2.924-2.924l.89.01.622-.636a2.89 2.89 0 0 1 4.134 0l-.715.698a1.89 1.89 0 0 0-2.704 0l-.92.944-1.32-.016a1.89 1.89 0 0 0-1.911 1.912l.016 1.318-.944.921a1.89 1.89 0 0 0 0 2.704l.944.92-.016 1.32a1.89 1.89 0 0 0 1.912 1.911l1.318-.016.921.944a1.89 1.89 0 0 0 2.704 0l.92-.944 1.32.016a1.89 1.89 0 0 0 1.911-1.912l-.016-1.318.944-.921a1.89 1.89 0 0 0 0-2.704l-.944-.92.016-1.32a1.89 1.89 0 0 0-1.912-1.911z" />
								</svg>
								<!-- <i class="bi bi-patch-check-fill"></i> UI 적용후 이걸로 전환-->
							</c:if>
							<div class="view-detail">
								<c:choose>
									<c:when test="${status == 'pre'}">
										<a href="#"
											onclick="deleteChallenge(${challengeJoin.chal_joi_num})">챌린지취소</a>
									</c:when>
									<c:when test="${status == 'on'}">
										<a href="${pageContext.request.contextPath}/challenge/verify/list?chal_num=${challengeJoin.chal_num}&chal_joi_num=${challengeJoin.chal_joi_num}&status=${status}">인증내역</a>
										<c:if test="${challengeData.total_count > 1}">
											<a href="#" class="chal_talk" data-chal-num="${challengeJoin.chal_num}"
											data-chal-joi-num="${challengeJoin.chal_joi_num}" data-status="${status}">단체톡방</a>
										</c:if>										
									</c:when>
									<c:when test="${status == 'post'}">
										<a href="${pageContext.request.contextPath}/challenge/verify/list?chal_num=${challengeJoin.chal_num}&chal_joi_num=${challengeJoin.chal_joi_num}&status=${status}">인증내역</a>
										<c:if test="${challengeData.hasReview}">
							                <a href="${pageContext.request.contextPath}/challenge/review/list?chal_num=${challengeJoin.chal_num}">후기보기</a>
							            </c:if>
							            <c:if test="${!challengeData.hasReview}">
							                <a href="${pageContext.request.contextPath}/challenge/review/write?chal_num=${challengeJoin.chal_num}">후기작성</a>
							            </c:if>
									</c:when>
								</c:choose>
							</div>
						</div>
						<p>${challengeJoin.chal_sdate}- ${challengeJoin.chal_edate}</p>
						<div>
							<b> <span>달성률</span> <span>${challengeData.achieveRate}%</span>
								<div class="progress progress-md">
									<div class="progress-bar bg-success" role="progressbar"style="width: ${challengeData.achieveRate}%" 
									aria-valuenow="100-${challengeData.achieveRate}" aria-valuemin="0" aria-valuemax="100"></div>
								</div>
							</b>
						</div>
						<div class="details">
							<div>
								<span>참여금</span> <span>${challengeData.formattedFee}원</span>
							</div>
							<div>
								<span>환급포인트</span> <span>${challengeData.returnPoint}p</span>
							</div>
							<div>
								<span>기부금</span> <span>${challengeData.donaAmount}원</span>
							</div>
							<div>
								<span>기부처</span> <span>${challengeJoin.dcate_charity}</span>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:forEach>
		</div>
		<div class="align-center">${page}</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/challenge/challenge.join.list.js"></script>