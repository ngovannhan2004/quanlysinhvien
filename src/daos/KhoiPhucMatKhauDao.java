package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import jdbc.DBConnect;
import models.KhoiPhucMatKhau;
import models.ThongBaoData;

public class KhoiPhucMatKhauDao implements DaoInterface<KhoiPhucMatKhau> {
	public String tableName = "khoiphucmatkhau";
	public String queryCreate = "INSERT INTO " + tableName + "(email, code) VALUES(?, ?)";
	public String queryUpdate = "UPDATE " + tableName + " SET `email` = ?, `code` = ? WHERE `id` = ?";
	public String queryXoa = "DELETE FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOne = "SELECT * FROM " + tableName + " WHERE `id` = ?";
	public String queryFindOneByEmail = "SELECT * FROM " + tableName + " WHERE `email` = ?";
	public String queryFindAll = "SELECT * FROM " + tableName;
	public Connection connection = DBConnect.connection;
	public NguoiDungDao nguoiDungDao = new NguoiDungDao();

	@Override
	public ThongBao create(KhoiPhucMatKhau khoiPhucMatKhau) throws Exception {
		if (nguoiDungDao.getNguoiDungByEmail(khoiPhucMatKhau.getEmail()).getEmail() == null) {
			return new ThongBao("Người dùng không tồn tại", false);
		}
		PreparedStatement statement = connection.prepareStatement(queryCreate);
		statement.setString(1, khoiPhucMatKhau.getEmail());
		statement.setString(2, khoiPhucMatKhau.getCode());
		int rowsInserted = statement.executeUpdate();
		statement.close();
		if (rowsInserted > 0) {
			return new ThongBao("Đã thêm dữ liệu thành công", true);
		} else {
			return new ThongBao("Không thể thêm dữ liệu", false);
		}
	}

	@Override
	public ThongBao update(int id, KhoiPhucMatKhau khoiPhucMatKhau) throws Exception {
		PreparedStatement statement = connection.prepareStatement(queryUpdate);
		statement.setString(1, khoiPhucMatKhau.getEmail());
		statement.setString(2, khoiPhucMatKhau.getCode());
		statement.setInt(3, id);
		int rowsUpdated = statement.executeUpdate();
		statement.close();
		if (rowsUpdated > 0) {
			return new ThongBao("Đã cập nhật dữ liệu thành công", true);
		} else {
			return new ThongBao("Không thể cập nhật dữ liệu", false);
		}
	}

	@Override
	public ThongBao delete(int id) throws Exception {
		PreparedStatement statement = connection.prepareStatement(queryXoa);
		statement.setInt(1, id);
		int rowsDeleted = statement.executeUpdate();
		statement.close();
		if (rowsDeleted > 0) {
			return new ThongBao("Đã xóa dữ liệu thành công", true);
		} else {
			return new ThongBao("Không thể xóa dữ liệu", false);
		}
	}

	@Override
	public KhoiPhucMatKhau findOne(int id) throws Exception {
		String query = queryFindOne;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		KhoiPhucMatKhau khoiPhucMatKhau = null;
		if (result.next()) {
			khoiPhucMatKhau = new KhoiPhucMatKhau();
			khoiPhucMatKhau.setId(result.getInt("id"));
			khoiPhucMatKhau.setEmail(result.getString("email"));
			khoiPhucMatKhau.setCode(result.getString("code"));
		}
		return khoiPhucMatKhau;
	}

	public KhoiPhucMatKhau findOneByEmail(String email) throws Exception {
		String query = queryFindOne;
		PreparedStatement statement = connection.prepareStatement(queryFindOneByEmail);
		statement.setString(1, email);
		ResultSet result = statement.executeQuery();
		KhoiPhucMatKhau khoiPhucMatKhau = null;
		if (result.next()) {
			khoiPhucMatKhau = new KhoiPhucMatKhau();
			khoiPhucMatKhau.setId(result.getInt("id"));
			khoiPhucMatKhau.setEmail(result.getString("email"));
			khoiPhucMatKhau.setCode(result.getString("code"));
		}
		return khoiPhucMatKhau;
	}

	@Override
	public Vector<KhoiPhucMatKhau> findAll() throws Exception {
		Vector<KhoiPhucMatKhau> result = new Vector<KhoiPhucMatKhau>();
		String query = queryFindAll;
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			KhoiPhucMatKhau khoiPhucMatKhau = new KhoiPhucMatKhau();
			khoiPhucMatKhau.setId(rs.getInt("id"));
			khoiPhucMatKhau.setEmail(rs.getString("email"));
			khoiPhucMatKhau.setCode(rs.getString("code"));
			result.add(khoiPhucMatKhau);
		}
		ps.close();
		return result;
	}

	@Override
	public Vector<KhoiPhucMatKhau> findBy(String key, String data) throws Exception {
		Vector<KhoiPhucMatKhau> results = new Vector<KhoiPhucMatKhau>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			String query = "SELECT * FROM " + tableName + " WHERE " + key + " = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, data);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				KhoiPhucMatKhau khoiPhucMatKhau = new KhoiPhucMatKhau();
				khoiPhucMatKhau.setId(resultSet.getInt("id"));
				khoiPhucMatKhau.setEmail(resultSet.getString("email"));
				khoiPhucMatKhau.setCode(resultSet.getString("code"));
				results.add(khoiPhucMatKhau);
			}

		} catch (SQLException e) {
			throw new Exception("Error finding records: " + e.getMessage());
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		}

		return results;
	}

	public static String generateRandomNumber() {
		int maxNumber = 100000000; // Tối đa 8 chữ số
		int randomNumber = (int) (Math.random() * maxNumber); // Tạo số ngẫu nhiên
		return String.format("%08d", randomNumber); // Định dạng số thành chuỗi với đủ 8 chữ số
	}

	public ThongBao updatePassword(String email, String code, String matKhau) throws Exception {
		KhoiPhucMatKhau khoiPhucMatKhau = this.findOneByEmail(email);
		if (!khoiPhucMatKhau.getCode().equals(code)) {
			return new ThongBao("Mã xác nhận không chính xác vui lòng thử lại!", false);
		} else {
			return this.nguoiDungDao.updateByEmail(email, matKhau);
		}
	}

	public ThongBaoData<KhoiPhucMatKhau> createOrUpdateFunction(KhoiPhucMatKhau duLieu) {
		try {
			Vector<KhoiPhucMatKhau> existingRecords = findBy("email", duLieu.getEmail());
			if (existingRecords.size() == 0) {
				// record does not exist, create new record
				duLieu.setCode(generateRandomNumber());
				ThongBao thongBao = create(duLieu);
				return new ThongBaoData<KhoiPhucMatKhau>(thongBao.getTinNhan(), thongBao.getKiemTra(), duLieu);
			} else if (existingRecords.size() == 1) {
				// record already exists, update existing record
				int existingRecordId = existingRecords.get(0).getId();
				duLieu.setCode(generateRandomNumber());
				ThongBao thongBao = update(existingRecordId, duLieu);
				return new ThongBaoData<KhoiPhucMatKhau>(thongBao.getTinNhan(), thongBao.getKiemTra(), duLieu);
			} else {
				// multiple records found, should not happen
				return new ThongBaoData<KhoiPhucMatKhau>("Multiple records found for email: " + duLieu.getEmail(),
						false, duLieu);
			}
		} catch (Exception e) {
			return new ThongBaoData<KhoiPhucMatKhau>(e.getMessage(), false, duLieu);
		}
	}

}
