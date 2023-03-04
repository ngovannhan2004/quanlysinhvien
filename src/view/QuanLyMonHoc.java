package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.table.TableModel;

import daos.MonHocDao;
import daos.ThongBao;
import jdbc.DBConnect;
import models.Khoa;
import models.MonHoc;
import models.SinhVien;
import models.Truong;

public class QuanLyMonHoc extends JFrame {
	private SinhVien sinhVien = new SinhVien();
	private MonHoc monHoc = new MonHoc();
	private DefaultTableModel defaultTableModel = new DefaultTableModel();
	private MonHocDao monHocDao = new MonHocDao();
	private Vector<SinhVien> sinhViens = new Vector<>();
	private JComboBox<SinhVien> lopComboBox = new JComboBox<SinhVien>();
	private JTable table = new JTable();
	private Vector<MonHoc> monhocs;
	private DefaultComboBoxModel<SinhVien> sinhVienModel;

	public QuanLyMonHoc() {
		initUI();
	}

	public void initUI() {
		layDuLieu();
		this.setSize(600, 340);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		JPanel panelContainer = new JPanel(new BorderLayout());
		Font fontT = new Font("Tahoma", Font.BOLD, 20);

		JPanel panelTitle = new JPanel();
		JLabel lableTittle = new JLabel("QUẢN LÝ MÔN HỌC");
		panelTitle.add(lableTittle);
		lableTittle.setFont(fontT);
		lableTittle.setForeground(Color.red);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		// content
		JPanel jPanelW = new JPanel(new GridLayout(1, 3));
		JPanel jpanelLeftContent = new JPanel(new GridLayout(3, 1, 25, 35));
		// left
		Font fontTt = new Font("Tahoma", Font.BOLD, 16);
		JLabel jlabelId = new JLabel(" id ");
		jlabelId.setFont(fontTt);
		JLabel jlabelname = new JLabel(" Tên môn học ");
		jlabelname.setFont(fontTt);
		JLabel jlabelidSv = new JLabel(" Sinh Viên ");
		jlabelidSv.setFont(fontTt);

		JTextFieldCustom tfId = new JTextFieldCustom();
		JTextFieldCustom tfName = new JTextFieldCustom();

		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelname);
		jpanelLeftContent.add(jlabelidSv);

		JPanel paneTextField = new JPanel(new GridLayout(3, 1, 25, 35));

		paneTextField.add(tfId);
		paneTextField.add(tfName);
		paneTextField.add(lopComboBox);
		lopComboBox.setModel(sinhVienModel);
		/// right
		JPanel jPanelw = new JPanel();
		JPanel panelContent = new JPanel(new GridLayout());
		JScrollPane jpanelRightContent = new JScrollPane();
		jpanelRightContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		jpanelRightContent.setViewportView(table);
		table.setModel(defaultTableModel);
		table.setRowHeight(30);

		panelContent.add(jpanelRightContent);
		panelContainer.add(panelContent);

