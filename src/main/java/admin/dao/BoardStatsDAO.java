package admin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import common.DBConnPool;

public class BoardStatsDAO extends DBConnPool {
	private Connection conn;

	public BoardStatsDAO() {
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

	public Map<String, Integer> getBoardStats(int year) {
		Map<String, Integer> boardStats = new HashMap<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT EXTRACT(MONTH FROM postDate) AS month, COUNT(*) AS count " + "FROM recipeBoard "
					+ "WHERE EXTRACT(YEAR FROM postDate) = ? " + "GROUP BY EXTRACT(MONTH FROM postDate)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, year);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int month = rs.getInt("month");
				int count = rs.getInt("count");
				boardStats.put(String.valueOf(month), count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close resources
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return boardStats;
	}
}
