<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
    crossorigin="anonymous">
<title>Login</title>
</head>
<body class="container mt-5">
    <form action="login" method="post" class="col-md-4 offset-md-4">
        <h2 class="mb-4">Tạo tài khoản mới</h2>

        <c:if test="${alert !=null}">
            <div class="alert alert-danger">${alert}</div>
        </c:if>

        <div class="form-group">
            <label>Tài khoản</label>
            <input type="text" name="username" class="form-control" placeholder="Nhập tài khoản">
        </div>

        <div class="form-group">
            <label>Mật khẩu</label>
            <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu">
        </div>

        <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
    </form>
</body>
</html>
