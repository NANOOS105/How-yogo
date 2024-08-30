<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요고, 어때?</title>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR&display=swap')
	;
</style>
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<link rel="stylesheet" href="/Resources/CSS/Board/commuWrite.css">
<script type="text/javascript">
	function validateForm(form) {
		//필수 항목 입력 확인
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
		if (form.c_pwd.value == "") {
			alert("비밀번호를 입력하세요.");
			form.c_pwd.focus();
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
	<!-- 파일 첨부 작성 폼 -->
	<form name="writeFrm" method="post" enctype="multipart/form-data"
		action="${cPath}/write.do" onsubmit="return validateForm(this);">
		<table class="table tbl1" style="width: 90%">
			<tr>
				<td>작성자</td>
				<td><input class="inputCont" type="text" name="nickname"
					value="${bean.nickname}" style="width: 150px;" readonly /></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input class="inputCont" type="text" name="c_title"
					style="width: 90%" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="c_content"
						style="width: 90%; height: 100px;"></textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
					<!-- 파일 첨부만 되는 코드 --> <!--  <input type="file" name="c_imgOName"/> -->
					<!-- 파일 선택 시 이미지 미리 보기 --> <input type="file" name="c_imgOName"
					onchange="previewImage(event)" accept=".gif, .jpg, .jpeg, .png" />
					<!-- 이미지 미리 보기를 위한 영역 --> <img id="preview"
					src="./uploads/${ dto.c_imgSName }" alt="이미지 미리보기"
					style="max-width: 100px; max-height: 100px; display: none;">
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="c_pwd" style="width: 100px;" />
				</td>
			</tr>
			<tr>
				<td>카테고리</td>
				<td><input type="radio" id="category_together"
					name="c_category" value="같이요리" checked> <label
					for="category_together">같이요리</label> <input type="radio"
					id="category_cooking" name="c_category" value="요리신청"> <label
					for="category_request">요리신청</label></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<div class="btnWriteContainer">
						<button class="btnWrite btn1" type="submit">작성</button>
						<button class="btnWrite btn1" type="reset">재입력</button>
						<button class="btnWrite btn1" type="button"
							onclick="location.href='${cPath}/list.do';">목록보기</button>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>