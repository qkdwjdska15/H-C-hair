package model;

public class ProductVO {
	private int p_no; //상품 번호
	private String p_code;//상품 코드
	private String p_name;//상품 이름
	private String p_price;//상품 가격
	
	public ProductVO() {
		super();
	}

	public ProductVO(String p_code, String p_name, String p_price) {
		super();
		this.p_code = p_code;
		this.p_name = p_name;
		this.p_price = p_price;
	}

	public ProductVO(String p_name, String p_price) {
		super();
		this.p_name = p_name;
		this.p_price = p_price;
	}

	public ProductVO(int p_no, String p_code, String p_name, String p_price) {
		super();
		this.p_no = p_no;
		this.p_code = p_code;
		this.p_name = p_name;
		this.p_price = p_price;
	}

	public int getP_no() {
		return p_no;
	}

	public void setP_no(int p_no) {
		this.p_no = p_no;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
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

	@Override
	public String toString() {
		return "productVO [p_no=" + p_no + ", p_code=" + p_code + ", p_name=" + p_name + ", p_price=" + p_price + "]";
	}
	
	
	
}
