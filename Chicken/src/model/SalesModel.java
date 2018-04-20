package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesModel {
	Connection con;

	public SalesModel() throws Exception {
		con = DBCon.getConnection();
	}

	public void tmp() {
		String sql = "select price, stock from menu where menu=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "참이슬");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("STOCK"));
				System.out.println(rs.getString("PRICE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<ArrayList<String>> getDateSales(String period, Date startDate, Date endDate) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		String sql;
		if (period.equals("Daily"))
			sql = "SELECT to_char(order_date, 'mm-dd') order_date_1, daily_sales daily_sales_1 FROM dailysales where to_char(order_date,'yyyy-MM-dd') >= ? and to_char(order_date,'yyyy-MM-dd') <= ? order by order_date";
		else
			sql = "SELECT to_char(order_date, 'yyyy-mm') order_date_1, sum(daily_sales) daily_sales_1 FROM dailysales where to_char(order_date,'yyyy-MM-dd') >= ? and to_char(order_date,'yyyy-MM-dd') <= ? group by to_char(order_date, 'yyyy-mm') order by to_char(order_date, 'yyyy-mm')";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, formatter.format(startDate));
			ps.setString(2, formatter.format(endDate));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<>();
				temp.add(rs.getString("order_date_1"));
				temp.add(rs.getString("daily_sales_1"));

				data.add(temp);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			System.out.println("Fail to List");
		}

		return data;
	}

	public ArrayList getMenuSales(String period, Date startDate, Date endDate) {
		ArrayList data = new ArrayList();
		String sql = "SELECT * FROM dailysales " + " ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("vid"));
				temp.add(rs.getString("title"));
				temp.add(rs.getString("name"));
				temp.add(rs.getString("tel"));
				temp.add(rs.getDate("rent_date"));
				temp.add(rs.getDate("return_date"));

				data.add(temp);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			System.out.println("Fail to List");
		}

		return data;
	}
}
