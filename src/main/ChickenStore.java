package main;

import java.awt.Color;

//import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import view.OrderView;
import view.SalesView;
import view.StockView;
///
public class ChickenStore extends JFrame {

  OrderView order;
  SalesView sales;
  StockView stock;
  JTabbedPane pane = new JTabbedPane();
  
  // 생성자
  public ChickenStore() { 
  // 각각의 화면을 관리하는 클래스 객체 생성
    setSize(1000,600);
    order = new OrderView(this);
    sales = new SalesView(getWidth(), getHeight());//매출관리
    stock = new StockView();//재고관리 
   
    pane.addTab("주 문", order);
    pane.addTab("매 출", sales);
    pane.addTab("재 고", stock);
          
 //컬러     
    //1.패널 배경색
      order.setBackground(Color.ORANGE);
      sales.setBackground(Color.ORANGE);
      stock.setBackground(Color.ORANGE);
      
//    stock.setBorder(new EmptyBorder(0, 0, 0, 0));
//    sales.setBorder(new EmptyBorder(0, 0, 0, 0));
//    order.setBorder(new EmptyBorder(0, 0, 0, 0));
//    pane.setBorder(new EmptyBorder(0, 0, 0, 0));
//      
    //2.탭의 배경색, 탭의 글씨색  
      pane.setBackgroundAt(0, Color.ORANGE);//탭의 배경색
      pane.setForegroundAt(0, Color.BLACK);//탭의 색
      pane.setBackgroundAt(1, Color.ORANGE);
      pane.setForegroundAt(1, Color.BLACK);
      pane.setBackgroundAt(2, Color.ORANGE);
      pane.setForegroundAt(2, Color.BLACK);
      // 탭 화면의 최초화면을 인덱스로 지정
      pane.setSelectedIndex(0);
      
  
      // 화면크기지정
      add("Center", pane);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
  }
  
  public void setTabIndex(int i){
    pane.setSelectedIndex(i);
  }
  
  public void visible(boolean flag) {
    
    pane.setEnabledAt(1, flag);
    pane.setEnabledAt(2, flag);
    
  }
  
  public static void main(String[] args) {
      ChickenStore chickenStore = new ChickenStore();
      chickenStore.visible(false);
}

}