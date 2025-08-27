package k8Edu.test;

import k8Edu.dao.UserDaoImpl;
import k8Edu.model.UserModel;

public class test {
    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();

        // Gọi hàm get() để lấy user
        UserModel user = userDao.get("admin"); // thay "admin" bằng username có trong DB

        if (user != null) {
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + user.getUserName());
            System.out.println("Fullname: " + user.getFullName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone: " + user.getPhone());
        } else {
            System.out.println("User không tồn tại!");
        }
    }
}
