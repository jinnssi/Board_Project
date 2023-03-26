package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardDTO;
import board.model.InterBoardDAO;

@WebServlet("/updateEnd.do")
public class BoardUpdateEnd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	InterBoardDAO dao = new BoardDAO();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			String seq = request.getParameter("seq");
			String name = request.getParameter("name");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String markyn = request.getParameter("markyn");
			
			BoardDTO dto = new BoardDTO();
			dto.setSeq(seq);
			dto.setName(name);
			dto.setTitle(title);
			dto.setContent(content);
			dto.setMarkyn(markyn);
			dao.updateboard(dto);
			response.sendRedirect(request.getContextPath()+"/view.do?seq="+dto.getSeq());
		}
		else {// "GET"방식으로 접근한 경우
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
