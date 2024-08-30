<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="cPath" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고, 어때?</title>
<c:if test="${msg!=null }">
	<script type="text/javascript">
	alert("${msg }");
	</script>

</c:if>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
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
<link rel="stylesheet" href="/Resources/CSS/Member/login.css">
<script><%@include file="/WEB-INF/lib/jquery-3.7.1.min.js" %></script>
</head>
<body>

	<div class="wrapper">
		<span class="icon-close" id="icon-close"> <ion-icon
				name="close"></ion-icon>
		</span>
		<div class="form-box login">
			<h2>Login</h2>
			<form method="post">
				<div class="input-box">
					<span class="icon"><ion-icon name="person"></ion-icon></span> <input
						type="text" id="loginId" /> <label>id</label>
				</div>
				<div class="input-box">
					<span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
					<input type="password" id="loginPassword" /> <label>password
					</label> <input type="hidden" name="watchedPage" id="watchedPage"
						value="${watchedPage}" />
				</div>
				<div class="forgot-idpassword">
					<a href="#" id="searchId">아이디찾기</a> <a href="#" id="searchPassword">비밀번호찾기</a>
				</div>
				<button type="button" class="btn" id="loginAction">Login</button>
				<div class="login-register">
					<p>
						요고어때 회원이 아니신가요? <a href="#" class="register">회원가입</a>
					</p>
				</div>
			</form>
		</div>

		<div class="form-box register">
			<h2>회원가입</h2>
			<form method="post" name="registerFrm" id="registerFrm">
				<div class="input-box">
					<span class="icon"><button class="btn1" id="checkId"
							name="checkId">check</button></span> <input type="text" class="id_input"
						name="id" id="id" check_result="fail" /> <label>Id</label>
					<ion-icon name="checkmark-circle" id="success"
						style="display:none;"></ion-icon>
				</div>
				<div class="input-box">
					<span class="icon"><button class="btn1" id="checkNick"
							name="checkNick">check</button></span> <input type="text"
						name="nickname" id="nickname"> <label>Nickname</label>
				</div>
				<div class="input-box">
					<span class="icon"><ion-icon name="mail"></ion-icon></span> <input
						type="email" name="email" id="email"> <label>Email</label>
				</div>
				<div class="input-box">
					<span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
					<input type="password" name="password" id="password"> <label>password</label>
				</div>
				<div class="input-box">
					<span class="icon"><ion-icon name="checkmark-circle"></ion-icon></span>
					<input type="password" name="passwordcheck" id="passwordcheck">
					<label>password check</label>
				</div>
				<div class="forgot-idpassword">
					<label><input type="checkbox"> 회원가입에 동의합니다</label>
				</div>
				<button type="button" class="btn" id="registerSubmit">회원가입</button>
				<!--  <button type="button" class="btn" name="submit" id="submit">회원가입</button> -->
				<div class="login-register1">
					<p>
						이미 계정이 있으신가요? <a href="#" class="login">Login</a>
					</p>
				</div>
			</form>
		</div>
	</div>


	<script type="module"
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
	<script>
let isIdChecked = false;
let isNicknameChecked= false;

//로그인하는 메서드 
$(function(){
	$("button#loginAction").click(function(){
		const idVal = $("#loginId").val();
		const passwordVal = $("#loginPassword").val();
		const watchedPage = $("#watchedPage").val();
		
		if(idVal=="" ||idVal.length==0 ){
			alert("id를 입력해주세요");
				   $("#loginId").focus();
			return;
			}
		
		
		if(passwordVal=="" ||passwordVal.length==0 ){
			alert("비밀번호를 입력해주세요");
				   $("#loginPassword").focus();
			return;
			}
		
		$.ajax({
			url:"/loginActionCon.do",
			type:"post",
			data:{id: idVal, password:passwordVal, afterLoginId: idVal,watchedPage:watchedPage},
			dataType:"json",
			success:function(data){
				
				if(data['result']=='false') {
					//로그인 실패
					alert("ID 또는 비밀번호의 정보가 다르거나 일치하는 회원 정보가 없습니다.");
					$("#loginId").val("");
					$("#loginPassword").val("");
				
			        } else{
			        	//로그인 성공
			        	window.location.href = '/loginSession.do?afterLoginId=' + idVal+ '&watchedPage=' +watchedPage;
                       
			        }
		
			},
			error:function(){
				alert("로그인에 실패하였습니다");
				$("#loginId").val("");
				$("#loginPassword").val("");
	                
			}
			
		});//ajax끝 			
	});
});

