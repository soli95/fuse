package ServerPresentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import ServerStorage.DBBorrow;
import ServerStorage.DBStudent;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 학생정보관리팬
 */

public class StudentPane extends JPanel {
	private static StudentPane SMP = null;

	public static StudentPane getInstance() {
		if (SMP == null)
			SMP = new StudentPane();
		return SMP;
	}

	public DefaultTableModel sMTableModel = new DefaultTableModel();
	JTable tbStudent = null;
	JScrollPane spStudent = null;
	JButton btAddStudent = new JButton("학생등록");

	public Vector<String> title = new Vector<String>(); 
	private Vector<Object> result = new Vector<Object>();
	
	StudentDialog sA = new StudentDialog("학생등록");
	StudentDialog sU = new StudentDialog("정보수정");

	private StudentPane() {
		setBackground(Color.WHITE);
		setLayout(null);

		title.add("이름");
		title.add("학번");
		title.add("연락처");
		title.add("비밀번호");

		result = DBStudent.getSDInstance().selectStudentAll(); 
		sMTableModel.setDataVector(result, title);
		
		tbStudent = new JTable(sMTableModel);
		tbStudent.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tbStudent.setBackground(Color.WHITE);
		tbStudent.getTableHeader().setReorderingAllowed(false);
		tbStudent.setRowHeight(30);
		tbStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateStudent();
			}
		});

		spStudent = new JScrollPane(tbStudent);
		spStudent.setBounds(10, 0, 920, 560);
		add(spStudent);

		//***********************버튼************************
		btAddStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sA.dispose();
				sA = new StudentDialog("학생등록");
				sA.bP.add(sA.btAdd);
				sA.bP.add(sA.btQuit);
				sA.setVisible(true);
			}
		});
		setBt(btAddStudent);
		btAddStudent.setBounds(790, 570, 140, 40);
		add(btAddStudent);

	}
	
	private void setBt(JButton botton){
		botton.setHorizontalAlignment(SwingConstants.CENTER);
		botton.setBackground(SystemColor.inactiveCaption);
		botton.setForeground(Color.WHITE);
		botton.setFont(new Font("맑은 고딕", Font.BOLD, 22));
	}
	
	private void updateStudent(){
		sU.dispose();
		sU = new StudentDialog("정보수정");
		int row = tbStudent.getSelectedRow();
		String sN = (String) tbStudent.getValueAt(row, 1);
		Vector<String> res = new Vector<String>();
		res = DBStudent.getSDInstance().selectStudentOne(sN);
		sU.tfSName.setText(res.elementAt(0));
		sU.tfSNum.setText(res.elementAt(1));
		sU.tfPhone.setText(res.elementAt(2));
		sU.tfPw.setText(res.elementAt(3));
		sU.bP.add(sU.btUp);
		sU.bP.add(sU.btDel);
		sU.bP.add(sU.btQuit);
		sU.setVisible(true);
	}

}
