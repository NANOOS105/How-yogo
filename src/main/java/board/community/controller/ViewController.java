package board.community.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.community.dao.CommunityDAO;
import board.community.dto.CommunityDTO;
import comment.community.dao.CoCommentDAO;
import comment.community.dto.CoCommentDTO;
import member.dto.UserDTO;

@WebServlet("/view.do")
public class ViewController extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글처리
		request.setCharacterEncoding("UTF-8");

		/////// 추가
		// 세션에서 사용자 정보 가져오기
		HttpSession session = request.getSession(false);
		// CommunityDTO 객체 생성
		CommunityDTO dto = null;
		UserDTO userDTO = null;
		if (session != null) {
			userDTO = (UserDTO) session.getAttribute("bean");
		}

		// 게시물 불러오기
		CommunityDAO dao = new CommunityDAO();
		String c_num = request.getParameter("c_num");
		// 조회수를 1 증가 시킨다.
		dao.updateHits(c_num);
		// CommunityDTO dto = dao.selectView(c_num);
		dto = dao.selectView(c_num);
		// dao.close();

		// 줄 바꿈 처리
		dto.setC_content(dto.getC_content().replaceAll("\r\n", "<br/>"));
		// null반환을 처리하기위한 코드 수정
		String content = dto.getC_content() != null ? dto.getC_content().replaceAll("\r\n", "<br/>") : "";

		/////// 동일 작성자만 수정, 삭제 보이도록 추가
		// 게시글 작성자와 현재 로그인한 사용자의 닉네임 비교하여 일치하는지 확인
		boolean isAuthor = false;
		if (userDTO != null && dto != null) {
			isAuthor = dto.getNickname().equals(userDTO.getnickname());
		}

		// 첨부파일 확장자 추출 및 이미지 타입 확인
		String ext = null, fileName = dto.getC_imgSName();
		if (fileName != null) {
			ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		String[] mimeStr = { "png", "jpg", "jpeg", "gif" };
		List<String> mimeList = Arrays.asList(mimeStr);
		boolean isImage = false;
		if (mimeList.contains(ext)) {
			isImage = true;
		}

		// 댓글 목록 받기
		CoCommentDAO commentDao = new CoCommentDAO();
		List<CoCommentDTO> commentList = commentDao.selectCommentList(c_num);

		// 전달할 데이터에 isAuthor 속성 추가
		request.setAttribute("isAuthor", isAuthor);

		// 게시물(dto) 저장 후 뷰로 포워드
		request.setAttribute("dto", dto);
		request.setAttribute("isImage", isImage);
		request.setAttribute("commentList", commentList);
		request.getRequestDispatcher("/WEB-INF/views/board/community/View.jsp").forward(request, response);
	}
}