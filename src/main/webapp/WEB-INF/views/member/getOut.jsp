<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
      <html>
      <head>
      <link rel="icon" href="/Resources/Image/Common/menu_logo.png">
<title>요고, 어때?</title>

     	<style>
   			body {
      text-align: center;
    		}
  		</style>
  		
       <script>
       <%@include file="/WEB-INF/lib/jquery-3.7.1.min.js" %>
       
          $(document).ready(function() {
            $("#confirm").click(function() {
              var confirmText = $("#getoutInput").val().trim();
              if (confirmText === "회원탈퇴") {
                window.opener.location.href = "/getoutUser.do";
                window.close(); 
              } else {
                alert("입력하신 내용이 일치하지 않습니다. 다시 확인해주세요.");
              }
            });

            $("#cancel").click(function() {
              window.close(); 
            });
          });
        </script>
      </head>
      <body>
        <p>정말 회원탈퇴를 하시겠습니까?</p>
        <input type="text" id="getoutInput" placeholder="'회원탈퇴'를 입력해주세요"><br/>
        <br/>
        <button id="confirm">확인</button>
        <button id="cancel">취소</button>
        
      </body>
      </html>