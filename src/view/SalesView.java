package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartPanel;

import chart.Chart;
import chart.DateLabelFormatter;
import main.ChickenStore;

public class SalesView extends JPanel implements ActionListener {
	int width, height;

	JRadioButton rbMenu = new JRadioButton("메뉴별");
	JRadioButton rbDate = new JRadioButton("날짜별");
	JRadioButton rbDaily = new JRadioButton("일별");
	JRadioButton rbMonthly = new JRadioButton("월별");

	ButtonGroup group1 = new ButtonGroup();
	ButtonGroup group2 = new ButtonGroup();

	UtilDateModel startModel = new UtilDateModel();
	UtilDateModel endModel = new UtilDateModel();
	Properties p = new Properties();
	JDatePanelImpl startDatePanel;
	JDatePanelImpl endDatePanel;
	JDatePickerImpl startDate;
	JDatePickerImpl endDate;

	JComboBox<String> comboBox = new JComboBox<>();

	JButton show;
	JButton init;
	JButton bLogOut;

	JLabel laChickenStore = new JLabel(getIcon("laChickenStore", 350, 100));
	JLabel laTel = new JLabel(getIcon("laTel", 200, 60));
	JLabel laNothing = new JLabel(getIcon("laNothing", 200, 60));

	BorderFactory factory;
	ChartPanel panel;
	ChickenStore parents;
	OrderView order;

	Border emptyBorder = BorderFactory.createEmptyBorder();

	public SalesView(ChickenStore parents, OrderView order, int width, int height) {
		this.parents = parents;
		this.order = order;
		this.width = (int) (width * 0.9);
		this.height = (int) (height * 0.9);
		addLayout();
		eventProc();
	}

	// 이미지 사이즈 조절하는 메소드당당당
	public ImageIcon getIcon(String name, int width, int height) {
		return new ImageIcon(new ImageIcon("src\\view\\chickimg\\" + name + ".png").getImage().getScaledInstance(width,
				height, Image.SCALE_DEFAULT));
	}

	private void addLayout() {

		show = new JButton(getIcon("bSearch", 100, 40));
		show.setBorder(emptyBorder);
		show.setBorderPainted(false);
		show.setContentAreaFilled(false);
		init = new JButton(getIcon("bReset", 100, 40));
		init.setBorder(emptyBorder);
		init.setBorderPainted(false);
		init.setContentAreaFilled(false);
		bLogOut = new JButton(getIcon("bLogOut", 100, 40));
		bLogOut.setBorder(emptyBorder);
		bLogOut.setBorderPainted(false);
		bLogOut.setContentAreaFilled(false);
// north
		JPanel p_north = new JPanel();
		p_north.setLayout(new GridLayout(1, 3));
		p_north.add(laNothing);
		p_north.add(laChickenStore);
		p_north.add(laTel);

	// center
		JPanel p_south = new JPanel();
		panel = new ChartPanel(new Chart().getChart(new String[] {"Menu", "Daily", "BarChart"}, new Date(), new Date()));
		p_south.add(panel);

	// south
		JPanel p_center = new JPanel();
		// radio button
		JPanel p_center_upper = new JPanel();
		rbDate.setActionCommand("Date");
		rbMenu.setActionCommand("Menu");
		rbDaily.setActionCommand("Daily");
		rbMonthly.setActionCommand("Monthly");
		group1.add(rbMenu);
		group1.add(rbDate);
		group2.add(rbDaily);
		group2.add(rbMonthly);
		p_center_upper.add(rbMenu);
		p_center_upper.add(rbDate);
		p_center_upper.add(rbDaily);
		rbDaily.setVisible(false);
		p_center_upper.add(rbMonthly);
		rbMonthly.setVisible(false);

		// combobox
		comboBox.addItem("BarChart");
		comboBox.addItem("LineChart");

		p_center_upper.add(comboBox);

		// button
		JPanel p_center_lower = new JPanel();

		show.setContentAreaFilled(false);
		show.setBorder(BorderFactory.createEmptyBorder());
		show.setBorderPainted(false);
		init.setContentAreaFilled(false);
		init.setBorder(BorderFactory.createEmptyBorder());
		init.setBorderPainted(false);

		// calendar
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		startModel.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DATE));
		endModel.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DATE));

		startDatePanel = new JDatePanelImpl(startModel, p);
		endDatePanel = new JDatePanelImpl(endModel, p);

		startDate = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
		endDate = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

		startModel.setSelected(true);
		endModel.setSelected(true);

		p_center_lower.add(startDate);
		p_center_lower.add(endDate);
		p_center_lower.add(show);
		p_center_lower.add(init);
		p_center_lower.add(bLogOut);
		p_center.add(p_center_upper);
		p_center.add(p_center_lower);

		// color
		p_north.setBackground(Color.WHITE);
		startDatePanel.setBackground(Color.WHITE);
		endDatePanel.setBackground(Color.WHITE);
		startDate.setBackground(Color.WHITE);
		endDate.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		p_south.setBackground(Color.WHITE);
		p_center_upper.setBackground(Color.WHITE);
		p_center_lower.setBackground(Color.WHITE);
		panel.setBackground(Color.WHITE);

		rbDate.setBackground(Color.WHITE);
		rbMenu.setBackground(Color.WHITE);
		rbDaily.setBackground(Color.WHITE);
		rbMonthly.setBackground(Color.WHITE);
		comboBox.setBackground(Color.WHITE);

		// size
		p_center_upper.setPreferredSize(new Dimension(width, (int) (height * 0.07)));
		p_center_lower.setPreferredSize(new Dimension(width, (int) (height * 0.09)));
		p_center.setPreferredSize(new Dimension(width, (int) (height * 0.2)));
		p_south.setSize(new Dimension(width, (int) (height * 0.6)));
		p_south.setPreferredSize(new Dimension(width, (int) (height * 0.55)));
		panel.setPreferredSize(new Dimension(p_south.getWidth(), p_south.getHeight() - 40));

		// add
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);
	}

	private void eventProc() {
		rbMenu.addActionListener(this);
		rbDate.addActionListener(this);
		rbDaily.addActionListener(this);
		rbMonthly.addActionListener(this);
		show.addActionListener(this);
		init.addActionListener(this);
		bLogOut.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();

		if (evt == rbMenu || evt == rbDate) {
			rbDaily.setVisible(true);
			rbMonthly.setVisible(true);
		} else if (evt == show) {
			try {
				String[] infom = { group1.getSelection().getActionCommand(), group2.getSelection().getActionCommand(),
						(String) comboBox.getSelectedItem() };
				panel.setChart(new Chart().getChart(infom, (Date) startDate.getModel().getValue(),
						(Date) endDate.getModel().getValue()));				
			} catch (NullPointerException exception) {
				JOptionPane.showMessageDialog(null, "옵션을 넣으세욥");
			}
		} else if (evt == init) {
			group1.clearSelection();
			group2.clearSelection();
			rbMonthly.setVisible(false);
			rbDaily.setVisible(false);
			comboBox.setSelectedIndex(0);
			panel.setChart(null);
		} else if (evt == bLogOut) {
			logout();
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

}