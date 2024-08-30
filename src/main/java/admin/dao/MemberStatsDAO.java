package admin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MemberStatsDAO {
	private Connection conn;

	public MemberStatsDAO() {
		// DB 연결 초기화
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbUser = "how_yogo";
			String dbPass = "1234";
			conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Integer> getMemberStats(int year) {
		Map<String, Integer> memberStats = new HashMap<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// 회원가입 수 조회 쿼리
			String sql = "SELECT EXTRACT(MONTH FROM regdate) AS month, COUNT(*) AS count " + "FROM member "
					+ "WHERE EXTRACT(YEAR FROM regdate) = ? " + "GROUP BY EXTRACT(MONTH FROM regdate)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, year);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int month = rs.getInt("month");
				int count = rs.getInt("count");
				memberStats.put(String.valueOf(month), count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close resources
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return memberStats;
	}

}
