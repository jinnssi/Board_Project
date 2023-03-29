package board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.common.MyUtil;
import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.InterBoardDAO;

@WebServlet("/list.do")
public class BoardList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	InterBoardDAO dao = new BoardDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");

		if(searchType == null || (!"title".equals(searchType) && !"name".equals(searchType)) ) {
			searchType="";
		}
		if(searchWord == null || "".equals(searchWord) || searchWord.trim().isEmpty() ) {
			searchWord = "";
		}
		if(searchWord != null) {
			searchWord = searchWord.trim();
		}
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord.trim());
		
		// 먼저 총게시물 건수(totalCount) 
		int totalCount = 0;        // 총 게시물 건수
        int sizePerPage = 5;       // 한 페이지당 보여줄 게시물 건수 
        int currentShowPageNo = 0; // 현재 보여주는 페이지번호로서, 초기치로는 1페이지로 설정함.
        int totalPage = 0;         // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
      
        int startRno = 0; // 시작 행번호
        int endRno = 0;   // 끝 행번호
        
        // 총게시물 건수(totalCount)
        totalCount = dao.getTotalCount(paraMap);
//        System.out.println("~~~ totalCount:"+totalCount);
        
        // 만약에 총게시물건수가 22개라면
        // 총 페이지수는 3개가 되어야한다.
        totalPage = (int)Math.ceil( (double)totalCount/sizePerPage);
//        System.out.println("~~~ totalPage:"+totalPage);
        // (double)22/10 ==> 2.2 ==> Math.ceil(2.2) ==> 3.0 ==> (int)3.0 ==> 3
        
        if(str_currentShowPageNo == null) { // 게시판에 보여지는 초기화면 
        	currentShowPageNo = 1;
        }
        else {
        	try {
        		currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
        		if( currentShowPageNo < 1 || currentShowPageNo > totalPage) {
        			currentShowPageNo = 1;
        		}
        	}catch(NumberFormatException e) {
        		currentShowPageNo = 1;
        	}
        }
     //  *** 가져올 게시글의 범위 *** 
        startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
        endRno = startRno + sizePerPage - 1;
        
        paraMap.put("startRno", String.valueOf(startRno));
        paraMap.put("endRno", String.valueOf(endRno));
        paraMap.put("sizePerPage", String.valueOf(sizePerPage));
        
        List<BoardDTO> boardList = null;
        if(totalCount>0) {
        	boardList = dao.selectAll(paraMap); // 페이징 처리한 글목록 가져오기(검색 모두 다 포함)
        }
        
        
        // 아래의 것은 검색대상 컬럼과 검색어를 뷰단 페이지에서 유지시키기 위한 것임 
 		if( !"".equals(searchWord) && !"".equals(searchType)  ) {
 			request.setAttribute("paraMap",paraMap);
 		}
 		
 		// == 페이지바 만들기 == //
 		int blockSize = 10; // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수
 		int loop = 1; // 블럭을 이루는 페이지번호의 개수

 		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;

 		String pageBar = "<ul style='list-style:none; '>";
 		String url ="list.do";
 		
 		// [맨처음][이전] //
 		if(pageNo != 1) {
 			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo=1'>[맨처음]</a></li>";
 			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";
 		}
 		
 		while( !(loop > blockSize || pageNo > totalPage) ) {
 			if(pageNo==currentShowPageNo) {
 				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color: red; padding: 2px 4px; '>"+pageNo+"</li>";
 			}
 			else {
 				pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
 			}
 			loop ++;
 			pageNo ++;
 		}// end of while()
 		
 		// [다음][마지막] //
 		if(pageNo <= totalPage) {
 			pageBar += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
 			pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";
 		}
 		
 		pageBar += "</ul>";
 		request.setAttribute("pageBar", pageBar);

 		String gobackURL = MyUtil.getCurrentURL(request);
 		request.setAttribute("gobackURL",gobackURL.replaceAll("&", " ")); // 웹 url에서 공백은 안쓰이기때문에 안쓰이는 공백으로 바꿔준다 
		request.setAttribute("boardList", boardList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/board/boardList.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
