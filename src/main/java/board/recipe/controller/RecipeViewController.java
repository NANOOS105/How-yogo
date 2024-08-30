package board.recipe.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.recipe.dao.RecipeBoardDAO;
import comment.recipe.dao.RecipeCommentDAO;
import board.recipe.dao.RecipeDAO;
import board.recipe.dao.RecipeLikeDAO;
import board.recipe.dto.RecipeBoardDTO;
import comment.recipe.dto.RecipeCommentDTO;
import board.recipe.dto.RecipeDTO;

@WebServlet("/RecipeView")
public class RecipeViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 번호 받기
		String recipeId = req.getParameter("recipeId");

		// DAO를 통해 레시피 정보 받기
		RecipeBoardDAO boardDao = new RecipeBoardDAO();
		RecipeBoardDTO boardDto = boardDao.selectRecipeBoard(recipeId);
		boardDao.updateHits(recipeId);

		// ingredients를 Map 형태로 변환
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

		// 댓글 목록 받기
		RecipeCommentDAO commentDao = new RecipeCommentDAO();
		List<RecipeCommentDTO> commentList = commentDao.selectCommentList(Integer.parseInt(recipeId));

		// 좋아요 개수 받기
		RecipeLikeDAO likeDao = new RecipeLikeDAO();
		int likeCount = likeDao.selectLikeCount(Integer.parseInt(recipeId));

		// 뷰에 전달
		req.setAttribute("boardDto", boardDto);
		req.setAttribute("stepList", stepList);
		req.setAttribute("commentList", commentList);
		req.setAttribute("likeCount", likeCount);

		req.getRequestDispatcher("/WEB-INF/views/board/recipe/View.jsp").forward(req, resp);
	}
}