package model;

public class ReservationVO {
	private int p_no;// 상품번호
	private int c_code;// 회원 코드
	private int d_code;// 디자이너 코드

	private String p_name;// 상품명
	private String p_price;// 상품가격

	private int r_code;// 예약코드(주문번호)
	private String c_name;// 회원성함
	private String c_gender;// 회원성별
	private String c_phone;// 회원전화번호
	private String d_name;// 디자이너성함(담당디자이너)
	private String c_tiket_rating;// 회원 정액권 등급
	private String c_tiket_sales;// 회원 정액권에 따른 할인율
	private String r_product_name;// 예약한 상품명
	private String r_add_product;// 추가된 상품명
	private String r_delete_product;// 삭제된 상품명
	private String r_total_price;// 예약한 상품 총액
	private String r_date;// 예약일
	private String r_visit_time;// 예약시간
	private String r_condetion;// 예약상태

	public ReservationVO() {
		super();
	}
	// select r.r_code, c.c_name, c.c_gender, c.c_phone, c.c_tiket_rating,
	// c.c_tiket_sales, d.d_name, r.r_product_name, r.r_add_product,
	// r.r_delete_product, r.r_total_price, r.r_date, r.r_visit_time, r.r_condition
	// " +
	// " from customer c, designer d, product p, reservation r "+
	// " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code

	
	
	
	public ReservationVO(String p_name, String p_price) {
		super();
		this.p_name = p_name;
		this.p_price = p_price;
	}

	public ReservationVO(String p_name) {
		super();
		this.p_name = p_name;
	}

	public ReservationVO(String r_product_name, String r_total_price, String r_date, String r_visit_time, int p_no,
			int c_code, int d_code) {
		super();
		this.p_no = p_no;
		this.c_code = c_code;
		this.d_code = d_code;
		this.r_product_name = r_product_name;
		this.r_total_price = r_total_price;
		this.r_date = r_date;
		this.r_visit_time = r_visit_time;
	}

	public ReservationVO(String c_name, String c_gender, String c_phone, String c_tiket_rating, String c_tiket_sales) {
		super();
		this.c_name = c_name;
		this.c_gender = c_gender;
		this.c_phone = c_phone;
		this.c_tiket_rating = c_tiket_rating;
		this.c_tiket_sales = c_tiket_sales;
	}

	public ReservationVO(String p_name, String p_price, String r_product_name, String r_total_price) {
		super();
		this.p_name = p_name;
		this.p_price = p_price;
		this.r_product_name = r_product_name;
		this.r_total_price = r_total_price;
	}

	public ReservationVO(String d_name, String r_date, String r_visit_time) {
		super();
		this.d_name = d_name;
		this.r_date = r_date;
		this.r_visit_time = r_visit_time;
	}

	public ReservationVO(String p_name, String p_price, String c_name, String c_gender, String c_phone, String d_name,
			String c_tiket_rating, String c_tiket_sales, String r_product_name, String r_total_price, String r_date,
			String r_visit_time) {
		super();
		this.p_name = p_name;
		this.p_price = p_price;
		this.c_name = c_name;
		this.c_gender = c_gender;
		this.c_phone = c_phone;
		this.d_name = d_name;
		this.c_tiket_rating = c_tiket_rating;
		this.c_tiket_sales = c_tiket_sales;
		this.r_product_name = r_product_name;
		this.r_total_price = r_total_price;
		this.r_date = r_date;
		this.r_visit_time = r_visit_time;
	}

	public ReservationVO(int r_code, String c_name, String c_gender, String c_phone, String d_name,
			String c_tiket_rating, String c_tiket_sales, String r_product_name, String r_add_product,
			String r_delete_product, String r_total_price, String r_date, String r_visit_time, String r_condetion) {
		super();
		this.r_code = r_code;
		this.c_name = c_name;
		this.c_gender = c_gender;
		this.c_phone = c_phone;
		this.d_name = d_name;
		this.c_tiket_rating = c_tiket_rating;
		this.c_tiket_sales = c_tiket_sales;
		this.r_product_name = r_product_name;
		this.r_add_product = r_add_product;
		this.r_delete_product = r_delete_product;
		this.r_total_price = r_total_price;
		this.r_date = r_date;
		this.r_visit_time = r_visit_time;
		this.r_condetion = r_condetion;
	}

