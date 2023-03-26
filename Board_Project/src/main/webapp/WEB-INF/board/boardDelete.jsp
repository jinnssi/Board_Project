<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제 후 나오는 페이지</title>

<link rel="stylesheet" href="<%= ctxPath%>/bootstrap-4.6.0-dist/css/bootstrap.min.css" type="text/css"> 
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js"></script> 
<script type="text/javascript">
	
	window.onload = function() {
		alert("${requestScope.dto.name} 님을 삭제했습니다. ");
		location.href='list.do';
	}
	
</script>
</head>
<body>
</body>
</html>