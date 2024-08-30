package board.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import board.recipe.dto.RecipeLikeDTO;
import common.DBConnPool;

public class RecipeLikeDAO extends DBConnPool {
	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	// 좋아요 등록
	public void insertLike(RecipeLikeDTO dto) {
		String query = "INSERT INTO recipeLike (memberid, recipeId) VALUES (?, ?)";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getMemberid());
			psmt.setInt(2, dto.getRecipeId());
			int result = psmt.executeUpdate();
			System.out.println("좋아요 등록 결과: " + result);
		} catch (SQLException e) {
			System.out.println("좋아요 등록 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}

	}

	// 좋아요 상태 확인
	public boolean isLiked(String memberid, int recipeId) {
		boolean isLiked = false;
		String query = "SELECT COUNT(*) FROM recipeLike WHERE memberid = ? AND recipeId = ?";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, memberid);
			psmt.setInt(2, recipeId);
			rs = psmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				isLiked = (count > 0);
			}
			return isLiked;
		} catch (SQLException e) {
			System.out.println("좋아요 상태 확인 중 예외발생");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
	}

	// 좋아요 취소
	public void cancelLike(String memberid, int recipeId) {
		String query = "DELETE FROM recipeLike WHERE memberid = ? AND recipeId = ?";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, memberid);
			psmt.setInt(2, recipeId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("좋아요 취소 작업 중 예외발생");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 좋아요 전체 삭제
	public void deleteLike(String recipeId) {
		String query = "DELETE FROM recipeLike WHERE recipeId = ?";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, recipeId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("좋아요 전체 삭제 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 좋아요 개수 조회
	public int selectLikeCount(int recipeId) {
		int likeCount = 0;
		String query = "SELECT COUNT(*) AS likeCount FROM recipeLike WHERE recipeId = ?";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, recipeId);
			rs = psmt.executeQuery();
			if (rs.next()) {
				likeCount = rs.getInt("likeCount");
			}
			return likeCount;
		} catch (SQLException e) {
			System.out.println("좋아요 개수 조회 중 예외발생");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
	}

}