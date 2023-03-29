<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String ctxPath = request.getContextPath();
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세정보</title>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> 
<%-- Bootstrap CSS --%>
<link rel="stylesheet" href="<%= ctxPath%>/bootstrap-4.6.0-dist/css/bootstrap.min.css" type="text/css"> 

<style type="text/css">
	
	span#colorbox{
		display: inline-block;
		width: 30px;
		height: 30px;
		margin-left: 20px;
		
		position: relative;
		top: 8px;
	}
	
</style>
  
  
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript">
	
	$(document).ready(function() {
	})

	// Function Declaration 
	function updateboard(seq) {
		const frm = document.updateFrm;
		frm.seq.value = seq;
		frm.action = "update.do";
		frm.method = "post";
		frm.submit();
	} // end of function updateboard(seq) {}---------
	
	function deleteboard(seq) {
		const bool = confirm("정말로 삭제하시겠습니까?");
		if(bool){
			const frm = document.updateFrm;
			frm.seq.value = seq;
			frm.action = "delete.do";
			frm.method = "post";
			frm.submit();
		}
	}// end of function deleteboard(seq) {}--------

</script> 
</head>
<body class="py-3">
	
	<div class="container">
		<div class="card">
		
			<div class="card-header"> 
				<p class="h2 text-center">
					<span class="text-primary">${requestScope.dto.name}</span>&nbsp;<small>님의 게시물</small> 
				</p>
			</div>
			<div class="card-body"> 
				<div class="row mx-4 my-3 border border-top-0 border-left-0 border-right-0">
					<div class="col-md-3 py-2">중요 게시물</div>
					<div class="col-md-9 py-2">
						<c:choose>
							<c:when test="${fn:startsWith(requestScope.dto.seq, 'm')==true}"><span class="h5">O</span></c:when>
							<c:when test="${fn:startsWith(requestScope.dto.seq, 'm')!=true}"><span class="h5">X</span></c:when>
						</c:choose>
					</div>
				</div>
				<div class="row mx-4 my-3 border border-top-0 border-left-0 border-right-0">
					<div class="col-md-3 py-2">제목</div>
					<div class="col-md-9 py-2"><span class="h5">${requestScope.dto.title}</span></div>
				</div>
				<div class="row mx-4 my-3 border border-top-0 border-left-0 border-right-0">
					<div class="col-md-3 py-2">성명</div>
					<div class="col-md-9 py-2"><span class="h5">${requestScope.dto.name}</span></div>
				</div>
				
				<div class="row mx-4 my-3 border border-top-0 border-left-0 border-right-0">
					<div class="col-md-3 py-2">내용</div>
					<div class="col-md-9 py-2"><span class="h5">${requestScope.dto.content}</span></div>
				</div>
			</div>
				<div class="card-footer">
					<div class="row" style="position:relative; top:10px;">
						<div class="col-md-3">
							<p class="text-center"><button type="button" onclick="javascript:location.href='list.do'" class="btn btn-success">목록보기</button></p>
						</div>
						<div class="col-md-3">
							<p class="text-center"><button type="button" onclick="deleteboard('${requestScope.dto.seq}')" class="btn btn-danger">삭제하기</button></p>
						</div>
						<div class="col-md-3">
							<p class="text-center"><button type="button" onclick="updateboard('${requestScope.dto.seq}')" class="btn btn-primary">수정하기</button></p>
						</div>
					</div>
				</div>
			</div>
	</div>
	<form name="updateFrm" >
		<input type="hidden" name="seq" />
	</form>
</body>
</html>