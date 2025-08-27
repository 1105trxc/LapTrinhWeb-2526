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
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String COOKIE_REMEMBER = "username";

    public LoginController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        // Check cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    String username = cookie.getValue();
                    UserService service = new UserServiceImpl();
                    UserModel user = service.get(username);
                    if (user != null) {
                        session = req.getSession(true);
                        session.setAttribute("account", user);
                        resp.sendRedirect(req.getContextPath() + "/waiting");
                        return;
                    }
                }
            }
        }

        // Nếu chưa login thì forward về trang login.jsp
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String alertMsg = "";

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        UserService service = new UserServiceImpl();
        UserModel user = service.login(username, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("account", user);

            // Lưu cookie remember-me
            saveRememberMe(resp, username);

            resp.sendRedirect(req.getContextPath() + "/waiting");
        } else {
            alertMsg = "Tài khoản hoặc mật khẩu không đúng";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    private void saveRememberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(COOKIE_REMEMBER, username);
        cookie.setMaxAge(30 * 60); // 30 phút
        response.addCookie(cookie);
    }
}


