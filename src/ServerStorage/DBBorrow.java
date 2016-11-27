package ServerStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import ServerApplication.Borrow;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 대여정보 접근 디비
 */

public class DBBorrow {
	private static DBBorrow dbBorrow;
	private static Connection conn;
	
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtAdd = null;

	private DBBorrow() {
		// DB 연결하기
		conn = DBConnectivity.getConnection("properties/fuse.properties");
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static DBBorrow getDbBInstance() {
		if (dbBorrow == null)
			dbBorrow = new DBBorrow();
		return dbBorrow;
	}

	// 대여목록 가져오기
	// reject, return, complete, cancel 상태의 대여목록
	public Vector<Object> selectComRejRetAll() {
		Vector<Object> data = new Vector<>();
		data.clear();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select bNum, sName, eName, bStatus "
							+ "from student, equipment,"
							+ "(select bNum , bStatus, "
							+ "studentNum, eNum from borrow "
							+ "where bStatus = 'return' "
							+ "OR bStatus = 'complete' "
							+ "OR bStatus = 'cancel' "
							+ "OR bStatus = 'reject') b "
							+ "where student.sNum=b.studentNum "
							+ "AND b.eNum=equipment.adminNum");
			while (rset.next()) {
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
		// 전체 데이터 저장하는 data 벡터 리턴
		return data;
	}

	//'request', 'accept' 상태인 대여목록
	public Vector<Object> selectRequAccAll() {
		Vector<Object> data = new Vector<>();
		data.clear();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select bNum , sName, eName, requestDate, bStatus "
							+ "from student, equipment,"
							+ "(select bNum , requestDate, bStatus, "
							+ "studentNum, eNum from borrow "
							+ "where bStatus = 'request' "
							+ "OR bStatus = 'accept') b "
							+ "where student.sNum=b.studentNum "
							+ "AND b.eNum=equipment.adminNum "
							+ "order by bNum");
			while (rset.next()) {
				Vector<String> in = new Vector<String>(); 

				in.add(rset.getString(1));
				in.add(rset.getString(2));
				in.add(rset.getString(3));
				in.add(rset.getString(4));
				in.add(rset.getString(5));

				data.add(in);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	

	public Vector<String> selectOne(String s) {
		Vector<String> in = new Vector<String>(); // 1개의 레코드 저장하는 벡터 생성
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.
					executeQuery("select bNum , sName, sNum, "
							+ "sPhone, eNum, eName, type,"
							+ " image, requestDate, acceptDate, "
							+ "completeDate, returnDate, bStatus "
							+ "from student, equipment, borrow "
							+ "where student.sNum=borrow.studentNum "
							+ "AND borrow.eNum=equipment.adminNum "
							+ "AND bNum = "+ s);
			while (rset.next()) {
				in.add(rset.getString(1));
				in.add(rset.getString(2));
				in.add(rset.getString(3));
				in.add(rset.getString(4));
				in.add(rset.getString(5));
				in.add(rset.getString(6));
				in.add(rset.getString(7));
				in.add(rset.getString(8));
				in.add(rset.getString(9));
				in.add(rset.getString(10));
				in.add(rset.getString(11));
				in.add(rset.getString(12));
				in.add(rset.getString(13));
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 전체 데이터 저장하는 data 벡터 리턴
		return in;
	}
	
	/** 클라이언트 cancel^대여번호  */
	public void cancelBorrow(String bNum, String bStatus){
		try {
			pstmtUpdate = conn.prepareStatement(
					"update borrow set bStatus = ? where bNum = ?");
			pstmtUpdate.setString(1, bStatus);
			pstmtUpdate.setString(2, bNum);
			pstmtUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 대여 승인&거절 후 수정
	public void updateBorrow(String bNum, String bStatus, String date) {
		try {
			pstmtUpdate = conn.prepareStatement(
							"update borrow set bStatus = ?, "
							+ "acceptDate = ? where bNum = ?");
			pstmtUpdate.setString(1, bStatus);
			pstmtUpdate.setString(2, date);
			pstmtUpdate.setString(3, bNum);
			pstmtUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 대여 완료 후 수정
	public void comUpdateBorrow(String bNum, String bStatus,
			String date) {
		try {
			pstmtUpdate = conn.prepareStatement(
					"update borrow set bStatus = ?, "
							+ "completeDate = ? where bNum = ?");
			pstmtUpdate.setString(1, bStatus);
			pstmtUpdate.setString(2, date);
			pstmtUpdate.setString(3, bNum);
			pstmtUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 반납 후 수정
	public void retUpdateBorrow(String bNum, String bStatus,
			String date) {
		try {
			pstmtUpdate = conn.prepareStatement(
					"update borrow set bStatus = ?, "
							+ "returnDate = ? where bNum = ?");
			pstmtUpdate.setString(1, bStatus);
			pstmtUpdate.setString(2, date);
			pstmtUpdate.setString(3, bNum);
			pstmtUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 대여 추가
	/** 클라이언트 request^학번^adminNum^requestDate */
	public void insertBorrow(int bNum, String sNum, int eNum,
			String bStatus, String rD, String aD, 
			String cD, String retD) {
		try {
			pstmtAdd = conn.prepareStatement(
					"insert into borrow values(?,?,?,?,?,?,?,?)");
			pstmtAdd.setInt(1, bNum);
			pstmtAdd.setString(2, sNum);
			pstmtAdd.setInt(3, eNum);
			pstmtAdd.setString(4, bStatus);
			pstmtAdd.setString(5, rD);
			pstmtAdd.setString(6, aD);
			pstmtAdd.setString(7, cD);
			pstmtAdd.setString(8, retD);

			pstmtAdd.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//가장 큰 borrowNum 가져오기
	public int selecBorrowNum() {
		int bNum = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select max(bNum) from borrow");
			while (rset.next()) {
				bNum = rset.getInt(1);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 전체 데이터 저장하는 data 벡터 리턴
		return bNum;
	}
	
	/** 클라이언트 check^학번 - Select Personal Borrow All */
	public Vector<Object> selePerBorrowAll(String sNum) {
		Vector<Object> data = new Vector<Object>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select bNum, eName, requestDate, acceptDate,"
							+ " completeDate, returnDate, bStatus "
							+ "from borrow, equipment "
							+ "where borrow.eNum = equipment.adminNum "
							+ "AND studentNum = " + sNum);
			while (rset.next()) {
				Vector<String> in = new Vector<String>();

				in.add(rset.getString(1));
				in.add(rset.getString(2));
				in.add(rset.getString(3));
				in.add(rset.getString(4));
				in.add(rset.getString(5));

				data.add(in);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 전체 데이터 저장하는 data 벡터 리턴
		return data;
	}
}
