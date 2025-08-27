package k8Edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import k8Edu.model.UserModel;
import khoatrac.config.DBconnect;
import k8Edu.dao.UserDao;

public class UserDaoImpl implements UserDao {
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public UserModel get(String username) {
	String sql = "SELECT * FROM [User] WHERE username = ? ";
	try {
	conn = new DBconnect().getConnectionW();
	ps = conn.prepareStatement(sql);
	ps.setString(1, username);
	rs = ps.executeQuery();
	while (rs.next()) {
	UserModel user = new UserModel();
	user.setId(rs.getInt("id"));
	user.setEmail(rs.getString("email"));
	user.setUserName(rs.getString("username"));
	user.setFullName(rs.getString("fullname"));
	user.setPassWord(rs.getString("password"));
	user.setAvatar(rs.getString("avatar"));
	user.setRoleid(Integer.parseInt(rs.getString("roleid")));
	user.setPhone(rs.getString("phone"));
	return user; }
	} catch (Exception e) {e.printStackTrace(); }
	return null;
	}

}
