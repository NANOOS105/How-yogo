<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>닉네임 수정</title>
<style>
    /* 팝업 창 스타일 */
    .popup {
        position: fixed;
        width: 300px;
        height: 200px;
        background-color: #fff;
        border: 1px solid #ccc;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        padding: 20px;
        z-index: 9999;
    }
</style>
</head>
<body>
<%
    // 사용자 아이디를 파라미터로 받아옴
    String memberId = request.getParameter("memberId");
%>
<div class="popup">
    <h2>닉네임 수정</h2>
    <form action="AdminUpdateUser" method="get">
        <input type="hidden" name="memberId" value="<%= memberId %>">
        <label for="nickname">새로운 닉네임:</label>
        <input type="text" id="nickname" name="nickname" required><br>
        <input type="submit" value="수정">
    </form>
</div>
</body>
</html>