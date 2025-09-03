package k8Edu.dao;

import k8Edu.model.PasswordResetTokenModel;
import k8Edu.model.UserModel;

public interface UserDao {
	UserModel get(String username);

	// Thay đổi kiểu trả về từ void sang boolean để báo cáo trạng thái thành công/thất bại
	boolean insert(UserModel user);

	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);

	//Quen mat khau
	UserModel getUserByEmail(String email); // Lấy người dùng theo email
    boolean updatePassword(int userId, String newHashedPassword); // Cập nhật mật khẩu

}