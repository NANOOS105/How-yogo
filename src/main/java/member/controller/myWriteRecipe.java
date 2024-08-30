package member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.community.dao.CommunityDAO;
import board.community.dto.CommunityDTO;
import board.community.service.BoardPage;
import board.notice.dao.noticeDAO;
import board.notice.dto.noticeDTO;
import board.recipe.dto.RecipeBoardDTO;
import board.recipe.dto.RecipeDTO;
import member.dao.UserDAO;
import member.dto.UserDTO;

@WebServlet("/myWriteRecipe.do")
public class myWriteRecipe extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(false);
		UserDTO bean = (UserDTO) session.getAttribute("bean");
		String nickname = bean.getnickname(); // 세션에 저장된 nickname값

		try {

			int pageSize = 10;
			String pageNum = request.getParameter("pageNum");
			if (pageNum == null) {
				pageNum = "1";
			}

			int currentPage = Integer.parseInt(pageNum);
			UserDAO bean1 = new UserDAO();
			int count = bean1.getAllCount(nickname);
			int startRow = (currentPage - 1) * pageSize + 1;
			int endRow = currentPage * pageSize;
			Vector<RecipeBoardDTO> v = bean1.getAllCount(startRow, endRow);
			int number = count - (currentPage - 1) * pageSize;
			request.setAttribute("v", v);
			request.setAttribute("number", number);
			request.setAttribute("pageSize", Integer.valueOf(pageSize));
			request.setAttribute("count", count);
			request.setAttribute("currentPage", currentPage);
			RequestDispatcher dis;

			dis = request.getRequestDispatcher("/WEB-INF/views/member/myWriteRecipe.jsp");
			dis.forward(request, response);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
