package models;

public class Lop {
	private int id;
	public String tenlop;
	private Nganh nganh;

	public Lop(int id, String tenlop, Nganh nganh) {
		super();
		this.id = id;
		this.tenlop = tenlop;
		this.nganh = nganh;
	}

	public Lop() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenlop() {
		return tenlop;
	}

	public void setTenlop(String tenlop) {
		this.tenlop = tenlop;
	}

	public Nganh getNganh() {
		return nganh;
	}

	public void setNganh(Nganh nganh) {
		this.nganh = nganh;
	}

	@Override
	public String toString() {
		return tenlop + " - " + id;
	}

}