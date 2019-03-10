package ml.webUtils.chessClient;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;

public class Field extends JFrame {
	/*static int SIZE = 80;
	static int xChessBoarder = 50;
	static int yChessBoarder = 50;
	static int ChessLabelDeep = 25;
	static int chessBkDeep = 35;*/
	Container cp;
	Font ChessLabelFont = new Font("Serif", Font.BOLD, 24);
	Color bk = new Color(132, 24, 0);
	// Color bk = new Color(84, 26, 0);

	public Field() {
		super("chess");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(1300, 800);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);

		cp = getContentPane();
		cp.setLayout(null);
		// Clock2 cl = new Clock2(cp, 180);
		//JPanel cl = new Clock2();
		JPanel cl = new ChessField();
		cl.setBounds(600, 100, 500, 500);
		add(cl);
		setVisible(true);
	}

}
