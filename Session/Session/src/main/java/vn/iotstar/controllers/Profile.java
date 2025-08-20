package vn.iotstar.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/profile"})
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false); // lấy session hiện tại, không tạo mới
        if (session != null && session.getAttribute("name") != null) {
            String name = (String) session.getAttribute("name");
            out.print("<h2>Xin chào, " + name + "!</h2>");
            out.print("<p>Đây là trang hồ sơ cá nhân (Profile Page).</p>");
            out.print("<a href='logout'>Đăng xuất</a>");
        } else {
            response.sendRedirect("Login.html");
        }
    }
}
