<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세정보 수정</title>

<style type="text/css">
	ul {list-style-type: none;}
	li {line-height: 200%;}
</style>

<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.6.0.min.js"></script> 
<script type="text/javascript">


	$(document).ready(function(){
	   
	   // 1. 성명 입력해주기
	   $("input:text[name='name']").val("${requestScope.dto.name}");
	   
	   // 2. 제목 입력해주기
	   $("input:text[name='title']").val("${requestScope.dto.title}");
	   
	   // 3. 중요 게시물 체크해주기
	    $("input:radio[id='${requestScope.dto.markyn}']").prop("checked", true); 
	   
	   // 4. 내용 입력해주기 
	   $("textarea").val("${requestScope.dto.content}");

	   $("form[name='updateFrm']").submit(() => {	 		   
 	   
		   // === 유효성 검사 === //
		   // 1. 제목
		   const title_length = $("input:text[name='title']").val().trim().length;
		   if(title_length == 0) {
			   alert("제목을 입력하세요!!");
			   $("input:text[name='title']").val("").focus();
			   return false; // submit 을 하지 않고 종료한다.
		   }
		   // 2. 닉네임
		   const name_length = $("input:text[name='name']").val().trim().length;
		   if(name_length == 0) {
			   alert("성명을 입력하세요!!");
			   $("input:text[name='name']").val("").focus();
			   return false; // submit 을 하지 않고 종료한다.
		   }
		   // 3. 내용
		   const content_length = $("textarea").val().trim().length;
		   if(content_length == 0) {
			   alert("내용을 입력하세요!!");
			   $("textarea").val("").focus();
			   return false; // submit 을 하지 않고 종료한다.
		   }
	 		   
 	   });// end of $("form#updateFrm").submit(function(){})------------
	 	   
    });// end of $(document).ready()----------------------------

 </script>

 </head>
 <body>
	     
     <div style="width: 60%; margin: 50px auto;">
	 	<form name="updateFrm" action="<%= ctxPath%>/updateEnd.do" method="post">
	 		<fieldset>  
			<legend>게시물 수정</legend> 
			<ul>
				<li>
					<label for="">강조유무</label>
					<div>
						<label for="marky">표시</label>
						<input type="radio" name="markyn" id="marky" value="marky" />
						<label for="markn">표시안함</label>
						<input type="radio" name="markyn" id="markn" value="markn" />
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
					<textarea name="content"rows="" cols=""></textarea>
				</li>
				<li>
					<input type="submit" value="수정완료" />
					<input type="reset" value="취소" />
				</li>
			</ul>
		</fieldset>
			<input type="hidden" name="seq" />
	 	</form>
     </div>
     
 </body>
 </html>