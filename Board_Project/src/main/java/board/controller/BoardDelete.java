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

@WebServlet("/delete.do")
public class BoardDelete extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    InterBoardDAO dao = new BoardDAO();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getMethod();
		String path = "";
		if("POST".equalsIgnoreCase(method)) {
			String seq = request.getParameter("seq");

			// 삭제하기전에 저보를 가져온다
			BoardDTO dto = dao.selectOne(seq);
			request.setAttribute("dto", dto);
			
			// 삭제
			dao.deleteboard(seq);
			path="/WEB-INF/board/boardDelete.jsp";

			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
		else {// "get"
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
