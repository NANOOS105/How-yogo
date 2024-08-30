package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.dao.UserDAO;

@WebServlet("/registerProc.do")
public class registerProc extends HttpServlet {

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

		request.setCharacterEncoding("utf-8");

		// 폼에서 넘어온 데이터를 받아옴

		String id = request.getParameter("id");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			UserDAO udao = new UserDAO();
			udao.registerUser(id, nickname, email, password);

			request.setAttribute("msg", "회원 가입이 완료되었습니다.");
			RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/views/member/login.jsp");
			dis.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
