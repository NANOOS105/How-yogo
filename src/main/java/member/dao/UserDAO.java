package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import board.notice.dto.noticeDTO;
import board.recipe.dto.RecipeBoardDTO;
import board.recipe.dto.RecipeDTO;
import common.DBConnPool;
import member.dto.UserDTO;

public class UserDAO extends DBConnPool {

	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	public UserDAO() {
		super();
	}

	// 회원 가입 시키는 메서드
	public void registerUser(String id, String nickname, String email, String password) {

		try {

			// 데이터를 삽입하는 쿼리
			String sql = "insert into member(memberid,nickname,email,mpwd,regdate) values(?,?,?,?,SYSDATE)";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, nickname);
			psmt.setString(3, email);
			psmt.setString(4, password);

			psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// ID 중복 체크 하는 메서드
	public int checkId(String id) {
		int count = 0;

		try {

			String sql = "select count(memberid) from member where memberid=?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return count;
	}

	// 닉네임 중복 체크 하는 메서드
	public int checkNick(String nickname) {
		int count = 0;

		try {

			String sql = "select count(nickname) from member where nickname=?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, nickname);
			rs = psmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return count;
	}

	// 로그인 시키는 메서드
	public int loginAction(String id, String password) {

		int count = 0;
		try {
			String sql = "select count(*) from member where memberid=? and mpwd=?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, password);
			rs = psmt.executeQuery();

			while (rs.next()) {

				count = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return count;
	}

	// 프로필 정보 가져오는 메서드
	public UserDTO getoneprofile(String id) {
		UserDTO bean = new UserDTO();

		try {
			String sql = "select * from member where memberid=?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);

			rs = psmt.executeQuery();
			if (rs.next()) {
				bean.setMemberid(rs.getString("memberid"));
				bean.setNickname(rs.getString("nickname"));
				bean.setEmail(rs.getString("email"));
				bean.setMpwd(rs.getString("mpwd"));
				bean.setImgSrc(rs.getString("imgsrc"));
				bean.setImgSname(rs.getString("imgsname"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
		return bean;
	}

	// 프로필 업데이트 하는 메서드
	public void updateProfile(String afterLoginId, String nickname, String imgSrc, String imgSname) {
		try {
			System.out.println(nickname);
			String sql = "update member set nickname=?,imgSrc=?,imgSname=? where memberid=?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, nickname);
			psmt.setString(2, imgSrc);
			psmt.setString(3, imgSname);
			psmt.setString(4, afterLoginId);

			psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("DAO업데이트실패 ㅠㅠ");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 회원 삭제하는 메서드
	public void deleteUser(String id) {

		try {
			String sql = "delete from member where memberid =?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// ID 찾는 메서드
	public String searchEmail(String email) {
		String resultId = null;

		try {

			String sql = "select memberid from member where email=?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, email);
			rs = psmt.executeQuery();

			while (rs.next()) {
				resultId = rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return resultId;
	}

	public String searchPassword(String id, String email) {
		String resultPass = null;

		try {

			String sql = "select mpwd from member where memberid=? and email=?";
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, email);
			rs = psmt.executeQuery();

			while (rs.next()) {
				resultPass = rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return resultPass;
	}

	// 검색 조건에 맞는 게시물의 개수를 반환.
	public int getAllCount(String nickname) {
		int totalCount = 0;

		// 쿼리문 준비

		try {
			String sql = "SELECT COUNT(*) FROM recipeBoard where=?";
			// 쿼리문 생성
			con = DBConnPool.getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setString(1, nickname);
			rs = psmt.executeQuery();

			while (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
		// 게시물 개수 서블릿으로 반환
		return totalCount;
	}

	public Vector<RecipeBoardDTO> getAllCount(int startRow, int endRow) {
		Vector<RecipeBoardDTO> v = new Vector();

		try {
			String sql = "SELECT * FROM (SELECT A.*, ROWNUM Rnum FROM (SELECT * FROM recipeBoard ORDER BY NNUM DESC) A) WHERE Rnum >= ? AND Rnum <= ?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setInt(1, startRow);
			this.psmt.setInt(2, endRow);
			this.rs = this.psmt.executeQuery();

			while (this.rs.next()) {
				RecipeBoardDTO dto = new RecipeBoardDTO();

				dto.setRecipeId(rs.getInt("recipeId"));
				dto.setRecipeTitle(rs.getString("recipeTitle"));
				dto.setPostDate(rs.getDate("postDate"));
				dto.setHits(rs.getInt("hits"));
				dto.setLikecount(rs.getInt("likeCount"));
				v.add(dto);
			}
		} catch (Exception var6) {
			var6.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return v;
	}

}
