package ServerStorage;

import java.sql.ResultSet;

public class DBStudent {
	String sName;
	String sNum;
	boolean result;
	
	private static DBStudent db;
	
	public static DBStudent getSDInstance() {
		if ( db == null )
			db = new DBStudent();
		return db;
	}

	public boolean studentNumCheck(String sNa, String sNu) {
		try {
			ResultSet rs = DBEquip.getDBInstance().stmt.executeQuery("select sName from student where sNum="+sNu);
			while (rs.next()) {
				sName = rs.getString(1);
			}
			if (sNa.equals(sName)) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
