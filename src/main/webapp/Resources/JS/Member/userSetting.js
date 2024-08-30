
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
				} else {
					alert("중복된 닉네임입니다");
					$("#nickname").val("");
				}
			},
			error: function() {
				alert("닉네임 중복 체크에 실패했습니다");
				$("#nickname").val("");
			}

		});//ajax끝 			
	});

});

//메인페이지로 가는 메서드 
$(document).ready(function() {
	$('a#intoMain').click(function() {
		window.location.href = '/how_yogo';
	});
});

//마이페이지로 가는 메서드 
$(document).ready(function() {
	$('a#intoMypage').click(function() {
		window.location.href = '/userSettingCon.do';
	});
});

//내가 쓴 글로 가는 메서드 
$(document).ready(function() {
	$('a#intoMyList').click(function() {
		window.location.href = '/myWriteRecipe.do';
	});
});

//우리만의 레시피로 가는 메서드 
$(document).ready(function() {
	$('a#intoRecipe').click(function() {
		window.location.href = '/RecipeList';
	});
});

//커뮤니티로 가는 메서드 
$(document).ready(function() {
	$('a#intoCommu').click(function() {
		window.location.href = '/list.do';
	});
});

//공지사항으로 가는 메서드 
$(document).ready(function() {
	$('a#intoNotice').click(function() {
		window.location.href = '/NoticeListCon.do';
	});
});

//관리자페이지로 가는 메서드 
$(document).ready(function() {
	$('a#AdminMain').click(function() {
		window.location.href = '/AdminMain';
	});
});


//회원 탈퇴하는 메서드 
$(function() {
	$("input#getout").click(function() {
		window.open("/intoGetout.do", "getout", "width=400,height=200")
	});

});



// 파일 선택시 미리보기 업데이트
$(function() {
	$("input#editImg").change(function() {
		const profilePreview = $("#profileimg");
		const editImg = this.files[0];

		if (editImg) {
			var reader = new FileReader();

			reader.onload = function(e) {
				profilePreview.attr('src', e.target.result);

				$("#afterImg").val(profilePreview.attr('src'));
			}
			reader.readAsDataURL(editImg);
		}
	});
});






