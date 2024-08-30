<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고, 어때?</title>
<link rel="stylesheet" href="/Resources/CSS/Member/myWriteRecipe.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Nanum+Pen+Script&family=Yeon+Sung&display=swap"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gasoek+One&family=Nanum+Pen+Script&family=Yeon+Sung&display=swap"
	rel="stylesheet">
<script><%@include file="/WEB-INF/lib/jquery-3.7.1.min.js" %></script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

	<nav class="navbar">
		<ul class="navbar__menu">
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoMain"><ion-icon name="home-outline"></ion-icon><span>Home</span></a>
			</li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoMypage"><ion-icon name="accessibility-outline"></ion-icon><span>마이페이지</span></a>
			</li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoMyList"><ion-icon name="bookmarks-outline"></ion-icon><span>내가
						쓴 글 보기</span></a></li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoRecipe"><ion-icon name="fast-food-outline"></ion-icon><span>우리만의
						레시피</span></a></li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoCommu"><ion-icon name="musical-notes-outline"></ion-icon><span>커뮤니티</span></a>
			</li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoNotice"><ion-icon name="notifications-outline"></ion-icon><span>공지사항</span></a>
			</li>
		</ul>
	</nav>

	<div class="wrapper">
		<div class="form-box userSetting">
			<h2>내가 쓴 레시피</h2>
			<div class="board_table">
				<table>
					<thead>
						<tr>
							<th class="table_etc">글번호</th>
							<th class="table_title">제 목</th>
							<th class="table_etc">작성일</th>
							<th class="table_etc">조회수</th>
							<th class="table_etc">좋아요</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="number" value="${number}" />
						<c:forEach var="dto" items="${v}">
							<tr>
								<td>${bean1.setRecipeId}</td>
								<td class="titlearray"><a
									href="NoticeInfo.do?num=${dto.num}">${bean1.recipeTitle}</a></td>
								<td>${bean1.recipeTitle}</td>
								<td>${bean1.postDate}</td>
								<td>${bean1.hits}</td>
								<td>${bean1.likeCount}</td>
							</tr>
							<c:set var="number" value="${number-1}" />
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="paging">
				<c:if test="${count>0}">
					<c:set var="pageCount"
						value="${count /pageSize + (count%pageSize == 0 ? 0 : 1 )}" />
					<c:set var="startPage" value="${1}" />

					<c:if test="${currentPage%10 != 0}">
						<fmt:parseNumber var="result" value="${currentPage/10}"
							integerOnly="true" />
						<c:set var="startPage" value="${result*10+1}" />
					</c:if>

					<c:if test="${currentPage%10 == 0}">
						<c:set var="startPage" value="${(result-1)*10+1}" />
					</c:if>

					<c:set var="pageBlock" value="${10}" />
					<c:set var="endPage" value="${startPage+pageBlock-1}" />

					<c:if test="${endPage > pageCount}">
						<c:set var="endPage" value="${pageCount}" />
					</c:if>

					<c:if test="${startPage > 10}">
						<a href="BoardListCon.do?pageNum=${startPage-10}">[이전]</a>
					</c:if>

					<c:forEach var="i" begin="${startPage}" end="${endPage}">
						<a href="BoardListCon.do?pageNum=${i}">[${i}]</a>
					</c:forEach>

					<c:if test="${endPage < pageCount}">
						<a href="BoardListCon.do?pageNum=${startPage+10}">[다음]</a>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>

	<script src="\Resources\JS\Member\userSetting.js"></script>
	<script type="module"
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

</body>
</html>