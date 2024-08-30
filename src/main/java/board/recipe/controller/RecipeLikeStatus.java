package board.recipe.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.recipe.dao.RecipeLikeDAO;

@WebServlet("/RecipeLikeStatus")
public class RecipeLikeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberid = request.getParameter("memberid");
		String recipeIdStr = request.getParameter("recipeId");
		int recipeId = 0;

		// recipeId가 빈 문자열이 아닌지 확인
		if (recipeIdStr != null && !recipeIdStr.isEmpty()) {
			// recipeId가 정수로 변환 가능한지 확인
			try {
				recipeId = Integer.parseInt(recipeIdStr);
			} catch (NumberFormatException e) {
				// recipeId가 정수로 변환되지 않을 경우 예외 처리
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 오류 코드 반환
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.println("Recipe ID must be an integer.");
				out.flush();
				return; // 메서드 종료
			}
		} else {
			// recipeId가 빈 문자열일 경우 예외 처리
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 오류 코드 반환
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.flush();
			return; // 메서드 종료
		}

		RecipeLikeDAO dao = new RecipeLikeDAO();
		boolean isLiked = dao.isLiked(memberid, recipeId);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("{\"isLiked\": " + isLiked + "}");
		out.flush();
	}
}
