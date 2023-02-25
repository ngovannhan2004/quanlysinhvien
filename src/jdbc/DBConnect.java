package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.SinhVien;

public class DBConnect {

	static String url = "jdbc:mysql://localhost:3306/quanlysinhvien";
	static String user = "root";
	static String password = "";
	public static Connection connection = getConnection();
	public static Connection getConnection() {
		
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