		/// action
		JPanel panelBottom = new JPanel(new GridLayout(2, 1));
		JPanel panelAction = new JPanel();
		JButtonCustom JButtonCustomAdd = new JButtonCustom("Thêm");
		JButtonCustom JButtonCustomDelete = new JButtonCustom("Xóa");
		JButtonCustom JButtonCustomFind = new JButtonCustom("Tìm Kiếm");
		JButtonCustom JButtonCustomUpdate = new JButtonCustom("Sửa");
		JButtonCustom JButtonCustomRefresh = new JButtonCustom("Làm Mới");
		JButtonCustom sort = new JButtonCustom("Sắp Xếp");
		JButtonCustom buttonBack = new JButtonCustom("Thoát");
		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Menu();
			}
		});
		JButtonCustomAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				monHoc.setTenMonHoc(tfName.getText());
				SinhVien sinhVien = (SinhVien) lopComboBox.getSelectedItem();
				monHoc.setSinhVien(sinhVien);
				if (tienXuLyDuLieu(monHoc)) {
					try {
						ThongBao thongBao = monHocDao.create(monHoc);
						thongBaoTinNhan(thongBao);
						thongBaoTinNhan(thongBao);
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}

			}
		});
		JButtonCustomDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				monHoc.setId(Integer.parseInt(tfId.getText()));
				ThongBao thongBao = monHocDao.delete(monHoc.getId());
				thongBaoTinNhan(thongBao);

			}
		});
		JButtonCustomFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				monHoc.setId(Integer.parseInt(tfId.getText()));
				String[] columns = { "id", "Tên môn học", "Sinh viên" };
				defaultTableModel = new DefaultTableModel(columns, 0);
				Vector<MonHoc> monhocs;
				Vector<SinhVien> sinhViens = new Vector<>();
				try {
					monHoc = monHocDao.findOne(monHoc.getId());
					Object[] row = { monHoc.getId(), monHoc.getTenMonHoc(), monHoc.getSinhVien().getTensinhvien() };
					defaultTableModel.addRow(row);
					table.setModel(defaultTableModel);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		JButtonCustomUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				monHoc.setId(Integer.parseInt(tfId.getText()));
				monHoc.setTenMonHoc(tfName.getText());
				monHoc.setSinhVien((SinhVien) lopComboBox.getSelectedItem());
				if (tienXuLyDuLieu(monHoc)) {
					try {
						ThongBao thongBao = monHocDao.update(monHoc.getId(), monHoc);
						thongBaoTinNhan(thongBao);
						layDuLieu();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}

			}

		});
		JButtonCustomRefresh.addActionListener(new ActionListener() {

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

					MonHoc monHoc = monHocDao.findOne(Integer.parseInt(id));
					tfId.setText(monHoc.getId() + "");
					tfName.setText(monHoc.getTenMonHoc());
					sinhVienModel.setSelectedItem(monHoc.getSinhVien());
					lopComboBox.setModel(sinhVienModel);
				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
			}
		});

		JPanel jPanelSearch = new JPanel(new FlowLayout());
		jPanelSearch.add(sort);
		jPanelSearch.add(buttonBack);
		panelAction.add(JButtonCustomAdd);
		panelAction.add(JButtonCustomDelete);
		panelAction.add(JButtonCustomFind);
		panelAction.add(JButtonCustomFind);
		panelAction.add(JButtonCustomRefresh);
		panelAction.add(JButtonCustomUpdate);
		panelBottom.add(jPanelSearch);
		panelBottom.add(panelAction);
		//
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
		Vector<MonHoc> monHocs = monHocDao.findAll();
		Collections.sort(monHocs, new Comparator<MonHoc>() {
			public int compare(MonHoc monHoc1, MonHoc monHoc2) {
				return monHoc1.getTenMonHoc().compareToIgnoreCase(monHoc2.getTenMonHoc());
			}
		});
		System.out.println(monHocs);
		layDuLieu(monHocs);
	}

	public void layDuLieu(Vector<MonHoc> monHocs) {
		String[] columns = { "id", "Tên môn học", "Sinh Viên" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		sinhVienModel = new DefaultComboBoxModel<>(sinhViens);
		for (MonHoc monHoc : monHocs) {
			Object[] row = { monHoc.getId(), monHoc.getTenMonHoc(), monHoc.getSinhVien().getTensinhvien() };
			defaultTableModel.addRow(row);
		}
		table.setModel(defaultTableModel);
	}

	public void layDuLieu() {
		try {
			sinhViens = monHocDao.getAllSinhvien();
			monhocs = monHocDao.findAll();
			layDuLieu(monhocs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void thongBao(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public boolean tienXuLyDuLieu(MonHoc monHoc) {
		if (monHoc.getTenMonHoc().isEmpty()) {
			thongBao("Chưa nhập tên môn học");
			return false;
		}
		if (String.valueOf(monHoc.getSinhVien().getId()).isEmpty()) {
			thongBao("Chưa chọn sinh viên");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		new QuanLyMonHoc();
	}
}
