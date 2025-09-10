<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
      crossorigin="anonymous">
</head>
<body class="container mt-5">

    <div class="card shadow p-4 col-md-6 offset-md-3">
        <h2 class="text-center mb-4">Xin chào!</h2>

        <p class="text-center">
            Chào mừng bạn đã đăng nhập vào hệ thống 🎉
        </p>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/logout"
               class="btn btn-danger btn-lg">
                Đăng xuất
            </a>
        </div>
    </div>

</body>
</html>
