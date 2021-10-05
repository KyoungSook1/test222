package kh.my.board.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kh.my.board.common.JdbcTemplate;
import kh.my.board.member.model.vo.Member;

public class MemberDao {

	public MemberDao() {
		// TODO Auto-generated constructor stub
	}

	public int login(Connection conn, String member_id, String member_pwd) {
		int result = 0; // 로그인 실패 : 0
		String sql = "select member_id from member where member_id = ? and member_pwd = ? ";
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setString(2, member_pwd);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				result = 1; // 로그인 성공 : 1
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}

		return result;
	}

	public ArrayList<Member> readMemberListAll(Connection conn) {
		ArrayList<Member> volist = null;
		String sql = "select * from member";
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			ResultSet rs = pstmt.executeQuery();
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				volist = new ArrayList<Member>();
				do {
					Member vo = new Member();
					vo.setMember_id(rset.getString("member_id"));
					vo.setMember_pwd(rset.getString("member_pwd"));
					vo.setMember_name(rset.getString("member_name"));
					vo.setGender(rset.getString("gender").charAt(0)); 
					vo.setEmail(rset.getString("email"));
					vo.setPhone(rset.getString("phone"));
					vo.setAddress(rset.getString("address"));
					vo.setAge(rset.getInt("age")); // NUMBER
					vo.setEnroll_date(rset.getDate("enroll_date")); // date
					vo.setPoint(rset.getInt("point")); // NUMBER
					volist.add(vo);
				} while (rset.next());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return volist;
	}

	// 중복확인
	public int checkDuplicatedMember(Connection conn, Member vo) {
		int result = -1;
		String sql = "select member_id from member where member_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMember_id());
			rset = pstmt.executeQuery();

			if (rset.next()) {
				result = 2; // 기존회원이 있으면
			} else {
				System.out.println("기존회원없음");
				result = 0; // 기존회원이 없으면
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 여기 -1
		} finally {
		
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt); 
		}
		return result;
	}

	public int insertMember(Connection conn, Member vo) {
		int result = -1;
		String sqlInsert = "insert into member"
				+ " (MEMBER_ID,MEMBER_PWD,MEMBER_NAME,GENDER,EMAIL,PHONE,ADDRESS,AGE,ENROLL_DATE,POINT)" // "
																											// (MEMBER_ID,...)
																											// 부분에서
																											// 큰따옴표뒤 공간
																											// 꼭 띄어주기!
																											// -> 없애면
																											// 오류남
																											// (한문장으로
																											// 인식되기때문)
				+ " values (?,?,?,?,?,?,?,?, sysdate)"; // POINT 컬럼을 default값으로 사용하기위해 이렇게 작성함
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sqlInsert);
			pstmt.setString(1, vo.getMember_id());
			pstmt.setString(2, vo.getMember_pwd());
			pstmt.setString(3, vo.getMember_name());
			pstmt.setString(4, String.valueOf(vo.getGender())); // Gender=CHAR
			pstmt.setString(5, vo.getEmail());
			pstmt.setString(6, vo.getPhone());
			pstmt.setString(7, vo.getAddress());
			pstmt.setInt(8, vo.getAge());

			result = pstmt.executeUpdate(); // insert문은 executeUpdate
		} catch (Exception e) {
			e.printStackTrace();
			// 여기 -1
		} finally {
			
			JdbcTemplate.close(pstmt); 
		}
		return result;
	}

	// point 적립
	public int updatePointMember(Connection conn, String member_id, int point) {
		int result = -1;
		String sql = "update member set point = point + ? where member_id = ? ";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setString(2, member_id);
			result = pstmt.executeUpdate(); // insert문은 executeUpdate
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
	
			JdbcTemplate.close(pstmt); 
		}
		return result;
	}

}
