package k8Edu.service;

import k8Edu.model.UserModel;
import k8Edu.dao.UserDao;
import k8Edu.dao.UserDaoImpl;

public class UserServiceImpl implements UserService {

	UserDao userDao = new UserDaoImpl();

	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.get(username);
		if (user != null && password.equals(user.getPassWord())) {
		return user;
		}
		return null;
	}

	@Override
	public UserModel get(String username) {
		return userDao.get(username);
	}

}
