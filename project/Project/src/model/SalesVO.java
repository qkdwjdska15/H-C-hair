package model;

public class SalesVO {
	
	private String r_product_name;// 예약한 상품명
	private String r_total_price;// 예약한 상품 총액
	
	
	private int s_no;//결제번호
	private String s_customer_name;//결제한 회원이름
	private String s_customer_gender;//결제한 회원 성별
	private String s_customer_phone;//결제한 회원 전화번호
	private String s_designer_name;// 결제한 회원 담당 디자이너 이름
	private String s_reservation_product_name;//결제한 상품명
	private String s_reservation_total_price;//상품의 총액
	private String s_customer_tiket_rating;//결제한 회원의 정액권 등급
	private String s_customer_tiket_sales;//결제한 회원의 정액권 등급에 따른 할인율
	private String s_total_price; //결제한 금액
	private String s_date;//결제일(당일)



	public SalesVO() {
		super();
	}
	
	
	
	
	public SalesVO(String s_customer_name, String s_customer_gender, String s_customer_phone,
			String s_designer_name, String s_reservation_product_name, String s_reservation_total_price,
			String s_customer_tiket_rating, String s_customer_tiket_sales, String s_total_price) {
		super();
		this.s_customer_name = s_customer_name;
		this.s_customer_gender = s_customer_gender;
		this.s_customer_phone = s_customer_phone;
		this.s_designer_name = s_designer_name;
		this.s_reservation_product_name = s_reservation_product_name;
		this.s_reservation_total_price = s_reservation_total_price;
		this.s_customer_tiket_rating = s_customer_tiket_rating;
		this.s_customer_tiket_sales = s_customer_tiket_sales;
		this.s_total_price = s_total_price;
	}




	public SalesVO(int s_no, String s_customer_name, String s_customer_gender, String s_customer_phone,
			String s_designer_name, String s_reservation_product_name, String s_reservation_total_price,
			String s_customer_tiket_rating, String s_customer_tiket_sales, String s_total_price, String s_date) {
		super();
		this.s_no = s_no;
		this.s_customer_name = s_customer_name;
		this.s_customer_gender = s_customer_gender;
		this.s_customer_phone = s_customer_phone;
		this.s_designer_name = s_designer_name;
		this.s_reservation_product_name = s_reservation_product_name;
		this.s_reservation_total_price = s_reservation_total_price;
		this.s_customer_tiket_rating = s_customer_tiket_rating;
		this.s_customer_tiket_sales = s_customer_tiket_sales;
		this.s_total_price = s_total_price;
		this.s_date = s_date;
	}




	public SalesVO(String r_product_name, String r_total_price) {
		super();
		this.r_product_name = r_product_name;
		this.r_total_price = r_total_price;
	}






	public int getS_no() {
		return s_no;
	}




	public void setS_no(int s_no) {
		this.s_no = s_no;
	}




	public String getS_customer_name() {
		return s_customer_name;
	}




	public void setS_customer_name(String s_customer_name) {
		this.s_customer_name = s_customer_name;
	}




	public String getS_customer_gender() {
		return s_customer_gender;
	}




	public void setS_customer_gender(String s_customer_gender) {
		this.s_customer_gender = s_customer_gender;
	}




	public String getS_customer_phone() {
		return s_customer_phone;
	}




	public void setS_customer_phone(String s_customer_phone) {
		this.s_customer_phone = s_customer_phone;
	}




	public String getS_designer_name() {
		return s_designer_name;
	}




	public void setS_designer_name(String s_designer_name) {
		this.s_designer_name = s_designer_name;
	}




	public String getS_reservation_product_name() {
		return s_reservation_product_name;
	}




	public void setS_reservation_product_name(String s_reservation_product_name) {
		this.s_reservation_product_name = s_reservation_product_name;
	}




	public String getS_reservation_total_price() {
		return s_reservation_total_price;
	}




	public void setS_reservation_total_price(String s_reservation_total_price) {
		this.s_reservation_total_price = s_reservation_total_price;
	}




	public String getS_customer_tiket_rating() {
		return s_customer_tiket_rating;
	}




	public void setS_customer_tiket_rating(String s_customer_tiket_rating) {
		this.s_customer_tiket_rating = s_customer_tiket_rating;
	}




	public String getS_customer_tiket_sales() {
		return s_customer_tiket_sales;
	}




	public void setS_customer_tiket_sales(String s_customer_tiket_sales) {
		this.s_customer_tiket_sales = s_customer_tiket_sales;
	}




	public String getS_total_price() {
		return s_total_price;
	}




	public void setS_total_price(String s_total_price) {
		this.s_total_price = s_total_price;
	}




	public String getS_date() {
		return s_date;
	}




	public void setS_date(String s_date) {
		this.s_date = s_date;
	}




	public String getR_product_name() {
		return r_product_name;
	}

	public void setR_product_name(String r_product_name) {
		this.r_product_name = r_product_name;
	}

	public String getR_total_price() {
		return r_total_price;
	}

	public void setR_total_price(String r_total_price) {
		this.r_total_price = r_total_price;
	}




	@Override
	public String toString() {
		return "MainsystemVO [r_product_name=" + r_product_name + ", r_total_price=" + r_total_price + ", s_no="
				+ s_no + ", s_customer_name=" + s_customer_name + ", s_customer_gender=" + s_customer_gender
				+ ", s_customer_phone=" + s_customer_phone + ", s_designer_name=" + s_designer_name
				+ ", s_reservation_product_name=" + s_reservation_product_name + ", s_reservation_total_price="
				+ s_reservation_total_price + ", s_customer_tiket_rating=" + s_customer_tiket_rating
				+ ", s_customer_tiket_sales=" + s_customer_tiket_sales + ", s_total_price=" + s_total_price
				+ ", s_date=" + s_date + "]";
	}


	

}