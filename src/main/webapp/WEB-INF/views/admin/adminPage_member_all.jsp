<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %><!DOCTYPE html>
<html lang="en">
<head>
<title>관리자 페이지: 회원 조회</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
  box-sizing: border-box;
}

body {
  margin: 0;
}

/* Style the header */
.header {
  background-color: #f1f1f1;
  padding: 20px;
  text-align: center;
}
.logo {
    margin-right: 20px; /* 로고 이미지 사이 간격 조절 */
}

.logo img {
    display: block;
}

/* Style the logo */
.logo {
  margin-top: 1px; /* 로고의 상단 여백 추가 */
}

.logo-text.left-text {
  position: absolute;
  bottom: 0;
  left: 0;
}

.logo-text.right-text {
  position: absolute;
  bottom: 0;
  right: 0;
}

.header {
  position: relative; /* 헤더를 상대 위치로 설정 */
}

/* Style the top navigation bar */
.topnav {
  overflow: hidden;
  background-color: rgb(40, 155, 110);
  text-align: center; /* 메뉴를 가운데로 정렬 */
  position: relative; /* topnav 영역을 상대 위치로 설정 */
}

/* Style the topnav links */
.topnav a {
  display: inline-block; /* 가로로 나열되도록 수정 */
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px; /* 간격 조절 */
  text-decoration: none;
  margin: 0 50px; /* 메뉴 사이의 간격 조절 */
}

/* Change color on hover */
.topnav a:hover {
  background-color: #ddd;
  color: black;
}

/* Style the sidebar */
.sidebar {
  height: calc(100% - 160px); /* 화면 높이에서 topnav의 높이를 뺀 값으로 설정 */
  width: 0; /* 초기에는 사이드바가 보이지 않도록 너비를 0으로 설정 */
  position: fixed;
  top: 130px; /* topnav의 아래에 위치하도록 조정 */
  left: 0;
  background-color: rgb(40, 155, 110);
  padding-top: 20px;
  transition: width 0.3s; /* 사이드바가 나타나거나 사라질 때의 transition 설정 */
  z-index: 2; /* topnav보다 위에 나타나도록 설정 */
}

/* Style the sidebar links */
.sidebar a {
  padding: 16px;
  text-decoration: none;
  font-size: 20px;
  color: white;
  display: block;
}

/* Change color on hover */
.sidebar a:hover {
  background-color: rgb(40, 155, 110);
  color: black;
}

/* Style the sub menu */
.sub-menu {
  display: none;
  padding-left: 20px;
}

/* Style the main content */
.content {
  margin-left: 0; /* 초기에는 사이드바가 보이지 않으므로 메인 콘텐츠는 왼쪽으로 밀리지 않음 */
  padding: 20px;
}

/* Style the hamburger toggle */
.hamburger {
  position: fixed;
  top: 20px;
  left: 20px;
  font-size: 30px;
  cursor: pointer;
  z-index: 3; /* 사이드바와 topnav보다 위에 나타나도록 설정 */
}

/* Media query for smaller screens */
@media screen and (max-width: 600px) {
  .topnav a:not(:first-child) {display: none;} /* 작은 화면에서는 메뉴 항목들을 숨김 */
  .topnav a.icon {
    float: right;
    display: block; /* 작은 화면에서는 토글을 보이도록 함 */
  }
}

@media screen and (max-width: 600px) {
  .topnav.responsive {position: relative;}
  .topnav.responsive .icon {
    position: absolute;
    right: 0;
    top: 0;
  }
  .topnav.responsive a {
    float: none;
    display: block;
    text-align: left;
  }
}
</style>
</head>
<body>

<div class="header" height="45">
    <div class="logo-text left-text">관리자 페이지: 회원 조회</div>
    <div class="logo" align="center">
        <a href="AdminMain">
        <img alt="YogoLogo" src="logoimage" width="250" height="65">
        </a>
    </div>
    <div class="logo-text right-text">관리자님 안녕하세요!</div>
</div>

<div class="topnav" id="myTopnav">
  <a href="#" class="icon" onmouseenter="toggleSidebar()" position="left">☰</a>
  <a href="how_yogo">메인 페이지</a>
  <a href="RecipeList">전체 레시피</a>
  <a href="list.do">같이 요리</a>
  <a href="NoticeListCon.do">공지사항</a>
</div>

