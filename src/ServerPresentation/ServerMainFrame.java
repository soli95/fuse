package ServerPresentation;

import javax.swing.*;

import ServerStorage.DBEquip;

import java.awt.*;
import java.util.Vector;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 메인프레임-main함수
 */

public class ServerMainFrame extends JFrame {

   private MainPane mainPane;

   public ServerMainFrame() { //프레임생성
      super("패기컴과");
      init();
   }
   
   private void init() { //메인팬 집어넣기
      //x버튼 클릭시 서버가 꺼지면 안되므로 막아두었음.
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
      setBounds(100, 100, 1100, 765);
      mainPane = new MainPane();
      mainPane.setBackground(new Color(255,255,255));
      setContentPane(mainPane);  
      
      String re = "";
      Vector<Object> res = DBEquip.getDBInstance().selectEquipType("스마트폰");
      for(int i = 0; i < res.size() ;i++){
    	  Vector<String> result = (Vector<String>) res.elementAt(i);
    	  for(int j = 0; j < result.size();j++ ){
    		  re += result.elementAt(j) + "^";
    	  }
    	  re += ",";
      }
      
      System.out.println(re);
   }
   
   public static void main(String[] args) {
      ServerThread.ServerThread thread=new ServerThread.ServerThread();
      ServerMainFrame frame = new ServerMainFrame();
      thread.start();
      frame.setVisible(true);
   }
}