<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/goods/update" method="post" enctype="multipart/form-data">
    <input type="hidden" name="item_num" value="${goodsVO.item_num}">
    상품명: <input type="text" name="item_name" value="${goodsVO.item_name}"><br>
    가격: <input type="text" name="item_price" value="${goodsVO.item_price}"><br>
    수량: <input type="text" name="item_stock" value="${goodsVO.item_stock}"><br>
    사진: <input type="file" name="upload"><br>
    <button type="submit">수정</button>
</form>
</body>
</html>