package main;


import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.OrderView;
import view.SalesView;
import view.StockView;


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