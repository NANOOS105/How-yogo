package board.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import board.community.dto.CommunityDTO;
import common.DBConnPool;

public class CommunityDAO extends DBConnPool {

	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	public CommunityDAO() {
		super();
	}

	// 검색 조건에 맞는 게시물의 개수를 반환.
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;

		// 쿼리문 준비
		String query = "SELECT COUNT(*) FROM community";

		// 검색 조건 WHERE절
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " " + " LIKE '%" + map.get("searchWord") + "%'";
		}
		try {
			con = DBConnPool.getConnection();
			// 쿼리문 생성
			stmt = con.createStatement();
			// 쿼리문 실행
			rs = stmt.executeQuery(query);
			rs.next();
			// 검색된 게시물 개수 저장
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		// 게시물 개수 서블릿으로 반환
		return totalCount;
	}

	// 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원).
	public List<CommunityDTO> selectListPage(Map<String, Object> map) {
		List<CommunityDTO> commu = new Vector<CommunityDTO>();
		String query = " " + "SELECT * FROM ( " + "    SELECT Tb.*, ROWNUM rNum FROM ( "
				+ "        SELECT * FROM community ";

		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%' ";
		}

		query += "        ORDER BY c_num DESC " + "    ) Tb " + " ) " + " WHERE rNum BETWEEN ? AND ?";

		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();

			while (rs.next()) {
				CommunityDTO dto = new CommunityDTO();

				dto.setC_num(rs.getString(1));
				// dto.setMemberid(rs.getString(2));
				dto.setNickname(rs.getString(2));
				dto.setC_title(rs.getString(3));
				dto.setC_content(rs.getString(4));
				dto.setC_postdate(rs.getDate(5));
				dto.setC_editdate(rs.getDate(6));
				dto.setC_imgOName(rs.getString(7));
				dto.setC_imgSName(rs.getString(8));
				dto.setC_likecount(rs.getInt(9));
				dto.setC_pwd(rs.getString(10));
				dto.setC_hits(rs.getInt(11));
				dto.setC_category(rs.getString(12));

				commu.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		// 목록 반환
		return commu;
	}

	// 게시글 데이터를 받아 DB에 추가(파일 업로드)
	public int insertWrite(CommunityDTO dto) {
		int result = 0;
		try {
			String query = "INSERT INTO community ( "
					+ " nickname, c_title, c_content, c_imgOName, c_imgSName, c_pwd, c_category) " + " VALUES ( "
					+ " ?, ?, ?, ?, ?, ?, ?)";
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			// c_num은 트리거가 자동으로 생성하므로 값을 넣지 않음
			psmt.setString(1, dto.getNickname());
			psmt.setString(2, dto.getC_title());
			psmt.setString(3, dto.getC_content());
			psmt.setString(4, dto.getC_imgOName());
			psmt.setString(5, dto.getC_imgSName());
			psmt.setString(6, dto.getC_pwd());
			psmt.setString(7, dto.getC_category());
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		return result;
	}

	// 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환
	public CommunityDTO selectView(String c_num) {
		// DTO 객체 생성
		CommunityDTO dto = new CommunityDTO();
		String query = "SELECT * FROM community where c_num=?";
		try {
			con = DBConnPool.getConnection();

			// 쿼리문 준비
			psmt = con.prepareStatement(query);
			// 인파라미터 설정
			psmt.setString(1, c_num);
			// 쿼리문 실행
			rs = psmt.executeQuery();
			if (rs.next()) {
				// 결과를 DTO에 저장
				dto.setC_num(rs.getString(1));
				dto.setNickname(rs.getString(2));
				dto.setC_title(rs.getString(3));
				dto.setC_content(rs.getString(4));
				dto.setC_postdate(rs.getDate(5));
				dto.setC_editdate(rs.getDate(6));
				dto.setC_imgOName(rs.getString(7));
				dto.setC_imgSName(rs.getString(8));
				dto.setC_likecount(rs.getInt(9));
				dto.setC_pwd(rs.getString(10));
				dto.setC_hits(rs.getInt(11));
				dto.setC_category(rs.getString(12));
			}

		} catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		return dto;
	}

	// 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가 시킨다.
	public void updateHits(String c_num) {
		// 조회수 증가 쿼리문 작성
		String query = "UPDATE community SET " + " c_hits=c_hits+1 " + " WHERE c_num=?";
		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setString(1, c_num);
			psmt.executeQuery();
		} catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 게시글 비밀번호 일치여부 확인
	public boolean confirmPassword(String c_pwd, String c_num) {
		boolean isCorr = true;
		try {
			con = DBConnPool.getConnection();

			String sql = "SELECT COUNT(*) FROM community WHERE c_pwd=? AND c_num=?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, c_pwd);
			psmt.setString(2, c_num);

			rs = psmt.executeQuery();
			rs.next();
			// 일치하는 게시물이 없으면(실행결과가 0이면) false를 반환한다.
			if (rs.getInt(1) == 0) {
				isCorr = false;
			}
		} catch (Exception e) {
			// 에러가 발생해도 false를 반환한다.
			isCorr = false;
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		return isCorr;
	}

	// 지정한 번호의 게시물을 삭제
	public int deletePost(String c_num) {
		int result = 0;
		try {
			con = DBConnPool.getConnection();

			String query = "DELETE FROM community WHERE c_num=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, c_num);
			// 정상적으로 삭제되면 executeUpdate()가 1을 반환한다.
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시글 삭제 중 예외 발생함");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		return result;
	}

	// 게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신한다. (파일 업로드 지원)
	public int updatePost(CommunityDTO dto) {
		int result = 0;
		try {

			// 쿼리문 템플릿 준비
			String query = "UPDATE community" + " SET c_title=?, nickname=?, c_content=?, c_imgOName=?, c_imgSName=? "
					+ " WHERE c_num=? and c_pwd=?";
			con = DBConnPool.getConnection();

			// 쿼리문 준비
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getC_title());
			psmt.setString(2, dto.getNickname());
			// psmt.setString(2, dto.getMemberid());
			psmt.setString(3, dto.getC_content());
			psmt.setString(4, dto.getC_imgOName());
			psmt.setString(5, dto.getC_imgSName());
			psmt.setString(6, dto.getC_num());
			psmt.setString(7, dto.getC_pwd());

			// 쿼리문 실행
			// 일련번호와 비밀번호가 모두 일치해야 수정된다.
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		return result;
	}

	// 카테고리별 조회
	public List<CommunityDTO> selectListByCategory(String category, Map<String, Object> map) {
		List<CommunityDTO> commu = new Vector<CommunityDTO>();
		String query = " " + "SELECT * FROM ( " + "    SELECT Tb.*, ROWNUM rNum FROM ( "
				+ "        SELECT * FROM community ";

		if (!category.equals("all")) {
			query += " WHERE c_category = ? ";
		}

		if (map.get("searchWord") != null) {
			query += " AND " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%' ";
		}

		query += "        ORDER BY c_num DESC " + "    ) Tb " + " ) " + " WHERE rNum BETWEEN ? AND ?";

		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			// PreparedStatement에 설정할 매개변수의 인덱스
			int index = 1;
			// 카테고리가 "all"이 아닌 경우 PreparedStatement에 카테고리 매개변수 설정
			if (!category.equals("all")) { // PreparedStatement의 첫 번째 매개변수에 카테고리 설정
				psmt.setString(index++, category);
			}
			psmt.setString(index++, map.get("start").toString()); // PreparedStatement의 두 번째 매개변수에 검색 시작 번호 설정
			psmt.setString(index++, map.get("end").toString()); // PreparedStatement의 세 번째 매개변수에 검색 끝 번호 설정
			rs = psmt.executeQuery();

			while (rs.next()) {
				CommunityDTO dto = new CommunityDTO();
				dto.setC_num(rs.getString(1));
				// dto.setMemberid(rs.getString(2));
				dto.setNickname(rs.getString(2));
				dto.setC_title(rs.getString(3));
				dto.setC_content(rs.getString(4));
				dto.setC_postdate(rs.getDate(5));
				dto.setC_editdate(rs.getDate(6));
				dto.setC_imgOName(rs.getString(7));
				dto.setC_imgSName(rs.getString(8));
				dto.setC_likecount(rs.getInt(9));
				dto.setC_pwd(rs.getString(10));
				dto.setC_hits(rs.getInt(11));
				dto.setC_category(rs.getString(12));

				commu.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		// 목록 반환
		return commu;
	}

	// 댓글 개수를 반환하는 메서드
	public int getCommentCount(String c_num) {
		int commentCount = 0;
		String query = "SELECT COUNT(*) FROM CoComment WHERE c_num = ?";
		try {
			con = DBConnPool.getConnection();

			psmt = con.prepareStatement(query);
			psmt.setString(1, c_num);
			rs = psmt.executeQuery();
			if (rs.next()) {
				commentCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("댓글 개수 조회 중 예외 발생");
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
		return commentCount;
	}

}
