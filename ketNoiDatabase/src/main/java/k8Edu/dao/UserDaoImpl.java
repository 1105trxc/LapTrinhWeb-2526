package k8Edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Import SQLException
import java.sql.Timestamp; // Import Timestamp

import k8Edu.model.PasswordResetTokenModel;
import k8Edu.model.UserModel;
import khoatrac.config.DBconnect; // Giả định đây là class kết nối DB của bạn

public class UserDaoImpl implements UserDao {
	// Không khai báo conn, ps, rs là biến thành viên của lớp
	// public Connection conn = null;
	// public PreparedStatement ps = null;
	// public ResultSet rs = null;

	@Override
	public UserModel get(String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM [User] WHERE username = ?"; // Sửa lỗi cú pháp khoảng trắng
		try {
			conn = new DBconnect().getConnectionW();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) { // Dùng if thay vì while nếu username là unique
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUserName(rs.getString("username"));
				user.setFullName(rs.getString("fullname"));
				user.setPassWord(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
				// Chú ý: roleid là int, không nên parseInt từ String nếu nó đã là int trong DB
				user.setRoleid(rs.getInt("roleid"));
				user.setPhone(rs.getString("phone"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace(); // In lỗi để debug
		} finally {
			// Đảm bảo đóng tài nguyên
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	// Thay đổi kiểu trả về từ void sang boolean
	public boolean insert(UserModel user) {
		String sql = "INSERT INTO [User](email, username, fullname, password, avatar, roleid, phone, createddate) VALUES (?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = new DBconnect().getConnectionW();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getFullName());
			// TODO: Mật khẩu nên được hash trước khi lưu vào DB
			ps.setString(4, user.getPassWord());
			ps.setString(5, user.getAvatar()); // avatar có thể là null, cần đảm bảo cột DB cho phép null
			ps.setInt(6, user.getRoleid());
			ps.setString(7, user.getPhone());
			// Đã thêm dòng này: Gắn giá trị cho tham số thứ 8 (createddate)
			ps.setDate(8, user.getCreatedDate()); // Giả định UserModel có getCreatedDate() trả về java.sql.Date

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0; // Trả về true nếu có ít nhất một hàng được chèn
		} catch (Exception e) {
			e.printStackTrace(); // In lỗi để debug
			return false; // Trả về false nếu có lỗi
		} finally {
			// Đảm bảo đóng tài nguyên
			try {
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean checkExistEmail(String email) {
		boolean duplicate = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from [User] where email = ?"; // Sửa lỗi cú pháp
		try {
			conn = new DBconnect().getConnectionW();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace(); // Không nên bỏ qua lỗi một cách im lặng
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return duplicate;
	}

	@Override
	public boolean checkExistUsername(String username) {
		boolean duplicate = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from [User] where username = ?";
		try {
			conn = new DBconnect().getConnectionW();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace(); // Không nên bỏ qua lỗi một cách im lặng
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return duplicate;
	}

	 @Override
	 public boolean checkExistPhone(String phone) {
	     boolean duplicate = false;
	     Connection conn = null;
	     PreparedStatement ps = null;
	     ResultSet rs = null;
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
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }
	     return duplicate;
	 }

	 @Override
	    public UserModel getUserByEmail(String email) { // Phương thức mới
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        String sql = "SELECT * FROM [User] WHERE email = ?";
	        try {
	            conn = new DBconnect().getConnectionW();
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, email);
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                UserModel user = new UserModel();
	                user.setId(rs.getInt("id")); // Quan trọng
	                user.setEmail(rs.getString("email"));
	                user.setUserName(rs.getString("username"));
	                user.setFullName(rs.getString("fullname"));
	                user.setPassWord(rs.getString("password"));
	                user.setAvatar(rs.getString("avatar"));
	                user.setRoleid(rs.getInt("roleid"));
	                user.setPhone(rs.getString("phone"));
	                // user.setCreatedDate(rs.getDate("createddate"));
	                return user;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }

	    @Override
	    public boolean updatePassword(int userId, String newHashedPassword) { // Phương thức mới
	        Connection conn = null;
	        PreparedStatement ps = null;
	        String sql = "UPDATE [User] SET password = ? WHERE id = ?";
	        try {
	            conn = new DBconnect().getConnectionW();
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, newHashedPassword);
	            ps.setInt(2, userId);
	            int rowsAffected = ps.executeUpdate();
	            return rowsAffected > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            try {
	                if (ps != null) ps.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }



}