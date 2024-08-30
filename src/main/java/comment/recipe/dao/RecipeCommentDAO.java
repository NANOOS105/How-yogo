package comment.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comment.recipe.dto.RecipeCommentDTO;
import common.DBConnPool;

public class RecipeCommentDAO extends DBConnPool {
	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	// 댓글 등록
	public void insertComment(RecipeCommentDTO dto) {
		con = DBConnPool.getConnection();
		String query = "INSERT INTO recipeComment (recipeId, memberid, commentContent, parentId) VALUES (?, ?, ?, ?)";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getRecipeId());
			psmt.setString(2, dto.getMemberid());
			psmt.setString(3, dto.getCommentContent());
			if (dto.getParentId() == null) {
				psmt.setNull(4, java.sql.Types.INTEGER);
			} else {
				psmt.setInt(4, dto.getParentId());
			}
			int result = psmt.executeUpdate();
			System.out.println("댓글 등록 결과: " + result);
		} catch (SQLException e) {
			System.out.println("댓글 등록 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 댓글 목록 조회
	public List<RecipeCommentDTO> selectCommentList(int recipeId) {
		con = DBConnPool.getConnection();
		List<RecipeCommentDTO> commentList = new ArrayList<>();
		String query = "SELECT c.commentId, c.recipeId, c.memberid, m.nickname, c.commentContent, c.postDate, c.parentId, LEVEL AS depth "
				+ "FROM recipeComment c " + "JOIN member m ON c.memberid = m.memberId " + "WHERE c.recipeId = ? "
				+ "START WITH c.parentId IS NULL " + "CONNECT BY PRIOR c.commentId = c.parentId "
				+ "ORDER SIBLINGS BY c.postDate";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, recipeId);
			rs = psmt.executeQuery();
			while (rs.next()) {
				RecipeCommentDTO dto = new RecipeCommentDTO();
				dto.setCommentId(rs.getInt("commentId"));
				dto.setRecipeId(rs.getInt("recipeId"));
				dto.setMemberid(rs.getString("memberid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setCommentContent(rs.getString("commentContent"));
				dto.setPostDate(rs.getDate("postDate"));
				dto.setParentId(rs.getInt("parentId"));
				dto.setDepth(rs.getInt("depth"));
				commentList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("댓글 목록 조회 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
		return commentList;
	}

	// 댓글 수정
	public void editComment(RecipeCommentDTO dto) {
		con = DBConnPool.getConnection();
		String query = " UPDATE recipeComment " + " SET commentContent=? " + " WHERE commentId=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getCommentContent());
			psmt.setInt(2, dto.getCommentId());
			int result = psmt.executeUpdate();
			System.out.println("댓글 수정 결과: " + result);
		} catch (SQLException e) {
			System.out.println("댓글 수정 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 댓글 삭제
	public void deleteComment(int commentId) {
		con = DBConnPool.getConnection();
		String query = "DELETE FROM recipeComment WHERE commentId = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, commentId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("댓글 삭제 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	public void deleteAll(String recipeId) {
		con = DBConnPool.getConnection();
		String query = "DELETE FROM recipeComment WHERE recipeId = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, recipeId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("댓글 전체삭제 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}
}