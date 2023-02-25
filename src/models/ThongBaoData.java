package models;

import daos.ThongBao;

public class ThongBaoData<T> extends ThongBao {

	private T data;

	public ThongBaoData(String tinNhan, Boolean kiemTra, T data) {
		super(tinNhan, kiemTra);
		this.data = data;
		// TODO Auto-generated constructor stub
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return super.toString() + "ThongBaoData [data=" + data + "]";
	}
}
