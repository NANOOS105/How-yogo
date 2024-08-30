package member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import member.dao.UserDAO;

@WebServlet("/loginActionCon.do")
public class loginActionCon extends HttpServlet {
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

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String watchedPage = request.getParameter("watchedPage");
		System.out.println(password);
		System.out.println(id);

		UserDAO udao = new UserDAO();
		int count = udao.loginAction(id, password);

		String result = null;
		if (count == 0) {
			result = "false";
		} else {
			result = "true";
		}
		System.out.println(result);
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("watchedPage", watchedPage);
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().append(json.toJSONString());

	}

}