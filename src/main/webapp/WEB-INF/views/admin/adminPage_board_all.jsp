<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>CSS Website Layout</title>
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
    <div class="logo-text left-text">관리자 페이지: 게시글 조회</div>
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
  <a href="AdminMemberAll" data-submenu="subMenu1" onmouseenter="showSubMenu(this)">회원 관리</a>
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
<form method="get" action="AdminBoardAll">
    <table width="600">
        <tr height="10">
            <td align="center">        
                <input type="text" name="searchTerm" id="searchTerm" size="12" placeholder="게시글 정보" style="border: 2px solid rgb(40, 155, 110); border-radius: 10px; padding: 8px;">
                <button type="submit" value="검색" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-left: 5px;">검색</button>
                <button type="reset" value="초기화" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-left: 5px;">초기화</button>
            </td>
        </tr>
    </table>
</form>

<table border="1" width="450" style="border-color: rgb(40, 155, 110);">
<form method="get" action="AdminBoardAll">
    <input type="submit" name="orderBy" value="조회수 HOT5 게시글" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px; margin-top: 10px;">
</form>

<style>
    .column-recipeId { min-width: 40px; border: 1px solid rgb(40, 155, 110); }
    .column-memberid { min-width: 75px; border: 1px solid rgb(40, 155, 110); }
    .column-recipeTitle { min-width: 125px; border: 1px solid rgb(40, 155, 110); }
    .column-ingredients { min-width: 150px; border: 1px solid rgb(40, 155, 110); }
    .column-postDate { min-width: 100px; border: 1px solid rgb(40, 155, 110); }
    .column-editDate { min-width: 60px; border: 1px solid rgb(40, 155, 110); }
    .column-mainOName { min-width: 100px; border: 1px solid rgb(40, 155, 110); }
    .column-mainSName { min-width: 100px; border: 1px solid rgb(40, 155, 110); }
    .column-inOName { min-width: 100px; border: 1px solid rgb(40, 155, 110); }
    .column-inSName { min-width: 100px; border: 1px solid rgb(40, 155, 110); }
    .column-recipeCategory { min-width: 80px; border: 1px solid rgb(40, 155, 110); }
    .column-hits { min-width: 60px; border: 1px solid rgb(40, 155, 110); }
</style>


<thead>
<tr style="background-color: rgb(40, 155, 110); color: white; text-align: center;">
    <th class="column-recipeId">번호</th>
    <th class="column-memberid">아이디</th>
    <th class="column-recipeTitle">제목</th>
    <th class="column-ingredients">재료</th>
    <th class="column-postDate">게시일</th>
    <th class="column-editDate">수정일</th>
    <th class="column-mainOName">메인이미지명</th>
    <th class="column-mainSName">mainOName저장명</th>
    <th class="column-inOName">재료이미지명</th>
    <th class="column-inSName">inOName저장명</th>
    <th class="column-recipeCategory">카테고리</th>
    <th class="column-hits">조회수</th>
</tr>

</thead>

<tbody>
<%
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int totalPost = 0; // 게시글 수를 저장할 변수
    
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "dbcp_howyogo";
        String dbPass = "howyogo";
        conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
        
        String searchTerm = request.getParameter("searchTerm");
        String orderBy = request.getParameter("orderBy");
        
        String countSql = "SELECT COUNT(*) AS total FROM recipeBoard"; // 이미 등록된 게시글 수를 가져오는 쿼리
        pstmt = conn.prepareStatement(countSql);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            totalPost = rs.getInt("total");
        }
        rs.close(); // 결과셋 닫기
        pstmt.close();
     
        String sql = "SELECT * FROM (SELECT recipeId, memberid, recipeTitle, ingredients,postDate, editDate, mainOName, mainSName, inOName, inSName, recipeCategory, hits FROM recipeBoard ";
        if (orderBy != null && !orderBy.isEmpty()) {
            switch(orderBy) {
                case "조회수 HOT5 게시글":
                    sql += "ORDER BY hits DESC) WHERE ROWNUM <= 5";
                    break;
                default:
                    break;
            }
            // 아무 조건 없을 경우 모든 게시글 조회
            pstmt = conn.prepareStatement(sql);
        }
        
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int recipeId = rs.getInt("recipeId"); // recipeId는 숫자형으로 설정
            String memberid = rs.getString("memberid");
            String recipeTitle = rs.getString("recipeTitle");
            String ingredients = rs.getString("ingredients");
            java.util.Date postDate = rs.getDate("postDate"); // postDate는 날짜 형식으로 설정
            java.util.Date editdate = rs.getDate("editDate"); // editDate도 날짜 형식으로 설정
            String mainOName = rs.getString("mainOName");
            String mainSName = rs.getString("mainSName");
            String inOName = rs.getString("inOName");
            String inSName = rs.getString("inSName");
            String recipeCategory = rs.getString("recipeCategory");
            int hits = rs.getInt("hits"); // 조회수는 숫자형으로 설정
        %>
        <tr>
            <td style="text-align: center;"><%= recipeId %></td>
            <td style="text-align: center;"><%= memberid %></td>
            <td style="text-align: center;"><%= recipeTitle %></td>
            <td style="text-align: center;"><%= ingredients %></td>
            <td style="text-align: center;"><%= postDate %></td>
            <td style="text-align: center;"><%= editdate %></td>
            <td style="text-align: center;"><%= mainOName %></td>
            <td style="text-align: center;"><%= mainSName %></td>
            <td style="text-align: center;"><%= inOName %></td>
            <td style="text-align: center;"><%= inSName %></td>
            <td style="text-align: center;"><%= recipeCategory %></td>
            <td style="text-align: center;"><%= hits %></td>
        </tr>
