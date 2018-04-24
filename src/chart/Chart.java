package chart;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.ArrayList;

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

import model.SalesModel;

public class Chart {

	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	public Chart() {

	}

	public void setData(String rb1, String rb2, Date startDate, Date endDate) {
		SalesModel model;

		try {
			model = new SalesModel();
			ArrayList<ArrayList<String>> list = rb1.equals("Date") ? model.getDateSales(rb2, startDate, endDate)
					: model.getMenuSales(rb2, startDate, endDate);
			if (list.get(0).size() == 3) {
				for (ArrayList<String> ar : list) {
					dataset.addValue(Integer.parseInt(ar.get(1)), ar.get(0), ar.get(2));
				}
			} else {
				for (ArrayList<String> ar : list)
					dataset.addValue(Integer.parseInt(ar.get(1)), String.valueOf(ar.get(0)), String.valueOf(ar.get(0)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public JFreeChart getChart(String[] data, Date startDate, Date endDate) {
		setData(data[0], data[1], startDate, endDate);
		String kind = data[2];
		BarRenderer renderer = new BarRenderer();
		LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
		ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);
		Font font = new Font("Gulim", Font.BOLD, 14);
		Font axisFont = new Font("Gulim", Font.PLAIN, 14);

		if (kind.equals("BarChart")) {
			renderer.setBaseItemLabelGenerator(generator);
			renderer.setBaseItemLabelsVisible(false);
			renderer.setBasePositiveItemLabelPosition(p_center);
			renderer.setBaseItemLabelFont(font);
			renderer.setSeriesPaint(0, new Color(0, 162, 255));

		} else if (kind.equals("LineChart")) {
			renderer2.setBaseItemLabelGenerator(generator);
			renderer2.setBaseItemLabelsVisible(false);
			renderer2.setBaseShapesVisible(true);
			renderer2.setDrawOutlines(true);
			renderer2.setUseFillPaint(true);
			renderer2.setBaseFillPaint(Color.WHITE);
			renderer2.setBaseItemLabelFont(font);
			renderer2.setBasePositiveItemLabelPosition(p_below);
			renderer2.setSeriesPaint(0, new Color(219, 121, 22));
			renderer2.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 3.0f));

		}

		CategoryPlot plot = new CategoryPlot();
		if (kind.equals("BarChart")) {
			plot.setDataset(dataset);
			plot.setRenderer(renderer);
		} else if (kind.equals("LineChart")) {
			plot.setDataset(dataset);
			plot.setRenderer(renderer2);
		}

		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		plot.setDomainAxis(new CategoryAxis());
		plot.getDomainAxis().setTickLabelFont(axisFont);
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		plot.getDomainAxis().setVisible(false);

		plot.setRangeAxis(new NumberAxis());
		plot.getRangeAxis().setTickLabelFont(axisFont);
		
		JFreeChart chart = new JFreeChart(plot);

		return chart;
	}
}
