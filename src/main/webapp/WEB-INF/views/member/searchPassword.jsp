<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고, 어때?</title>
<style>
body {
	text-align: center;
}
</style>

<script>
	
<%@include file="/WEB-INF/lib/jquery-3.7.1.min.js" %>
	$(document).ready(function() {
		$("#confirm").click(function() {

			var inputId = $("#inputId").val().trim();
			var inputEmail = $("#inputEmail").val().trim();

			if (inputId == "" || inputId.length == 0) {
				alert("ID를 입력해주세요");
				$("#inputId").focus();
				return;
			}
			if (inputEmail == "" || inputEmail.length == 0) {
				alert("이메일을 입력해주세요");
				$("#inputEmail").focus();
				return;
			}

			$.ajax({
				url : "/searchPassword",
				type : "post",
				data : {
					email : inputEmail,
					id : inputId
				},
				dataType : "json",
				success : function(response) {
					if (response.resultPass != null) {
						alert("귀하의 비밀번호는 " + response.resultPass + "입니다");
					} else {
						alert("일치하는 정보가 없습니다");
					}
				},
				error : function() {
					alert("비밀번호 찾기에 실패했습니다");
					$("#inputId").val("");
					$("#inputEmail").val("");
				}

			});//ajax끝 			
		});

		$("#cancel").click(function() {
			window.close();
		});
	});
</script>
</head>
<body>
	<br />
	<p>비밀번호 찾기</p>
	<input type="text" id="inputId" placeholder="ID를 입력해주세요">
	<br />
	<input type="text" id="inputEmail" placeholder="email을 입력해주세요">
	<br />
	<br />
	<button type="submit" id="confirm">비밀번호 찾기</button>
	<button id="cancel">취소</button>

</body>
</html>