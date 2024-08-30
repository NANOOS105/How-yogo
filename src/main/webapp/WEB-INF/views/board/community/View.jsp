<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cPath" value="<%=request.getContextPath()%>" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap')
	;

@import
	url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR&display=swap')
	;
</style>
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<link rel="stylesheet" href="/Resources/CSS/Board/commuView.css">
<title>요고, 어때?</title>
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/common/top.jsp" />
	</header>

	<table style="width: 90%" class="table tbl1">
		<colgroup>
			<col width="15%" />
			<col width="35%" />
			<col width="15%" />
			<col width="*" />
		</colgroup>
		<!-- 게시글 정보 -->
		<tr>
			<td>번호</td>
			<td>${dto.c_num }</td>
			<td>작성자</td>
			<td>${dto.nickname }</td>
		</tr>
		<tr>
			<td>작성일</td>
			<td>${dto.c_postdate }</td>
			<td>조회수</td>
			<td>${dto.c_hits }</td>
		</tr>
		<tr>
			<td>카테고리</td>
			<td colspan="3">${dto.c_category }</td>
		</tr>
		<tr>
			<td>제목</td>
			<td colspan="3">${dto.c_title }</td>
		</tr>
		<tr>
			<td>내용</td>
			<td colspan="3" height="100">${ dto.c_content }<c:if
					test="${ not empty dto.c_imgOName and isImage eq true }">
					<br>
					<img src="./uploads/${ dto.c_imgSName }" style="max-width: 100%;" />
				</c:if>
			</td>
		</tr>
		<!-- 하단 메뉴(버튼) -->
		<tr>
			<td colspan="4" align="center">
				<div class="btnViewContainer">
					<c:if test="${isAuthor}">
						<button class="btnView btn1" type="button"
							onclick="location.href='${cPath}/pass.do?mode=edit&c_num=${ param.c_num }';">
							수정</button>
						<button class="btnView btn1" type="button"
							onclick="location.href='${cPath}/pass.do?mode=delete&c_num=${ param.c_num }';">
							삭제</button>
					</c:if>

					<button class="btnView btn1" type="button"
						onclick="location.href='${cPath}/list.do';">목록보기</button>
				</div>
			</td>
		</tr>
	</table>
	<br>
	<br>

	<!-- 댓글 작성 폼 -->
	<c:if test="${not empty bean}">
		<form action="ComComment" method="post">
			<input type="hidden" name="action" value="insert"> <input
				type="hidden" name="c_num" value="${dto.c_num}"> <input
				type="hidden" name="nickname" value="${bean.nickname }">
			<textarea class="comment-list" name="co_content" required></textarea>
			<input type="hidden" name="co_parentId" value="0"> <input
				class="btnView btn2" type="submit" value="작성">
		</form>
	</c:if>

	<!-- 댓글 목록 -->
	<ul>
		<c:forEach var="comment" items="${commentList}">
			<li>
				<div style="margin-left: ${(comment.depth - 1) * 20}px;">
					<p>${comment.nickname}|${comment.co_postDate}</p>
					<p>${comment.co_content}</p>
					<c:if test="${bean.nickname == comment.nickname}">
						<form action="/ComComment" method="post">
							<input type="hidden" name="action" value="delete"> <input
								type="hidden" name="comId" value="${comment.comId}"> <input
								type="hidden" name="c_num" value="${dto.c_num}"> <input
								class="btnView btn2" type="submit" value="삭제">
						</form>
						<form action="/ComComment" method="post">
							<input type="hidden" name="action" value="updateForm"> <input
								type="hidden" name="comId" value="${comment.comId}"> <input
								type="hidden" name="c_num" value="${dto.c_num}">
							<button type="button" onclick="showEditForm(${comment.comId})">수정</button>
						</form>
						<div id="editForm${comment.comId}" style="display: none;">
							<form action="/ComComment" method="post">
								<input type="hidden" name="action" value="update"> <input
									type="hidden" name="comId" value="${comment.comId}"> <input
									type="hidden" name="c_num" value="${dto.c_num}">
								<textarea class="comment-list" name="co_content" required>${comment.co_content}</textarea>
								<input class="btnView btn2" type="submit" value="저장">
							</form>
						</div>
					</c:if>
					<c:if test="${not empty bean}">
						<button onclick="showReplyForm(${comment.comId})">댓글</button>
						<form action="/ComComment" method="post"
							id="replyForm${comment.comId}" style="display: none;">
							<input type="hidden" name="action" value="insert"> <input
								type="hidden" name="c_num" value="${dto.c_num}"> <input
								type="hidden" name="nickname" value="${bean.nickname }">
							<input type="hidden" name="co_parentId" value="${comment.comId}">
							<textarea class="comment-list" name="co_content" required></textarea>
							<input class="btnView btn2" type="submit" value="작성">
						</form>
					</c:if>
				</div>
			</li>
		</c:forEach>
	</ul>
	<script>
        function showReplyForm(comId) {
            var replyForm = document.getElementById("replyForm" + comId);
            if (replyForm.style.display === "none") {
                replyForm.style.display = "block";
            } else {
                replyForm.style.display = "none";
            }
        }
    </script>

	<script>
    function showEditForm(comId) {
        var editForm = document.getElementById("editForm" + comId);
        if (editForm.style.display === "none") {
            editForm.style.display = "block";
        } else {
            editForm.style.display = "none";
        }
    }
</script>

</body>
</html>