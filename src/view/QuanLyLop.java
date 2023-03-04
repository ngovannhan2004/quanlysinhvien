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

import daos.LopHocDao;
import daos.NganhDao;
import daos.ThongBao;
import models.Lop;
import models.Nganh;

public class QuanLyLop extends JFrame {

	private Lop lop = new Lop();
	private Nganh nganh = new Nganh();
	protected static final Lop LOP = null;
	private DefaultTableModel defaultTableModel;
	private LopHocDao lopHocDao = new LopHocDao();
	private NganhDao nganhDao = new NganhDao();
	private JComboBox<Nganh> nganhComboBox = new JComboBox<Nganh>();
	private JTable table = new JTable();
	private Vector<Lop> lops;
	private Vector<Nganh> nganhs = new Vector<>();
	private DefaultComboBoxModel<Nganh> nganhModel;

	public QuanLyLop() throws HeadlessException {

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
		JLabel lableTittle = new JLabel("QUẢN LÝ LỚP");
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
		JLabel jlabelname = new JLabel(" Tên Lớp ");
		jlabelname.setFont(fontTt);
		JLabel jlabelidnganh = new JLabel(" id Ngành ");
		jlabelidnganh.setFont(fontTt);

		JTextFieldCustom tfId = new JTextFieldCustom();
		JTextFieldCustom tfName = new JTextFieldCustom();

		jpanelLeftContent.add(jlabelId);
		jpanelLeftContent.add(jlabelname);
		jpanelLeftContent.add(jlabelidnganh);

		JPanel paneTextField = new JPanel(new GridLayout(3, 1, 20, 35));
		paneTextField.setSize(300, 100);
		paneTextField.add(tfId);
		paneTextField.add(tfName);
		paneTextField.add(nganhComboBox);
		nganhComboBox.setModel(nganhModel);
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

				lop.setTenlop(tfName.getText());
				Nganh nganh = (Nganh) nganhComboBox.getSelectedItem();
				lop.setNganh(nganh);
				if (tienXuLyDuLieu(lop)) {
					try {
						ThongBao thongBao = lopHocDao.create(lop);
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
				lop.setId(Integer.parseInt(tfId.getText()));
				ThongBao thongBao = lopHocDao.delete(lop.getId());
				thongBaoTinNhan(thongBao);

			}
		});
		JButtonCustomFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] columns = { "id", "Tên Lớp", "id Ngành" };
				defaultTableModel = new DefaultTableModel(columns, 0);
				lop.setId(Integer.parseInt(tfId.getText()));

				Vector<Nganh> nganhs = new Vector<>();
				try {
					lop = lopHocDao.findOne(lop.getId());

					Object[] row = { lop.getId(), lop.getTenlop(), lop.getNganh().getId() };
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
				lop.setTenlop(tfName.getText());
				lop.setNganh((Nganh) nganhComboBox.getSelectedItem());
				if (tienXuLyDuLieu(lop)) {
					try {
						ThongBao thongBao = lopHocDao.update(lop.getId(), lop);
						thongBaoTinNhan(thongBao);
					} catch (Exception e2) {
						// TODO: handle exception
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

					Lop lop = lopHocDao.findOne(Integer.parseInt(id));
					tfId.setText(lop.getId() + "");
					tfName.setText(lop.getTenlop());
					nganhModel.setSelectedItem(lop.getNganh());
					nganhComboBox.setModel(nganhModel);
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
		Vector<Lop> lops = lopHocDao.findAll();
		Collections.sort(lops, new Comparator<Lop>() {
			public int compare(Lop lop1, Lop lop2) {
				return lop1.getTenlop().compareToIgnoreCase(lop2.getTenlop());
			}
		});
		layDuLieu(lops);
	}

	public void layDuLieu(Vector<Lop> lops) {
		String[] columns = { "id", "Tên Lớp", "Ngành" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		nganhModel = new DefaultComboBoxModel<>(nganhs);
		for (Lop lop : lops) {
			Object[] row = { lop.getId(), lop.getTenlop(), lop.getNganh().getTenNganh() };
			defaultTableModel.addRow(row);
		}
		table.setModel(defaultTableModel);
	}

	public void layDuLieu() {

		try {
			nganhs = lopHocDao.getAllNganh();
			lops = lopHocDao.findAll();

			layDuLieu(lops);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void thongBao(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public boolean tienXuLyDuLieu(Lop lop) {
		if (lop.getTenlop().isEmpty()) {
			thongBao("Chưa nhập tên lớp");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		new QuanLyLop();
	}
}
