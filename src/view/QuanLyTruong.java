package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import daos.ThongBao;
import daos.TruongDao;
import jdbc.DBConnect;
import models.Truong;

public class QuanLyTruong extends JFrame {
	JButton buttonBack;
	protected static final Truong TRUONG = null;
	private DefaultTableModel defaultTableModel;
	private JComboBox<String> JComboBox;
	public Connection connection = DBConnect.getConnection();
	TruongDao truongDao = new TruongDao();
	private JTable table = new JTable();
	Vector<Truong> truongs = new Vector<>();
	Truong truong = new Truong();

	public QuanLyTruong() throws HeadlessException {

		initUI();
	}

	public void initUI() {
		layDuLieu();
		this.setResizable(false);
		this.setSize(700, 340);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JPanel panelContainer = new JPanel(new BorderLayout());
		Font fontT = new Font("Tahoma", Font.BOLD, 25);

		JPanel panelTitle = new JPanel();
		JLabel lableTittle = new JLabel("QUẢN LÝ TRƯỜNG");
		panelTitle.add(lableTittle);
		lableTittle.setFont(fontT);
		lableTittle.setForeground(Color.red);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		// content
		JPanel jPanelW = new JPanel(new GridLayout(1, 3));
		JPanel jpanelLeftContent = new JPanel(new GridLayout(3, 1, 20, 35));
		// left
		Font fontTt = new Font("Tahoma", Font.BOLD, 13);
		JLabel jlabelId = new JLabel(" id ");
		jlabelId.setFont(fontTt);
		JLabel jlabelname = new JLabel(" Tên Trường ");
		jlabelname.setFont(fontTt);
		JLabel jlabelDiachi = new JLabel(" Địa Chỉ ");
		jlabelDiachi.setFont(fontTt);

		JTextField tfId = new JTextFieldCustom();
		JTextField tfName = new JTextFieldCustom();
		JTextField tfDiachi = new JTextFieldCustom();

		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelname);
		jpanelLeftContent.add(jlabelDiachi);

		JPanel paneTextField = new JPanel(new GridLayout(3, 1, 20, 35));
		paneTextField.setSize(300, 100);
		paneTextField.add(tfId);
		paneTextField.add(tfName);
		paneTextField.add(tfDiachi);

		/// right
		JPanel jPanelw = new JPanel();
		JPanel panelContent = new JPanel(new GridLayout());
		JScrollPane jpanelRightContent = new JScrollPane();
		jpanelRightContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		jpanelRightContent.setViewportView(table);
		table.setModel(defaultTableModel);
		table.setRowHeight(20);
		panelContent.add(jpanelRightContent);
		panelContainer.add(panelContent);

		/// action
		JPanel panelBottom = new JPanel(new GridLayout(2, 1));
		JPanel panelAction = new JPanel();
		JButton jButtonAdd = new JButtonCustom("Add");
		JButton jButtonDelete = new JButtonCustom("Delete");
		JButton jButtonFind = new JButtonCustom("Find");
		JButton jButtonUpdate = new JButtonCustom("Update");
		JButton jButtonRefresh = new JButtonCustom("Refresh");
		JButton sort = new JButtonCustom("Sort By Name");
		buttonBack = new JButtonCustom("Back");
		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MenuDashBoards();
			}
		});
		JPanel jPanelSearch = new JPanel(new FlowLayout());
		jPanelSearch.add(sort);
		jPanelSearch.add(buttonBack);
		panelAction.add(jButtonAdd);
		panelAction.add(jButtonDelete);
		panelAction.add(jButtonFind);
		panelAction.add(jButtonFind);
		panelAction.add(jButtonRefresh);
		panelAction.add(jButtonUpdate);
		panelBottom.add(jPanelSearch);
		panelBottom.add(panelAction);
		jButtonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				truong.setTenTruong(tfName.getText());

				truong.setDiaChi(tfDiachi.getText());
				if (tienXuLyDuLieu(truong)) {
					try {
						ThongBao thongBao = truongDao.create(truong);
						thongBaoTinNhan(thongBao);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}

			}
		});
		jButtonUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				truong.setId(Integer.parseInt(tfId.getText()));
				truong.setTenTruong(tfName.getText());
				truong.setDiaChi(tfDiachi.getText());
				try {
					ThongBao thongBao = truongDao.update(truong.getId(), truong);
					thongBaoTinNhan(thongBao);
					layDuLieu();
				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}

			}

		});
		jButtonDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				truong.setId(Integer.parseInt(tfId.getText()));
				ThongBao thongBao = truongDao.delete(truong.getId());
				thongBaoTinNhan(thongBao);
				layDuLieu();
			}
		});
		jButtonFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				truong.setId(Integer.parseInt(tfId.getText()));
				String[] columns = { "id", "Tên trường", "Địa chỉ" };
				defaultTableModel = new DefaultTableModel(columns, 0);
				Vector<Truong> truongs;

				try {
					truong = truongDao.findOne(truong.getId());
					Object[] row = { truong.getId(), truong.getTenTruong(), truong.getDiaChi() };
					defaultTableModel.addRow(row);
					table.setModel(defaultTableModel);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		jButtonRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					layDuLieu();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					sortByName();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				String id = (table.getModel().getValueAt(row, 0)).toString();
				try {
					Truong truong = truongDao.findOne(Integer.parseInt(id));
					tfId.setText(truong.getId() + "");
					tfName.setText(truong.getTenTruong());
					tfDiachi.setText(truong.getDiaChi());
				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
			}
		});
		jButtonUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					truong.setId(Integer.parseInt(tfId.getText()));
					truong.setTenTruong(tfName.getText());
					truong.setDiaChi(tfDiachi.getText());
					ThongBao thongBao = truongDao.update(truong.getId(), truong);
				} catch (Exception e2) {
					// TODO: handle exception
				}

				// TODO Auto-generated method stub

			}
		});
		jPanelW.add(jpanelLeftContent);
		jPanelW.add(paneTextField);
		panelContainer.add(jPanelW, BorderLayout.WEST);
		panelContainer.add(panelBottom, BorderLayout.SOUTH);
		this.add(panelContainer);
		this.setVisible(true);

	}

	public void thongBaoTinNhan(ThongBao thongBao) {
		if (thongBao.getKiemTra()) {
			this.layDuLieu();

		}
		JOptionPane.showMessageDialog(null, thongBao.getTinNhan());

	}

	public void sortByName() throws Exception {
		Vector<Truong> truongs = truongDao.findAll();
		Collections.sort(truongs, new Comparator<Truong>() {
			public int compare(Truong truong1, Truong truong2) {
				return truong1.getTenTruong().compareToIgnoreCase(truong2.getTenTruong());
			}
		});
		layDuLieu(truongs);
	}

	public void layDuLieu(Vector<Truong> truongs) {
		String[] columns = { "id", "Tên Trường", "Địa chỉ" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		for (Truong truong : truongs) {
			Object[] row = { truong.getId(), truong.getTenTruong(), truong.getDiaChi() };
			defaultTableModel.addRow(row);
		}

		table.setModel(defaultTableModel);
	}

	public void layDuLieu() {
		Vector<Truong> truongs;

		try {
			truongs = truongDao.findAll();
			layDuLieu(truongs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void thongBao(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public boolean tienXuLyDuLieu(Truong truong) {
		if (truong.getTenTruong().isEmpty()) {
			thongBao("Chưa nhập tên trường");
			return false;
		}
		if (truong.getDiaChi().isEmpty()) {
			thongBao("Chưa nhập địa chỉ");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		new QuanLyTruong();
	}
}
