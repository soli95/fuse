package ServerStorage;
import java.sql.*;
import java.util.Vector;

public class DBEquip {
	
	private static DBEquip db;
	
	public static DBEquip getDBInstance() {
		if ( db == null )
			db = new DBEquip();
		return db;
	}
	
	//장비상태 able, disable, request(누군가 신청함), accept(누군가 승인을 받긴했지만 아직 찾아가지 않음)
	
	public Connection con = null;
	public Statement stmt = null;
	
	private String Url = "jdbc:mariadb://localhost:3308/fuse"; 
	private String user = "root"; 
	private String password = "se";
	
	private PreparedStatement pstmtAdd    = null;//추가(insert) 쿼리 실행하는 PreparedStatement 객체 변수 선언
	private PreparedStatement pstmtDel    = null;//삭제(delete) 쿼리 실행하는 PreparedStatement 객체 변수 선언
	private PreparedStatement pstmtUpdate = null;//수정(update) 쿼리 실행하는 PreparedStatement 객체 변수 선언
	
	private DBEquip(){
		preDbTreatment();
	}
	
	public void preDbTreatment() {
		try{
			Class.forName("org.mariadb.jdbc.Driver");			
			con = DriverManager.getConnection(Url,user,password);
			stmt = con.createStatement();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//모든 장비 불러오기
	public Vector selectEquipAll() {
		@SuppressWarnings("rawtypes")
		Vector data = new Vector<>();
		try {
			ResultSet rs = stmt.executeQuery("select * from equipment");
			while (rs.next()) {
				Vector<String> in = new Vector<String>(); // 1개의 레코드 저장하는 벡터 생성
				String adminNum = rs.getString(1); 
				String serialNum = rs.getString(2); 
				String eName = rs.getString(3);
				String type = rs.getString(4);
				String eStatus = rs.getString(5);
				String image = rs.getString(6);
				String details = rs.getString(7);
				// 벡터에 각각의 값 추가
				
				in.add(eName);
				in.add(type);
				in.add(adminNum);
				in.add(serialNum);
				in.add(eStatus);
				in.add(image);
				in.add(details);

				// 전체 데이터를 저장하는 벡터에 in(1명의 데이터 저장) 벡터 추가
				data.add(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data; // 전체 데이터 저장하는 data 벡터 리턴
	}
	
	//선택한 하나의 장비 불러오기
	public Vector<String> selectEquipOne(String s) {
		Vector<String> equip = new Vector<String>();
		try {
			ResultSet rs = stmt.executeQuery("select * from equipment where"+s);
			while (rs.next()) {
				String adminNum = rs.getString(1); 
				String serialNum = rs.getString(2); 
				String eName = rs.getString(3);
				String type = rs.getString(4);
				String eStatus = rs.getString(5);
				String image = rs.getString(6);
				String details = rs.getString(7);
				
				equip.add(eName);
				equip.add(type);
				equip.add(adminNum);
				equip.add(serialNum);
				equip.add(eStatus);
				equip.add(image);
				equip.add(details);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equip; // 전체 데이터 저장하는 data 벡터 리턴
	}
	
	//장비추가
	public void insert(String eName, String type, String adminN, 
			String serialN,String eStatus, String image, String detail){
		try{
			//PreparedStatement 생성-> conn은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtAdd = con.prepareStatement("insert into equipment values(?,?,?,?,?,?,?)");

			//insert into member values(? -> 1 ,? -> 2, ? -> 3)" 각각의 ? 에 값 대입		
			pstmtAdd.setString(1, adminN);
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
	public void delete(String adminN){
		try{
			pstmtDel = con.prepareStatement("delete from equipment where adminNum = ?");
 			pstmtDel.setString(1, adminN);

 			//대입받은 쿼리를 실행-> 삭제 (delete)
			pstmtDel.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//장비 수정
	public void update(String eName, String type, String adminN, String serialN,String eStatus, String image, String details){
		try{

			pstmtUpdate = con.prepareStatement("update equipment set adminNum = ?, "
					+ "serialNum = ?, ename = ?, type = ?, eStatus = ?, Image = ?, details =? where adminNum = ?");
			pstmtUpdate.setString(1, adminN);
			pstmtUpdate.setString(2, serialN);
			pstmtUpdate.setString(3, eName);
			pstmtUpdate.setString(4, type);
			pstmtUpdate.setString(5, eStatus);
			pstmtUpdate.setString(6, image);
			pstmtUpdate.setString(7, details);
			pstmtUpdate.setString(8, adminN);

			pstmtUpdate.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//장비 상태만 수정
	public void updateStatus(String adminN,String eStatus){
		try{

			pstmtUpdate = con.prepareStatement("update equipment set eStatus = ? where adminNum = ?");
			pstmtUpdate.setString(1, eStatus);
			pstmtUpdate.setString(2, adminN);

			pstmtUpdate.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
