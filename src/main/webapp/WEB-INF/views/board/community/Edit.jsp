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
<link rel="stylesheet" href="/Resources/CSS/Board/commuEdit.css">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고, 어때?</title>
<script type="text/javascript">
	function validateForm(form) {
		if (form.nickname.value == "") {
			alert("작성자를 입력하세요.");
			form.nickname.focus();
			return false;
		}
		if (form.c_title.value == "") {
			alert("제목을 입력하세요.");
			form.c_title.focus();
			return false;
		}
		if (form.c_content.value == "") {
			alert("내용을 입력하세요.");
			form.c_content.focus();
			return false;
		}
	}

	//파일 선택 시 미리보기
	function previewImage(event) {
		var reader = new FileReader();
		reader.onload = function() {
			var output = document.getElementById('preview');
			output.style.display = 'block'; // 이미지 미리보기 영역을 보이도록 설정
			output.src = reader.result; // 이미지를 미리보기 영역에 표시
		};
		reader.readAsDataURL(event.target.files[0]); // 선택한 파일을 읽어옴
	}
</script>
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/common/top.jsp" />
	</header>
	<h2>Modify</h2>
	<form name="writeFrm" method="post" enctype="multipart/form-data"
		action="${cPath}/edit.do" onsubmit="return validateForm(this);">
		<input type="hidden" name="c_num" value="${ dto.c_num }" /> <input
			type="hidden" name="prevOfile" value="${ dto.c_imgOName }" /> <input
			type="hidden" name="prevSfile" value="${ dto.c_imgSName }" />

		<table class="table tbl1" style="width: 90%">
			<tr>
				<td>작성자</td>
				<td><input class="inputCont" type="text" name="nickname"
					style="width: 150px;" value="${ dto.nickname }" readonly /> <!-- readonly 속성 추가 -->
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input class="inputCont" type="text" name="c_title"
					style="width: 90%;" value="${ dto.c_title }" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="c_content"
						style="width: 90%; height: 100px;">${ dto.c_content }</textarea></td>
			</tr>
			<tr>
				<td>첨부 파일</td>
				<td>
					<!-- 파일 첨부만 되는 코드 --> <!--  <input type="file" name="c_imgOName"/> -->
					<!-- 파일 선택 시 이미지 미리 보기 --> <input type="file" name="c_imgOName"
					onchange="previewImage(event)" /> <!-- 이미지 미리 보기를 위한 영역 --> <img
					id="preview" src="./uploads/${ dto.c_imgSName }" alt="이미지 미리보기"
					style="max-width: 100px; max-height: 100px; display: none;">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<div class="btnEditContainer">
						<button class="btnEdit btn1" type="submit">작성</button>
						<button class="btnEdit btn1" type="reset">재입력</button>
						<button class="btnEdit btn1" type="button"
							onclick="location.href='${cPath}/list.do';">목록보기</button>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>