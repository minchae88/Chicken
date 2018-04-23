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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.OrderModel;

public class OrderDialog extends JDialog implements ActionListener {

	JLabel title, orderNum, menuList, totalPrice;
	JButton modify, cancle, payment;
	JTextField orderField, priceField;
	JButton plus = new JButton("+");
	JButton minus = new JButton("-");
	JTable tableOrderList;
	DefaultTableModel tableModel;
	Vector columnNames = new Vector();

	OrderView parents;

	OrderModel model;

	// public OrderDialog(JTable tableOrderList) {
	//
	// this.tableOrderList = tableOrderList;
	// addLayout();
	// eventProc();
	// setTotalPrice();
	//
	// }

	public OrderDialog(OrderView parents) {
		this.parents = parents;
		addLayout();
		eventProc();
		connectDB();
		setTotalPrice();

	}

	public ImageIcon getIcon(String name, int width, int height) {

		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\" + name + ".png").getImage().getScaledInstance(width,
				height, Image.SCALE_DEFAULT));
	}

	public void addLayout() {

		columnNames.add("주문번호");
		columnNames.add("메뉴명");
		columnNames.add("수량");
		columnNames.add("가격");
		tableModel = new DefaultTableModel(parents.data, columnNames);
		tableOrderList = new JTable(tableModel);
		setSize(500, 500);

		title = new JLabel(getIcon("menuTitle", 200, 50));
		orderNum = new JLabel(getIcon("orderNum", 100, 30));
		menuList = new JLabel(getIcon("menuList", 100, 30));
		totalPrice = new JLabel(getIcon("totalPrice", 100, 30));
		modify = new JButton(getIcon("modify", 60, 30));
		modify.setBorderPainted(false);
		modify.setContentAreaFilled(false);
		cancle = new JButton(getIcon("cancle2", 60, 30));
		cancle.setBorderPainted(false);
		cancle.setContentAreaFilled(false);
		payment = new JButton(getIcon("payment", 60, 30));
		payment.setBorderPainted(false);
		payment.setContentAreaFilled(false);
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
		p_south.add(modify);
		p_south.add(cancle);
		p_south.add(payment);

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
		cancle.addActionListener(this);
		payment.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {

		Object evt = e.getSource();
		if (plus == evt) {
			menuPlus();
			setTotalPrice();
		} else if (minus == evt) {
			menuMinus();
			setTotalPrice();
		} else if (cancle == evt) {
			tableModel.removeRow(tableOrderList.getSelectedRow());
			setTotalPrice();
		} else if (payment == evt) {
			if (insertOrderList() == 0) {
				parents.tableModel.setNumRows(0);
				parents.tableModel.fireTableDataChanged();
				this.dispose();
			}
		}
	}

	public void menuPlus() {

		int row = tableOrderList.getSelectedRow();
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

	public void connectDB() {
		try {
			model = new OrderModel();
			System.out.println("오더 디비 연결 성공");
		} catch (Exception e) {
			System.out.println("디비 연결 실패:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public int insertOrderList() {
		int result = 0;
		try {
			model.getOid();
			model.getEmpno();
			result = model.insertOrderList(tableModel.getDataVector());
		} catch (Exception e) {
			System.out.println("주문 정보 전송 실패");
			e.printStackTrace();
		}

		return result;
	}

}