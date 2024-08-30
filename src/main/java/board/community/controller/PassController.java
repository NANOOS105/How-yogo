package board.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.community.dao.CommunityDAO;
import board.community.dto.CommunityDTO;
import board.community.utils.FileUtil;
import board.community.utils.JSFunction;

@WebServlet("/pass.do")
public class PassController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("mode", request.getParameter("mode"));
		request.getRequestDispatcher("/WEB-INF/views/board/community/Pass.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 매개변수 저장
		String c_num = request.getParameter("c_num");
		String mode = request.getParameter("mode");
		String c_pwd = request.getParameter("c_pwd");

		// 비밀번호 확인
		CommunityDAO dao = new CommunityDAO();
		boolean confirmed = dao.confirmPassword(c_pwd, c_num);
		// dao.close();

		// 비밀번호가 일치하고 현재 요청이 수정이라면 session영역에 비밀번호 저장한 후 수정하기 페이지로 이동한다.
		if (confirmed) {
			if (mode.equals("edit")) {
				// 수정 모드
				HttpSession session = request.getSession();
				session.setAttribute("c_pwd", c_pwd);
				response.sendRedirect("/edit.do?c_num=" + c_num);
			} else if (mode.equals("delete")) {
				// 삭제 모드
				dao = new CommunityDAO();
				// 기존 정보 보관
				CommunityDTO dto = dao.selectView(c_num);
				// 게시물 삭제
				int result = dao.deletePost(c_num);
				// 보관해둔 정보에서 파일 이름을 찾아 첨부 파일도 삭제
				if (result == 1) { // 게시물 삭제 성공 시 첨부파일도 삭제
					String saveFileName = dto.getC_imgSName();
					FileUtil.deleteFile(request, "/Uploads", saveFileName);
				}
				JSFunction.alertLocation(response, "삭제되었습니다.", "/list.do");
			}
		}
		// 비밀번호 불일치
		else {
			JSFunction.alertBack(response, "비밀번호 검증에 실패했습니다.");
		}
	}
}
