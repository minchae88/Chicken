package main;


import java.awt.*;
import javax.swing.*;

import view.OrderView;
import view.SalesView;
import view.StockView;


public class ChickenStore extends JFrame {
	
	OrderView order;
	SalesView sales;
	StockView stock;

	// 생성자
	public ChickenStore() { 
	// 각각의 화면을 관리하는 클래스 객체 생성
		setSize(1000,600);
		order = new OrderView();
		sales = new SalesView(getWidth(), getHeight());//매출관리
		stock = new StockView();//재고관리 
		
			JTabbedPane pane = new JTabbedPane();
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
	public static void main(String[] args) {
			new ChickenStore();
	}
}

