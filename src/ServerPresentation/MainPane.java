package ServerPresentation;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ServerStorage.DBAdmin;
import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;
import ServerStorage.DBStudent;
import ServerApplication.Administrator;
import ServerApplication.Borrow;

/**
 * @author 김솔이
 * 2016.11.20 
 * SE 팀프로젝트 - fuse
 * 컴과 장비 관리&예약 프로그램
 * version 1.0
 * 메인패널
 */

public class MainPane extends JPanel {
	// 로그인 전
	private JLabel lbserverOnOff = new JLabel("서버OFF");
	private JTextField tfId = new JTextField(15);
	private JTextField tfPw = new JTextField(15);
	private JButton btLogin = new JButton("서버 켜기");// 로그인아웃 같은 버튼으로 사용
	// 로그인 후
	private JLabel lbIcon = new JLabel(); // 로그인후 아이콘
	private JLabel lbAdminId = new JLabel();// 로그인후 아이디만 보여줌
	// 기능
	private JLabel lbSearch = new JLabel("장비조회");
	private JLabel lbRequest  = new JLabel("신청목록");
	private JLabel lbBorrow = new JLabel("대여목록");
	private JLabel lbManage = new JLabel("학생조회");
	// 폰트
	private Font fBold20 = new Font("맑은 고딕", Font.BOLD, 20);
	private Font fBold22 = new Font("맑은 고딕", Font.BOLD, 22);
	private Font fPlain20 = new Font("맑은 고딕", Font.PLAIN, 20);

	private StartPane startPane = new StartPane();
	private SearchPane searchPane = SearchPane.getInstance();
	private RequestPane requestPane = RequestPane.getRPInstance();
	private BorrowPane borrowPane = BorrowPane.getRPInstance();
	private StudentPane studentPane = StudentPane.getInstance();
	
	private Administrator admin = new Administrator();

	// 라벨 상태에 따라 라벨백그라운드 색바꿔주려고 0-exit 1-enter 2-click
	private int lbserverOnOffState = 0;
	private int lbSearchState = 0; 
	private int lbRequestState = 0; 
	private int lbBorrowState = 0; 
	private int lbManageState = 0; 

	// 로그인한 상태 체크 로그인 안되어있을시 기능작동 안함
	private boolean loginCheck = false;

	public MainPane() {
		init();
	}

