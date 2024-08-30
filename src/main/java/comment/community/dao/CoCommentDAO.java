package comment.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comment.community.dto.CoCommentDTO;
import common.DBConnPool;

public class CoCommentDAO extends DBConnPool {

	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	// 댓글 등록
	public void insertComment(CoCommentDTO dto) {
		String query = "INSERT INTO CoComment (c_num, nickname, co_content, co_parentId) VALUES (?, ?, ?, ?)";

		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getC_num());
			psmt.setString(2, dto.getNickname());
			psmt.setString(3, dto.getCo_content());
//            if (dto.getCo_parentId() == null) {
//                psmt.setNull(4, java.sql.Types.INTEGER);
//            } else {
//                psmt.setInt(4, dto.getCo_parentId());
//            }
//            
			// // 첫 댓글인 경우
			if (dto.getCo_parentId() == 0) {
				psmt.setNull(4, java.sql.Types.INTEGER);
			}
			// 대댓글인 경우
			else {
				psmt.setInt(4, dto.getCo_parentId());
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
	public List<CoCommentDTO> selectCommentList(String c_num) {
		List<CoCommentDTO> commentList = new ArrayList<>();
		String query = "SELECT comId, c_num, nickname, co_content, co_postDate, co_parentId, " + "LEVEL AS depth "
				+ "FROM CoComment " + "WHERE c_num = ? " + "START WITH co_parentId IS NULL "
				+ "CONNECT BY PRIOR comId = co_parentId " + "ORDER SIBLINGS BY co_postDate";
		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setString(1, c_num);
			rs = psmt.executeQuery();
			while (rs.next()) {
				CoCommentDTO dto = new CoCommentDTO();
				dto.setComId(rs.getInt("comId"));
				dto.setC_num(rs.getString("c_num"));
				dto.setNickname(rs.getString("nickname"));
				dto.setCo_content(rs.getString("co_content"));
				dto.setCo_postDate(rs.getDate("co_postDate"));
				dto.setCo_parentId(rs.getInt("co_parentId"));
				dto.setDepth(rs.getInt("depth")); // depth 값을 설정
				commentList.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("댓글 목록 조회 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		return commentList;
	}

	// 댓글 삭제
	public void deleteComment(int comId) {
		String query = "DELETE FROM CoComment WHERE comId = ?";
		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setInt(1, comId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("댓글 삭제 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 댓글 전체 삭제
	public void deleteAll(String c_num) {
		String query = "DELETE FROM CoComment WHERE c_num = ?";
		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setString(1, c_num);
			psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("댓글 전체삭제 중 예외발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 댓글 수정 부분
	public void updateComment(CoCommentDTO dto) {
		String query = "UPDATE CoComment SET co_content = ? WHERE comId = ?";
		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getCo_content());
			psmt.setInt(2, dto.getComId());
			psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("댓글 수정 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}
}
