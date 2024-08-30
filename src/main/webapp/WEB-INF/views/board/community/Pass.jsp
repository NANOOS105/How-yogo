<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<title>요고, 어때?</title>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap')
	;
</style>
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<link rel="stylesheet" href="/Resources/CSS/Board/commuPass.css">

<script type="text/javascript">
	function validateForm(form) {
		if (form.c_pwd.value == "") {
			alert("비밀번호를 입력하세요.");
			form.c_pwd.focus();
			return false;
		}
	}

	var password = document.querySelector('.password');
	var error = document.querySelector('.error-message');
	var padlock = document.querySelector('.padlock');
	var arrow = document.querySelector('.arrow-icon');
	var input = document.querySelector('.input-icon');
	var pass = "dupa";
	password.addEventListener('keypress', function(e) {
		var key = e.which || e.keyCode;
		if (key === 13) {
			if (password.value != "") {
				if (password.value != pass) {
					error.classList.add('show');
					padlock.classList.remove('unlock');
				} else {
					error.classList.remove('show');
					unlock();
				}
			}
		}
	});
	password.addEventListener('keyup', function() {
		if (password.value == "") {
			error.classList.remove('show');
			padlock.classList.remove('unlock');
			arrow.classList.remove('show');
		} else {
			arrow.classList.add('show');
		}
	});
	arrow.addEventListener('click', function() {
		if (password.value != pass) {
			error.classList.add('show');
			padlock.classList.remove('unlock');
		} else {
			error.classList.remove('show');
			unlock();
		}
	}, false);
	function unlock() {
		padlock.classList.add('unlock');
		input.classList.add('unlock');
		setTimeout(function() {
			padlock.classList.add('hide');
			setTimeout(function() {
				// If the password is correct
				window.location.href = '';
			}, 900);
		}, 600);
	}
</script>
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/common/top.jsp" />
	</header>
	<h2>Please enter your password</h2>
	<form name="writeFrm" method="post" action="${cPath}/pass.do"
		onsubmit="return validateForm(this);">
		<input type="hidden" name="c_num" value="${ param.c_num }" /> <input
			type="hidden" name="mode" value="${ param.mode }" />
		<table style="width: 90%">
			<tr>
				<td></td>
				<td>
					<div class="center">
						<label><div class="padlock">
								<svg id="lock" viewbox="0 0 64 64">
    <circle stroke-width="2" fill="none" cx="32" cy="32" r="31" />
    <path
										d="M40.2,29v-4c0-4.5-3.7-8.2-8.2-8.2s-8.2,3.7-8.2,8.2v4H21v15h22V29H40.2z M27.2,25 c0-2.6,2.1-4.8,4.8-4.8s4.8,2.1,4.8,4.8v4h-9.5V25z" />
  </svg>
							</div></label>
						<div class="input-icon">
							<input class="inputPass input" type="password" name="c_pwd"
								style="width: 100px;" placeholder="Password" />
							<div class="arrow-icon">
								<svg id="arrow" viewbox="0 0 48 44">
        <polygon
										points="27.7,13.3 26.3,14.7 31.6,20 14,20 14,22 31.6,22 26.3,27.3 27.7,28.7 35.4,21 " />
      </svg>
							</div>
							<div class="error-message">Please try again.</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
				<br />
					<div class="btnContainer">
						<button class="btnPush btn1" type="submit">검증하기</button>
						<button class="btnPush btn1" type="reset">재입력</button>
						<button class="btnPush btn1" type="button"
							onclick="location.href='${cPath}/list.do';">목록보기</button>
					</div></td>
			</tr>
		</table>
	</form>
</body>
</html>