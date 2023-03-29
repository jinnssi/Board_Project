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
<title>게시판 리스트</title>
<link rel="stylesheet" href="<%= ctxPath%>/bootstrap-4.6.0-dist/css/bootstrap.min.css" type="text/css"> 
<style type="text/css">

	div#div1 {
	 /* border: solid 1px gray; */
		width: 80%;
		margin: 20px auto;
	}
	
	div#div1 > table {
		width: 100%;
		border-collapse: collapse;	
	}
	
	div#div1 > table th , div#div1 > table td {
		border: solid 1px gray;
	}
	
	div#div1 > table > tbody > tr:hover {
		background-color: blue;
		color: white;
		cursor: pointer;
	}
	tbody > tr > td:first-child > span {
		display:none;
	}
	
</style>

<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js"></script> 

<script type="text/javascript">

	$(document).ready(function(){
		
		$("tbody > tr").click((e)=>{ // 게시물 클릭시 
			const $target = $(e.target);  
			const seq = $target.parent().find("span").text();
			goView(seq);
		});

		$("input#searchWord").keyup(function(e){
			if(e.keyCode == 13) { // 검색어에 엔터를 했을 경우
				goSearch();
			}
		});

		// 검색시 검색조건 및 검색어 값 유지시키기
		if( ${not empty requestScope.paradto} ) {
			$("select#searchType").val("${requestScope.paraMap.searchType}");
			$("input#searchWord").val("${requestScope.paraMap.searchWord}");
		}
		
		<%-- 검색어 입력시 자동글 --%>
		$("div#displayList").hide();
		$("input#searchWord").keyup(function(e){ 
			const wordLength = $(e.target).val().trim().length; // 검색어에서 공백을 제거한 길이를 알아온다.
			if(wordLength == 0) { // 검색어가 공백만 되어진 경우 , 백스페이스키를 눌러서 검색어를 모두 지운 경우 내용 안나오도 
				$("div#displayList").hide();
			}
			else {
				$.ajax({
					url:"<%= ctxPath%>/wordSearch.do",
					type:"GET",
					data:{"searchType":$("select#searchType").val()
						 ,"searchWord":$("input#searchWord").val()},
					dataType:"JSON",
					success:function(json){
						console.log(json);
		                  if(json.length > 0) { // 검색된 데이터가 있는 경우 
		                     let html = "";
		                     $.each(json, function(index, item) {
		                        const word = item.word;
		                        const idx = word.toLowerCase().indexOf($("input#searchWord").val().toLowerCase());
		                        const len = $("input#searchWord").val().length;
		                        const result = word.substring(0, idx) + "<span style='color:blue;'>"+word.substring(idx, idx+len)+"</span>" + word.substring(idx+len); 
		                        html += "<span style='cursor:pointer;' class='result'>"+result+"</span><br>";
		                     });
		                     
		                     const input_width = $("input#searchWord").css("width"); // 검색어 input 태그 width 알아오기 
		                     
		                     $("div#displayList").css({"width":input_width}); // 검색결과 div 의 width 크기를 검색어 입력 input 태그의 width 와 일치시킴.
		                     $("div#displayList").html(html);
		                     $("div#displayList").show();
		                  }
		               },
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				    }
				});
			}
		}); // end of $("input#searchWord").keyup()---------------
		
		<%-- 검색어 입력시 자동글 완성  --%>
		$(document).on("click","span.result",function(e){ 
			const word = $(this).text();
			$("input#searchWord").val(word);
			$("div#displayList").hide();
			goSearch();
		});
		
	});
	// 게시물 상세페이지 함수
	function goView(seq) {
 		const gobackURL = "${requestScope.gobackURL}"; // 현재 페이지 주소를 뷰단으로 넘겨준다
 		const searchType = $("select#searchType").val();
 		const searchWord = $("input#searchWord").val();
		location.href="<%= ctxPath%>/view.do?seq="+seq+"&gobackURL="+gobackURL+"&searchType="+searchType+"&searchWord="+searchWord;
	}// end of function goView(seq)-------------------
	// 검색 함수
	function goSearch() {
		const frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= ctxPath%>/list.do";
		frm.submit();
	}// end of function goSearch()--------------------

</script>

</head>
<body>
   
   <div id="div1" >
	   <div class="container-fluid">
	   	   <h3>게시판 목록</h3>
	   	   <c:if test="${not empty requestScope.boardList}">
		   	   <table class="table table-hover">
		   	       <thead>
			          <tr>
			             <th width="10%" >번호</th>
			             <th width="15%" >닉네임</th>
			             <th width="25%">제목</th>
			             <th width="50%">내용</th>
			          </tr>
			       </thead>
			       
			       <tbody> 
			       <c:forEach var="dto" items="${requestScope.boardList}" varStatus="status">
				       <c:if test="${ fn:startsWith(dto.seq, 'm')==true }"><!-- 중요 표시한 게시글 -->
				       		<tr style="background-color: grey">
				       			<td>${status.count}</td>
				       			<td><span hidden="">${dto.seq}</span>${dto.name}</td>
				       			<td>${dto.title}</td>
				       			<td>${dto.content}</td>
				       		</tr>
				       </c:if>
				       <c:if test="${ fn:startsWith(dto.seq, 'm')==false }"><!-- 중요 표시안한 게시글 --> 
				       		<tr>
				       			<td>${status.count}</td>
				       			<td><span hidden="">${dto.seq}</span>${dto.name}</td>
				       			<td>${dto.title}</td>
				       			<td>${dto.content}</td>
				       		</tr>
				       </c:if>
				    </c:forEach>
				       
			       </tbody>
		   	   </table>
	   	   </c:if>
	   	   
	   	   <c:if test="${empty requestScope.boardList}">  <!-- null 이거나  size가 0임   -->
	   	    	<span style="color: red;">데이터가 존재하지 않습니다.</span>
	   	   </c:if>
	   </div>
	   
	   <%-- 페이지바 --%>
    <div id="pageBar" align="center" style="width:70%; margin:20px auto;">${requestScope.pageBar}</div>
    
    <%-- 글검색 폼 : 글제목, 글쓴이로 검색 --%>
    <form name="searchFrm" style="margin-top: 20px;">
        <select name="searchType" id="searchType" style="height: 26px;">
           <option value="title">글제목</option>
           <option value="name">닉네임</option>
        </select>
        <input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" />
        <input type="text" style="display: none;" />  
        <button type="button" class="btn btn-secondary btn-sm" onclick="goSearch()">검색</button>
    </form>
	   <%-- 검색어 입력시 자동글 === --%>
    <div id="displayList" style="border:solid 1px gray; border-top:0px; height:100px; margin-left:75px; margin-top:-1px; overflow:auto;">
	</div>
	   
	   
	   
	   <p class="text-center mt-5" >												
	   	<button type="button" class="btn btn-info" onclick="javascript:location.href='add.do'">게시글 입력페이지로 가기</button>
	   </p>
   </div>
	   
</body>
</html>