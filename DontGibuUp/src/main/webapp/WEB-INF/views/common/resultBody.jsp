<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container mt-4">
	<h2 class="mb-4">${accessTitle}</h2>
	<div class="container">
		<div class="row justify-content-left main-content-container">
			<div class="col-md-8 col-lg-6">
				<div class="mt-4">
					<div class="align-left">
						<p id="result_p">${accessMsg}</p>
						<input type="button" value="${accessBtn}" class="custom-btn btn"
							onclick="location.href='${accessUrl}'">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
