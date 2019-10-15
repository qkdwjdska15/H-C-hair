package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;

public class CustomerDAO {
	// 날짜 1년뒤 계산 메소드
	public String getSysdate() throws Exception {
		String sql = "select to_char(sysdate + 365) as a from dual";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sys = null;
		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담아줄 그릇
			pstmt = con.prepareStatement(sql);
			// jvo에서 변수들을 가져와서 sql문에 넣어준다.
			rs = pstmt.executeQuery();
			// sql을 날리고 불러온 값이 있으면 로그인결과변수 true
			if (rs.next()) {
				sys = rs.getString("A");
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {

			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return sys;
	}

	// 신규 고객 등록
	public CustomerVO getCustomerRegiste(CustomerVO cVo) throws Exception {

		// 데이터 처리를 위한 SQL문
		StringBuffer sql = new StringBuffer();
		sql.append("insert into customer");
		sql.append(" (c_code,c_name,c_gender,c_phone,c_age,c_address,c_tiket_rating,c_expiration_date,c_tiket_sales)");
		sql.append(" values (customer_seq.nextval,?,?,?,?,?,?,TO_CHAR(sysdate+365),?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		CustomerVO retval = null;

		try {
			// DBUtil 이라는 클래스의 getConnenction()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 입력받은 학생 정보를 처리하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, cVo.getC_name());
			pstmt.setString(2, cVo.getC_gender());
			pstmt.setString(3, cVo.getC_phone());
			pstmt.setString(4, cVo.getC_age());
			pstmt.setString(5, cVo.getC_address());
			pstmt.setString(6, cVo.getC_tiket_rating());
			pstmt.setString(7, cVo.getC_tiket_sales());

			// SQL문을 수행 후 처리 결과 얻어온다.
			int i = pstmt.executeUpdate();
			System.out.println(i+"이게 번호라는거야");
			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("회원 정보 등록");
				alert.setHeaderText(cVo.getC_name() + " 회원 정보 등록 완료");
				alert.setContentText("회원 정보 등록 성공.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("회원 정보 등록");
				alert.setHeaderText("회원 정보 등록 실패.");
				alert.setContentText("회원 정보 등록 실패.");
				alert.showAndWait();
			}
			retval = new CustomerVO();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return retval;
	}

	// 데이터베이스에서 회원 테이블 컬럼의 갯수
	public ArrayList<String> getColumnName() {

		ArrayList<String> columnName = new ArrayList<String>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from customer");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData 객체 변수 선언
		ResultSetMetaData rsmd = null;

		try {

			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();

			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getCatalogName(i));
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException se) {
			}
		}
		return columnName;

	}

	// 회원 전체 리스트
	public ArrayList<CustomerVO> getCustomerTotal() {

		ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from customer order by c_code ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO cVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				cVo = new CustomerVO();

				cVo.setC_code(rs.getInt("c_code"));
				cVo.setC_name(rs.getString("c_name"));
				cVo.setC_gender(rs.getString("c_gender"));
				cVo.setC_phone(rs.getString("c_phone"));
				cVo.setC_age(rs.getString("c_age"));
				cVo.setC_address(rs.getString("c_address"));
				cVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				cVo.setC_expiration_date(rs.getString("c_expiration_date"));
				cVo.setC_tiket_sales(rs.getString("c_tiket_sales"));

				list.add(cVo); // 다음 리스트에 결과값 추가
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// 5. 데이터베이스와의 연결에 사용되었던 오브젝트 해제
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException se) {
			}
		}

		return list;

	}

