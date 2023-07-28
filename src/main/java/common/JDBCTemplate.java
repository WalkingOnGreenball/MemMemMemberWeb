package common;

import java.sql.*;

public class JDBCTemplate {
	
	private static JDBCTemplate instance;	// 싱글톤 패턴, private이기 때문에 static으로 리턴
	private static Connection conn;			// 연결 싱글톤
	
	private JDBCTemplate() {}
	
	public static JDBCTemplate getInstance() {	// 해당 코드만 JDBCTemplate을 생성하기위해 필드를 private, static으로 설정
		if(instance == null) {
			instance = new JDBCTemplate();
		}
		return instance;
	}
	
	public Connection createConnection() {
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "MEMBERWEB";
		String password = "MEMBERWEB";
		
		try {
			if(conn == null || conn.isClosed()) {	// null일 때, 닫혀있을 때 다시 만들어줘
				Class.forName(driverName);
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(false);	// 오토커밋 해제
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
		// 연결 해제
	public void close(Connection conn) {
		if(conn != null) {
			try {
				if(!conn.isClosed()) {					
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		// 커밋
	public void commit(Connection conn) {
		if(conn != null) {
			try {
				if(!conn.isClosed()) {
					conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		// 롤백
	public void rollback(Connection conn) {
		if(conn != null) {
			try {
				if(!conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
