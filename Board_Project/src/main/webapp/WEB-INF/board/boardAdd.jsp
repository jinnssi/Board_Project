<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성 페이지</title>

<style type="text/css">
	ul {list-style-type: none;}
	li {line-height: 200%;}
</style>

<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.6.0.min.js"></script> 
<script type="text/javascript">

   $(document).ready(function(){
	   if("${requestScope.usedname != null}"){ //이전에 사용했던 닉네임 값 넣어두기 
		   $("input:text[name='name']").val("${requestScope.usedname}");
	   }
		   
	   $("input:text[name='name']").val();// 이전에 사용한 닉네임값 가져오기
	   
	   $("form[name='registerFrm']").submit(() => {	 		   
		   // === 유효성 검사 === //
		   // 1. 제목
		   const title_length = $("input:text[name='title']").val().trim().length;
		   if(title_length == 0) {
			   alert("제목을 입력하세요!!");
			   $("input:text[name='title']").val("").focus();
			   return false; // submit 을 하지 않고 종료
		   }
		   // 2. 닉네임
		   const name_length = $("input:text[name='name']").val().trim().length;
		   if(name_length == 0) {
			   alert("성명을 입력하세요!!");
			   $("input:text[name='name']").val("").focus();
			   return false; // submit 을 하지 않고 종료
		   }
		   // 3. 내용
		   const content_length = $("textarea").val().trim().length;
		   if(content_length == 0) {
			   alert("내용을 입력하세요!!");
			   $("textarea").val("").focus();
			   return false; // submit 을 하지 않고 종료
		   }
	   });// end of $("form#registerFrm").submit(function(){})------------
	   
   });// end of $(document).ready()----------------------------

</script>

</head>
<body>
	<div style="width: 60%; margin: 50px auto;">
		<form name="registerFrm" action="<%= ctxPath%>/add.do" method="post">
			<fieldset>  
				<legend>게시물 작성</legend> 
				<ul>
					<li>
						<label for="">강조유무</label>
						<div>
							<label for="marky">표시</label>
							<input type="radio" name="markyn" id="marky" value="marky" />
							<label for="markn">표시안함</label>
							<input type="radio" name="markyn" id="markn" value="markn" checked="checked"/>
						</div>
					</li>
					<li>
						<label for="title">제목</label>
						<input type="text" name="title" placeholder="제목입력"/> 
					</li>
					<li>
						<label for="name">닉네임</label>
						<input type="text" name="name" placeholder="닉네임입력"/> 
					</li>
					<li>
						<label for="content">내용</label>
						<textarea name="content" rows="" cols=""></textarea>
					</li>
					<li>
						<input type="submit" value="전송" />
						<input type="reset" value="취소" />
					</li>
				</ul>
			</fieldset>
		</form>
	</div>
</body>
</html>