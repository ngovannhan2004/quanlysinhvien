package daos;

public class ThongBao {
	private String tinNhan ;
	private Boolean kiemTra;
	public ThongBao(String tinNhan, Boolean kiemTra) {
		super();
		this.tinNhan = tinNhan;
		this.kiemTra = kiemTra;
	}
	public String getTinNhan() {
		return tinNhan;
	}
	public void setTinNhan(String tinNhan) {
		this.tinNhan = tinNhan;
	}
	public Boolean getKiemTra() {
		return kiemTra;
	}
	public void setKiemTra(Boolean kiemTra) {
		this.kiemTra = kiemTra;
	}
	@Override
	public String toString() {
		return "ThongBao [tinNhan=" + tinNhan + ", kiemTra=" + kiemTra + "]";
	}
	
}
