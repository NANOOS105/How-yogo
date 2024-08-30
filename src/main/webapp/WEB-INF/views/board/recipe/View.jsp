<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요고, 어때?</title>
<link rel="stylesheet" href="/Resources/CSS/Board/recipeView.css">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&family=Noto+Serif+KR&display=swap')
	;
</style>
</head>
<body>
	<header>
		<jsp:include page="../../common/top.jsp" />
	</header>

	<section id="body">

		<!-- 작성자 메뉴 -->
		<c:choose>
			<c:when test="${bean.nickname == boardDto.nickname}">
				<div class="edit-delete">
					<a href="RecipeEdit?recipeId=${boardDto.recipeId}"
						onclick="return confirm('수정하시겠습니까?')">수정</a> <a
						href="RecipeDelete?recipeId=${boardDto.recipeId}"
						onclick="return confirm('삭제하시겠습니까?')">삭제</a>
				</div>
			</c:when>
		</c:choose>

		<!-- 레시피 상세조회 -->
		<div class="recipe-header">
			<img src="/uploads/${boardDto.mainSName}" alt="대표 이미지">
			<h1 class="recipe-title">${boardDto.recipeTitle}</h1>
			<div class="recipe-info">
				<p>글쓴이: ${boardDto.nickname}</p>
				<div class="likes-views">
					<div class="views">
						<i class="fas fa-eye"></i> ${boardDto.hits}
					</div>
					<div class="likes">
						<button
							class="like-button ${dao.isLiked(bean.memberid, boardDto.recipeId) ? 'liked' : ''}"
							data-recipe-id="${boardDto.recipeId}"
							onclick="handleLikeButtonClick(event)">
							<i class="far fa-heart"></i> ${likeCount}
						</button>
					</div>
				</div>
			</div>
		</div>

		<h3>재료</h3>
		<hr />
		<div class="recipe-section">
			<div class="recipe-image">
				<img src="/uploads/${boardDto.inSName}" alt="재료 이미지">
			</div>
			<div class="recipe-content">
				<ul>
					<c:forEach var="ingredient" items="${ingredientsMap}">
						<li class="ingredients-box"><div id="key">${ingredient.key}</div>
							<div id="value">${ingredient.value}</div></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="recipe-steps">
			<h3>레시피</h3>
			<hr />
			<ul>
				<c:forEach var="step" items="${stepList}">
					<li>
						<div class="recipe-section">
							<div class="recipe-image">
								<img src="/uploads/${step.imgSName}" alt="요리 순서 이미지">
							</div>
							<div class="recipe-content">
								<h3>Step ${step.recipeStep}</h3>
								<hr />
								<p>${step.stepContent}</p>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<hr />
		<a href="RecipeList" style="margin-right: 0px;">목록보기</a>
	</section>

	<section id="reply">
		<br />
		<br />
		<br />
		<!-- 댓글 작성 폼 -->
		<h3>댓글</h3>
		<form id="commentForm" action="RecipeComment" method="post">
			<input type="hidden" name="action" value="insert"> <input
				type="hidden" name="recipeId" value="${boardDto.recipeId}">
			<input type="hidden" name="memberid" value="${bean.memberid}">
			<input type="hidden" name="nickname" value="${bean.nickname}">
			<textarea name="commentContent" required></textarea>
			<input type="hidden" name="parentComment" value="0"> <input
				type="submit" value="작성" onclick="handleCommentButtonClick(event)"
				class="comment-submit-button">
		</form>
		<!-- 댓글 목록 -->
		<ul id="comment-list">
			<c:forEach var="comment" items="${commentList}">
				<li>
					<div style="margin-left: ${(comment.depth - 1) * 20}px;">
						<p>${comment.nickname}| ${comment.postDate}</p>
						<p>${comment.commentContent}</p>
						<button onclick="showReplyForm(${comment.commentId})"
							class="comment-submit-button">댓글 작성</button>
						<form action="/RecipeComment" method="post"
							id="replyForm${comment.commentId}" style="display: none;">
							<input type="hidden" name="action" value="insert"> <input
								type="hidden" name="recipeId" value="${boardDto.recipeId}">
							<input type="hidden" name="nickname" value="${bean.nickname}">
							<input type="hidden" name="memberid" value="${bean.memberid}">
							<input type="hidden" name="parentId" value="${comment.commentId}">
							<textarea name="commentContent" required></textarea>
							<input type="submit" value="작성" class="comment-submit-button">
						</form>
						<c:if test="${bean.nickname == comment.nickname}">
							<div class="more-actions">
								<i class="fas fa-ellipsis-v" style="color: #207f59;"></i>
								<div class="dropdown-menu">
									<form action="/RecipeComment" method="post">
										<input type="hidden" name="action" value="edit"> <input
											type="hidden" name="commentId" value="${comment.commentId}">
										<input type="hidden" name="recipeId"
											value="${boardDto.recipeId}">
										<button type="button"
											onclick="showEditForm(${comment.commentId})">
											<i class="fas fa-pencil-alt"></i> 수정
										</button>
									</form>
									<form action="/RecipeComment" method="post"
										class="delete-comment-form">
										<input type="hidden" name="action" value="delete"> <input
											type="hidden" name="commentId" value="${comment.commentId}">
										<input type="hidden" name="recipeId"
											value="${boardDto.recipeId}">
										<button type="submit">
											<i class="fas fa-times"></i> 삭제
										</button>
									</form>
								</div>
							</div>
							<div class="edit-comment-form" id="editForm${comment.commentId}"
								style="display: none;">
								<form action="/RecipeComment" method="post">
									<input type="hidden" name="action" value="edit"> <input
										type="hidden" name="commentId" value="${comment.commentId}">
									<input type="hidden" name="recipeId"
										value="${boardDto.recipeId}">
									<textarea name="commentContent" required>${comment.commentContent}</textarea>
									<input type="submit" value="작성" class="comment-submit-button">
								</form>
							</div>
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>
	</section>

	<script>
    var isLoggedIn = ${not empty bean.memberid};
    var user_memberid = "${bean.memberid}";
    var this_recipeId = "${boardDto.recipeId}";
    
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

</script>
</body>
</html>