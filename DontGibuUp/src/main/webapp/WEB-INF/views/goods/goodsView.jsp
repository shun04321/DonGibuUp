<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 게시판 글 상세 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/videoAdapter.js"></script>
<script src="${pageContext.request.contextPath}/js/board.fav.js"></script>
<script src="${pageContext.request.contextPath}/js/board.reply.js"></script>
<div class="page-main">
	<h2>${goods.item_name}</h2>
	<ul class="detail-info">
		<li>
			<img src="${pageContext.request.contextPath}${goods.item_photo}" width="40" height="40" class="my-photo">
		</li>
		<li>
			재고 :${goods.item_stock}<br>
			카테고리:${goods.dcate_num}<br>
			재고 :${goods.item_price}<br>
		</li>
	</ul>

		<li>첨부파일 : <a href="file?item_num=${goods.item_num}">${goods.item_photo}</a></li>

		<div class="detail-content">
			${goods.item_detail}
		</div>
		 <div>
		<%--	댓글수
			<span id="output_rcount"></span>
		</div>
		<hr size="1" width="100%">
		<div class="align-right">
			<c:if test="${!empty user && user.mem_num == board.mem_num}">
			<input type="button" value="수정" onclick="location.href='update?board_num=${board.board_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<script>
				const delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					const choice=confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('delete?item_num=${board.board_num}');
					}
				};
			</script>
			</c:if>--%>
			<input type="button" value="목록" onclick="location.href='list'">
		</div> 
		<hr size="1" width="100%">
		<%-- <!--  댓글 UI 시작 -->
		<div id="reply_div">
			<span class="re-title">댓글 달기</span>
			<form id="re_form">
				<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
				<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep_content"
				<c:if test="${empty user}">disabled="disabled"</c:if>
				><c:if test="${empty user}">로그인 해야 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user}">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송">
				</div>
				</c:if>
			</form>
		</div>
		<!-- 댓글 목록 출력 -->
		<div id="output"></div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="30" height="30">
		</div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="더보기">
		</div>
	
<!-- 게시판 글 상세 끝 --> --%>
</div>