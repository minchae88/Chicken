package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import main.ChickenStore;
import model.StockModel;

public class StockView extends JPanel implements ActionListener {
	JButton bAdd, bMinus, bPayment, bCancel, bSearchMenu,bLogOut;
	JLabel laNothing,laChickenStore, laTel;
	JTextField tfSearchMenu;
	Border emptyBorder = BorderFactory.createEmptyBorder();

	JTable tableStock, tableStockOrder; // Jtable 뷰1,2
	StockTableModel tbModelStock; // JTable의 모델1
	StockTableOrderModel tbModelStockOrder; // JTable의 모델2

	StockModel model;// 비지니스로직(JDBC 연결)
	ChickenStore parents;
	OrderView order;
	int selectedRow;// 테이블에서 선택한 행

// 이미지 사이즈 조절(사진명,가로,세로)
	public ImageIcon getIcon(String name, int width, int height) {
     	return new ImageIcon(new ImageIcon("src\\view\\chickimg\\" + name + ".png").getImage().getScaledInstance(width,
				height, Image.SCALE_DEFAULT));
	}

// ==============================================
// 생성자 함수
	public StockView(ChickenStore parents, OrderView order) {
		this.parents = parents;
		this.order = order;
		addLayout();
		connectDB();
		eventProc();
		search();// // 재고목록테이블 출력, 화면 뜨자마자 뜨게 하려면
	}

// ==============================================
// 화면 구성
	public void addLayout() {
  // 1. 버튼설정
		bAdd = new JButton(getIcon("bAdd", 40, 40));
		bAdd.setBorder(emptyBorder);
		bAdd.setBorderPainted(false);// 버튼 테두리 설정, false는 없앰
		bAdd.setContentAreaFilled(false);// 버튼 영역 배경 표시 설정, false는 없앰

		bMinus = new JButton(getIcon("bMinus", 40, 40));
		bMinus.setBorder(emptyBorder);
		bMinus.setBorderPainted(false);
		bMinus.setContentAreaFilled(false);

		bPayment = new JButton(getIcon("bPayment", 100, 40));
		bPayment.setBorder(emptyBorder);
		bPayment.setBorderPainted(false);
		bPayment.setContentAreaFilled(false);

		bCancel = new JButton(getIcon("bCancel", 100, 40));
		bCancel.setBorder(emptyBorder);
		bCancel.setBorderPainted(false);
		bCancel.setContentAreaFilled(false);

		bSearchMenu = new JButton(getIcon("bSearchMenu", 100, 40));
		bSearchMenu.setBorder(emptyBorder);
		bSearchMenu.setBorderPainted(false);
		bSearchMenu.setContentAreaFilled(false);
		
		bLogOut=new JButton(getIcon("bLogOut", 100, 40));
		bLogOut.setBorder(emptyBorder);
		bLogOut.setBorderPainted(false);
		bLogOut.setContentAreaFilled(false);

		// 2. 텍스트필드설정
		tfSearchMenu = new JTextField(15);
		// 3.라벨설정
		laNothing = new JLabel(getIcon("laNothing", 200, 60));
		laChickenStore = new JLabel(getIcon("laChickenStore", 350, 100));
		laTel= new JLabel(getIcon("laTel", 200, 60));

		
    	// 4. 테이블설정
		tbModelStock = new StockTableModel();
		tableStock = new JTable(tbModelStock);
		tableStock.setPreferredScrollableViewportSize(new Dimension(400, 300));
		tableStock.setBorder(BorderFactory.createEmptyBorder());// 테이블의 보더 제거

		tbModelStockOrder = new StockTableOrderModel();
		tableStockOrder = new JTable(tbModelStockOrder);
		tableStockOrder.setPreferredScrollableViewportSize(new Dimension(400, 300));
		tableStockOrder.setBorder(BorderFactory.createEmptyBorder());
// 테이블 컬럼 너비,정렬,높이지정 메소드
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		tableStock.getColumn("메뉴").setPreferredWidth(70);// 컬럼의 너비
		tableStock.getColumn("메뉴").setCellRenderer(celAlignCenter);// 컬럼정렬
		tableStock.getColumn("재고량").setPreferredWidth(40);
		tableStock.getColumn("재고량").setCellRenderer(celAlignCenter);
		tableStock.setRowHeight(25);//테이블 행 높이
		tableStockOrder.getColumn("메뉴").setPreferredWidth(70);
		tableStockOrder.getColumn("메뉴").setCellRenderer(celAlignCenter);
		tableStockOrder.getColumn("주문량").setPreferredWidth(40);
		tableStockOrder.getColumn("주문량").setCellRenderer(celAlignCenter);
		tableStockOrder.setRowHeight(25);//테이블 행 높이
	// 테이블 배경색/ 헤더배경색,폰트,글씨크기/ 선택한 행 색 변경
		tableStock.setBackground(new Color(255, 255, 255));
		tableStock.setSelectionBackground(new Color(255, 195, 0)); 
		tableStock.getTableHeader().setFont((new Font("", Font.BOLD, 20)));
		tableStock.getTableHeader().setReorderingAllowed(false); // 컬럼이동 불가 
		tableStock.getTableHeader().setResizingAllowed(false); // 컬럼크기 조절 불가

		tableStockOrder.setBackground(new Color(255, 255, 255));
		tableStockOrder.setSelectionBackground(new Color(255, 195, 0)); 
		tableStockOrder.getTableHeader().setFont((new Font("", Font.BOLD, 20)));
		tableStockOrder.getTableHeader().setReorderingAllowed(false); // 컬럼이동 불가 
		tableStockOrder.getTableHeader().setResizingAllowed(false); // 컬럼크기 조절 불가
// 화면구성
		//화면 위치 -위쪽
		JPanel p_north = new JPanel();
		p_north.setLayout(new GridLayout(1,3));
		p_north.add(laNothing);
		p_north.add(laChickenStore);
		p_north.add(laTel);
		// 화면 위치 -왼쪽
		JPanel p_west = new JPanel(new BorderLayout());
        JPanel p_west_west = new JPanel();
		for (int i = 0; i < 5; i++) {
			p_west_west.add(new JLabel(""));}
		JPanel p_west_south = new JPanel();
		p_west_south.add(tfSearchMenu);
		p_west_south.add(bSearchMenu);
		p_west.add(p_west_west, BorderLayout.WEST);
		//p_west.add(laStockInfo, BorderLayout.NORTH);
		p_west.add(new JScrollPane(tableStock), BorderLayout.CENTER);
		p_west.add(p_west_south, BorderLayout.SOUTH);

		// 화면 위치 -오른쪽
		JPanel p_east = new JPanel(new BorderLayout());
		JPanel p_east_south = new JPanel();// 버튼붙일 패널
		p_east_south.add(bPayment);
		p_east_south.add(bCancel);
		p_east_south.add(bLogOut);
		JPanel p_east_east = new JPanel();
		for (int i = 0; i < 5; i++) {
			p_east_east.add(new JLabel(""));
		}
		//p_east.add(laStockOrderInfo, BorderLayout.NORTH);
		p_east.add(new JScrollPane(tableStockOrder), BorderLayout.CENTER);
		p_east.add(p_east_south, BorderLayout.SOUTH);
		p_east.add(p_east_east, BorderLayout.EAST);

		// 화면 위치 -가운데
		JPanel p_center = new JPanel(new BorderLayout());// 가운데
		JPanel p_center_center = new JPanel(new GridLayout(5, 1));//3 1
		p_center_center.add(new JLabel(" "));
		p_center_center.add(new JLabel(" "));
		JPanel p_center_center_center = new JPanel();
		p_center_center_center.add(bAdd);
		p_center_center_center.add(bMinus);
		p_center_center.add(p_center_center_center);
		p_center_center.add(new JLabel(" "));
		p_center_center.add(new JLabel(" "));
		p_center.add(p_center_center, BorderLayout.CENTER);

		
	//화면 컬러
		p_north.setBackground(Color.WHITE);
		p_west.setBackground(Color.WHITE);
		p_west_west.setBackground(Color.WHITE);
		p_west_south.setBackground(Color.WHITE);
		p_east.setBackground(Color.WHITE);
		p_east_south.setBackground(Color.WHITE);
		p_east_east.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		p_center_center.setBackground(Color.WHITE);
		p_center_center_center.setBackground(Color.WHITE);
		
		
		//화면
		setLayout(new BorderLayout());
		add(p_north, BorderLayout.NORTH);
		add(p_west, BorderLayout.WEST); // 라벨,테이블
		add(p_center, BorderLayout.CENTER);// 가운데에 +/-버튼
		add(p_east, BorderLayout.EAST); // 라벨,테이블,버튼2개
	}

