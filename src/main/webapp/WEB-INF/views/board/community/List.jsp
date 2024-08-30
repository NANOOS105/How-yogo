<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR&display=swap')
	;
</style>
<link rel="stylesheet" href="/Resources/CSS/Board/commuList.css">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고, 어때?</title>
<style>
a {
	text-decoration: none;
}
</style>
<script type="text/javascript">
	function submitForm() {
		document.writeFrm.submit(); // 폼을 제출하는 함수
	}
</script>
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/common/top.jsp" />
	</header>
	<br />
	<br />


	<div align="center">
		<!-- 검색 폼 -->
		<form method="get">
			<table style="width: 90%">
				<tr>
					<td align="center"><select name="searchField"
						class="custom-dropdown">
							<option value="c_title">제목</option>
							<option value="c_content">내용</option>
					</select> <input class="inputSearch" type="text" name="searchWord"
						placeholder="SEARCH" /> <!-- <input type="submit" value="검색하기"/> -->
						<!-- 기존의 input type="submit" 버튼 대신 이미지를 사용 --> <!-- 이미지를 클릭하면 submitForm() 함수를 호출하여 폼을 제출 -->
						<input type="image" src="\Resources\Image\Board\b2.png" alt="검색"
						onclick="submitForm();" width="25" height="25" /></td>
				</tr>
			</table>
		</form>
		<br />
		<!-- 카테고리별 버튼 -->
		<form class="btnCateContainer" action="CategoryService" method="get">
			<button class="btnCate btn1" type="submit" name="category"
				value="all">전체보기</button>
			<button class="btnCate btn1" type="submit" name="category"
				value="같이요리">같이요리</button>
			<button class="btnCate btn1" type="submit" name="category"
				value="요리신청">요리신청</button>
		</form>
		<br />
		<!-- 목록 테이블 -->
		<section id="list">
			<table style="width: 90%">
				<tr>
					<th width="8%" align="center">번호</th>
					<th width="10%" align="center">카테고리</th>
					<th width="*" align="center">제목</th>
					<th width="15%" align="center">작성자</th>
					<th width="10%" align="center">조회수</th>
					<th width="15%" align="center">작성일</th>
				</tr>
				<c:choose>
					<c:when test="${ empty communityLists }">
						<!-- 게시물이 없을 때 -->
						<tr>
							<td colspan="6" align="center">등록된 게시물이 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise>
						<!-- 게시물이 있을 때 -->
						<c:forEach items="${ communityLists }" var="row" varStatus="loop">
							<tr align="center">
								<td>${ row.c_num }</td>
								<!-- 글번호 -->
								<!--  <td>   
                 ${ map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index)} 
            </td>-->
								<td>${ row.c_category }</td>
								<!-- 카테고리 -->
								<td align="left">
									<!-- 제목(링크) --> <a href="${cPath}/view.do?c_num=${row.c_num}">${row.c_title}&nbsp;&nbsp;<c:if
											test="${row.commentCount > 0}">[${row.commentCount}]</c:if></a>
								</td>
								<td>${ row.nickname }</td>
								<!-- 작성자 -->
								<td>${ row.c_hits }</td>
								<!-- 조회수 -->
								<td>${ row.c_postdate }</td>
								<!-- 작성일 -->
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</section>
		<!-- 하단 메뉴 (바로가기, 글쓰기) -->
		<table style="width: 90%">
			<tr align="center">
				<td>${map.pagingImg }</td>
				<td width="100">
					<div class="container">
						<c:if test="${writeButtonVisible}">
							<button class="btnPush btn1" type="button"
								onclick="location.href='${cPath}/write.do';">글쓰기</button>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
	</div>


</body>
</html>