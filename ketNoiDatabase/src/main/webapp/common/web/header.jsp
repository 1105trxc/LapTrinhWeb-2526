<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="container-fluid">
		<a class="navbar-brand" href="#"> <i
			class="fas fa-shopping-cart me-2"></i> LoTStar
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="#">Trang chủ</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Danh mục</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#">Sản phẩm</a>
				</li>
			</ul>
			<ul class="navbar-nav d-flex">
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
			</ul>

		</div>
	</div>
</nav>
