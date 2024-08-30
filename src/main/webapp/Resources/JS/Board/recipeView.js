// 댓글 작성 버튼 클릭 이벤트 처리
function handleCommentButtonClick(event) {
	event.preventDefault();

	if (!isLoggedIn) {
		var confirmation = confirm('로그인이 필요한 기능입니다. 로그인 페이지로 이동하시겠습니까?');
		if (confirmation) {
			window.location.href = 'loginForm.do';
		}
	} else {
		// 댓글 작성 폼 제출
		event.target.form.submit();
	}
}

// 대댓글 작성 폼 보이기
function showReplyForm(commentId) {
	if (!isLoggedIn) {
		var confirmation = confirm('로그인이 필요한 기능입니다. 로그인 페이지로 이동하시겠습니까?');
		if (confirmation) {
			window.location.href = 'loginForm.do';
		}
	} else {
		var replyForm = document.getElementById("replyForm" + commentId);
		if (replyForm.style.display === "none") {
			replyForm.style.display = "block";
		} else {
			replyForm.style.display = "none";
		}
	}
}

// 댓글 수정 폼 보이기
function showEditForm(commentId) {
	var editForm = document.getElementById("editForm" + commentId);
	if (editForm.style.display === "none") {
		editForm.style.display = "block";
	} else {
		editForm.style.display = "none";
	}
}

// 페이지 로드 시 좋아요 버튼 초기화
window.addEventListener('load', function() {
	initLikeButtons();
});

// 좋아요 버튼 초기화 함수
function initLikeButtons() {
	document.querySelectorAll('.like-button').forEach(function(button) {
		var recipeId = button.getAttribute('data-recipe-id');

		// 서버에서 좋아요 상태 확인
		if (isLoggedIn) {
			var memberid = user_memberid;
			fetch('RecipeLikeStatus?memberid=' + encodeURIComponent(memberid) + '&recipeId=' + encodeURIComponent(recipeId))
				.then(function(response) {
					return response.json();
				})
				.then(function(data) {
					if (data.isLiked) {
						button.classList.add('liked');
					}
				});
		}

		// 좋아요 버튼 클릭 이벤트 처리
		button.addEventListener('click', function(event) {
			event.preventDefault();
			var isLiked = this.classList.contains('liked');

			if (!isLoggedIn) {
				var confirmation = confirm('로그인이 필요한 기능입니다. 로그인 페이지로 이동하시겠습니까?');
				if (confirmation) {
					window.location.href = 'loginForm.do';
				}
			} else {
				var memberid = user_memberid;
				// 서버로 좋아요 상태 변경 요청 보내기
				fetch('RecipeLike', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					body: 'memberid=' + encodeURIComponent(memberid) + '&recipeId=' + encodeURIComponent(recipeId)
				})
					.then(function(response) {
						if (response.ok) {
							// 좋아요 상태 변경 성공 시 버튼 모양 변경
							if (isLiked) {
								button.classList.remove('liked');
							} else {
								button.classList.add('liked');
							}
						}
					});
			}
		});
	});
}
