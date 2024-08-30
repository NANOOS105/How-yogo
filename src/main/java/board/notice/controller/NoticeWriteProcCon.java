
package board.notice.controller;

import board.notice.dao.*;
import board.notice.dto.*;
import member.dto.UserDTO;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({ "/NoticeWriteProcCon.do" })
public class NoticeWriteProcCon extends HttpServlet {
	public NoticeWriteProcCon() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

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
		// noticeDTO 객체 생성
		noticeDTO dto = new noticeDTO();

		String saveFolder = "/uploads"; // 업로드할 파일 경로
		String realpath = request.getServletContext().getRealPath(saveFolder); // 실제 서버 경로
		int maxSize = 5242880; // 최대 업로드 파일 크기 (5MB)
		File uploadDir = new File(realpath);

		if (!uploadDir.exists()) {
			uploadDir.mkdirs(); // 디렉토리 생성
		}

		try {
			MultipartRequest multi = new MultipartRequest(request, realpath, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			String fileName = multi.getFilesystemName("filedata");
			String filePath = saveFolder + "/" + fileName; // 저장된 파일 경로

			// 실제 파일 이름과 저장된 파일 경로를 DTO에 설정
			dto.setImgSname(fileName != null ? fileName : "");
			dto.setImgOname(filePath);

			// 나머지 파라미터 설정
			dto.setWriter(nickname); // 닉네임 설정
			dto.setTitle(multi.getParameter("title"));
			dto.setPwd(multi.getParameter("password"));
			dto.setContent(multi.getParameter("content").replaceAll("\r\n", "<br>"));
		} catch (Exception var11) {
			var11.printStackTrace();
		}

		noticeDAO ndao = new noticeDAO();
		ndao.insertNotice(dto);
		RequestDispatcher rd = request.getRequestDispatcher("NoticeListCon.do");
		rd.forward(request, response);
	}
}