package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import jdbc.DBConnect;
import models.Lop;
import models.SinhVien;

public class SinhVienDao implements DaoInterface<SinhVien> {
	public String tableName = "SinhVien";
	public String queryCreate = "INSERT INTO " + tableName
			+ "(masinhvien,tensinhvien,email,dienthoai,diachi,gioitinh,idlop) VALUES(?,?,?,?,?,?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET tensinhvien = ? " + ", idlop=? " + ", masinhvien=?"
			+ ", email=?" + ", dienthoai=?" + ", diachi=?" + ", gioitinh=? WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	public String queryRefresh = "SELECT * FROM " + tableName;
	public String queryCountByMasv = "SELECT COUNT(*) FROM sinhvien WHERE masinhvien = ?";
	public String queryCountByEmail = "SELECT COUNT(*) FROM sinhvien WHERE email = ?";
	public String queryGetAllSinhVienByIdTruong = "SELECT sinhvien.* " + "FROM sinhvien "
			+ "INNER JOIN lop ON sinhvien.idlop = lop.id " + "INNER JOIN nganh ON lop.idnganh = nganh.id "
			+ "INNER JOIN khoa ON nganh.idkhoa = khoa.id " + "INNER JOIN truong ON khoa.idtruong = truong.id "
			+ "WHERE truong.id = ?";
	public String queryGetAllSinhVienByIdKhoa = "SELECT sinhvien.* " + "FROM sinhvien "
			+ "INNER JOIN lop ON sinhvien.idlop = lop.id " + "INNER JOIN nganh ON lop.idnganh = nganh.id "
			+ "INNER JOIN khoa ON nganh.idkhoa = khoa.id " 
			+ "WHERE khoa.id = ?";
	public Connection connection = DBConnect.connection;
	private LopHocDao lopHocDao = new LopHocDao();
	Lop lop = new Lop();

	public Vector<Lop> getAllLop() throws Exception {
		return lopHocDao.findAll();
	}

	public boolean checkEmailExist(String email) {
		try {
			int count = this.getCountByEmail(email);
			return count > 0 ? true : false;
		} catch (Exception e) {
			return true;
		}
	}

	public boolean checkMaSinhVienExist(String masv) {
		try {
			int count = this.getCountByMaSV(masv);
			return count > 0 ? true : false;
		} catch (Exception e) {
			return true;
		}
	}

