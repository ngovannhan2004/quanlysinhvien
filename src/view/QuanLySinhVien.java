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
import javax.swing.ButtonGroup;
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

import daos.LopHocDao;
import daos.SinhVienDao;
import daos.ThongBao;
import models.Khoa;
import models.Lop;
import models.SinhVien;
import models.Truong;
import utils.Validate;

public class QuanLySinhVien extends JFrame {

	private Lop lop = new Lop();
	private SinhVien sinhVien = new SinhVien();
	protected static final SinhVien SINH_VIEN = null;
	private DefaultTableModel defaultTableModel;
	private LopHocDao lopHocDao = new LopHocDao();
	private SinhVienDao sinhVienDao = new SinhVienDao();
	private JComboBox<Lop> lopComboBox = new JComboBox<Lop>();
	private JTable table = new JTable();
	Vector<SinhVien> sinhviens;
	Vector<Lop> lops = new Vector<>();
	private DefaultComboBoxModel<Lop> lopModel;
	private Vector<SinhVien> sinhViens = new Vector<>();
	JTextFieldCustom tfId;
	JTextFieldCustom tfname;
	JTextFieldCustom tfmasv;
	JTextFieldCustom tflop;
	JTextFieldCustom tfEmail;
	JTextFieldCustom tfPhone;
	JTextFieldCustom tfDiachi;
	public static JLabel jLabelg;
	String[] dataGioiTinh = { "Nam", "Nữ" };

	public QuanLySinhVien() throws HeadlessException {

		initUI();
	}

