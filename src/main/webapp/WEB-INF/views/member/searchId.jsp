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

			var confirmText = $("#inputEmail").val().trim();
			if (confirmText == "" || confirmText.length == 0) {
				alert("이메일을 입력해주세요");
				$("#inputEmail").focus();
				return;
			}

			$.ajax({
				url : "/searchId",
				type : "post",
				data : {
					email : confirmText
				},
				dataType : "json",
				success : function(response) {
					if (response.resultId != null) {
						alert("귀하의 ID는 " + response.resultId + "입니다");
					} else {
						alert("일치하는 ID가 없습니다");
					}
				},
				error : function() {
					alert("ID 찾기에 실패했습니다");
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
	<p>ID 찾기</p>
	<input type="text" id="inputEmail" placeholder="email을 입력해주세요">
	<br />
	<br />
	<button type="submit" id="confirm">ID찾기</button>
	<button id="cancel">취소</button>

</body>
</html>