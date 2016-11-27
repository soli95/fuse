package ServerPresentation;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ServerStorage.DBBorrow;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 대여기록 팬
 */

public class BorrowPane extends JPanel {
	private static BorrowPane rp = null;

	public static BorrowPane getRPInstance() {
		if (rp == null)
			rp = new BorrowPane();
		return rp;
	}

	public DefaultTableModel borrowTableModel = new DefaultTableModel();
	private JScrollPane borrowScrollPane = null;
	JTable tbBorrow = null;
	public Vector<String> titleBorrow = new Vector<String>();
	private BorrowInfoDialog bInfoD = new BorrowInfoDialog();

	private BorrowPane() {
		Vector<Object> res = null;
		setBackground(Color.WHITE);
		setLayout(null);
		
		titleBorrow.add("대여번호");
		titleBorrow.add("학생이름");
		titleBorrow.add("장비이름");
		titleBorrow.add("상태");

		// 대여 리스트
		res = DBBorrow.getDbBInstance().selectComRejRetAll();
		borrowTableModel.setDataVector(res, titleBorrow);
		tbBorrow = new JTable(borrowTableModel);
		tbBorrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 viewDetailsInfo();	
			}
		});
		tbBorrow.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tbBorrow.setBackground(Color.WHITE);
		tbBorrow.getTableHeader().setReorderingAllowed(false);
		tbBorrow.setRowHeight(30);

		borrowScrollPane = new JScrollPane(tbBorrow);
		borrowScrollPane.setBounds(10, 0, 920, 600);
		add(borrowScrollPane);
	}
	private void viewDetailsInfo(){
		bInfoD.dispose();
		bInfoD = new BorrowInfoDialog();
		int row = tbBorrow.getSelectedRow();
		String s = (String) tbBorrow.getValueAt(row, 0);
		Vector<String> res = new Vector<String>();
		res = DBBorrow.getDbBInstance().selectOne(s);
		bInfoD.tfBorrowNum.setText(res.elementAt(0));
		bInfoD.tfSName.setText(res.elementAt(1));
		bInfoD.tfSNum.setText(res.elementAt(2));
		bInfoD.tfSPhone.setText(res.elementAt(3));
		bInfoD.tfAdminNum.setText(res.elementAt(4));
		bInfoD.tfEName.setText(res.elementAt(5));
		bInfoD.tfType.setText(res.elementAt(6));
		bInfoD.tfRequestDate.setText(res.elementAt(8));
		bInfoD.tfAcceptDate.setText(res.elementAt(9));
		bInfoD.tfComDate.setText(res.elementAt(10));
		bInfoD.tfRetDate.setText(res.elementAt(11));
		bInfoD.tfStatus.setText(res.elementAt(12));
		
		bInfoD.setVisible(true);
	}
}
