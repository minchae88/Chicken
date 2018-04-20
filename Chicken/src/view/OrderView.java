package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OrderView extends JPanel implements ActionListener {
	
	JLabel menu, orderList; // 왼쪽, 오른쪽의 제목
	JButton bStoreManagement; // 매장 관리 버튼
	
	// 메뉴 버튼 20개를 추가 할 변수들
	int size = 20;
	ImageIcon []imageIcon = new ImageIcon[size];
	JButton [] btn = new JButton[size];
	String [] strMenu = {"후라이드 치킨", "양념 치킨", "허니 치킨", "땡초 치킨", "크리미언 치킨", "세트 치킨", "눈꽃 치킨", "마늘 치킨",
						"꼬꼬 칩스", "치킨 샐러드", "웨지 감자", "파채 추가", "사이다", "콜라", "카스", "호가든", "칭따오", "기네스", "처음처럼", "참이슬"};
	String [] menuPrice = {"15,000원", "16,000원", "16,000원", "17,000원", "17,000원", "19,000원", "18,000원", "18,000원", "9,000원", "10,000원", "8,000원", "5,000원", "2,000원", "2,000원", "3,000원", "4,000원", "5,000원", "5,000원", "3,000원", "3,000원"};
	int [] imageWidth = {50, 70, 90, 90, 50, 50, 80, 100, 100, 100, 100, 100, 60, 60, 120, 100, 40, 60, 40, 60};
	int [] imageHeight = {50, 70, 90, 90, 50, 50, 80, 100, 100, 100, 100, 100, 100, 100, 100, 100 ,100, 100, 100, 110};
	
	JTable tableOrderList;	
	DefaultTableModel model;
	Vector columnNames = new Vector(); // 컬럼의 이름을 벡터로 저장
	Vector data = new Vector(); // 주문 목록에 저장될 데이터들을 저장하는 벡터
	
	// 생성자
	public OrderView() {

		addLayout(); // 화면설계
		eventProc(); // 이벤트 호출
		connectDB(); // 디비 연결
		
	}
	
	// 이미지 사이즈 조절하는 메소드
	public ImageIcon getIcon(String name, int width, int height) {
		
		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\"+name+".png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}
	
	// 화면 설계 레이아웃
	public void addLayout() {
		
		menu = new JLabel(getIcon("i1", 150, 50));
		orderList = new JLabel(getIcon("i2", 150, 50));
	
		for(int i=0; i<size; i++) {
			imageIcon[i] = new ImageIcon(new ImageIcon("src\\view\\chickimg\\c"+(i+1)+".png").getImage().getScaledInstance(imageWidth[i], imageHeight[i], Image.SCALE_DEFAULT));
			btn[i] = new JButton("<html>"+strMenu[i] +"<br/>" + "<font color='red'>"+menuPrice[i]+"</font>" + "</html>", imageIcon[i]);
		}

		bStoreManagement = new JButton(getIcon("i3", 120, 40));
		bStoreManagement.setBorderPainted(false);
		bStoreManagement.setContentAreaFilled(false);
		
		//  테이블의 제목 생성
		columnNames.add("주문번호");
		columnNames.add("메뉴명");
		columnNames.add("수량");
		columnNames.add("가격");
		// JTable에 붙일 DefaultTableModel 정의
		model = new DefaultTableModel(data, columnNames);
		// DefaultTableModel 붙이기
		tableOrderList = new JTable(model);
		
		// 전체 왼쪽 영역
		JPanel p_west = new JPanel();
		p_west.setLayout(new BorderLayout());
		
		// 왼쪽 위 영역
		JPanel p_west_north = new JPanel();
		p_west_north.setLayout(new BorderLayout());
		p_west_north.add(menu, BorderLayout.CENTER);
		
		// 왼쪽 가운데 영역
		JPanel p_menu_list = new JPanel();
		p_menu_list.setLayout(new GridLayout(10,2));
		for(int i=0; i<size; i++) {
			p_menu_list.add(btn[i]);
		}
		JScrollPane s_menu_list = new JScrollPane(p_menu_list);
		s_menu_list.setPreferredSize(new Dimension(450, 400));
		
		// 왼쪽 아래 영역
		JPanel p_west_south = new JPanel(new GridLayout(3,1));
		p_west_south.add(new JLabel(" "));
		p_west_south.add(new JLabel(" "));
		p_west_south.add(new JLabel(" "));
		
		// 왼쪽의 왼쪽영역
		JPanel p_west_west = new JPanel(new GridLayout(1,10));
		for(int i=0; i<10; i++) {
			p_west_west.add(new JLabel(" "));
		}
		
		// 전체 오른쪽 영역
		JPanel p_east = new JPanel();
		p_east.setLayout(new BorderLayout());
		
		// 오른쪽 위 영역
		JPanel p_east_north = new JPanel();
		p_east_north.setLayout(new BorderLayout());
		p_east_north.add(orderList, BorderLayout.CENTER);
		
		// 오른쪽 아래 영역
		JPanel p_east_south = new JPanel();
		p_east_south.setLayout(new BorderLayout());
		p_east_south.add(bStoreManagement, BorderLayout.CENTER);
		
		// 오른쪽의 오른쪽 영역
		JPanel p_east_east = new JPanel(new GridLayout(1,10));
		for(int i=0; i<10; i++) {
			p_east_east.add(new JLabel(" "));
		}
		
		// 왼쪽 붙이기
		p_west.add(p_west_north, BorderLayout.NORTH);
		p_west.add(s_menu_list, BorderLayout.CENTER);
		p_west.add(p_west_south, BorderLayout.SOUTH);
		p_west.add(p_west_west, BorderLayout.WEST);
		
		// 오른쪽 붙이기
		p_east.add(p_east_north, BorderLayout.NORTH); 
		p_east.add(new JScrollPane(tableOrderList), BorderLayout.CENTER); 
		p_east.add(p_east_south, BorderLayout.SOUTH); 
		p_east.add(p_east_east, BorderLayout.EAST);
		
		// 전체 붙이기
		setLayout(new BorderLayout());
		add(p_west, BorderLayout.WEST);
		add(p_east, BorderLayout.EAST);	
	}
	
	// 디비 연결
	public void connectDB() {
		
	}
	
	// 이벤트 등록
	public void eventProc() {
		
		bStoreManagement.addActionListener(this);

	}
	
	// 이벤트가 발생했을때
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object evt = e.getSource();
		
	}

}
