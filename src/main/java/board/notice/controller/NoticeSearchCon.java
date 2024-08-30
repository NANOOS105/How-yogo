
package board.notice.controller;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.notice.dao.noticeDAO;
import board.notice.dto.noticeDTO;
import member.dto.UserDTO;

@WebServlet({ "/NoticeSearchCon.do" })
public class NoticeSearchCon extends HttpServlet {
	public NoticeSearchCon() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.reqpro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.reqpro(request, response);
	}

	protected void reqpro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		response.setCharacterEncoding("utf-8");
		String searchtype = request.getParameter("searchtype");
		String search = request.getParameter("search");
		Vector<noticeDTO> searchResult = new Vector();
		noticeDAO ndao = new noticeDAO();
		if (searchtype.equals("title")) {
			searchResult = ndao.NoticeTitleSearch(search);
		} else if (searchtype.equals("content")) {
			searchResult = ndao.NoticeContentSearch(search);
		} else if (searchtype.equals("both")) {
			searchResult = ndao.NoticeBothSearch(search);
		}

		int count = searchResult.size();
		int pageSize = 10;
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}

		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize;
		int endRow = startRow + pageSize;
		if (endRow > count) {
			endRow = count;
		}

		Vector<noticeDTO> v = new Vector(searchResult.subList(startRow, endRow));
		int number = count - (currentPage - 1) * pageSize + 1;
		request.setAttribute("v", v);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", Integer.valueOf(pageSize));
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/notice/NoticeSearchList.jsp");
		rd.forward(request, response);
	}
}
