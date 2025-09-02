package k8edu.Service;

import k8edu.Model.User;
import k8edu.Dao.UserDao;
import k8edu.Dao.UserDaoImpl;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();

	@Override
	public boolean register(String username, String password, String email, String
	fullname, String phone ) {
	if (userDao.checkExistUsername(username)) {
	return false;
	}
	long millis=System.currentTimeMillis();
	java.sql.Date date=new java.sql.Date(millis);
	userDao.insert(new User(email, username, fullname,password,null,5,phone,date));
	return true;
	}

	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
		}
		public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
		}
		@Override
		public boolean checkExistPhone(String phone) {
		return userDao.checkExistPhone(phone);
		}
		@Override
		public void insert(User user) {
		userDao.insert(user);
		}

}
