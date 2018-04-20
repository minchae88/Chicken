package chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class Chart {
	
	DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
	
	public Chart() {
		setData();
	}
	
	public void setData() {
		dataset1.addValue(1.0, "S1", "1월");
		dataset1.addValue(4.0, "S1", "2월");
		dataset1.addValue(3.0, "S1", "3월");
		dataset1.addValue(5.0, "S1", "4월");
		dataset1.addValue(5.0, "S1", "5월");
		dataset1.addValue(7.0, "S1", "6월");
		dataset1.addValue(7.0, "S1", "7월");
		dataset1.addValue(8.0, "S1", "8월");
		dataset1.addValue(9.0, "S1", "9월");
		dataset1.addValue(10.0, "S1", "10월");
		dataset1.addValue(11.0, "S1", "11월");
		dataset1.addValue(5.0, "S1", "12월");
	}
	
	
	public JFreeChart getChart(String kind) {
		
		BarRenderer renderer = new BarRenderer();
		LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
		ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);
		Font font = new Font("Gulim", Font.BOLD, 14);
		Font axisFont = new Font("Gulim", Font.PLAIN, 14);
		
		if(kind.equals("BarChart")) {
			renderer.setBaseItemLabelGenerator(generator);
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBasePositiveItemLabelPosition(p_center);
			renderer.setBaseItemLabelFont(font);
			renderer.setSeriesPaint(0, new Color(0, 162, 255));
			
		} 
		else if(kind.equals("LineChart")) {
			renderer2.setBaseItemLabelGenerator(generator);
			renderer2.setBaseItemLabelsVisible(true);
			renderer2.setBaseShapesVisible(true);
			renderer2.setDrawOutlines(true);
			renderer2.setUseFillPaint(true);
			renderer2.setBaseFillPaint(Color.WHITE);
			renderer2.setBaseItemLabelFont(font);
			renderer2.setBasePositiveItemLabelPosition(p_below);
			renderer2.setSeriesPaint(0,new Color(219,121,22));
			renderer2.setSeriesStroke(0,new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,3.0f));

		}
		
		CategoryPlot plot = new CategoryPlot();
		if(kind.equals("BarChart")) {
			plot.setDataset(dataset1);
			plot.setRenderer(renderer);
		}
		else if(kind.equals("LineChart")) {
			plot.setDataset(dataset1);
			plot.setRenderer(renderer2);
		}
		
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
		plot.setDomainAxis(new CategoryAxis());
		plot.getDomainAxis().setTickLabelFont(axisFont);
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		
		
		plot.setRangeAxis(new NumberAxis());
		plot.getRangeAxis().setTickLabelFont(axisFont);
		
		JFreeChart chart = new JFreeChart(plot);
		
		
		return chart;
	}
}
