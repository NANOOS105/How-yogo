package board.recipe.dto;

public class RecipeDTO {
    private int recipeStep;
    private int recipeId;
    private String stepContent;
    private String imgOName;
    private String imgSName;
    
	public int getRecipeStep() {
		return recipeStep;
	}
	public void setRecipeStep(int recipeStep) {
		this.recipeStep = recipeStep;
	}
	public int getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	public String getStepContent() {
		return stepContent;
	}
	public void setStepContent(String stepContent) {
		this.stepContent = stepContent;
	}
	public String getImgOName() {
		return imgOName;
	}
	public void setImgOName(String imgOName) {
		this.imgOName = imgOName;
	}
	public String getImgSName() {
		return imgSName;
	}
	public void setImgSName(String imgSName) {
		this.imgSName = imgSName;
	}
    
}