package daos;

import java.util.ArrayList;
import java.util.Vector;

import models.Khoa;
import models.SinhVien;
import models.Truong;

public class ThongKeDao {
	public static TruongDao truongDao = new TruongDao();
	public static SinhVienDao sinhVienDao = new SinhVienDao();

	public static Vector thongKeTruong() {
		Vector<Object> results = new Vector<>();
		try {
			Vector<Truong> truongs = truongDao.findAll();
			for (Truong truong : truongs) {
				Vector<Object> temp = new Vector<>();
				temp.add(truong.getTenTruong());
				temp.add(0.0 + sinhVienDao.getAllSinhVienByIdTruong(truong.getId()).size());
				results.add(temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;

	}

}
