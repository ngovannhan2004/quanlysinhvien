package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import daos.ThongKeDao;

public class chatr {
	private JButton buttonBack;

	public chatr() {
		init();
	}

	public void init() {
		JFreeChart pieChart = createPieChart(createDataset());
		ChartPanel pieChartPanel = new ChartPanel(pieChart);
		pieChartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

		JFreeChart barChart = createBarChart(createBarDataset());
		ChartPanel barChartPanel = new ChartPanel(barChart);
		barChartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new GridLayout(1, 2));

		buttonBack = new JButton("THOÁT");
		buttonBack.setBackground(new Color(255, 255, 255));
		buttonBack.setForeground(Color.BLACK);
		buttonBack.setBorderPainted(false);
		buttonBack.setFocusPainted(false);

		JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		panelBack.add(buttonBack);
		buttonBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Menu();

				// TODO Auto-generated method stub

			}

		});
		panel.add(pieChartPanel);
		panel.add(barChartPanel);

		frame.setTitle("Biểu đồ thể hiện số sinh viên của một trường");
		frame.add(panel);
		frame.add(panelBack, "South");
		frame.setSize(1000, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private static JFreeChart createPieChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("BIỂU ĐỒ TRÒN", dataset, true, true, true);
		return chart;
	}

	private static JFreeChart createBarChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ CỘT", "Trường", "Số Sinh Viên", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		return chart;
	}

	private static PieDataset createDataset() {
		Vector results = ThongKeDao.thongKeTruong();
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < results.size(); i++) {
			Vector elm = (Vector) results.get(i);
			String name = (String) elm.get(0);
			Double quatity = (Double) elm.get(1);

			dataset.setValue(name + " - " + Math.round(quatity), quatity);
		}
		return dataset;
	}

	private static CategoryDataset createBarDataset() {
		Vector results = ThongKeDao.thongKeTruong();
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < results.size(); i++) {
			Vector elm = (Vector) results.get(i);
			String name = (String) elm.get(0);
			Double quatity = (Double) elm.get(1);
			dataset.addValue(Math.round(quatity), "Số người", name);
		}

		return dataset;
	}

	public static void main(String[] args) {
		new chatr();
	}
}
