let isIdChecked = false;
let isNicknameChecked = false;

//로그인하는 메서드 
$(function() {
	$("button#loginAction").click(function() {
		const idVal = $("#loginId").val();
		const passwordVal = $("#loginPassword").val();
		const watchedPage = $("#watchedPage").val();

		if (idVal == "" || idVal.length == 0) {
			alert("id를 입력해주세요");
			$("#loginId").focus();
			return;
		}


		if (passwordVal == "" || passwordVal.length == 0) {
			alert("비밀번호를 입력해주세요");
			$("#loginPassword").focus();
			return;
		}

		$.ajax({
			url: "/loginActionCon.do",
			type: "post",
			data: { id: idVal, password: passwordVal, afterLoginId: idVal, watchedPage: watchedPage },
			dataType: "json",
			success: function(data) {

				if (data['result'] == 'false') {
					//로그인 실패
					alert("ID 또는 비밀번호의 정보가 다르거나 일치하는 회원 정보가 없습니다.");
					$("#loginId").val("");
					$("#loginPassword").val("");

				} else {
					//로그인 성공
					window.location.href = '/loginSession.do?afterLoginId=' + idVal + '&watchedPage=' + watchedPage;

				}

			},
			error: function() {
				alert("로그인에 실패하였습니다");
				$("#loginId").val("");
				$("#loginPassword").val("");

			}

		});//ajax끝 			
	});
});

//아이디 중복 체크 
$(function() {
	$("button#checkId").click(function() {

		event.preventDefault();
		const idVal = $("#id").val();
		let idValLen = idVal.length;

		if (idVal == "" || idVal.length == 0) {
			alert("id를 입력해주세요");
			$("#loginId").focus();
			return;
		}

		//회원글자길이 체크(6글자이상 15글자이하)
		if (idValLen < 6 || idValLen > 16) {
			alert("회원 id는 6글자 이상 ~ 15글자 이하이어야 합니다");
			$("#id").val("");
			$("#id").focus();
			return;
		}



		$.ajax({
			url: "/checkId.do",
			type: "post",
			data: { id: idVal },
			dataType: "json",
			success: function(data) {

				if (data['result'] == 'true') {
					alert("사용 가능한 ID입니다");
					isIdChecked = true;

				} else {
					alert("중복된 ID입니다");
					$("#id").val("");
					isIdChecked = false;

				}

			},
			error: function() {
				alert("ID 중복 체크에 실패했습니다");
				$("#id").val("");
				isIdChecked = false;
			}

		});//ajax끝 			


	});

});


//닉네임 중복 체크 
$(function() {
	$("button#checkNick").click(function() {
		event.preventDefault();
		const nickVal = $("#nickname").val();

		if (nickVal == "" || nickVal.length == 0) {
			alert("닉네임을 입력해주세요");
			$("#nickname").focus();
			return;
		}

		$.ajax({
			url: "/checkNick.do",
			type: "post",
			data: { nickname: nickVal },
			dataType: "json",
			success: function(data) {

				if (data['result'] == 'true') {
					alert("사용 가능한 닉네임입니다");
					isNicknameChecked = true;
				} else {
					alert("중복된 닉네임입니다");
					$("#nickname").val("");
					isNicknameChecked = false;
				}
			},
			error: function() {
				alert("닉네임 중복 체크에 실패했습니다");
				$("#nickname").val("");
				isNicknameChecked = false;
			}

		});//ajax끝 			
	});

});

//회원가입
$(function() {
	$("button#registerSubmit").click(function() {
		const registerFrm = $("form#registerFrm");
		let memberVal = $("#id").val().trim();
		let memberValLen = memberVal.length;

		if (memberVal == "" || memberVal.length == 0) {
			alert("회원id는 필수입력입니다.");
			$("#id").focus();
			return;
		}

		//회원글자길이 체크(6글자이상 15글자이하)
		if (memberValLen < 6 || memberValLen > 16) {
			alert("회원 id는 6글자 이상 ~ 15글자 이하이어야 합니다");
			$("#id").val("");
			$("#id").focus();
			return;
		}

		//닉네임 필수입력 
		if ($("#nickname").val() == "" || $("#nickname").val().trim().length == 0) {
			alert("닉네임을 입력해주세요♥");
			$("#nickname").focus();
			return;
		}

		//이메일 필수입력 
		if ($("#email").val() == "" || $("#email").val().trim().length == 0) {
			alert("이메일을 입력해주세요♥");
			$("#nickname").focus();
			return;
		}

		//비밀번호 필수입력 
		let pwdVal1 = $("#password").val();
		let pwdVal2 = $("#passwordcheck").val();
		if (pwdVal1 == "" || pwdVal1.trim().length == 0) {
			alert("비밀번호를 입력해주세요♥");
			$("#password1").focus();
			return;
		}


		//비밀번호확인 필수입력 
		if (pwdVal2 == "" || pwdVal2.trim().length == 0) {
			alert("비밀번호 확인을 입력해주세요♥");
			$("#password2").focus();
			return;
		}

		//비밀번호와 비밀번호 확인 일치여부 
		if (pwdVal1 != pwdVal2) {
			alert("비밀번호와 비밀번호 확인이 일치하지 않습니다");
			$("#password").val("");
			$("#passwordcheck").val("");
			$("#passwordcheck").focus();
			return;
		}

		// 닉네임 중복 체크 여부 확인
		if (!isIdChecked) {
			alert("아이디 중복 체크를 해주세요");
			return;
		}
		if (!isNicknameChecked) {
			alert("닉네임 중복 체크를 해주세요");
			return;
		}
		if (!$("input[type='checkbox']").is(":checked")) {
			alert("회원 가입에 동의해주세요");
			$("input[type='checkbox']").focus();
			return;
		}

		registerFrm.attr("method", "post");
		registerFrm.attr("action", "/registerProc.do");
		registerFrm.submit();//서버로 폼데이터 전송 

	});

});

//창닫는 메서드 
$(document).ready(function() {
	$('.icon-close').click(function() {
		window.location.href = '/how_yogo';
	});
});

//회원 id 찾는 메서드
$(function() {
	$("a#searchId").click(function() {
		window.open("/intoSearchId.do", "intoSearchId", "width=400,height=200")
	});

});

//회원 password 찾는 메서드
$(function() {
	$("a#searchPassword").click(function() {
		window.open("/intoSearchPassword.do", "intoSearchPassword", "width=400,height=250")
	});

});

const wrapper = document.querySelector('.wrapper');
const login = document.querySelector('.login-register1 a.login');
const register = document.querySelector('.login-register a.register');
//const btnPopup = document.querySelector('.btnLogin-popup');
//const iconClose = document.querySelector('.icon-close');

register.addEventListener('click', () => {
	wrapper.classList.add('active');
});
login.addEventListener('click', () => {
	wrapper.classList.remove('active');
});

/*btnPopup.addEventListener('click',()=> { 
	wrapper.classList.add('active-popup');
});

iconClose.addEventListener('click',()=> { 
	wrapper.classList.remove('active-popup');
});*/




