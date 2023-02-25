package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import daos.KhoiPhucMatKhauDao;
import daos.NguoiDungDao;
import daos.ThongBao;
import models.KhoiPhucMatKhau;
import models.NguoiDung;
import models.ThongBaoData;
import utils.MailUtil;
import utils.SendEmail;

public class GetCodeForgotPass extends JFrame {
	private JFrame frame = new JFrame();
	private JTextField textFieldMK;
	private MailUtil mailUtil = new MailUtil();
	private KhoiPhucMatKhauDao khoiPhucMatKhauDao = new KhoiPhucMatKhauDao();
	public GetCodeForgotPass() {
		initUI();
	}

	private void initUI() {
		this.setSize(400, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JPanel panelContainer = new JPanel(new BorderLayout());
		JPanel panelTitle = new JPanel();
		JLabel labelTitle = new JLabel("Nhập Email");
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelTitle.setForeground(Color.RED);
		panelTitle.add(labelTitle);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		JPanel panelContent = new JPanel(new GridLayout(0, 1, 3, 4));
		JLabel labelTen = new JLabel("Nhập Email Của Bạn:");
		labelTen.setFont(new Font("Tahoma", Font.BOLD, 10));

		textFieldMK = new JTextFieldCustom();

		panelContent.add(labelTen);
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
				ThongBaoData<KhoiPhucMatKhau> thongBaoData = khoiPhucMatKhauDao
						.createOrUpdateFunction(new KhoiPhucMatKhau(0, textFieldMK.getText(), null));
				thongBaoTinNhan(thongBaoData);
			}
		});

	}

	public void thongBaoTinNhan(ThongBao thongBao) {
		if (thongBao.getKiemTra()) {

		}
		JOptionPane.showMessageDialog(null, thongBao.getTinNhan());
	}

	public void thongBaoTinNhan(ThongBaoData<KhoiPhucMatKhau> thongBao) {
		if (thongBao.getKiemTra() == false) {
			JOptionPane.showMessageDialog(null, thongBao.getTinNhan());
		} else {
			// send Mail
			SendEmail sendEmail = new SendEmail(textFieldMK.getText(), "", thongBao.getData().getCode(),
					"Xin Chào");
			sendEmail.start();
			Timer timer = new Timer();
			JOptionPane.showMessageDialog(null, "Mã của bạn đã được gửi về email của bạn");
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// Task to be executed after timeout
					new view.KhoiPhucMatKhau(thongBao.getData().getEmail());
				}
			}, 3000);

		}

	}
	public static void main(String[] args) {
		new GetCodeForgotPass();
	}
}
