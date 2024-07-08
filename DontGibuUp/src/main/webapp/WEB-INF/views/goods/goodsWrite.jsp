<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 게시판 글쓰기 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- include ckeditor js -->
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<div class="page-main">
	<h2>글쓰기</h2>
	<form:form action="write" id="Goods_register"
		enctype="multipart/form-data"
							modelAttribute="GoodsVO">
	<ul>
		<li>
		 	<form:label path="dcate_num">분류</form:label>
		 	<form:select path="dcate_num">
		 		<option disabled="disabled" selected>선택하세요</option>
		 		<form:option value="1">자바</form:option>
		 		<form:option value="2">데이터 베이스</form:option>
		 		<form:option value="3">자바스크립트</form:option>
		 		<form:option value="4">기타</form:option>
		 	</form:select>
		 	<form:errors path="dcate_num" cssClass="error-color"/>
		</li>
		<li>
		 	
		 	<form:input path="item_name" placeholder="상품명을 입력하세요"/>
		 	<form:errors path="item_name" cssClass="error-color"/>
		</li>
		<li>
		 	<form:textarea path="item_detail"/>
		 	<form:errors path="item_detail" cssClass="error-color"/>
		 	<script>
				 function MyCustomUploadAdapterPlugin(editor) {
					    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
					        return new UploadAdapter(loader);
					    }
					}
				 
				 ClassicEditor
		            .create( document.querySelector( '#item_detail' ),{
		            	extraPlugins: [MyCustomUploadAdapterPlugin]
		            })
		            .then( editor => {
						window.editor = editor;
					} )
		            .catch( error => {
		                console.error( error );
		            } );
			    </script> 
		</li>
		<li>
		 	<form:label path="item_photo">파일업로드</form:label>
		 	<input type="file" name="item_photo" id="item_photo">
		</li>
	</ul>						
	<div class="align-center">
		<form:button class="default-btn">등록</form:button>
		<input type="button" value="목록"
				class="default-btn"
				onclick="location.href='list'">
	</div>					
	</form:form>

<!-- 게시판 글쓰기 끝 -->
</div>
