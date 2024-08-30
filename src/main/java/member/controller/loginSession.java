package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import member.dao.UserDAO;
import member.dto.UserDTO;

/**
 * Servlet implementation class loginSession
 */
@WebServlet("/loginSession.do")
public class loginSession extends HttpServlet {
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

		String afterLoginId = request.getParameter("afterLoginId");
		String watchedPage = request.getParameter("watchedPage");
		System.out.println(watchedPage + "loginSession");

		HttpSession session = request.getSession();
		UserDAO udao = new UserDAO();
		UserDTO bean = udao.getoneprofile(afterLoginId);
		session.setAttribute("bean", bean);

		if (afterLoginId.equals("admin") && bean.getmpwd().equals("admin")) {
			response.sendRedirect("/AdminMain");
		} else {
			if (watchedPage != null && !watchedPage.isEmpty()) {
				response.sendRedirect(watchedPage);
			} else {
				response.sendRedirect("/how_yogo");
			}
		}

	}

}