package ServerPresentation;

import java.awt.SystemColor;
import javax.swing.*;

public class StartPane extends JPanel {
	private JLabel lbMainImage = new JLabel();

	public StartPane() {
		setBackground(SystemColor.inactiveCaption);
		setLayout(null);
		lbMainImage.setBounds(249, 80, 432, 484);
		lbMainImage.setIcon(new ImageIcon("cs.png"));
		add(lbMainImage);		
	}

}
