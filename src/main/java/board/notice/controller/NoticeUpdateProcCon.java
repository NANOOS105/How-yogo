package board.notice.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import board.notice.dao.noticeDAO;
import board.notice.dto.noticeDTO;
import member.dto.UserDTO;

@WebServlet("/NoticeUpdateProcCon.do")
public class NoticeUpdateProcCon extends HttpServlet {
	public NoticeUpdateProcCon() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqpro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqpro(request, response);
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

		request.setCharacterEncoding("utf-8");
		noticeDTO dto = new noticeDTO();
		String saveFolder = "/uploads";
		String realpath = request.getServletContext().getRealPath(saveFolder);
		int maxSize = 5242880; // 최대 파일 크기 설정
		File uploadDir = new File(realpath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		try {
			MultipartRequest multi = new MultipartRequest(request, realpath, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			String fileName = multi.getFilesystemName("filedata");
			String originalFileName = multi.getOriginalFileName("filedata");

			// 파일이 업로드되었을 때만 파일 경로 설정
			if (fileName != null) {
				String filePath = saveFolder + "/" + fileName;
				dto.setImgOname(filePath);
			}

			dto.setImgSname(fileName != null ? fileName : dto.getImgSname());
			dto.setWriter(multi.getParameter("writer"));
			dto.setTitle(multi.getParameter("title"));
			dto.setPwd(multi.getParameter("password"));
			dto.setContent(multi.getParameter("content").replaceAll("\r\n", "<br>"));
			// 수정일자는 현재 시간으로 설정
			Date editdate = new Date();

			// 수정된 내용을 DAO에 전달하기 위해 추가
			int num = Integer.parseInt(multi.getParameter("num")); // 수정된 글의 번호
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");
			String filePath = dto.getImgOname(); // 수정된 파일의 경로

			// DAO에 수정된 내용 전달
			noticeDAO ndao = new noticeDAO();
			ndao.updateNotice(num, title, content, fileName, filePath, editdate);
		} catch (Exception e) {
			e.printStackTrace();
			// 파일 업로드 및 DTO 설정 과정에서 예외 처리
		}

		RequestDispatcher rd = request.getRequestDispatcher("NoticeListCon.do");
		rd.forward(request, response);
	}
}