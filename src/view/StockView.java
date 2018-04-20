package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import model.StockModel;

public class StockView extends JPanel implements ActionListener{
	JButton bAdd, bMinus, bPayment, bCancel, bSearchMenu;
	JLabel laStockInfo, laStockOrderInfo;
	JTextField tfSearchMenu;
	Border emptyBorder = BorderFactory.createEmptyBorder();
	
	JTable tableStock, tableStockOrder; //Jtable 뷰1,2
	StockTableModel tbModelStock; //JTable의 모델1
	StockTableOrderModel tbModelStockOrder; //JTable의 모델2
	
    StockModel model;//비지니스로직(JDBC 연결)

    //이미지 사이즈 조절(사진명,가로,세로)
    public ImageIcon getIcon(String name, int width, int height) {
		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\"+name+".png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}
//==============================================
	//	 생성자 함수
	public StockView(){
		addLayout();
		connectDB();
		eventProc();
		search();//화면 뜨자마자 재고목록 출력
	}
	
	public void addLayout(){
	//버튼설정
	     bAdd = new JButton(getIcon("bAdd", 40, 40));
	     bAdd.setBorder(emptyBorder);
	     bAdd.setBorderPainted(false);//버튼 테두리 설정, false는 없앰
	     bAdd.setContentAreaFilled(false);//버튼 영역 배경 표시 설정, false는 없앰
	     
	     bMinus = new JButton(getIcon("bMinus", 40, 40));
	     bMinus.setBorder(emptyBorder);
	     bMinus.setBorderPainted(false);
	     bMinus.setContentAreaFilled(false);
	     
	     bPayment = new JButton(getIcon("bPayment", 130, 50));
	     bPayment.setBorder(emptyBorder);
	     bPayment.setBorderPainted(false);
	     bPayment.setContentAreaFilled(false);
	     
	     bCancel = new JButton(getIcon("bCancel", 130, 50));
	     bCancel.setBorder(emptyBorder);
	     bCancel.setBorderPainted(false);
	     bCancel.setContentAreaFilled(false);
	     
	     bSearchMenu= new JButton(getIcon("bSearchMenu", 130, 50));
	     bSearchMenu.setBorder(emptyBorder);
	     bSearchMenu.setBorderPainted(false);
	     bSearchMenu.setContentAreaFilled(false);
	//텍스트필드설정     
	     tfSearchMenu=new JTextField(15);
	//라벨설정
	     laStockInfo= new JLabel(new ImageIcon("src\\view\\chickimg\\laStockInfo.png"));
	     laStockOrderInfo=new JLabel(new ImageIcon("src\\view\\chickimg\\laStockOrderInfo.png"));
	//테이블설정     
	     tbModelStock = new StockTableModel();
	     tableStock = new JTable(tbModelStock);
	     tableStock.setPreferredScrollableViewportSize(new Dimension(400, 300));
	     tableStock.getColumn("메뉴").setPreferredWidth(80);
	     tableStock.getColumn("재고량").setPreferredWidth(80);
//tableStock.setBackground(Color.WHITE); 색변경어떻게 하나.....
	     tbModelStockOrder = new StockTableOrderModel();
	     tableStockOrder = new JTable(tbModelStockOrder);
	     tableStockOrder.setPreferredScrollableViewportSize(new Dimension(400, 300));
	     tableStock.getColumn("메뉴").setPreferredWidth(80);
	     tableStock.getColumn("재고량").setPreferredWidth(80);
      
     //화면 위치 -왼쪽
	     JPanel p_west = new JPanel(new BorderLayout());
	     JPanel p_west_west=new JPanel();
	     for(int i=0;i<5;i++){
	   	   p_west_west.add(new JLabel(""));}
 	  	 JPanel p_west_south=new JPanel();
	     p_west_south.add(tfSearchMenu);
	     p_west_south.add(bSearchMenu);
	     p_west.add(p_west_west,BorderLayout.WEST);
	     p_west.add(laStockInfo, BorderLayout.NORTH);
	     p_west.add(new JScrollPane(tableStock), BorderLayout.CENTER);
	     p_west.add(p_west_south,BorderLayout.SOUTH);
	     
	//화면 위치 -오른쪽
	     JPanel p_east = new JPanel(new BorderLayout());
	     JPanel p_east_south = new JPanel();//버튼붙일 패널
	     p_east_south.add(bPayment);
	     p_east_south.add(bCancel);
	     JPanel p_east_east=new JPanel();
	     for(int i=0;i<5;i++){
	      p_east_east.add(new JLabel("")); }
	     p_east.add(laStockOrderInfo, BorderLayout.NORTH);
	     p_east.add(new JScrollPane(tableStockOrder), BorderLayout.CENTER);
	     p_east.add(p_east_south,BorderLayout.SOUTH);
	     p_east.add(p_east_east,BorderLayout.EAST);
	     
	//화면 위치 -가운데
	     JPanel p_center = new JPanel(new BorderLayout());// 가운데
         JPanel p_center_center = new JPanel(new GridLayout(3,1));
         p_center_center.add(new JLabel(" "));
         JPanel p_center_center_center = new JPanel();
         p_center_center_center.add(bAdd);
         p_center_center_center.add(bMinus);
         p_center_center.add(p_center_center_center);
         p_center_center.add(new JLabel(" "));        
         p_center.add(p_center_center,BorderLayout.CENTER);
  
         setLayout(new BorderLayout());
	     add(p_west,BorderLayout.WEST); //라벨,테이블
	     add(p_center,BorderLayout.CENTER);//가운데에 +/-버튼
	     add(p_east,BorderLayout.EAST); //라벨,테이블,버튼2개
 }
	/**
	 * 이 메소드의 역할:
	 * 인자
	 * return값이 뭔지 >?
	 */
	void search(){//재고목록 출력, 화면 뜨자마자 뜨게 하려면
		ArrayList data;
		try {
			data = model.search();
			tbModelStock.data=data;
			tableStock.setModel(tbModelStock);
			tbModelStock.fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
class StockTableModel extends AbstractTableModel { 
	    
	    ArrayList data = new ArrayList();
	    String [] columnNames = {"메뉴","재고량"};
	   
	    //=============================================================
	    // 1. 기본적인 TabelModel  만들기
	    // 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
	    // AbstractTabelModel에서 구현되지 않았기에...
	    // 반드시 사용자 구현 필수!!!!

	        public int getColumnCount() { 
	            return columnNames.length; 
	        } 
	         
	        public int getRowCount() { 
	            return data.size(); 
	        } 

	        public Object getValueAt(int row, int col) { 
	        ArrayList temp = (ArrayList)data.get( row );
	            return temp.get( col ); 
	        }
	        
	        public String getColumnName(int col){
	         return columnNames[col];
	        }

	}
class StockTableOrderModel extends AbstractTableModel { 
	    
	    ArrayList<ArrayList> data = new ArrayList<ArrayList>();
	    String [] columnNames = {"메뉴","주문량"};
	   
	    //=============================================================
	    // 1. 기본적인 TabelModel  만들기
	    // 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
	    // AbstractTabelModel에서 구현되지 않았기에...
	    // 반드시 사용자 구현 필수!!!!

	        public int getColumnCount() { 
	            return columnNames.length; 
	        } 
	         
	        public int getRowCount() { 
	            return data.size(); 
	        } 

	        public Object getValueAt(int row, int col) { 
	        	ArrayList temp = (ArrayList)data.get( row );
	            return temp.get( col ); 
	        }
	        
	        public String getColumnName(int col){
	         return columnNames[col];
	        }
//	        public boolean isCellEditable(int row, int col){
//				return true;
//			
//			}

//			public void removeTableModelListener(int select) {
//				// TODO Auto-generated method stub
//				
//			}

	}

 
    void eventProc(){
    	bAdd.addActionListener(this);
    	bMinus.addActionListener(this);
    	bPayment.addActionListener(this);
    	bCancel.addActionListener(this);
    	bSearchMenu.addActionListener(this);
	
	    //재고목록테이블에 메뉴명 클릭하면, 주문목록 테이블에 메뉴 이름 뜨게  
	    tableStock.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    	//가져온 메뉴 이름을 String data에 저장
	    		int row = tableStock.getSelectedRow();
	    		int col=0;
	    		String data = (String)tableStock.getValueAt(row, col);
			//저장한값들을 Arraylist에 담아서	
	    		ArrayList temp = new ArrayList();
	    		temp.add(data);//메뉴컬럼
	    		temp.add(1);//주문량컬럼-디폴트값 1
	    		tbModelStockOrder.data.add(temp);//모델에 붙여서 재고주문목록에 출력
	    		tbModelStockOrder.fireTableDataChanged();
 		
		    }
	     });
   }	
	public void actionPerformed(ActionEvent ev){
		Object evt = ev.getSource();
		if(evt == bPayment){
			JOptionPane.showMessageDialog(null, "재고 주문이 완료되었습니다");
			//주문량과 DB의 재고량 합해야함.
		}else if(evt==bCancel){
			JOptionPane.showMessageDialog(null, "재고 주문이 취소되었습니다");
			bCancelDelete();
		}else if(evt==bAdd){ //+버튼 눌렀을때
			try {
				JOptionPane.showMessageDialog(null, "주문추가");
				//+버튼 누르면 주문량 증가
				search();     //+버튼을 누르자마자 재고목록이 바로 바뀌게, 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(evt==bMinus){//-버튼 눌렀을때 
			try{
			JOptionPane.showMessageDialog(null, "주문 취소");
			//-버튼 누르면 주문량 증가
			 search();  //-버튼을 누르자마자 재고목록이 바로 바뀌게,
			}catch(Exception e){
			}
		}else if(evt==bSearchMenu){
			searchByMenu();
		}
	}

	private void bCancelDelete() {
		int row = tableStockOrder.getSelectedRow();
		if(row==-1){//선택안하고 취소버튼 누르면 테이블 전체 내용 삭제
			tbModelStockOrder.data = new ArrayList<>();//data를 비워버림
			tbModelStockOrder.fireTableDataChanged();
			return;
		}else{//선택하면 선택한 행 삭제
			tbModelStockOrder.data.remove(row);	//행 삭제 함수
			tbModelStockOrder.fireTableDataChanged();
		}
	}
	
	
	void searchByMenu(){
	//1.입력한 메뉴 가져오기
		String menu = tfSearchMenu.getText();
		ArrayList<ArrayList<String>> info;
		try {
			info=model.selectByPK(menu);//메뉴테이블 정보를 리스트 info에 가져옴.
			String stock=info.get(0).get(0).toString();
			String price=info.get(0).get(1).toString();
     	    JOptionPane.showMessageDialog(null, "메뉴명:"+menu+" 가격:"+price+" 재고량:"+stock);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
}
	void connectDB(){
		try{
			model=new StockModel();
			System.out.println("재고 디비 연결 성공");
			}catch(Exception e){
				System.out.println("디비 연결 실패:"+e.getMessage());
			e.printStackTrace();
			}
	}
	
}