	public void initUI() {

		layDuLieu();
		this.setSize(1250, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		JPanel panelContainer = new JPanel(new BorderLayout());
		Font fontT = new Font("Tahoma", Font.BOLD, 25);

		JPanel panelTitle = new JPanel();
		JLabel lableTittle = new JLabel("QUẢN LÝ  SINH VIÊN");
		panelTitle.add(lableTittle);
		lableTittle.setFont(fontT);
		lableTittle.setForeground(Color.red);
		panelContainer.add(panelTitle, BorderLayout.NORTH);

		// content
		JPanel jPanelW = new JPanel(new GridLayout(1, 3));
		JPanel jpanelLeftContent = new JPanel(new GridLayout(8, 1, 10, 10));
		// left
		Font fontTt = new Font("Tahoma", Font.BOLD, 16);
		JLabel jlabelId = new JLabel(" id ");
		jlabelId.setFont(fontTt);
		JLabel jlabelmasv = new JLabel(" Mã Sinh Viên ");
		jlabelmasv.setFont(fontTt);
		JLabel jlabelname = new JLabel(" Tên Sinh Viên ");
		jlabelname.setFont(fontTt);
		JLabel jlabelGender = new JLabel(" Giới Tính ");
		jlabelGender.setFont(fontTt);

		JLabel jlabelmail = new JLabel(" Email ");
		jlabelmail.setFont(fontTt);
		JLabel jlabeldt = new JLabel(" Điện Thọai ");
		jlabeldt.setFont(fontTt);
		JLabel jlabeldiachi = new JLabel(" Địa Chỉ ");
		jlabeldiachi.setFont(fontTt);
		JLabel jlabelidlop = new JLabel(" id lớp ");
		jlabelidlop.setFont(fontTt);
		JPanel panelGender = new JPanel(new FlowLayout());

		JComboBox<String> comboBoxGender = new JComboBox<String>(dataGioiTinh);
		ButtonGroup buttonGroup = new ButtonGroup();

		JTextFieldCustom tfId = new JTextFieldCustom();
		JTextFieldCustom tfmasv = new JTextFieldCustom();
		JTextFieldCustom tfname = new JTextFieldCustom();

		JTextFieldCustom tfEmail = new JTextFieldCustom();
		JTextFieldCustom tfPhone = new JTextFieldCustom();
		JTextFieldCustom tfDiachi = new JTextFieldCustom();

		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelmasv);
		jpanelLeftContent.add(jlabelname);
		jpanelLeftContent.add(jlabelGender);

		jpanelLeftContent.add(jlabelmail);
		jpanelLeftContent.add(jlabeldt);
		jpanelLeftContent.add(jlabeldiachi);
		jpanelLeftContent.add(jlabelidlop);
		JPanel paneTextField = new JPanel(new GridLayout(8, 1, 10, 10));
		paneTextField.add(tfId);
		paneTextField.add(tfmasv);
		paneTextField.add(tfname);
		paneTextField.add(comboBoxGender);

		paneTextField.add(tfEmail);
		paneTextField.add(tfPhone);
		paneTextField.add(tfDiachi);
		paneTextField.add(lopComboBox);
		lopComboBox.setModel(lopModel);
		/// right
		JPanel jPanelw = new JPanel();
		JPanel panelContent = new JPanel(new GridLayout());
		JScrollPane jpanelRightContent = new JScrollPane();
		jpanelRightContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		jpanelRightContent.setViewportView(table);
		table.setModel(defaultTableModel);
		table.setRowHeight(40);
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

				sinhVien.setMasinhvien(tfmasv.getText());
				sinhVien.setTensinhvien(tfname.getText());
				sinhVien.setEmail(tfEmail.getText());
				sinhVien.setGender(comboBoxGender.getSelectedIndex() > 0 ? 0 : 1);
				sinhVien.setDienthoai(tfPhone.getText());
				sinhVien.setDiachi(tfDiachi.getText());
				Lop lop = (Lop) lopComboBox.getSelectedItem();
				sinhVien.setLop(lop);
				if (tienXuLyDuLieu(sinhVien)) {
					try {
						ThongBao thongBao = sinhVienDao.create(sinhVien);
						thongBaoTinNhan(thongBao);
					} catch (Exception e2) {
						e2.printStackTrace();
						// TODO: handle exception
					}
				}

			}
		});
		JButtonCustomDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				sinhVien.setId(Integer.parseInt(tfId.getText()));
				ThongBao thongBao = sinhVienDao.delete(sinhVien.getId());
				thongBaoTinNhan(thongBao);

			}
		});
		JButtonCustomFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] columns = { "id", "Mã Sinh Viên", "Tên Sinh Viên", "Giới Tính", "Email", "Điện Thọai",
						"Địa Chỉ", "lớp" };
				defaultTableModel = new DefaultTableModel(columns, 0);
				Vector<SinhVien> sinhviens;
				Vector<Lop> lops = new Vector<>();
				try {
					sinhviens = sinhVienDao.findAll();
					lops = sinhVienDao.getAllLop();
					lopComboBox = new JComboBox<Lop>(lops);
					for (SinhVien sinhVien : sinhviens) {
						Object[] row = { sinhVien.getId(), sinhVien.getMasinhvien(), sinhVien.getTensinhvien(),
								sinhVien.getGender() > 0 ? dataGioiTinh[0] : dataGioiTinh[1],
								sinhVien.getLop().getTenlop(), sinhVien.getEmail(), sinhVien.getDienthoai(),
								sinhVien.getDiachi() };
						defaultTableModel.addRow(row);
					}
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

				sinhVien.setId(Integer.parseInt(tfId.getText()));
				sinhVien.setLop((Lop) lopComboBox.getSelectedItem());
				sinhVien.setTensinhvien(tfname.getText());
				sinhVien.setMasinhvien(tfmasv.getText());
				sinhVien.setGender(comboBoxGender.getSelectedIndex() > 0 ? 0 : 1);
				sinhVien.setEmail(tfEmail.getText());
				sinhVien.setDienthoai(tfPhone.getText());
				sinhVien.setDiachi(tfDiachi.getText());
				if (tienXuLyDuLieu(sinhVien)) {
					try {
						ThongBao thongBao = sinhVienDao.update(sinhVien.getId(), sinhVien);
						thongBaoTinNhan(thongBao);
						layDuLieu();
					} catch (Exception e2) {
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
					SinhVien sinhVien = sinhVienDao.findOne(Integer.parseInt(id));
					tfId.setText(sinhVien.getId() + "");
					tfmasv.setText(sinhVien.getMasinhvien());
					tfname.setText(sinhVien.getTensinhvien());
					comboBoxGender.setSelectedItem(sinhVien.getGender() > 0 ? dataGioiTinh[0] : dataGioiTinh[1]);
					tfEmail.setText(sinhVien.getEmail());
					tfPhone.setText(sinhVien.getDienthoai());
					tfDiachi.setText(sinhVien.getDiachi());
					lopModel.setSelectedItem(sinhVien.getLop());
					lopComboBox.setModel(lopModel);
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
		Vector<SinhVien> sinhViens = sinhVienDao.findAll();
		Collections.sort(sinhviens, new Comparator<SinhVien>() {
			public int compare(SinhVien sinhVien1, SinhVien sinhVien2) {
				return sinhVien1.getTensinhvien().compareToIgnoreCase(sinhVien2.getTensinhvien());
			}
		});
		layDuLieu(sinhviens);
	}

	public void layDuLieu(Vector<SinhVien> sinhViens) {
		String[] columns = { "id", "Mã Sinh Viên", "Tên Sinh Viên", "Giới Tính", "Email", "Điện Thoại", "Địa Chỉ",
				"Lớp" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		lopModel = new DefaultComboBoxModel<>(lops);
		for (SinhVien sinhVien : sinhviens) {
			Object[] row = { sinhVien.getId(), sinhVien.getMasinhvien(), sinhVien.getTensinhvien(),
					(sinhVien.getGender() > 0 ? dataGioiTinh[0] : dataGioiTinh[1]), sinhVien.getEmail(),
					sinhVien.getDienthoai(), sinhVien.getDiachi(), sinhVien.getLop().getTenlop() };
			defaultTableModel.addRow(row);
		}
		table.setModel(defaultTableModel);
	}

	public void layDuLieu() {

		try {
			lops = sinhVienDao.getAllLop();
			sinhviens = sinhVienDao.findAll();
			layDuLieu(sinhViens);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void thongBao(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public boolean tienXuLyDuLieu(SinhVien sinhVien) {
		if (sinhVien.getTensinhvien().isEmpty()) {
			thongBao("Chưa nhập tên sinh viên");
			return false;
		}
		if (sinhVien.getEmail().isEmpty()) {
			thongBao("Chưa nhập email");
			return false;
		}
		if (sinhVien.getDiachi().isEmpty()) {
			thongBao("Chưa nhập địa chỉ");
			return false;
		}
		if (sinhVien.getDienthoai().isEmpty()) {
			thongBao("Chưa nhập điện thoại");
			return false;
		}
		if (sinhVien.getMasinhvien().isEmpty()) {
			thongBao("Chưa nhập mã sinh viên");
			return false;
		}
		if (String.valueOf(sinhVien.getGender()).isEmpty()) {
			thongBao("Chưa chọn giới tính");
			return false;
		}
		if (!Validate.isEmailValid(sinhVien.getEmail())) {
			thongBao("Email không hợp lệ");
			return false;
		}

		if (!Validate.isPhoneValid(sinhVien.getDienthoai())) {
			thongBao("Số điện thoại không hợp lệ");
			return false;
		}

		if (!Validate.isMaSinhVienValid(sinhVien.getMasinhvien())) {
			thongBao("Mã sinh viên không hợp lệ");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		new QuanLySinhVien();
	}

}
