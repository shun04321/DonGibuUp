<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 수정</title>
</head>
<body>
    <h2>상품 수정 페이지</h2>
    <form action="${pageContext.request.contextPath}/goods/update" method="post" enctype="multipart/form-data">
        <input type="hidden" name="item_num" value="${goodsVO.item_num}">
        <div>
            <label for="item_name">상품명:</label>
            <input type="text" id="item_name" name="item_name" value="${goodsVO.item_name}">
        </div>
        <div>
            <label for="item_price">가격:</label>
            <input type="text" id="item_price" name="item_price" value="${goodsVO.item_price}">
        </div>
        <div>
            <label for="item_stock">재고:</label>
            <input type="text" id="item_stock" name="item_stock" value="${goodsVO.item_stock}">
        </div>
        <div>
            <label for="item_photo">사진:</label>
            <input type="file" id="item_photo" name="upload">
        </div>
        <div>
            <label for="item_detail">상세 설명:</label>
            <textarea id="item_detail" name="item_detail">${goodsVO.item_detail}</textarea>
        </div>
        <div>
            <label for="dcate_num">카테고리:</label>
            <input type="text" id="dcate_num" name="dcate_num" value="${goodsVO.dcate_num}">
        </div>
        <div>
            <label for="item_status">상태:</label>
            <select id="item_status" name="item_status">
                <option value="1" ${goodsVO.item_status == 1 ? 'selected' : ''}>판매</option>
                <option value="2" ${goodsVO.item_status == 2 ? 'selected' : ''}>미판매</option>
            </select>
        </div>
        <button type="submit">수정</button>
    </form>
</body>
</html>
