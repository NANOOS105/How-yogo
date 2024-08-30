package board.community.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.community.dao.CommunityDAO;
import board.community.dto.CommunityDTO;
import board.community.utils.FileUtil;
import board.community.utils.JSFunction;

@MultipartConfig
@WebServlet("/write.do")
public class WriteController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/board/community/Write.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 한글처리
		request.setCharacterEncoding("UTF-8");

		// 파일 업로드 처리
		// 업로드 디렉토리의 물리적 경로를 확인하기
		// 파일이 업로드될 Uploads 디렉토리의 물리적 경로를 얻는다.
		// String saveDirectory = request.getServletContext().getRealPath("/Uploads");
		String saveDirectory = "C:\\Uploads";
		System.out.println(saveDirectory);
		// 파일 업로드
		String originalFileName = "";

		try {
			originalFileName = FileUtil.uploadFile(request, saveDirectory);

		} catch (Exception e) {
			e.printStackTrace();
			JSFunction.alertLocation(response, "파일 업로드 오류입니다.", "/write.do");
			return;
		}

		// 파일 업로드 외 처리

		// 폼 값을 DTO에 저장
		CommunityDTO dto = new CommunityDTO();

		// dto.setMemberid(request.getParameter("memberid"));
		dto.setNickname(request.getParameter("nickname"));
		dto.setC_title(request.getParameter("c_title"));
		dto.setC_content(request.getParameter("c_content"));
		dto.setC_pwd(request.getParameter("c_pwd"));
		dto.setC_category(request.getParameter("c_category"));

		// 원본 파일명과 저장된 파일 이름을 설정
		if (originalFileName != "") {// 조건문으로 원본 파일이 있는지 확인
			// 첨부 파일이 있을 경우 파일명 변경
			// 원본 파일명과 변경된 파일명을 따로 기록하기 위해 DTO에 저장한다.
			// 파일 첨부가 없으면 실행되지 않는 부분이다.
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
			// 원래 파일 이름
			dto.setC_imgOName(originalFileName);
			// 서버에 저장된 파일 이름
			dto.setC_imgSName(savedFileName);
		}

		// DAO를 통해 DB에 게시 내용 저장
		CommunityDAO dao = new CommunityDAO();
		int result = dao.insertWrite(dto);
		// dao.close();

		// 작업 성공 시 목록으로 이동하고, 실패 시 경고창 반환
		if (result == 1) {// 글쓰기 성공
			response.sendRedirect("/list.do");
		} else {// 글쓰기 실패
			JSFunction.alertLocation(response, "글쓰기에 실패했습니다.", "/write.do");
		}
	}
}