	private void init() {
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// 로그인 전
		lbserverOnOff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (lbserverOnOffState == 1 && loginCheck) {
		
					setLabel(lbSearch);
					lbSearchState = 0;
					
					setLabel(lbRequest);
					lbRequestState = 0;
					
					setLabel(lbBorrow);
					lbBorrowState = 0;
					
					setLabel(lbManage);
					lbManageState = 0;
					
					lbserverOnOff.setFont(fBold20);
					lbserverOnOffState = 0;
					setPanel(true,false,false,false,false);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbserverOnOffState == 0 && loginCheck) {
					lbserverOnOff.setFont(new Font("맑은 고딕", Font.BOLD, 23));
					lbserverOnOffState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbserverOnOffState == 1 && loginCheck) {
					lbserverOnOff.setFont(new Font("맑은 고딕", Font.BOLD, 21));
					lbserverOnOffState = 0;
				}
			}
		});
		lbserverOnOff.setHorizontalAlignment(SwingConstants.CENTER);
		lbserverOnOff.setFont(new Font("맑은 고딕", Font.BOLD, 21));
		lbserverOnOff.setForeground(Color.LIGHT_GRAY);
		lbserverOnOff.setBackground(Color.WHITE);
		lbserverOnOff.setBounds(0, 10, 127, 60);

		tfId.setFont(fPlain20);
		tfId.setText("아이디");
		tfId.setForeground(Color.LIGHT_GRAY);
		tfId.setHorizontalAlignment(SwingConstants.LEFT);
		tfId.setBounds(600, 15, 130, 30);
		tfId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (tfId.getText().equals("아이디")) {
					tfId.setText("");
					tfId.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tfId.getText().length() == 0) {
					tfId.setText("아이디");
					tfId.setForeground(Color.LIGHT_GRAY);
				}
			}
		});

		tfPw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER ){
					loginOut();
				}
			}
		});
		tfPw.setFont(fPlain20);
		tfPw.setForeground(Color.LIGHT_GRAY);
		tfPw.setHorizontalAlignment(SwingConstants.LEFT);
		tfPw.setText("비밀번호");
		tfPw.setBounds(747, 15, 130, 30);
		tfPw.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfPw.getText().equals("비밀번호")) {
					tfPw.setText("");
					tfPw.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (tfPw.getText().length() == 0) {
					tfPw.setText("비밀번호");
					tfPw.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		
		btLogin.requestFocus();
		btLogin.setFont(fPlain20);
		btLogin.setBackground(Color.WHITE);
		btLogin.setBounds(899, 12, 135, 32);
		// 관리자 아이디 비밀번호 맞는지 확인 -> 맞으면 서버 켜짐
		btLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				loginOut();
			}
		});

		lbIcon.setIcon(new ImageIcon("icon.png"));
		lbIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lbIcon.setBounds(780, 0, 50, 50);
		lbIcon.setVisible(false);

		lbAdminId.setForeground(new Color(0, 0, 128));
		lbAdminId.setFont(fBold22);
		lbAdminId.setHorizontalAlignment(SwingConstants.LEFT);
		lbAdminId.setBounds(835, 14, 90, 27);
		lbAdminId.setVisible(false);

		// 기능 라벨 선택시 팬 교체 아래 기능은 로그인한 후에만 이용가능********************
		startPane.setBounds(127, 79, 930, 610);
		searchPane.setBounds(127, 79, 930, 610);
		requestPane.setBounds(127, 79, 930, 610);
		borrowPane.setBounds(127, 79, 930, 610);
		studentPane.setBounds(127, 79, 930, 610);
		setPanel(true,false,false,false,false);

		//왼쪽라벨
		setLabel(lbSearch);
		lbSearch.setBounds(0, 120, 127, 60);

		setLabel(lbRequest);
		lbRequest.setBounds(0, 182, 127, 60);

		setLabel(lbBorrow);
		lbBorrow.setBounds(0, 244, 127, 60);
		
		setLabel(lbManage);
		lbManage.setBounds(0, 306, 127, 60);

		// 이벤트 리스너는 나중에 따로 클래스로 해야 할듯 너무 길어짐
		// '장비관리'라벨 이벤트 리스너
		lbSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Vector<Object> res = null;
				if (loginCheck) {
					if (lbSearchState == 1) {
						
						setSelectedLabel(lbSearch);
						lbSearchState = 2;

						setLabel(lbRequest);
						lbRequestState = 0;
						
						setLabel(lbBorrow);
						lbBorrowState = 0;
						
						setLabel(lbManage);
						lbManageState = 0;
					}
					setPanel(false,true,false,false,false);
					res = DBEquip.getDBInstance().selectEquipAll(); //장비내용 불러오기
					searchPane.searTableModel.setDataVector(res, searchPane.title);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbSearchState == 0 && loginCheck) {
					setSelectedLabel(lbSearch);
					lbSearchState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbSearchState == 1 && loginCheck) {
					setLabel(lbSearch);
					lbSearchState = 0;
				}
			}
		});
		// '신청목록'라벨 이벤트 리스너
		lbRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector<Object> res = null;
				if (loginCheck) {
					if (lbRequestState == 1) {
						
						setLabel(lbSearch);
						lbSearchState = 0;
						
						setSelectedLabel(lbRequest);
						lbRequestState = 2;
						
						setLabel(lbBorrow);
						lbBorrowState = 0;
						
						setLabel(lbManage);
						lbManageState = 0;
					}
					setPanel(false,false,true,false,false);
					res = DBBorrow.getDbBInstance().selectRequAccAll(); //대여 리스트
					requestPane.requTableModel.setDataVector(res, requestPane.titleRequest);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbRequestState == 0 && loginCheck) {
					setSelectedLabel(lbRequest);
					lbRequestState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbRequestState == 1 && loginCheck) {
					setLabel(lbRequest);
					lbRequestState = 0;
				}
			}
		});
		// '대여목록'라벨 이벤트 리스너
		lbBorrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector<Object> res = null;
				if (loginCheck) {
					if (lbBorrowState == 1) {
						
						setLabel(lbSearch);
						lbSearchState = 0;
					
						setLabel(lbRequest);
						lbRequestState = 0;
						
						setSelectedLabel(lbBorrow);
						lbBorrowState = 2;

						setLabel(lbManage);
						lbManageState = 0;
					}
					setPanel(false,false,false,true,false);
					res = DBBorrow.getDbBInstance().selectComRejRetAll(); //대여 리스트
					borrowPane.borrowTableModel.setDataVector(res, borrowPane.titleBorrow);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbBorrowState == 0 && loginCheck) {
					setSelectedLabel(lbBorrow);
					lbBorrowState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbBorrowState == 1 && loginCheck) {
					setLabel(lbBorrow);
					lbBorrowState = 0;
				}
			}
		});

		// '학생조회'라벨 이벤트 리스너
		lbManage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector<Object> result = null;
				if (loginCheck) {
					if (lbManageState == 1) {

						setLabel(lbSearch);
						lbSearchState = 0;

						setLabel(lbRequest);
						lbRequestState = 0;

						setLabel(lbBorrow);
						lbBorrowState = 0;

						setSelectedLabel(lbManage);
						lbManageState = 2;
					}
					setPanel(false,false,false,false,true);
					result = DBStudent.getSDInstance()
							.selectStudentAll(); // 대여 리스트
					studentPane.sMTableModel.setDataVector(result,
							studentPane.title);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbManageState == 0 && loginCheck) {
					setSelectedLabel(lbManage);
					lbManageState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbManageState == 1 && loginCheck) {
					setLabel(lbManage);
					lbManageState = 0;
				}
			}
		});
		// ***************************************************************

		add(lbserverOnOff);
		add(tfId);
		add(tfPw);
		add(btLogin);
		add(lbIcon);
		add(lbAdminId);

		add(startPane);
		add(searchPane);
		add(requestPane);
		add(borrowPane);
		add(studentPane);
		
		add(lbSearch);
		add(lbRequest);
		add(lbBorrow);
		add(lbManage);

	}
	
	void loginOut(){
		if (btLogin.getText().equals("서버 켜기")) {
			admin.setAdminId(tfId.getText()); // 관리자 아이디 저장
			admin.setAdminPw(tfPw.getText()); // 관리자 비번 저장
			//디비에서 아이디 비밀번호 확인하기
			DBAdmin db = new DBAdmin();
			loginCheck = db.idPwCheck(admin.getAdminId(), admin.getAdminPw());
			if (loginCheck) {
				tfId.setVisible(false); // 아이디 텍스트필드 안보이게
				tfPw.setVisible(false); // 비번 텍스트필드 안보이게
				btLogin.setForeground(Color.LIGHT_GRAY);
				btLogin.setText("서버 끄기"); // 로그아웃으로 스트링 변경
				lbIcon.setVisible(true);
				lbAdminId.setText(admin.getAdminId());
				lbAdminId.setVisible(true);
				lbserverOnOff.setText("서버 ON");
				lbserverOnOff.setForeground(new Color(25, 25, 112));
			}else{
				JOptionPane.showConfirmDialog(null, "아이디와 비밀번호를 다시 확인하세요.", "불일치",
						JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				tfId.setText("아이디");
				tfPw.setText("비밀번호"); 
				tfId.setForeground(Color.LIGHT_GRAY);
				tfPw.setForeground(Color.LIGHT_GRAY);
			}
		} else if (btLogin.getText().equals("서버 끄기")) {
			int yesNo = JOptionPane.showConfirmDialog(null, "서버를 끝내시겠습니까?", "서버 종료",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (yesNo == 0) {
				loginCheck = false;
				btLogin.setForeground(Color.BLACK);
				btLogin.setText("로그인");
				System.exit(0);
			}
		}
	}
	
	private void setLabel(JLabel label){
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(Color.WHITE);
		label.setFont(fBold22);
		label.setForeground(Color.GRAY);
	}
	
	private void setSelectedLabel(JLabel label){
		label.setBackground(SystemColor.inactiveCaption);
		label.setForeground(Color.WHITE);
		label.setFont(fBold22);
	}
	
	private void setPanel(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5){
		startPane.setVisible(b1);
		searchPane.setVisible(b2);
		requestPane.setVisible(b3);
		borrowPane.setVisible(b4);
		studentPane.setVisible(b5);
	}
}
