package kh.my.board.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;

/**
 * Servlet implementation class BoardList
 */
@WebServlet("/boardlist2")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();

		final int PAGE_SIZE = 20; // 한 페이지 당 글 개수
		final int PAGE_BLOCK = 3; // 한 화면에 나타날 페이지 링크수
		int bCount = 0; // 총 글수
		int pageCount = 0; // 총 페이지수
		int startPage = 1; // 화면에 나타날 시작페이지
		int endPage = 1; // 화면에 나타날 마지막페이지
		int currentPage = 1; // 눌려진 페이지
		int startRnum = 1; // 화면에 나타날 글 번호
		int endRnum = 1; // 화면에 나타날 글 번호

		String pageNum = request.getParameter("pagenum"); 
		if (pageNum != null) { // 눌려진 페이지가 있음.
			currentPage = Integer.parseInt(pageNum); 
		}

		// 총 글수
		bCount = new BoardService().getBoardCount();

		// 총 페이지수 = (총 글 개수 / 페이지당 글수) + (총 글 개수에서 페이지당 글수로 나눈 나머지가 0이 아니라면 페이지개수를 1증가)
		// : 78글 / 한 페이지당 글 개수 5
		pageCount = (bCount / PAGE_SIZE) + (bCount % PAGE_SIZE == 0 ? 0 : 1);

		// rownum 조건 계산
		startRnum = (currentPage - 1) * PAGE_SIZE + 1; // 1-6-11-16-21 페이지
		endRnum = startRnum + PAGE_SIZE - 1;
		if (endRnum > bCount)
			endRnum = bCount;

		if(currentPage % PAGE_BLOCK == 0) {
			startPage = (currentPage / PAGE_BLOCK -1)  * PAGE_BLOCK + 1;
		} else {
			startPage = (currentPage / PAGE_BLOCK)  * PAGE_BLOCK + 1;
		}
		endPage = startPage + PAGE_BLOCK -1; 
		if(endPage > pageCount) endPage = pageCount;
		
		ArrayList<Board> volist = new BoardService().selectBoardList(startRnum,endRnum);
		
		
		request.setAttribute("boardvolist", volist);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageCount", pageCount);
		request.getRequestDispatcher("/boardlist2.jsp").forward(request, response); 
	
		
		
		

		
		
////		if(volist.size() > 0 ) 
//		{
//			for(Board vo : volist) {
//				out.println("<p>"+vo.toString()+"</p>");
//			}
//		}
//		if(startPage > 1)
//			out.println("이전  ");
//		for(int i = startPage; i <= endPage; i++) {
//			out.print(i);
//			if(i != endPage) {
//				out.println(", ");
//			}
//		}
//		if(endPage < pageCount)
//			out.println("  다음");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}