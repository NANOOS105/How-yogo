package admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.community.dto.CommunityDTO;
import member.dto.UserDTO;

@WebServlet("/AdminModifyUser")
public class AdminModifyUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/////// 추가
		// 세션에서 사용자 정보 가져오기
		HttpSession session = request.getSession(false);
		// CommunityDTO 객체 생성
		CommunityDTO dto = null;
		UserDTO userDTO = null;
		if (session != null) {
			userDTO = (UserDTO) session.getAttribute("bean");
		}
		request.getRequestDispatcher("/WEB-INF/views/admin/adminPage_modifyUser.jsp").forward(request, response);
	}
}
