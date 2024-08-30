package board.recipe.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.recipe.dao.RecipeBoardDAO;
import board.recipe.dao.RecipeDAO;
import board.recipe.dto.RecipeBoardDTO;
import board.recipe.dto.RecipeDTO;

@WebServlet("/RecipeEdit")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class RecipeEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 번호 받기
		String recipeId = req.getParameter("recipeId");

		// DAO를 통해 레시피 정보 받기
		RecipeBoardDAO boardDao = new RecipeBoardDAO();
		RecipeBoardDTO boardDto = boardDao.selectRecipeBoard(recipeId);

		// ingredients를 Map 형태로 변환하여 뷰에 전달
		Map<String, String> ingredientsMap = new LinkedHashMap<>();
		String[] ingredientArray = boardDto.getIngredients().split(",");
		for (String ingredient : ingredientArray) {
			String[] parts = ingredient.split(":");
			String name = parts[0];
			String amount = parts[1];
			ingredientsMap.put(name, amount);
		}
		req.setAttribute("ingredientsMap", ingredientsMap);

		// 요리 순서 정보 받기
		RecipeDAO recipeDao = new RecipeDAO();
		List<RecipeDTO> stepList = recipeDao.selectRecipeSteps(recipeId);

		// 뷰에 전달
		req.setAttribute("boardDto", boardDto);
		req.setAttribute("stepList", stepList);
		req.getRequestDispatcher("/WEB-INF/views/board/recipe/Edit.jsp").forward(req, resp);
	}
}