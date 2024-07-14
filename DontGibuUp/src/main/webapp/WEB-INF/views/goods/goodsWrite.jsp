<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 게시판 글쓰기 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- include ckeditor js -->
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<div class="page-main">
	<h2>글쓰기</h2>
	<form:form action="write" id="goods_register"
		enctype="multipart/form-data"
							modelAttribute="goodsVO">
	<ul>
		<li>
		 	<form:label path="dcate_num">분류</form:label>
		 	<form:select path="dcate_num">
		 		<option disabled="disabled" selected>선택하세요</option>
		 		<form:option value="1">노약자</form:option>
		 		<form:option value="2">청소년</form:option>
		 		<form:option value="3">다문화</form:option>
		 		<form:option value="4">정신영기부좀요</form:option>
		 	</form:select>
		 	<form:errors path="dcate_num" cssClass="error-color"/>
		</li>
		<li>상품명<br>
		 	<form:input path="item_name"/>
		 	<form:errors path="item_name" cssClass="error-color"/>
		</li>
		<li>가격<br>
		 	<form:input path="item_price"/>
		 	<form:errors path="item_price" cssClass="error-color"/>
		</li>
		<li>수량<br>
		 	<form:input path="item_stock"/>
		 	<form:errors path="item_stock" cssClass="error-color"/>
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
		 	<input type="file" name="upload" id="upload">
		 	<form:errors path="upload" cssClass="error-color"/>
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
