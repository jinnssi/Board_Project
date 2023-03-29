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

import org.json.JSONArray;
import org.json.JSONObject;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.InterBoardDAO;

@WebServlet("/wordSearch.do")
public class wordSearchJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	InterBoardDAO dao = new BoardDAO();
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<String> wordList = dao.wordSearchShow(paraMap);
		JSONArray jsonarr = new JSONArray(); // []
		
		if(wordList  != null) {
			for(String word: wordList) {
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("word", word);
				jsonarr.put(jsonobj);
			}
		}else {  // 조회된 것이 없다라면 
			String json = jsonarr.toString(); 	// 문자열로 변환  
			request.setAttribute("json", json);
		}
		
		request.setAttribute("json", jsonarr.toString());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/common/jsonview.jsp");  
		dispatcher.forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
