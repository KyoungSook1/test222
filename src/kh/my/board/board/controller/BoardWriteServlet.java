package kh.my.board.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.board.model.service.BoardService;
import kh.my.board.board.model.vo.Board;

/**
 * Servlet implementation class BoardReWriteServlet
 */
@WebServlet("/boardwrite2.kh")
public class BoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		
		Board oVo = null;
		String viewBno = request.getParameter("bno"); // 현재 보고있는 페이지
		if (viewBno == null) { // 기존 읽고 있던 글이 없다면 원본 새글쓰기로 인식
			oVo = new Board();
		} else {
			int bno = Integer.parseInt(viewBno); 
			// 알아와야함. bref , bre_level, bre_step
			 oVo = new BoardService().getBoard(bno);
		}
		
		// 화면입력 전달되어 옴. request - parameter (= 변수명) : t, c
		// 크롬창에 : http://localhost:8090/myBoard/boardwrite.kh?c=내용부분입력된값&제목부분입력값
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = (String) request.getSession().getAttribute("memberLoginInfo"); // getAttribute의 자료형이 object이기때문에
																						// 강제형변환 해줘야함
		if (writer == null) {
			writer = "user01"; // 임시 USER 설정
		}
//		out.println("입력된 title : " + title);
//		out.println("<br>입력된 content : " + content);

		Board vo = new Board(oVo.getBno(), title, content, writer, oVo.getBref(), oVo.getBreLevel(), oVo.getBreStep()) ; // vo.setTitle(title); 이거쓰기 귀찮아서 이문장처럼 적음
		int result = new BoardService().insertBoard(vo);
//		if (result == 0) {
//			out.println("<br> 게시글이 입력되지 않았습니다. <br> 다시 작성해주세요.");
//		} else {
//			out.println("<br> 게시글이 입력되었습니다.");
//		}
		response.sendRedirect("boardlist2");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
