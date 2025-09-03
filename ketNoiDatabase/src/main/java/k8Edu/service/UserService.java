package k8Edu.service;

import k8Edu.model.UserModel;

public interface UserService {
    // Các phương thức hiện có...
    UserModel login(String username, String password);
    UserModel get(String username);
    boolean insert(UserModel user);
    boolean register(String username, String password, String email, String fullname, String phone);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    // ...

    // --- Các phương thức mới/cập nhật cho Quên Mật khẩu (Không gửi email) ---
    // Phương thức để tìm người dùng bằng email
    UserModel getUserByEmail(String email);

    // Phương thức để đặt lại mật khẩu (không cần token, chỉ cần userId)
    boolean resetPasswordWithoutToken(int userId, String newPassword);

    String hashPassword(String password); // Phương thức để băm mật khẩu (RẤT QUAN TRỌNG CHO BẢO MẬT!)
}