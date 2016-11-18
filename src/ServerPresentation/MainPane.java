package ServerPresentation;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ServerStorage.DBAdmin;
import ServerStorage.DBBorrow;
import ServerStorage.DBEquip;

public class MainPane extends JPanel {
	// 로그인 전
	JLabel lbserverOnOff;
	JTextField tfId;
	JTextField tfPw;
	JButton btLogin; // 로그인아웃 같은 버튼으로 사용
	// 로그인 후
	JLabel lbIcon; // 로그인후 아이콘
	JLabel lbAdminId;// 로그인후 아이디만 보여줌
	// 기능
	JLabel lbSearch;
	JLabel lbRequest;
	JLabel lbRental;

	StartPane startPane;

	String adminId; // 관리자 아이디
	String adminPw; // 관리자 비밀번호

	// 라벨 상태에 따라 라벨백그라운드 색바꿔주려고
	int lbSearchState = 0; // 0-exit 1-enter 2-click
	int lbRequestState = 0; // 0-exit 1-enter 2-click
	int lbRentalState = 0; // 0-exit 1-enter 2-click

	// 로그인한 상태 체크 로그인 안되어있을시 기능작동 안함
	boolean loginCheck = false;

	public MainPane() {
		init();
	}

	private void init() {
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// 로그인 전
		lbserverOnOff = new JLabel("서버OFF");
		lbserverOnOff.setHorizontalAlignment(SwingConstants.CENTER);
		lbserverOnOff.setFont(new Font("Dialog", Font.BOLD, 21));
		lbserverOnOff.setForeground(Color.LIGHT_GRAY);
		lbserverOnOff.setBackground(Color.WHITE);
		lbserverOnOff.setBounds(0, 10, 127, 60);

		tfId = new JTextField(10);
		tfId.setFont(new Font("굴림", Font.PLAIN, 19));
		tfId.setText("아이디");
		tfId.setForeground(Color.LIGHT_GRAY);
		tfId.setHorizontalAlignment(SwingConstants.LEFT);
		tfId.setBounds(643, 15, 130, 30);
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

		tfPw = new JTextField(10);
		tfPw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER ){
					loginOut();
				}
			}
		});
		tfPw.setFont(new Font("굴림", Font.PLAIN, 19));
		tfPw.setForeground(Color.LIGHT_GRAY);
		tfPw.setHorizontalAlignment(SwingConstants.LEFT);
		tfPw.setText("비밀번호");
		tfPw.setBounds(790, 15, 130, 30);
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

		btLogin = new JButton("서버 켜기");
		btLogin.requestFocus();
		btLogin.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		btLogin.setBackground(Color.WHITE);
		btLogin.setBounds(935, 15, 120, 30);
		// 관리자 아이디 비밀번호 맞는지 확인 -> 맞으면 서버 켜짐
		btLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				loginOut();
			}
		});

		lbIcon = new JLabel();
		lbIcon.setIcon(new ImageIcon("icon.png"));
		lbIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lbIcon.setBounds(805, 0, 50, 50);
		lbIcon.setVisible(false);

		lbAdminId = new JLabel();
		lbAdminId.setForeground(new Color(0, 0, 128));
		lbAdminId.setFont(new Font("나눔고딕", Font.BOLD, 19));
		lbAdminId.setHorizontalAlignment(SwingConstants.LEFT);
		lbAdminId.setBounds(860, 14, 90, 27);
		lbAdminId.setVisible(false);

		// 기능 라벨 선택시 팬 교체 아래 기능은 로그인한 후에만 이용가능*********************
		startPane = new StartPane();
		startPane.setBounds(127, 79, 930, 610);
		startPane.setVisible(true);

		//searchPane 싱글톤임
		SearchPane.getInstance().setBounds(127, 79, 930, 610);
		SearchPane.getInstance().setVisible(false);

		RequestPane.getRPInstance().setBounds(127, 79, 930, 610);
		RequestPane.getRPInstance().setVisible(false);

		BorrowPane.getRPInstance().setBounds(127, 79, 930, 610);
		BorrowPane.getRPInstance().setVisible(false);

		lbSearch = new JLabel("장비조회");
		lbSearch.setForeground(Color.GRAY);
		lbSearch.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lbSearch.setOpaque(true);
		lbSearch.setBackground(Color.WHITE);
		lbSearch.setBounds(0, 120, 127, 60);

		lbRequest = new JLabel("신청목록");
		lbRequest.setForeground(Color.GRAY);
		lbRequest.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbRequest.setHorizontalAlignment(SwingConstants.CENTER);
		lbRequest.setOpaque(true);
		lbRequest.setBackground(Color.WHITE);
		lbRequest.setBounds(0, 182, 127, 60);

		lbRental = new JLabel("대여목록");
		lbRental.setForeground(Color.GRAY);
		lbRental.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbRental.setHorizontalAlignment(SwingConstants.CENTER);
		lbRental.setOpaque(true);
		lbRental.setBackground(Color.WHITE);
		lbRental.setBounds(0, 244, 127, 60);

		// 이벤트 리스너는 나중에 따로 클래스로 해야 할듯 너무 길어짐
		// '장비관리'라벨 이벤트 리스너
		lbSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (loginCheck) {
					if (lbSearchState == 1) {
						lbSearch.setBackground(SystemColor.inactiveCaption);
						lbSearch.setForeground(Color.WHITE);
						lbSearch.setFont(new Font("맑은 고딕", Font.BOLD, 22));
						lbSearchState = 2;

						lbRequest.setBackground(new Color(255, 255, 255));
						lbRequest.setForeground(Color.GRAY);
						lbRequest.setFont(new Font("맑은 고딕", Font.BOLD, 20));
						lbRequestState = 0;
						lbRental.setBackground(new Color(255, 255, 255));
						lbRental.setForeground(Color.GRAY);
						lbRental.setFont(new Font("맑은 고딕", Font.BOLD, 20));
						lbRentalState = 0;
					}
					startPane.setVisible(false);
					SearchPane.getInstance().setVisible(true);
					RequestPane.getRPInstance().setVisible(false);
					BorrowPane.getRPInstance().setVisible(false);
					Vector result = DBEquip.getDBInstance().selectEquipAll(); //장비내용 불러오기
					SearchPane.getInstance().defaultTableModel.setDataVector(result, SearchPane.getInstance().title);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbSearchState == 0 && loginCheck) {
					lbSearch.setBackground(SystemColor.inactiveCaption);
					lbSearch.setForeground(Color.WHITE);
					lbSearch.setFont(new Font("맑은 고딕", Font.BOLD, 22));
					lbSearchState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbSearchState == 1 && loginCheck) {
					lbSearch.setBackground(Color.WHITE);
					lbSearch.setForeground(Color.GRAY);
					lbSearch.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					lbSearchState = 0;
				}
			}
		});
		// '신청목록'라벨 이벤트 리스너
		lbRequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (loginCheck) {
					if (lbRequestState == 1) {
						lbRequest.setBackground(SystemColor.inactiveCaption);
						lbRequest.setForeground(Color.WHITE);
						lbRequest.setFont(new Font("맑은 고딕", Font.BOLD, 22));
						lbRequestState = 2;

						lbSearch.setBackground(new Color(255, 255, 255));
						lbSearch.setForeground(Color.GRAY);
						lbSearch.setFont(new Font("맑은 고딕", Font.BOLD, 20));
						lbSearchState = 0;
						lbRental.setBackground(new Color(255, 255, 255));
						lbRental.setForeground(Color.GRAY);
						lbRental.setFont(new Font("맑은 고딕", Font.BOLD, 20));
						lbRentalState = 0;
					}
					startPane.setVisible(false);
					SearchPane.getInstance().setVisible(false);
					RequestPane.getRPInstance().setVisible(true);
					BorrowPane.getRPInstance().setVisible(false);
					Vector result = DBBorrow.getDbBInstance().selectRequAccAll(); //대여 리스트
					RequestPane.getRPInstance().requestTableModel.setDataVector(result, RequestPane.getRPInstance().titleRequest);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbRequestState == 0 && loginCheck) {
					lbRequest.setBackground(SystemColor.inactiveCaption);
					lbRequest.setForeground(Color.WHITE);
					lbRequest.setFont(new Font("맑은 고딕", Font.BOLD, 22));
					lbRequestState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbRequestState == 1 && loginCheck) {
					lbRequest.setBackground(Color.WHITE);
					lbRequest.setForeground(Color.GRAY);
					lbRequest.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					lbRequestState = 0;
				}
			}
		});
		// '대여목록'라벨 이벤트 리스너
		lbRental.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (loginCheck) {
					if (lbRentalState == 1) {
						lbRental.setBackground(SystemColor.inactiveCaption);
						lbRental.setForeground(Color.WHITE);
						lbRental.setFont(new Font("맑은 고딕", Font.BOLD, 22));
						lbRentalState = 2;

						lbSearch.setBackground(new Color(255, 255, 255));
						lbSearch.setForeground(Color.GRAY);
						lbSearch.setFont(new Font("맑은 고딕", Font.BOLD, 20));
						lbSearchState = 0;
						lbRequest.setBackground(new Color(255, 255, 255));
						lbRequest.setForeground(Color.GRAY);
						lbRequest.setFont(new Font("맑은 고딕", Font.BOLD, 20));
						lbRequestState = 0;
					}
					startPane.setVisible(false);
					SearchPane.getInstance().setVisible(false);
					RequestPane.getRPInstance().setVisible(false);
					BorrowPane.getRPInstance().setVisible(true);
					Vector result = DBBorrow.getDbBInstance().selectComRejRetAll(); //대여 리스트
					BorrowPane.getRPInstance().borrowTableModel.setDataVector(result, BorrowPane.getRPInstance().titleBorrow);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (lbRentalState == 0 && loginCheck) {
					lbRental.setBackground(SystemColor.inactiveCaption);
					lbRental.setForeground(Color.WHITE);
					lbRental.setFont(new Font("맑은 고딕", Font.BOLD, 22));
					lbRentalState = 1;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lbRentalState == 1 && loginCheck) {
					lbRental.setBackground(new Color(255, 255, 255));
					lbRental.setForeground(Color.GRAY);
					lbRental.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					lbRentalState = 0;
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
		add(SearchPane.getInstance());
		add(RequestPane.getRPInstance());
		add(BorrowPane.getRPInstance());
		add(lbSearch);
		add(lbRequest);
		add(lbRental);

	}
	
	void loginOut(){
		if (btLogin.getText().equals("서버 켜기")) {
			adminId = tfId.getText(); // 관리자 아이디 저장
			adminPw = tfPw.getText(); // 관리자 비번 저장
			//디비에서 아이디 비밀번호 확인하기
			DBAdmin db = new DBAdmin();
			loginCheck = db.idPwCheck(adminId,adminPw);
			if (loginCheck) {
				tfId.setVisible(false); // 아이디 텍스트필드 안보이게
				tfPw.setVisible(false); // 비번 텍스트필드 안보이게
				btLogin.setForeground(Color.LIGHT_GRAY);
				btLogin.setText("서버 끄기"); // 로그아웃으로 스트링 변경
				lbIcon.setVisible(true);
				lbAdminId.setText(adminId);
				lbAdminId.setVisible(true);
				lbserverOnOff.setText("서버 ON");
				lbserverOnOff.setForeground(new Color(25, 25, 112));
			}else{
				JOptionPane.showConfirmDialog(null, "아이디와 비밀번호를 다시 확인하세요.", "CHECK",
						JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				tfId.setText("아이디");
				tfPw.setText("비밀번호"); 
				tfId.setForeground(Color.LIGHT_GRAY);
				tfPw.setForeground(Color.LIGHT_GRAY);
			}
		} else if (btLogin.getText().equals("서버 끄기")) {
			int yesNo = JOptionPane.showConfirmDialog(null, "서버를 끝내시겠습니까?", "REALLY?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (yesNo == 0) {
				loginCheck = false;
				btLogin.setForeground(Color.BLACK);
				btLogin.setText("로그인");
				System.exit(0);
			}
		}
	}
}
