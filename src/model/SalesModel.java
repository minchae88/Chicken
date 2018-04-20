package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesModel {
	Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	Connection con;

	public SalesModel() throws Exception {
		con = DBCon.getConnection();
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

	public ArrayList<ArrayList<String>> getMenuSales(String period, Date startDate, Date endDate) {
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		String sql;
		if (period.equals("Daily"))
			sql = "SELECT menu, sum(count) sum FROM menusales WHERE to_char(order_date,'yyyy-MM-dd') >= ? AND to_char(order_date,'yyyy-MM-dd') <= ? GROUP BY menu ORDER BY sum(count) desc";
		else
			sql = "select menu, month, sum from(SELECT row_number() over(partition by to_char(order_date, 'yyyy-MM') order by to_char(order_date, 'yyyy-MM')) rnum, menu, to_char(order_date,'yyyy-MM') month, sum(count) sum FROM menusales WHERE to_char(order_date,'yyyy-MM-dd') >= ? AND to_char(order_date,'yyyy-MM-dd') <= ? GROUP BY menu, to_char(order_date,'yyyy-MM') ORDER BY to_char(order_date, 'yyyy-MM') asc, sum(count) desc) where rnum = 1";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, formatter.format(startDate));
			ps.setString(2, formatter.format(endDate));
			ResultSet rs = ps.executeQuery();
			
			if (period.equals("Daily")) {
				while (rs.next()) {
					ArrayList<String> temp = new ArrayList<>();
					temp.add(rs.getString("menu"));
					temp.add(rs.getString("sum"));

					data.add(temp);
				}
			}
			else {
				while (rs.next()) {
					ArrayList<String> temp = new ArrayList<>();
					temp.add(rs.getString("menu"));
					temp.add(rs.getString("sum"));
					temp.add(rs.getString("month"));

					data.add(temp);
				}
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			System.out.println("Fail to List");
		}

		return data;
	}
}
