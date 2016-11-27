package ServerStorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 */

public class DBAdmin {
	private static Connection conn;

	public DBAdmin() {
		// DB 연결하기
		conn = DBConnectivity.getConnection("properties/fuse.properties");
	}
	
	public static Connection getConnection() {
		return conn;
	}

	public boolean idPwCheck(String id, String pw) {
		String AdminId = null;
		String AdminPw = null;
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from administrator");
			while (rset.next()) {
				AdminId = rset.getString(1);
				AdminPw = rset.getString(2);
			}
			if (id.equals(AdminId) && pw.equals(AdminPw)) {
				result = true;
			} else {
				result = false;
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
