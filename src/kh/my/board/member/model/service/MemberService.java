package kh.my.board.member.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kh.my.board.common.JdbcTemplate;
import kh.my.board.member.model.dao.MemberDao;
import kh.my.board.member.model.vo.Member;

public class MemberService {

	public MemberService() {
		// TODO Auto-generated constructor stub
	}

	public int login(String member_id, String member_pwd) {
		Connection conn = JdbcTemplate.getConnection();
		int result = new MemberDao().login(conn, member_id, member_pwd);
		JdbcTemplate.close(conn);
		return result;
	}

	public ArrayList<Member> readMemberListAll() {
		ArrayList<Member> volist = null;
		Connection conn = JdbcTemplate.getConnection();
		volist = new MemberDao().readMemberListAll(conn);

		JdbcTemplate.close(conn);
		return volist;
	}

	public int insertMember(Member vo) {
		int result = -1;
		int result2 = -1;
		Connection conn = JdbcTemplate.getConnection();
		JdbcTemplate.setAutoCommit(conn, false);

		// 입력받은 member_ id가 기존회원 id와 중복인지 확인
		// select count(*) from member where member_id = ?
		// select * from member where member_id = ?
		// select member_id from member where member_id = ? --> 속도와 메모리 가장 best

		result = new MemberDao().checkDuplicatedMember(conn, vo);

		// 기존회원이 있으면 :2, 기존회원이 없으면 : 0, 오류발생하면 : -1
		if (result == 0) { // 기존회원이 없다면
			// 입력받은 값들로 회원가입
			// insert into member values (?,?,?,?,?,?,?,?, sysdate);
			result = new MemberDao().insertMember(conn, vo);

			// 회원가입시 event로 point 500
			// 가입한 회원 id에 point 수정

			result2 = new MemberDao().updatePointMember(conn, vo.getMember_id(), 500);

			
			if (result > 0 && result2 > 0)
				JdbcTemplate.commit(conn);
			else
				JdbcTemplate.rollback(conn);
		}
		JdbcTemplate.close(conn);
		return result; // 오류발생 : -1, 가입성공 : 1, 가입실패 : 0, 기존회원이 있다면 : 2 (가장큰수(255))
	}

}
