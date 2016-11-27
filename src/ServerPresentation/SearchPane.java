package ServerPresentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ServerPresentation.*;
import ServerStorage.DBEquip;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * @author 김솔이
 * 2016.11.28
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 장비조회 팬
 */

public class SearchPane extends JPanel {
	private static SearchPane SP = null;

	public static SearchPane getInstance() {
		if (SP == null)
			SP = new SearchPane();
		return SP;
	}

	public DefaultTableModel searTableModel = new DefaultTableModel();
	JTable tbEquip = null;
	JScrollPane spEquip = null;
	JButton btAddEquip = null;

	public Vector<String> title = new Vector<String>(); 
	@SuppressWarnings("rawtypes")
	private Vector result = new Vector();
	
	private EquipAddDialog eA = new EquipAddDialog();
	private EquipInfoDialog eI = new EquipInfoDialog();
	
	private SearchPane() {
		 init();
	}
	
	private void init() {
		setBackground(Color.WHITE);
		setLayout(null);
		//DBEquip.getDBInstance().preDbTreatment(); // 디비연결

		title.add("장비이름");
		title.add("장비종류");
		title.add("관리번호");
		title.add("시리얼번호");
		title.add("상태");

		result = DBEquip.getDBInstance().selectEquipAll(); 
		searTableModel.setDataVector(result, title);
		
		tbEquip = new JTable(searTableModel);
		tbEquip.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tbEquip.setBackground(Color.WHITE);
		tbEquip.getTableHeader().setReorderingAllowed(false);
		tbEquip.setRowHeight(30);
		tbEquip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				viewDetailsInfo();
			}
		});

		spEquip = new JScrollPane(tbEquip);
		spEquip.setBounds(10, 0, 920, 560);
		add(spEquip);

		//***********************버튼************************
		btAddEquip = new JButton("장비추가");
		btAddEquip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				eA.dispose();
				eA = new EquipAddDialog();
				eA.setVisible(true);

			}
		});
		btAddEquip.setHorizontalAlignment(SwingConstants.CENTER);
		btAddEquip.setBackground(SystemColor.inactiveCaption);
		btAddEquip.setForeground(Color.WHITE);
		btAddEquip.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		btAddEquip.setBounds(790, 570, 140, 40);
		add(btAddEquip);
	}
	
	private void viewDetailsInfo(){
		String image = null;
		int row = 0;
		String eNum = null;
		Vector<String> result = new Vector<String>();
		eI.dispose(); // 세부사항다이얼로그는 하나만 띄움
		eI = new EquipInfoDialog();
		eI.setVisible(true);
		row = tbEquip.getSelectedRow();
		eNum = (String) tbEquip.getValueAt(row, 2);
		result = DBEquip.getDBInstance().selectEquipOne(eNum);
		image = result.elementAt(5);
		eI.contentPane.tfEname.setText(result.elementAt(0));
		eI.contentPane.tfType.setText(result.elementAt(1));
		eI.contentPane.tfAdminNum.setText(result.elementAt(2));
		eI.contentPane.tfSerialNum.setText(result.elementAt(3));
		eI.contentPane.tfEStatus.setText(result.elementAt(4));
		eI.imagefile = image;
		eI.contentPane.tfDetails.setText(result.elementAt(6));
		if (image != null && !image.equals("")
				&& !image.equals(" ")) {
			ImageIcon iiEquipIcon = new ImageIcon(image);
			Image chgedSize = iiEquipIcon.getImage()
					.getScaledInstance(400, 300,
							java.awt.Image.SCALE_SMOOTH);
			iiEquipIcon = new ImageIcon(chgedSize);
			eI.lbImage.setIcon(iiEquipIcon);
			eI.setVisible(true);
		}
	}
}
