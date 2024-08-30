package board.community.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.community.dao.CommunityDAO;
import board.community.dto.CommunityDTO;
import member.dto.UserDTO;

@WebServlet("/CategoryService")
public class CategoryService extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 선택된 카테고리를 가져옴
		String category = request.getParameter("category");
		// 한글처리
		request.setCharacterEncoding("UTF-8");

		// 세션에서 사용자 정보 가져오기
		HttpSession session = request.getSession(false);
		boolean writeButtonVisible = false; // 글쓰기 버튼을 보이게 할지 여부를 저장할 변수
		if (session != null) {
			UserDTO userDTO = (UserDTO) session.getAttribute("bean");
			if (userDTO != null) {
				writeButtonVisible = true; // 사용자 정보가 있다면 글쓰기 버튼을 보이도록 설정
			}
		}

		// DAO 생성
		CommunityDAO dao = new CommunityDAO();

		// 모델로 전달할 검색 매개변수 및 뷰로 전달할 페이징 관련 값을 저장하기 위해 Map 컬렉션을 생성하고
		// 매개변수로 전달된 검색어가 있다면 Map 컬렉션에 저장한다.
		// 뷰에 전달할 매개변수 저장용 맵 생성

		Map<String, Object> map = new HashMap<String, Object>();

		String searchField = request.getParameter("searchField");
		String searchWord = request.getParameter("searchWord");
		if (searchWord != null) {
			// 쿼리스트링으로 전달 받은 매개변수 중 검색어가 있다면 맵에 저장
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		// 게시물 개수
		int totalCount = dao.selectCount(map);

		/* 페이지 처리 start */
		// getServletContext() 메서드는 현재 서블릿 객체를 통해 ServletContext 객체를 가져온다.
		// 이후 application 변수에 저장하여 이후 활용할 수 있도록 한다.
		ServletContext application = getServletContext();
		// 페이징 설정값 상수를 가져와 페이지당 게시물수와 블록당 페이지 수를 구한다.
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));

		// 현재 페이지 확인
		int pageNum = 1;// 기본값
		String pageTemp = request.getParameter("pageNum");
		if (pageTemp != null && !pageTemp.equals(""))
			// 요청받은 페이지수로 수정
			pageNum = Integer.parseInt(pageTemp);

		// 목록에 출력할 게시물 범위 계산해 매개변수 컬렉션(map)에 추가
		// 첫 게시물 번호
		int start = (pageNum - 1) * pageSize + 1;
		// 마지막 게시물 번호
		int end = pageNum * pageSize;
		map.put("start", start);
		map.put("end", end);
		/* 페이지 처리 end */

		/* 카테고리 처리 시작 */
		// 카테고리에 맞는 게시물 목록 받기
		List<CommunityDTO> communityLists;
		if (category != null) {
			communityLists = dao.selectListByCategory(category, map);
		} else {
			// 카테고리가 선택되지 않은 경우, 모든 카테고리의 게시물 목록을 가져옴
			communityLists = dao.selectListPage(map);
		}
		/* 카테고리 처리 끝 */

		// DB 연결 닫기
		// dao.close();

		// 각 게시물의 댓글 수를 조회하여 commentCount 필드에 저장
		for (CommunityDTO community : communityLists) {
			int commentCount = dao.getCommentCount(community.getC_num());
			community.setCommentCount(commentCount);
		}

		// 뷰에 전달할 매개변수 추가
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "/list.do");
		// 바로가기 영역 HTML 문자열
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);

		// 로그인 해야 글쓰기 버튼 보이도록 추가
		request.setAttribute("writeButtonVisible", writeButtonVisible);

		// 전달할 데이터를 request영역에 저장 후 List.jsp로 포워드
		request.setAttribute("communityLists", communityLists);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/WEB-INF/views/board/community/List.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
