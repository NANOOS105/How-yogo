package board.recipe.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.recipe.dao.RecipeLikeDAO;
import board.recipe.dto.RecipeLikeDTO;

@WebServlet("/RecipeLike")
public class RecipeLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String memberid = req.getParameter("memberid");
		int recipeId = Integer.parseInt(req.getParameter("recipeId"));

		RecipeLikeService recipeLikeService = new RecipeLikeService();
		if (recipeLikeService.isLiked(memberid, recipeId)) {
			recipeLikeService.cancelLike(memberid, recipeId);
		} else {
			RecipeLikeDTO dto = new RecipeLikeDTO();
			dto.setMemberid(memberid);
			dto.setRecipeId(recipeId);
			recipeLikeService.insertLike(dto);
		}

		resp.setStatus(HttpServletResponse.SC_OK);
	}
}

class RecipeLikeService {
	private RecipeLikeDAO recipeLikeDAO;

	public RecipeLikeService() {
		recipeLikeDAO = new RecipeLikeDAO();
	}

	public boolean isLiked(String memberid, int recipeId) {
		return recipeLikeDAO.isLiked(memberid, recipeId);
	}

	public void cancelLike(String memberid, int recipeId) {
		recipeLikeDAO.cancelLike(memberid, recipeId);
	}

	public void insertLike(RecipeLikeDTO dto) {
		recipeLikeDAO.insertLike(dto);
	}
}