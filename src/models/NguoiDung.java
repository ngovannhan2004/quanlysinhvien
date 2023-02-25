package models;

public class NguoiDung {
	private int id;
	private String tendangnhap;

	private String email;
	private String matkhau;
	private String ten;

	public NguoiDung() {

	}

	public NguoiDung(int id, String tendangnhap, String email, String matkhau, String ten) {
		super();
		this.id = id;
		this.tendangnhap = tendangnhap;

		this.email = email;
		this.matkhau = matkhau;
		this.ten = ten;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTendangnhap() {
		return tendangnhap;
	}

	public void setTendangnhap(String tendangnhap) {
		this.tendangnhap = tendangnhap;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatkhau() {
		return matkhau;
	}

	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@Override
	public String toString() {
		return "NguoiDung [id=" + id + ", tendangnhap=" + tendangnhap + ", email=" + email + ", matkhau=" + matkhau
				+ ", ten=" + ten + "]";
	}

}