<%
        }
    } catch(Exception ex) {
        ex.printStackTrace();
    } finally {
        if (rs != null) { 
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
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
        String dbUser = "dbcp_howyogo";
        String dbPass = "howyogo";
        conn1 = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

        String sql;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            // 검색어가 있을 경우 해당 검색어로 검색
            sql = "SELECT recipeId, memberid, recipeTitle, ingredients, postDate, editDate, mainOName, mainSName, inOName, inSName, recipeCategory, hits " +
                     "FROM recipeBoard " +
                     "WHERE recipeId LIKE ? OR memberid LIKE ? OR recipeTitle LIKE ? OR ingredients LIKE ? OR recipeCategory LIKE ?";
            pstmt1 = conn1.prepareStatement(sql);
            pstmt1.setString(1, "%" + searchTerm + "%");
            pstmt1.setString(2, "%" + searchTerm + "%");
            pstmt1.setString(3, "%" + searchTerm + "%");
            pstmt1.setString(4, "%" + searchTerm + "%");
            pstmt1.setString(5, "%" + searchTerm + "%");
        } else {
            // 검색어가 없을 때는 아무 작업도 수행하지 않음
            out.println("<tr><td colspan='12' style='text-align: center;''>게시판 정보를 입력하세요.</td></tr>");
        }
        
        if (pstmt1 != null) {
            rs1 = pstmt1.executeQuery();
        
            while (rs1.next()) {
            	int recipeId = rs1.getInt("recipeId"); // recipeId는 숫자형으로 설정
                String memberid = rs1.getString("memberid");
                String recipeTitle = rs1.getString("recipeTitle");
                String ingredients = rs1.getString("ingredients");
                java.util.Date postDate = rs1.getDate("postDate"); // postDate는 날짜 형식으로 설정
                java.util.Date editdate = rs1.getDate("editDate"); // editDate도 날짜 형식으로 설정
                String mainOName = rs1.getString("mainOName");
                String mainSName = rs1.getString("mainSName");
                String inOName = rs1.getString("inOName");
                String inSName = rs1.getString("inSName");
                String recipeCategory = rs1.getString("recipeCategory");
                int hits = rs1.getInt("hits"); // 조회수는 숫자형으로 설정
    %>
    <tr>
        	<td style="text-align: center;"><%= recipeId %></td>
            <td style="text-align: center;"><%= memberid %></td>
            <td style="text-align: center;"><%= recipeTitle %></td>
            <td style="text-align: center;"><%= ingredients %></td>
            <td style="text-align: center;"><%= postDate %></td>
            <td style="text-align: center;"><%= editdate %></td>
            <td style="text-align: center;"><%= mainOName %></td>
            <td style="text-align: center;"><%= mainSName %></td>
            <td style="text-align: center;"><%= inOName %></td>
            <td style="text-align: center;"><%= inSName %></td>
            <td style="text-align: center;"><%= recipeCategory %></td>
            <td style="text-align: center;"><%= hits %></td>
    </tr>
    <%
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        out.println("<tr><td colspan='6'>Error: " + ex.getMessage() + "</td></tr>");
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
<h3>등록된 총 게시글 수는 <%= totalPost %>개입니다.</h3><!-- 총 게시글 수 표시 -->
  </tbody>    
</table>
</div>

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
</table>
</body>
</html>
