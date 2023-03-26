package board.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.InterBoardDAO;


@WebServlet("/update.do")
public class BoardUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	InterBoardDAO dao = new BoardDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		String seq = request.getParameter("seq"); // form 의 name 이 seq

		if("POST".equalsIgnoreCase(method)) {
			String name = request.getParameter("name");
			HttpSession session =  request.getSession();// 메모리에 생성되어져 있는 session 을 불러오는 것
			session.setAttribute("name", name);// 닉네임값 넣어놓기 
			
			BoardDTO dto = dao.selectOne(seq);
			request.setAttribute("dto", dto); 
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/board/boardUpdate.jsp");
			dispatcher.forward(request, response);
		}
		else {// "GET" 방식 
			response.sendRedirect( request.getContextPath()+"/view.do?seq="+seq); // update 페이지를 보여주는게 아니라 해당사람의 상세정보를 보여줌
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
