package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

public class OrderModel {

	Connection con;
	public int empno;
	public int oid;

	public OrderModel() throws Exception {
		con = DBCon.getConnection();
	}
	
	public void getOid() {
		try {
			String sql = "SELECT seq_oid.nextval from dual";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				oid = rs.getInt("nextval");
				System.out.println(oid);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getEmpno() {
		try {
			String sql = "SELECT empno FROM employee WHERE ENAME = '이지은'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				empno = rs.getInt("empno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertOrderList(Vector data) throws Exception {

		int result = JOptionPane.showConfirmDialog(null, "리얼 결제?");
		if (result == 0) {
			String sql = "INSERT INTO orderlist (OLID, OID, MENU, COUNT, SUM_PRICE) "
					+ "VALUES (SEQ_OLID.nextval, ?, ?, ?, ?)";
			String sql2 = "INSERT INTO orders (OID, EMPNO, ORDER_DATE) VALUES (?, ?, sysdate)";
			PreparedStatement ps = con.prepareStatement(sql);
			PreparedStatement ps2 = con.prepareStatement(sql2);
			
			ps2.setInt(1, oid);
			ps2.setInt(2, empno);
			ps2.executeUpdate();
			for (int i = 0; i < data.size(); i++) {
				Vector temp = (Vector) data.get(i);
				ps.setInt(1, oid);
				ps.setString(2, String.valueOf(temp.get(1)));
				ps.setInt(3, Integer.parseInt(String.valueOf(temp.get(2))));
				ps.setInt(4, Integer.parseInt(String.valueOf(temp.get(3))));
				ps.executeUpdate();

			}
			
			getOid();
		}
		
		
		
		return result;
	}

}
