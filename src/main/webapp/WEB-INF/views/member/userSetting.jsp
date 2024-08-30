
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="cPath" value="<%=request.getContextPath()%>" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고, 어때?</title>
<link rel="stylesheet" href="/Resources/CSS/Member/userSetting.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Nanum+Pen+Script&family=Yeon+Sung&display=swap"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Gasoek+One&family=Nanum+Pen+Script&family=Yeon+Sung&display=swap"
	rel="stylesheet">
<script><%@include file="/WEB-INF/lib/jquery-3.7.1.min.js" %></script>
<c:if test="${msg!=null }">
	<script type="text/javascript">
		alert("${msg }");
	</script>
</c:if>


</head>
<body>
	<nav class="navbar">
		<ul class="navbar__menu">
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoMain"><ion-icon name="home-outline"></ion-icon><span>Home</span></a>
			</li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoMypage"><ion-icon name="accessibility-outline"></ion-icon><span>MyPage</span></a>
			</li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoRecipe"><ion-icon name="fast-food-outline"></ion-icon><span>Recipe</span></a>
			</li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoCommu"><ion-icon name="musical-notes-outline"></ion-icon><span>Community</span></a>
			</li>
			<li class="navbar__item"><a href="#" class="navbar__link"
				id="intoNotice"><ion-icon name="notifications-outline"></ion-icon><span>Notice</span></a>
			</li>
			<c:if
				test="${not empty sessionScope.bean && sessionScope.bean.memberid == 'admin12'}">
				<li class="navbar__item"><a href="#" class="navbar__link"
					id="AdminMain"><ion-icon name="construct-outline"></ion-icon><span>Admin</span></a>
				</li>
			</c:if>
		</ul>
	</nav>
	<form method="post" action="/userSettingProcCon.do"
		enctype="multipart/form-data">
		<div class="wrapper">
			<div class="form-box userSetting">
				<h2>My page</h2>
				<div class="input-box">
					<span class="icon"><ion-icon name="person"></ion-icon></span> <span
						name="id" class="idspan" id="id">Id :${bean.memberid}</span>
				</div>
				<div class="input-box">
					<span class="icon"><button class="btn1" id="checkNick">중복체크</button></span>
					<input type="text" name="nickname" id="nickname"
						value="${bean.nickname}" /> <label>Nickname :
						${bean.nickname}</label>
				</div>
				<div class="input-box">
					<span class="icon"><ion-icon name="mail"></ion-icon></span> <span
						name="email" class="idspan">Email : ${bean.email}</span>
				</div>
				<div class="input-box">
					<span class="icon"><ion-icon name="checkmark-outline"></ion-icon></span>
					<input type="password" name="password" placeholder="비밀번호를 입력해주세요" />
					<label>password</label> <input type="hidden" name="originalPWD"
						value="${bean.mpwd}">
				</div>

			</div>
			<div>
				<div>
					<label class="editImg" name="editImgbtn" id="editImgbtn"
						for="editImg">Edit</label> <input type="file" class="btn1 hidden"
						id="editImg" accept="image/*" name="editImg" />
					<!-- 이미지들어가는 부분  -->
					<span class="icon"> </span>
					<c:if test="${not empty sessionScope.bean}">
						<c:choose>
							<c:when
								test="${not empty sessionScope.bean.imgSrc and not empty sessionScope.bean.imgSname}">
								<img class="profileimg" id="profileimg"
									src="${sessionScope.bean.imgSrc}/${sessionScope.bean.imgSname}"
									alt="Profile Image">
							</c:when>
							<c:otherwise>
								<!-- 기본 이미지 경로를 설정해주세요 -->
								<img class="profileimg" id="profileimg"
									src="<%= request.getContextPath()%>/Resources/Image/Member/cookingcat.PNG"
									alt="Default Profile Image">
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<input type="hidden" id="uploadPath" name="uploadPath" value="">
				<input type="hidden" id="newFileName" name="newFileName" value="">
				<div>
					<button type="submit" class="btn">회원정보수정</button>
					<input type="button" value="탈퇴" class="getout" id="getout" />
				</div>
			</div>
		</div>
	</form>

	<script src="\Resources\JS\Member\userSetting.js"></script>
	<script type="module"
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

</body>
</html>