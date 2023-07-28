package member.model.service;

import java.sql.*;

import common.JDBCTemplate;
import member.model.dao.MemberDAO;
import member.model.vo.Member;

public class MemberService {
	
	JDBCTemplate jdbcTemplate;
	MemberDAO mDao;
	
	public MemberService() {
		jdbcTemplate = JDBCTemplate.getInstance();	// 싱글톤 패턴
		mDao = new MemberDAO();
	}

	public int insertMember(Member member) {
//		연결생성
		// JDBCTemplate jdbcTemplate = new JDBCTemplate();
		Connection conn = jdbcTemplate.createConnection();
		
//		DAO 호출 (연결도 넘겨줘야함)
		// MemberDAO mDao = new MemberDAO();
		int result = mDao.insertMember(conn, member);
		
//		커밋, 롤백
		if(result > 0) {
			jdbcTemplate.commit(conn);		// 성공 - 커밋
		} else {
			jdbcTemplate.rollback(conn);	// 실패 - 롤백
		}
		jdbcTemplate.close(conn);
		return result;
	}

	public int updateMember(Member member) {
		Connection conn = jdbcTemplate.createConnection();
		int result = mDao.updateMember(conn, member);
		if(result > 0) {
			jdbcTemplate.commit(conn);
		} else {
			jdbcTemplate.rollback(conn);
		}
		return result;
	}

	public int deleteMember(String memberId) {
		Connection conn = jdbcTemplate.createConnection();
		int result = mDao.deleteMember(conn, memberId);
		if(result > 0) {
			jdbcTemplate.commit(conn);
		} else {
			jdbcTemplate.rollback(conn);
		}
		return result;
	}

	public int selectCheckLogin(Member member) {
		Connection conn = jdbcTemplate.createConnection();
		int result = mDao.selectCheckLogin(conn, member);
		jdbcTemplate.close(conn);
		return result;
	}

	public Member selectOneById(String memberId) {
		Connection conn = jdbcTemplate.createConnection();
		Member member = mDao.selectOneById(conn, memberId);
		jdbcTemplate.close(conn);
		return member;
	}


}
