package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;
import model.DesignerVO;

public class DesignerDAO {

	// 데이터베이스에서 디자이너 테이블 컬럼의 갯수
	public ArrayList<String> getDColumnName() {

		ArrayList<String> columnName = new ArrayList<String>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from designer");
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

	// 디자이너 전체 리스트
	public ArrayList<DesignerVO> getDesignerTotal() {

		ArrayList<DesignerVO> list = new ArrayList<DesignerVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from designer order by d_code ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DesignerVO dVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				dVo = new DesignerVO();

				dVo.setD_code(rs.getInt("d_code"));
				dVo.setD_name(rs.getString("d_name"));
				dVo.setD_gender(rs.getString("d_gender"));
				dVo.setD_phone(rs.getString("d_phone"));
				dVo.setD_age(rs.getString("d_age"));
				dVo.setD_address(rs.getString("d_address"));
				dVo.setD_id(rs.getString("d_id"));
				dVo.setD_pw(rs.getString("d_pw"));
				list.add(dVo); // 다음 리스트에 결과값 추가
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

	// 신규 디자이너 등록
	public DesignerVO getDesignerRegiste(DesignerVO dVo) throws Exception {

		// 데이터 처리를 위한 SQL문
		StringBuffer sql = new StringBuffer();
		sql.append("insert into designer");
		sql.append(" (d_code,d_name,d_gender,d_phone,d_age,d_address,d_id,d_pw)");
		sql.append(" values (designer_seq.nextval,?,?,?,?,?,?,?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		DesignerVO retval = null;

		try {
			// DBUtil 이라는 클래스의 getConnenction()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 입력받은 학생 정보를 처리하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dVo.getD_name());
			pstmt.setString(2, dVo.getD_gender());
			pstmt.setString(3, dVo.getD_phone());
			pstmt.setString(4, dVo.getD_age());
			pstmt.setString(5, dVo.getD_address());
			pstmt.setString(6, dVo.getD_id());
			pstmt.setString(7, dVo.getD_pw());

			// SQL문을 수행 후 처리 결과 얻어온다.
			int i = pstmt.executeUpdate();

			retval = new DesignerVO();

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

	// 디자이너 아이디 중복 체크
	public boolean getStudentIdOverlap(String idOverlap) throws Exception {

		String sql = "select * from designer where d_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean idOverlapResult = false; // 중복된 아이디 없음

		try {
			con = DBUtil.getConnection(); // DBUtil 연결
			pstmt = con.prepareStatement(sql); // sql문을 prepareStatement로 실행한다
			pstmt.setString(1, idOverlap); // prepareStatement 객체로 sql쿼리를 실행해 그 쿼리에 의해 생성된 resultSet 객체를 돌려줌
			rs = pstmt.executeQuery(); // 쿼리 실행

			if (rs.next()) {
				idOverlapResult = true; // 중복된 아이디가 있음
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
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
			} catch (SQLException e) {
			}
		}

		return idOverlapResult;
	}

	// 디자이너 정보 수정
	public boolean getDesignerUpdate(String d_name, String d_phone, String d_address, String d_id, String d_pw)
			throws Exception {
		String sql = "update designer set d_phone = ? ,  d_address =?, d_id=?, d_pw=?  where d_name =?";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean designerUpdateSucess = false;

		try {
			con = DBUtil.getConnection();// dbutil 연결
			pstmt = con.prepareStatement(sql);// sql문을 PreparedStatement로 실행한다.
			pstmt.setString(1, d_phone);
			pstmt.setString(2, d_address);
			pstmt.setString(3, d_id);
			pstmt.setString(4, d_pw);
			pstmt.setString(5, d_name);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("디자이너 정보 수정");
				alert.setHeaderText(d_name + " 디자이너 정보 수정 완료");
				alert.setContentText("디자이너 정보 수정 성공.");
				alert.showAndWait();
				designerUpdateSucess = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("디자이너 정보 수정");
				alert.setHeaderText("디자이너 정보 수정 실패");
				alert.setContentText("디자이너 정보 수정 실패.");
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
		System.out.println(designerUpdateSucess);
		return designerUpdateSucess;
	}

	// 선택한 디자이너의 데이터 삭제
	public void getDesignerDelete(int d_code) throws Exception {

		// 데이터 처리를 위한 SQL문
		StringBuffer sql = new StringBuffer();
		sql.append("delete from designer where d_code = ?");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, d_code);

			System.out.println(d_code);

			// SQL문을 수행 후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("디자이너 정보 삭제");
				alert.setHeaderText("디자이너 정보 삭제 완료");
				alert.setContentText("디자이너 정보 삭제 성공.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("디자이너 정보 삭제");
				alert.setHeaderText("디자이너 정보 삭제 실패");
				alert.setContentText("디자이너 정보 삭제 실패.");
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

	// 디자이너 정보 탭에서 이름 으로 검색
	public ArrayList<DesignerVO> getDesignerDNameSearchList(String d_name) throws Exception {
		// ArrayList배열 생성
		ArrayList<DesignerVO> list = new ArrayList<>();
		// 이름으로 데이터를 가져오는 sql문
		String sql = "select * from designer where d_name = ? order by d_code";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 인스턴스 생성
		DesignerVO dVo = null;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// 학번입력
			pstmt.setString(1, d_name);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 인스턴스 생성
				dVo = new DesignerVO();
				// 쿼이문을 날리고 얻은 결과에서 값을 가져와 객체의 필드값을 설정한다.
				dVo.setD_code(rs.getInt("d_code"));
				dVo.setD_name(rs.getString("d_name"));
				dVo.setD_gender(rs.getString("d_gender"));
				dVo.setD_phone(rs.getString("d_phone"));
				dVo.setD_age(rs.getString("d_age"));
				dVo.setD_address(rs.getString("d_address"));
				dVo.setD_id(rs.getString("d_id"));
				dVo.setD_pw(rs.getString("d_pw"));

				// 필드값을 설정해준후 arraylist배열에 객체를 추갛ㄴ다.
				list.add(dVo);

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

		// designerVO 객체 배열 반환
		return list;
	}

	// 디자이너 정보 탭에서 이름 으로 검색
	public ArrayList<DesignerVO> getDesignerDPhoneSearchList(String d_phone) throws Exception {
		// ArrayList배열 생성
		ArrayList<DesignerVO> list = new ArrayList<>();
		// 이름으로 데이터를 가져오는 sql문
		String sql = "select * from designer where d_phone = ? order by d_code";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 인스턴스 생성
		DesignerVO dVo = null;

		try {
			// DB연동
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// 학번입력
			pstmt.setString(1, d_phone);
			// sql문을 날리고 결과를 저장
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 인스턴스 생성
				dVo = new DesignerVO();
				// 쿼이문을 날리고 얻은 결과에서 값을 가져와 객체의 필드값을 설정한다.
				dVo.setD_code(rs.getInt("d_code"));
				dVo.setD_name(rs.getString("d_name"));
				dVo.setD_gender(rs.getString("d_gender"));
				dVo.setD_phone(rs.getString("d_phone"));
				dVo.setD_age(rs.getString("d_age"));
				dVo.setD_address(rs.getString("d_address"));
				dVo.setD_id(rs.getString("d_id"));
				dVo.setD_pw(rs.getString("d_pw"));

				// 필드값을 설정해준후 arraylist배열에 객체를 추갛ㄴ다.
				list.add(dVo);

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

		// designerVO 객체 배열 반환
		return list;
	}

	// 디자이너 이름 뽑아내기
	public ArrayList<DesignerVO> getDesignerName() {

		ArrayList<DesignerVO> list = new ArrayList<DesignerVO>();

		StringBuffer sql = new StringBuffer();
		sql.append("select d_name,d_code from designer ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DesignerVO dVo = null;

		try {

			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 회원 정보를 삭제하기 위하여 SQL문장 생성
			pstmt = con.prepareStatement(sql.toString());

			rs = pstmt.executeQuery(); // ResultSet에 결과값을 넣는다

			while (rs.next()) { // 각 객체에 결과값을 넣는다
				dVo = new DesignerVO();

				dVo.setD_name(rs.getString("d_name"));
				dVo.setD_code(rs.getInt("d_code"));
				list.add(dVo); // 다음 리스트에 결과값 추가
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

	

}
