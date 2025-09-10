<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
	crossorigin="anonymous">
<title>Đăng nhập</title>
</head>
<body class="container mt-5">
	<form action="${pageContext.request.contextPath}/login" method="post"
		class="col-md-4 offset-md-4">
		<h2 class="mb-4 text-center">Đăng nhập</h2>

		<!-- Hiển thị thông báo lỗi -->
		<c:if test="${not empty alert}">
			<div class="alert alert-danger">${alert}</div>
		</c:if>

		<div class="form-group">
			<label for="username">Tài khoản</label> <input type="text"
				id="username" name="username" class="form-control"
				placeholder="Nhập tài khoản">
		</div>

		<div class="form-group">
			<label for="password">Mật khẩu</label> <input type="password"
				id="password" name="password" class="form-control"
				placeholder="Nhập mật khẩu">
		</div>

		<button type="submit" class="btn btn-primary btn-block">Đăng
			nhập</button>

<%-- 		<!-- Link Quên mật khẩu -->
		<div class="text-center mt-3">
			<a href="${pageContext.request.contextPath}/resetPassword.jsp">Quên
				mật khẩu?</a>
		</div> --%>

		<!-- Trong file login.jsp, thêm đoạn này vào phần footer hoặc bất cứ đâu bạn thấy phù hợp -->
		<div class="footer">
			<a href="${pageContext.request.contextPath}/forgotPassword">Quên mật khẩu?</a>

			<p>
				Chưa có tài khoản? <a
					href="${pageContext.request.contextPath}/register">Đăng ký</a>
			</p>
		</div>
	</form>
</body>
</html>
