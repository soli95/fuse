package ServerPresentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EquipContentPane extends JPanel {
	public JLabel lbEname;
	public JTextField tfEname;
	public JLabel lbType;
	public JTextField tfType;
	public JLabel lbAdminNum;
	public JTextField tfAdminNum;
	public JLabel lbSerialNum;
	public JTextField tfSerialNum;
	public JLabel lbDetails;
	public JTextField tfDetails;
	public JLabel lbEStatus;
	public JTextField tfEStatus;

	public EquipContentPane() {
		// 정보 팬*****************************************************************
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
