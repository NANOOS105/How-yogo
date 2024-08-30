<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, admin.dao.MemberStatsDAO, com.google.gson.Gson" %>
<%
// 요청 파라미터에서 년도 가져오기
String orderBy = request.getParameter("year");

int year = 0;
if (orderBy != null && !orderBy.isEmpty()) {
    switch(orderBy) {
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

    // MonthlyStatsDAO를 사용하여 해당 년도의 월별 데이터 가져오기
    MemberStatsDAO dao = new MemberStatsDAO();
    Map<String, Integer> memberStats = dao.getMemberStats(year);
    
    // JSON 형식으로 변환하여 응답
    String json = new Gson().toJson(memberStats);
    response.getWriter().write(json);
    
%>

