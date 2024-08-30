package board.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.community.dao.CommunityDAO;
import board.community.dto.CommunityDTO;
import board.community.utils.FileUtil;
import board.community.utils.JSFunction;

@WebServlet("/edit.do")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 10)
public class EditController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글처리
		request.setCharacterEncoding("UTF-8");

		// 수정할 게시물의 일련번호를 받아서
		// 기존 게시물 내용을 담은 DTO 객체의 request영역에 저장한 다음 Edit.jsp로 포워드
		String c_num = request.getParameter("c_num");
		CommunityDAO dao = new CommunityDAO();
		CommunityDTO dto = dao.selectView(c_num);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/WEB-INF/views/board/community/Edit.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 파일 업로드 처리
		// 업로드 디렉토리의 물리적 경로 확인
		// String saveDirectory = request.getServletContext().getRealPath("/Uploads");
		String saveDirectory = "C:\\Uploads";

		// 파일 업로드
		String originalFileName = "";
		try {
			originalFileName = FileUtil.uploadFile(request, saveDirectory);
		} catch (Exception e) {
			JSFunction.alertBack(response, "파일 업로드 오류입니다.");
			return;
		}
		// 파일 업로드 외 처리
		String c_num = request.getParameter("c_num");
		String prevOfile = request.getParameter("prevOfile");
		String prevSfile = request.getParameter("prevSfile");

		// String memberid = request.getParameter("memberid");
		String nickname = request.getParameter("nickname");
		String c_title = request.getParameter("c_title");
		String c_content = request.getParameter("c_content");

		// 비밀번호는 session에서 가져온다.
		// 서블릿에서 session에 저장한 값 가져오며 얻어온 값은 모두 DTO에 저장한다.
		HttpSession session = request.getSession(false);
		String c_pwd = (String) session.getAttribute("c_pwd");

		CommunityDTO dto = new CommunityDTO();
		dto.setC_num(c_num);
		dto.setNickname(nickname);
		// dto.setMemberid(memberid);
		dto.setC_title(c_title);
		dto.setC_content(c_content);
		dto.setC_pwd(c_pwd);

		// 원본 파일명과 저장된 파일이름 설정
		// 파일명 처리 후 기존 파일 있으면 삭제하기
		// 원본 파일명과 저장된 파일 이름 설정
		if (originalFileName != "") {
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);

			dto.setC_imgOName(originalFileName); // 원래 파일 이름
			dto.setC_imgSName(savedFileName); // 서버에 저장된 파일 이름

			// 기존 파일 삭제
			// 뷰에서 매개변수로 얻어온 값
			FileUtil.deleteFile(request, "/Uploads", prevSfile);
		} else {
			// 첨부 파일이 없으면 기존 이름 유지
			dto.setC_imgOName(prevOfile);
			dto.setC_imgSName(prevSfile);
		}

		// DB에 수정 내용 반영
		CommunityDAO dao = new CommunityDAO();
		int result = dao.updatePost(dto);

		// 성공 or 실패?
		// 수정이 정상적으로 처리되면 session 영역에 저장된 비밀번호는 삭제하고 상세 보기 뷰로 이동해 수정된 내용을 확인한다.
		if (result == 1) {
			// 수정 성공
			session.removeAttribute("c_pwd");
			response.sendRedirect("/view.do?c_num=" + c_num);
		} else {
			// 수정실패 시에는 비밀번호 검증을 다시 하도록 한다.
			JSFunction.alertLocation(response, "비밀번호 검증을 다시 진행해주세요.", "/view.do?c_num=" + c_num);
		}
	}
}
