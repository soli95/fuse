package ServerPresentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class RequestPane extends JPanel {
	private static RequestPane requestp;

	public static RequestPane getRPInstance() {
		if ( requestp == null )
			requestp = new RequestPane();
		return requestp;
	}
	public DefaultTableModel requestTableModel;
	private JScrollPane requestScrollPane;
	JTable tbRequestTable;
	public Vector<String> titleRequest = null;
	
	JTextField tfSearchRequest;
	JButton btSearchRequest;
	JComboBox cbSearchRequest;
	
	JButton btAccept;
	JButton btReject;
	
	RequestInfoDialog reqInfoDialog = new RequestInfoDialog();
	
	private RequestPane() {
		setBackground(Color.WHITE);
		setLayout(null);
		requestTableModel = new DefaultTableModel();
		titleRequest = new Vector<String>(); //타이틀
		titleRequest.add("대여번호");
		titleRequest.add("학생이름");
		titleRequest.add("장비이름");
		titleRequest.add("신청날짜");
		titleRequest.add("상태");
		
		Vector result = DBBorrow.getDbBInstance().selectRequAccAll(); //대여 리스트
		requestTableModel.setDataVector(result, titleRequest);
		tbRequestTable = new JTable(requestTableModel);
		tbRequestTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				reqInfoDialog.dispose();
				reqInfoDialog = new RequestInfoDialog();
				int row = tbRequestTable.getSelectedRow();
				String s = (String) tbRequestTable.getValueAt(row, 0);
				Vector<String> result = new Vector<String>();
				result = DBBorrow.getDbBInstance().selectRequAccOne(s);
				reqInfoDialog.tfRequestNum.setText(result.elementAt(0));
				reqInfoDialog.tfSName.setText(result.elementAt(1));
				reqInfoDialog.tfSNum.setText(result.elementAt(2));
				reqInfoDialog.tfSPhone.setText(result.elementAt(3));
				reqInfoDialog.tfAdminNum.setText(result.elementAt(4));
				reqInfoDialog.tfEName.setText(result.elementAt(5));
				reqInfoDialog.tfType.setText(result.elementAt(6));
				
				reqInfoDialog.tfRequestDate.setText(result.elementAt(8));
				reqInfoDialog.tfAcceptDate.setText(result.elementAt(9));
				reqInfoDialog.tfStatus.setText(result.elementAt(10));
				
				reqInfoDialog.setVisible(true);
			}
		});
		tbRequestTable.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tbRequestTable.setBackground(Color.WHITE);
		tbRequestTable.getTableHeader().setReorderingAllowed(false); 
		tbRequestTable.setRowHeight(30);
		
		requestScrollPane = new JScrollPane(tbRequestTable);
		requestScrollPane.setBounds(10, 40, 920, 525);
		add(requestScrollPane);		

		tfSearchRequest = new JTextField(20);
		tfSearchRequest.setBounds(295, 2, 300, 30);
		add(tfSearchRequest);
		
		btSearchRequest = new JButton("검색");
		btSearchRequest.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btSearchRequest.setBackground(Color.WHITE);
		btSearchRequest.setBounds(612, 2, 100, 30);
		add(btSearchRequest);
		
		cbSearchRequest = new JComboBox(new String[] {"학생이름", "장비이름", "장비종류","신청날짜","전체"});
		cbSearchRequest.setSelectedIndex(0);
		cbSearchRequest.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		cbSearchRequest.setBackground(Color.WHITE);
		cbSearchRequest.setBounds(158, 2, 120, 30);
		add(cbSearchRequest);
		
		btAccept = new JButton("승인");
		btAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DBBorrow.getDbBInstance().acceptBorrow("accept");
				Vector result = DBBorrow.getDbBInstance().selectRequAccAll(); //장비내용 불러오기			
				requestTableModel.setDataVector(result, titleRequest);
			}
		});
		btAccept.setBackground(Color.WHITE);
		btAccept.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btAccept.setBounds(650, 575, 125, 35);
		add(btAccept);
		
		btReject = new JButton("거절");
		btReject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DBBorrow.getDbBInstance().rejectBorrow("reject");
				Vector result = DBBorrow.getDbBInstance().selectRequAccAll(); //장비내용 불러오기			
				BorrowPane.getRPInstance().borrowTableModel.setDataVector(result, BorrowPane.getRPInstance().titleBorrow);

			}
		});
		btReject.setBackground(Color.WHITE);
		btReject.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btReject.setBounds(800, 575, 125, 35);
		add(btReject);
	}
}