package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;
import model.DesignerVO;
import model.ReservationVO;

public class ReservationDAO {

	// 신규 예약 정보 등록
	public ReservationVO getReservationJoin(ReservationVO rVo) throws Exception {

		String sql = "insert into reservation (r_code,r_date,r_visit_time,r_product_name,r_total_price,r_condition,p_no,c_code,d_code) values (reservation_seq.nextval,?,?,?,?,'대기',?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		ReservationVO retval = null;

		try {
			// DBUtil 이라는 클래스의 getConnenction()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 입력 받은 예약 정보를 처리하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rVo.getR_date());
			pstmt.setString(2, rVo.getR_visit_time());
			pstmt.setString(3, rVo.getR_product_name());
			pstmt.setString(4, rVo.getR_total_price());
			pstmt.setInt(5, rVo.getP_no());
			pstmt.setInt(6, rVo.getC_code());
			pstmt.setInt(7, rVo.getD_code());

			// SQL문을 수행 후 처리 결과 얻어온다.
			int i = pstmt.executeUpdate();
			retval = new ReservationVO();

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

	// 예약 ALL 전체 리스트
	public ArrayList<ReservationVO> getReservationTotal() throws Exception {

		ArrayList<ReservationVO> list = new ArrayList<>();

		String sql = "select r.r_code as r_code, c.c_name as c_name, c.c_gender as c_gender, c.c_phone as c_phone, c.c_tiket_rating as c_tiket_rating, c.c_tiket_sales as c_tiket_sales, d.d_name as d_name, r.r_product_name as r_product_name, r.r_add_product as r_add_product, r.r_delete_product as r_delete_product, r.r_total_price as r_total_price, r.r_date as r_date, r.r_visit_time as r_visit_time, r.r_condition as r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다
			while (rs.next()) { // 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				rVo = new ReservationVO();
				rVo.setR_code(rs.getInt("r_code"));
				rVo.setC_name(rs.getString("c_name"));
				rVo.setC_gender(rs.getString("c_gender"));
				rVo.setC_phone(rs.getString("c_phone"));
				rVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				rVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				rVo.setD_name(rs.getString("d_name"));
				rVo.setR_product_name(rs.getString("r_product_name"));
				rVo.setR_add_product(rs.getString("r_add_product"));
				rVo.setR_delete_product(rs.getString("r_delete_product"));
				rVo.setR_total_price(rs.getString("r_total_price"));
				rVo.setR_date(rs.getString("r_date"));
				rVo.setR_visit_time(rs.getString("r_visit_time"));
				rVo.setR_condetion(rs.getString("r_condition"));
				list.add(rVo);
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

	// 예약 대기 전체 리스트
	public ArrayList<ReservationVO> getReservationConditionNullTotal() throws Exception {

		ArrayList<ReservationVO> list = new ArrayList<>();

		String sql = "select r.r_code as r_code, c.c_name as c_name, c.c_gender as c_gender, c.c_phone as c_phone, c.c_tiket_rating as c_tiket_rating, c.c_tiket_sales as c_tiket_sales, d.d_name as d_name, r.r_product_name as r_product_name, r.r_add_product as r_add_product, r.r_delete_product as r_delete_product, r.r_total_price as r_total_price, r.r_date as r_date, r.r_visit_time as r_visit_time, r.r_condition as r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code and r.r_condition ='대기'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationVO rVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다
			while (rs.next()) { // 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				rVo = new ReservationVO();
				rVo.setR_code(rs.getInt("r_code"));
				rVo.setC_name(rs.getString("c_name"));
				rVo.setC_gender(rs.getString("c_gender"));
				rVo.setC_phone(rs.getString("c_phone"));
				rVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				rVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				rVo.setD_name(rs.getString("d_name"));
				rVo.setR_product_name(rs.getString("r_product_name"));
				rVo.setR_add_product(rs.getString("r_add_product"));
				rVo.setR_delete_product(rs.getString("r_delete_product"));
				rVo.setR_total_price(rs.getString("r_total_price"));
				rVo.setR_date(rs.getString("r_date"));
				rVo.setR_visit_time(rs.getString("r_visit_time"));
				rVo.setR_condetion(rs.getString("r_condition"));
				list.add(rVo);
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

	// 예약 테이블 컬럼의 갯수
	public ArrayList<String> getReservationColumnName() throws Exception {

		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select r.r_code,c.c_name,c.c_gender,c.c_phone,c.c_tiket_rating,c.c_tiket_sales,d.d_name,r.r_product_name,r.r_add_product, r.r_delete_product,r.r_date,r.r_visit_time,r.r_total_price,r.r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData 객체 변수 선언
		ResultSetMetaData rsmd = null;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			rs = pstmt.executeQuery(); // 쿼리 실행
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
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
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

	// 예약 정보 회원이름으로 검색
	public ArrayList<ReservationVO> getReservationSearchC_name(String c_name) throws Exception {

		// ArrayList배열 생성
		ArrayList<ReservationVO> list = new ArrayList<>();

		String sql = "select r.r_code,c.c_name,c.c_gender,c.c_phone,c.c_tiket_rating,c.c_tiket_sales,d.d_name,r.r_product_name,r.r_add_product, r.r_delete_product,r.r_date,r.r_visit_time,r.r_total_price,r.r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code and c.c_name =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ReservationVO rVo = null;
		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			// 회원이름 입력
			pstmt.setString(1, c_name);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				rVo = new ReservationVO();
				rVo.setR_code(rs.getInt("r_code"));
				rVo.setC_name(rs.getString("c_name"));
				rVo.setC_gender(rs.getString("c_gender"));
				rVo.setC_phone(rs.getString("c_phone"));
				rVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				rVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				rVo.setD_name(rs.getString("d_name"));
				rVo.setR_product_name(rs.getString("r_product_name"));
				rVo.setR_add_product(rs.getString("r_add_product"));
				rVo.setR_delete_product(rs.getString("r_delete_product"));
				rVo.setR_total_price(rs.getString("r_total_price"));
				rVo.setR_date(rs.getString("r_date"));
				rVo.setR_visit_time(rs.getString("r_visit_time"));
				rVo.setR_condetion(rs.getString("r_condition"));
				list.add(rVo);
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
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

	// 예약 정보 회원번호로 검색
	public ArrayList<ReservationVO> getReservationSearchC_phone(String c_phone) throws Exception {

		// ArrayList배열 생성
		ArrayList<ReservationVO> list = new ArrayList<>();

		String sql = "select r.r_code,c.c_name,c.c_gender,c.c_phone,c.c_tiket_rating,c.c_tiket_sales,d.d_name,r.r_product_name,r.r_add_product, r.r_delete_product,r.r_date,r.r_visit_time,r.r_total_price,r.r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code and c.c_phone =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ReservationVO rVo = null;
		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			// 회원이름 입력
			pstmt.setString(1, c_phone);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				rVo = new ReservationVO();
				rVo.setR_code(rs.getInt("r_code"));
				rVo.setC_name(rs.getString("c_name"));
				rVo.setC_gender(rs.getString("c_gender"));
				rVo.setC_phone(rs.getString("c_phone"));
				rVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				rVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				rVo.setD_name(rs.getString("d_name"));
				rVo.setR_product_name(rs.getString("r_product_name"));
				rVo.setR_add_product(rs.getString("r_add_product"));
				rVo.setR_delete_product(rs.getString("r_delete_product"));
				rVo.setR_total_price(rs.getString("r_total_price"));
				rVo.setR_date(rs.getString("r_date"));
				rVo.setR_visit_time(rs.getString("r_visit_time"));
				rVo.setR_condetion(rs.getString("r_condition"));
				list.add(rVo);
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
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

	// 예약 정보 디자이너 이름으로 검색
	public ArrayList<ReservationVO> getReservationSearchD_name(String d_name) throws Exception {

		// ArrayList배열 생성
		ArrayList<ReservationVO> list = new ArrayList<>();

		String sql = "select r.r_code,c.c_name,c.c_gender,c.c_phone,c.c_tiket_rating,c.c_tiket_sales,d.d_name,r.r_product_name,r.r_add_product, r.r_delete_product,r.r_date,r.r_visit_time,r.r_total_price,r.r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code and d.d_name =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ReservationVO rVo = null;
		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			// 회원이름 입력
			pstmt.setString(1, d_name);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				rVo = new ReservationVO();
				rVo.setR_code(rs.getInt("r_code"));
				rVo.setC_name(rs.getString("c_name"));
				rVo.setC_gender(rs.getString("c_gender"));
				rVo.setC_phone(rs.getString("c_phone"));
				rVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				rVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				rVo.setD_name(rs.getString("d_name"));
				rVo.setR_product_name(rs.getString("r_product_name"));
				rVo.setR_add_product(rs.getString("r_add_product"));
				rVo.setR_delete_product(rs.getString("r_delete_product"));
				rVo.setR_total_price(rs.getString("r_total_price"));
				rVo.setR_date(rs.getString("r_date"));
				rVo.setR_visit_time(rs.getString("r_visit_time"));
				rVo.setR_condetion(rs.getString("r_condition"));
				list.add(rVo);
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
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

	// 선택한 예약 정보 의 데이터 삭제
	public void getReservationDelete(int r_code) throws Exception {

		// 데이터 처리를 위한 SQL문
		StringBuffer sql = new StringBuffer();
		sql.append("delete from reservation where r_code = ?");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, r_code);

			System.out.println(r_code + "삭제시 r코드 확인바람!");

			// SQL문을 수행 후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("예약 삭제");
				alert.setHeaderText("예약 정보 삭제 완료");
				alert.setContentText("예약 정보 삭제 성공.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("예약 삭제");
				alert.setHeaderText("예약 정보 삭제 실패");
				alert.setContentText("예약 정보 삭제 실패.");
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

	// 상품추가시 수정되는 예약정보 메소드
	public boolean getReservationTotalPnamePpriceUpdate(String r_product_name, String r_total_price,
			String r_add_product, int r_code) {
		String sql = "update reservation set r_product_name =?, r_total_price =? , r_add_product =?  where r_code =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean reservationUpdateSucess = false;

		try {
			con = DBUtil.getConnection();// dbutil 연결
			pstmt = con.prepareStatement(sql);// sql문을 PreparedStatement로 실행한다.
			pstmt.setString(1, r_product_name);
			pstmt.setString(2, r_total_price);
			pstmt.setString(3, r_add_product);
			pstmt.setInt(4, r_code);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("예약 상품 정보 수정");
				alert.setHeaderText(" 예약 상품 정보 수정 완료");
				alert.setContentText("예약 상품 정보 수정 성공!!!");
				alert.showAndWait();
				reservationUpdateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("예약 상품 정보 수정");
				alert.setHeaderText("예약 상품 수정 실패");
				alert.setContentText("예약 상품  수정 실패!!!");
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
		return reservationUpdateSucess;
	}

	// 결제버튼 클릭시 예약상태에 확정 넣기
	public boolean getReservationConditionUpdate(int r_code) throws Exception {
		String sql = "update reservation set r_condition = '확정' where r_code=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean updateSucess = false;
		try {
			con = DBUtil.getConnection();// dbutil 연결
			pstmt = con.prepareStatement(sql);// sql문을 PreparedStatement로 실행한다.
			pstmt.setInt(1, r_code);

			int i = pstmt.executeUpdate();
			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("결제 정보 ");
				alert.setHeaderText("결제 완료");
				alert.setContentText("결제 완료");
				alert.showAndWait();
				updateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("결제 정보");
				alert.setHeaderText("결제 실패");
				alert.setContentText("결제 실패");
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
		return updateSucess;
	}

	// 예약 정보 탭에서 이름 으로 검색
	public ArrayList<ReservationVO> getReservationCNameSearchList(String c_name) throws Exception {
		// ArrayList배열 생성
		ArrayList<ReservationVO> list = new ArrayList<>();
		// 이름으로 데이터를 가져오는 sql문
		String sql = "select r.r_code as r_code, c.c_name as c_name, c.c_gender as c_gender, c.c_phone as c_phone, c.c_tiket_rating as c_tiket_rating, c.c_tiket_sales as c_tiket_sales, d.d_name as d_name, r.r_product_name as r_product_name, r.r_add_product as r_add_product, r.r_delete_product as r_delete_product, r.r_total_price as r_total_price, r.r_date as r_date, r.r_visit_time as r_visit_time, r.r_condition as r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code and r.r_condition='대기' and c.c_name =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 인스턴스 생성
		ReservationVO rVo = null;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// 학번입력
			pstmt.setString(1, c_name);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 인스턴스 생성
				rVo = new ReservationVO();
				rVo.setR_code(rs.getInt("r_code"));
				rVo.setC_name(rs.getString("c_name"));
				rVo.setC_gender(rs.getString("c_gender"));
				rVo.setC_phone(rs.getString("c_phone"));
				rVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				rVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				rVo.setD_name(rs.getString("d_name"));
				rVo.setR_product_name(rs.getString("r_product_name"));
				rVo.setR_total_price(rs.getString("r_total_price"));
				rVo.setR_date(rs.getString("r_date"));
				rVo.setR_visit_time(rs.getString("r_visit_time"));
				// 필드값을 설정해준후 arraylist배열에 객체를 추가한다.
				list.add(rVo);

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

		// customerVO 객체 배열 반환
		return list;
	}

	// 예약 정보 탭에서 전화번호 으로 검색
	public ArrayList<ReservationVO> getReservationCPhoneSearchList(String c_phone) throws Exception {
		// ArrayList배열 생성
		ArrayList<ReservationVO> list = new ArrayList<>();
		// 이름으로 데이터를 가져오는 sql문
		String sql = "select r.r_code as r_code, c.c_name as c_name, c.c_gender as c_gender, c.c_phone as c_phone, c.c_tiket_rating as c_tiket_rating, c.c_tiket_sales as c_tiket_sales, d.d_name as d_name, r.r_product_name as r_product_name, r.r_add_product as r_add_product, r.r_delete_product as r_delete_product, r.r_total_price as r_total_price, r.r_date as r_date, r.r_visit_time as r_visit_time, r.r_condition as r_condition  "
				+ " from customer c, designer d, product p, reservation r "
				+ " where p.p_no = r.p_no and c.c_code = r.c_code and d.d_code=r.d_code and r.r_condition='대기' and c.c_phone =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 인스턴스 생성
		ReservationVO rVo = null;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// 학번입력
			pstmt.setString(1, c_phone);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 인스턴스 생성
				rVo = new ReservationVO();
				rVo.setR_code(rs.getInt("r_code"));
				rVo.setC_name(rs.getString("c_name"));
				rVo.setC_gender(rs.getString("c_gender"));
				rVo.setC_phone(rs.getString("c_phone"));
				rVo.setC_tiket_rating(rs.getString("c_tiket_rating"));
				rVo.setC_tiket_sales(rs.getString("c_tiket_sales"));
				rVo.setD_name(rs.getString("d_name"));
				rVo.setR_product_name(rs.getString("r_product_name"));
				rVo.setR_total_price(rs.getString("r_total_price"));
				rVo.setR_date(rs.getString("r_date"));
				rVo.setR_visit_time(rs.getString("r_visit_time"));
				// 필드값을 설정해준후 arraylist배열에 객체를 추가한다.
				list.add(rVo);

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

		// customerVO 객체 배열 반환
		return list;
	}
}
