package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import daos.NguoiDungDao;
import daos.ThongBao;
import models.Khoa;
import models.NguoiDung;

class JTextFieldCustom extends JTextField {

	public JTextFieldCustom() {
		super();
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	}

}

class JPasswordCustom extends JPasswordField {

	public JPasswordCustom() {
		super();
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

}

class JButtonCustom extends JButton {

	public JButtonCustom(String string) {
		super(string);
		this.setBackground(new Color(20, 123, 123));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setForeground(Color.white);
	}

}

public class Login extends JFrame {

	private JTextField textFieldTen;
	private JPasswordField passwordField;
	private NguoiDungDao nguoiDungDao = new NguoiDungDao();
	private NguoiDung nguoiDung = new NguoiDung();

	public Login() {
		initUI();
	}

	private void initUI() {
		this.setSize(460, 400);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		JPanel panelContainer = new JPanel(new BorderLayout());
		JPanel panelTitle = new JPanel();
		JLabel labelTitle = new JLabel("ĐĂNG NHẬP");
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelTitle.setForeground(Color.RED);
		panelTitle.add(labelTitle);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		JPanel panelContent = new JPanel(new GridLayout(0, 1, 3, 4));
		JLabel labelTen = new JLabel("Tên Đăng Nhập:");
		labelTen.setFont(new Font("Tahoma", Font.BOLD, 10));
		JLabel labelMatKhau = new JLabel("Mật Khẩu:");
		labelMatKhau.setFont(new Font("Tahoma", Font.BOLD, 10));

		textFieldTen = new JTextFieldCustom();
		passwordField = new JPasswordCustom();
		panelContent.add(labelTen);
		panelContent.add(textFieldTen);
		panelContent.add(labelMatKhau);
		panelContent.add(passwordField);
		panelContent.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		JButton buttonDangNhap = new JButtonCustom("Đăng nhập");
		JButton buttonDangKi = new JButtonCustom("Đăng kí");
		JLabel panelQuenMatKhau = new JLabel("Quên mật khẩu ?");
		panelQuenMatKhau.setForeground(Color.red);
		JPanel panelAction = new JPanel(new GridLayout(1, 1, 2, 0));
		panelAction.add(buttonDangNhap);
		panelAction.add(buttonDangKi);
		panelContent.add(panelQuenMatKhau);
		panelAction.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		panelContent.add(panelAction);
		this.add(panelContainer, BorderLayout.NORTH);
		this.add(panelContent, BorderLayout.SOUTH);
		this.setVisible(true);
		panelQuenMatKhau.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new GetCodeForgotPass();
			}
		});
		buttonDangKi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new DangKi();
			}
		});
		buttonDangNhap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nguoiDung.setTendangnhap(textFieldTen.getText());
				nguoiDung.setMatkhau(passwordField.getText());
				if (tienXuLyDuLieu(nguoiDung)) {
					ThongBao thongBao = nguoiDungDao.dangnhap(nguoiDung.getTendangnhap(), nguoiDung.getMatkhau());
					thongBaoTinNhan(thongBao);
					if (thongBao.getKiemTra() == true) {
						dispose();
						new MenuDashBoards();
					}
				}

			}
		});
	}

	public void thongBaoTinNhan(ThongBao thongBao) {
		if (thongBao.getKiemTra()) {

		}
		JOptionPane.showMessageDialog(null, thongBao.getTinNhan());
	}

	public void thongBao(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public boolean tienXuLyDuLieu(NguoiDung nguoiDung) {
		if (nguoiDung.getTendangnhap().isEmpty()) {
			thongBao("Chưa nhập tên đăng nhập");
			return false;
		}
		if (nguoiDung.getMatkhau().isEmpty()) {
			thongBao("Chưa nhập mật khẩu");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		new Login();
	}

}
