package board.controller;

import java.io.IOException;
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

@WebServlet("/add.do")
public class BoardAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	InterBoardDAO dao = new BoardDAO();
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getMethod(); // GET 또는 POST
		HttpSession session =  request.getSession();// 메모리에 생성되어져 있는 session 을 불러오는 것
		
		if("GET".equalsIgnoreCase(method)) { // get 입력창 
			String usedname = (String)session.getAttribute("usedname");
			request.setAttribute("usedname", usedname);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/board/boardAdd.jsp");  
			dispatcher.forward(request, response);
		}
		else { // post
			String name = request.getParameter("name");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String markyn = request.getParameter("markyn");
			
			BoardDTO dto = new BoardDTO();
			dto.setName(name);
			dto.setTitle(title);
			dto.setContent(content);
			dto.setMarkyn(markyn);
			dao.boardAdd(dto);
			session.setAttribute("usedname", dto.getName());// 닉네임값 넣어놓기 
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/board/boardAdd_success.jsp");  
			dispatcher.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
