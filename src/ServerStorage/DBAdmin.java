package ServerStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBAdmin {
	String AdminId;
	String AdminPw;
	boolean result;

	public boolean idPwCheck(String id, String pw) {
		try {
			ResultSet rs = DBEquip.getDBInstance().stmt.executeQuery("select * from administrator");
			while (rs.next()) {
				AdminId = rs.getString(1);
				AdminPw = rs.getString(2);
			}
			if (id.equals(AdminId) && pw.equals(AdminPw)) {
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
