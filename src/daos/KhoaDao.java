package daos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import jdbc.DBConnect;
import models.Khoa;
import models.Truong;

public class KhoaDao implements DaoInterface<Khoa>, ActionListener {
	public String tableName = "Khoa";
	public String queryCreate = "INSERT INTO " + tableName + "(tenkhoa,dienthoai,idtruong) VALUES(?,?,?)";
	public String queryUpdate = "UPDATE " + tableName + " SET tenkhoa=?" + ", idtruong=?"
			+ ", dienthoai = ? WHERE id = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	public Connection connection = DBConnect.connection;
	private TruongDao truongDao = new TruongDao();

	public Vector<Truong> getAllTruong() throws Exception {
		return truongDao.findAll();
	}

	@Override
	public ThongBao create(Khoa duLieu) {
		// TODO Auto-generated method stub

		try {
			PreparedStatement stm = connection.prepareStatement(queryCreate);
			stm.setString(1, duLieu.getTenkhoa());
			stm.setString(2, duLieu.getDienThoai());
			stm.setInt(3, duLieu.getTruong().getId());
			stm.executeUpdate();
			return new ThongBao("Thêm thành công khoa  ", true);

		} catch (Exception e) {
			return new ThongBao("Thêm không thành công khoa  ", false);
		}

	}

	@Override
	public ThongBao update(int id, Khoa duLieu) {
		// TODO Auto-generated method stub

		try {
			PreparedStatement stm = connection.prepareStatement(queryUpdate);
			stm.setString(1, duLieu.getTenkhoa());
			stm.setInt(2, duLieu.getTruong().getId());
			stm.setString(3, duLieu.getDienThoai());
			stm.setInt(4, id);
			stm.executeUpdate();
			return new ThongBao("Cập Nhật Thành Công  ", true);
		} catch (Exception e) {
			// TODO: handle exception
			return new ThongBao("Cập Nhật không Thành Công  ", false);
		}

	}

	@Override
	public ThongBao delete(int id) {
		try {
			Khoa khoa = this.findOne(id);
			if (khoa == null) {
				return new ThongBao("Khoa không tồn tại" + id, false);
			}
			// TODO Auto-generated method stub
			PreparedStatement stm =   connection.prepareStatement(queryXoa);
			stm.setInt(1, id);
			stm.executeUpdate();
			return new ThongBao("Xóa thành công khoa  ", true);
		} catch (Exception e) {
			return new ThongBao("Không thể xóa khoa này", false);
		}

	}

	@Override
	public Khoa findOne(int id) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stm = connection.prepareStatement(queryFindOne);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				Truong truong = truongDao.findOne(rs.getInt("idtruong"));
				return new Khoa(rs.getInt(1), rs.getString(2), truong, rs.getString(4));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public Vector<Khoa> findAll() throws Exception {
		// TODO Auto-generated method stub
		TruongDao truongDao = new TruongDao();
		Vector<Khoa> khoas = new Vector<>();
		PreparedStatement stm = connection.prepareStatement(queryFindAll);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			Truong truong = truongDao.findOne(rs.getInt("idtruong"));
			khoas.add(new Khoa(rs.getInt(1), rs.getString(2), truong, rs.getString(4)));
		}
		return khoas;
	}

	@Override
	public Vector<Khoa> findBy(String key, String data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
