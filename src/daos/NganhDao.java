package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import jdbc.DBConnect;
import models.Khoa;
import models.Lop;
import models.Nganh;
import models.SinhVien;
import models.Truong;

public class NganhDao implements DaoInterface<Nganh> {
	public String tableName = "Nganh";
	public String queryCreate = "INSERT INTO " + tableName + "(tennganh,idkhoa) VALUES(?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET `tennganh` = ?" + ", idkhoa=? WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	public String queryRefresh = "delete * from " + tableName;
	public Connection connection = DBConnect.connection;
	private KhoaDao khoaDao = new KhoaDao();

	public Vector<Khoa> getAllKhoa() throws Exception {
		return khoaDao.findAll();
	}

	@Override
	public ThongBao create(Nganh duLieu) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement stm = connection.prepareStatement(queryCreate);
		stm.setString(1, duLieu.getTenNganh());
		stm.setInt(2, duLieu.getKhoa().getId());
		stm.executeUpdate();
		return new ThongBao("Thêm Thành công ", true);

	}

	@Override
	public ThongBao update(int id, Nganh duLieu) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stm = connection.prepareStatement(queryUpdate);
			stm.setString(1, duLieu.getTenNganh());
			stm.setInt(2, duLieu.getKhoa().getId());
			stm.setInt(3, id);
			stm.executeUpdate();
			return new ThongBao("Câp Nhật Thành Công  ", true);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ThongBao delete(int id) {
		// TODO Auto-generated method stub
		try {
			Nganh nganh = this.findOne(id);
			if (nganh == null) {
				return new ThongBao("Nganh Không Tồn Tại", false);
			}
			PreparedStatement stm = connection.prepareStatement(queryXoa);
			stm.setInt(1, id);
			stm.executeUpdate();
			return new ThongBao("Xóa Thành công ngành", true);

		} catch (Exception e) {
			return new ThongBao("Không thể xóa ngành này", false);
			// TODO: handle exception
		}

	}

	@Override
	public Nganh findOne(int id) {
		// TODO Auto-generated method stub
		try {
			KhoaDao khoaDao = new KhoaDao();
			PreparedStatement stm = connection.prepareStatement(queryFindOne);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				Khoa khoa = khoaDao.findOne(rs.getInt("idkhoa"));
				return new Nganh(rs.getInt(1), rs.getString(2), khoa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Vector<Nganh> findAll() throws Exception {
		// TODO Auto-generated method stub
		Vector<Nganh> nganhs = new Vector<>();
		KhoaDao khoaDao = new KhoaDao();
		PreparedStatement stm = connection.prepareStatement(queryFindAll);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			Khoa khoa = khoaDao.findOne(rs.getInt("idkhoa"));
			nganhs.add(new Nganh(rs.getInt(1), rs.getString(2), khoa));
		}
		return nganhs;
	}

	@Override
	public Vector<Nganh> findBy(String key, String data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
