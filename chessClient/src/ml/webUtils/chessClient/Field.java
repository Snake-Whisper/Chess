package ml.webUtils.chessClient;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;

public class Field extends JFrame {
	Container cp;
	Font ChessLabelFont = new Font("Serif", Font.BOLD, 24);
	Color bk = new Color(132, 24, 0);
	// Color bk = new Color(84, 26, 0);
	
	java.net.URL wRook   = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_rlt45.png");
	java.net.URL wKnight = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_nlt45.png");
	java.net.URL wBishop = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_blt45.png");
	java.net.URL wQueen  = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_qlt45.png");
	java.net.URL wKing   = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_klt45.png");
	java.net.URL wPawn   = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_plt45.png");
	
	java.net.URL bRook   = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_rdt45.png");
	java.net.URL bKnight = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_ndt45.png");
	java.net.URL bBishop = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_bdt45.png");
	java.net.URL bQueen  = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_qdt45.png");
	java.net.URL bKing   = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_kdt45.png");
	java.net.URL bPawn   = ml.webUtils.chessClient.Squares.class.getResource("/images/Chess_pdt45.png");

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