	// 테이블 모델 클래스
	class StockTableModel extends AbstractTableModel {

		ArrayList data = new ArrayList();
		String[] columnNames = { "메뉴", "재고량" };

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public Object getValueAt(int row, int col) {
			ArrayList temp = (ArrayList) data.get(row);
			return temp.get(col);
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}
	}

	class StockTableOrderModel extends AbstractTableModel {

		ArrayList<ArrayList> data = new ArrayList<ArrayList>();
		String[] columnNames = { "메뉴", "주문량" };

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public Object getValueAt(int row, int col) {
			ArrayList temp = (ArrayList) data.get(row);
			return temp.get(col);
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			ArrayList tmp = new ArrayList();
			tmp.add(data.get(rowIndex).get(0));
			tmp.add(aValue);
			data.set(rowIndex, tmp);
		}

		// 재고주문내역테이블에 값은 메뉴명이 있는지 확인
		public boolean contains(String data1) {
			for (ArrayList list : data) {// 향상된for문으로 data 스캔
			if (list.get(0).equals(data1))// list의 0번째 메뉴명이 data1의 값과 같은지,
				return true;
			}
			return false;
		}
	}

	// 이벤트 처리
	void eventProc() {
		bAdd.addActionListener(this);
		bMinus.addActionListener(this);
		bPayment.addActionListener(this);
		bCancel.addActionListener(this);
		bSearchMenu.addActionListener(this);
		bLogOut.addActionListener(this);

		// Mouse 이벤트: 재고목록테이블 클릭하면, 주문목록 테이블에 메뉴 생성
		tableStock.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 가져온 메뉴 이름을 String data에 저장
				int row = tableStock.getSelectedRow();
				int col = 0;// 0번째 컬럼(메뉴명)
				String data1 = (String) tableStock.getValueAt(row, col);
				// 저장한값들을 Arraylist에 담아서
				ArrayList temp = new ArrayList();
				temp.add(data1);// 메뉴명
				temp.add(1);// 주문량컬럼의 값-디폴트값 1로 설정
				if (!tbModelStockOrder.contains(data1)) {// 같은메뉴명이 테이블상에 없다면
					tbModelStockOrder.data.add(temp);// 모델에 붙여서 재고주문목록에 추가
					tbModelStockOrder.fireTableDataChanged();
				}
			}
		});
		// Mouse 이벤트: 재고주문내역테이블 클릭하면, 해당 셀의 좌표를 얻어옮
		tableStockOrder.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectedRow = tableStockOrder.getSelectedRow();
				int col = tableStockOrder.getSelectedColumn();
				Object str = tableStockOrder.getValueAt(selectedRow, col);

			}
		});
	}

	// 버튼 이벤트
	public void actionPerformed(ActionEvent ev) {
		Object evt = ev.getSource();
		if (evt == bPayment) {
			JOptionPane.showMessageDialog(null, "주문이 완료되었습니다");
	// 테이블에서 모든 행을 스캔, 행의 첫번재 컬럼값은 메뉴, 두번째 컬럼값은 주문량 /addstock 함수에 호출
			try {
				for (int i = 0; i < tbModelStockOrder.data.size(); i++) {
				String menu = (String)tbModelStockOrder.getValueAt(i, 0);
				int orderCount = (int) (tbModelStockOrder.getValueAt(i,1));
				model.addStock(menu, orderCount);
				}
				search();//결제 끝나면 재고 목록 바뀌게 
				tbModelStockOrder.data = new ArrayList<>();// table을 비워버림
				tbModelStockOrder.fireTableDataChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (evt == bCancel) {
			JOptionPane.showMessageDialog(null, "주문이 취소되었습니다");
			bCancelDelete();
		} else if (evt == bAdd) { // +버튼 눌렀을때
			try {
				bAddOrder();// +버튼 누르면 주문량 증가
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (evt == bMinus) {// -버튼 눌렀을때
			try {
				bMinusOrder();// -버튼 누르면 주문량 증가
			} catch (Exception e) {
			}
		} else if (evt == bSearchMenu) {
			searchByMenu();
		} else if (evt == bLogOut) {
			logout();
		}
	}

	// 메소드 정리
	void search() {// 재고목록테이블 출력, DB내 데이터 변경된것도 반영되야함
		ArrayList data;
		try {
			data = model.search();
			tbModelStock.data = data;
			tableStock.setModel(tbModelStock);
			tbModelStock.fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bMinusOrder() {
		int row = selectedRow;// 선택한 행,
		int col = 1;
		Integer str = (Integer) tableStockOrder.getValueAt(row, col);
		tableStockOrder.setValueAt(str - 1, row, col);
		tbModelStockOrder.fireTableDataChanged();
	}

	private void bAddOrder() {// 좌표 받아와서,해당 좌표의 값을 변경
		int row = selectedRow;// 선택한 행
		int col = 1;// 주문량컬럼
		Integer str = (Integer) tableStockOrder.getValueAt(row, col);
		tbModelStockOrder.setValueAt(str + 1, row, col);
		tbModelStockOrder.fireTableDataChanged();
	}

	private void bCancelDelete() {
		int row = tableStockOrder.getSelectedRow();
		if (row == -1) {// 선택안하고 취소버튼 누르면 테이블 전체 내용 삭제
			tbModelStockOrder.data = new ArrayList<>();// data를 비워버림
			tbModelStockOrder.fireTableDataChanged();
			return;
		} else {// 선택하면 선택한 행 삭제
			tbModelStockOrder.data.remove(row); // 행 삭제 함수
			tbModelStockOrder.fireTableDataChanged();
		}
	}

	void searchByMenu() {// 메뉴 입력시 메뉴 정보 가져오기
		String menu = tfSearchMenu.getText();
		ArrayList<ArrayList<String>> info;
		try {
			info = model.selectByPK(menu);// 메뉴테이블 정보를 리스트 info에 가져옴.
			String stock = info.get(0).get(0).toString();
			String price = info.get(0).get(1).toString();
			JOptionPane.showMessageDialog(null, "메뉴명: " + menu + "  가격: " + price+"원"+ "  재고량: " + stock);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void logout() {
	    int num = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?");
		if (num == 0) {
			parents.Visible(false);
			parents.setTabIndex(0);
			order.bLogOut.setEnabled(false);
		}	    
	}

	void connectDB() {
		try {
			model = new StockModel();
			System.out.println("재고 디비 연결 성공");
		} catch (Exception e) {
			System.out.println("디비 연결 실패:" + e.getMessage());
			e.printStackTrace();
		}
	}

}
