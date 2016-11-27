package ServerStorage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 디비 연결
 */

public class DBConnectivity {
	static Connection getConnection(String propFile) {
		Properties props = new Properties();
		InputStream pfile = null;
		Connection conn = null;

		/** property file을 로드하기 */
		try {
			pfile = new FileInputStream(propFile);
			props.load(pfile);
			pfile.close();
		} catch (IOException e) {
			e.printStackTrace();
			return conn;
		} finally {
			if (pfile != null) {
				try {
					pfile.close();
				} catch (IOException ignored) {
				}
			}
		}

		/** 드라이버 로드하기 */
		String driver = props.getProperty("jdbc.driver");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return conn;
		}

		/** 드라이버 연결하기 & properties 파일로부터 DB user와 password 가져오기 */
		String dbURL = props.getProperty("jdbc.url");
		String dbUser = props.getProperty("jdbc.user");
		String dbPass = props.getProperty("jdbc.pass");
		try {
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
