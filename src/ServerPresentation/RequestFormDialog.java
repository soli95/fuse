package ServerPresentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerApplication.Borrow;
import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;
import ServerStorage.DBStudent;

import java.awt.*;
import java.text.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 김솔이 
 * 2016.11.28 
 * SE 팀프로젝트 - fuse 
 * 컴과 장비 관리&예약 프로그램 
 * version 1.2
 * 현장 대여시 신청서 형식 다이얼로그
 */

public class RequestFormDialog extends JDialog {
	
	protected JPanel requestFormPane = new EquipContentPane();
	protected JLabel lbBorrowNum = new JLabel("대여번호");
	public JTextField tfBorrowNum = new JTextField(15);
	protected JLabel lbSName = new JLabel("학생이름");
	protected JTextField tfSName = new JTextField(15);
	protected JLabel lbSNum = new JLabel("학번");
	protected JTextField tfSNum = new JTextField(15);
	protected JLabel lbEName  = new JLabel("장비이름");
	protected JTextField tfEName = new JTextField(15);
	protected JLabel lbAdminNum = new JLabel("관리번호");
	public JTextField tfAdminNum = new JTextField(15);
	protected JLabel lbRequestDate = new JLabel("대여날짜");
	protected JTextField tfRequestDate = new JTextField(15);
	private JPanel buttonPane = new JPanel();
	private JButton btAccept = new JButton("현장 대여 승인");
	private JButton btCancel = new JButton("취소");
	
	private Font fPlain19 = new Font("맑은 고딕", Font.PLAIN, 19);
	private Font fPlain20 = new Font("맑은 고딕", Font.PLAIN, 20);

	public RequestFormDialog() {
		Borrow b = Borrow.getBorrowInstance();
		setTitle("신청서");
		setBounds(1000, 110, 500, 450);
		getContentPane().setLayout(new BorderLayout());

		//***********************신청정보팬**************************
		getContentPane().add(requestFormPane, BorderLayout.CENTER);
		requestFormPane.setLayout(new GridLayout(0, 2));
		requestFormPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		lbBorrowNum.setFont(fPlain19);
		requestFormPane.add(lbBorrowNum);
		tfBorrowNum.setFont(fPlain19);
		requestFormPane.add(tfBorrowNum);

		lbSName.setFont(fPlain19);
		requestFormPane.add(lbSName);
		tfSName.setFont(fPlain19);
		requestFormPane.add(tfSName);

		lbSNum.setFont(fPlain19);
		requestFormPane.add(lbSNum);
		tfSNum.setFont(fPlain19);
		requestFormPane.add(tfSNum);

		lbEName.setFont(fPlain19);
		requestFormPane.add(lbEName);
		tfEName.setFont(fPlain19);
		requestFormPane.add(tfEName);

		lbAdminNum.setFont(fPlain19);
		requestFormPane.add(lbAdminNum);
		tfAdminNum.setFont(fPlain19);
		requestFormPane.add(tfAdminNum);

		lbRequestDate.setFont(fPlain19);
		requestFormPane.add(lbRequestDate);
		// **Borrow클래스에서 신청날짜 받아옴**
		tfRequestDate.setText(b.setToday());
		tfRequestDate.setFont(fPlain19);
		requestFormPane.add(tfRequestDate);

		//***********************버튼팬*******************************
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String bNum = tfBorrowNum.getText();
				String sName = tfSName.getText();
				String sNum = tfSNum.getText();
				//String eName = tfEName.getText();
				int eNum = Integer.parseInt(tfAdminNum.getText());
				//현장대여시 신청승인완료날짜를 모두 현재 날짜로 세팅
				String completDate = tfRequestDate.getText();
				boolean check = DBStudent.getSDInstance().sNumCheck(sName, sNum);
				// 학생 이름과 학번확인
				if (!check) {
					JOptionPane.showConfirmDialog(null,
							"이름과 학번이 일치하는 학생이 없습니다.", "학생 불일치 확인",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				} else {
					DBBorrow.getDbBInstance().insertBorrow(Integer.parseInt(bNum),sNum,
							eNum, "complete", completDate,
							completDate, completDate, null);
					DBEquip.getDBInstance().updateStatus(eNum,"disable");
					Vector result = DBEquip.getDBInstance()
							.selectEquipAll(); // 장비내용 불러오기
					SearchPane.getInstance().searTableModel
							.setDataVector(result,SearchPane.getInstance().title);
				}
				// 신청번호 자동생성
				dispose();
			}
		});
		btAccept.setBackground(Color.WHITE);
		btAccept.setFont(fPlain20);
		buttonPane.add(btAccept);

		btCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btCancel.setFont(fPlain20);
		btCancel.setBackground(Color.WHITE);
		buttonPane.add(btCancel);
	}
}
