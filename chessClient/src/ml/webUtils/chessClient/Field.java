package ml.webUtils.chessClient;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;

public class Field extends JFrame {
	Container cp;
	Font ChessLabelFont = new Font("Serif", Font.BOLD, 24);
	Color bk = new Color(132, 24, 0);
	private ChessField field;
	// Color bk = new Color(84, 26, 0);
	
	ImageIcon wRook   = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_rlt45.png"));
	ImageIcon wKnight = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_nlt45.png"));
	ImageIcon wBishop = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_blt45.png"));
	ImageIcon wQueen  = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_qlt45.png"));
	ImageIcon wKing   = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_klt45.png"));
	ImageIcon wPawn   = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_plt45.png"));

	ImageIcon bRook   = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_rdt45.png"));
	ImageIcon bKnight = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_ndt45.png"));
	ImageIcon bBishop = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_bdt45.png"));
	ImageIcon bQueen  = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_qdt45.png"));
	ImageIcon bKing   = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_kdt45.png"));
	ImageIcon bPawn   = new ImageIcon(ml.webUtils.chessClient.Field.class.getResource("/images/Chess_pdt45.png"));

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
		ChessField field = new ChessField();
		//setPiece(0, 0, wKing);
		field.setBounds(600, 100, 500, 500);
		add(field);
		setVisible(true);
	}
	
	/*private void setPiece(int x, int y, ImageIcon img ) {
		
		//field.labledChess.chess.squares[x][y].setBackground(Color.RED);
		ml.webUtils.chessClient.Squares.squares[x][y].setIcon(img);
		
	}*/

}