<div class="sidebar" id="mySidebar" onmouseleave="hideSubMenu()" style="width: 0;">
  <a href="#" data-submenu="subMenu1" onmouseenter="showSubMenu(this)">회원 관리</a>
  <div class="sub-menu" id="subMenu1">
    <a href="AdminMemberAll">회원 조회</a>
    <a href="AdminMemberChart">회원가입 추이</a>
  </div>
  <a href="AdminBoardAll" data-submenu="subMenu2" onmouseenter="showSubMenu(this)">게시판 관리</a>
  <div class="sub-menu" id="subMenu2">
    <a href="AdminBoardAll">게시글 조회</a>
    <a href="AdminBoardChart">게시글 추이</a>
  </div>
</div>


<div class="content" align="center">
  <form method="get" action="AdminMemberAll">
    <table width="600">
        <tr height="10">
            <td align="center">        
               <input type="text" name="searchTerm" id="searchTerm" size="12" placeholder="회원 정보" style="border: 2px solid rgb(40, 155, 110); border-radius: 10px; padding: 8px;">
                <button type="submit" value="검색" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-left: 5px;">검색</button>
                <button type="reset" value="초기화" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-left: 5px;">초기화</button>
 
            </td>
        </tr>
    </table>
</form>

<table border="1" width="450" style="border-color: rgb(40, 155, 110);">
  <form method="get" action="AdminMemberAll">
    <input type="submit" name="orderBy" value="아이디 오름차순 조회" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-top: 10px;">
    <input type="submit" name="orderBy" value="닉네임 오름차순 조회" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-top: 10px;">
    <input type="submit" name="orderBy" value="이메일 오름차순 조회" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-top: 10px;"><br/>
    <input type="submit" name="orderBy" value="아이디 내림차순 조회" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-top: 10px;">
    <input type="submit" name="orderBy" value="닉네임 내림차순 조회" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-top: 10px;">
    <input type="submit" name="orderBy" value="이메일 내림차순 조회" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-top: 10px;">
</form>

<style>
    .column-id { min-width: 75px; border: 1px solid rgb(40, 155, 110); }
    .column-nickname { min-width: 75px; border: 1px solid rgb(40, 155, 110); }
    .column-edit { min-width: 90px; border: 1px solid rgb(40, 155, 110); }
    .column-email { min-width: 175px; border: 1px solid rgb(40, 155, 110); }
    .column-regdate { min-width: 100px; border: 1px solid rgb(40, 155, 110); }
    .column-memberDelete { min-width: 80px; border: 1px solid rgb(40, 155, 110); }
</style>



<thead>
    <tr style="background-color: rgb(40, 155, 110); color: white; text-align: center;">
        <th class="column-id">아이디</th>
        <th class="column-nickname">닉네임</th>
        <th class="column-edit">닉네임 수정</th>
        <th class="column-email">이메일</th>
        <th class="column-regdate">가입일</th>
        <th class="column-memberDelete">회원 삭제</th>
    </tr>
</thead>

  <tbody>
<%
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    int totalUsers = 0; // 사용자 수를 저장할 변수
    
    try{
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "how_yogo";
        String dbPass = "1234";
        conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
        
        String searchTerm = request.getParameter("searchTerm");
        String orderBy = request.getParameter("orderBy");

        String countSql = "SELECT COUNT(*) AS total FROM member"; // 이미 등록된 회원 수를 가져오는 쿼리
        stmt = conn.createStatement();
        ResultSet countRs = stmt.executeQuery(countSql);
        if (countRs.next()) {
            totalUsers = countRs.getInt("total");
        }
        countRs.close(); // 결과셋 닫기
        stmt.close(); // 지우면 자동으로 전체회원을 불러옴
        
        String sql = "SELECT memberid, nickname, email, TO_CHAR(regdate, 'YYYY-MM-DD') AS regdate FROM member ";
        if (orderBy != null && !orderBy.isEmpty()) {
            switch(orderBy) {
                case "아이디 오름차순 조회":
                    sql += "ORDER BY memberid ASC";
                    break;
                case "아이디 내림차순 조회":
                    sql += "ORDER BY memberid DESC";
                    break;
                case "닉네임 오름차순 조회":
                    sql += "ORDER BY nickname ASC";
                    break;
                case "닉네임 내림차순 조회":
                    sql += "ORDER BY nickname DESC";
                    break;
                case "이메일 오름차순 조회":
                    sql += "ORDER BY email ASC";
                    break;
                case "이메일 내림차순 조회":
                    sql += "ORDER BY email DESC";
                    break;
                default:
                    break;
            }
            // 아무 조건 없을 경우 모든 회원 조회
            stmt = conn.prepareStatement(sql);
        }
        
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String memberId = rs.getString("memberid");
            String nickname = rs.getString("nickname");
            String email = rs.getString("email");
            String regdate = rs.getString("regdate");
%>
            <tr>
		<td style="text-align: center;"><%= memberId %></td>
		<td style="text-align: center;"><%= nickname %></td>
		<td style="text-align: center;"><a href="AdminModifyUser?memberId=<%= memberId %>">수정</a></td>
		<td style="text-align: center;"><%= email %></td>
		<td style="text-align: center;"><%= regdate %></td>
		<td style="text-align: center;"> <a href="#" onclick="deleteUser('<%= memberId %>')">삭제</a></td>

            </tr>
            
            <%-- JavaScript 코드 추가 --%>
<script>
    function deleteUser(memberId) {
        var confirmDelete = confirm("정말로 이 회원을 삭제하시겠습니까?");
        if (confirmDelete) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "AdminDeleteUser?memberId=" + memberId, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    // 삭제 요청이 성공적으로 처리되었을 때 추가 작업을 수행
                    alert("회원이 삭제되었습니다.");
                    // 페이지 새로고침 없이 삭제된 회원 목록을 업데이트하도록 할 수 있습니다.
                    // 여기서는 페이지 전체를 새로고침하는 예시를 드리겠습니다.
                    window.location.reload();
                }
            };
            xhr.send();
        }
    }
