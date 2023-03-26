<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>

<script type="text/javascript">
	// function declaration
	function goSelect() {
		location.href="<%= ctxPath%>/list.do";
	}// end of function goSelect() {}
</script>
</head>
<body>
	<h2>게시글 입력 성공</h2>
	<br>
	<button type="button" onclick="goSelect()">입력결과 조회하기 </button>
</body>
</html>