package ServerPresentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author 김솔이
 * 2016.11.20 
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.0
 */

public class EquipContentPane extends JPanel {
	public JLabel lbEname = null;
	public JTextField tfEname= null;
	public JLabel lbType= null;
	public JTextField tfType= null;
	public JLabel lbAdminNum= null;
	public JTextField tfAdminNum= null;
	public JLabel lbSerialNum= null;
	public JTextField tfSerialNum= null;
	public JLabel lbDetails= null;
	public JTextField tfDetails= null;
	public JLabel lbEStatus= null;
	public JTextField tfEStatus= null;

	public EquipContentPane() {
		// 정보 팬*****************************************************
		setVisible(true);
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		GridLayout gl_contentPanel = new GridLayout(0, 2);
		setLayout(gl_contentPanel);

		lbEname = new JLabel("장비이름");
		lbEname.setFont(new Font("맑은 고딕", Font.PLAIN, 19));

		tfEname = new JTextField();
		tfEname.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		tfEname.setColumns(15);

		lbType = new JLabel("장비종류");
		lbType.setFont(new Font("맑은 고딕", Font.PLAIN, 19));

		tfType = new JTextField();
		tfType.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		tfType.setColumns(15);

		lbAdminNum = new JLabel("관리번호");
		lbAdminNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));

		tfAdminNum = new JTextField();
		tfAdminNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		tfAdminNum.setColumns(15);

		lbSerialNum = new JLabel("시리얼번호");
		lbSerialNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));

		tfSerialNum = new JTextField();
		tfSerialNum.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		tfSerialNum.setColumns(15);
		
		lbDetails = new JLabel("세부사항");
		lbDetails.setFont(new Font("맑은 고딕", Font.PLAIN, 19));

		tfDetails = new JTextField();
		tfDetails.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		tfDetails.setColumns(15);

		lbEStatus = new JLabel("상태");
		lbEStatus.setFont(new Font("맑은 고딕", Font.PLAIN, 19));

		tfEStatus = new JTextField();
		tfEStatus.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		tfEStatus.setColumns(15);

	}
}
