package member.dto;

public class UserDTO {
	private String memberid;
	private String nickname;
	private String mpwd;
	private String passwordcheck;
	private String email;
	private String regdate;
	private String editdate;
	private String imgSrc;
	private String imgSname;

	public String getmemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getnickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getmpwd() {
		return mpwd;
	}

	public void setMpwd(String mpwd) {
		this.mpwd = mpwd;
	}

	public String getpasswordcheck() {
		return passwordcheck;
	}

	public void setPasswordcheck(String passwordcheck) {
		this.passwordcheck = passwordcheck;
	}

	public String getemail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getregdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String geteditdate() {
		return editdate;
	}

	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}

	public String getimgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String ingOname) {
		this.imgSrc = ingOname;
	}

	public String getimgSname() {
		return imgSname;
	}

	public void setImgSname(String imgSname) {
		this.imgSname = imgSname;
	}

}
