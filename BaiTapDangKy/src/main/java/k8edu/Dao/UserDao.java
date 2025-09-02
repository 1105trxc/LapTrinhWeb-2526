package k8edu.Dao;

import k8edu.Model.User;

public interface UserDao {
	void insert(User user);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
}
