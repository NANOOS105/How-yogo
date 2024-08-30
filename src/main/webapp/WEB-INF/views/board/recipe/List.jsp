<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link rel="stylesheet" href="/Resources/CSS/Board/list.css">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&family=Noto+Serif+KR&display=swap')
	;
</style>
<title>요고, 어때?</title>
</head>
<body>
	<input type="hidden" name="memberid" value="${bean.memberid}" />
	<input type="hidden" name="nickname" value="${bean.nickname}" />
<head>
<jsp:include page="/WEB-INF/views/common/top.jsp" />
</head>

<div class="buttons">
	<!-- 보기 형식 기능 -->
	<div class="checkbox-switch">
		<c:set var="viewFormat"
			value="${ empty param.viewFormat ? 'card' : param.viewFormat }" />
		<input type="checkbox" ${ viewFormat eq 'card' ? 'checked' : '' }
			onchange="T.toggleToolbarStatus()" value="1" name="status"
			class="input-checkbox" id="toolbar-active">
		<div class="checkbox-animate">
			<span class="checkbox-off">List</span> <span class="checkbox-on">Card</span>
		</div>
	</div>

	<!-- 정렬 버튼 -->
	<div class="toggle-container">
		<div class="toggle-buttons">
			<button class="toggle-button active" data-sort="latest">Latest</button>
			<button class="toggle-button" data-sort="views">Views</button>
			<button class="toggle-button" data-sort="likes">Likes</button>
		</div>
	</div>
</div>

<!-- 전체 레시피 리스트 -->
<section id="recipe-list">
	<c:choose>
		<c:when test="${ empty recipeLists }">
			<p>등록된 게시물이 없습니다^^*</p>
		</c:when>
		<c:otherwise>
			<!-- 목록 테이블 -->
			<section id="listSection" style="display: none;">
				<table border="1" width="90%">
					<tr>
						<th width="10%">번호</th>
						<th width="*">제목</th>
						<th width="15%">글쓴이</th>
						<th width="10%">조회수</th>
						<th width="10%">좋아요</th>
						<th width="8%">작성일</th>
					</tr>
					<c:forEach items="${ recipeLists }" var="row" varStatus="loop">
						<tr align="center">
							<td>${ row.recipeId }</td>
							<td align="left"><a
								href="RecipeView?recipeId=${ row.recipeId }">${ row.recipeTitle }</a>
							</td>
							<td>${ row.nickname }</td>
							<td><div class="views">
									<i class="fas fa-eye"></i> ${row.hits}
								</div></td>
							<td><button
									class="like-button ${dao.isLiked(bean.memberid, row.recipeId) ? 'liked' : ''}"
									data-recipe-id="${row.recipeId}"
									onclick="handleLikeButtonClick(event)">
									<i class="far fa-heart"> ${row.likecount}</i>
								</button></td>
							<td>${ row.postDate }</td>
						</tr>
					</c:forEach>
				</table>
			</section>

			<!-- 피드 형식 -->
			<section id="cardSection">
				<c:forEach items="${ recipeLists }" var="row" varStatus="loop">
					<div class="card">
						<img src="/uploads/${row.mainSName}" alt="${ row.recipeTitle }">
						<div class="card-content">
							<div class="card-title">
								<a href="RecipeView?recipeId=${ row.recipeId }">${ row.recipeTitle }</a>
							</div>
							<div class="card-author">${ row.nickname }</div>
							<div class="card-category">${ row.recipeCategory }</div>
							<div class="card-likes">
								<div class="views">
									<i class="fas fa-eye"></i> ${row.hits}
								</div>
								<button
									class="like-button ${dao.isLiked(bean.memberid, row.recipeId) ? 'liked' : ''}"
									data-recipe-id="${row.recipeId}"
									onclick="handleLikeButtonClick(event)">
									<i class="far fa-heart"> ${row.likecount}</i>
								</button>
							</div>
						</div>
					</div>
				</c:forEach>
			</section>
		</c:otherwise>
	</c:choose>
</section>

<!-- 하단 메뉴(바로가기, 글쓰기) -->
<div class="bottom">
	<div id="page">
		<c:choose>
			<c:when test="${ not empty param.sort }">
				<c:set var="sortBy" value="${ param.sort }" />
			</c:when>
			<c:otherwise>
				<c:set var="sortBy" value="latest" />
			</c:otherwise>
		</c:choose>
		${ map.pagingImg }
	</div>
	<div>
		<c:choose>
			<c:when test="${ not empty bean.memberid }">
				<form action="RecipeWrite" method="get">
					<input type="submit" value="글쓰기" />
				</form>
			</c:when>
		</c:choose>
	</div>
</div>
<script src="https://kit.fontawesome.com/your-font-awesome-kit.js"
	crossorigin="anonymous"></script>
<script>
    var isLoggedIn = ${not empty bean.memberid};
    var user_memberid = "${bean.memberid}";
    
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
</script>
</body>
</html>