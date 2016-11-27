package ServerPresentation;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerApplication.Borrow;
import ServerStorage.DBBorrow;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 */

public class RequestInfoDialog extends RequestFormDialog {

	//request팬에서만 사용
	
	//다이얼로그의 중앙 팬
	public JPanel requestInfoPane = new JPanel();
	
	//학생의 번호
	protected JLabel lbSPhone = new JLabel("학생연락처");
	protected JTextField tfSPhone = new JTextField(15);
	//장비의 종류
	public JLabel lbType  = new JLabel("장비종류");
	public JTextField tfType = new JTextField(15);
	//승인날짜
	public JLabel lbAcceptDate  = new JLabel("승인날짜");
	public JTextField tfAcceptDate = new JTextField(15);
	//대여상태
	public JLabel lbStatus = new JLabel("대여상태");
	public JTextField tfStatus = new JTextField(15);
	
	//버튼팬
	JPanel btPane = new JPanel();
	//승인버튼 -> 예약한 대여가 승인
	JButton btAccept  = new JButton("승인");
	//거절버튼 -> 예약한 대여가 거절
	JButton btReject = new JButton("거절");
	//완료버튼 -> 승인된 대여를 학생이 찾으러 옴
	JButton btCom = new JButton("대여");
	//종료버튼 -> 다이얼로그 닫힘
	JButton btQuit = new JButton("종료");
	
	private Font fPlain19 = new Font("맑은 고딕", Font.PLAIN, 19);
	private Font fPlain20 = new Font("맑은 고딕", Font.PLAIN, 20);

	//생성자
	public RequestInfoDialog() {
		setTitle("신청서 세부사항");
		setBounds(1200, 110, 500, 670);
		getContentPane().setLayout(new BorderLayout());
		
		//**************************정보팬******************************
		getContentPane().add(requestInfoPane, BorderLayout.CENTER);
		requestInfoPane.setLayout(new GridLayout(0, 2, 0, 0));
		requestInfoPane.setBackground(Color.WHITE);
		requestInfoPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// 대여번호->학생이름->학번->폰->관리번호->장비이름->종류->장비사진->신청날짜->승인날짜->상태
		requestInfoPane.add(lbBorrowNum);
		requestInfoPane.add(tfBorrowNum);

		requestInfoPane.add(lbSName);
		requestInfoPane.add(tfSName);

		requestInfoPane.add(lbSNum);
		requestInfoPane.add(tfSNum);

		lbSPhone.setFont(fPlain19);
		requestInfoPane.add(lbSPhone);
		tfSPhone.setFont(fPlain19);
		requestInfoPane.add(tfSPhone);

		requestInfoPane.add(lbAdminNum);
		requestInfoPane.add(tfAdminNum);

		requestInfoPane.add(lbEName);
		requestInfoPane.add(tfEName);

		lbType.setFont(fPlain19);
		requestInfoPane.add(lbType);
		tfType.setFont(fPlain19);
		requestInfoPane.add(tfType);
		
		lbRequestDate.setText("신청날짜");
		requestInfoPane.add(lbRequestDate);
		requestInfoPane.add(tfRequestDate);

		lbAcceptDate.setFont(fPlain19);
		requestInfoPane.add(lbAcceptDate);
		tfAcceptDate.setFont(fPlain19);
		requestInfoPane.add(tfAcceptDate);

		lbStatus.setFont(fPlain19);
		requestInfoPane.add(lbStatus);
		tfStatus.setFont(fPlain19);
		requestInfoPane.add(tfStatus);	

		//**************************버튼팬******************************
		getContentPane().add(btPane, BorderLayout.SOUTH);
		btPane.setLayout(new GridLayout(0, 4));
		btPane.setBackground(Color.WHITE);
		btPane.setBorder(new EmptyBorder(0, 5, 5, 5));
		
		//승인버튼
		btAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Borrow borrow = Borrow.getBorrowInstance();
				RequestPane r = RequestPane.getRPInstance();
				String accepDate = borrow.setToday();
				//승인날짜&승인상태 업데이트 필요
				DBBorrow.getDbBInstance().updateBorrow(tfBorrowNum.getText(), "accept", accepDate);
				System.out.println(tfBorrowNum.getText());
				Vector result = DBBorrow.getDbBInstance()
						.selectRequAccAll(); // 장비내용 불러오기
				r.requTableModel.setDataVector(result, r.titleRequest);
			}
		});
		btAccept.setBackground(Color.WHITE);
		btAccept.setFont(fPlain20);
		btPane.add(btAccept);

		//거절버튼
		btReject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Borrow borrow = Borrow.getBorrowInstance();
				RequestPane r = RequestPane.getRPInstance();
				BorrowPane b = BorrowPane.getRPInstance();
				String rejDate = borrow.setToday();
				Vector resultB = null;
				Vector resultR = null;
				//거절시 신청팬과 대여팬이 모두 업데이트 되어야함
				DBBorrow.getDbBInstance().updateBorrow(tfBorrowNum.getText(), "reject", rejDate);
				resultB = DBBorrow.getDbBInstance().selectRequAccAll();
				b.borrowTableModel.setDataVector(resultB, b.titleBorrow);
				resultR = DBBorrow.getDbBInstance().selectRequAccAll(); 
				r.requTableModel.setDataVector(resultR, r.titleRequest);
			}
		});
		btReject.setBackground(Color.WHITE);
		btReject.setFont(fPlain20);
		btPane.add(btReject);
		
		//대여완료버튼
		btCom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Borrow borrow = Borrow.getBorrowInstance();
				String comDate = borrow.setToday();//현장 완료시에 현재 날짜로 저장!
				RequestPane r = RequestPane.getRPInstance();
				BorrowPane b = BorrowPane.getRPInstance();
				Vector resultB = null;
				Vector resultR = null;
				//거절시 신청팬과 대여팬이 모두 업데이트 되어야함
				DBBorrow.getDbBInstance().comUpdateBorrow(tfBorrowNum.getText(), "complete", comDate);
				resultB = DBBorrow.getDbBInstance().selectRequAccAll();
				b.borrowTableModel.setDataVector(resultB, b.titleBorrow);
				resultR = DBBorrow.getDbBInstance().selectRequAccAll(); 
				r.requTableModel.setDataVector(resultR, r.titleRequest);

			}
		});
		btCom.setBackground(Color.WHITE);
		btCom.setFont(fPlain20);
		btPane.add(btCom);
		
		//종료버튼
		btQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btQuit.setBackground(Color.WHITE);
		btQuit.setFont(fPlain20);
		btPane.add(btQuit);
	}
}
