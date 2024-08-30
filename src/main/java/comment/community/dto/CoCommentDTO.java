package comment.community.dto;

import java.util.Date;

public class CoCommentDTO {

	private int comId;
	private String c_num;
	private String nickname;
	private String co_content;
	private Date co_postDate;
	private Integer co_parentId;
	private int depth;

	public int getComId() {
		return comId;
	}

	public void setComId(int comId) {
		this.comId = comId;
	}

	public String getC_num() {
		return c_num;
	}

	public void setC_num(String c_num) {
		this.c_num = c_num;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCo_content() {
		return co_content;
	}

	public void setCo_content(String co_content) {
		this.co_content = co_content;
	}

	public Date getCo_postDate() {
		return co_postDate;
	}

	public void setCo_postDate(Date co_postDate) {
		this.co_postDate = co_postDate;
	}

	public Integer getCo_parentId() {
		return co_parentId;
	}

	public void setCo_parentId(Integer co_parentId) {
		this.co_parentId = co_parentId;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "CoCommentDTO [comId=" + comId + ", c_num=" + c_num + ", nickname=" + nickname + ", co_content="
				+ co_content + ", co_postDate=" + co_postDate + ", co_parentId=" + co_parentId + ", depth=" + depth
				+ "]";
	}

}