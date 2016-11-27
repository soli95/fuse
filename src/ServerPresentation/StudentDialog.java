package ServerPresentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerStorage.DBEquip;
import ServerStorage.DBStudent;

import java.awt.event.*;
import java.util.Vector;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 */

public class StudentDialog extends JDialog {

	JPanel cP = new JPanel();
	
	private JLabel lbSName = new JLabel("이름");
	public JTextField tfSName = new JTextField(15);
	private JLabel lbSNum = new JLabel("학번");
	public JTextField tfSNum = new JTextField(15);
	private JLabel lbPhone  = new JLabel("연락처");
	public JTextField tfPhone = new JTextField(15);
	private JLabel lbPw  = new JLabel("비밀번호");
	public JTextField tfPw = new JTextField(15);
	
	private Font fPlain20 = new Font("맑은 고딕", Font.PLAIN, 20);
	
	JPanel bP = new JPanel();
	JButton btAdd = new JButton("정보등록");
	JButton btUp = new JButton("정보수정");
	JButton btDel = new JButton("정보삭제");
	JButton btQuit = new JButton("종료");
	
	public StudentDialog(String title) {
		setTitle(title);
		setBounds(1200, 200, 500, 300);
		getContentPane().setLayout(new BorderLayout());
		
		//******************학생정보팬**************************
		cP.setLayout(new GridLayout(0, 2));
		cP.setBorder(new EmptyBorder(5, 5, 5, 5));
		cP.setBackground(Color.WHITE);
		getContentPane().add(cP, BorderLayout.CENTER);
		
		setLabel(lbSName);
		cP.add(lbSName);
		
		tfSName.setFont(fPlain20);
		cP.add(tfSName);
		
		setLabel(lbSNum);
		cP.add(lbSNum);
		
		tfSNum.setFont(fPlain20);
		cP.add(tfSNum);
		
		setLabel(lbPhone);
		cP.add(lbPhone);
		
		tfPhone.setFont(fPlain20);
		cP.add(tfPhone);
		
		setLabel(lbPw);
		cP.add(lbPw);
		
		tfPw.setFont(fPlain20);
		cP.add(tfPw);
		bP.setBorder(new EmptyBorder(0, 5, 5, 5));
		bP.setBackground(Color.WHITE);
		getContentPane().add(bP, BorderLayout.SOUTH);
		bP.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//학생 정보 등록 버튼
		btAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				registStudent();
			}
		});		
		setButton(btAdd);
		
		//학생 정보 수정 버튼
		btUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				updateStudent();
			}
		});		
		setButton(btUp);
		
		//학생 정보 삭제 버튼
		btDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				deleteStudent();
			}
		});		
		setButton(btDel);
		
		//다이얼로그 종료 버튼
		btQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});		
		setButton(btQuit);
	}
	
	private void setLabel(JLabel label){
		label.setBackground(Color.WHITE);
		label.setFont(fPlain20);
	}
	
	private void setButton(JButton button){
		button.setFont(new Font("맑은 고딕", Font.PLAIN, 21));
		button.setBackground(Color.white);
	}
	
	private void registStudent(){
		String sName = tfSName.getText();
		String sNum = tfSNum.getText();
		String sPhone = tfPhone.getText();
		String sPw = tfPw.getText();
		if(sName.equals("")||sNum.equals("")||sPhone.equals("")||sPw.equals("")){
			JOptionPane.showConfirmDialog(null, "모든 항목을 입력해주세요.", "학생정보등록",
					JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
		}else{
			DBStudent.getSDInstance().inStudent(sName, sNum, sPhone, sPw);
			Vector<Object> res = DBStudent.getSDInstance().selectStudentAll();
			StudentPane.getInstance().sMTableModel.setDataVector(res, StudentPane.getInstance().title);
			dispose();
		}
	}
	private void updateStudent(){
		//임시변수
		StudentPane sP = StudentPane.getInstance();
		DBStudent dbS = DBStudent.getSDInstance();
		String sName = tfSName.getText();
		String sNum = tfSNum.getText();
		String sPhone = tfPhone.getText();
		String sPw = tfPw.getText();
		
		dbS.upStudent(sName, sNum, sPhone, sPw);
		Vector<Object> res = dbS.selectStudentAll();
		sP.sMTableModel.setDataVector(res, sP.title);
		dispose();
	}
	
	private void deleteStudent(){
		int yesNo = JOptionPane.showConfirmDialog(null,
				"해당 장비를 삭제하시겠습니까?", "장비 삭제",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (yesNo == 0) {
			//임시변수
			StudentPane sP = StudentPane.getInstance();
			DBStudent dbS = DBStudent.getSDInstance();
			String sNum = tfSNum.getText();
			
			dbS.delStudent(sNum);
			Vector<Object> res = dbS.selectStudentAll();
			sP.sMTableModel.setDataVector(res, sP.title);
			dispose();
		}
	}
}
