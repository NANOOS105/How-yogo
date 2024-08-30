package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import member.dao.UserDAO;

/**
 * Servlet implementation class searchId
 */
@WebServlet("/searchId")
public class searchId extends HttpServlet {
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

		try {
			String email = request.getParameter("email");
			UserDAO udao = new UserDAO();
			String resultId = udao.searchEmail(email);

			JSONObject json = new JSONObject();
			json.put("resultId", resultId);
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().append(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
