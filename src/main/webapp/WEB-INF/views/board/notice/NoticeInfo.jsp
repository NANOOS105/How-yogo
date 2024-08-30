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
	url('https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap')
	;

@import
	url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR&display=swap')
	;
</style>
<link rel="stylesheet" href="/Resources/CSS/Board/NoticeInfo.css">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고 어때?</title>
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/common/top.jsp" />
	</header>

	<table style="width: 90%" class="table tbl1">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="*" />
		</colgroup>
		<!-- 게시글 정보 -->

		<tr class="title">
			<td colspan="3">${dto.title}</td>
		</tr>

		<tr>
			<td>
				<div class="info">
					<em class="user-img"> <!-- 이미지 열 --> <c:choose>
							<c:when
								test="${not empty adminDTO.imgSrc and not empty adminDTO.imgSname}">
								<img class="user-img" id="user-img"
									src="${bean.imgSrc}/${bean.imgSname}" alt="Profile Image">
							</c:when>
							<c:otherwise>
								<!-- 기본 이미지 경로를 설정해주세요 -->
								<img class="user-img" id="user-img"
									src="<%=request.getContextPath()%>/Resources/Image/Member/cookingcat.PNG"
									alt="Default Profile Image">
							</c:otherwise>
						</c:choose>
					</em> <span class="user-info"> ${dto.writer} <br> <!-- 작성자 열 -->
						${dto.postdate} <!-- 작성일 열 -->
					</span>
				</div>
			</td>
		</tr>
		<tbody>
			<tr>
				<td class="content">${dto.content}</td>
			</tr>
			<c:if test="${not empty dto.imgSname}">
				<tr>
					<td><img src="/uploads/${dto.imgSname}" width="300"
						height="300"></td>
				</tr>
			</c:if>
		</tbody>


		<!-- 하단 메뉴(버튼) 
		<tr>
			<td colspan="4" align="center">
				<div class="btnViewContainer">
					<input type="button" class="btnView btn1" value="수정"
						onclick="location.href='NoticeUpdateCon.do?num=${dto.num}'">
					<script>
						function deleteNotice() {
							let deleteAuth = prompt("게시글을 삭제하시려면 '삭제'를 입력하세요.",
									"");
							if (deleteAuth === "삭제") {
								window.location.href = "NoticeDeleteProcCon.do?num=${dto.num}";
							} else if (deleteAuth === "" || deleteAuth === null) {
								window.location.href = "NoticeInfo.do?num=${dto.num}";
							}
						}
					</script>
					<button class="btnView btn1" onclick="deleteNotice()">삭제</button>-->
		<tr>
			<td colspan="4" align="center">
				<div class="btnViewContainer">
					<input type="button" value="목록보기" class="btnView btn1"
						onclick="location.href='NoticeListCon.do'">

				</div>
			</td>
		</tr>
	</table>


</body>
</html>