	public ReservationVO(int p_no, int c_code, int d_code, String p_name, String p_price, int r_code, String c_name,
			String c_gender, String c_phone, String d_name, String c_tiket_rating, String c_tiket_sales,
			String r_product_name, String r_add_product, String r_delete_product, String r_total_price, String r_date,
			String r_visit_time, String r_condetion) {
		super();
		this.p_no = p_no;
		this.c_code = c_code;
		this.d_code = d_code;
		this.p_name = p_name;
		this.p_price = p_price;
		this.r_code = r_code;
		this.c_name = c_name;
		this.c_gender = c_gender;
		this.c_phone = c_phone;
		this.d_name = d_name;
		this.c_tiket_rating = c_tiket_rating;
		this.c_tiket_sales = c_tiket_sales;
		this.r_product_name = r_product_name;
		this.r_add_product = r_add_product;
		this.r_delete_product = r_delete_product;
		this.r_total_price = r_total_price;
		this.r_date = r_date;
		this.r_visit_time = r_visit_time;
		this.r_condetion = r_condetion;
	}

	public int getP_no() {
		return p_no;
	}

	public void setP_no(int p_no) {
		this.p_no = p_no;
	}

	public int getC_code() {
		return c_code;
	}

	public void setC_code(int c_code) {
		this.c_code = c_code;
	}

	public int getD_code() {
		return d_code;
	}

	public void setD_code(int d_code) {
		this.d_code = d_code;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getP_price() {
		return p_price;
	}

	public void setP_price(String p_price) {
		this.p_price = p_price;
	}

	public int getR_code() {
		return r_code;
	}

	public void setR_code(int r_code) {
		this.r_code = r_code;
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

	public String getD_name() {
		return d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public String getC_tiket_rating() {
		return c_tiket_rating;
	}

	public void setC_tiket_rating(String c_tiket_rating) {
		this.c_tiket_rating = c_tiket_rating;
	}

	public String getC_tiket_sales() {
		return c_tiket_sales;
	}

	public void setC_tiket_sales(String c_tiket_sales) {
		this.c_tiket_sales = c_tiket_sales;
	}

	public String getR_product_name() {
		return r_product_name;
	}

	public void setR_product_name(String r_product_name) {
		this.r_product_name = r_product_name;
	}

	public String getR_add_product() {
		return r_add_product;
	}

	public void setR_add_product(String r_add_product) {
		this.r_add_product = r_add_product;
	}

	public String getR_delete_product() {
		return r_delete_product;
	}

	public void setR_delete_product(String r_delete_product) {
		this.r_delete_product = r_delete_product;
	}

	public String getR_total_price() {
		return r_total_price;
	}

	public void setR_total_price(String r_total_price) {
		this.r_total_price = r_total_price;
	}

	public String getR_date() {
		return r_date;
	}

	public void setR_date(String r_date) {
		this.r_date = r_date;
	}

	public String getR_visit_time() {
		return r_visit_time;
	}

	public void setR_visit_time(String r_visit_time) {
		this.r_visit_time = r_visit_time;
	}

	public String getR_condetion() {
		return r_condetion;
	}

	public void setR_condetion(String r_condetion) {
		this.r_condetion = r_condetion;
	}

	@Override
	public String toString() {
		return "ReservationVO [p_no=" + p_no + ", c_code=" + c_code + ", d_code=" + d_code + ", p_name=" + p_name
				+ ", p_price=" + p_price + ", r_code=" + r_code + ", c_name=" + c_name + ", c_gender=" + c_gender
				+ ", c_phone=" + c_phone + ", d_name=" + d_name + ", c_tiket_rating=" + c_tiket_rating
				+ ", c_tiket_sales=" + c_tiket_sales + ", r_product_name=" + r_product_name + ", r_add_product="
				+ r_add_product + ", r_delete_product=" + r_delete_product + ", r_total_price=" + r_total_price
				+ ", r_date=" + r_date + ", r_visit_time=" + r_visit_time + ", r_condetion=" + r_condetion + "]";
	}
	
	

}