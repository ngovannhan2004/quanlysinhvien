package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import jdbc.DBConnect;
import models.Truong;

public class TruongDao implements DaoInterface<Truong> {
	public String tableName = "Truong";
	public String queryCreate = "INSERT INTO " + tableName + "(TenTruong, DiaChi) VALUES(?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET tentruong = ? , diachi = ? WHERE 'id'= ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	public String queryRefresh = "delete * from " + tableName;
	public Connection connection = DBConnect.connection;
	@Override
	public ThongBao create(Truong duLieu) {
		// TODO Auto-generated method stub

		try {
			PreparedStatement stm = connection.prepareStatement(queryCreate);
			stm.setString(1, duLieu.getTenTruong());
			stm.setString(2, duLieu.getDiaChi());
			stm.executeUpdate();
			return new ThongBao("Thêm Thành công " + duLieu, true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ThongBao update(int id, Truong duLieu) {
		// TODO Auto-generated method stub

		try {
			PreparedStatement stm = connection.prepareStatement(queryUpdate);
			stm.setInt(1, id);
			stm.setString(2, duLieu.getTenTruong());
			stm.setString(3, duLieu.getDiaChi());
			stm.executeUpdate();
			return new ThongBao("Câp Nhật Thành Công  " + duLieu, true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ThongBao delete(int id) {
		try {
			// TODO Auto-generated method stub
			PreparedStatement stm = connection.prepareStatement(queryXoa);
			stm.setInt(1, id);
			stm.executeUpdate();
			return new ThongBao("Xóa Thành công  " + id, true);
		} catch (Exception e) {
			// TODO: handle exception
			return new ThongBao("Không thể xóa trường này", false);
		}

	}

	@Override
	public Truong findOne(int id) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement stm = connection.prepareStatement(queryFindOne);
		stm.setInt(1, id);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			return new Truong(rs.getInt(1), rs.getString(2), rs.getString(3));
		}
		return null;
	}

	@Override
	public Vector<Truong> findAll() throws Exception {
		// TODO Auto-generated method st ub
		Vector<Truong> truongs = new Vector<>();
		PreparedStatement stm = connection.prepareStatement(queryFindAll);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			truongs.add(new Truong(rs.getInt(1), rs.getString(2), rs.getString(3)));
		}
		return truongs;
	}

	  

	@Override
	public Vector<Truong> findBy(String key, String data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
