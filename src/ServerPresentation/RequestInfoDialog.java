package ServerPresentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RequestInfoDialog extends RequestFormDialog {

	public JPanel requestInfoPane = new JPanel();
	
	public JLabel lbType;
	public JTextField tfType;
	
	public JLabel lbAcceptDate;
	public JTextField tfAcceptDate;
	
	public JLabel lbStatus;
	public JTextField tfStatus;

	public RequestInfoDialog() {
		setTitle("신청서 세부사항");
		setBounds(1200, 110, 500, 700);
		getContentPane().setLayout(new BorderLayout());
		requestInfoPane.setBackground(Color.WHITE);
		requestInfoPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(requestInfoPane, BorderLayout.CENTER);
		requestInfoPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		//대여번호->학생이름->학번->폰->관리번호->장비이름->종류->장비사진->신청날짜->승인날짜->상태
		requestInfoPane.add(lbRequestNum);
		requestInfoPane.add(tfRequestNum);
		
		requestInfoPane.add(lbSName);
		requestInfoPane.add(tfSName);
		
		requestInfoPane.add(lbSNum);
		requestInfoPane.add(tfSNum);
		
		requestInfoPane.add(lbSPhone);
		requestInfoPane.add(tfSPhone);

		requestInfoPane.add(lbAdminNum);
		requestInfoPane.add(tfAdminNum);
		
		requestInfoPane.add(lbEName);
		requestInfoPane.add(tfEName);
		
		lbType = new JLabel("장비종류");
		lbType.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestInfoPane.add(lbType);
		
		tfType = new JTextField(15);
		tfType.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestInfoPane.add(tfType);
		
		requestInfoPane.add(lbRequestDate);
		requestInfoPane.add(tfRequestDate);
		
		lbAcceptDate = new JLabel("승인일");
		lbAcceptDate.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestInfoPane.add(lbAcceptDate);
		
		tfAcceptDate = new JTextField(15);
		tfAcceptDate.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestInfoPane.add(tfAcceptDate);
		
		lbStatus = new JLabel("상태");
		lbStatus.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestInfoPane.add(lbStatus);
		
		tfStatus = new JTextField(15);
		tfStatus.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		requestInfoPane.add(tfStatus);
		
	}

}
