package ServerPresentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;
import ServerStorage.DBStudent;

import java.awt.*;
import java.text.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RequestFormDialog extends JDialog {
	protected JPanel requestFormPane;
	protected JLabel lbRequestNum;
	public JTextField tfRequestNum;
	protected JLabel lbSName;
	protected JTextField tfSName;
	protected JLabel lbSNum;
	protected JTextField tfSNum;
	protected JLabel lbSPhone;
	protected JTextField tfSPhone;
	protected JLabel lbEName;
	protected JTextField tfEName;
	protected JLabel lbAdminNum;
	public JTextField tfAdminNum;
	protected JLabel lbRequestDate;
	protected JTextField tfRequestDate;
	
	private JPanel buttonPane;
	private JButton btAccept;
	private JButton btCancel;
	
	public RequestFormDialog() {
		setTitle("신청서");
		setBounds(1000, 110, 650, 450);
		getContentPane().setLayout(new BorderLayout());
		
		//신청정보 팬 
		requestFormPane = new EquipContentPane();
		requestFormPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(requestFormPane, BorderLayout.CENTER);
		requestFormPane.setLayout(new GridLayout(0, 2));
		
		lbRequestNum = new JLabel("신청번호");
		lbRequestNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(lbRequestNum);
		
		tfRequestNum = new JTextField(15);
		tfRequestNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(tfRequestNum);
		
		lbSName = new JLabel("학생이름");
		lbSName.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(lbSName);
		
		tfSName = new JTextField(15);
		tfSName.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(tfSName);
		
		lbSNum = new JLabel("학번");
		lbSNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(lbSNum);
		
		tfSNum = new JTextField(15);
		tfSNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(tfSNum);
		
		lbSPhone = new JLabel("학생연락처");
		lbSPhone.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		
		tfSPhone = new JTextField(15);
		tfSPhone.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		
		lbEName = new JLabel("장비이름");
		lbEName.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(lbEName);
		
		tfEName = new JTextField(15);
		tfEName.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(tfEName);
		
		lbAdminNum = new JLabel("관리번호");
		lbAdminNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(lbAdminNum);
		
		tfAdminNum = new JTextField(15);
		tfAdminNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));	
		requestFormPane.add(tfAdminNum);
		
		//현장대여시 대여날짜는 오늘 현장대여시 모든 날짜는 오늘 그리고 반납날짜만 계산해줌
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd", Locale.KOREA);
		Date currentTime = new Date();
		String dTime = formatter.format(currentTime);
		
		lbRequestDate = new JLabel("대여일");
		lbRequestDate.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(lbRequestDate);
		
		tfRequestDate = new JTextField(15);
		tfRequestDate.setText(dTime);
		tfRequestDate.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestFormPane.add(tfRequestDate);

		buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		FlowLayout flButtonPane = new FlowLayout(FlowLayout.RIGHT);
		flButtonPane.setHgap(10);
		flButtonPane.setVgap(10);
		buttonPane.setLayout(flButtonPane);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btAccept = new JButton("현장 대여 승인");
		btAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String rNum = tfRequestNum.getText();
				String sName = tfSName.getText();
				String sNum = tfSNum.getText();
				String eName = tfEName.getText();
				String eNum = tfAdminNum.getText();
				String completDate = tfRequestDate.getText();
				
				boolean check = DBStudent.getSDInstance().studentNumCheck(sName,sNum);
				//학생 이름과 학번확인
				if(!check){
					JOptionPane.showConfirmDialog(null, "이름과 학번이 일치하는 학생이 없습니다.", "CHECK",
							JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				}else{
					DBBorrow.getDbBInstance().insertBorrow(rNum,sNum,eNum,
							"complete",completDate,completDate,completDate,null);
					DBEquip.getDBInstance().updateStatus(eNum,"disable");
				}
				
				
				//대여디비에 저장
				//해당장비 상태 바꿈
				//신청번호 자동생성
				dispose();
			}
		});
		btAccept.setBackground(Color.WHITE);
		btAccept.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btAccept);

		btCancel = new JButton("취소");
		btCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btCancel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btCancel.setBackground(Color.WHITE);
		buttonPane.add(btCancel);

	}

}
