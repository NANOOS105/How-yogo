package board.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import board.recipe.dto.RecipeBoardDTO;
import common.DBConnPool;

public class RecipeBoardDAO extends DBConnPool {
	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	// 검색 조건에 맞는 게시물의 개수 조회
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		con = DBConnPool.getConnection();
		String query = "SELECT COUNT(*) FROM recipeBoard rb " + "INNER JOIN member m ON rb.memberId = m.memberId";
		if (map.get("searchWord") != null) {
			query += " WHERE rb.recipeTitle LIKE '%" + map.get("searchWord") + "%' " + " OR m.nickname LIKE '%"
					+ map.get("searchWord") + "%' " + " OR rb.ingredients LIKE '%" + map.get("searchWord") + "%' "
					+ " OR rb.recipeCategory LIKE '%" + map.get("searchWord") + "%'";
		}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(stmt);
			DBConnPool.close(rs);
		}
		return totalCount;
	}

	// 검색 조건에 맞는 게시물 목록 조회(페이징 기능 지원).
	public List<RecipeBoardDTO> selectListPage(Map<String, Object> map) {
		con = DBConnPool.getConnection();
		List<RecipeBoardDTO> board = new Vector<RecipeBoardDTO>();
		String query = " " + "SELECT * FROM ( " + "    SELECT Tb.*, ROWNUM rNum FROM ( "
				+ "        SELECT r.recipeId, m.nickname, r.recipeTitle, r.ingredients, r.postDate, "
				+ "               r.editDate, r.mainOName, r.mainSName, r.inOName, r.inSName, "
				+ "               r.recipeCategory, r.hits, ("
				+ "            SELECT COUNT(*) FROM recipeLike WHERE recipeId = r.recipeId" + "        ) AS likeCount "
				+ "        FROM recipeBoard r " + "        JOIN member m ON r.memberid = m.memberid";

		if (map.get("searchWord") != null) {
			query += " WHERE recipeTitle" + " LIKE '%" + map.get("searchWord") + "%' " + " OR m.nickname LIKE '%"
					+ map.get("searchWord") + "%' " + " OR ingredients LIKE '%" + map.get("searchWord") + "%'"
					+ " OR recipeCategory LIKE '%" + map.get("searchWord") + "%'";
		}

		String sortBy = (String) map.get("sort");
		if (sortBy.equals("latest")) {
			query += "        ORDER BY recipeId DESC ";
		} else if (sortBy.equals("views")) {
			query += "        ORDER BY hits DESC ";
		} else if (sortBy.equals("likes")) {
			query += "        ORDER BY likeCount DESC ";
		}

		query += "    ) Tb " + " ) " + " WHERE rNum BETWEEN ? AND ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();

			while (rs.next()) {
				RecipeBoardDTO dto = new RecipeBoardDTO();

				dto.setRecipeId(rs.getInt(1));
				dto.setNickname(rs.getString(2));
				dto.setRecipeTitle(rs.getString(3));
				dto.setIngredients(rs.getString(4));
				dto.setPostDate(rs.getDate(5));
				dto.setEditDate(rs.getDate(6));
				dto.setMainOName(rs.getString(7));
				dto.setMainSName(rs.getString(8));
				dto.setInOName(rs.getString(9));
				dto.setInSName(rs.getString(10));
				dto.setRecipeCategory(rs.getString(11));
				dto.setHits(rs.getInt(12));
				dto.setLikecount(rs.getInt("likeCount"));

				board.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
		return board;
	}

	// 레시피 게시물 조회
	public RecipeBoardDTO selectRecipeBoard(String recipeId) {
		con = DBConnPool.getConnection();
		RecipeBoardDTO dto = null;
		String query = " SELECT rb.*, m.nickname, COALESCE(likeCount, 0) AS likeCount " + " FROM recipeBoard rb "
				+ " JOIN member m ON rb.memberid = m.memberid " + " LEFT JOIN ( "
				+ "     SELECT recipeId, COUNT(*) AS likeCount " + "     FROM recipeLike " + "     GROUP BY recipeId "
				+ " ) rl ON rb.recipeId = rl.recipeId " + " WHERE rb.recipeId = ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, recipeId);
			rs = psmt.executeQuery();
			if (rs.next()) {
				dto = new RecipeBoardDTO();
				dto.setRecipeId(rs.getInt("recipeId"));
				dto.setNickname(rs.getString("nickname"));
				dto.setRecipeTitle(rs.getString("recipeTitle"));
				dto.setIngredients(rs.getString("ingredients"));
				dto.setPostDate(rs.getDate("postDate"));
				dto.setEditDate(rs.getDate("editDate"));
				dto.setMainOName(rs.getString("mainOName"));
				dto.setMainSName(rs.getString("mainSName"));
				dto.setInOName(rs.getString("inOName"));
				dto.setInSName(rs.getString("inSName"));
				dto.setRecipeCategory(rs.getString("recipeCategory"));
				dto.setHits(rs.getInt("hits"));
				dto.setLikecount(rs.getInt("likeCount"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return dto;
	}

	// 레시피 게시물 등록
	public int insertRecipeBoard(RecipeBoardDTO dto) {
		con = DBConnPool.getConnection();
		int recipeId = 0;
		String query = "INSERT INTO recipeBoard (memberid, recipeTitle, ingredients, mainOName, mainSName, "
				+ "inOName, inSName, recipeCategory) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			psmt = con.prepareStatement(query, new String[] { "recipeId" });
			psmt.setString(1, dto.getMemberid());
			psmt.setString(2, dto.getRecipeTitle());
			psmt.setString(3, dto.getIngredients());
			psmt.setString(4, dto.getMainOName());
			psmt.setString(5, dto.getMainSName());
			psmt.setString(6, dto.getInOName());
			psmt.setString(7, dto.getInSName());
			psmt.setString(8, dto.getRecipeCategory());
			psmt.executeUpdate();

			rs = psmt.getGeneratedKeys();
			if (rs.next()) {
				recipeId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}

		return recipeId;
	}

	// 레시피 게시물 수정
	public void updateRecipeBoard(RecipeBoardDTO dto) {
		con = DBConnPool.getConnection();
		String query = "UPDATE recipeBoard SET recipeTitle = ?, ingredients = ?, editDate = SYSDATE, "
				+ "mainOName = ?, mainSName = ?, inOName = ?, inSName = ?, recipeCategory = ? " + "WHERE recipeId = ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getRecipeTitle());
			psmt.setString(2, dto.getIngredients());
			psmt.setString(3, dto.getMainOName());
			psmt.setString(4, dto.getMainSName());
			psmt.setString(5, dto.getInOName());
			psmt.setString(6, dto.getInSName());
			psmt.setString(7, dto.getRecipeCategory());
			psmt.setInt(8, dto.getRecipeId());
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 레시피 게시물 삭제
	public void deleteRecipeBoard(String recipeId) {
		con = DBConnPool.getConnection();
		String query = "DELETE FROM recipeBoard WHERE recipeId = ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, recipeId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 조회수 증가
	public void updateHits(String recipeId) {
		con = DBConnPool.getConnection();
		String query = "UPDATE recipeBoard SET hits = hits + 1 WHERE recipeId = ?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, recipeId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}
}