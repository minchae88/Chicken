package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartPanel;

import chart.Chart;
import chart.DateLabelFormatter;

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
	JButton show = new JButton(getIcon("bSearch", 80, 40));
	JButton init = new JButton(getIcon("bReset", 80, 40));
	BorderFactory factory;

	ChartPanel panel;

	public SalesView(int width, int height) {
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
		// north
		JPanel p_north = new JPanel();
		// radio button
		JPanel p_north_upper = new JPanel();
		rbDate.setActionCommand("Date");
		rbMenu.setActionCommand("Menu");
		rbDaily.setActionCommand("Daily");
		rbMonthly.setActionCommand("Monthly");
		group1.add(rbMenu);
		group1.add(rbDate);
		group2.add(rbDaily);
		group2.add(rbMonthly);
		p_north_upper.add(rbMenu);
		p_north_upper.add(rbDate);
		p_north_upper.add(rbDaily);
		rbDaily.setVisible(false);
		p_north_upper.add(rbMonthly);
		rbMonthly.setVisible(false);

		// combobox
		comboBox.addItem("BarChart");
		comboBox.addItem("LineChart");
		p_north_upper.add(comboBox);

		// button
		JPanel p_north_lower = new JPanel();
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

		startModel.setDate(2017, 3, 1);
		endModel.setDate(2017, 3, 15);
		
		startDatePanel = new JDatePanelImpl(startModel, p);
		endDatePanel = new JDatePanelImpl(endModel, p);

		startDate = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
		endDate = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
		
		startModel.setSelected(true);
		endModel.setSelected(true);
		p_north_lower.add(startDate);
		p_north_lower.add(endDate);
		p_north_lower.add(show);
		p_north_lower.add(init);

		p_north.add(p_north_upper);
		p_north.add(p_north_lower);

		// center
		JPanel p_center = new JPanel(new BorderLayout());
		JButton salesInfo = new JButton(getIcon("laSalesInfo", 120, 60));
		salesInfo.setBorderPainted(false);
		salesInfo.setContentAreaFilled(false);

		p_center.add(salesInfo, BorderLayout.WEST);

		// south
		JPanel p_south = new JPanel();
		panel = new ChartPanel(null);
		p_south.add(panel);

		// color

		p_north.setBackground(Color.ORANGE);
		p_north_upper.setBackground(Color.ORANGE);
		p_north_lower.setBackground(Color.ORANGE);
		startDatePanel.setBackground(Color.ORANGE);
		endDatePanel.setBackground(Color.ORANGE);
		startDate.setBackground(Color.ORANGE);
		endDate.setBackground(Color.ORANGE);
		p_center.setBackground(Color.ORANGE);
		p_south.setBackground(Color.ORANGE);
		panel.setBackground(Color.ORANGE);

		rbDate.setBackground(Color.ORANGE);
		rbMenu.setBackground(Color.ORANGE);
		rbDaily.setBackground(Color.ORANGE);
		rbMonthly.setBackground(Color.ORANGE);
		comboBox.setBackground(Color.ORANGE);

		// size
		p_north_upper.setPreferredSize(new Dimension(width, (int) (height * 0.07)));
		p_north_lower.setPreferredSize(new Dimension(width, (int) (height * 0.09)));
		p_north.setPreferredSize(new Dimension(width, (int) (height * 0.2)));
		p_center.setPreferredSize(new Dimension(width, (int) (height * 0.1)));
		p_south.setSize(new Dimension(width, (int) (height * 0.6)));
		p_south.setPreferredSize(new Dimension(width, (int) (height * 0.6)));
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt = e.getSource();

		if (evt == rbMenu || evt == rbDate) {
			rbDaily.setVisible(true);
			rbMonthly.setVisible(true);
		} else if (evt == show) {
			String[] infom = { group1.getSelection().getActionCommand(), group2.getSelection().getActionCommand(),
					(String) comboBox.getSelectedItem() };
			panel.setChart(new Chart().getChart(infom, (Date) startDate.getModel().getValue(),
					(Date) endDate.getModel().getValue()));
		} else if (evt == init) {
			group1.clearSelection();
			group2.clearSelection();
			rbMonthly.setVisible(false);
			rbDaily.setVisible(false);
			comboBox.setSelectedIndex(0);
			panel.setChart(null);
		}
	}
}