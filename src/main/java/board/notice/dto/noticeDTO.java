package board.notice.dto;

import java.util.Date;
import java.text.SimpleDateFormat;

public class noticeDTO {
	private int num;
	private String writer;
	private String title;
	private String pwd;
	private String content;
	private String imgSname;
	private String imgOname;
	private String postdate;
	private String editdate;
	private int hits;

	// 생성자 추가
	public noticeDTO() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.postdate = sdf.format(new Date());
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgSname() {
		return imgSname;
	}

	public void setImgSname(String imgSname) {
		this.imgSname = imgSname;
	}

	public String getImgOname() {
		return imgOname;
	}

	public void setImgOname(String imgOname) {
		this.imgOname = imgOname;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public String getEditdate() {
		return editdate;
	}

	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	// 수정일자를 설정하는 메서드 추가
	public String setEditdate(Date date) {
		return editdate;
	}
}