package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/loginForm.do")
public class loginForm extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request,response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		
		String watchedPage = request.getHeader("Referer");
		System.out.println(watchedPage+"loginForm");
        if (watchedPage != null && !watchedPage.contains("/loginForm.do")) {
            request.setAttribute("watchedPage", watchedPage);
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/member/login.jsp");
        rd.forward(request, response);

	}
}
