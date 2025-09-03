<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đặt lại mật khẩu</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
    body {
        font-family: Arial, sans-serif;
        background: #f8f9fa;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .reset-box {
        background: #fff;
        padding: 20px 30px;
        border-radius: 8px;
        box-shadow: 0px 2px 8px rgba(0,0,0,0.2);
        width: 350px;
    }
    h2 {
        text-align: center;
        margin-bottom: 20px;
    }
    .input-group {
        margin-bottom: 15px;
        display: flex;
        align-items: center;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 5px 10px;
    }
    .input-group i {
        margin-right: 8px;
        color: #555;
    }
    .input-group input {
        border: none;
        outline: none;
        width: 100%;
        padding: 8px;
    }
    .btn {
        width: 100%;
        padding: 10px;
        background: #0099ff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
    }
    .btn:hover {
        background: #007acc;
    }
    .footer {
        margin-top: 15px;
        text-align: center;
    }
    .footer a {
        text-decoration: none;
        color: #0099ff;
    }
    .alert-message {
        color: red;
        text-align: center;
        margin-bottom: 10px;
    }
    .success-message {
        color: green;
        text-align: center;
        margin-bottom: 10px;
    }
</style>
</head>
<body>
    <div class="reset-box">
        <form action="resetPassword" method="post">
            <h2>Đặt lại mật khẩu</h2>

            <c:if test="${not empty alert}">
                <p class="alert-message">${alert}</p>
            </c:if>
            <c:if test="${not empty success}">
                <p class="success-message">${success}</p>
            </c:if>

            <div class="input-group">
                <i class="fa fa-lock"></i>
                <input type="password" name="newPassword" placeholder="Mật khẩu mới" required>
            </div>

            <div class="input-group">
                <i class="fa fa-lock"></i>
                <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu mới" required>
            </div>

            <button type="submit" class="btn">Đặt lại mật khẩu</button>

            <div class="footer">
                <p><a href="login.jsp">Quay lại đăng nhập</a></p>
            </div>
        </form>
    </div>
</body>
</html>