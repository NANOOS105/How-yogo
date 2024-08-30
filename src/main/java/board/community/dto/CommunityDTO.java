package board.community.dto;

import java.util.Date;

public class CommunityDTO {
	private String c_num;
	private String memberid;
	private String nickname;
	private String c_title;
	private String c_content;
	private Date c_postdate;
	private Date c_editdate;
	private String c_imgOName;
	private String c_imgSName;
	private int c_likecount;
	private String c_pwd;
	private int c_hits;
	private String c_category;
	private int commentCount;

	public CommunityDTO() {

	}

	public String getC_num() {
		return c_num;
	}

	public void setC_num(String c_num) {
		this.c_num = c_num;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getC_title() {
		return c_title;
	}

	public void setC_title(String c_title) {
		this.c_title = c_title;
	}

	public String getC_content() {
		return c_content;
	}

	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	public Date getC_postdate() {
		return c_postdate;
	}

	public void setC_postdate(Date c_postdate) {
		this.c_postdate = c_postdate;
	}

	public Date getC_editdate() {
		return c_editdate;
	}

	public void setC_editdate(Date c_editdate) {
		this.c_editdate = c_editdate;
	}

	public String getC_imgOName() {
		return c_imgOName;
	}

	public void setC_imgOName(String c_imgOName) {
		this.c_imgOName = c_imgOName;
	}

	public String getC_imgSName() {
		return c_imgSName;
	}

	public void setC_imgSName(String c_imgSName) {
		this.c_imgSName = c_imgSName;
	}

	public int getC_likecount() {
		return c_likecount;
	}

	public void setC_likecount(int c_likecount) {
		this.c_likecount = c_likecount;
	}

	public String getC_pwd() {
		return c_pwd;
	}

	public void setC_pwd(String c_pwd) {
		this.c_pwd = c_pwd;
	}

	public int getC_hits() {
		return c_hits;
	}

	public void setC_hits(int c_hits) {
		this.c_hits = c_hits;
	}

	public String getC_category() {
		return c_category;
	}

	public void setC_category(String c_category) {
		this.c_category = c_category;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public String toString() {
		return "CommunityDTO [c_num=" + c_num + ", memberid=" + memberid + ", nickname=" + nickname + ", c_title="
				+ c_title + ", c_content=" + c_content + ", c_postdate=" + c_postdate + ", c_editdate=" + c_editdate
				+ ", c_imgOName=" + c_imgOName + ", c_imgSName=" + c_imgSName + ", c_likecount=" + c_likecount
				+ ", c_pwd=" + c_pwd + ", c_hits=" + c_hits + ", c_category=" + c_category + ", commentCount="
				+ commentCount + "]";
	}

}
