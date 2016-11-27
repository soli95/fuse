package ServerPresentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerApplication.Borrow;
import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 대여리스트의 세부사항 다이얼로그 in 대여목록
 */

public class BorrowInfoDialog extends RequestInfoDialog {

	//정보팬
	private JPanel bInfoD = new JPanel();
	JLabel lbComDate = new JLabel("대여날짜");
	JTextField tfComDate = new JTextField(15);
	JLabel lbRetDate = new JLabel("반납날짜");
	JTextField tfRetDate = new JTextField(15);
	//버튼팬
	private JPanel buttonPane = new JPanel();
	private JButton btRet = new JButton("장비반납");
	private JButton btQuit = new JButton("종료");
	//폰트
	private Font fPlain19 = new Font("맑은 고딕", Font.PLAIN, 19);
	private Font fPlain20 = new Font("맑은 고딕", Font.PLAIN, 19);
	
	public BorrowInfoDialog() {
		setBounds(1200, 110, 500, 780);
		getContentPane().setLayout(new BorderLayout());
		bInfoD.setBackground(Color.WHITE);
		
		//*************************정보팬******************************
		bInfoD.setLayout(new GridLayout(0, 2));
		bInfoD.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(bInfoD, BorderLayout.CENTER);
		
		bInfoD.add(lbBorrowNum);
		bInfoD.add(tfBorrowNum);

		bInfoD.add(lbSName);
		bInfoD.add(tfSName);

		bInfoD.add(lbSNum);
		bInfoD.add(tfSNum);

		bInfoD.add(lbSPhone);
		bInfoD.add(tfSPhone);

		bInfoD.add(lbAdminNum);
		bInfoD.add(tfAdminNum);

		bInfoD.add(lbEName);
		bInfoD.add(tfEName);

		bInfoD.add(lbType);
		bInfoD.add(tfType);
		
		lbRequestDate.setText("신청날짜");
		bInfoD.add(lbRequestDate);
		bInfoD.add(tfRequestDate);

		bInfoD.add(lbAcceptDate);
		bInfoD.add(tfAcceptDate);

		lbComDate.setFont(fPlain19);
		bInfoD.add(lbComDate);
		tfComDate.setFont(fPlain19);
		bInfoD.add(tfComDate);

		lbRetDate.setFont(fPlain19);
		bInfoD.add(lbRetDate);
		tfRetDate.setFont(fPlain19);
		bInfoD.add(tfRetDate);

		bInfoD.add(lbStatus);
		bInfoD.add(tfStatus);

		
		//*************************버튼팬******************************
		buttonPane.setLayout(new GridLayout(0, 2));
		buttonPane.setBorder(new EmptyBorder(0, 5, 5, 5));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setBackground(Color.WHITE);
		//장비 반납 버튼 -> 장비 상태 able로 대여 상태 return으로 디비 업데이트 
		btRet.setFont(fPlain20);
		btRet.setBackground(Color.WHITE);
		btRet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int eNum = Integer.parseInt(tfAdminNum.getText());
				DBEquip.getDBInstance().updateStatus(eNum,"able");
				Borrow borrow = Borrow.getBorrowInstance();
				String returnDate = borrow.setToday();
				DBBorrow.getDbBInstance().retUpdateBorrow(tfBorrowNum.getText(), "return", returnDate);
				Vector<Object> res = DBBorrow.getDbBInstance().selectComRejRetAll();
				BorrowPane.getRPInstance().borrowTableModel.
					setDataVector(res, BorrowPane.getRPInstance().titleBorrow);
				dispose();
			}
		});
		buttonPane.add(btRet);
		
		btQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btQuit.setFont(fPlain20);
		btQuit.setBackground(Color.WHITE);
		buttonPane.add(btQuit);
	
	}
}
