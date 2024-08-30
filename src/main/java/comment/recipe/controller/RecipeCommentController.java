package comment.recipe.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.recipe.dao.RecipeCommentDAO;
import comment.recipe.dto.RecipeCommentDTO;

@WebServlet("/RecipeComment")
public class RecipeCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		switch (action) {
		case "insert":
			insertComment(req);
			break;
		case "delete":
			deleteComment(req);
			break;
		case "edit":
			editComment(req);
			break;
		default:
			throw new IllegalArgumentException("Invalid action: " + action);
		}

		resp.sendRedirect("RecipeView?recipeId=" + req.getParameter("recipeId"));
	}

	private void insertComment(HttpServletRequest req) {
		RecipeCommentDTO dto = new RecipeCommentDTO();
		dto.setRecipeId(Integer.parseInt(req.getParameter("recipeId")));
		dto.setMemberid(req.getParameter("memberid"));
		dto.setCommentContent(req.getParameter("commentContent"));
		String parentId = req.getParameter("parentId");
		if (parentId != null && !parentId.isEmpty()) {
			dto.setParentId(Integer.parseInt(parentId));
		}
		RecipeCommentDAO dao = new RecipeCommentDAO();
		dao.insertComment(dto);
	}

	private void deleteComment(HttpServletRequest req) {
		int commentId = Integer.parseInt(req.getParameter("commentId"));
		RecipeCommentDAO dao = new RecipeCommentDAO();
		dao.deleteComment(commentId);
	}

	private void editComment(HttpServletRequest req) {
		RecipeCommentDTO dto = new RecipeCommentDTO();
		dto.setCommentId(Integer.parseInt(req.getParameter("commentId")));
		dto.setCommentContent(req.getParameter("commentContent"));
		RecipeCommentDAO dao = new RecipeCommentDAO();
		dao.editComment(dto);
	}
}