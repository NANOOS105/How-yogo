package board.notice.controller;

import board.notice.dao.*;
import board.notice.dto.*;
import member.dto.UserDTO;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "/NoticeDeleteCon.do" })
public class NoticeDeleteCon extends HttpServlet {
	public NoticeDeleteCon() {
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

		int num = Integer.parseInt(request.getParameter("num"));
		noticeDAO ndao = new noticeDAO();
		noticeDTO dto = ndao.getOneNoticeUpdate(num);
		request.setAttribute("dto", dto);
		RequestDispatcher rd = request.getRequestDispatcher("NoticeDeleteProcCon.do");
		rd.forward(request, response);
	}
}
