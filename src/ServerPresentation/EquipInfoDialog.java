package ServerPresentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerApplication.Borrow;
import ServerStorage.DBEquip;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * @author 김솔이
 * 2016.11.20 
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.2
 * 장비정보다이얼로그
 */

public class EquipInfoDialog extends JDialog {

	private JPanel imagePane;
	public JLabel lbImage;
	public JLabel lbAddImage;
	int lbAddImageState = 0; // 0-exit 1-enter 2-click
	public String imagefile; // 추가한 이미지 파일 경로
	public EquipContentPane contentPane;

	private JPanel buttonPane;
	private JButton btDelete;
	private JButton btModify;
	private JButton btRental;
	private JButton btExit;
	
	DBEquip dbE = DBEquip.getDBInstance();

	public EquipInfoDialog() {
		setTitle("장비 세부 정보");
		setBounds(1000, 110, 500, 650);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// 이미지 팬 ****************************************************
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
				if (lbAddImageState == 1) {
					FileDialog fileDialog = new FileDialog(
							EquipInfoDialog.this, "이미지 파일 선택",
							FileDialog.LOAD);
					fileDialog.setLocation(400, 100);
					fileDialog.setVisible(true);

					imagefile = fileDialog.getDirectory()
							+ fileDialog.getFile(); // 파일경로 + 이름 저장
					ImageIcon iiEquipIcon = new ImageIcon(imagefile);
					Image chgedSize = iiEquipIcon.getImage()
							.getScaledInstance(400, 300,
									java.awt.Image.SCALE_SMOOTH);
					iiEquipIcon = new ImageIcon(chgedSize);
					if (!imagefile.equals("nullnull")) {
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
		imagePane.add(lbAddImage);

		lbImage = new JLabel();
		imagePane.add(lbImage);

		// 정보 팬******************************************************
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

		// 버튼 팬******************************************************
		buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setLayout(new GridLayout(0, 4));
		buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btDelete = new JButton("삭제");
		// 해당 장비 디비에서 삭제하고 다이얼로그 종료
		btDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteEquip();
			}
		});
		btDelete.setBackground(Color.WHITE);
		btDelete.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btDelete);

		btModify = new JButton("수정");
		// 텍스트 필드 내용으로 디비 수정해서 다시 화면에 보여줌
		btModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateEquip();
			}
		});
		btModify.setBackground(Color.WHITE);
		btModify.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btModify);

		btRental = new JButton("대여");
		// 현장대여하는 경우 대여버튼 클릭하면 해당 장비로 대여되는 폼 생성
		btRental.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				borrowEquip();
			}
		});
		btRental.setBackground(Color.WHITE);
		btRental.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		buttonPane.add(btRental);

		btExit = new JButton("종료");
		// 화면 종료 x버튼 누른거랑 똑같은 효과
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
	
	private void deleteEquip(){
		int yesNo = JOptionPane.showConfirmDialog(null,
				"해당 장비를 삭제하시겠습니까?", "장비 삭제",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (yesNo == 0) {
			dbE.delete(Integer.parseInt(contentPane.tfAdminNum.getText()));
			Vector<Object> res = dbE.selectEquipAll();
			SearchPane.getInstance().searTableModel
					.setDataVector(res,
							SearchPane.getInstance().title);
			dispose();
		}
	}
	
	private void updateEquip(){
		String eName = contentPane.tfEname.getText();
		String type = contentPane.tfType.getText();
		int adminN = Integer.parseInt(contentPane.tfAdminNum.getText());
		String serialN = contentPane.tfSerialNum.getText();
		String eStatus = contentPane.tfEStatus.getText();
		String image;
		if (imagefile != null) {// 새로 파일에서 이미지 가져옴
			image = imagefile;
		} else { // 원래 사진파일이 있는경우
			image = lbImage.getText();
		}
		String detail = contentPane.tfDetails.getText();
		dbE.update(eName, type, adminN,
				serialN, eStatus, image, detail);
		Vector<Object> result = dbE.selectEquipAll(); 
		SearchPane.getInstance().searTableModel
				.setDataVector(result,
						SearchPane.getInstance().title);
	}
	
	private void borrowEquip(){
		String status = contentPane.tfEStatus.getText();
		if (status.equals("able")) {
			RequestFormDialog reqDia = new RequestFormDialog();
			reqDia.tfBorrowNum.setText(""+Borrow.getBorrowInstance().getBNum());
			reqDia.tfEName.setText(contentPane.tfEname.getText());
			reqDia.tfAdminNum.setText(contentPane.tfAdminNum.getText());
			reqDia.setVisible(true);
		}else{
			JOptionPane.showConfirmDialog(null, "대여가능한 상태가 아닙니다.", "CHECK",
					JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
		}
	}
}
