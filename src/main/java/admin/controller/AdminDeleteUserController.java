package admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/AdminDeleteUser")
public class AdminDeleteUserController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbUser = "how_yogo";
			String dbPass = "1234";
			conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

			String sql = "DELETE FROM member WHERE memberid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				response.getWriter().println("회원 삭제가 완료되었습니다.");
			} else {
				response.getWriter().println("회원 삭제에 실패했습니다.");
			}
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
			response.getWriter().println("회원 삭제에 실패했습니다.");
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
