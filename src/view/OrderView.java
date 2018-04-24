package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.ChickenStore;
import model.OrderModel;

public class OrderView extends JPanel implements ActionListener, MouseListener {

	JLabel menu, orderList; // 왼쪽, 오른쪽의 제목
	JButton bStoreManagement, bCancel; // 매장 관리 버튼
	
	// 메뉴 버튼 20개를 추가 할 변수들
	int size = 20;
	ImageIcon [] imageIcon = new ImageIcon[size];
	JButton [] btn = new JButton[size];
	String [] strMenu = {"후라이드치킨", "양념치킨", "허니치킨", "땡초치킨", "크리미언치킨", "세트치킨", "눈꽃치킨", "마늘치킨",
			"꼬꼬칩스", "치킨샐러드", "웨지감자", "파채추가", "사이다", "콜라", "카스", "호가든", "칭따오", "기네스", "처음처럼", "참이슬"};
	String [] menuPrice = {"15000", "16000", "16000", "17000", "17000", "19000", "18000", "18000", "9000", "10000", "8000", "5000", "2000", "2000", "3000", "4000", "5000", "5000", "3000", "3000"};
	int [] imageWidth = {50, 70, 90, 90, 50, 50, 80, 100, 100, 100, 100, 100, 60, 60, 120, 100, 40, 60, 40, 60};
	int [] imageHeight = {50, 70, 90, 90, 50, 50, 80, 100, 100, 100, 100, 100, 100, 100, 100, 100 ,100, 100, 100, 110};
	int[] initValue;

	JTable tableOrderList;	

	DefaultTableModel tableModel;

	Border emptyBorder = BorderFactory.createEmptyBorder();

	Vector columnNames = new Vector(); // 컬럼의 이름을 벡터로 저장
	Vector<Vector<String>> data = new Vector<>(); // 주문 목록에 저장될 데이터들을 저장하는 벡터

	ChickenStore parents;
	OrderModel model;
	
	// 생성자
	public OrderView(ChickenStore parents) {
		this.parents = parents;
		addLayout(); // 화면설계
		eventProc(); // 이벤트 호출
		connectDB(); // 디비 연결
	}

	public void init() {
	}

	// 이미지 사이즈 조절하는 메소드
	public ImageIcon getIcon(String name, int width, int height) {

		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\"+name+".png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}

