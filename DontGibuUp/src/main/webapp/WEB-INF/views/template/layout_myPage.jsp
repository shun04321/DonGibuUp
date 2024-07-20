<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<tiles:insertAttribute name="css" ignore="true" />
</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header" />
	</div>
	<section class="news-section section-padding">
		<div class="container">
			<div class="row">
				<div class="col-lg-4 col-12 mx-auto mt-4 mt-lg-0">
					<tiles:insertAttribute name="nav" />
				</div>
				<div class="col-lg-7 col-12">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
	</section>
	<div id="main_footer" class="page-clear">
		<tiles:insertAttribute name="footer" />
	</div>
</div>
</body>
</html>