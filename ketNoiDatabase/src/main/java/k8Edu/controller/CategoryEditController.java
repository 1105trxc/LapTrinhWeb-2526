package k8Edu.controller;

import java.io.File; // Import cho lớp File
import java.io.IOException; // Import cho IOException
import java.util.List; // Import cho kiểu List
import jakarta.servlet.http.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import k8Edu.model.Category;
import k8Edu.service.CategoryService;
import k8Edu.service.CategoryServiceImpl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
// import org.apache.commons.fileupload.servlet.ServletRequestContext; // Có thể không cần
// import org.apache.commons.fileupload.FileUploadBase; // Chỉ cần nếu bạn dùng các hằng số của nó

@WebServlet(urlPatterns = { "/admin/category/edit" })
public class CategoryEditController extends HttpServlet {
	private static final long serialVersionUID = 1L; // Thêm serialVersionUID
    private CategoryService cateService = new CategoryServiceImpl(); // Khởi tạo Service

    // >> ĐỊNH NGHĨA HẰNG SỐ DIR CỦA BẠN <<
    private static final String UPLOAD_DIR = "E:/upload"; // <<-- Cần thay đổi thành đường dẫn thực tế và cấu hình động

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
		ServletException, IOException {
		String id = req.getParameter("id");
		Category category = cateService.get(Integer.parseInt(id));
		req.setAttribute("category", category);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/edit-category.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Category category = new Category();
		// Kiểm tra xem request có phải là multipart/form-data không
        if (!ServletFileUpload.isMultipartContent(req)) {
            throw new ServletException("Content type is not multipart/form-data");
        }

		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// diskFileItemFactory.setSizeThreshold(MEMORY_THRESHOLD); // Thiết lập ngưỡng bộ nhớ (tùy chọn)
        // diskFileItemFactory.setRepository(new File(System.getProperty("java.io.tmpdir"))); // Thiết lập thư mục tạm (tùy chọn)

		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		// servletFileUpload.setFileSizeMax(MAX_FILE_SIZE); // Kích thước tối đa cho mỗi file (tùy chọn)
        // servletFileUpload.setSizeMax(MAX_REQUEST_SIZE); // Kích thước tối đa cho toàn bộ request (tùy chọn)

		servletFileUpload.setHeaderEncoding("UTF-8");

		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8"); // Đảm bảo encoding cho request

			List<FileItem> items = servletFileUpload.parseRequest(req); // Parsing request
            System.out.println("Items received: " + items.size()); // Debugging

			for (FileItem item : items) {
                // Xử lý các trường form thông thường
				if (item.isFormField()) {
					if (item.getFieldName().equals("id")) {
						category.setId(Integer.parseInt(item.getString()));
					} else if (item.getFieldName().equals("name")) {
						category.setName(item.getString("UTF-8")); // Lấy giá trị chuỗi với UTF-8
					}
				} else { // Xử lý file upload
					if (item.getFieldName().equals("icon")) {
						if (item.getSize() > 0) { // Nếu có file được upload
							String originalFileName = item.getName();
                            // Lấy tên file gốc (ví dụ: "myimage.png")
                            // Một số trình duyệt có thể gửi đường dẫn đầy đủ, cần trích xuất tên file
                            originalFileName = new File(originalFileName).getName();

							int index = originalFileName.lastIndexOf(".");
							String ext = "";
							if (index > 0) { // Kiểm tra để tránh lỗi nếu không có phần mở rộng
								ext = originalFileName.substring(index + 1);
							}
							String fileName = System.currentTimeMillis() + "." + ext; // Tạo tên file duy nhất

                            // Tạo thư mục "category" nếu chưa tồn tại
                            File uploadFolder = new File(UPLOAD_DIR + "/category");
                            if (!uploadFolder.exists()) {
                                uploadFolder.mkdirs(); // Tạo các thư mục cần thiết
                            }

							File file = new File(uploadFolder.getAbsolutePath() + "/" + fileName);
                            System.out.println("Saving file to: " + file.getAbsolutePath()); // Debugging
							item.write(file); // Ghi file vào ổ đĩa
							category.setIcon("category/"+fileName); // Lưu đường dẫn tương đối vào database
						} else {
							// Nếu không có file mới được upload, giữ lại icon cũ hoặc set null tùy logic
                            // Để giữ lại icon cũ, bạn cần fetch category cũ trước đó
                            // Category existingCategory = cateService.get(category.getId());
                            // category.setIcon(existingCategory.getIcon());
							category.setIcon(null); // Hoặc set null nếu bạn muốn xóa icon nếu không có file mới
						}
					}
				}
            }
            // Đảm bảo id và name đã được set trước khi gọi edit
            // Nếu không có id hoặc name trong form, bạn cần xử lý lỗi hoặc lấy từ DB
            if(category.getId() == 0) { // Ví dụ: nếu ID không được gửi từ form
                 throw new IllegalArgumentException("Category ID is missing for editing.");
            }

			cateService.edit(category);
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
		} catch (FileUploadException e) {
			e.printStackTrace();
            req.setAttribute("alert", "Lỗi tải lên file: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
		} catch (Exception e) { // Bắt Exception chung để xử lý các lỗi khác (IO, NumberFormat, v.v.)
			e.printStackTrace();
            req.setAttribute("alert", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
        }
	}
}