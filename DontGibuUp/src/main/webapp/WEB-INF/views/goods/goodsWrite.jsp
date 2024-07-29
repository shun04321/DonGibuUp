<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 게시판 글쓰기 시작 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<!-- CSS FILES -->
    <link href="${pageContext.request.contextPath}/t1/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/t1/css/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/t1/css/templatemo-kind-heart-charity.css" rel="stylesheet">
     <section class="cta-section section-padding section-bg">
    <div class="container">
        <div class="row justify-content-center align-items-center">
            <div class="col-lg-5 col-12 ms-auto">
                <h2 class="mb-0">
                    Make an impact. <br> Save lives.
                </h2>
            </div>
            <div class="col-lg-5 col-12">
                <a href="#" class="me-4">Make a donation</a> <a href="#section_4" class="custom-btn btn smoothscroll">Become a volunteer</a>
            </div>
        </div>
    </div>
</section>
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
		 		<form:option value="1">독거노인 종합 지원센터</form:option>
		 		<form:option value="2">안무서운회사</form:option>
		 		<form:option value="2">동물권행동 카라</form:option>
		 		<form:option value="2">희망 조약돌</form:option>
		 		<form:option value="2">Save the Children</form:option>
		 		<form:option value="2">굿네이버스</form:option>
		 		<form:option value="2">서울 환경 연합</form:option>
		 		<form:option value="2">푸르메 재단</form:option>
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
		 <li>
            <form:label path="item_status">판매 상태</form:label>
            <form:select path="item_status">
                <form:option value="1">판매 중</form:option>
                <form:option value="0">미판매</form:option>
            </form:select>
            <form:errors path="item_status" cssClass="error-color"/>
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
