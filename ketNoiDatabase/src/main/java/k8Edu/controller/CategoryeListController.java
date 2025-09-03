package k8Edu.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import k8Edu.model.Category;
import k8Edu.service.*;

	@WebServlet(urlPatterns = { "/admin/category/list" })
	public class CategoryeListController extends HttpServlet {
		CategoryService cateService = new CategoryServiceImpl();

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			List<Category> cateList = cateService.getAll();
			req.setAttribute("cateList", cateList);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/list-category.jsp");
					dispatcher.forward(req, resp);
		}
	}


