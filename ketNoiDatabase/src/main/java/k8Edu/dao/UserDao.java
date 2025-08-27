package k8Edu.dao;

import k8Edu.model.UserModel;

public interface UserDao {
	UserModel get(String username);
	
}
