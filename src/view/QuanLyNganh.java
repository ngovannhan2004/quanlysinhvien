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
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import daos.KhoaDao;
import daos.NganhDao;
import daos.ThongBao;
import models.Khoa;
import models.Nganh;

public class QuanLyNganh extends JFrame {

	private Nganh nganh = new Nganh();
	private Khoa khoa = new Khoa();
	protected static final Nganh NGANH = null;
	private DefaultTableModel defaultTableModel;
	private KhoaDao khoaDao = new KhoaDao();
	private NganhDao nganhDao = new NganhDao();
	Vector<Nganh> nganhs;
	private Vector<Khoa> khoas = new Vector<>();
	private JComboBox<Khoa> khoaComboBox = new JComboBox<Khoa>();
	private JTable table = new JTable();
	private DefaultComboBoxModel<Khoa> khoaModel;

	public QuanLyNganh() throws HeadlessException {

		initUI();
	}

	public void initUI() {
		layDuLieu();
		this.setSize(1000, 340);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		JPanel panelContainer = new JPanel(new BorderLayout());
		Font fontT = new Font("Tahoma", Font.BOLD, 20);

		JPanel panelTitle = new JPanel();
		JLabel lableTittle = new JLabel("QUẢN LÝ NGÀNH");
		panelTitle.add(lableTittle);
		lableTittle.setFont(fontT);
		lableTittle.setForeground(Color.red);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		// content
		JPanel jPanelW = new JPanel(new GridLayout(1, 3));
		JPanel jpanelLeftContent = new JPanel(new GridLayout(3, 1, 20, 35));
		// left
		Font fontTt = new Font("Tahoma", Font.BOLD, 16);
		JLabel jlabelId = new JLabel(" id ");
		jlabelId.setFont(fontTt);
		JLabel jlabelname = new JLabel(" Tên ngành ");
		jlabelname.setFont(fontTt);
		JLabel jlabelidnganh = new JLabel("Khoa ");
		jlabelidnganh.setFont(fontTt);

		JTextFieldCustom tfId = new JTextFieldCustom();
		JTextFieldCustom tfName = new JTextFieldCustom();

		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelname);
		jpanelLeftContent.add(jlabelidnganh);

		JPanel paneTextField = new JPanel(new GridLayout(3, 1, 20, 35));
		paneTextField.add(tfId);
		paneTextField.add(tfName);
		paneTextField.add(khoaComboBox);
		khoaComboBox.setModel(khoaModel);

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

				nganh.setTenNganh(tfName.getText());
				Khoa khoa = (Khoa) khoaComboBox.getSelectedItem();
				nganh.setKhoa(khoa);
				if (tienXuLyDuLieu(nganh)) {
					try {
						ThongBao thongBao = nganhDao.create(nganh);
						thongBaoTinNhan(thongBao);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}

			}
		});
		JButtonCustomDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nganh.setId(Integer.parseInt(tfId.getText()));
				ThongBao thongBao = nganhDao.delete(nganh.getId());
				thongBaoTinNhan(thongBao);
			}
		});
		JButtonCustomFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] columns = { "id", "Tên Ngành", "id khoa " };
				defaultTableModel = new DefaultTableModel(columns, 0);
				nganh = new Nganh();
				try {
					nganh.setId(Integer.parseInt(tfId.getText()));
					Vector<Nganh> nganhs;
					Vector<Khoa> khoas = new Vector<>();
					nganh = nganhDao.findOne(nganh.getId());
					if (nganh != null) {
						Object[] row = { nganh.getId(), nganh.getTenNganh(), nganh.getKhoa().getId() };
						defaultTableModel.addRow(row);
						table.setModel(defaultTableModel);
					} else {
						table.setModel(new DefaultTableModel(columns, 0));
					}

				} catch (Exception e1) {
					// TODO: handle exception
					table.setModel(new DefaultTableModel(columns, 0));
				}

			}
		});
		JButtonCustomUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Nganh nganh = new Nganh();
				nganh.setId(Integer.parseInt(tfId.getText()));
				nganh.setTenNganh(tfName.getText());
				nganh.setKhoa((Khoa) khoaModel.getSelectedItem());
				if (tienXuLyDuLieu(nganh)) {
					try {
						ThongBao thongBao = nganhDao.update(nganh.getId(), nganh);
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
					Nganh nganh = nganhDao.findOne(Integer.parseInt(id));
					tfId.setText(nganh.getId() + "");
					tfName.setText(nganh.getTenNganh());
					khoaModel.setSelectedItem(nganh.getKhoa());
					khoaComboBox.setModel(khoaModel);

				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
			}
		});

//
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
		Vector<Nganh> nganhs = nganhDao.findAll();
		Collections.sort(nganhs, new Comparator<Nganh>() {
			public int compare(Nganh nganh1, Nganh nganh2) {
				return nganh1.getTenNganh().compareToIgnoreCase(nganh2.getTenNganh());
			}
		});
		layDuLieu(nganhs);
	}

	public void layDuLieu(Vector<Nganh> nganhs) {
		String[] columns = { "id", "Tên Ngành", "Tên Khoa" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		khoaModel = new DefaultComboBoxModel<>(khoas);
		for (Nganh nganh : nganhs) {
			Object[] row = { nganh.getId(), nganh.getTenNganh(), nganh.getKhoa().getTenkhoa() };
			defaultTableModel.addRow(row);
		}
		table.setModel(defaultTableModel);
	}

	public void layDuLieu() {

		try {
			nganhs = nganhDao.findAll();
			khoas = nganhDao.getAllKhoa();
			layDuLieu(nganhs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void thongBao(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public boolean tienXuLyDuLieu(Nganh nganh) {
		if (nganh.getTenNganh().isEmpty()) {
			thongBao("Chưa nhập tên ngành");
			return false;
		}
		if (String.valueOf(nganh.getKhoa().getId()).isEmpty()) {
			thongBao("Chưa chọn Khoa");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		new QuanLyNganh();
	}

}