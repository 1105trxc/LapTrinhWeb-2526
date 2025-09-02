package khoatrac.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBconnect {
	private final String serverName = "localhost";
	private final String dbName = "LTWeb";
	private final String portNumber = "1433";
	private final String instance = "";
	private final String userID = "";
	private final String password = "";

	public Connection getConnectionW() throws Exception {
			String url = "jdbc:sqlserver://" + serverName + "\\" + instance + ":" +
			portNumber + ";integratedSecurity=true;databaseName=" + dbName;
			if (instance == null || instance.trim().isEmpty())
			url = "jdbc:sqlserver://" + serverName + ":" + portNumber +
			";integratedSecurity=true;databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(url);
		}
	public static void main(String[] args) {
		try {
            // Test kết nối với Windows Authentication
			DBconnect dbConn = new DBconnect();
            Connection conn = dbConn.getConnectionW();

            // Create statement
            Statement stmt = conn.createStatement();

            // Insert 'GiaoVien'
            stmt.executeUpdate("INSERT INTO Users(id, name, address) VALUES (2, 'Trung', 'HCM')");

            // Get data from table 'Users'
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users");

            // Show data
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("address"));
            }

            conn.close(); // close connection
        } catch (Exception ex) {
            ex.printStackTrace();
        }

	}
}