	public int getCountByMaSV(String masv) throws Exception {
		int count = 0;
		try {
			PreparedStatement stmt = connection.prepareStatement(queryCountByMasv);
			stmt.setString(1, masv);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int getCountByEmail(String email) throws Exception {
		int count = 0;
		try {
			PreparedStatement stmt = connection.prepareStatement(queryCountByEmail);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public ThongBao create(SinhVien duLieu) {
		// TODO Auto-generated method stub
		if (this.checkMaSinhVienExist(duLieu.getMasinhvien())) {
			return new ThongBao("Mã sinh viên đã tồn tại", false);
		}
		if (this.checkEmailExist(duLieu.getEmail())) {
			return new ThongBao("Email đã tồn tại", false);
		}
		try {
			PreparedStatement stm = connection.prepareStatement(queryCreate);
			stm.setString(1, duLieu.getMasinhvien());
			stm.setString(2, duLieu.getTensinhvien());
			stm.setString(3, duLieu.getEmail());
			stm.setString(4, duLieu.getDienthoai());
			stm.setString(5, duLieu.getDiachi());
			stm.setInt(6, duLieu.getGender());
			stm.setInt(7, duLieu.getLop().getId());
			stm.executeUpdate();
			return new ThongBao("Thêm Thành công  ", true);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ThongBao update(int id, SinhVien duLieu) throws Exception {
		SinhVien sinhVienByEmail = this.findOneBy("email", duLieu.getEmail());
		SinhVien sinhVienByMasv = this.findOneBy("masinhvien", duLieu.getMasinhvien());
		if (sinhVienByEmail.getId() != id && sinhVienByEmail != null) {
			return new ThongBao("Email đã tồn tại", false);
		}
		if (sinhVienByMasv.getId() != id && sinhVienByMasv != null) {
			return new ThongBao("Mã sinh viên đã tồn tại", false);
		}
		try {
			// TODO Auto-generated method stub
			PreparedStatement stm = connection.prepareStatement(queryUpdate);
			stm.setString(1, duLieu.getTensinhvien());
			stm.setInt(2, duLieu.getLop().getId());
			stm.setString(3, duLieu.getMasinhvien());
			stm.setString(4, duLieu.getEmail());
			stm.setString(5, duLieu.getDienthoai());
			stm.setString(6, duLieu.getDiachi());
			stm.setInt(7, duLieu.getGender());
			stm.setInt(8, duLieu.getId());
			stm.executeUpdate();
			return new ThongBao("Câp Nhật Thành Công  ", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ThongBao("Câp Nhật Thất Bại", false);
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
			return new ThongBao("Không thể xóa sinh viên này", false);
		}

	}

	@Override
	public SinhVien findOne(int id) throws Exception {
		// TODO Auto-generated method stub

		PreparedStatement stm = connection.prepareStatement(queryFindOne);
		stm.setInt(1, id);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			Lop lop = lopHocDao.findOne(rs.getInt("idlop"));
			SinhVien sinhVien = new SinhVien();
			sinhVien.setId(rs.getInt("id"));
			sinhVien.setTensinhvien(rs.getString("tensinhvien"));
			sinhVien.setMasinhvien(rs.getString("masinhvien"));
			sinhVien.setEmail(rs.getString("email"));
			sinhVien.setDienthoai(rs.getString("dienthoai"));
			sinhVien.setDiachi(rs.getString("diachi"));
			sinhVien.setGender(rs.getInt("gioitinh"));
			sinhVien.setLop(lop);
			return sinhVien;

		}
		return null;
	}

	@Override
	public Vector<SinhVien> findAll() throws Exception {
		// TODO Auto-generated method stub
		LopHocDao lopHocDao = new LopHocDao();
		Vector<SinhVien> sinhviens = new Vector<>();
		PreparedStatement stm = connection.prepareStatement(queryFindAll);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			Lop lop = lopHocDao.findOne(rs.getInt("idlop"));
			sinhviens.add(new SinhVien(rs.getInt(1), rs.getString(2), rs.getInt("gioitinh"), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), lop));

		}
		return sinhviens;
	}

	public SinhVien findOneBy(String key, String data) throws Exception {
		SinhVien sinhVien = null;
		String query = "SELECT * FROM sinhvien WHERE " + key + " = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, data);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Lop lop = lopHocDao.findOne(rs.getInt("idlop"));
				sinhVien = new SinhVien();
				sinhVien.setId(rs.getInt("id"));
				sinhVien.setTensinhvien(rs.getString("tensinhvien"));
				sinhVien.setMasinhvien(rs.getString("masinhvien"));
				sinhVien.setEmail(rs.getString("email"));
				sinhVien.setDienthoai(rs.getString("dienthoai"));
				sinhVien.setDiachi(rs.getString("diachi"));
				sinhVien.setGender(rs.getInt("gioitinh"));
				sinhVien.setLop(lop);

			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sinhVien;
	}

	@Override
	public Vector<SinhVien> findBy(String key, String data) throws Exception {
		Vector<SinhVien> result = new Vector<SinhVien>();
		String query = "SELECT * FROM sinhvien WHERE " + key + " = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, data);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Lop lop = lopHocDao.findOne(rs.getInt("idlop"));
				SinhVien sinhVien = new SinhVien();
				sinhVien.setId(rs.getInt("id"));
				sinhVien.setTensinhvien(rs.getString("tensinhvien"));
				sinhVien.setMasinhvien(rs.getString("masinhvien"));
				sinhVien.setEmail(rs.getString("email"));
				sinhVien.setDienthoai(rs.getString("dienthoai"));
				sinhVien.setDiachi(rs.getString("diachi"));
				sinhVien.setGender(rs.getInt("gioitinh"));
				sinhVien.setLop(lop);
				result.add(sinhVien);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	public Vector<SinhVien> getAllSinhVienByIdTruong(int id) {
		Vector<SinhVien> sinhViens = new Vector<>();
		try {
			PreparedStatement stmt = connection.prepareStatement(queryGetAllSinhVienByIdTruong);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Lop lop = lopHocDao.findOne(rs.getInt("idlop"));
				
				SinhVien sinhVien = new SinhVien();
				sinhVien.setId(rs.getInt("id"));
				sinhVien.setTensinhvien(rs.getString("tensinhvien"));
				sinhVien.setMasinhvien(rs.getString("masinhvien"));
				sinhVien.setEmail(rs.getString("email"));
				sinhVien.setDienthoai(rs.getString("dienthoai"));
				sinhVien.setDiachi(rs.getString("diachi"));
				sinhVien.setGender(rs.getInt("gioitinh"));
				sinhVien.setLop(lop);
				sinhViens.add(sinhVien);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sinhViens;
	}
	
}
