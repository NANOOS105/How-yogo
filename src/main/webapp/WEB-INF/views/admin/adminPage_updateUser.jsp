<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 완료</title>
</head>
<body>
<%
    // 사용자가 수정한 정보를 받아옵니다.
    String memberId = request.getParameter("memberId");
    String nickname = request.getParameter("nickname");
    
    // DB 연결 정보 설정
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
    String dbUser = "how_yogo";
    String dbPass = "1234";
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
        if (nickname != null && !nickname.isEmpty()) { // 닉네임이 null이 아니고 비어있지 않은 경우에만 업데이트 수행
            // DB 연결
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
            
            // 사용자 닉네임을 업데이트하는 쿼리 작성
            String sql = "UPDATE member SET nickname = ? WHERE memberId = ?";
            
            // PreparedStatement를 사용하여 쿼리 실행 준비
            pstmt = conn.prepareStatement(sql);
            
            // 쿼리의 ?에 값을 설정
            pstmt.setString(1, nickname);
            pstmt.setString(2, memberId);
            
            // 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();
            
            // 수정된 행의 수가 1개 이상이면 성공 메시지를 출력
            if (rowsAffected > 0) {
                out.println("<h1>닉네임이 성공적으로 수정되었습니다.</h1>");
                
            } else {
                out.println("<h1>닉네임 수정에 실패하였습니다.</h1>");
                out.println("<p>회원 아이디: " + memberId + "</p>");
                out.println("<p>수정된 닉네임: " + nickname + "</p>");
            }
        } else {
            out.println("<h1>닉네임은 비어있거나 null일 수 없습니다.</h1>");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        out.println("<h1>닉네임 수정 중 오류가 발생하였습니다. 오류 내용: " + ex.getMessage() + "</h1>");
    } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
        out.println("<h1>닉네임 수정 중 오류가 발생하였습니다. 오류 내용: " + ex.getMessage() + "</h1>");
    } finally {
        // 자원 반납
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
%>
   <p>수정된 닉네임: <%= nickname %></p>
        <a href="AdminMemberAll">회원 조회 페이지로 돌아가기</a><br/><br/>
</body>
</body>
</html>