package k8Edu.controller;

import jakarta.servlet.RequestDispatcher;
import k8Edu.service.*;
import k8Edu.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.eclipse.tags.shaded.org.apache.bcel.classfile.Constant;

/**
 * Servlet implementation class LoginController
 */

@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("account") != null) {
            UserModel user = (UserModel) session.getAttribute("account");
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<h3>Bạn đã đăng nhập với tài khoản: " + user.getUserName() + "</h3>");
            return;
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    session = req.getSession(true);
                    session.setAttribute("username", cookie.getValue());
                    resp.setContentType("text/html;charset=UTF-8");
                    resp.getWriter().println("<h3>Bạn đã đăng nhập (cookie) với tài khoản: " + cookie.getValue() + "</h3>");
                    return;
                }
            }
        }

        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.isEmpty() || password.isEmpty()) {
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        UserService service = new UserServiceImpl();
        UserModel user = service.login(username, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("account", user);

            // Đăng nhập thành công → chuyển sang trang khác (ví dụ home.jsp)
            resp.sendRedirect(req.getContextPath() + "/home.jsp");
        } else {
            // Sai tài khoản/mật khẩu → quay về login.jsp và hiển thị alert
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

}


