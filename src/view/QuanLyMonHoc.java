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
	private JComboBox<SinhVien> jComboBox = new JComboBox<SinhVien>();
	private JTable table = new JTable();
	private Vector<MonHoc> monhocs;

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
		JLabel jlabelname = new JLabel(" Tenmonhoc ");
		jlabelname.setFont(fontTt);
		JLabel jlabelidSv = new JLabel(" idSinhVien ");
		jlabelidSv.setFont(fontTt);

		JTextField tfId = new JTextField();
		JTextField tfname = new JTextField();

		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelname);
		jpanelLeftContent.add(jlabelidSv);

		JPanel paneTextField = new JPanel(new GridLayout(3, 1, 25, 35));

		paneTextField.add(tfId);
		paneTextField.add(tfname);
		paneTextField.add(jComboBox);

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
		JButton jButtonAdd = new JButton("Add");
		JButton jButtonDelete = new JButton("Delete");
		JButton jButtonFind = new JButton("Find");
		JButton jButtonUpdate = new JButton("Update");
		JButton jButtonRefresh = new JButton("Refresh");
		JButton sort = new JButton("Sort By Name");
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MenuDashBoards();
			}
		});
		jButtonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				monHoc.setTenMonHoc(tfname.getText());
				SinhVien sinhVien = (SinhVien) jComboBox.getSelectedItem();
				monHoc.setSinhVien(sinhVien);
				ThongBao thongBao = monHocDao.create(monHoc);
				thongBaoTinNhan(thongBao);
			}
		});
		jButtonDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				monHoc.setId(Integer.parseInt(tfId.getText()));
				ThongBao thongBao = monHocDao.delete(monHoc.getId());
				thongBaoTinNhan(thongBao);

			}
		});
		jButtonFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				monHoc.setId(Integer.parseInt(tfId.getText()));
				String[] columns = { "id", "Tenmonhoc", "idSinhvien" };
				defaultTableModel = new DefaultTableModel(columns, 0);
				Vector<MonHoc> monhocs;
				Vector<SinhVien> sinhViens = new Vector<>();
				try {
					monHoc = monHocDao.findOne(monHoc.getId());

					Object[] row = { monHoc.getId(), monHoc.getTenMonHoc(), monHoc.getSinhVien().getId() };
					defaultTableModel.addRow(row);

					table.setModel(defaultTableModel);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		jButtonUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				monHoc.setId(Integer.parseInt(tfId.getText()));
				monHoc.setTenMonHoc(tfname.getText());
				monHoc.setSinhVien((SinhVien) jComboBox.getSelectedItem());
				try {
					ThongBao thongBao = monHocDao.update(monHoc.getId(), monHoc);
					thongBaoTinNhan(thongBao);
				} catch (Exception e2) {
					// TODO: handle exception
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
				TableModel tableModel = table.getModel();
				ComboBoxModel<SinhVien> comboBoxModel = jComboBox.getModel();
				int idSinhVien = (int) tableModel.getValueAt(row, 2);
				SinhVien sinhVien = timKiemSinhVienById(idSinhVien);
				comboBoxModel.setSelectedItem(sinhVien);
				jComboBox.setModel(comboBoxModel);
				try {

					MonHoc monHoc = monHocDao.findOne(Integer.parseInt(id));
					tfId.setText(monHoc.getId() + "");
					tfname.setText(monHoc.getTenMonHoc());
				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
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
		layDuLieu(monhocs);
	}

	public void layDuLieu(Vector<MonHoc> monHocs) {
		String[] columns = { "id", "Tenmonhoc", "idSinhvien" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		for (MonHoc monHoc : monhocs) {
			Object[] row = { monHoc.getId(), monHoc.getTenMonHoc(), monHoc.getSinhVien().getId() };
			defaultTableModel.addRow(row);
		}
		table.setModel(defaultTableModel);
	}

	public void layDuLieu() {
		try {
			sinhViens = monHocDao.getAllSinhvien();
			jComboBox = new JComboBox<SinhVien>(sinhViens);
			monhocs = monHocDao.findAll();

			layDuLieu(monhocs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public SinhVien timKiemSinhVienById(int id) {

		for (SinhVien sinhVien : sinhViens) {
			if (sinhVien.getId() == id)
				return sinhVien;
		}
		return null;

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
