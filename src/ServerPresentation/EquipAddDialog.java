package ServerPresentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

public class EquipAddDialog extends JDialog {

	public EquipContentPane contentPane = null;

	private JPanel imagePane  = new JPanel();
	private JLabel lbAddImage = new JLabel();
	private JLabel lbImage = null;
	int lbAddImageState = 0; // 0-exit 1-enter 2-click

	private JPanel buttonPane  = new JPanel();
	private JButton btAdd = new JButton("장비 추가하기");

	String imgFile = null;// 추가한 이미지 파일 경로

	public EquipAddDialog() {
		setTitle("장비 추가");
		setBounds(800, 110, 610, 680);
		getContentPane().setLayout(new BorderLayout());

		imagePane.setBackground(Color.WHITE);
		getContentPane().add(imagePane, BorderLayout.NORTH);
		imagePane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lbAddImage.setBackground(Color.WHITE);
		lbAddImage.setOpaque(true);
		lbAddImage.setIcon(new ImageIcon("image.png"));
		lbAddImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (lbAddImageState == 1) {
					FileDialog fileD = new FileDialog(
							EquipAddDialog.this, "이미지 파일 선택",FileDialog.LOAD);
					fileD.setLocation(400, 100);
					fileD.setVisible(true);
					// 파일경로 + 이름 저장
					imgFile = fileD.getDirectory()+ fileD.getFile(); 
					ImageIcon iiEquipIcon = new ImageIcon(imgFile);
					Image chgedSize = iiEquipIcon.getImage()
							.getScaledInstance(400, 300,
									java.awt.Image.SCALE_SMOOTH);
					iiEquipIcon = new ImageIcon(chgedSize);
					if (!imgFile.equals("nullnull")) {
						lbImage.setIcon(iiEquipIcon);
						lbAddImage.setVisible(false);
						imagePane.setLayout(new FlowLayout(
								FlowLayout.CENTER, 5, 5));
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (lbAddImageState == 0) {
					lbAddImage.setBackground(
							UIManager.getColor("Button.background"));
					lbAddImageState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbAddImageState == 1) {
					lbAddImage.setBackground(Color.WHITE);
					lbAddImageState = 0;
				}
			}
		});

		lbImage = new JLabel(""); // 이미지 선택시 나옴

		imagePane.add(lbAddImage);
		imagePane.add(lbImage);

		// 내용추가
		contentPane = new EquipContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(new GridLayout(0, 2));

		contentPane.add(contentPane.lbEname);
		contentPane.add(contentPane.tfEname);
		contentPane.add(contentPane.lbType);
		contentPane.add(contentPane.tfType);
		contentPane.add(contentPane.lbAdminNum);
		contentPane.add(contentPane.tfAdminNum);
		contentPane.add(contentPane.lbSerialNum);
		contentPane.add(contentPane.tfSerialNum);
		contentPane.add(contentPane.lbDetails);
		contentPane.add(contentPane.tfDetails);

		// 추가하기 버튼
		buttonPane.setBackground(Color.WHITE);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		// 추가하기 누르면 디비에 텍스트필드 정보 넘기고 저장 다이얼로그창 종료
		btAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addEquip();
			}
		});
		buttonPane.setLayout(new BorderLayout(10, 5));
		btAdd.setBackground(Color.WHITE);
		btAdd.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btAdd);
	}
	
	private void addEquip(){
		String eName = contentPane.tfEname.getText();
		String type = contentPane.tfType.getText();
		int adminN = Integer.parseInt(contentPane.tfAdminNum.getText());
		String serialN = contentPane.tfSerialNum.getText();
		String eStatus = "able";
		String image = imgFile;
		String detail = contentPane.tfDetails.getText();
		@SuppressWarnings("rawtypes")
		Vector res = new Vector();
		SearchPane s = SearchPane.getInstance();
		
		DBEquip.getDBInstance().insert(eName, type, adminN,
				serialN, eStatus, image, detail);
		
		// 장비내용 불러오기
		res = DBEquip.getDBInstance().selectEquipAll(); 
		s.searTableModel.setDataVector(res, s.title);
		dispose();
	}
}
