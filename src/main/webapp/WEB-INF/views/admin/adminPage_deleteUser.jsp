<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 삭제 확인</title>
</head>
<body>
<script>
    function deleteUser(memberId) {
        var confirmDelete = confirm("정말로 이 회원을 삭제하시겠습니까?");
        if (confirmDelete) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "AdminDeleteUser?memberId=" + memberId, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    // 삭제 요청이 성공적으로 처리되었을 때 추가 작업을 수행
                    alert(xhr.responseText);
                    // 페이지 새로고침 없이 삭제된 회원 목록을 업데이트하도록 할 수 있습니다.
                    // 여기서는 페이지 전체를 새로고침하는 예시를 드리겠습니다.
                    window.location.reload();
                }
            };
            xhr.send();
        }
    }
</script>
</body>
</html>
