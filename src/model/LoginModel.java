package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginModel {

	Connection con;
	ArrayList list = new ArrayList();

	public LoginModel() throws Exception {

		con = DBCon.getConnection();
	}

	public ArrayList loginGrant() {

		String sql = "SELECT empno, password FROM employee WHERE job IN ('보스님', '매니저')";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getInt("empno"));
				list.add(rs.getInt("password"));
			}
			st.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("로그인 오류:" + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

}
