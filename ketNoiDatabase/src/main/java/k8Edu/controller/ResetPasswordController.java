package k8Edu.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import k8Edu.service.UserService;
import k8Edu.service.UserServiceImpl;

import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa có
        if (session == null || session.getAttribute("resetUserId") == null) {
            request.setAttribute("alert", "Phiên đặt lại mật khẩu không hợp lệ hoặc đã hết hạn. Vui lòng thử lại.");
            response.sendRedirect(request.getContextPath() + "/forgotPassword"); // Chuyển hướng về trang yêu cầu quên mật khẩu
            return;
        }

        request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("resetUserId") == null) {
            request.setAttribute("alert", "Phiên đặt lại mật khẩu không hợp lệ hoặc đã hết hạn. Vui lòng thử lại.");
            response.sendRedirect(request.getContextPath() + "/forgotPassword");
            return;
        }

        int userId = (int) session.getAttribute("resetUserId");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            request.setAttribute("alert", "Vui lòng nhập mật khẩu mới và xác nhận mật khẩu.");
            request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("alert", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
            return;
        }

        // Thêm kiểm tra độ mạnh mật khẩu nếu cần (ví dụ: độ dài tối thiểu)
        // if (newPassword.length() < 6) { request.setAttribute("alert", "Mật khẩu phải có ít nhất 6 ký tự."); ... }

        boolean success = userService.resetPasswordWithoutToken(userId, newPassword);

        if (success) {
            session.removeAttribute("resetUserId"); // Xóa userId khỏi session sau khi reset thành công
            request.setAttribute("success", "Mật khẩu của bạn đã được đặt lại thành công! Vui lòng đăng nhập với mật khẩu mới.");
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // Chuyển hướng về trang đăng nhập
        } else {
            request.setAttribute("alert", "Có lỗi xảy ra khi đặt lại mật khẩu. Vui lòng thử lại.");
            request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
        }
    }
}