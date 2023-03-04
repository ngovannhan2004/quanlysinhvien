package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import jdbc.DBConnect;
import models.Khoa;
import models.Lop;
import models.MonHoc;
import models.Nganh;
import models.SinhVien;
import models.Truong;

public class MonHocDao implements DaoInterface<MonHoc> {
	public String tableName = "MonHoc";
	public String queryCreate = "INSERT INTO " + tableName + " (tenmonhoc,idsinhVien)VALUES(?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET tenmonhoc = ?" + ", idsinhvien=? WHERE id = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;

	public Connection connection = DBConnect.connection;
	private SinhVienDao sinhVienDao = new SinhVienDao();

	public Vector<SinhVien> getAllSinhvien() throws Exception {
		return sinhVienDao.findAll();
	}

	@Override
	public ThongBao create(MonHoc duLieu) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stm = connection.prepareStatement(queryCreate);
			stm.setString(1, duLieu.getTenMonHoc());
			stm.setInt(2, duLieu.getSinhVien().getId());
			stm.executeUpdate();
			return new ThongBao("Thêm Thành công ", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ThongBao("Thêm Không Thành công ", false);
		}
		

	}

	@Override
	public ThongBao update(int id, MonHoc duLieu) throws Exception {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stm = connection.prepareStatement(queryUpdate);
			
			stm.setString(1, duLieu.getTenMonHoc());
			stm.setInt(2, duLieu.getSinhVien().getId());
			stm.setInt(3, id);
			stm.executeUpdate();
			return new ThongBao("Câp Nhật Thành Công  ", true);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ThongBao("Câp Nhật Không Thành Công  ", false);
		}
		
	}

	@Override
	public ThongBao delete(int id) {
		try {
			PreparedStatement stm = connection.prepareStatement(queryXoa);
			stm.setInt(1, id);
			stm.executeUpdate();
			return new ThongBao("Xóa Thành công  ", true);
		} catch (Exception e) {
			// TODO: handle exception
			return new ThongBao("Không thể xóa lớp này", false);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public MonHoc findOne(int id) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement stm = connection.prepareStatement(queryFindOne);
		stm.setInt(1, id);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			SinhVien sinhVien = sinhVienDao.findOne(rs.getInt("idsinhVien"));
			return new MonHoc(rs.getInt(1), rs.getString(2), sinhVien);
		}
		return null;
	}

	@Override
	public Vector<MonHoc> findAll() throws Exception {
		// TODO Auto-generated method stub
		Vector<MonHoc> monhocs = new Vector<>();
		PreparedStatement stm = connection.prepareStatement(queryFindAll);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			SinhVien sinhVien = sinhVienDao.findOne(rs.getInt("idsinhVien"));
			monhocs.add(new MonHoc(rs.getInt(1), rs.getString(2), sinhVien));
		}
		return monhocs;
	}

	@Override
	public Vector<MonHoc> findBy(String key, String data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}