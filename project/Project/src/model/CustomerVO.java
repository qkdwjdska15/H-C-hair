package model;

public class CustomerVO {
	private int c_code;// 고객 일련번호
	private String c_name;//고객 성함
	private String c_gender;//고객 성별
	private String c_phone;//고객 전화번호
	private String c_age;//고객 생년월일
	private String c_address;//고객 주소
	private String c_tiket_rating;//정액권 등급
	private String c_expiration_date;//정액권 만료 일자
	private String c_tiket_sales;//정액권 할인율
	
	public CustomerVO() {
		super();
	}

	public CustomerVO(String c_name, String c_gender, String c_phone, String c_age, String c_address,
			String c_tiket_rating, String c_expiration_date, String c_tiket_sales) {
		super();
		this.c_name = c_name;
		this.c_gender = c_gender;
		this.c_phone = c_phone;
		this.c_age = c_age;
		this.c_address = c_address;
		this.c_tiket_rating = c_tiket_rating;
		this.c_expiration_date = c_expiration_date;
		this.c_tiket_sales = c_tiket_sales;
	}

	public CustomerVO(int c_code, String c_name, String c_gender, String c_phone, String c_age, String c_address,
			String c_tiket_rating, String c_expiration_date, String c_tiket_sales) {
		super();
		this.c_code = c_code;
		this.c_name = c_name;
		this.c_gender = c_gender;
		this.c_phone = c_phone;
		this.c_age = c_age;
		this.c_address = c_address;
		this.c_tiket_rating = c_tiket_rating;
		this.c_expiration_date = c_expiration_date;
		this.c_tiket_sales = c_tiket_sales;
	}
	
	public CustomerVO(String c_name, String c_gender, String c_phone, String c_age, String c_address) {
		super();
		this.c_name = c_name;
		this.c_gender = c_gender;
		this.c_phone = c_phone;
		this.c_age = c_age;
		this.c_address = c_address;
	}

	public int getC_code() {
		return c_code;
	}

	public void setC_code(int c_code) {
		this.c_code = c_code;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public String getC_gender() {
		return c_gender;
	}

	public void setC_gender(String c_gender) {
		this.c_gender = c_gender;
	}

	public String getC_phone() {
		return c_phone;
	}

	public void setC_phone(String c_phone) {
		this.c_phone = c_phone;
	}

	public String getC_age() {
		return c_age;
	}

	public void setC_age(String c_age) {
		this.c_age = c_age;
	}

	public String getC_address() {
		return c_address;
	}

	public void setC_address(String c_address) {
		this.c_address = c_address;
	}

	public String getC_tiket_rating() {
		return c_tiket_rating;
	}

	public void setC_tiket_rating(String c_tiket_rating) {
		this.c_tiket_rating = c_tiket_rating;
	}

	public String getC_expiration_date() {
		return c_expiration_date;
	}

	public void setC_expiration_date(String c_expiration_date) {
		this.c_expiration_date = c_expiration_date;
	}

	public String getC_tiket_sales() {
		return c_tiket_sales;
	}

	public void setC_tiket_sales(String c_tiket_sales) {
		this.c_tiket_sales = c_tiket_sales;
	}

	@Override
	public String toString() {
		return "customerVO [c_code=" + c_code + ", c_name=" + c_name + ", c_gender=" + c_gender + ", c_phone=" + c_phone
				+ ", c_age=" + c_age + ", c_address=" + c_address + ", c_tiket_rating=" + c_tiket_rating
				+ ", c_expiration_date=" + c_expiration_date + ", c_tiket_sales=" + c_tiket_sales + "]";
	}

	
	
	
	
}
