// 페이지 로드 시 정렬 버튼 초기화
window.addEventListener('load', function() {
	var urlParams = new URLSearchParams(window.location.search);
	var searchWord = urlParams.get('searchWord') || '';
	document.querySelector('input[name="searchWord"]').value = searchWord;

	var sortBy = urlParams.get('sort') || 'latest';
	var pageNum = urlParams.get('pageNum') || 1;

	sendSortRequest(sortBy, pageNum, searchWord);
	initSortButtons();
	initLikeButtons();
	updateViewFormat();
});

document.getElementById('toolbar-active').addEventListener('change', function() {
	updateViewFormat();
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

// 보기 형식 업데이트 함수
function updateViewFormat() {
	var listSection = document.getElementById('listSection');
	var cardSection = document.getElementById('cardSection');
	var toolbarActive = document.getElementById('toolbar-active');

	if (toolbarActive.checked) {
		cardSection.style.display = 'block';
		listSection.style.display = 'none';
	} else {
		cardSection.style.display = 'none';
		listSection.style.display = 'block';
	}
}


// 검색 폼 제출 이벤트 처리
document.getElementById('searchForm').addEventListener('submit', function(event) {
	event.preventDefault(); // 폼 제출 기본 동작 막기
	var searchWord = document.querySelector('input[name="searchWord"]').value;
	//var sortBy = document.querySelector('.toggle-button.active').dataset.sort;
	sendSearchRequest(searchWord);
});

// 검색 요청 보내기 함수
function sendSearchRequest(searchWord) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				updateRecipeList(xhr.responseText, searchWord);
			} else {
				// 오류 처리 코드 추가
			}
		}
	};
	xhr.open('GET', 'RecipeList?searchWord=' + encodeURIComponent(searchWord), true);
	xhr.send();
}

// 정렬 요청 보내기 함수
function sendSortRequest(sortBy, pageNum = 1) {
	var searchWord = document.querySelector('input[name="searchWord"]').value;
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				updateRecipeList(xhr.responseText, searchWord);
			} else {
				// 오류 처리 코드 추가
			}
		}
	};
	xhr.open('GET', 'RecipeList?sort=' + sortBy + '&pageNum=' + pageNum + '&searchWord=' + encodeURIComponent(searchWord), true);
	xhr.send();
}

// 정렬 버튼 클릭 이벤트 처리
function initSortButtons() {
	const toggleButtons = document.querySelectorAll('.toggle-button');
	toggleButtons.forEach((button) => {
		button.addEventListener('click', function(event) {
			event.preventDefault();
			if (!this.classList.contains('active')) {
				toggleButtons.forEach((btn) => btn.classList.remove('active'));
				this.classList.add('active');
				const sortBy = this.dataset.sort;
				const pageNum = new URLSearchParams(window.location.search).get('pageNum') || 1;
				const searchWord = document.querySelector('input[name="searchWord"]').value;
				sendSortRequest(sortBy, pageNum, searchWord);
			}
		});
	});
}

function attachPagingLinkListeners() {
	var pagingLinks = document.querySelectorAll('#page a');
	pagingLinks.forEach(function(link) {
		link.addEventListener('click', function(event) {
			event.preventDefault();
			var pageNum = new URLSearchParams(this.getAttribute('href')).get('pageNum') || 1;
			var sortBy = document.querySelector('.toggle-button.active').dataset.sort;
			var searchWord = document.querySelector('input[name="searchWord"]').value;
			sendSortRequest(sortBy, pageNum, searchWord);
		});
	});
}

// 레시피 목록 업데이트 함수
function updateRecipeList(responseText, searchWord) {
	var recipeListSection = document.getElementById('recipe-list');
	var tempElement = document.createElement('div');
	tempElement.innerHTML = responseText;
	var newRecipeList = tempElement.querySelector('section');
	recipeListSection.innerHTML = newRecipeList.innerHTML;

	initLikeButtons();
	updateViewFormat();
	initSortButtons();

	// 페이지 링크 업데이트
	var pagingLinks = document.querySelectorAll('#page a');
	pagingLinks.forEach(function(link) {
		var pageNum = link.textContent;
		var sortBy = document.querySelector('.toggle-button.active').dataset.sort;
		link.href = 'RecipeList?searchWord=' + encodeURIComponent(searchWord) + '&pageNum=' + pageNum + '&sort=' + sortBy;
	});

	attachPagingLinkListeners();

	// 검색어 표시
	var searchWordElement = document.getElementById('search-word');
	if (searchWord) {
		searchWordElement.textContent = '검색어: ' + searchWord;
	} else {
		searchWordElement.textContent = '';
	}
} 