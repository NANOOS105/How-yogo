<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, admin.dao.BoardStatsDAO, com.google.gson.Gson"%>
<%
// 요청 파라미터에서 년도 가져오기
String orderBy = request.getParameter("year");

int year = 0;
if (orderBy != null && !orderBy.isEmpty()) {
	switch (orderBy) {
	case "2022":
		year = 2022;
		break;
	case "2023":
		year = 2023;
		break;
	case "2024":
		year = 2024;
		break;
	default:
		break;
	}
}

// BoardStatsDAO를 사용하여 해당 년도의 월별 데이터 가져오기
BoardStatsDAO dao = new BoardStatsDAO();
Map<String, Integer> boardStats = dao.getBoardStats(year);

// JSON 형식으로 변환하여 응답
String json = new Gson().toJson(boardStats);
response.getWriter().write(json);
%>

