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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import daos.KhoaDao;
import daos.ThongBao;
import daos.TruongDao;
import models.Khoa;
import models.Truong;
import utils.Validate;

public class QuanLyKhoa extends JFrame {
	protected static final Khoa Khoa = null;
	private DefaultTableModel defaultTableModel;
	private KhoaDao khoaDao = new KhoaDao();
	private TruongDao truongDao = new TruongDao();
	private JComboBox<Truong> truongComboBox = new JComboBox<Truong>();
	Vector<Truong> truongs = new Vector<>();
	private JTable table = new JTable();
	private DefaultComboBoxModel<Truong> truongModel;

	public QuanLyKhoa() throws HeadlessException {
		initUI();
	}

	public void initUI() {
		layDuLieu();
		this.setSize(800, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JPanel panelContainer = new JPanel(new BorderLayout());
		Font fontT = new Font("Tahoma", Font.BOLD, 25);

		JPanel panelTitle = new JPanel();
		JLabel lableTittle = new JLabel("QUẢN LÝ KHOA");
		panelTitle.add(lableTittle);
		lableTittle.setFont(fontT);
		lableTittle.setForeground(Color.red);
		panelContainer.add(panelTitle, BorderLayout.NORTH);
		// content
		JPanel jPanelW = new JPanel(new GridLayout(1, 3));
		JPanel jpanelLeftContent = new JPanel(new GridLayout(4, 1, 20, 25));
		// left

		Font fontTt = new Font("Tahoma", Font.BOLD, 16);
		JLabel jlabelId = new JLabel(" id ");
		jlabelId.setFont(fontTt);
		JLabel jlabelname = new JLabel(" Tên Khoa ");
		jlabelname.setFont(fontTt);
		JLabel jlabelDT = new JLabel("Điện Thoại");
		jlabelDT.setFont(fontTt);
		JLabel jlabelidTruong = new JLabel(" id Trường ");
		jlabelidTruong.setFont(fontTt);

		JTextFieldCustom tfId = new JTextFieldCustom();
		JTextFieldCustom tfName = new JTextFieldCustom();
		JTextFieldCustom tfPhone = new JTextFieldCustom();
		JTextFieldCustom tfTruong = new JTextFieldCustom();

		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelname);
		jpanelLeftContent.add(jlabelDT);
		jpanelLeftContent.add(jlabelidTruong);

		JPanel paneTextField = new JPanel(new GridLayout(4, 1, 15, 25));

		paneTextField.add(tfId);
		paneTextField.add(tfName);
		paneTextField.add(tfPhone);
		paneTextField.add(truongComboBox);
		truongComboBox.setModel(truongModel);

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
		JPanel jPanelSearch = new JPanel(new FlowLayout());
		jPanelSearch.add(sort);
		jPanelSearch.add(buttonBack);
		panelAction.add(JButtonCustomAdd);
		panelAction.add(JButtonCustomDelete);
		panelAction.add(JButtonCustomFind);
		panelAction.add(JButtonCustomRefresh);
		panelAction.add(JButtonCustomUpdate);
		panelBottom.add(jPanelSearch);
		panelBottom.add(panelAction);
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
				Khoa khoa = new Khoa();
				khoa.setTenkhoa(tfName.getText());
				khoa.setDienThoai(tfPhone.getText());
				Truong truong = (Truong) truongComboBox.getSelectedItem();
				khoa.setTruong(truong);
				if (tienXuLyDuLieu(khoa)) {
					try {
						ThongBao thongBao = khoaDao.create(khoa);
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
				Khoa khoa = new Khoa();
				khoa.setId(Integer.parseInt(tfId.getText()));
				ThongBao thongBao = khoaDao.delete(khoa.getId());
				thongBaoTinNhan(thongBao);
			}
		});
		JButtonCustomFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Khoa khoa = new Khoa();
				khoa.setId(Integer.parseInt(tfId.getText()));
				String[] columns = { "id", "TenKhoa", "dienthoai", "idTruong" };
				defaultTableModel = new DefaultTableModel(columns, 0);
				Vector<Khoa> khoas;
				Vector<Truong> truongs = new Vector<>();
				try {
					khoa = khoaDao.findOne(khoa.getId());
					Object[] row = { khoa.getId(), khoa.getTenkhoa(), khoa.getDienThoai(), khoa.getTruong().getId() };
					defaultTableModel.addRow(row);
					table.setModel(defaultTableModel);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		JButtonCustomUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Khoa khoa = new Khoa();
				khoa.setId(Integer.parseInt(tfId.getText()));
				khoa.setTenkhoa(tfName.getText());
				khoa.setDienThoai(tfPhone.getText());
				khoa.setTruong((Truong) truongComboBox.getSelectedItem());
				if (tienXuLyDuLieu(khoa)) {
					try {
						ThongBao thongBao = khoaDao.update(khoa.getId(), khoa);
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
					Khoa khoa = khoaDao.findOne(Integer.parseInt(id));
					tfId.setText(khoa.getId() + "");
					tfName.setText(khoa.getTenkhoa());
					tfPhone.setText(khoa.getDienThoai());
					truongModel.setSelectedItem(khoa.getTruong());
					truongComboBox.setModel(truongModel);

				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
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
		Vector<Khoa> khoas = khoaDao.findAll();
		Collections.sort(khoas, new Comparator<Khoa>() {
			public int compare(Khoa khoa1, Khoa khoa2) {
				return khoa1.getTenkhoa().compareToIgnoreCase(khoa2.getTenkhoa());
			}
		});
		layDuLieu(khoas);
	}

	public void layDuLieu(Vector<Khoa> khoas) {
		String[] columns = { "id", "Tên Khoa ", "Điện Thoại", "Trường" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		truongModel = new DefaultComboBoxModel<>(truongs);
		for (Khoa khoa : khoas) {
			Object[] row = { khoa.getId(), khoa.getTenkhoa(), khoa.getDienThoai(), khoa.getTruong().getTenTruong() };
			defaultTableModel.addRow(row);
		}
		table.setModel(defaultTableModel);
	}

	public void layDuLieu() {
		Vector<Khoa> khoas;
		try {
			truongs = khoaDao.getAllTruong();
			khoas = khoaDao.findAll();
			layDuLieu(khoas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void thongBao(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public boolean tienXuLyDuLieu(Khoa khoa) {
		if (khoa.getTenkhoa().isEmpty()) {
			thongBao("Chưa nhập tên khoa");
			return false;
		}
		if (khoa.getDienThoai().isEmpty()) {
			thongBao("Chưa nhập điện thoại");
			return false;
		}
		if (String.valueOf(khoa.getTruong().getId()).isEmpty()) {
			thongBao("Chưa chọn Trường");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		new QuanLyKhoa();
	}

}
