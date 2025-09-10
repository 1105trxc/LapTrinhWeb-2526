package k8Edu.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import k8Edu.service.CategoryService;
import k8Edu.service.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/delete" })
public class CategoryDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            if (id != null && !id.isEmpty()) {
                cateService.delete(Integer.parseInt(id));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
        } catch (Exception e) {
            throw new ServletException("Lỗi khi xóa Category", e);
        }
    }
}
