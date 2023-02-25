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

import daos.KhoiPhucMatKhauDao;
import daos.NguoiDungDao;
import daos.ThongBao;
import models.NguoiDung;

public class KhoiPhucMatKhau extends JFrame {

	private JPasswordField textFieldMKM;
	private JTextField textFieldEmail;
	private JTextField textFieldMK;
	private JTextField textFieldMa;
	private KhoiPhucMatKhauDao khoiPhucMatKhauDao = new KhoiPhucMatKhauDao();
	private String email;

	public KhoiPhucMatKhau(String email) {
		this.email = email;
		initUI();
	}

	private void initUI() {
		this.setSize(400, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JPanel panelContainer = new JPanel(new BorderLayout());
		JPanel panelTitle = new JPanel();
		JLabel labelTitle = new JLabel("Khôi Phục Mật Khẩu");
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelTitle.setForeground(Color.RED);
		panelTitle.add(labelTitle);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		JPanel panelContent = new JPanel(new GridLayout(0, 1, 3, 4));

		JLabel labelEmail = new JLabel("Email:");
		JLabel labelMKM = new JLabel("Mật khẩu Mới:");
		labelEmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		JLabel labelMK = new JLabel("Xác Nhận Mật Khẩu:");
		labelEmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		JLabel labelMa = new JLabel("Mã Xác Nhận:");
		labelEmail.setFont(new Font("Tahoma", Font.BOLD, 10));

		textFieldMKM = new JPasswordCustom();
		textFieldMK = new JPasswordCustom();
		textFieldEmail = new JTextFieldCustom();
		textFieldEmail.setText(email);
		textFieldEmail.disable();

		textFieldMa = new JTextFieldCustom();
		panelContent.add(labelEmail);
		panelContent.add(textFieldEmail);
		panelContent.add(labelMa);
		panelContent.add(textFieldMa);
		panelContent.add(labelMKM);
		panelContent.add(textFieldMKM);
		panelContent.add(labelMK);
		panelContent.add(textFieldMK);
		panelContent.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
		JButton buttonOK = new JButtonCustom("OK");
		JButton buttonCanel = new JButtonCustom("Canel");
		JPanel panelAction = new JPanel(new GridLayout(1, 1, 2, 0));
		panelAction.add(buttonOK);
		panelAction.add(buttonCanel);
		panelAction.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		panelContent.add(panelAction);

		this.add(panelContainer, BorderLayout.NORTH);
		this.add(panelContent, BorderLayout.SOUTH);
		this.setVisible(true);
		buttonCanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new Login();
			}
		});
		buttonOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (textFieldMKM.getText().equals(textFieldMK.getText())) {
					try {
						ThongBao thongBao = khoiPhucMatKhauDao.updatePassword(textFieldEmail.getText(),
								textFieldMa.getText(), textFieldMKM.getText());
						thongBaoTinNhan(thongBao);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				} else {
					JOptionPane.showMessageDialog(null, "Mật Khẩu Không Trùng Khớp Vui Lòng Nhập Lại!");
				}
			}
		});
	}
	public void thongBaoTinNhan(ThongBao thongBao) {
		if (thongBao.getKiemTra()) {
		}
		JOptionPane.showMessageDialog(null, thongBao.getTinNhan());
	}

}
