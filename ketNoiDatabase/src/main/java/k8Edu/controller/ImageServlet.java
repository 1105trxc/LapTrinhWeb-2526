package k8Edu.controller;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "D:/upload/category";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fname = req.getParameter("fname");
        if (fname == null || fname.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name is missing");
            return;
        }

        File file = new File(UPLOAD_DIR, fname.replace("category/", ""));
        // Bỏ "category/" nếu đã thêm trong DB

        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + file.getAbsolutePath());
            return;
        }

        // Set content type
        String mime = getServletContext().getMimeType(file.getName());
        if (mime == null) {
            mime = "application/octet-stream";
        }
        resp.setContentType(mime);

        // Gửi file về client
        try (var in = new java.io.FileInputStream(file); var out = resp.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }
}

