package board.notice.dao;

import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import board.notice.dto.noticeDTO;
import common.DBConnPool;
import member.dto.UserDTO;
//import common.DBConnPool;

public class noticeDAO extends DBConnPool {

	public Connection con = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	public noticeDAO() {

	}

	public int getAllCount() {
		int count = 0;

		try {
			String sql = "select count(*) from notice";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.rs = this.psmt.executeQuery();
			if (this.rs.next()) {
				count = this.rs.getInt(1);
			}

		} catch (Exception var3) {
			var3.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return count;
	}

	public Vector<noticeDTO> getAllCount(int startRow, int endRow) {
		Vector<noticeDTO> v = new Vector<noticeDTO>();

		try {

			String sql = "SELECT * FROM (SELECT A.*, ROWNUM Rnum FROM (SELECT * FROM notice ORDER BY NNUM DESC) A) WHERE Rnum >= ? AND Rnum <= ?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setInt(1, startRow);
			this.psmt.setInt(2, endRow);
			this.rs = this.psmt.executeQuery();

			while (this.rs.next()) {
				noticeDTO dto = new noticeDTO();
				dto.setNum(this.rs.getInt(1));
				dto.setWriter(this.rs.getString(2));
				dto.setTitle(this.rs.getString(3));
				dto.setPwd(this.rs.getString(4));
				dto.setContent(this.rs.getString(5));
				dto.setImgSname(this.rs.getString(6));
				dto.setImgOname(this.rs.getString(7));
				dto.setPostdate(this.rs.getString(8));
				dto.setEditdate(this.rs.getString(9));
				dto.setHits(this.rs.getInt(10));
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

	public void insertNotice(noticeDTO dto) {

		try {

			String sql = "INSERT INTO notice VALUES(notice_seq.NEXTVAL,?,?,?,?,?,?,sysdate,sysdate,0)";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setString(1, dto.getWriter());
			this.psmt.setString(2, dto.getTitle());
			this.psmt.setString(3, dto.getPwd());
			this.psmt.setString(4, dto.getContent());
			this.psmt.setString(5, dto.getImgSname());
			this.psmt.setString(6, dto.getImgOname());
			this.psmt.executeUpdate();

		} catch (Exception var3) {
			var3.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

	}

	public noticeDTO getOneNotice(int num) {

		noticeDTO dto = null;

		try {

			String countsql = "update notice set hits=hits+1 where nnum=?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(countsql);
			this.psmt.setInt(1, num);
			this.psmt.executeUpdate();
			String sql = "select * from notice where nnum=?";
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setInt(1, num);
			this.rs = this.psmt.executeQuery();
			if (this.rs.next()) {
				dto = new noticeDTO();
				dto.setNum(this.rs.getInt(1));
				dto.setWriter(this.rs.getString(2));
				dto.setTitle(this.rs.getString(3));
				dto.setPwd(this.rs.getString(4));
				dto.setContent(this.rs.getString(5));
				dto.setImgSname(this.rs.getString(6));
				dto.setImgOname(this.rs.getString(7));
				dto.setPostdate(this.rs.getDate(8).toString());
				dto.setEditdate(this.rs.getDate(9).toString());
				dto.setHits(this.rs.getInt(10));
			}

		} catch (Exception var5) {
			var5.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return dto;
	}

	public noticeDTO getOneNoticeUpdate(int num) {

		noticeDTO dto = null;

		try {

			String sql = "select * from notice where nnum=?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setInt(1, num);
			this.rs = this.psmt.executeQuery();
			if (this.rs.next()) {
				dto = new noticeDTO();
				dto.setNum(this.rs.getInt(1));
				dto.setWriter(this.rs.getString(2));
				dto.setTitle(this.rs.getString(3));
				dto.setPwd(this.rs.getString(4));
				dto.setContent(this.rs.getString(5));
				dto.setImgSname(this.rs.getString(6));
				dto.setImgOname(this.rs.getString(7));
				dto.setPostdate(this.rs.getDate(8).toString());
				dto.setEditdate(this.rs.getDate(9).toString());
				dto.setHits(this.rs.getInt(10));
			}

		} catch (Exception var4) {
			var4.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return dto;
	}

	public void updateNotice(int num, String title, String content, String fileName, String filePath,
			java.util.Date editdate) {
		try {
			String sql = "update notice set ntitle=?, ncontent=?, imgsname=?, imgoname=?, editdate=? where nnum=?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setString(1, title);
			this.psmt.setString(2, content);
			this.psmt.setString(3, fileName);
			this.psmt.setString(4, filePath);
			// 수정일자를 java.sql.Timestamp 객체로 변환하여 설정
			java.sql.Timestamp timestamp = new java.sql.Timestamp(editdate.getTime());
			this.psmt.setTimestamp(5, timestamp);
			this.psmt.setInt(6, num);
			this.psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
	}

	public void deleteNotice(int num) {

		try {

			String sql = "delete from notice where nnum=?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setInt(1, num);
			this.psmt.executeUpdate();
			this.con.close();
		} catch (Exception var3) {
			var3.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

	}

	public String getPassword(int num) {
		String pass = null;

		try {

			String sql = "select npwd from notice where nnum=?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setInt(1, num);
			this.rs = this.psmt.executeQuery();
			if (this.rs.next()) {
				pass = this.rs.getString("npwd");
			}

		} catch (Exception var4) {
			var4.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return pass;
	}

	public void NoticeAllDelete() {

		try {

			String sql = "truncate table notice";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.executeUpdate();
			this.con.close();
		} catch (Exception var2) {
			var2.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

	}

	public Vector<noticeDTO> NoticeTitleSearch(String result) {
		Vector<noticeDTO> v = new Vector<noticeDTO>();

		try {

			String sql = "select * from notice where ntitle like ?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setString(1, "%" + result + "%");
			this.rs = this.psmt.executeQuery();

			while (this.rs.next()) {
				noticeDTO dto = new noticeDTO();
				dto.setNum(this.rs.getInt(1));
				dto.setWriter(this.rs.getString(2));
				dto.setTitle(this.rs.getString(3));
				dto.setPwd(this.rs.getString(4));
				dto.setContent(this.rs.getString(5));
				dto.setImgSname(this.rs.getString(6));
				dto.setImgOname(this.rs.getString(7));
				dto.setPostdate(this.rs.getString(8));
				dto.setEditdate(this.rs.getDate(9).toString());
				dto.setHits(this.rs.getInt(10));
				v.add(dto);
			}

		} catch (Exception var5) {
			var5.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return v;
	}

	public Vector<noticeDTO> NoticeContentSearch(String result) {
		Vector<noticeDTO> v = new Vector<noticeDTO>();

		try {

			String sql = "select * from notice where ncontent like ?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setString(1, "%" + result + "%");
			this.rs = this.psmt.executeQuery();

			while (this.rs.next()) {
				noticeDTO dto = new noticeDTO();
				dto.setNum(this.rs.getInt(1));
				dto.setWriter(this.rs.getString(2));
				dto.setTitle(this.rs.getString(3));
				dto.setPwd(this.rs.getString(4));
				dto.setContent(this.rs.getString(5));
				dto.setImgSname(this.rs.getString(6));
				dto.setImgOname(this.rs.getString(7));
				dto.setPostdate(this.rs.getString(8));
				dto.setEditdate(this.rs.getDate(9).toString());
				dto.setHits(this.rs.getInt(10));
				v.add(dto);
			}

		} catch (Exception var5) {
			var5.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return v;
	}

	public Vector<noticeDTO> NoticeBothSearch(String result) {
		Vector<noticeDTO> v = new Vector<noticeDTO>();

		try {

			String sql = "select * from notice where ncontent like ? and ntitle like ?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setString(1, "%" + result + "%");
			this.psmt.setString(2, "%" + result + "%");
			this.rs = this.psmt.executeQuery();

			while (this.rs.next()) {
				noticeDTO dto = new noticeDTO();
				dto.setNum(this.rs.getInt(1));
				dto.setWriter(this.rs.getString(2));
				dto.setTitle(this.rs.getString(3));
				dto.setPwd(this.rs.getString(4));
				dto.setContent(this.rs.getString(5));
				dto.setImgSname(this.rs.getString(6));
				dto.setImgOname(this.rs.getString(7));
				dto.setPostdate(this.rs.getString(8));
				dto.setEditdate(this.rs.getDate(9).toString());
				dto.setHits(this.rs.getInt(10));
				v.add(dto);
			}

		} catch (Exception var5) {
			var5.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return v;
	}

// getUserImgInfo() 메서드 추가하여 작성자의 이미지 정보 가져오기
	public UserDTO getUserImgInfo(String writer) {
		UserDTO userDTO = new UserDTO();
		try {
			String sql = "SELECT imgSrc, imgSname FROM member WHERE nickname=?";
			con = DBConnPool.getConnection();
			this.psmt = this.con.prepareStatement(sql);
			this.psmt.setString(1, writer);
			this.rs = this.psmt.executeQuery();
			if (this.rs.next()) {
				// 조회된 이미지 정보를 DTO에 설정
				userDTO.setImgSrc(this.rs.getString("imgSrc"));
				userDTO.setImgSname(this.rs.getString("imgSname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}
		return userDTO;
	}

}