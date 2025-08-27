package k8Edu.service;

import k8Edu.model.UserModel;

public interface UserService {
	UserModel login(String username, String password);
	UserModel get(String username);
}
