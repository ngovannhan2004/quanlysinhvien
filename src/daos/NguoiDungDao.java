package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.DBConnect;
import models.NguoiDung;

public class NguoiDungDao implements NguoidungInterface<NguoiDung> {
	public String tableName = "nguoidung";
	public String queryDangnhap = "SELECT * FROM " + tableName + " WHERE tendangnhap = ? AND matkhau = ?";
	public String queryUpdate = "UPDATE " + tableName + " SET  matkhau=? WHERE email=?";
	public String queryDangki = "INSERT INTO " + tableName + "(tendangnhap,email,matkhau,ten) VALUES(?,?,?,?)";
	public String query = "SELECT * FROM " + tableName + " WHERE email=?";
	public Connection connection = DBConnect.connection;
	private static final String salt = "hahhdsfdhahđfaghzfgadfhagffhshdfhgdagđfhsdhdh";

	public NguoiDungDao() {

	}

	@Override
	public ThongBao dangki(NguoiDung duLieu) {
		// Mã hóa mật khẩu trước khi lưu vào database
		String hashedPwd = getSecurityMD5(duLieu.getMatkhau() + salt);
		try {
			PreparedStatement stm = connection.prepareStatement(queryDangki);
			stm.setString(1, duLieu.getTendangnhap());
			stm.setString(2, duLieu.getEmail());
			stm.setString(3, hashedPwd);
			stm.setString(4, duLieu.getTen());
			stm.executeUpdate();
			return new ThongBao("Đăng kí thành công", true);
		} catch (Exception e) {
			// TODO: handle exception
			return new ThongBao("Đăng kí không thành công", false);
		}

	}

	@Override
	public ThongBao dangnhap(String tendangnhap, String matkhau) {
		// Mã hóa mật khẩu trước khi so sánh với mật khẩu đã mã hóa trong database
		String hashedPwd = getSecurityMD5(matkhau + salt);
		try {
			PreparedStatement stm = connection.prepareStatement(queryDangnhap);
			stm.setString(1, tendangnhap);
			stm.setString(2, hashedPwd);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return new ThongBao("Đăng nhập thành công", true);
			} else {
				return new ThongBao("Đăng nhập không thành công", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ThongBao("Đăng nhập không thành công", false);
		}
	}

	// Updatea method to update password for a given email
	public ThongBao updateByEmail(String email, String matkhau) {
		// Mã hóa mật khẩu trước khi lưu vào database
		String hashedPwd = getSecurityMD5(matkhau + salt);
		try {
			PreparedStatement stm = connection.prepareStatement(queryUpdate);
			stm.setString(2, email);
			stm.setString(1, hashedPwd);
			stm.executeUpdate();
			return new ThongBao("Đổi mật khẩu thành công", true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ThongBao("Đổi mật khẩu  không thành công", false);
		}
	}

	public NguoiDung getNguoiDungByEmail(String email) {
		NguoiDung nguoiDung = new NguoiDung();

		try {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, email);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				nguoiDung = new NguoiDung();
				nguoiDung.setTendangnhap(rs.getString("tendangnhap"));
				nguoiDung.setEmail(rs.getString("email"));
				nguoiDung.setMatkhau(rs.getString("matkhau"));
				nguoiDung.setTen(rs.getString("ten"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nguoiDung;
	}

	public static String getSecurityMD5(String pwd) {
		return MD5(pwd + NguoidungInterface.key + salt);
	}

	private static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static void main(String[] args) {

	}

}
