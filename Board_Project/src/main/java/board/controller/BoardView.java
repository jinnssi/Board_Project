package board.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.InterBoardDAO;

@WebServlet("/view.do")
public class BoardView extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	InterBoardDAO dao = new BoardDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String seq = request.getParameter("seq"); //  jsp에서 form 태그가 아니라 직접달아준거 "boardDetail.do?seq="+seq
		
		String path = "";
		BoardDTO dto = dao.selectOne(seq);
		if(dto !=null) { // 정상이라면 영역에 저장 
			request.setAttribute("dto", dto);
			path="/WEB-INF/board/boardView.jsp";
		}
		else { // null값인경우 (없는 번호)
			response.sendRedirect(request.getContextPath()+"/list.do"); // URL 페이지의 이동  
			return; 
		}
		RequestDispatcher dispatcher =request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
