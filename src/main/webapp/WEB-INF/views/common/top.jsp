<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
	<head>
	  <meta charset="UTF-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1.0">
	  <title>how_yogo Top</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/Resources/CSS/Common/top.css">
	</head>
	
	<body>
		  <header>
		    <div class="banner">
		      <div class="banner_logo">
		        <a href="how_yogo" class="banner_link"><img src="<%= request.getContextPath() %>/Resources/Image/Common/header_logo2.png" class="banner_logo"></a>
		      </div>
		      
		      <div class="search_container">
			    <form id="searchForm" method="get" action="RecipeList" >  
		            <input type="text" name="searchWord" placeholder="레시피 검색" class="search_txt" />
		            <button type="submit" class="search_btn">
            			<img src="<%= request.getContextPath() %>/Resources/Image/Common/search_icon.png" alt="검색" />
        			</button>
			    </form>
		      </div>
		
		      <div>
		        <div class="login_container">
			    	<!-- 이미지들어가는 부분  -->
					<span class="icon"> </span>
					<c:if test="${not empty bean}">
					<div class="profile-container">
						
	                   	<c:choose>
	                       <c:when test="${not empty bean.imgSrc and not empty bean.imgSname}">
	                           <img class="profileimg" id="profileimg" src="${bean.imgSrc}/${bean.imgSname}" alt="Profile Image">
	                       </c:when>
	                       <c:otherwise>
	                           <!-- 기본 이미지 경로를 설정해주세요 -->
	                           <img class="profileimg" id="profileimg" src="<%= request.getContextPath()%>/Resources/Image/Member/cookingcat.PNG" alt="Default Profile Image">
	                       </c:otherwise>
	                   	</c:choose>
	                   	
	                   	<span class="welcome_message" id="welcome_message">${bean.nickname}</span>
	                   	<input type="hidden" name="memberid" value="memberid">
				        
				    </div>
				            <div class="profile_buttons">
				              <a href="/userSettingCon.do" class="profile_button">회원정보 수정</a>
				              <a href="/logout.do" class="profile_button">로그아웃</a>
				            </div>
                 	</c:if>
                 	<c:if test="${empty bean}">
		          		<div class="login_button">
			          	<a href="loginForm.do" class="login_link">로그인</a>
			          	</div>
	          		</c:if>
		        </div><!-- login container -->
		     </div>
		    </div>
		    
		    <div class="menu_bar">
		      <div class="RecipeList"><a href="RecipeList" class="menu_link">Recipe</a>
		          <ul class="recipe_menu">
		            <li><a href="RecipeList?searchWord=한식">한식</a></li>
		            <li><a href="RecipeList?searchWord=양식">양식</a></li>
		            <li><a href="RecipeList?searchWord=중식">중식</a></li>
		            <li><a href="RecipeList?searchWord=일식">일식</a></li>
		          </ul>
		      </div>
		
		      <div class="community_board">
		      <a href="/list.do" class="menu_link">Community</a>
		        <ul class="community_menu">
		          <li><a href="${cPath}/CategoryService?category=같이요리">같이 요리</a></li>
		          <li><a href="${cPath}/CategoryService?category=요리신청">요리 신청</a></li>
		        </ul>
		      </div>
		      
		      <div class="notice_board"><a href="NoticeListCon.do" class="menu_link">Notice</a>
		      </div>
		
		    </div>
		  </header>
	<script src="<%= request.getContextPath() %>/Resources/JS/Common/top.js"></script>
	<script>
	    var isLoggedIn = ${not empty bean.memberid};
	</script>
	</body>
</html>