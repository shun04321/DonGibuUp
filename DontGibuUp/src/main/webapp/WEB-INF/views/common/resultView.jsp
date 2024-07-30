<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${accessTitle}</title>
<style>
    .centered-content {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        text-align: center;
        animation: fadeIn 2s ease-in-out;
        font-size: 5vw; /* Viewport width의 5% */
        color: #333;
        height: 100vh; /* Viewport height */
        white-space: nowrap; /* 줄바꿈 방지 */
        font-family: '맑은 고딕', sans-serif;
    }

    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    input[type="button"] {
        margin-top: 20px;
        padding: 10px 20px;
        font-size: 2vw; /* Viewport width의 2% */
        color: #fff;
        background-color: #007BFF;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    input[type="button"]:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>
<div class="centered-content fade-in">
    ${accessMsg}
    <p>
    <input type="button" value="${accessBtn}" onclick="location.href='${accessUrl}'">
</div>
</body>
</html>
