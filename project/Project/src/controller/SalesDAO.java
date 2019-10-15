package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import model.SalesVO;
import model.ReservationVO;

public class SalesDAO {
	// 매장현황 테이블 컬럼의 갯수
		public ArrayList<String> getSalesColumnName() throws Exception {

			ArrayList<String> columnName = new ArrayList<String>();

			String sql = "select * from sales";
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

	// 매장현황 테이블 등록문
	public SalesVO getSalesJoin(SalesVO mVo) throws Exception {
		String sql = "insert into sales (s_no,s_customer_name,s_customer_gender,s_customer_phone,s_designer_name,s_reservation_product_name,s_reservation_total_price,s_customer_tiket_rating,s_customer_tiket_sales,s_total_price,s_date)"
				+ "values (sales_seq.nextval,?,?,?,?,?,?,?,?,?,sysdate)";

		Connection con = null;
		PreparedStatement pstmt = null;
		SalesVO retval = null;
		try {
			// DBUtil 이라는 클래스의 getConnenction()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mVo.getS_customer_name());
			pstmt.setString(2, mVo.getS_customer_gender());
			pstmt.setString(3, mVo.getS_customer_phone());
			pstmt.setString(4, mVo.getS_designer_name());
			pstmt.setString(5, mVo.getS_reservation_product_name());
			pstmt.setString(6, mVo.getS_reservation_total_price());
			pstmt.setString(7, mVo.getS_customer_tiket_rating());
			pstmt.setString(8, mVo.getS_customer_tiket_sales());
			pstmt.setString(9, mVo.getS_total_price());

			int i = pstmt.executeUpdate();
			retval = new SalesVO();
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

	// 오늘 매장현황 전체 리스트
	public ArrayList<SalesVO> getSalesTotal() throws Exception {
		ArrayList<SalesVO> list = new ArrayList<SalesVO>();

		String sql = "select * from sales where to_char(s_date,'yy/mm/dd')=to_char(sysdate,'yy/mm/dd')";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO mVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) {// 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				mVo = new SalesVO();
				mVo.setS_no(rs.getInt("s_no"));
				mVo.setS_customer_name(rs.getString("s_customer_name"));
				mVo.setS_customer_gender(rs.getString("s_customer_gender"));
				mVo.setS_customer_phone(rs.getString("s_customer_phone"));
				mVo.setS_designer_name(rs.getString("s_designer_name"));
				mVo.setS_reservation_product_name(rs.getString("s_reservation_product_name"));
				mVo.setS_reservation_total_price(rs.getString("s_reservation_total_price"));
				mVo.setS_customer_tiket_rating(rs.getString("s_customer_tiket_rating"));
				mVo.setS_customer_tiket_sales(rs.getString("s_customer_tiket_sales"));
				mVo.setS_total_price(rs.getString("s_total_price"));
				mVo.setS_date(rs.getString("s_date"));
				list.add(mVo);
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
	// 매장현황 전체 리스트
	public ArrayList<SalesVO> getTotalSales() throws Exception {
		ArrayList<SalesVO> list = new ArrayList<SalesVO>();
		
		String sql = "select * from sales ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO mVo = null;
		
		try {
			
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다
			
			while (rs.next()) {// 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				mVo = new SalesVO();
				mVo.setS_no(rs.getInt("s_no"));
				mVo.setS_customer_name(rs.getString("s_customer_name"));
				mVo.setS_customer_gender(rs.getString("s_customer_gender"));
				mVo.setS_customer_phone(rs.getString("s_customer_phone"));
				mVo.setS_designer_name(rs.getString("s_designer_name"));
				mVo.setS_reservation_product_name(rs.getString("s_reservation_product_name"));
				mVo.setS_reservation_total_price(rs.getString("s_reservation_total_price"));
				mVo.setS_customer_tiket_rating(rs.getString("s_customer_tiket_rating"));
				mVo.setS_customer_tiket_sales(rs.getString("s_customer_tiket_sales"));
				mVo.setS_total_price(rs.getString("s_total_price"));
				mVo.setS_date(rs.getString("s_date"));
				list.add(mVo);
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
	// 매장현황 일 로 검색
	public ArrayList<SalesVO> getTotalSalesDaySearch(String day) throws Exception {

		// ArrayList배열 생성
		ArrayList<SalesVO> list = new ArrayList<>();

		String sql = "select * from sales where to_char(s_date,'yy/mm/dd') =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SalesVO sVo = null;
		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			// 회원이름 입력
			pstmt.setString(1,day);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				// 인스턴스 생성
				sVo = new SalesVO();
				sVo.setS_no(rs.getInt("s_no"));
				sVo.setS_customer_name(rs.getString("s_customer_name"));
				sVo.setS_customer_gender(rs.getString("s_customer_gender"));
				sVo.setS_customer_phone(rs.getString("s_customer_phone"));
				sVo.setS_designer_name(rs.getString("s_designer_name"));
				sVo.setS_reservation_product_name(rs.getString("s_reservation_product_name"));
				sVo.setS_reservation_total_price(rs.getString("s_reservation_total_price"));
				sVo.setS_customer_tiket_rating(rs.getString("s_customer_tiket_rating"));
				sVo.setS_customer_tiket_sales(rs.getString("s_customer_tiket_sales"));
				sVo.setS_total_price(rs.getString("s_total_price"));
				sVo.setS_date(rs.getString("s_date"));
				list.add(sVo);
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
	
	// 매장현황 달 로 검색
		public ArrayList<SalesVO> getTotalSalesMonthSearch(String day) throws Exception {

			// ArrayList배열 생성
			ArrayList<SalesVO> list = new ArrayList<>();

			String sql = "select * from sales where to_char(s_date,'yy/mm') =?";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			SalesVO sVo = null;
			try {
				con = DBUtil.getConnection(); // DBUtil 연결
				pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
				// 회원이름 입력
				pstmt.setString(1,day);
				// sql문을 날리고 결과를 저장
				rs = pstmt.executeQuery(); // 쿼리 실행

				while (rs.next()) { // 각 객체에 결과값을 넣는다
					// 인스턴스 생성
					sVo = new SalesVO();
					sVo.setS_no(rs.getInt("s_no"));
					sVo.setS_customer_name(rs.getString("s_customer_name"));
					sVo.setS_customer_gender(rs.getString("s_customer_gender"));
					sVo.setS_customer_phone(rs.getString("s_customer_phone"));
					sVo.setS_designer_name(rs.getString("s_designer_name"));
					sVo.setS_reservation_product_name(rs.getString("s_reservation_product_name"));
					sVo.setS_reservation_total_price(rs.getString("s_reservation_total_price"));
					sVo.setS_customer_tiket_rating(rs.getString("s_customer_tiket_rating"));
					sVo.setS_customer_tiket_sales(rs.getString("s_customer_tiket_sales"));
					sVo.setS_total_price(rs.getString("s_total_price"));
					sVo.setS_date(rs.getString("s_date"));
					list.add(sVo);
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
		// 매장현황 연 으로 검색
		public ArrayList<SalesVO> getTotalSalesYearSearch(String day) throws Exception {
			
			// ArrayList배열 생성
			ArrayList<SalesVO> list = new ArrayList<>();
			
			String sql = "select * from sales where to_char(s_date,'yy') =?";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			SalesVO sVo = null;
			try {
				con = DBUtil.getConnection(); // DBUtil 연결
				pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
				// 회원이름 입력
				pstmt.setString(1,day);
				// sql문을 날리고 결과를 저장
				rs = pstmt.executeQuery(); // 쿼리 실행
				
				while (rs.next()) { // 각 객체에 결과값을 넣는다
					// 인스턴스 생성
					sVo = new SalesVO();
					sVo.setS_no(rs.getInt("s_no"));
					sVo.setS_customer_name(rs.getString("s_customer_name"));
					sVo.setS_customer_gender(rs.getString("s_customer_gender"));
					sVo.setS_customer_phone(rs.getString("s_customer_phone"));
					sVo.setS_designer_name(rs.getString("s_designer_name"));
					sVo.setS_reservation_product_name(rs.getString("s_reservation_product_name"));
					sVo.setS_reservation_total_price(rs.getString("s_reservation_total_price"));
					sVo.setS_customer_tiket_rating(rs.getString("s_customer_tiket_rating"));
					sVo.setS_customer_tiket_sales(rs.getString("s_customer_tiket_sales"));
					sVo.setS_total_price(rs.getString("s_total_price"));
					sVo.setS_date(rs.getString("s_date"));
					list.add(sVo);
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

}
