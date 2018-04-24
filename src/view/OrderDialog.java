package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jfree.util.ExtendedConfigurationWrapper;

import model.OrderModel;

public class OrderDialog extends JDialog implements ActionListener {

	JLabel title, orderNum, totalPrice;
	JButton cancel, payment, delete;
	JTextField orderField, priceField;
	JButton plus, minus;

	JTable tableOrderList;
	DefaultTableModel tableModel;
	Vector columnNames = new Vector();

	OrderView parents;
	OrderModel model;

	public OrderDialog(OrderView parents) {
		this.parents = parents;
		setTitle("Goobne Order Dialog");
		setLocationRelativeTo(null);// 창 화면 정가운데 뜨게
		addLayout();
		eventProc();
		// connectDB();
		setTotalPrice();

	}

	public ImageIcon getIcon(String name, int width, int height) {

		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\" + name + ".png").getImage().getScaledInstance(width,
				height, Image.SCALE_DEFAULT));
	}

	public void addLayout() {

		plus = new JButton(getIcon("bAdd", 40, 40));
		plus.setBorderPainted(false);// 버튼 테두리 설정, false는 없앰
		plus.setContentAreaFilled(false);// 버튼 영역 배경 표시 설정, false는 없앰

		minus = new JButton(getIcon("bMinus", 40, 40));
		minus.setBorderPainted(false);
		minus.setContentAreaFilled(false);

		columnNames.add("주문번호");
		columnNames.add("메뉴명");
		columnNames.add("수량");
		columnNames.add("가격");

		tableModel = new DefaultTableModel(parents.data, columnNames);
		tableOrderList = new JTable(tableModel);
		tableOrderList.setSelectionBackground(new Color(255, 195, 0));
		setSize(500, 500);

		title = new JLabel(getIcon("laMenuCheck", 200, 50));

		orderNum = new JLabel(getIcon("bOrderNum", 80, 30));
		totalPrice = new JLabel(getIcon("bTotal", 80, 30));

		payment = new JButton(getIcon("bPay", 100, 40));
		payment.setBorderPainted(false);
		payment.setContentAreaFilled(false);
		delete = new JButton(getIcon("bDelete", 100, 40));
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);
		cancel = new JButton(getIcon("bCancel", 100, 40));
		cancel.setBorderPainted(false);
		cancel.setContentAreaFilled(false);

		orderField = new JTextField();
		priceField = new JTextField();

		JPanel p_north = new JPanel();
		p_north.setLayout(new GridLayout(1, 6));
		p_north.add(title);

		JPanel p_center = new JPanel();
		p_center.setLayout(new BorderLayout());

		JPanel p_center_north = new JPanel();
		p_center_north.setLayout(new GridLayout(1, 6));
		p_center_north.add(orderNum);
		p_center_north.add(orderField);
		for (int i = 0; i < 4; i++) {
			p_center_north.add(new JLabel(" "));
		}

		JPanel p_center_center = new JPanel();
		p_center_center.setLayout(new BorderLayout());
		p_center_center.setPreferredSize(new Dimension(200, 300));
		JScrollPane scrollPane = new JScrollPane(tableOrderList);
		scrollPane.setPreferredSize(new Dimension(200, 300));
		p_center_center.add(tableOrderList);

		JPanel p_center_east = new JPanel();
		p_center_east.setLayout(new GridLayout(2, 1));
		p_center_east.add(plus);
		p_center_east.add(minus);

		JPanel p_center_south = new JPanel();
		p_center_south.setLayout(new GridLayout(2, 6));
		p_center_south.add(totalPrice);
		p_center_south.add(priceField);
		for (int i = 0; i < 10; i++) {
			p_center_south.add(new JLabel(" "));
		}
		JPanel p_south = new JPanel();
		p_south.add(payment);
		p_south.add(delete);
		p_south.add(cancel);
		// 컬러
		p_north.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		p_center_east.setBackground(Color.WHITE);
		p_center_north.setBackground(Color.WHITE);
		p_center_center.setBackground(Color.WHITE);
		p_center_south.setBackground(Color.WHITE);
		p_south.setBackground(Color.WHITE);

		setLayout(new BorderLayout());
		p_center.add(p_center_north, BorderLayout.NORTH);
		p_center.add(new JScrollPane(p_center_center), BorderLayout.CENTER);
		p_center.add(p_center_east, BorderLayout.EAST);
		p_center.add(p_center_south, BorderLayout.SOUTH);
		add(p_center, BorderLayout.CENTER);
		add(p_north, BorderLayout.NORTH);
		add(p_south, BorderLayout.SOUTH);

		setVisible(true);

	}

	public void eventProc() {
		plus.addActionListener(this);
		minus.addActionListener(this);
		cancel.addActionListener(this);
		payment.addActionListener(this);
		delete.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();
		if (plus == evt) {
			menuPlus();
			setTotalPrice();
		} else if (minus == evt) {
			menuMinus();
			setTotalPrice();

		} else if (cancel == evt) {
			parents.tableModel.fireTableDataChanged();
			setVisible(false);
		} else if (payment == evt) {
			if (insertOrderList() == 0) {
				parents.tableModel.setNumRows(0);
				parents.tableModel.fireTableDataChanged();
				parents.parents.stock.search();
				this.dispose();
			}
		} else if (delete == evt) {
			int selectedRow = tableOrderList.getSelectedRow();
			if (selectedRow != -1) {
				tableModel.removeRow(selectedRow);
				setTotalPrice();
			}
		}
	}

	public void menuPlus() {
		int row = tableOrderList.getSelectedRow();
		if (row == -1)
			return;

		int changeNum = Integer.parseInt(String.valueOf(tableOrderList.getValueAt(row, 2))) + 1;
		tableOrderList.setValueAt(changeNum, row, 2);

		if (changeNum < 1) {
		} else {
			int price = Integer.parseInt(String.valueOf(tableOrderList.getValueAt(row, 3))) / (changeNum - 1);
			int changePrice = price * changeNum;
			tableOrderList.setValueAt(changePrice, row, 3);
		}
	}

	public void menuMinus() {

		int row = tableOrderList.getSelectedRow();
		if (row == -1)
			return;

		int totalSum = Integer.parseInt(String.valueOf(tableOrderList.getValueAt(row, 3))); // 현재
		// 총
		// 금액
		int currentNum = Integer.parseInt(String.valueOf(tableOrderList.getValueAt(row, 2))); // 현재
		// 갯수
		int price = totalSum / currentNum; // 개당 단가
		int changeNum = currentNum - 1;

		if (changeNum < 1) {
		} else {
			tableOrderList.setValueAt(changeNum, row, 2);
			tableOrderList.setValueAt(totalSum - price, row, 3);
		}
	}

	public void setTotalPrice() {

		int sum = 0;
		for (int i = 0; i < tableModel.getRowCount(); i++)
			sum += Integer.parseInt(String.valueOf(tableModel.getValueAt(i, 3)));

		priceField.setText(String.valueOf(sum));
	}

	public int insertOrderList() {
		int result = 0;
		try {
			result = parents.model.insertOrderList(tableModel.getDataVector());
			parents.orderId++;
		} catch (Exception e) {
			System.out.println("주문 정보 전송 실패");
			e.printStackTrace();
		}
		return result;
	}

}