package k8edu.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import k8edu.Model.User;
import khoatrac.config.DBconnect;

public class UserDaoImpl implements UserDao {
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public void insert(User user) {
	String sql = "INSERT INTO [User](email, username, fullname, password, avatar, roleid,phone,createddate) VALUES (?,?,?,?,?,?,?,?)";
	try {
	conn = new DBconnect().getConnectionW();
	ps = conn.prepareStatement(sql);
	ps.setString(1, user.getEmail());
	ps.setString(2, user.getUserName());
	ps.setString(3, user.getFullName());
	ps.setString(4, user.getPassWord());
	ps.setString(5, user.getAvatar());
	ps.setInt(6,user.getRoleid());
	ps.setString(7,user.getPhone());
	ps.setDate(8, user.getCreatedDate());
	ps.executeUpdate();
	} catch (Exception e) {e.printStackTrace();}
	}
	@Override
	public boolean checkExistEmail(String email) {
	boolean duplicate = false;
	String query = "select * from [user] where email = ?";
	try {
	conn = new DBconnect().getConnectionW();
	ps = conn.prepareStatement(query);
	ps.setString(1, email);
	rs = ps.executeQuery();
	if (rs.next()) {
	duplicate = true;
	}
	ps.close();
	conn.close();
	} catch (Exception ex) {}
	return duplicate;
	}

	@Override
	public boolean checkExistUsername(String username) {
	boolean duplicate = false;
	String query = "select * from [User] where username = ?";
	try {
	conn = new DBconnect().getConnectionW();
	ps = conn.prepareStatement(query);
	ps.setString(1, username);
	rs = ps.executeQuery();
	if (rs.next()) {
	duplicate = true;
	}
	ps.close();
	conn.close();
	} catch (Exception ex) {}
	return duplicate;
	}

	 @Override
	    public boolean checkExistPhone(String phone) {
	        boolean duplicate = false;
	        String query = "SELECT * FROM [User] WHERE phone = ?";
	        try {
	            conn = new DBconnect().getConnectionW();
	            ps = conn.prepareStatement(query);
	            ps.setString(1, phone);
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                duplicate = true;
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (conn != null) conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        return duplicate;
	    }

}
