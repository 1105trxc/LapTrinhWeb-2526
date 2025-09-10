package k8Edu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths; // Import Paths để xử lý tên file an toàn hơn

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


@WebServlet(urlPatterns = { "/admin/category/add" })
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024,   // 2MB
        maxFileSize = 10 * 1024 * 1024,        // 10MB
        maxRequestSize = 20 * 1024 * 1024      // 20MB
)
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService cateService = new CategoryServiceImpl();

    private static final String UPLOAD_BASE_DIR = "D:/upload";
    private static final String UPLOAD_CATEGORY_DIR = UPLOAD_BASE_DIR + "/category";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/views/admin/add-category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Category category = new Category();

        try {
            System.out.println("CategoryAddController: doPost started."); // DEBUG

            // Lấy các trường form thông thường
            String name = req.getParameter("name");
            System.out.println("CategoryAddController: Category name from form = " + name); // DEBUG

            if (name == null || name.isEmpty()) {
                System.out.println("CategoryAddController: Category name is empty. Throwing IllegalArgumentException."); // DEBUG
                throw new IllegalArgumentException("Category name cannot be empty.");
            }
            category.setName(name);

            // Xử lý file upload
            Part filePart = req.getPart("icon");

            String uploadedIconPath = null; // Biến để lưu đường dẫn icon đã upload

            if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
                System.out.println("CategoryAddController: File part detected. Size = " + filePart.getSize()); // DEBUG

                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                System.out.println("CategoryAddController: Original file name = " + originalFileName); // DEBUG

                String ext = "";
                int index = originalFileName.lastIndexOf(".");
                if (index > 0) {
                    ext = originalFileName.substring(index);
                }
                String fileName = System.currentTimeMillis() + ext;

                File uploadDir = new File(UPLOAD_CATEGORY_DIR);
                if (!uploadDir.exists()) {
                    System.out.println("CategoryAddController: Upload directory " + UPLOAD_CATEGORY_DIR + " does not exist. Creating..."); // DEBUG
                    uploadDir.mkdirs();
                }

                File targetFile = new File(uploadDir, fileName);
                System.out.println("CategoryAddController: Saving file to " + targetFile.getAbsolutePath()); // DEBUG
                filePart.write(targetFile.getAbsolutePath()); // Ghi file
                uploadedIconPath = "category/" + fileName;
                System.out.println("CategoryAddController: File saved. DB path = " + uploadedIconPath); // DEBUG
            } else {
                System.out.println("CategoryAddController: No file uploaded for icon."); // DEBUG
            }
            category.setIcon(uploadedIconPath); // Gán đường dẫn icon đã upload hoặc null

            System.out.println("CategoryAddController: Calling cateService.insert() with category name='" + category.getName() + "', icon='" + category.getIcon() + "'"); // DEBUG

            // >> ĐÂY LÀ ĐIỂM QUAN TRỌNG <<
            // Nếu cateService.insert() không ném ngoại lệ khi thất bại (do DAO chỉ in lỗi),
            // bạn cần thay đổi DAO/Service để báo hiệu thất bại.
            cateService.insert(category); // Phương thức này hiện tại trả về void.
                                         // Nếu nó trả về boolean, bạn có thể kiểm tra ở đây.
                                         // VD: boolean success = cateService.insert(category);
                                         // if (!success) { throw new RuntimeException("Insert failed in DB."); }

            System.out.println("CategoryAddController: cateService.insert() finished. Attempting redirect."); // DEBUG
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            System.out.println("CategoryAddController: Redirect issued."); // DEBUG
            // Quan trọng: Thêm return để kết thúc phương thức sau khi redirect
            return;

        } catch (IllegalArgumentException e) {
            System.err.println("CategoryAddController: IllegalArgumentException caught."); // DEBUG
            e.printStackTrace();
            req.setAttribute("alert", "Lỗi dữ liệu: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
        } catch (ServletException e) {
            System.err.println("CategoryAddController: ServletException caught (Multipart config issue?)."); // DEBUG
            e.printStackTrace();
            req.setAttribute("alert", "Lỗi cấu hình Servlet/Upload: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
        } catch (IOException e) {
            System.err.println("CategoryAddController: IOException caught (File write issue?)."); // DEBUG
            e.printStackTrace();
            req.setAttribute("alert", "Lỗi I/O khi xử lý file: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
        } catch (Exception e) { // Bắt các Exception chung khác
            System.err.println("CategoryAddController: Generic Exception caught."); // DEBUG
            e.printStackTrace();
            req.setAttribute("alert", "Lỗi hệ thống không xác định: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
        }
        System.out.println("CategoryAddController: doPost finished via forward path (an error occurred)."); // DEBUG
    }
}