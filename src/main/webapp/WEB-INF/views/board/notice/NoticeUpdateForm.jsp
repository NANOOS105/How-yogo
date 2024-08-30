<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<link rel="stylesheet" href="/Resources/CSS/Board/NoticeWrite.css">
<title>요고, 어때?</title>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap')
	;

@import
	url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR&display=swap')
	;
</style>
<link rel="stylesheet" href="/Resources/CSS/Board/NoticeWrite.css">
</head>
<body>

	<header>
		<jsp:include page="/WEB-INF/views/common/top.jsp" />
	</header>

	<!-- 파일 첨부 작성 폼 -->
	<form name="writeFrm" method="post" enctype="multipart/form-data"
		action="NoticeUpdateProcCon.do">
		<table class="table tbl1" style="width: 90%">

			<tr>
				<td>작성자</td>
				<td><input type="text" class="inputCont" value="${dto.writer}"
					name="writer" required="required" style="width: 150px;" readonly /></td>
			</tr>

			<tr>
				<td>제목</td>
				<td><input class="inputCont" type="text" name="title"
					value="${dto.title}" style="width: 80%" required="required" /></td>
			</tr>

			<tr>
				<td>내용</td>
				<td><textarea name="content" style="width: 80%; height: 130px;"
						required="required">${dto.content}</textarea></td>
			</tr>

			<tr>
				<td><input type="hidden" name="num" value="${dto.num}"></td>
				<!-- 수정: num은 hidden 필드로 전달 -->
			</tr>

			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="filedata"> <!-- 파일 입력 필드 -->
					<!-- 기존에 첨부된 파일이 있을 경우 파일 이름 표시 --> <c:if
						test="${not empty dto.imgSname}">
						<br>
						<!-- 파일 이름 클릭시 파일 보여주기 -->
						<span class="file-link" onclick="showFile('${dto.imgSname}')">현재
							첨부된 파일: ${dto.imgSname}</span>
					</c:if></td>
			</tr>

			<tr>
				<td colspan="2" align="center">
					<div class="btnWriteContainer">
						<button type="submit" class="btnWrite btn1">수정&nbsp;&nbsp;</button>
						<button type="button" class="btnWrite btn1"
							onclick="location.href='NoticeListCon.do'">목록보기</button>
					</div>
				</td>
			</tr>
		</table>
	</form>

	<script>
		function showFile(fileName) {
			// 파일 경로 생성
			var filePath = "/uploads/" + fileName;

			// 새 창 열어서 파일 보여주기
			window.open(filePath);
		}
	</script>
</body>
</html>