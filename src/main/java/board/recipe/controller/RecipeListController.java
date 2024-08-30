package board.recipe.controller;

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

import board.recipe.dao.RecipeBoardDAO;
import board.recipe.dto.RecipeBoardDTO;
import common.BoardPage;

@WebServlet("/RecipeList")
public class RecipeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RecipeBoardDAO dao = new RecipeBoardDAO();

		// 뷰에 전달할 매개변수 저장용 맵 생성
		Map<String, Object> map = new HashMap<String, Object>();

		String searchWord = req.getParameter("searchWord");
		if (searchWord != null) {
			// 검색어가 있다면 map에 저장
			map.put("searchWord", searchWord);
		}

		String viewFormat = req.getParameter("viewFormat");
		if (viewFormat != null) {
			map.put("viewFormat", viewFormat);
		}

		int totalCount = dao.selectCount(map);

		ServletContext application = getServletContext();
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));

		// 현재 페이지 확인
		int pageNum = 1; // 기본값
		String pageTemp = req.getParameter("pageNum");
		if (pageTemp != null && !pageTemp.equals(""))
			pageNum = Integer.parseInt(pageTemp);

		// AJAX 요청 처리
		String sortBy = req.getParameter("sort");
		if (sortBy == null) {
			sortBy = "latest";
		}
		map.put("sort", sortBy);

		// 목록에 출력할 게시물 범위 계산
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		map.put("start", start);
		map.put("end", end);

		List<RecipeBoardDTO> recipeLists = dao.selectListPage(map);
		// dao.close();

		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "RecipeList", sortBy); // 바로가기
																												// 영역
																												// HTML
																												// 문자열
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);

		req.setAttribute("recipeLists", recipeLists);
		req.setAttribute("map", map);
		req.getRequestDispatcher("/WEB-INF/views/board/recipe/List.jsp").forward(req, resp);
	}
}
