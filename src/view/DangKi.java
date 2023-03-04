package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import models.NguoiDung;
import utils.Validate;

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

public class DangKi extends JFrame {

	private JTextField textFieldTen;
	private JTextField textFieldNLMK;
	private JTextField textFieldEmail;
	private JTextField textFieldten;
	private JPasswordField passwordField;
	private NguoiDungDao nguoiDungDao = new NguoiDungDao();
	private NguoiDung nguoiDung = new NguoiDung();

	public DangKi() {
		initUI();
	}

	private void initUI() {
		this.setSize(400, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JPanel panelContainer = new JPanel(new BorderLayout());
		JPanel panelTitle = new JPanel();
		JLabel labelTitle = new JLabel("ĐĂNG KÍ");
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelTitle.setForeground(Color.RED);
		panelTitle.add(labelTitle);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		JPanel panelContent = new JPanel(new GridLayout(0, 1, 3, 4));
		JLabel labelTen = new JLabel("Tên Đăng Nhập:");
		labelTen.setFont(new Font("Tahoma", Font.BOLD, 10));

		JLabel labelEmail = new JLabel("Email:");
		labelEmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		JLabel labelMatKhau = new JLabel("Mật Khẩu:");
		labelMatKhau.setFont(new Font("Tahoma", Font.BOLD, 10));
		JLabel labelNLMK = new JLabel("Nhập Lại Mật Khẩu:");
		labelNLMK.setFont(new Font("Tahoma", Font.BOLD, 10));
		JLabel labelten = new JLabel("Tên Người Dùng:");
		labelMatKhau.setFont(new Font("Tahoma", Font.BOLD, 10));

		textFieldTen = new JTextFieldCustom();
		textFieldNLMK = new JPasswordCustom();
		textFieldEmail = new JTextFieldCustom();
		textFieldten = new JTextFieldCustom();
		passwordField = new JPasswordCustom();
		panelContent.add(labelTen);
		panelContent.add(textFieldTen);

		panelContent.add(labelEmail);
		panelContent.add(textFieldEmail);
		panelContent.add(labelten);
		panelContent.add(textFieldten);
		panelContent.add(labelMatKhau);
		panelContent.add(passwordField);
		panelContent.add(labelNLMK);
		panelContent.add(textFieldNLMK);
		panelContent.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		JButton buttonDangKy = new JButtonCustom("Đăng ký");
		JButton buttonDangNhap = new JButtonCustom("Đăng Nhập");
		JPanel panelAction = new JPanel(new GridLayout(1, 1, 2, 0));
		panelAction.add(buttonDangKy);
		panelAction.add(buttonDangNhap);
		panelAction.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		panelContent.add(panelAction);

		this.add(panelContainer, BorderLayout.NORTH);
		this.add(panelContent, BorderLayout.SOUTH);
		this.setVisible(true);

		buttonDangNhap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new Login();

			}
		});
		buttonDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nguoiDung.setTendangnhap(textFieldTen.getText());
				nguoiDung.setEmail(textFieldEmail.getText());
				nguoiDung.setMatkhau(passwordField.getText());
				nguoiDung.setTen(textFieldten.getText());
				if (tienXuLyDuLieu(nguoiDung)) {
					ThongBao thongBao = nguoiDungDao.dangki(nguoiDung);
					thongBaoTinNhan(thongBao);

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
		if (nguoiDung.getEmail().isEmpty()) {
			thongBao("Chưa nhập email");
			return false;
		}
		if (nguoiDung.getTen().isEmpty()) {
			thongBao("Chưa nhập tên người dùng");
			return false;
		}
		if (!Validate.isEmailValid(nguoiDung.getEmail())) {
			thongBao("Email không hợp lệ");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		new DangKi();
	}
}