</script>
<%
        }
    } catch(SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (rs != null) { 
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>
<%
    request.setCharacterEncoding("UTF-8");

    String searchTerm = request.getParameter("searchTerm");
    Connection conn1 = null;
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "how_yogo";
        String dbPass = "1234";
        conn1 = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

        String sql;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            // 검색어가 있을 경우 해당 검색어로 검색
            sql = "SELECT memberid, nickname, email, TO_CHAR(regdate, 'YYYY-MM-DD') AS regdate " +
                     "FROM member " +
                     "WHERE memberid LIKE ? OR nickname LIKE ? OR email LIKE ?";
            pstmt1 = conn1.prepareStatement(sql);
            pstmt1.setString(1, "%" + searchTerm + "%");
            pstmt1.setString(2, "%" + searchTerm + "%");
            pstmt1.setString(3, "%" + searchTerm + "%");
        } else {
            // 검색어가 없을 때는 아무 작업도 수행하지 않음
        	out.println("<tr><td colspan='8' style='text-align: center;'>회원정보를 입력하세요.</td></tr>");
        }
        
        if (pstmt1 != null) {
            rs1 = pstmt1.executeQuery();
        
            while (rs1.next()) {
                String memberid = rs1.getString("memberid");
                String nickname = rs1.getString("nickname");         
                String email = rs1.getString("email");
                String regdate = rs1.getString("regdate");
    %>
    <tr>
		<td style="text-align: center;"><%= memberid %></td>
		<td style="text-align: center;"><%= nickname %></td>
		<td style="text-align: center;"><a href="AdminModifyUser?memberId=<%= memberid %>" target="_blank">수정</a></td>
		<td style="text-align: center;"><%= email %></td>
		<td style="text-align: center;"><%= regdate %></td>
		<td style="text-align: center;"><a href="AdminDeleteUser?memberId=<%= memberid %>" target="_blank">삭제</a></td>
    </tr>
    <%
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        out.println("<tr><td colspan='8'>Error: " + ex.getMessage() + "</td></tr>");
        ex.printStackTrace();
    } finally {
        // Close resources in finally block
        if (rs1 != null) {
            try {
                rs1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt1 != null) {
            try {
                pstmt1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn1 != null) {
            try {
                conn1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>


<h3>등록된 총 회원 수는 <%= totalUsers %>명입니다.</h3><!-- 총 사용자 수 표시 -->
  </tbody>    
</table> 
</body>


<script>
function toggleSidebar() {
  var sidebar = document.getElementById("mySidebar");
  if (sidebar.style.width === "200px") {
    sidebar.style.width = "0";
  } else {
    sidebar.style.width = "200px";
  }
}

function showSubMenu(element) {
  var subMenuId = element.getAttribute("data-submenu");
  var subMenu = document.getElementById(subMenuId);
  if (subMenu) {
    subMenu.style.display = "block";
  }
}

function hideSubMenu() {
  var subMenus = document.querySelectorAll(".sub-menu");
  subMenus.forEach(function(subMenu) {
    subMenu.style.display = "none";
  });
}
</script>

</body>
</html>
