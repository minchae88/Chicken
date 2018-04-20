package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.ChickenStore;
import model.DBCon;

public class LoginDialog extends JDialog implements ActionListener {
	  
	  JLabel title, userImage, passImage;
	  JTextField user;
	  JPasswordField pass;
	  JButton ok, cancle;
	  
	  Connection con;
	  ChickenStore parent;
	  
	  public LoginDialog(ChickenStore parent) throws Exception {
	    this.parent = parent;
	    addLayout(); 
	    eventProc(); 
	    con = DBCon.getConnection();
	    
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
	    
	  }
// 로그인 화면에서 유저번호와 비밀번호를 입력 후 ok 버튼을 눌렀을 때
public void login(String userNum, String passNum) {
  
  String sql = "SELECT empno, password FROM employee WHERE job = '보스님'";
  try {
    PreparedStatement ps = con.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    
      while(rs.next()) {
        if(userNum.equals(rs.getString("empno"))) {
          if(passNum.equals(rs.getString("password"))) {
            JOptionPane.showMessageDialog(null, "로그인 성공");
            parent.setTabIndex(1);
            parent.visible(true);
            this.dispose(); // JDialog를 메모리상에서 지움
          } else JOptionPane.showMessageDialog(null, "비밀번호 다름");
        } else JOptionPane.showMessageDialog(null, "허가되지 않은 아이디");
      }
      ps.close();
      rs.close();
  } catch (Exception e) {
    System.out.println("로그인 오류");
  }
  
} 

  public void eventProc() {
    
    ok.addActionListener(this);
    cancle.addActionListener(this);
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
    if (evt == ok) {
      login(user.getText(), pass.getText());
    } else if (evt == cancle) {
      this.dispose();
    }
  }
  
}
