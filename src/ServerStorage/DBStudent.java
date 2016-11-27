package ServerStorage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 학생정보접근 디비
 */

public class DBStudent {

	private static DBStudent db;
	private static Connection conn = null;
	
	private PreparedStatement pstmtIn = null;
	private PreparedStatement pstmtUp = null;
	private PreparedStatement pstmtDel = null;
	
	private DBStudent() {
		// DB 연결하기
		conn = DBConnectivity.getConnection("properties/fuse.properties");
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static DBStudent getSDInstance() {
		if (db == null)
			db = new DBStudent();
		return db;
	}
	
	public boolean sNumCheck(String sNa, String sNu) {
		String sName = null;
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select sName from student where sNum=" + sNu);
			while (rset.next()) {
				sName = rset.getString(1);
			}
			if (sNa.equals(sName)) {
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
	
	public Vector<Object> selectStudentAll(){
		Vector<Object> data = new Vector<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from student order by sNum");
			while (rset.next()) {
				// 1개의 레코드 저장하는 벡터 생성
				Vector<String> in = new Vector<String>();
				// 벡터에 각각의 값 추가		
				in.add(rset.getString(1));
				in.add(rset.getString(2));
				in.add(rset.getString(3));
				in.add(rset.getString(4));
				
				// 전체 데이터를 저장하는 벡터에 in(1명의 데이터 저장) 벡터 추가
				data.add(in);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data; // 전체 데이터 저장하는 data 벡터 리턴
	}
	
	public Vector<String> selectStudentOne(String sN){
		Vector<String> student = new Vector<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select * from student where sNum = "+sN);
			while (rset.next()) {
				student.add(rset.getString(1));
				student.add(rset.getString(2));
				student.add(rset.getString(3));
				student.add(rset.getString(4));
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}
	
	// 학생 등록
	public void inStudent(String sName, String sNum, String sPhone,
				String sPw) {
		try {
			pstmtIn = conn.prepareStatement(
					"insert into student values(?,?,?,?,?)");

			pstmtIn.setString(1, sName);
			pstmtIn.setString(2, sNum);
			pstmtIn.setString(3, sPhone);
			pstmtIn.setString(4, sPw);
			pstmtIn.setBoolean(5, false);

			pstmtIn.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 학생 정보 수정
	public void upStudent(String sName, String sNum,
			String sPhone, String sPw) {
		try {
			pstmtUp = conn.prepareStatement(
					"update student set sName = ?, sNum = ?, "
					+ "sPhone = ?, sPw = ? where sNum = ?");
			pstmtUp.setString(1, sName);
			pstmtUp.setString(2, sNum);
			pstmtUp.setString(3, sPhone);
			pstmtUp.setString(4, sPw);
			pstmtUp.setString(5, sNum);
			
			pstmtUp.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 학생 삭제
	public void delStudent(String sNum) {
		try {
			pstmtDel = conn.prepareStatement(
					"delete from student where sNum = ?");
			pstmtDel.setString(1, sNum);
			
			pstmtDel.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 클라가 change^학번^새비번 */
	public void upSPw(String sNum, String sPw) {
		try {
			pstmtUp = conn.prepareStatement(
					"update student set sPw = ? where sNum = ?");
			pstmtUp.setString(1, sPw);
			pstmtUp.setString(2, sNum);
			
			pstmtUp.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 클라이언트 login^학번^비번 */ 
	public boolean sLogin(String sNum, String sPw) {
		String sPass = null;
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select sPw from student where sNum=" + sNum);
			while (rset.next()) {
				sPass = rset.getString(1);
			}
			if (sPw.equals(sPass)) {
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
