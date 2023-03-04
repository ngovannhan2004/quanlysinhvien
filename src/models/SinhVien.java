package models;

public class SinhVien {
	private int id;
	private String tensinhvien;
	private int Gender;
	private String email;
	private String masinhvien;
	private String dienthoai;
	private String diachi;
	private Lop lop;

	public SinhVien() {
		// TODO Auto-generated constructor stub
	}

	public SinhVien(int id, String tensinhvien, int gender, String email, String masinhvien, String dienthoai,
			String diachi, Lop lop) {
		super();
		this.id = id;
		this.tensinhvien = tensinhvien;
		this.Gender = gender;
		this.email = email;
		this.masinhvien = masinhvien;
		this.dienthoai = dienthoai;
		this.diachi = diachi;
		this.lop = lop;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTensinhvien() {
		return tensinhvien;
	}

	public void setTensinhvien(String tensinhvien) {
		this.tensinhvien = tensinhvien;
	}

	public int getGender() {
		return Gender;
	}

	public void setGender(int gender) {
		Gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMasinhvien() {
		return masinhvien;
	}

	public void setMasinhvien(String masinhvien) {
		this.masinhvien = masinhvien;
	}

	public String getDienthoai() {
		return dienthoai;
	}

	public void setDienthoai(String dienthoai) {
		this.dienthoai = dienthoai;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public Lop getLop() {
		return lop;
	}

	public void setLop(Lop lop) {
		this.lop = lop;
	}

	@Override
	public String toString() {
		return id + "-" + tensinhvien;
	}

}