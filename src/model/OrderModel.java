package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

public class OrderModel {
	Connection con;

	public OrderModel() throws Exception {
		con = DBCon.getConnection();
	}
	
	public int getOid() {
		int oid = 1;
		try {
			String sql = "SELECT oid from orders order by oid desc";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				oid = rs.getInt("oid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return oid;
	}
	
	public int insertOrderList(Vector data) throws Exception {

		int result = JOptionPane.showConfirmDialog(null, "결제 하시겠습니까?");
		if (result == 0) {
			String sql = "INSERT INTO orderlist (OLID, OID, MENU, COUNT, SUM_PRICE) "
					+ "VALUES (SEQ_OLID.nextval, ?, ?, ?, ?)";
			String sql2 = "INSERT INTO orders (OID, EMPNO, ORDER_DATE) VALUES (seq_oid.nextval, ?, sysdate)";
			String sql3 = "UPDATE menu SET stock = stock - ? where menu = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			PreparedStatement ps2 = con.prepareStatement(sql2);
			PreparedStatement ps3 = con.prepareStatement(sql3);
			ps2.setInt(1, 5);
			ps2.executeUpdate();
			for (int i = 0; i < data.size(); i++) {
				Vector temp = (Vector) data.get(i);
				ps.setInt(1, Integer.parseInt(String.valueOf(temp.get(0))));
				ps.setString(2, String.valueOf(temp.get(1)));
				ps.setInt(3, Integer.parseInt(String.valueOf(temp.get(2))));
				ps.setInt(4, Integer.parseInt(String.valueOf(temp.get(3))));
				ps.executeUpdate();
				
				ps3.setInt(1, Integer.parseInt(String.valueOf(temp.get(2))));
				ps3.setString(2, String.valueOf(temp.get(1)));
				ps3.executeUpdate();
			}
			
			ps.close();
			ps2.close();
			ps3.close();
		}
		
		return result;
	}

}
