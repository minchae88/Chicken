package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.ChickenStore;
import model.LoginModel;

public class LoginDialog extends JDialog implements ActionListener {
	  JLabel title, userImage, passImage;
	  JTextField user;
	  JPasswordField pass;
	  JButton bLogin, bCancel;
	  
	  Connection con;
	  ChickenStore parent;
	  OrderView order;
	  LoginModel loginModel;
	  ArrayList list = new ArrayList();
	  
	  public LoginDialog(ChickenStore parent) throws Exception {
	    this.parent = parent;
	    setTitle("Manager Login Dialog");
	    setLocationRelativeTo(null);// 창 화면 정가운데 뜨게
	    order = parent.getOrderView();
	    addLayout(); 
	    eventProc(); 
	    connectDB();	    
	  }
	  
	  public ImageIcon getIcon(String name, int width, int height) {
	      
		    return new ImageIcon(new ImageIcon("src\\view\\chickimg\\"+name+".png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		  }

	  public void addLayout() {
		  
		    title = new JLabel(getIcon("laLogin", 200, 50));
		    userImage = new JLabel(getIcon("laUserImage", 25, 25));
		    passImage = new JLabel(getIcon("laPassImage", 25, 25));
		    
		    bLogin = new JButton(getIcon("bLogin", 80, 40));
		    bLogin.setBorderPainted(false);
		    bLogin.setContentAreaFilled(false);
		    bCancel = new JButton(getIcon("bCancel", 80, 40));
		    bCancel.setBorderPainted(false);
		    bCancel.setContentAreaFilled(false);
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
	    p_south.add(bLogin);
	    p_south.add(bCancel);
	    
 //컬러
	    p_north.setBackground(Color.WHITE);
	    p_center.setBackground(Color.WHITE);
	    p_center_user.setBackground(Color.WHITE);
	    p_center_pass.setBackground(Color.WHITE);
	    p_south.setBackground(Color.WHITE);

	    p_center.add(p_center_user);
	    p_center.add(p_center_pass);
	    add(p_north, BorderLayout.NORTH);
	    add(p_center, BorderLayout.CENTER);
	    add(p_south, BorderLayout.SOUTH);
	    
	    setSize(300,270);
	    setVisible(true);
	    
	  }

	public void connectDB() {
		try {
			loginModel = new LoginModel();
			System.out.println("로그인 디비연결 성공");
		} catch (Exception e) {
			System.out.println("로그인 디비연결 실패 : " + e.getMessage());
			e.printStackTrace();
		}
	}

  public void eventProc() {
    
    bLogin.addActionListener(this);
    bCancel.addActionListener(this);
    user.addActionListener(this);
    pass.addActionListener(this);
    
    user.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Object evt = e.getSource();
        if(user.getText().equals("Username")) user.setText("");
      }
    });
    pass.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Object evt = e.getSource();
        if(pass.getText().equals("****")) pass.setText("");
      }
    });

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    
    Object evt = e.getSource();
    if (evt == bLogin) {
      list = loginModel.loginGrant(); // 보스님과 매니저에 해당하는 직원번호와 비밀번호를 가져옴
      this.login(list);
    } else if (evt == bCancel) {
      this.dispose();
    }
  }
  
  public void login(ArrayList list) {
	  
	  int[] empno = new int[list.size()/2];
	  int[] password = new int[list.size()/2];
	  int count = 0;
	  
	  // empno와 password에 직원 번호와 패스워드를 저장
	  for(int i=0; i<list.size()/2; i++) {
		  empno[i] = (int)list.get(count);
		  password[i] = (int)list.get(count+1);
		  count += 2;
	  
		  if(empno[i] == Integer.parseInt(user.getText())) {
			  if(password[i] == Integer.parseInt(pass.getText())) {
				  JOptionPane.showMessageDialog(null, "로그인 성공");
				  parent.Visible(true);
				  parent.setTabIndex(1);
				  order.bLogOut.setEnabled(true);
				  this.dispose();
				  break;
			  } else {
				  continue;
			  }
		  } else {
			  continue;
		  }
		  
	  }
	  
  }
  
}