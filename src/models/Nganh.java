package models;

public class Nganh {
	private int id;
	private String tenNganh;
	private Khoa khoa;

	public Nganh(int id, String tenNganh, Khoa khoa) {
		super();
		this.id = id;
		this.tenNganh = tenNganh;
		this.khoa = khoa;
	}

	public Nganh() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenNganh() {
		return tenNganh;
	}

	public void setTenNganh(String tenNganh) {
		this.tenNganh = tenNganh;
	}

	public Khoa getKhoa() {
		return khoa;
	}

	public void setKhoa(Khoa khoa) {
		this.khoa = khoa;
	}

	@Override
	public String toString() {
		return tenNganh + "-" + id;
	}

}
