<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách Category</title>
<style>
    table {
        width: 80%;
        border-collapse: collapse;
        margin: 20px auto;
    }
    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        text-align: center;
    }
    th {
        background-color: #f2f2f2;
    }
    img {
        border: 1px solid #ddd;
        border-radius: 4px;
        padding: 3px;
    }
</style>
</head>
<body>
    <h2 style="text-align:center;">Danh sách Category</h2>
    <table>
        <thead>
            <tr>
                <th>STT</th>
                <th>Icon</th>
                <th>Tên Category</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cateList}" var="cate" varStatus="STT">
                <tr>
                    <td>${STT.index + 1}</td>
                    <c:url value="/image?fname=${cate.icon}" var="imgUrl" />
                    <td><img height="100" width="120" src="${imgUrl}" alt="icon" /></td>
                    <td>${cate.name}</td>
                    <td>
                        <a href="<c:url value='/admin/category/edit?id=${cate.id}'/>">Sửa</a> |
                        <a href="<c:url value='/admin/category/delete?id=${cate.id}'/>"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