	// 화면 설계 레이아웃
	public void addLayout() {

		
		menu = new JLabel(getIcon("laMenu", 250, 80));
		orderList = new JLabel(getIcon("laOrderList", 230, 80));
	

		for(int i=0; i<size; i++) {
			btn[i] = new JButton(getIcon("c"+(i+1)+"", 150, 100));
			btn[i].setBackground(Color.WHITE);
//imageIcon[i] = new ImageIcon(new ImageIcon("src\\view\\chickimg\\c"+(i+1)+".png").getImage().getScaledInstance(imageWidth[i], imageHeight[i], Image.SCALE_DEFAULT));
//btn[i] = new JButton("<html>"+strMenu[i] +"<br/>" + "<font color='red'>"+menuPrice[i]+"</font>" + "</html>", imageIcon[i]);
		}
		
		bStoreManagement = new JButton(getIcon("bStoreManagement", 130, 50));
		bStoreManagement.setBorder(emptyBorder);
		bStoreManagement.setBorderPainted(false);
		bStoreManagement.setContentAreaFilled(false);

		bCancel = new JButton(getIcon("bCancel", 130, 50));
		bCancel.setBorder(emptyBorder);
		bCancel.setBorderPainted(false);
		bCancel.setContentAreaFilled(false);


		//  테이블의 제목 생성
		columnNames.add("주문번호");
		columnNames.add("메뉴명");
		columnNames.add("수량");
		columnNames.add("가격");
		// JTable에 붙일 DefaultTableModel 정의
		tableModel = new DefaultTableModel(data, columnNames);
		// DefaultTableModel 붙이기

		tableOrderList = new JTable(tableModel);


		tableOrderList.setEnabled(true);
//테이블정렬	
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		tableOrderList.getColumn("주문번호").setPreferredWidth(80);// 컬럼의 너비
		tableOrderList.getColumn("주문번호").setCellRenderer(celAlignCenter);// 컬럼정렬
		tableOrderList.getColumn("메뉴명").setPreferredWidth(80);// 컬럼의 너비
		tableOrderList.getColumn("메뉴명").setCellRenderer(celAlignCenter);// 컬럼정렬
		tableOrderList.getColumn("수량").setPreferredWidth(80);// 컬럼의 너비
		tableOrderList.getColumn("수량").setCellRenderer(celAlignRight);// 컬럼정렬
		tableOrderList.getColumn("가격").setPreferredWidth(80);// 컬럼의 너비
		tableOrderList.getColumn("가격").setCellRenderer(celAlignRight);// 컬럼정렬
		

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
		p_east_south.add(bStoreManagement);
		p_east_south.add(bCancel);
		

		// 오른쪽의 오른쪽 영역
		JPanel p_east_east = new JPanel(new GridLayout(1,10));
		for(int i=0; i<10; i++) {
			p_east_east.add(new JLabel(" "));
		}



	
//컬러
		p_west.setBackground(Color.ORANGE);
		p_west_north.setBackground(Color.ORANGE);
		p_menu_list.setBackground(Color.ORANGE);
		p_west_south.setBackground(Color.ORANGE);
		p_west_west.setBackground(Color.ORANGE);
		p_east.setBackground(Color.ORANGE);
		p_east_north.setBackground(Color.ORANGE);
		p_east_south.setBackground(Color.ORANGE);
		p_east_east.setBackground(Color.ORANGE);
		

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
		try {
			model = new OrderModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 선택한 버튼을 테이블에 옮기기
	public void moveTable(int idx) {

		Vector<String> tmp = new Vector<String>();
		tmp.add(String.valueOf(model.oid));
		tmp.add(strMenu[idx]);
		tmp.add(String.valueOf(1));
		tmp.add(menuPrice[idx]);

		if(data.size() == 0) data.add(tmp);
		else {
			for(int i=0; i<data.size(); i++) {
				if (data.get(i).get(1).equals(strMenu[idx])) {
					int num = Integer.parseInt(String.valueOf((Integer.parseInt(data.get(i).get(2))+1)));
					data.get(i).set(2, String.valueOf((Integer.parseInt(data.get(i).get(2))+1)));
					data.get(i).set(3, String.valueOf((Integer.parseInt(data.get(i).get(3))+Integer.parseInt(menuPrice[idx]))));
					break;
				} else if (i == data.size()-1) {
					data.add(tmp);
					break;
				}

			}
		}

		tableModel.setDataVector(data, columnNames);
		tableOrderList.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}

	// 이벤트 등록
	public void eventProc() {
		bStoreManagement.addActionListener(this);
		for (int i=0; i<20; i++) {
			btn[i].addActionListener(this);
		}
		tableOrderList.addMouseListener(this);

//		tableOrderList.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int row = tableOrderList.getSelectedRow();
//				int col = 0;
//				String data1 = (String) tableOrderList.getValueAt(row, col);
//				JTable orderList = new JTable(model);
//				OrderDialog orderDialog = new OrderDialog(orderList);
//				orderDialog.orderField.setText(data1);
//				orderDialog.setVisible(true);
//			}
//		});
		
	}

	// 이벤트가 발생했을때
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object evt = e.getSource();
		if (evt == bStoreManagement) {
			try {
				new LoginDialog(parents);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} 
		for(int i=0; i<size; i++){
			if (evt == btn[i]) {
				moveTable(i);
			}	
		}


	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int row = tableOrderList.getSelectedRow();
		int col = 0;
		String data1 = (String) tableOrderList.getValueAt(row, col);
		JTable orderList = new JTable(tableModel);
		OrderDialog orderDialog = new OrderDialog(this);
		orderDialog.orderField.setText(data1);
		orderDialog.setVisible(true);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
