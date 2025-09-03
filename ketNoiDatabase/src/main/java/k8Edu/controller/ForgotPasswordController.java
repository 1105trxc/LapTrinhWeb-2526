package k8Edu.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Sử dụng HttpSession để lưu trữ userId
import k8Edu.model.UserModel;
import k8Edu.service.UserService;
import k8Edu.service.UserServiceImpl;

import java.io.IOException;

@WebServlet("/forgotPassword")
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            request.setAttribute("alert", "Vui lòng nhập email.");
            request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            return;
        }

        UserModel user = userService.getUserByEmail(email);
        if (user == null) {
            request.setAttribute("alert", "Email không tồn tại trong hệ thống.");
            request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            return;
        }

        // Nếu email tồn tại, lưu userId vào session và chuyển hướng ngay đến trang đặt lại mật khẩu
        HttpSession session = request.getSession();
        session.setAttribute("resetUserId", user.getId()); // Lưu ID người dùng vào session

        response.sendRedirect(request.getContextPath() + "/resetPassword"); // Chuyển hướng đến trang đặt lại mật khẩu
    }
}