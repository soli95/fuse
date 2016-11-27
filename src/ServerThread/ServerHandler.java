package ServerThread;

import io.netty.channel.ChannelHandler.Sharable;

import java.nio.charset.Charset;
import java.util.Vector;

import ServerApplication.Borrow;
import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;
import ServerStorage.DBStudent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * Handler implementation
 */

@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override	//클라이언트가 데이터를 전송하면 이 메소드가 자동 실행됨
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());

        StringBuilder builder = new StringBuilder();
        builder.append("수신한 문자열 [");
        builder.append(readMessage);
        builder.append("]");
        System.out.println(builder.toString());

        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
    
    
    //
    void receiveMassage(ChannelHandlerContext ctx, Object msg){
    	
    	String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
    	String[] values = readMessage.split("^"); 
    	 
    	//search^type - 장비디비접근
    	//여러 개의 장비
		if (values[0].equals("search")) {
			//해당 타입의 대여가능한 장비 불러옴
			String toClient = "";
			Vector<Object> res = DBEquip.getDBInstance().selectEquipType(values[1]);
			for(int i = 0; i < res.size() ;i++){
		    	  Vector<String> result = (Vector<String>) res.elementAt(i);
		    	  for(int j = 0; j < result.size();j++ ){
		    		  toClient += result.elementAt(j) + "^";
		    	  }
		    	  toClient += ",";
		      }
			ctx.write(toClient);
		}
		//detail^adminNum - 장비디비접근
		//장비 하나에 대한 정보임
		else if (values[0].equals("detail")) {
			String toClient = "";
			Vector<String> res = DBEquip.getDBInstance()
					.selectEquipToClient(Integer.parseInt(values[1]));
			for (int i = 0; i < res.size(); i++) {
				toClient += res.elementAt(i) + "^";
			}
			ctx.write(toClient);
		}
		//request^학번^adminNum^requestDate - 장비디비,대여디비,학생디비접근
		else if (values[0].equals("request")) {
			Vector<String> student = DBStudent.getSDInstance().selectStudentOne(values[1]);
			String sName = student.elementAt(0);
			String sNum = student.elementAt(1);
			String sPhone = student.elementAt(2);
			String sPw = student.elementAt(3);
			int bNum = Borrow.getBorrowInstance().getBNum();
			DBBorrow.getDbBInstance().insertBorrow(bNum, values[1], 
					Integer.parseInt(values[2]), "request", values[3], null, null, null);
			//상태변경
			DBEquip.getDBInstance().updateStatus(Integer.parseInt(values[2]), "disable");
			//클라이언트한테 보낼 정보 없음
		} 
		//check^학번
		else if (values[0].equals("check")) {
			Vector<Object> res = DBBorrow.getDbBInstance().selePerBorrowAll(values[1]);
			String toClient = "";
			for(int i = 0; i < res.size() ;i++){
		    	  Vector<String> result = (Vector<String>) res.elementAt(i);
		    	  for(int j = 0; j < result.size();j++ ){
		    		  toClient += result.elementAt(j) + "^";
		    	  }
		    	  toClient += ",";
		      }
			ctx.write(toClient);
		} 
		//cancel^대여번호^장비번호
		else if (values[0].equals("cancel")) {
			//해당장비상태 able로
			DBBorrow.getDbBInstance().cancelBorrow(values[1], "cancel");
			DBEquip.getDBInstance().updateStatus(Integer.parseInt(values[2]), "able");
		} 
		//equip - 장비디비접근 모든 장비 보내줌
		else if (values[0].equals("equip")) {
			Vector<Object> res = DBEquip.getDBInstance().selectEquipAllToClient();
			String toClient = "";
			for(int i = 0; i < res.size() ;i++){
		    	  Vector<String> result = (Vector<String>) res.elementAt(i);
		    	  for(int j = 0; j < result.size();j++ ){
		    		  toClient += result.elementAt(j) + "^";
		    	  }
		    	  toClient += ",";
		      }
			ctx.write(toClient);
		} 
		//login^학번^비번 - 학생디비접근
		else if (values[0].equals("login")) {
			boolean res = DBStudent.getSDInstance().sLogin(values[1], values[2]);
			String toClient = ""+res;
			ctx.write(toClient);
		} 
		//change^학번^새비번 - 학생디비접근
		else if (values[0].equals("change")) {
			//클라이언트한테 보낼 정보 없음
			DBStudent.getSDInstance().upSPw(values[1], values[2]);
		}
    		
    }
}

