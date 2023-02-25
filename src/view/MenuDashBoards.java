package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class JButtonCustom extends JButton {

	public JButtonCustom(String text) {
		super(text);
		this.setPreferredSize(new Dimension(100, 3));
		this.setFont(new Font("Roboto", Font.PLAIN, 15));
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground(new Color(0, 128, 130));
		this.setForeground(Color.WHITE);
		this.setBorder(null);
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
	}

}

public class MenuDashBoards extends JFrame {
	JButton jButtonQLT;
	JButton jButtonQLK;
	JButton jButtonQLN;
	JButton jButtonQLL;
	JButton jButtonQLSV;
	JButton jButtonQLMH;
	String id;
	JButton buttonBack;

	public void MenuDashBoards() {

	}

	public MenuDashBoards() {
		initUI();
	}

	private void initUI() {

		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JPanel panelContainer = new JPanel(new BorderLayout());
		panelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		Font fontT = new Font("Tahoma", Font.BOLD, 25);

		JPanel panelTitle = new JPanel();
		JLabel lableTittle = new JLabel("MENU");
		panelTitle.add(lableTittle);
		lableTittle.setFont(fontT);
		lableTittle.setForeground(Color.red);
		panelContainer.add(panelTitle, BorderLayout.NORTH);
		JPanel panelBottom = new JPanel(new GridLayout(2, 3, 10, 10));

		JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		jButtonQLT = new JButtonCustom("Quản Lý Trường");
		jButtonQLK = new JButtonCustom("Quản Lý Khoa");
		jButtonQLN = new JButtonCustom("Quản Lý Ngành");
		jButtonQLL = new JButtonCustom("Quản Lý Lớp");
		jButtonQLSV = new JButtonCustom("Quản Lý Sinh Viên");
		jButtonQLMH = new JButtonCustom("Quản Lý Môn Học");
		buttonBack = new JButton("THOÁT");
		buttonBack.setBackground(new Color(255, 255, 255));
		buttonBack.setForeground(Color.BLACK);
		buttonBack.setBorderPainted(false);
		buttonBack.setFocusPainted(false);

		panelBack.add(buttonBack);

		buttonBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();

				// TODO Auto-generated method stub

			}
		});
		panelBottom.add(jButtonQLT);
		panelBottom.add(jButtonQLK);
		panelBottom.add(jButtonQLN);
		panelBottom.add(jButtonQLL);
		panelBottom.add(jButtonQLSV);
		panelBottom.add(jButtonQLMH);
		panelContainer.add(panelBottom, BorderLayout.CENTER);
		panelContainer.add(panelBack, BorderLayout.SOUTH);
		this.add(panelContainer);
		this.setVisible(true);
		addevent();
	}

	public void addevent() {
		jButtonQLT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new QuanLyTruong();
			}
		});
		jButtonQLK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new QuanLyKhoa();
			}
		});
		jButtonQLN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new QuanLyNganh();
			}
		});
		jButtonQLL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new QuanLyLop();
			}
		});
		jButtonQLSV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new QuanLySinhVien();
			}
		});
		jButtonQLMH.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new QuanLyMonHoc();
			}
		});
	}

	public static void main(String[] args) {
		new MenuDashBoards();
	}

}