	// 회원 정보 수정
	public boolean getCustomerUpdate(String c_name, String c_phone, String c_address) throws Exception {
		String sql = "update customer set c_phone = ? ,  c_address =?  where c_name =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean customerUpdateSucess = false;

		try {
			con = DBUtil.getConnection();// dbutil 연결
			pstmt = con.prepareStatement(sql);// sql문을 PreparedStatement로 실행한다.
			pstmt.setString(1, c_phone);
			pstmt.setString(2, c_address);
			pstmt.setString(3, c_name);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("회원 정보 수정");
				alert.setHeaderText(c_name + " 회원 정보 수정 완료");
				alert.setContentText("회원 정보 수정 성공!!!");
				alert.showAndWait();
				customerUpdateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("회원 정보 수정");
				alert.setHeaderText("회원 정보 수정 실패");
				alert.setContentText("회원 정보 수정 실패!!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return customerUpdateSucess;
	}

	// 선택한 학생의 데이터 삭제
	public void getCustomerDelete(int c_code) throws Exception {

		// 데이터 처리를 위한 SQL문
		StringBuffer sql = new StringBuffer();
		sql.append("delete from customer where c_code = ?" );

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, c_code);

			System.out.println(c_code);

			// SQL문을 수행 후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("회원 삭제");
				alert.setHeaderText("회원 정보 삭제 완료");
				alert.setContentText("회원 정보 삭제 성공.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("회원 삭제");
				alert.setHeaderText("회원 정보 삭제 실패");
				alert.setContentText("회원 정보 삭제 실패.");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트 해제
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	// 회원 정보 탭에서 이름 으로 검색
	public ArrayList<CustomerVO> getCustomerCNameSearchList(String c_name) throws Exception {
		// ArrayList배열 생성
		ArrayList<CustomerVO> list = new ArrayList<>();
		// 이름으로 데이터를 가져오는 sql문
		String sql = "select * from customer where c_name = ? order by c_code";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 인스턴스 생성
		CustomerVO cVo = null;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// 학번입력
			pstmt.setString(1, c_name);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//인스턴스 생성
				cVo = new CustomerVO();
				//쿼이문을 날리고 얻은 결과에서 값을 가져와 객체의 필드값을 설정한다.
				cVo.setC_code(rs.getInt("c_code"));
				cVo.setC_name(rs.getString("c_name"));
				cVo.setC_gender(rs.getString("c_gender"));
				cVo.setC_phone(rs.getString("c_phone"));
				cVo.setC_age(rs.getString("c_age"));
				cVo.setC_address(rs.getString("c_address"));
				cVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				cVo.setC_expiration_date(rs.getString("c_expiration_date"));
				cVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				//필드값을 설정해준후 arraylist배열에 객체를 추갛ㄴ다.
				list.add(cVo);
				
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// DB연결 해제
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
			}
		}
		
		//customerVO 객체 배열 반환
		return list;
	}
	// 회원 정보 탭에서 전화번호로 검색
	public ArrayList<CustomerVO> getCustomerCPhoneSearchList(String c_phone) throws Exception {
		// ArrayList배열 생성
		ArrayList<CustomerVO> list = new ArrayList<>();
		// 이름으로 데이터를 가져오는 sql문
		String sql = "select * from customer where c_phone = ? order by c_code";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 인스턴스 생성
		CustomerVO cVo = null;
		
		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// 학번입력
			pstmt.setString(1, c_phone);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//인스턴스 생성
				cVo = new CustomerVO();
				//쿼이문을 날리고 얻은 결과에서 값을 가져와 객체의 필드값을 설정한다.
				cVo.setC_code(rs.getInt("c_code"));
				cVo.setC_name(rs.getString("c_name"));
				cVo.setC_gender(rs.getString("c_gender"));
				cVo.setC_phone(rs.getString("c_phone"));
				cVo.setC_age(rs.getString("c_age"));
				cVo.setC_address(rs.getString("c_address"));
				cVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				cVo.setC_expiration_date(rs.getString("c_expiration_date"));
				cVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				//필드값을 설정해준후 arraylist배열에 객체를 추갛ㄴ다.
				list.add(cVo);
				
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// DB연결 해제
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				
			} catch (SQLException e) {
			}
		}
		
		//customerVO 객체 배열 반환
		return list;
	}
}
