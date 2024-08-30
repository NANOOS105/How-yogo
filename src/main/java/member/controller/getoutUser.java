package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.dao.UserDAO;
import member.dto.UserDTO;

@WebServlet("/getoutUser.do")
public class getoutUser extends HttpServlet {
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

		response.setContentType("text/html;charset=utf-8");

		HttpSession session = request.getSession(false);
		UserDTO bean = (UserDTO) session.getAttribute("bean");
		UserDAO udao = new UserDAO();
		String id = bean.getmemberid();

		udao.deleteUser(id);

		request.setAttribute("msg", "탈퇴 처리가 완료되었습니다.");
		request.getSession().invalidate();
		response.sendRedirect("how_yogo");

	}

}