package models;

public class MonHoc {
	private int id;
	private String tenMonHoc;
	private SinhVien sinhVien;

	public MonHoc(int id, String tenMonHoc, SinhVien sinhVien) {
		super();
		this.id = id;
		this.tenMonHoc = tenMonHoc;
		this.sinhVien = sinhVien;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public SinhVien getSinhVien() {
		return sinhVien;
	}

	public void setSinhVien(SinhVien sinhVien) {
		this.sinhVien = sinhVien;
	}

	public MonHoc() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MonHoc [id=" + id + ", tenMonHoc=" + tenMonHoc + ", sinhVien=" + sinhVien + "]";
	}

}