//아이디 중복 체크 
 $(function(){
	$("button#checkId").click(function(){
	
		event.preventDefault();
		const idVal = $("#id").val();
		let idValLen = idVal.length;
		
		if(idVal=="" ||idVal.length==0 ){
			alert("id를 입력해주세요");
				   $("#loginId").focus();
			return;
			}
		
		 //회원글자길이 체크(6글자이상 15글자이하)
		 if(idValLen<6||idValLen>16){
		  alert("회원 id는 6글자 이상 ~ 15글자 이하이어야 합니다");
		  $("#id").val("");
		  $("#id").focus();
		  return;
			   }
		
		
		
		$.ajax({
			url:"/checkId.do",
			type:"post",
			data:{id: idVal},
			dataType:"json",
			success:function(data){
				
				if(data['result']=='true') {
					alert("사용 가능한 ID입니다");
					isIdChecked = true;
					
			        } else{
			        	alert("중복된 ID입니다");
			        	$("#id").val("");
			        	isIdChecked = false;
			        
			        }
				
			},
			error:function(){
				alert("ID 중복 체크에 실패했습니다");
				$("#id").val("");
				isIdChecked = false;
			}
			
		});//ajax끝 			
		
		
	});

});


//닉네임 중복 체크 
$(function(){
	$("button#checkNick").click(function(){
		event.preventDefault();
		const nickVal = $("#nickname").val();

		if(nickVal=="" ||nickVal.length==0 ){
			alert("닉네임을 입력해주세요");
				   $("#nickname").focus();
			return;
			}
	
		$.ajax({
			url:"/checkNick.do",
			type:"post",
			data:{nickname: nickVal},
			dataType:"json",
			success:function(data){
				
				if(data['result']=='true') {
					alert("사용 가능한 닉네임입니다");
					isNicknameChecked= true;
			        } else{
			        	alert("중복된 닉네임입니다");
			        	$("#nickname").val("");
			        	isNicknameChecked= false;
			        }				
			},
			error:function(){
				alert("닉네임 중복 체크에 실패했습니다");
				$("#nickname").val("");
				isNicknameChecked= false;
			}
			
		});//ajax끝 			
	});

});

//회원가입
$(function(){
	$("button#registerSubmit").click(function(){
		const registerFrm = $("form#registerFrm");
		let memberVal = $("#id").val().trim();
		let memberValLen = memberVal.length;
		
		if(memberVal=="" ||memberVal.length==0 ){
		alert("회원id는 필수입력입니다.");
			   $("#id").focus();
		return;
		}
		
   //회원글자길이 체크(6글자이상 15글자이하)
	 if(memberValLen<6||memberValLen>16){
	  alert("회원 id는 6글자 이상 ~ 15글자 이하이어야 합니다");
	  $("#id").val("");
	  $("#id").focus();
	  return;
		   }
   
	//닉네임 필수입력 
	   if($("#nickname").val()=="" || $("#nickname").val().trim().length==0 ){
		   alert("닉네임을 입력해주세요♥");
		   $("#nickname").focus();
		   return;
	   }
	   
	 //이메일 필수입력 
	   if($("#email").val()=="" || $("#email").val().trim().length==0 ){
		   alert("이메일을 입력해주세요♥");
		   $("#nickname").focus();
		   return;
	   }
	   
	 //비밀번호 필수입력 
	   let pwdVal1 = $("#password").val(); 
	   let pwdVal2 = $("#passwordcheck").val();
	   if(pwdVal1=="" || pwdVal1.trim().length==0 ){
		   alert("비밀번호를 입력해주세요♥");
		   $("#password1").focus();
		   return;
	   }
	   
	  
	   //비밀번호확인 필수입력 
	   if(pwdVal2=="" || pwdVal2.trim().length==0 ){
		   alert("비밀번호 확인을 입력해주세요♥");
		   $("#password2").focus();
		   return;
	   }

		//비밀번호와 비밀번호 확인 일치여부 
		   if(pwdVal1 != pwdVal2){
			   alert("비밀번호와 비밀번호 확인이 일치하지 않습니다");
			   $("#password").val("");
			   $("#passwordcheck").val("");
			   $("#passwordcheck").focus();
			   return;
		   }
		   
		// 닉네임 중복 체크 여부 확인
    if (!isIdChecked) {
      alert("아이디 중복 체크를 해주세요");
      return ;
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
		   
       registerFrm.attr("method","post");
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
$(function(){
	$("a#searchId").click(function(){
	window.open("/intoSearchId.do", "intoSearchId", "width=600,height=200")
	});

});

 //회원 password 찾는 메서드
$(function(){
	$("a#searchPassword").click(function(){
	window.open("/intoSearchPassword.do", "intoSearchPassword", "width=600,height=250")
	});

});

const wrapper = document.querySelector('.wrapper');
const login = document.querySelector('.login-register1 a.login');
const register = document.querySelector('.login-register a.register');
//const btnPopup = document.querySelector('.btnLogin-popup');
//const iconClose = document.querySelector('.icon-close');

register.addEventListener('click',()=> { 
	wrapper.classList.add('active');
});
login.addEventListener('click',()=>{
	wrapper.classList.remove('active');
});

/*btnPopup.addEventListener('click',()=> { 
	wrapper.classList.add('active-popup');
});

iconClose.addEventListener('click',()=> { 
	wrapper.classList.remove('active-popup');
});*/

				
			

				
</script>
</body>
</html>