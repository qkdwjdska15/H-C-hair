package model;

public class DesignerVO {
	private int d_code;//디자이너 코드
	private String d_name;//디자이너 이름
	private String d_gender;//디자이너 성별
	private String d_phone;//디자이너 전화번호
	private String d_age;//디자이너 생년월일
	private String d_address;//디자이너 주소
	private String d_id;//디자이너 아이디
	private String d_pw;//디자이너 비밀번호
	
	public DesignerVO() {
		super();
	}
	
	

	public DesignerVO(String d_name) {
		super();
		this.d_name = d_name;
	}

	
	

	public DesignerVO(String d_name, String d_gender, String d_phone, String d_age, String d_address, String d_id,
			String d_pw) {
		super();
		this.d_name = d_name;
		this.d_gender = d_gender;
		this.d_phone = d_phone;
		this.d_age = d_age;
		this.d_address = d_address;
		this.d_id = d_id;
		this.d_pw = d_pw;
	}



	public DesignerVO(int d_code, String d_name, String d_gender, String d_phone, String d_age, String d_address,
			String d_id, String d_pw) {
		super();
		this.d_code = d_code;
		this.d_name = d_name;
		this.d_gender = d_gender;
		this.d_phone = d_phone;
		this.d_age = d_age;
		this.d_address = d_address;
		this.d_id = d_id;
		this.d_pw = d_pw;
	}



	public int getD_code() {
		return d_code;
	}



	public void setD_code(int d_code) {
		this.d_code = d_code;
	}



	public String getD_name() {
		return d_name;
	}



	public void setD_name(String d_name) {
		this.d_name = d_name;
	}



	public String getD_gender() {
		return d_gender;
	}



	public void setD_gender(String d_gender) {
		this.d_gender = d_gender;
	}



	public String getD_phone() {
		return d_phone;
	}



	public void setD_phone(String d_phone) {
		this.d_phone = d_phone;
	}



	public String getD_age() {
		return d_age;
	}



	public void setD_age(String d_age) {
		this.d_age = d_age;
	}



	public String getD_address() {
		return d_address;
	}



	public void setD_address(String d_address) {
		this.d_address = d_address;
	}



	public String getD_id() {
		return d_id;
	}



	public void setD_id(String d_id) {
		this.d_id = d_id;
	}



	public String getD_pw() {
		return d_pw;
	}



	public void setD_pw(String d_pw) {
		this.d_pw = d_pw;
	}



	@Override
	public String toString() {
		return d_name;
	}







	
	
	
	
}
