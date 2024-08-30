package board.recipe.dto;

import java.util.Date;

public class RecipeBoardDTO {
	private int recipeId;
	private String nickname;
	private String memberid;
	private String recipeTitle;
	private String ingredients;
	private Date postDate;
	private Date editDate;
	private String mainOName;
	private String mainSName;
	private String inOName;
	private String inSName;
	private String recipeCategory;
	private int hits;
	private int likecount;

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getMainOName() {
		return mainOName;
	}

	public void setMainOName(String mainOName) {
		this.mainOName = mainOName;
	}

	public String getMainSName() {
		return mainSName;
	}

	public void setMainSName(String mainSName) {
		this.mainSName = mainSName;
	}

	public String getInOName() {
		return inOName;
	}

	public void setInOName(String inOName) {
		this.inOName = inOName;
	}

	public String getInSName() {
		return inSName;
	}

	public void setInSName(String inSName) {
		this.inSName = inSName;
	}

	public String getRecipeCategory() {
		return recipeCategory;
	}

	public void setRecipeCategory(String recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getLikecount() {
		return likecount;
	}

	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}

}