package board.recipe.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

@WebServlet("/RecipeEditProcess")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class RecipeEditProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 번호 받기
		String recipeId = req.getParameter("recipeId");
		System.out.println("recipeId:" + recipeId);

		// 폼값 받기
		String recipeTitle = req.getParameter("recipeTitle");
		String recipeCategory = req.getParameter("recipeCategory");

		// 재료 정보 받기
		Map<String, String> ingredientsMap = new LinkedHashMap<>();
		int ingredientCount = 1;
		while (req.getParameter("ingredientName" + ingredientCount) != null) {
			String ingredientName = req.getParameter("ingredientName" + ingredientCount);
			String ingredientAmount = req.getParameter("ingredientAmount" + ingredientCount);
			ingredientsMap.put(ingredientName, ingredientName + ":" + ingredientAmount);
			ingredientCount++;
		}
		String ingredients = String.join(",", ingredientsMap.values());

		// DTO에 저장
		RecipeBoardDTO boardDto = new RecipeBoardDTO();
		boardDto.setRecipeId(Integer.parseInt(recipeId));
		boardDto.setRecipeTitle(recipeTitle);
		boardDto.setIngredients(ingredients);
		boardDto.setRecipeCategory(recipeCategory);

		// 이미지 파일 업로드
		String saveDirectory = "C:\\Uploads";
		Map<String, Map<String, String>> fileNames = FileUtil.uploadFile(req, saveDirectory);

		// 대표 이미지 파일 처리
		processImageFile(req, boardDto, fileNames, "mainOName", "mainSName", "existingMainName", "existingMainImage");

		// 재료 이미지 파일 처리
		processImageFile(req, boardDto, fileNames, "inOName", "inSName", "existingInName", "existingInImage");

		// 게시글 업데이트
		RecipeBoardDAO boardDao = new RecipeBoardDAO();
		boardDao.updateRecipeBoard(boardDto);

		// 요리 순서 업데이트
		RecipeDAO recipeDao = new RecipeDAO();
		recipeDao.deleteRecipeSteps(String.valueOf(recipeId)); // 기존 요리 순서 삭제

		String stepCountParam = req.getParameter("stepCount");
		if (stepCountParam != null && !stepCountParam.isEmpty()) {
			int stepCount = Integer.parseInt(stepCountParam);
			for (int i = 1; i <= stepCount; i++) {
				String stepContent = req.getParameter("stepContent" + i);
				String existingStepName = req.getParameter("existingStepName" + i);
				String existingStepImage = req.getParameter("existingStepImage" + i);

				System.out.println("Step " + i + " - Step Content: " + stepContent);
				System.out.println("Step " + i + " - Existing Step Name: " + existingStepName);
				System.out.println("Step " + i + " - Existing Step Image: " + existingStepImage);

				RecipeDTO recipeDto = new RecipeDTO();
				recipeDto.setRecipeId(Integer.parseInt(recipeId));
				recipeDto.setRecipeStep(i);
				recipeDto.setStepContent(stepContent);

				if (fileNames.containsKey("imgOName" + i)) {
					String imgOName = fileNames.get("imgOName" + i).get("OName");
					String imgSName = fileNames.get("imgOName" + i).get("SName");
					recipeDto.setImgOName(imgOName);
					recipeDto.setImgSName(imgSName);
				} else {
					recipeDto.setImgOName(existingStepName);
					recipeDto.setImgSName(existingStepImage);
				}

				recipeDao.insertRecipeStep(recipeDto); // 새로운 요리 순서 등록
			}
		} else {
			System.out.println("Step Count Parameter is null or empty");
		}

		resp.sendRedirect("RecipeView?recipeId=" + recipeId);
		System.out.println("게시글 수정 성공!");
	}

	private void processImageFile(HttpServletRequest req, RecipeBoardDTO boardDto,
			Map<String, Map<String, String>> fileNames, String oNameKey, String sNameKey, String existingNameParam,
			String existingImageParam) {
		String existingName = req.getParameter(existingNameParam);
		String existingImage = req.getParameter(existingImageParam);
		if (fileNames.containsKey(oNameKey)) {
			String oName = fileNames.get(oNameKey).get("OName");
			String sName = fileNames.get(oNameKey).get("SName");
			setField(boardDto, oNameKey, oName);
			setField(boardDto, sNameKey, sName);
		} else {
			setField(boardDto, sNameKey, existingImage);
			setField(boardDto, oNameKey, existingName);
		}
	}

	private void setField(RecipeBoardDTO dto, String fieldName, String value) {
		try {
			Field field = RecipeBoardDTO.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(dto, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
