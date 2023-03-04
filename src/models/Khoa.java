package models;

public class Khoa {
	private int id;
	private String tenkhoa;
	private Truong truong;
	private String dienThoai;

	public Khoa(int id, String tenkhoa, Truong truong, String dienthoai) {
		super();
		this.id = id;
		this.tenkhoa = tenkhoa;
		this.truong = truong;
		this.dienThoai = dienthoai;
	}

	public String getDienThoai() {
		return dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	public Khoa() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenkhoa() {
		return tenkhoa;
	}

	public void setTenkhoa(String tenkhoa) {
		this.tenkhoa = tenkhoa;
	}

	public Truong getTruong() {
		return truong;
	}

	public void setTruong(Truong truong) {
		this.truong = truong;
	}

	@Override
	public String toString() {
		return id + " - " + tenkhoa;
	}

}
