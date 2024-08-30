<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<link rel="stylesheet" href="/Resources/CSS/Board/NoticeListAdmin.css">
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap')
	;

@import
	url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR&display=swap')
	;
</style>
<title>요고, 어때?</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/common/top.jsp" />
	</header>

	<div align="center">
		<!-- 검색 폼 -->
		<form action="NoticeSearchCon.do" method="get">
			<table style="width: 90%">
				<tr>
					<td align="center"><select name="searchtype" id="searchtype"
						class="custom-dropdown">
							<option value="title">제목</option>
							<option value="content">내용</option>
							<option value="both">제목+내용</option>
					</select> <input class="inputSearch" type="text" name="search"
						placeholder="SEARCH" /> <input type="image"
						src="/Resources/Image/Board/b2.png" alt="검색"
						onclick="submitForm();" width="30" height="30"
						style="vertical-align: middle;" /></td>
				</tr>
			</table>

		</form>
		<br />

		<!-- 목록 테이블 -->
		<section id="list">
			<table style="width: 90%">
				<tr>
					<th width="10%" align="center">번호</th>
					<th width="*%" align="center">제목</th>
					<th width="15%" align="center">작성자</th>
					<th width="15%" align="center">작성일</th>
					<th width="10%" align="center">조회수</th>
				</tr>


				<c:set var="number" value="${number}" />
				<c:forEach var="dto" items="${v}">
					<tr>
						<td>${number}</td>

						<td><a href="NoticeInfo.do?num=${dto.num}">${dto.title}</a></td>
						<td>${dto.writer}</td>
						<td>${dto.postdate.substring(0, 10)}</td>
						<td>${dto.hits}</td>
					</tr>

					<c:set var="number" value="${number-1}" />
				</c:forEach>

			</table>
		</section>

		<!-- 페이지 링크 -->
		<c:if test="${count > 0}">
			<c:set var="pageCount"
				value="${count / pageSize + (count % pageSize == 0 ? 0 : 1)}" />
			<c:set var="startPage" value="${currentPage - 5}" />
			<c:if test="${startPage < 1}">
				<c:set var="startPage" value="1" />
			</c:if>

			<c:set var="endPage" value="${currentPage + 4}" />
			<c:if test="${endPage > pageCount}">
				<c:set var="endPage" value="${pageCount}" />
			</c:if>

			<c:if test="${startPage > 10}">
				<a href="NoticeListCon.do?pageNum=${startPage - 10}">[이전]</a>
			</c:if>

			<c:forEach var="i" begin="${startPage}" end="${endPage}">
				<c:url var="pageUrl" value="NoticeListCon.do">
					<c:param name="pageNum" value="${i}" />
				</c:url>
				<c:choose>
					<c:when test="${i == currentPage}">
						<span>${i}</span>
					</c:when>
					<c:otherwise>
						<a href="${pageUrl}" style="text-decoration: none">${i}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${endPage < pageCount}">
				<a href="NoticeListCon.do?pageNum=${startPage + 10}">[다음]</a>
			</c:if>
		</c:if>

	</div>
	<!-- 

	<table style="width: 90%">
		<tr align="center">
			<td width="100">
				<div class="container" style="text-align: right;">
					이 부분이 부모 요소가 됩니다
					<button class="btnPush btn1"
						onclick="location.href='NoticeWrite.do'">글쓰기</button>

					<script>
						function deleteAll() {
							let deleteAuth = prompt(
									"게시글을 삭제하시려면 '일괄삭제'를 입력하세요.", "");
							if (deleteAuth === "일괄삭제") {
								window.location.href = "NoticeAllDeleteCon.do";
							} else if (deleteAuth === "" || deleteAuth === null) {
								window.location.href = "NoticeListCon.do";
							}
						}
					</script>
					<button class="btnPush btn1" onclick="deleteAll()">일괄삭제</button>
				</div>
			</td>
		</tr>
	</table>
 -->
</body>
</html>