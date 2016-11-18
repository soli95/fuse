package ServerStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class DBBorrow {
private static DBBorrow dbBorrow;
	
	String requestNum = null;
	String studentNum = null;
	String adminNum = null;
	String bStatus = null;
	String requestDate = null;
	String acceptDate = null;
	String completeDate = null;
	String returnDate = null;
	
	String sName;
	String eName;

	@SuppressWarnings("rawtypes")
	Vector dataB = new Vector<>();
	
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtAdd = null;
	
	public static DBBorrow getDbBInstance() {
		if ( dbBorrow == null )
			dbBorrow = new DBBorrow();
		return dbBorrow;
	}
	
	//대여목록 가져오기 
	//request, accept->신청리스트에
	//reject, return, complete->대여리스트에
	public Vector selectComRejRetAll(){
		@SuppressWarnings("rawtypes")
		Vector data = new Vector<>();
		data.clear();
		try {
			ResultSet rs = DBEquip.getDBInstance().stmt.executeQuery(
					"select requestNum, sName, eName, bStatus "
							+"from student, equipment,"
							+"(select requestNum, bStatus, studentNum, eNum from borrow "
							+"where bStatus = 'return' OR bStatus = 'complete' OR bStatus = 'reject') b "
							+ "where student.sNum=b.studentNum AND b.eNum=equipment.adminNum");
			while (rs.next()) {
				Vector<String> in = new Vector<String>(); // 1개의 레코드 저장하는 벡터 생성
				requestNum = rs.getString(1); 
				sName = rs.getString(2); 
				eName = rs.getString(3);
				bStatus = rs.getString(4);

				// 벡터에 각각의 값 추가
				in.add(requestNum);
				in.add(sName);
				in.add(eName);
				in.add(bStatus);
			
				// 전체 데이터를 저장하는 벡터에 in(1명의 데이터 저장) 벡터 추가
				data.add(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 전체 데이터 저장하는 data 벡터 리턴
		return data;
	}
	
	public Vector selectRequAccAll(){
		@SuppressWarnings("rawtypes")
		Vector data = new Vector<>();
		data.clear();
		try {
			ResultSet rs = DBEquip.getDBInstance().stmt.executeQuery(
					"select requestNum, sName, eName, requestDate, bStatus "
					+"from student, equipment,"
					+"(select requestNum, requestDate, bStatus, studentNum, eNum from borrow "
					+"where bStatus = 'request' OR bStatus = 'accept') b "
					+ "where student.sNum=b.studentNum AND b.eNum=equipment.adminNum");
			while (rs.next()) {
				Vector<String> in = new Vector<String>(); // 1개의 레코드 저장하는 벡터 생성
				requestNum = rs.getString(1); 
				sName = rs.getString(2); 
				eName = rs.getString(3);
				requestDate = rs.getString(4);
				bStatus = rs.getString(5);
				
				in.add(requestNum);
				in.add(sName);
				in.add(eName);
				in.add(requestDate);
				in.add(bStatus);

				// 전체 데이터를 저장하는 벡터에 in(1명의 데이터 저장) 벡터 추가
				data.add(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 전체 데이터 저장하는 data 벡터 리턴
		return data;
	}
	
	public Vector selectRequAccOne(String s){
		Vector<String> in = new Vector<String>(); // 1개의 레코드 저장하는 벡터 생성
	
		try {
			ResultSet rs = DBEquip.getDBInstance().stmt.executeQuery(
					"select requestNum, sName, sNum, phoneNum, eNum, eName, type,"
					+ " image, requestDate, acceptDate, bStatus "
					+"from student, equipment,"
					+"(select requestNum, requestDate, bStatus, studentNum, eNum, acceptDate from borrow "
					+"where bStatus = 'request' OR bStatus = 'accept') b "
					+ "where student.sNum=b.studentNum AND b.eNum=equipment.adminNum AND requestNum = "+s);
			while (rs.next()) {
				requestNum = rs.getString(1);
				sName = rs.getString(2);
				String sNum = rs.getString(3);
				String phone = rs.getString(4);
				String eNum = rs.getString(5);
				eName = rs.getString(6);
				String type = rs.getString(7);
				String image = rs.getString(8);
				requestDate = rs.getString(9);
				acceptDate = rs.getString(10);
				bStatus = rs.getString(11);

				in.add(requestNum);
				in.add(sName);
				in.add(sNum);
				in.add(phone);
				in.add(eNum);
				in.add(eName);
				in.add(type);
				in.add(image);
				in.add(requestDate);
				in.add(acceptDate);
				in.add(bStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 전체 데이터 저장하는 data 벡터 리턴
		return in;
	}
		
	
	//대여 수정
	public void acceptBorrow(String bStatus) {
		try {
			pstmtUpdate = DBEquip.getDBInstance().con.prepareStatement("update borrow set bStatus = ?");
			pstmtUpdate.setString(1, bStatus);
			pstmtUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void rejectBorrow(String bStatus) {
		try {
			pstmtUpdate = DBEquip.getDBInstance().con.prepareStatement("update borrow set bStatus = ?");
			pstmtUpdate.setString(1, bStatus);
			pstmtUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//대여 추가
	public void insertBorrow(String reqN, String sNum, String eNum, 
			String bStatus ,String reqD, String aD, String cD, String retD){
		try{
			pstmtAdd = DBEquip.getDBInstance().con.prepareStatement("insert into borrow values(?,?,?,?,?,?,?,?)");
		
			pstmtAdd.setString(1, reqN);
			pstmtAdd.setString(2, sNum);
			pstmtAdd.setString(3, eNum);
			pstmtAdd.setString(4, bStatus);
			pstmtAdd.setString(5, reqD);
			pstmtAdd.setString(6, aD);
			pstmtAdd.setString(7, cD);	
			pstmtAdd.setString(8, retD);

			pstmtAdd.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
