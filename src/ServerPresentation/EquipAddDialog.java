package ServerPresentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerStorage.DBEquip;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class EquipAddDialog extends JDialog {
	
	public EquipContentPane contentPane;
	
	private JPanel ImagePane;
	private JLabel lbAddImage;
	private JLabel lbImage;
	int lbAddImageState = 0; // 0-exit 1-enter 2-click
	
	private JPanel buttonPane;
	private JButton btAdd;
	
	String imagefile;//추가한 이미지 파일 경로

	public EquipAddDialog() {
		setTitle("장비 추가");
		setBounds(800, 110, 610, 680);
		getContentPane().setLayout(new BorderLayout());
		
		ImagePane = new JPanel();
		ImagePane.setBackground(Color.WHITE);
		getContentPane().add(ImagePane, BorderLayout.NORTH);
		ImagePane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lbAddImage = new JLabel();
		lbAddImage.setBackground(Color.WHITE);
		lbAddImage.setOpaque(true);
		lbAddImage.setIcon(new ImageIcon("image.png"));
		lbAddImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lbAddImageState==1){
					FileDialog fileDialog = new FileDialog(EquipAddDialog.this, "이미지 파일 선택", FileDialog.LOAD);
					fileDialog.setLocation(400, 100);
					fileDialog.setVisible(true);

					imagefile = fileDialog.getDirectory() + fileDialog.getFile(); // 파일경로 + 이름 저장
					ImageIcon iiEquipIcon = new ImageIcon(imagefile);
					Image chgedSize = iiEquipIcon.getImage().getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
					iiEquipIcon = new ImageIcon(chgedSize);
					if (!imagefile.equals("nullnull")) {
						lbImage.setIcon(iiEquipIcon);
						lbAddImage.setVisible(false);
						ImagePane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(lbAddImageState==0){
					lbAddImage.setBackground(UIManager.getColor("Button.background"));
					lbAddImageState = 1;
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(lbAddImageState==1){
					lbAddImage.setBackground(Color.WHITE);
					lbAddImageState = 0;
				}
			}
		});
		
		lbImage = new JLabel(""); //이미지 선택시 나옴
		
		ImagePane.add(lbAddImage);
		ImagePane.add(lbImage);
		
		//내용추가
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
		
		//추가하기 버튼
		buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btAdd = new JButton("장비 추가하기");
		//추가하기 누르면 디비에 텍스트필드 정보 넘기고 저장 다이얼로그창 종료
		btAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String eName = contentPane.tfEname.getText();
				String type = contentPane.tfType.getText();
				String adminN = contentPane.tfAdminNum.getText();
				String serialN = contentPane.tfSerialNum.getText();
				String eStatus = "able";
				String image = imagefile;
				String detail = contentPane.tfDetails.getText();
			
				DBEquip.getDBInstance().insert(eName, type, adminN, serialN, eStatus, image, detail );
				
				Vector result = DBEquip.getDBInstance().selectEquipAll(); //장비내용 불러오기			
				SearchPane.getInstance().defaultTableModel.setDataVector(result, SearchPane.getInstance().title);
				dispose();
			}
		});
		buttonPane.setLayout(new BorderLayout(10, 5));
		btAdd.setBackground(Color.WHITE);
		btAdd.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btAdd);

	}

}
