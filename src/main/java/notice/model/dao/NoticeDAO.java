package notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import notice.model.vo.Notice;

public class NoticeDAO {

	private Notice rsetToNotice(ResultSet rset) throws SQLException {
		Notice notice = new Notice();
		notice.setNoticeNo(rset.getInt("NOTICE_NO"));
		notice.setNoticeSubject(rset.getString("NOTICE_SUBJECT"));
		notice.setNoticeContent(rset.getString("NOTICE_CONTENT"));
		notice.setNoticeWriter(rset.getString("NOTICE_WRITER"));
		notice.setNoticeDate(rset.getTimestamp("NOTICE_Date"));
		notice.setUpdateDate(rset.getTimestamp("UPDATE_DATE"));
		notice.setViewCount(rset.getInt("VIEW_COUNT"));
		return notice;
	}

	public int insertNotice(Connection conn, Notice notice) {
		String query = "INSERT INTO NOTICE_TBL VALUES(SEQ_NOTICENO.NEXTVAL, ?, ?, 'admin', DEFAULT, DEFAULT, DEFAULT)";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getNoticeSubject());
			pstmt.setString(2, notice.getNoticeContent());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int deleteNoticeByNo(Connection conn, int noticeNo) {
		String query = "DELETE FROM NOTICE_TBL WHERE NOTICE_NO = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<Notice> selectNoticeList(Connection conn, int currentPage) {	// 한 페이지에 원하는만큼의 게시물을 노출하는 로직
//		String query = "SELECT * FROM NOTICE_TBL ORDER BY NOTICE_DATE DESC";
		String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_NO DESC) ROW_NUM, NOTICE_TBL.* FROM NOTICE_TBL) WHERE ROW_NUM BETWEEN ? AND ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Notice> nList = new ArrayList<Notice>();
		int recordCountPerPage = 10;	// 한 페이지에 보여줄 게시물의 갯수
		// current		start
		//    1			  1
		//	  2			 11
		//	  3			 21
		//	  4			 31
		int start = currentPage * recordCountPerPage - (recordCountPerPage -1);
		int end = currentPage * recordCountPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice notice = rsetToNotice(rset);
				nList.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nList;
	}
	
	public String generatePageNavi(int currentPage) {	// 전체 게시물, 범위에 따른 html 태그 만들기
		// 전체 개시물의 갯수 : 37
		// 1페이지에 보여줄 게시물의 수 : 10
		// 범위의 갯수(네비게이터의 갯수) : 4
		
		// 전체 게시물의 갯수 : 55
		// 1페이지에 보여줄 게시물의 수 : 10
		// 범위의 갯수 : 6
		
		int totalCount = 207;			// 전체 게시물의 갯수
		int recordCountPerPage = 10;	// 한 페이지에 보여줄 게시물의 갯수
		int naviTotalCount = 0;			// 범위의 총 갯수
		if(totalCount % recordCountPerPage > 0) {
			naviTotalCount = (totalCount / recordCountPerPage) + 1;
		} else {
			naviTotalCount = totalCount / recordCountPerPage;
		}
		int naviCountPerPage = 5;		// 한 페이지에 보여줄 범위의 갯수
		
		//  currentPage		startNavi		endNavi
		//   1,2,3,4,5			1				5
		// 	6,7,8,9,10			6				10
		// 11,12,13,14,15		11				15
		// 16,17,18,19,20		16				20
		
		int startNavi = ((currentPage - 1)/naviCountPerPage) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage -1;
		
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;	// 마지막 범위의 값이 총 범위의 갯수보다 커지지 않게 설정
		}
		
		boolean needPrev = true;		// 이전 값
		boolean needNext = true;		// 다음 값
		
		if(startNavi == 1) {			// startNavi 가 1이면 needPrev가 뜨지않게
			needPrev = false;
		}
		if(endNavi == naviTotalCount) {	// 마지막 범위의 값이 총 범위의 갯수이면 needNext가 뜨지않게
			needNext = false;
		}
		
//		String result = "";		// 너무 data값이 넓어서 StringBuilder를 사용했음
		StringBuilder result = new StringBuilder();
		
		if(needPrev) {
			result.append("<a href='/notice/list.do?currentPage="+(startNavi-1)+"'>[이전]</a> ");
		}
		for(int i = startNavi; i <= endNavi; i++) {
			result.append("<a href='/notice/list.do?currentPage=" + i + "'>" + i + "</a>&nbsp;&nbsp;");	// 범위 만드는 html 태그  // &nbsp; 는 띄어쓰기
		}
		if(needNext) {
			result.append("<a href='/notice/list.do?currentPage="+(endNavi+1)+"'>[다음]</a>");
		}
		return result.toString();
	}

	public Notice selectOneByNo(Connection conn, int noticeNo) {
		String query = "SELECT * FROM NOTICE_TBL WHERE NOTICE_NO = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice notice = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				notice = rsetToNotice(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return notice;
	}

	public int updateNotice(Connection conn, Notice notice) {
		String query = "UPDATE NOTICE_TBL SET NOTICE_SUBJECT = ?, NOTICE_CONTENT = ? WHERE NOTICE_NO = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getNoticeSubject());
			pstmt.setString(2, notice.getNoticeContent());
			pstmt.setInt(3, notice.getNoticeNo());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
