<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>관리자 페이지: 회원 추이</title>
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
    <div class="logo-text left-text">관리자 페이지: 회원 추이</div>
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
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<title>게시글 목록</title>
<style>
    table {
        width: 100%;
    }
    th, td {
        width: 8.33%;
        text-align: center;
    }
</style>
</head>
<body>
<canvas id="lineChart" width="400" height="200"></canvas>
<canvas id="barChart" width="400" height="200"></canvas>
<!-- <form id="searchForm" onsubmit="submitForm(); return false;"> -->
<form id="searchForm">
    <table>
        <tr>
            <td align="center">
                <input type="text" id="yearInput" name="yearInput" value="" placeholder="연도를 입력하세요" style="border: 1px solid rgb(40, 155, 110); border-radius: 20px; padding: 8px; margin-right: 5px;">
                <button type="button" id="searchButton" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px;">검색</button>
                <button type="reset" style="background-color: rgb(40, 155, 110); color: white; border: none; border-radius: 20px; padding: 8px 16px;">초기화</button>
            </td>
        </tr>
    </table>
</form>


<script>
/* function submitForm() {
    var inputVal = document.getElementById("yearInput").value;

    // 사용자가 입력한 값을 orderBy 파라미터로 설정하여 폼을 제출합니다.
    var form = document.getElementById("searchButton");
    form.action = "adminPage_board_chart.jsp?orderBy=" + inputVal;
    console.log(form.action);
    form.submit();
} */

// 선형 그래프 생성 모듈
var chartModule = (function() {
    var lineChart;
    var lineChartCtx = document.getElementById('lineChart').getContext('2d');

    // 차트를 그리는 함수
    function drawCharts(months, monthlyData) {
        // 이전에 그려진 그래프를 삭제
        if (lineChart) {
            lineChart.destroy();
        }   
        // 선형 그래프 생성
        lineChart = new Chart(lineChartCtx, {
            type: 'line',
            data: {
                labels: months, // 월별 레이블
                datasets: [{
                    label: '월별 회원가입 수',
                    data: monthlyData, // 월별 게시글 데이터
                    borderColor: 'rgb(215, 64, 43)',
                    borderWidth: 1,
                    fill: false
                }]
            },
            options: {
                responsive: false,
                maintainAspectRatio: true,
                scales: {
                    y: {
                        ticks: {
                            precision: 0, // 소수점 이하 자리 수를 0으로 설정하여 정수로 표시
                            beginAtZero: true // 축의 시작을 0으로 설정
                        }
                    }
                }
            }
        });

        // 막대형 그래프 생성
        lineChart.data.datasets.push({
            type: 'bar',
            label: '월별 회원가입 수',
            backgroundColor: 'rgb(40, 155, 110)',
            data: monthlyData
        });
        lineChart.update(); // 차트 업데이트
    }

    return {
        drawCharts: drawCharts
    };
})();

//검색 버튼 클릭 이벤트 처리
var submitButton = document.getElementById('searchButton');
submitButton.addEventListener('click', function(event) {
    event.preventDefault(); // 기본 동작(페이지 새로고침) 방지
    
    //--------------------------------------------
//var inputVal = document.getElementById("yearInput").value;

    //--------------------------------------------
    
    
    // 입력 필드에서 년도 값을 가져옴
    var year = document.getElementById('yearInput').value;

    // 서버로부터 데이터 가져오기
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var monthlyData = JSON.parse(xhr.responseText);
                var months = Object.keys(monthlyData); // 월별 레이블을 저장할 배열

                chartModule.drawCharts(months, monthlyData); // 데이터를 받은 후 차트를 그리는 함수 호출
            } else {
                console.error('서버 응답 오류: ' + xhr.status); // 오류 메시지를 콘솔에 출력
            }
        }
    };

    // 년도를 파라미터로 포함하여 요청
    xhr.open("GET", "AdminMemberChartData?year=" + year, true);
    xhr.send();
});

   // }
//});

</script>

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
</body>
</html>