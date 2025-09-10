package k8Edu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import k8Edu.model.Category;
import k8Edu.service.CategoryService;
import k8Edu.service.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/edit" })
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024,   // 2MB
        maxFileSize = 10 * 1024 * 1024,        // 10MB
        maxRequestSize = 20 * 1024 * 1024      // 20MB
)
public class CategoryEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService cateService = new CategoryServiceImpl();

    // Thư mục upload
    private static final String UPLOAD_DIR = "E:/upload/category";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Category category = cateService.get(Integer.parseInt(id));
        req.setAttribute("category", category);

        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/views/admin/edit-category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Category category = new Category();
        category.setId(Integer.parseInt(req.getParameter("id")));
        category.setName(req.getParameter("name"));

        // Lấy file từ form (input name="icon")
        Part filePart = req.getPart("icon");
        if (filePart != null && filePart.getSize() > 0) {
            String originalFileName =
                    Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String ext = "";
            int index = originalFileName.lastIndexOf(".");
            if (index > 0) {
                ext = originalFileName.substring(index); // gồm dấu chấm
            }
            String fileName = System.currentTimeMillis() + ext;

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            filePart.write(new File(uploadDir, fileName).getAbsolutePath());

            category.setIcon("category/" + fileName);
        } else {
            category.setIcon(null); // hoặc giữ icon cũ tuỳ ý
        }

        cateService.edit(category);
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
