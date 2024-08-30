package board.recipe.controller;

import java.io.IOException;
import java.util.HashMap;
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
import common.FileUtil;

@WebServlet("/RecipeWrite")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class RecipeWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 글쓰기 페이지로 이동
		request.getRequestDispatcher("/WEB-INF/views/board/recipe/Write.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 폼값 받기
			String nickname = req.getParameter("nickname");
			String memberid = req.getParameter("memberid");
			String recipeTitle = req.getParameter("recipeTitle");
			String recipeCategory = req.getParameter("recipeCategory");

			// 파일 업로드
			String saveDirectory = "C:\\Uploads";
			Map<String, Map<String, String>> fileNames = FileUtil.uploadFile(req, saveDirectory);

			// DTO에 저장
			RecipeBoardDTO boardDto = new RecipeBoardDTO();
			boardDto.setMemberid(memberid);
			boardDto.setNickname(nickname);
			boardDto.setRecipeTitle(recipeTitle);
			setImageFields(boardDto, fileNames);
			boardDto.setRecipeCategory(recipeCategory);
			// 재료 정보 저장
			Map<String, String> ingredients = getIngredients(req);
			boardDto.setIngredients(String.join(",", ingredients.values()));

			// DAO를 통해 DB에 저장
			RecipeBoardDAO boardDao = new RecipeBoardDAO();
			int recipeId = boardDao.insertRecipeBoard(boardDto);

			// 요리 순서 저장
			saveRecipeSteps(req, recipeId, fileNames);

			System.out.println("게시글 작성 성공!");
			resp.sendRedirect("RecipeView?recipeId=" + recipeId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("게시글 작성 실패: " + e.getMessage());
		}
	}

	private Map<String, String> getIngredients(HttpServletRequest req) {
		Map<String, String> ingredients = new HashMap<>();
		int ingredientCount = 1;
		while (req.getParameter("ingredientName" + ingredientCount) != null) {
			String ingredientName = req.getParameter("ingredientName" + ingredientCount);
			String ingredientAmount = req.getParameter("ingredientAmount" + ingredientCount);
			ingredients.put(ingredientName, ingredientName + ":" + ingredientAmount);
			ingredientCount++;
		}
		return ingredients;
	}

	private void setImageFields(RecipeBoardDTO boardDto, Map<String, Map<String, String>> fileNames) {
		String mainOName = fileNames.get("mainOName").get("OName");
		String mainSName = fileNames.get("mainOName").get("SName");
		String inOName = fileNames.get("inOName").get("OName");
		String inSName = fileNames.get("inOName").get("SName");
		boardDto.setMainOName(mainOName);
		boardDto.setMainSName(mainSName);
		boardDto.setInOName(inOName);
		boardDto.setInSName(inSName);
	}

	private void saveRecipeSteps(HttpServletRequest req, int recipeId, Map<String, Map<String, String>> fileNames)
			throws Exception {
		int recipeStep = 1;
		while (req.getParameter("stepContent" + recipeStep) != null) {
			String stepContent = req.getParameter("stepContent" + recipeStep);
			String imgOName = fileNames.get("imgOName" + recipeStep).get("OName");
			String imgSName = fileNames.get("imgOName" + recipeStep).get("SName");

			RecipeDTO recipeDto = new RecipeDTO();
			recipeDto.setRecipeStep(recipeStep);
			recipeDto.setRecipeId(recipeId);
			recipeDto.setStepContent(stepContent);
			recipeDto.setImgOName(imgOName);
			recipeDto.setImgSName(imgSName);

			RecipeDAO recipeDao = new RecipeDAO();
			recipeDao.insertRecipeStep(recipeDto);

			recipeStep++;
		}
	}
}