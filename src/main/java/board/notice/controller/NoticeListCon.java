package board.notice.controller;

import board.notice.dao.*;
import board.notice.dto.*;
import member.dto.UserDTO;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "/NoticeListCon.do" })
public class NoticeListCon extends HttpServlet {
	public NoticeListCon() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.reqpro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/NoticeListCon.do");
	}

	protected void reqpro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession(false);
		String nickname = null;
		if (session != null) {
			// 세션에서 UserDTO 객체 가져오기
			UserDTO userDTO = (UserDTO) session.getAttribute("bean");
			if (userDTO != null) {
				// UserDTO 객체에서 닉네임 가져오기
				nickname = userDTO.getnickname();

				// 닉네임이 관리자일 경우
				if (nickname != null && nickname.equals("관리자")) {
					session.setAttribute("nickname", "관리자");
				} else {
					// 일반 사용자의 경우, 세션에 닉네임 설정
					session.setAttribute("nickname", nickname);
				}
			}
		}

		System.out.println("닉네임: " + nickname);

		int pageSize = 10;
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}

		int currentPage = Integer.parseInt(pageNum);
		noticeDAO ndao = new noticeDAO();
		int count = ndao.getAllCount();
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		Vector<noticeDTO> v = ndao.getAllCount(startRow, endRow);
		int number = count - (currentPage - 1) * pageSize;
		request.setAttribute("v", v);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", Integer.valueOf(pageSize));
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		RequestDispatcher dis;
		if (nickname != null && nickname.equals("관리자")) {
			dis = request.getRequestDispatcher("/WEB-INF/views/board/notice/NoticeListAdmin.jsp");
			dis.forward(request, response);
		} else {
			dis = request.getRequestDispatcher("/WEB-INF/views/board/notice/NoticeList.jsp");
			dis.forward(request, response);
		}
	}

}
