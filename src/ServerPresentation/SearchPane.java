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

public class SearchPane extends JPanel {
	private static SearchPane pp;
	
	public static SearchPane getInstance() {
		if ( pp == null )
			pp = new SearchPane();
		return pp;
	}
	
	public DefaultTableModel defaultTableModel;
	JTable tbEquip;
	JScrollPane spEquip;
	JTextField tfSearchEquip;
	JButton btSearchEquip;
	JComboBox cbSearchEquip;
	JButton btAddEquip;

	public Vector<String> title = null;
	
	EquipAddDialog eA = new EquipAddDialog();
	EquipInfoDialog eI = new EquipInfoDialog();
	
	private SearchPane() {
		setBackground(Color.WHITE);
		setLayout(null);
		DBEquip.getDBInstance().preDbTreatment(); //디비연결
		
		defaultTableModel = new DefaultTableModel();
		title = new Vector<String>(); //타이틀
		title.add("장비이름");
		title.add("장비종류");
		title.add("관리번호");
		title.add("시리얼번호");
		title.add("상태");
		
		Vector result = DBEquip.getDBInstance().selectEquipAll(); //장비내용 불러오기
		defaultTableModel.setDataVector(result, title);
		tbEquip = new JTable(defaultTableModel);
		tbEquip.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		tbEquip.setBackground(Color.WHITE);
		tbEquip.getTableHeader().setReorderingAllowed(false); 
		tbEquip.setRowHeight(30);
		tbEquip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				eI.dispose(); //세부사항다이얼로그는 하나만 띄움
				eI = new EquipInfoDialog();
				eI.setVisible(true);
				int row = tbEquip.getSelectedRow();
				String s = (String) tbEquip.getValueAt(row, 2);
				Vector<String> result = new Vector<String>();
				result = DBEquip.getDBInstance().selectEquipOne(" adminNum="+s);
				String eName = result.elementAt(0);
				String type = result.elementAt(1);
				String adminNum = result.elementAt(2);
				String serialNum = result.elementAt(3);
				String eStatus = result.elementAt(4);
				String image = result.elementAt(5);
				String details = result.elementAt(6);
				eI.contentPane.tfEname.setText(eName);
				eI.contentPane.tfType.setText(type);
				eI.contentPane.tfAdminNum.setText(adminNum);
				eI.contentPane.tfSerialNum.setText(serialNum);
				eI.contentPane.tfEStatus.setText(eStatus);
				eI.imagefile = image;
				eI.contentPane.tfDetails.setText(details);
				if (image != null && !image.equals("") && !image.equals(" ")){
					ImageIcon iiEquipIcon = new ImageIcon(image);
					Image chgedSize = iiEquipIcon.getImage().getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
					iiEquipIcon = new ImageIcon(chgedSize);
					eI.lbImage.setIcon(iiEquipIcon);
					eI.setVisible(true);
				}
			}
		});
		
		spEquip = new JScrollPane(tbEquip);
		spEquip.setBounds(10, 40, 920, 525);
		add(spEquip);
		
		tfSearchEquip = new JTextField(20);
		tfSearchEquip.setBounds(295, 2, 300, 30);
		add(tfSearchEquip);
		
		btSearchEquip = new JButton("검색");
		btSearchEquip.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btSearchEquip.setBackground(Color.WHITE);
		btSearchEquip.setBounds(612, 2, 100, 30);
		add(btSearchEquip);
		
		cbSearchEquip = new JComboBox(new String[] {"장비이름", "장비종류", "관리번호", "시리얼번호","상태", "전체"});
		cbSearchEquip.setSelectedIndex(0);
		cbSearchEquip.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		cbSearchEquip.setBackground(Color.WHITE);
		cbSearchEquip.setBounds(158, 2, 120, 30);
		add(cbSearchEquip); 
		
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
		btAddEquip.setBackground(Color.WHITE);
		btAddEquip.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btAddEquip.setBounds(790, 575, 125, 35);
		add(btAddEquip);

	}
}
