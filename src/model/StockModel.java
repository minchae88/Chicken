package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StockModel {

	Connection con;
	public StockModel() throws Exception {
		con= DBCon.getConnection();
	}
	
   public ArrayList search() throws Exception{
    	   ArrayList data=new ArrayList();
    	   String sql="SELECT menu, stock FROM menu";
    	   PreparedStatement ps = con.prepareStatement(sql);
    	   ResultSet rs = ps.executeQuery();
      	   while(rs.next()){//2차원 배열
       	 		ArrayList temp = new ArrayList(); //안에도 만듦
       	 		temp.add(rs.getString("menu"));//가져온 정보를 temp에 붙이고 
       	 		temp.add(rs.getString("stock"));
        		data.add(temp);//다시 temp를 data에 붙임(벡터를 벡터에 붙임)
       	}
       	
       	 	rs.close();
       	 	ps.close(); //Array list에 담기
    	    return data;
       }
  //메뉴이름으로 정보 가져오기
   public ArrayList<ArrayList<String>> selectByPK(String menu) throws Exception{
	   ArrayList<ArrayList<String>> info=new ArrayList<>();
	   System.out.println(menu);
	   String sql="SELECT stock, price FROM menu WHERE menu=?";
	   PreparedStatement ps = con.prepareStatement(sql);
	   ps.setString(1, menu);//넘어온 menu를 ps에 붙이고 
	   ResultSet rs = ps.executeQuery();
	   if(rs.next()){
		    ArrayList<String> temp=new ArrayList<>();
		    temp.add(rs.getString("STOCK"));//1
		    temp.add(rs.getString("PRICE"));//2
		    info.add(temp);
     	}
   	   	 	rs.close();
   	 	    ps.close(); 
	    return info;
	}
   //결제버튼 누르면 재고주문내역의 주문량대로 db의 메뉴 테이블 재고량이 증가 된다.
	public void addStock(String menu, int orderCount) throws Exception{
	     String sql="UPDATE menu SET stock = stock+? WHERE menu=?"; 
		 PreparedStatement ps = con.prepareStatement(sql);
		 ps.setInt(1, orderCount);
		 ps.setString(2,menu);
		 ps.executeUpdate();
		 ps.close();
				  
	}


	
	
	
	
	
	
	
	
}
