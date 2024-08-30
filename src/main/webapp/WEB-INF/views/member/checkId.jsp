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
</head>
<body>
	<c:if test="${msg!=null }">
		<script type="text/javascript">
			alert("${msg }");
		</script>
	</c:if>
	<button onclick="history.go(-1);">close</button>

</body>
</html>