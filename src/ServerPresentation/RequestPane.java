package ServerPresentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
 */

public class RequestPane extends JPanel {
	//신청팬 싱글톤
	private static RequestPane requestp = null;

	public static RequestPane getRPInstance() {
		if (requestp == null)
			requestp = new RequestPane();
		return requestp;
	}

	public DefaultTableModel requTableModel = new DefaultTableModel();
	private JScrollPane requestScrollPane = null;
	JTable tbRequestTable = null;
	
	//테이블 속성 타이틀
	public Vector<String> titleRequest = new Vector<String>();

	RequestInfoDialog rI = new RequestInfoDialog();

	// 신청&승인 리스트
	@SuppressWarnings("rawtypes")
	private Vector result = new Vector();
	
	private RequestPane() {
		init();
	}
	
	private void init() {

		//테이블 속성
		titleRequest.add("대여번호");
		titleRequest.add("학생이름");
		titleRequest.add("장비이름");
		titleRequest.add("신청날짜");
		titleRequest.add("상태");

		result = DBBorrow.getDbBInstance().selectRequAccAll();
		requTableModel.setDataVector(result, titleRequest);
		tbRequestTable = new JTable(requTableModel);
		tbRequestTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rI.dispose();
				rI = new RequestInfoDialog();
				int row = tbRequestTable.getSelectedRow();
				String s = (String) tbRequestTable.getValueAt(row, 0);
				Vector<String> res = new Vector<String>();
				
				res = DBBorrow.getDbBInstance().selectOne(s);
				rI.tfBorrowNum.setText(res.elementAt(0));
				rI.tfSName.setText(res.elementAt(1));
				rI.tfSNum.setText(res.elementAt(2));
				rI.tfSPhone.setText(res.elementAt(3));
				rI.tfAdminNum.setText(res.elementAt(4));
				rI.tfEName.setText(res.elementAt(5));
				rI.tfType.setText(res.elementAt(6));
				rI.tfRequestDate.setText(res.elementAt(8));
				rI.tfAcceptDate.setText(res.elementAt(9));
				rI.tfStatus.setText(res.elementAt(12));
				rI.setVisible(true);
			}
		});
		tbRequestTable.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tbRequestTable.setBackground(Color.WHITE);
		tbRequestTable.getTableHeader().setReorderingAllowed(false);
		tbRequestTable.setRowHeight(30);

		requestScrollPane = new JScrollPane(tbRequestTable);
		requestScrollPane.setBounds(10, 0, 920, 600);
		add(requestScrollPane);
		
		setBackground(Color.WHITE);
		setLayout(null);
	}
}