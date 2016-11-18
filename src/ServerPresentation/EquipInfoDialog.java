package ServerPresentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerStorage.DBEquip;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class EquipInfoDialog extends JDialog {

	private JPanel imagePane;
	public JLabel lbImage;
	public JLabel lbAddImage;
	int lbAddImageState = 0; // 0-exit 1-enter 2-click
	public String imagefile;//추가한 이미지 파일 경로
	public EquipContentPane contentPane;

	private JPanel buttonPane;
	private JButton btDelete;
	private JButton btModify;
	private JButton btRental;
	private JButton btExit;

	public EquipInfoDialog(Composite parent){

	}
	public EquipInfoDialog() {
		setTitle("장비 세부 정보");
		setBounds(1000, 110, 610, 680);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		// 이미지 팬 ***************************************************************
		imagePane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) imagePane.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(imagePane, BorderLayout.NORTH);
		imagePane.setBackground(Color.WHITE);
		
		lbAddImage = new JLabel();
		lbAddImage.setBackground(Color.WHITE);
		lbAddImage.setOpaque(true);
		lbAddImage.setIcon(new ImageIcon("image.png"));
		lbAddImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lbAddImageState==1){
					FileDialog fileDialog = new FileDialog(EquipInfoDialog.this, "이미지 파일 선택", FileDialog.LOAD);
					fileDialog.setLocation(400, 100);
					fileDialog.setVisible(true);

					imagefile = fileDialog.getDirectory() + fileDialog.getFile(); // 파일경로 + 이름 저장
					ImageIcon iiEquipIcon = new ImageIcon(imagefile);
					Image chgedSize = iiEquipIcon.getImage().getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
					iiEquipIcon = new ImageIcon(chgedSize);
					if (!imagefile.equals("nullnull")) {
						lbImage.setIcon(iiEquipIcon);
						lbAddImage.setVisible(false);
						imagePane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
		imagePane.add(lbAddImage);
		
		lbImage = new JLabel();
		imagePane.add(lbImage);

		// 정보 팬*****************************************************************
		contentPane = new EquipContentPane();		
		getContentPane().add(contentPane, BorderLayout.CENTER);
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
		contentPane.add(contentPane.lbEStatus);
		contentPane.add(contentPane.tfEStatus);
		
		// 버튼 팬*****************************************************************
		buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		FlowLayout flButtonPane = new FlowLayout(FlowLayout.RIGHT);
		flButtonPane.setHgap(15);
		flButtonPane.setVgap(10);
		buttonPane.setLayout(flButtonPane);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btDelete = new JButton("삭제");
		//해당 장비 디비에서 삭제하고 다이얼로그 종료
		btDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int yesNo = JOptionPane.showConfirmDialog(null, "해당 장비를 삭제하시겠습니까?", "장비 삭제",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (yesNo == 0) {
					DBEquip.getDBInstance().delete(contentPane.tfAdminNum.getText());
					Vector result = DBEquip.getDBInstance().selectEquipAll(); //장비내용 불러오기			
					SearchPane.getInstance().defaultTableModel.setDataVector(result, SearchPane.getInstance().title);
					dispose();
				}
			}
		});
		btDelete.setBackground(Color.WHITE);
		btDelete.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btDelete);

		btModify = new JButton("수정");
		//텍스트 필드 내용으로 디비 수정해서 다시 화면에 보여줌
		btModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String eName = contentPane.tfEname.getText();
				String type = contentPane.tfType.getText();
				String adminN = contentPane.tfAdminNum.getText();
				String serialN = contentPane.tfSerialNum.getText();
				String eStatus = contentPane.tfEStatus.getText();
				String image;
				if(imagefile!=null){//새로 파일에서 이미지 가져옴
					image = imagefile;
				}else{				//원래 사진파일이 있는경우
					image = lbImage.getText();
				}
				String detail = contentPane.tfDetails.getText();
				DBEquip.getDBInstance().update(eName, type, adminN, serialN, eStatus, image, detail);
				
				Vector result = DBEquip.getDBInstance().selectEquipAll(); //장비내용 불러오기			
				SearchPane.getInstance().defaultTableModel.setDataVector(result, SearchPane.getInstance().title);
			}
		});
		btModify.setBackground(Color.WHITE);
		btModify.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btModify);

		btRental = new JButton("대여");
		//현장대여하는 경우 대여버튼 클릭하면 해당 장비로 대여되는 폼 생성
		btRental.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RequestFormDialog requestFormDialog = new RequestFormDialog();
				requestFormDialog.tfEName.setText(contentPane.tfEname.getText());
				requestFormDialog.tfAdminNum.setText(contentPane.tfAdminNum.getText());
				requestFormDialog.setVisible(true);
			}
		});
		btRental.setBackground(Color.WHITE);
		btRental.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btRental);

		btExit = new JButton("종료");
		//화면 종료 x버튼 누른거랑 똑같은 효과
		btExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btExit.setBackground(Color.WHITE);
		btExit.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btExit);

	}
}
