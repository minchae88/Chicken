package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;

import chart.Chart;

public class SalesView extends JPanel implements ActionListener{
	int width, height;
	
	JRadioButton rbMenu = new JRadioButton("메뉴별");
	JRadioButton rbDate = new JRadioButton("날짜별");
	JRadioButton rbDaily = new JRadioButton("일별");
	JRadioButton rbMonthly = new JRadioButton("월별");
	
	ButtonGroup group1 = new ButtonGroup();
	ButtonGroup group2 = new ButtonGroup();
	
	JComboBox<String> comboBox = new JComboBox<>();
	
	JButton show = new JButton("조회");
	JButton init = new JButton("초기화");
	
	ChartPanel panel;
	
	
	public SalesView(int width, int height) {
		this.width = (int)(width*0.92);
		this.height = (int)(height*0.82);
		addLayout();
		eventProc();
	}
	
	private void addLayout() {
		JPanel p_north = new JPanel();
		p_north.setLayout(new FlowLayout());
		group1.add(rbMenu); group1.add(rbDate);
		group2.add(rbDaily); group2.add(rbMonthly);
		p_north.add(rbMenu); p_north.add(rbDate);
		p_north.add(rbDaily); rbDaily.setVisible(false);
		p_north.add(rbMonthly); rbMonthly.setVisible(false);
		
		comboBox.addItem("BarChart");
		comboBox.addItem("LineChart");
		p_north.add(comboBox);
		
		p_north.add(show); p_north.add(init);
		
		JPanel p_south = new JPanel();
		panel = new ChartPanel(null);
		p_south.setBorder(new TitledBorder("매출정보"));
		p_south.add(panel);
		
		p_south.setPreferredSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width-20, height-40));
		setLayout(new BorderLayout());
		add(p_north, BorderLayout.NORTH);
		add(p_south, BorderLayout.CENTER);
		
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
		
		if(evt == rbMenu || evt == rbDate) {
			rbDaily.setVisible(true);
			rbMonthly.setVisible(true);
	 	} else if(evt == show) {
	 		panel.setChart(new Chart().getChart((String)comboBox.getSelectedItem()));
	 	} else if(evt == init) {
	 		group1.clearSelection();
	 		group2.clearSelection();
	 		rbMonthly.setVisible(false);rbDaily.setVisible(false);
	 		comboBox.setSelectedIndex(0);
	 		panel.setChart(null);
	 	}
	}
	
	private void connectDB() {
		
	}
}
