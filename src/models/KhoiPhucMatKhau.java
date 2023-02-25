package models;

public class KhoiPhucMatKhau {
	private int id;
	private String email;
	private String code;
	public KhoiPhucMatKhau(int id, String email, String code) {
		super();
		this.id = id;
		this.email = email;
		this.code = code;
	}
	public KhoiPhucMatKhau() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "KhoiPhucMatKhau [id=" + id + ", email=" + email + ", code=" + code + "]";
	}
	
	
	
}
