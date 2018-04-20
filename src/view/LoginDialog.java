package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginDialog extends JDialog implements ActionListener {
	
	JLabel title, userImage, passImage;
	JTextField user;
	JPasswordField pass;
	JButton ok, cancle;
	
	public LoginDialog() {

		addLayout(); 
		eventProc(); 
		
	}
	
	public ImageIcon getIcon(String name, int width, int height) {
			
		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\"+name+".png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}
	
	public void addLayout() {
		
		title = new JLabel(getIcon("l1", 200, 40));
		userImage = new JLabel(getIcon("l2", 25, 25));
		passImage = new JLabel(getIcon("l3", 25, 25));
		ok = new JButton(getIcon("ok", 80, 40));
		ok.setBorderPainted(false);
		ok.setContentAreaFilled(false);
		cancle = new JButton(getIcon("cancle", 80, 40));
		cancle.setBorderPainted(false);
		cancle.setContentAreaFilled(false);
		user = new JTextField("Username");
		pass = new JPasswordField("****");
		
		JPanel p_north = new JPanel();
		p_north.setLayout(new BorderLayout());
		p_north.add(title, BorderLayout.CENTER);
		p_north.add(new JLabel(" "), BorderLayout.NORTH);
		p_north.add(new JLabel(" "), BorderLayout.SOUTH);
		
		JPanel p_center = new JPanel();
		
		JPanel p_center_user = new JPanel();
		p_center_user.add(userImage);
		user.setPreferredSize(new Dimension(160, 30));
		p_center_user.add(user);
		
		JPanel p_center_pass = new JPanel();
		p_center_pass.add(passImage);
		pass.setPreferredSize(new Dimension(160, 30));
		p_center_pass.add(pass);
		
		JPanel p_south = new JPanel();
		p_south.add(ok);
		p_south.add(cancle);
		
		p_center.add(p_center_user);
		p_center.add(p_center_pass);
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);
		
		setSize(300,270);
		setVisible(true);
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		
	}
	
	public void eventProc() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
