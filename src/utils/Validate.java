package utils;

public class Validate {

	public static String regexEmail = "^\\S+@\\S+\\.\\S+$";
	public static String regexPhone = "^(\\+84|0)(3[2-9]|5[689]|7[0|6-9]|8[1-9]|9[0-9])\\d{7}$";
	public static String regexMaSinhVien = "^[A-Z0-9]{5,}$";
	public static String regexUsername = "^[a-z0-9]+$";

	public static boolean isEmailValid(String email) {
		return email.matches(Validate.regexEmail);
	}

	public static boolean isMaSinhVienValid(String maSinhVien) {
		return maSinhVien.matches(Validate.regexMaSinhVien);
	}

	public static boolean isPhoneValid(String phone) {
		return phone.matches(Validate.regexPhone);
	}

	public boolean validateUsername(String username) {
		
		return username.matches(Validate.regexUsername);
	}
	public static void main(String[] args) {
		System.out.println(Validate.isEmailValid("i"));
	}

}
