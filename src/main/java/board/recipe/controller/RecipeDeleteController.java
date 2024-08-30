package board.recipe.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.recipe.dao.RecipeBoardDAO;
import board.recipe.dao.RecipeDAO;
import board.recipe.dao.RecipeLikeDAO;
import board.recipe.dto.RecipeBoardDTO;
import board.recipe.dto.RecipeDTO;
import comment.recipe.dao.RecipeCommentDAO;
import common.FileUtil;

@WebServlet("/RecipeDelete")
public class RecipeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 게시물 번호 받기
        String recipeId = req.getParameter("recipeId");
        
        // 게시글 삭제 처리
        deleteAttachments(req, recipeId);
        deleteComments(recipeId);
        deleteLikes(recipeId);
        deleteRecipeSteps(recipeId);
        deleteRecipeBoard(recipeId);

        // 게시물 목록으로 이동
        System.out.println("게시글 삭제 성공!");
        resp.sendRedirect("RecipeList");
    }
	
	private void deleteAttachments(HttpServletRequest req, String recipeId) {
        RecipeBoardDAO boardDao = new RecipeBoardDAO();
        RecipeBoardDTO boardDto = boardDao.selectRecipeBoard(recipeId);
        FileUtil.deleteFile(req, "C:\\Uploads", boardDto.getMainSName());
        FileUtil.deleteFile(req, "C:\\Uploads", boardDto.getInSName());

        RecipeDAO recipeDao = new RecipeDAO();
        List<RecipeDTO> stepList = recipeDao.selectRecipeSteps(recipeId);
        for (RecipeDTO step : stepList) {
            FileUtil.deleteFile(req, "C:\\Uploads", step.getImgSName());
        }
    }
	
	private void deleteComments(String recipeId) {
        RecipeCommentDAO commentDao = new RecipeCommentDAO();
        commentDao.deleteAll(recipeId);
    }

    private void deleteLikes(String recipeId) {
        RecipeLikeDAO likeDao = new RecipeLikeDAO();
        likeDao.deleteLike(recipeId);
    }

    private void deleteRecipeSteps(String recipeId) {
        RecipeDAO recipeDao = new RecipeDAO();
        recipeDao.deleteRecipeSteps(recipeId);
    }

    private void deleteRecipeBoard(String recipeId) {
        RecipeBoardDAO boardDao = new RecipeBoardDAO();
        boardDao.deleteRecipeBoard(recipeId);
    }
}