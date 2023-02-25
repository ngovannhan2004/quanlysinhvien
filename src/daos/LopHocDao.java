package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import jdbc.DBConnect;
import models.Khoa;
import models.Lop;
import models.Nganh;

public class LopHocDao implements DaoInterface<Lop> {
	public String tableName = "Lop";
	public String queryCreate = "INSERT INTO " + tableName + "(tenlop,idnganh) VALUES(?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET `tenlop` = ? WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	public Connection connection = DBConnect.connection;
	private NganhDao nganhDao = new NganhDao();

	public Vector<Nganh> getAllNganh() throws Exception {
		return nganhDao.findAll();
	}

	@Override
	public ThongBao create(Lop duLieu) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement stm = connection.prepareStatement(queryCreate);
		stm.setString(1, duLieu.getTenlop());
		stm.setInt(2, duLieu.getNganh().getId());
		stm.executeUpdate();
		return new ThongBao("Thêm Thành công " , true);
	}

	@Override
	public ThongBao update(int id, Lop duLieu) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement stm = connection.prepareStatement(queryUpdate);
		stm.setString(1, duLieu.getTenlop());
		stm.setInt(2, duLieu.getNganh().getId());
		stm.executeUpdate();
		return new ThongBao("Câp Nhật Thành Công  " , true);
	}

	@Override
	public ThongBao delete(int id) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stm = connection.prepareStatement(queryXoa);
			stm.setInt(1, id);
			stm.executeUpdate();
			return new ThongBao("Xóa Thành công  " , true);
		} catch (Exception e) {
			// TODO: handle exception
			return new ThongBao("Không thể xóa lớp này", false);
		}

	}

	@Override
	public Lop findOne(int id) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement stm = connection.prepareStatement(queryFindOne);
		stm.setInt(1, id);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			Nganh nganh = nganhDao.findOne(rs.getInt("idnganh"));
			return new Lop(rs.getInt(1), rs.getString(2), nganh);
		}
		return null;
	}

	@Override
	public Vector<Lop> findAll() throws Exception {
		// TODO Auto-generated method stub
		Vector<Lop> lops = new Vector<>();
		PreparedStatement stm = connection.prepareStatement(queryFindAll);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			Nganh nganh = nganhDao.findOne(rs.getInt("idnganh"));
			lops.add(new Lop(rs.getInt(1), rs.getString(2), nganh));
		}
		return lops;
	}

	@Override
	public Vector<Lop> findBy(String key, String data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}