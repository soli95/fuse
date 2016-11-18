package ServerPresentation;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;

public class BorrowPane extends JPanel {
	private static BorrowPane rp;

	public static BorrowPane getRPInstance() {
		if ( rp == null )
			rp = new BorrowPane();
		return rp;
	}
	public DefaultTableModel borrowTableModel;
	private JScrollPane borrowScrollPane;
	JTable tbBorrowTable;
	public Vector<String> titleBorrow = null;
	
	JTextField tfSearchBorrow;
	JButton btSearchBorrow;
	JComboBox cbSearchBorrow;

	private BorrowPane() {
		setBackground(Color.WHITE);
		setLayout(null);
		borrowTableModel = new DefaultTableModel();
		titleBorrow = new Vector<String>(); //타이틀
		titleBorrow.add("대여번호");
		titleBorrow.add("학생이름");
		titleBorrow.add("장비이름");
		titleBorrow.add("상태");
		
		Vector result = DBBorrow.getDbBInstance().selectComRejRetAll(); //대여 리스트
		borrowTableModel.setDataVector(result, titleBorrow);
		tbBorrowTable = new JTable(borrowTableModel);
		tbBorrowTable.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tbBorrowTable.setBackground(Color.WHITE);
		tbBorrowTable.getTableHeader().setReorderingAllowed(false); 
		tbBorrowTable.setRowHeight(30);
		
		borrowScrollPane = new JScrollPane(tbBorrowTable);
		borrowScrollPane.setBounds(10, 40, 920, 565);
		add(borrowScrollPane);		

		tfSearchBorrow = new JTextField(20);
		tfSearchBorrow.setBounds(295, 2, 300, 30);
		add(tfSearchBorrow);
		
		btSearchBorrow = new JButton("검색");
		btSearchBorrow.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btSearchBorrow.setBackground(Color.WHITE);
		btSearchBorrow.setBounds(612, 2, 100, 30);
		add(btSearchBorrow);
		
		cbSearchBorrow = new JComboBox(new String[] {"학생이름", "장비이름", "장비종류","반납날짜","전체"});
		cbSearchBorrow.setSelectedIndex(0);
		cbSearchBorrow.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		cbSearchBorrow.setBackground(Color.WHITE);
		cbSearchBorrow.setBounds(158, 2, 120, 30);
		add(cbSearchBorrow);
	}
}
