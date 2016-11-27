package ServerStorage;

import java.sql.*;
import java.util.Vector;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 장비 디비 접근
 */

public class DBEquip {
	
	private static DBEquip db = null;
	private static Connection conn = null;
	
	//추가(insert) 쿼리 실행하는 PreparedStatement 객체 변수 선언
	private PreparedStatement pstmtAdd    = null;
	//삭제(delete) 쿼리 실행하는 PreparedStatement 객체 변수 선언
	private PreparedStatement pstmtDel    = null;
	//수정(update) 쿼리 실행하는 PreparedStatement 객체 변수 선언
	private PreparedStatement pstmtUpdate = null;

	private DBEquip() {
		// DB 연결하기
		conn = DBConnectivity.getConnection("properties/fuse.properties");
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static DBEquip getDBInstance() {
		if ( db == null )
			db = new DBEquip();
		return db;
	}
	
	//모든 장비 불러오기
	public Vector<Object> selectEquipAll() {
		Vector<Object> data = new Vector<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from equipment");
			while (rset.next()) {
				Vector<String> in = new Vector<String>();
				// 벡터에 각각의 값 추가		
				in.add(rset.getString(3));
				in.add(rset.getString(4));
				in.add(""+rset.getInt(1));
				in.add(rset.getString(2));
				in.add(rset.getString(5));
				in.add(rset.getString(6));
				in.add(rset.getString(7));

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
	
	//선택한 하나의 장비 불러오기
	public Vector<String> selectEquipOne(String eNum) {
		Vector<String> equip = new Vector<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery
					("select * from equipment where adminNum = "+eNum);
			while (rset.next()) {		
				equip.add(rset.getString(3));
				equip.add(rset.getString(4));
				equip.add(""+rset.getInt(1));
				equip.add(rset.getString(2));
				equip.add(rset.getString(5));
				equip.add(rset.getString(6));
				equip.add(rset.getString(7));
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equip; // 전체 데이터 저장하는 data 벡터 리턴
	}
	
	/** 클라이언트 detail^adminNum */
	public Vector<String> selectEquipToClient(int s) {
		//클라이언트한테 보낼 장비정보
		Vector<String> toClient = new Vector<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery
					("select * from equipment where adminNum = "+s);
			while (rset.next()) {
				toClient.add(""+rset.getInt(1));
				toClient.add(rset.getString(3));
				toClient.add(rset.getString(4));
				toClient.add(rset.getString(5));
				toClient.add(rset.getString(6));
				toClient.add(rset.getString(7));
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toClient; 
	}
	
	/**
	 * 특정타입에 'able'상태의 장비 관리번호와 이름을 셀렉하는 메소드 
	 * 클라이언트 search^type
	 */
	public Vector<Object> selectEquipType(String type) {
		Vector<Object> equip = new Vector<Object>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery
					("select adminNum, eName, eStatus from equipment "
							+ "where eStatus = 'able' AND type = '"+type+"'");
			while (rset.next()) {	
				Vector<String> in = new Vector<String>();
				in.add(""+rset.getInt(1));
				in.add(rset.getString(2));
				in.add(rset.getString(3));
				
				equip.add(in);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equip; 
	}
	
	//장비추가
	public void insert(String eName, String type, int adminN, 
			String serialN, String eStatus,
			String image, String detail){
		try{
			pstmtAdd = conn.prepareStatement
					("insert into equipment values(?,?,?,?,?,?,?)");
	
			pstmtAdd.setInt(1, adminN);
			pstmtAdd.setString(2, serialN);
			pstmtAdd.setString(3, eName);
			pstmtAdd.setString(4, type);
			pstmtAdd.setString(5, eStatus);
			pstmtAdd.setString(6, image);
			pstmtAdd.setString(7, detail);		

			//대입받은 쿼리를 실행 -> 입력 (insert)
			pstmtAdd.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//장비삭제
	public void delete(int adminN){
		try{
			pstmtDel = conn.prepareStatement
					("delete from equipment where adminNum = ?");
 			pstmtDel.setInt(1, adminN);

 			//대입받은 쿼리를 실행-> 삭제 (delete)
			pstmtDel.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//장비 수정
	public void update(String eName, String type, 
			int adminN, String serialN,String eStatus, 
			String image, String details){
		try{
			pstmtUpdate = conn.prepareStatement
					( "update equipment set adminNum = ?, "
					+ "serialNum = ?, eName = ?, type = ?, eStatus = ?, "
					+ "Image = ?, details =? where adminNum = ?" );
			pstmtUpdate.setInt(1, adminN);
			pstmtUpdate.setString(2, serialN);
			pstmtUpdate.setString(3, eName);
			pstmtUpdate.setString(4, type);
			pstmtUpdate.setString(5, eStatus);
			pstmtUpdate.setString(6, image);
			pstmtUpdate.setString(7, details);
			pstmtUpdate.setInt(8, adminN);

			pstmtUpdate.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// 장비 상태만 수정
	/** 클라이언트 request -> 장비상태 disable로 */
	public void updateStatus(int adminN, String eStatus){
		try{

			pstmtUpdate = conn.prepareStatement
					("update equipment set eStatus = ? "
							+ "where adminNum = ?");
			pstmtUpdate.setString(1, eStatus);
			pstmtUpdate.setInt(2, adminN);

			pstmtUpdate.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** 클라이언트 equip -> 관리번호(1), 장비이름(3), 장비종류(4), 상태(5) */
	public Vector<Object> selectEquipAllToClient() {
		Vector<Object> toClient = new Vector<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select * from equipment");
			while (rset.next()) {
				Vector<Object> in = new Vector<Object>();
				// 벡터에 각각의 값 추가
				in.add(rset.getInt(1));
				in.add(rset.getString(3));
				in.add(rset.getString(4));
				in.add(rset.getString(5));

				toClient.add(in);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toClient; 
	}
}
