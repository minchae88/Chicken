package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class OrderDialog extends JDialog implements ActionListener {
	
	JLabel title, orderNum, menuList, totalPrice;
	JButton modify, cancle, payment;
	JTextField orderField, priceField;
	JLabel[] menuField, menuNum;
	JButton[] plus, minus;
	
	public OrderDialog() {
		
		addLayout();
		eventProc();
		
	}
	
	public ImageIcon getIcon(String name, int width, int height) {
		
		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\"+name+".png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}
	
	public void addLayout() {
		
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
		p_north.setLayout(new GridLayout(1,6));
		p_north.add(title);
		
		JPanel p_center = new JPanel();
		p_center.setLayout(new BorderLayout());
		
		JPanel p_center_north = new JPanel();
		p_center_north.setLayout(new GridLayout(1,6));
		p_center_north.add(orderNum);
		p_center_north.add(orderField);
		for(int i=0; i<4; i++) {
			p_center_north.add(new JLabel(" "));
		}
		
		JPanel p_center_center = new JPanel();
		p_center_center.setLayout(new GridLayout(5, 1));
		p_center_center.add(new JLabel("a"));
		p_center_center.add(new JLabel("b"));
		p_center_center.add(new JLabel("c"));
		p_center_center.add(new JLabel("a"));
		p_center_center.add(new JLabel("b"));
		p_center_center.add(new JLabel("c"));
		p_center_center.add(new JLabel("a"));
		p_center_center.add(new JLabel("b"));
		p_center_center.add(new JLabel("c"));
		p_center_center.add(new JLabel("a"));
		p_center_center.add(new JLabel("b"));
		p_center_center.add(new JLabel("c"));
		
		JPanel p_center_south = new JPanel();
		p_center_south.setLayout(new GridLayout(2,6));
		p_center_south.add(totalPrice);
		p_center_south.add(priceField);
		for(int i=0; i<10; i++) {
			p_center_south.add(new JLabel(" "));
		}
		
		JPanel p_south = new JPanel();
		p_south.add(modify);
		p_south.add(cancle);
		p_south.add(payment);
	
		setLayout(new BorderLayout());
		p_center.add(p_center_north, BorderLayout.NORTH);
		p_center.add(new JScrollPane(p_center_center), BorderLayout.CENTER);
		p_center.add(p_center_south, BorderLayout.SOUTH);
		add(p_center, BorderLayout.CENTER);
		add(p_north, BorderLayout.NORTH);
		add(p_south, BorderLayout.SOUTH);
		
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}
	
	public void eventProc() {
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	
}
