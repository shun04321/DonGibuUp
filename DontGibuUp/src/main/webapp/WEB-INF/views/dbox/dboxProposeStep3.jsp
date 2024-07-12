<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>   
<!-- include ckeditor js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/uploadAdapter.js"></script>
<!-- Step3 시작 -->
<div class="page-main">
	<form:form action="step3" id="step3" modelAttribute="dboxVO">	
		<form:errors element="div" cssClass="error-color"/><%-- 필드가 없는 에러메세지 --%>
		<ul>
			<%-- 기부박스 제목 --%>
			<li>
				<form:label path="dbox_title"><h3>기부박스 제목<span class="validation-check">*필수</span></h3></form:label>
				<form:input path="dbox_title" placeholder="제목을 작성해주세요"/>
				<form:errors path="dbox_title" cssClass="form-error"></form:errors>				
			</li>
			<%-- 기부박스 대표이미지 --%>
			<li>
				<form:label path="dbox_photo"><h3>기부박스 대표이미지</h3></form:label>
				<input type="file" name="dbox_photo" id="dbox_photo">
			</li>
			<%-- 기부박스 내용 작성 --%>
			<li>
				<form:label path="dbox_content"><h3>기부박스 내용 작성<span class="validation-check">*필수</span></h3></form:label>
				<form:textarea path="dbox_content"/>
				<form:errors path="dbox_content" cssClass="error-color"/>
				<script>
				 function MyCustomUploadAdapterPlugin(editor) {
					    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
					        return new UploadAdapter(loader);
					    }
					}
				 
				 ClassicEditor
		            .create( document.querySelector( '#dbox_content' ),{
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
		</ul>
		<div class="align-center">
			<form:button>다음 단계로</form:button>
		</div>
	</form:form>
</div>
<!-- Step3 끝 -->