package ServerPresentation;

import javax.swing.*;
import java.awt.*;

public class ServerMainFrame extends JFrame {

	private MainPane mainPane;

	public ServerMainFrame() { //프레임생성
		super("패기컴과");
		init();
	}
	
	private void init() { //메인팬 집어넣기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //아예 막아야 할듯 없애도 되고
		setBounds(100, 100, 1100, 765);
		mainPane = new MainPane();
		mainPane.setBackground(new Color(255, 255, 255));
		setContentPane(mainPane);
	}
	
	public static void main(String[] args) {
		ServerMainFrame frame = new ServerMainFrame();
		frame.setVisible(true);
	}
}
