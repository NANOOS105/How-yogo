package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import member.dao.UserDAO;

/**
 * Servlet implementation class checkId
 */
@WebServlet("/checkNick.do")
public class checkNick extends HttpServlet {
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

		String nickname = request.getParameter("nickname");
		UserDAO udao = new UserDAO();
		int count = udao.checkNick(nickname);

		String result = null;
		if (count == 0) {
			result = "true";

		} else {
			result = "false";
		}

		JSONObject json = new JSONObject();
		json.put("result", result);
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().append(json.toJSONString());

	}

}
