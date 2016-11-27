package ServerApplication;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 */

public class Administrator {
	private String adminId = null;
	private String adminPw = null;
	
	public void setAdminId(String id){
		this.adminId = id;
	}
	public String getAdminId(){
		return adminId;
	}
	public void setAdminPw(String pw){
		this.adminPw = pw;
	}
	public String getAdminPw(){
		return adminPw;
	}
}
