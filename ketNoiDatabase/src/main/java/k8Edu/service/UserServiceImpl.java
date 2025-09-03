package k8Edu.service;

import k8Edu.model.UserModel;
import k8Edu.dao.UserDao;
import k8Edu.dao.UserDaoImpl;
import org.mindrot.jbcrypt.BCrypt; // Import BCrypt cho việc băm mật khẩu

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public UserModel login(String username, String password) {
        UserModel user = this.get(username);
        // QUAN TRỌNG: So sánh mật khẩu đã băm
        if (user != null && BCrypt.checkpw(password, user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public UserModel get(String username) {
        return userDao.get(username);
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if (userDao.checkExistUsername(username)) {
            return false;
        }
        if (userDao.checkExistEmail(email)) {
            return false;
        }
        if (userDao.checkExistPhone(phone)) {
            return false;
        }

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        String hashedPassword = hashPassword(password); // Băm mật khẩu trước khi lưu

        UserModel newUser = new UserModel(email, username, fullname, hashedPassword, null, 5, phone, date);
        return userDao.insert(newUser);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

    @Override
    public boolean insert(UserModel user) {
        return userDao.insert(user);
    }

    // --- Các phương thức cho Quên Mật khẩu (Không gửi email) ---

    @Override
    public UserModel getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public boolean resetPasswordWithoutToken(int userId, String newPassword) {
        String hashedPassword = hashPassword(newPassword); // Băm mật khẩu mới
        return userDao.updatePassword(userId, hashedPassword);
    }

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}