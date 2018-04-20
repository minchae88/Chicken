package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon {
	static Connection con;
	static DBCon db;
	
	private String driver = "oracle.jdbc.driver.OracleDriver"; // 오라클 드라이버 경로
	private String url = "jdbc:oracle:thin:@70.12.115.58:1521:orcl";
	private String user = "chicken"; // 내 DB 계정
	private String pass = "chicken"; // 내 DB 비번
	
	private DBCon() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(url, user, pass);
	}

	public static Connection getConnection() throws Exception {
		if (con == null)
			new DBCon();
		return con;
	}
}
