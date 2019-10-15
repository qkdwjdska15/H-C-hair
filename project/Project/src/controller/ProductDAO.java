package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.ProductVO;

public class ProductDAO {
	// 상품 전체 리스트
	public ArrayList<ProductVO> getProductTotal() {

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from product order by p_no ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO pVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				pVo = new ProductVO();

				pVo.setP_no(rs.getInt("p_no"));
				pVo.setP_code(rs.getString("p_code"));
				pVo.setP_name(rs.getString("p_name"));
				pVo.setP_price(rs.getString("p_price"));

				list.add(pVo); // 다음 리스트에 결과값 추가
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

	// 데이터베이스에서 상품 테이블 컬럼의 갯수
	public ArrayList<String> getProductColumnName() throws Exception {

		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from product";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData 객체 변수 선언
		ResultSetMetaData rsmd = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();

			for (int i = 1; i < cols; i++) {
				columnName.add(rsmd.getColumnName(i));
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

	// 콤보박스에서 선택한 분류에 맞게 코드명 자동입력을 위한 메소드
	public String getProductCodeSet() throws Exception {
		String sql = "select productcode_seq.nextval as a from dual";
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

	// 상품 등록
	public void getProductRegiste(ProductVO pvo) throws Exception {

		String sql = "insert into product values " + "(product_seq.nextval, ?, ?,?)";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, pvo.getP_code());
			pstmt.setString(2, pvo.getP_name());
			pstmt.setString(3, pvo.getP_price());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 등록");
				alert.setHeaderText(pvo.getP_name() + " 상품 등록 완료");
				alert.setContentText("상품 등록 성공.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("상품 등록");
				alert.setHeaderText("상품 등록 실패");
				alert.setContentText("상품 등록 실패.");
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
				System.out.println(e);
			}
		}

	}

	// 상품 수정
	public boolean getProductUpdate(int P_no, String P_code, String P_name, String P_price) throws Exception {
		String sql = "update product set p_code = ?, p_name = ?, p_price = ? where p_no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean productUpdateSucess = false;

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, P_code);
			pstmt.setString(2, P_name);
			pstmt.setString(3, P_price);
			pstmt.setInt(4, P_no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("상품 정보 수정");
				alert.setHeaderText(P_name + " 상품 정보 수정 완료");
				alert.setContentText("상품 정보 수정 성공.");
				alert.showAndWait();
				productUpdateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("상품 정보 수정");
				alert.setHeaderText("상품 정보 수정 실패");
				alert.setContentText("상품 정보 수정 실패.");
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
		return productUpdateSucess;
	}

	// 상품 삭제
	public boolean getProductDelete(int p_no) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from product where p_no = ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		boolean productDeleteSucess = false;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, p_no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION); // 기본 다이얼로그 객체 생성
				alert.setTitle("상품 삭제"); // 다이얼로그 타이틀 설정
				alert.setHeaderText("상품 삭제 완료"); // 헤더 텍스트 설정
				alert.setContentText("상품 삭제 성공."); // 컨텐트 텍스트 설정
				alert.setResizable(false); // 리사이즈 불가
				alert.showAndWait(); // 사용자 응답 기다림
				productDeleteSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING); // 기본 다이얼로그 객체 생성
				alert.setTitle("상품 삭제"); // 다이얼로그 타이틀 설정
				alert.setHeaderText("상품 삭제 실패"); // 헤더 텍스트 설정
				alert.setContentText("상품 삭제 실패."); // 컨텐트 텍스트 설정
				alert.setResizable(false); // 리사이즈 불가
				alert.showAndWait(); // 사용자 응답 기다림
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
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

		return productDeleteSucess;

	}

	// 컷트 목록 버튼
	public ArrayList<ProductVO> getProductCutTotal() {

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_code like 'A%'  order by p_no ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO pVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				pVo = new ProductVO();

				pVo.setP_no(rs.getInt("p_no"));
				pVo.setP_code(rs.getString("p_code"));
				pVo.setP_name(rs.getString("p_name"));
				pVo.setP_price(rs.getString("p_price"));

				list.add(pVo); // 다음 리스트에 결과값 추가
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

	// 펌 목록 버튼
	public ArrayList<ProductVO> getProductFirmTotal() {

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_code like 'B%'  order by p_no ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO pVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				pVo = new ProductVO();

				pVo.setP_no(rs.getInt("p_no"));
				pVo.setP_code(rs.getString("p_code"));
				pVo.setP_name(rs.getString("p_name"));
				pVo.setP_price(rs.getString("p_price"));

				list.add(pVo); // 다음 리스트에 결과값 추가
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

	// 볼륨매직 목록 버튼
	public ArrayList<ProductVO> getProductVolumeMagicTotal() {

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_code like 'D%'  order by p_no ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO pVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				pVo = new ProductVO();

				pVo.setP_no(rs.getInt("p_no"));
				pVo.setP_code(rs.getString("p_code"));
				pVo.setP_name(rs.getString("p_name"));
				pVo.setP_price(rs.getString("p_price"));

				list.add(pVo); // 다음 리스트에 결과값 추가
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

	// 염색 목록 버튼
	public ArrayList<ProductVO> getProductDyeingTotal() {

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_code like 'E%'  order by p_no ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO pVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				pVo = new ProductVO();

				pVo.setP_no(rs.getInt("p_no"));
				pVo.setP_code(rs.getString("p_code"));
				pVo.setP_name(rs.getString("p_name"));
				pVo.setP_price(rs.getString("p_price"));

				list.add(pVo); // 다음 리스트에 결과값 추가
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

	// 디지털&셋팅 목록 버튼
	public ArrayList<ProductVO> getProductDigitalSettingTotal() {

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from product where p_code like 'C%'  order by p_no ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO pVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				pVo = new ProductVO();

				pVo.setP_no(rs.getInt("p_no"));
				pVo.setP_code(rs.getString("p_code"));
				pVo.setP_name(rs.getString("p_name"));
				pVo.setP_price(rs.getString("p_price"));

				list.add(pVo); // 다음 리스트에 결과값 추가
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

	// 크리닉 목록 버튼
	public ArrayList<ProductVO> getProductClinicTotal() {

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		StringBuffer sql = new StringBuffer();
		//상품 테이블에서 앞의 글자가F로시작하는 전체목록을 p_no내림차순으로 불러온다.
		sql.append("select * from product where p_code like 'F%'  order by p_no ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO pVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				pVo = new ProductVO();

				pVo.setP_no(rs.getInt("p_no"));
				pVo.setP_code(rs.getString("p_code"));
				pVo.setP_name(rs.getString("p_name"));
				pVo.setP_price(rs.getString("p_price"));

				list.add(pVo); // 다음 리스트에 결과값 추가
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
	
	//상품 번호로 상품이름 뽑는 메소드
		public String getProductName(String p_name) {


			StringBuffer sql = new StringBuffer();
			sql.append("select p_name from product where p_no=?");

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
				con = DBUtil.getConnection();

				// 회원 정보를 삭제하기 위하여 SQL문장 생성
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, p_name);




			} catch (SQLException se) {
				System.out.println(se);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					// 5. 데이터베이스와의 연결에 사용되었던 오브젝트 해제
					if (pstmt != null) {
						pstmt.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException se) {
				}
			}

			return p_name;

		}



}
