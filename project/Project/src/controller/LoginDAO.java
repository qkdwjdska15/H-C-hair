package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

	// 관리자 로그인(아이디와 패스워드를 가져와 정보가 있는지 boolean값 반환)
	public boolean getLogin(String admin_id, String admin_pw) throws Exception {
		// id와 password가 있는지 확인하는 sql문
		String sql = "select * from admin where admin_id = ? and admin_pw = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 로그인 결과상태 변수
		boolean loginResult = false;

		try {
			// DB연결
			con = DBUtil.getConnection();
			// sql문을 담을 그릇
			pstmt = con.prepareStatement(sql);
			// id에 입력받은 loginId를 넣는다
			pstmt.setString(1, admin_id);
			// password에 입력받은 loginPassword를 넣는다
			pstmt.setString(2, admin_pw);
			// sql문을 날리고 결과를 저장한다
			rs = pstmt.executeQuery();

			// sql을 날리고 불러온 값이 있으면 로그인결과변수 true
			if (rs.next()) {
				loginResult = true;
			}

		} catch (SQLException e) {
			System.out.println("e = [ " + e + "]");
		} catch (Exception e) {
			System.out.println("e = [ " + e + "]");
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
		// 일치하는 값이 없으면 초기값 그대로 false반환
		return loginResult;
	}
	
}