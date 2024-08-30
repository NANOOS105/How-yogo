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
		action="NoticeWriteProcCon.do">
		<table class="table tbl1" style="width: 90%">

			<tr>
				<td>작성자</td>
				<td><input type="text" class="inputCont"
					value="${sessionScope.nickname}" name="writer" required="required"
					style="width: 150px;" readonly /></td>
			</tr>

			<tr>
				<td>제목</td>
				<td><input class="inputCont" type="text" name="title"
					style="width: 90%" required="required" /></td>
			</tr>

			<tr>
				<td>내용</td>
				<td><textarea name="content" style="width: 90%; height: 100px;"
						required="required">${content}</textarea></td>
			</tr>

			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="filedata" /></td>
			</tr>

			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"
					style="width: 100px;" required="required" /></td>
			</tr>

			<tr>
				<td colspan="2" align="center">
					<div class="btnWriteContainer">
						<button type="submit" class="btnWrite btn1">작성&nbsp;&nbsp;</button>
						<input type="reset" class="btnWrite btn1" value="재작성"">&nbsp;&nbsp;
						<button type="button" class="btnWrite btn1"
							onclick="location.href='NoticeListCon.do'">목록보기</button>

					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>