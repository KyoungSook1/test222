package kh.my.board.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.my.board.member.model.service.MemberService;
import kh.my.board.member.model.vo.Member;

/**
 * Servlet implementation class EnrollMemberServlet
 */
@WebServlet("/enroll") // 회원가입
public class EnrollMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EnrollMemberServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 회원가입
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();

		String member_id = "aaa";
		String member_pwd = "bbb";
		String member_name = "aaaa";
		char gender = 'M'; // CHAR은 작은따옴표!!
		String email = "aaa@a.a";
		String phone = "010-1111-1111";
		String address = "aaa aaa";
		int age = 23;
		// Date enroll_date; // DATE

		// 화면 데이터를 vo에 싣기
		Member vo = new Member(member_id, member_pwd, member_name, gender, email, phone, address, age, null);// enroll_date=null

		// vo를 가지고 회원가입하러 Dao로 출발
		int result = new MemberService().insertMember(vo);

		// 오류발생 : -1, 가입성공 : 1, 가입실패 : 0, 기존회원이 있다면 : 2 (가장큰수(255))
		if (result == 1) {
			out.println(member_id + "님 가입되었습니다. 환영합니다.");
		} else if (result == 2) {
			out.println("기존회원 id가 존재합니다.");
		} else { // 오류발생 : -1, 가입실패 : 0, 그외 등등
			out.println("예기치 못한 오류 발생. 다시 시도해주세요.");
		}

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
