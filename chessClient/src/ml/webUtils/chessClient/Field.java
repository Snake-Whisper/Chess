package ml.webUtils.chessClient;

import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

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
	
	ImageIcon noPiece = new ImageIcon("");

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
		field.setBounds(600, 100, 500, 500);
		add(field);
		setVisible(true);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		groundPositions();
		
	}
	
	private void setPiece(int x, int y, ImageIcon img ) {		
		ml.webUtils.chessClient.Squares.squares[x][y].setIcon(img); //price for "higher" programming language!
		/*ml.webUtils.chessClient.Squares.squares[x][y].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Registered Mouse Click at"+e.getPoint());
			}
		});*/
	}
	
	private void delPiece(int x, int y) {
		ml.webUtils.chessClient.Squares.squares[x][y].setIcon(noPiece);
	}
	
	private void clearField() {
		for (int x=0; x<8; x++) {
			for (int y=0; y<8; y++) {
				delPiece(x,y);
			}
		}
	}
	
	private void groundPositions() {
		clearField();
		for (int x = 0; x < 8; x++) { // Bauern
			setPiece(x, 1, wPawn);
			setPiece(x, 6, bPawn);
		}

		setPiece(0, 0, wRook);
		setPiece(7, 0, wRook);
		setPiece(1, 0, wKnight);
		setPiece(6, 0, wKnight);
		setPiece(2, 0, wBishop);
		setPiece(5, 0, wBishop);
		setPiece(3, 0, wQueen);
		setPiece(4, 0, wKing);

		setPiece(0, 7, bRook);
		setPiece(7, 7, bRook);
		setPiece(1, 7, bKnight);
		setPiece(6, 7, bKnight);
		setPiece(2, 7, bBishop);
		setPiece(5, 7, bBishop);
		setPiece(3, 7, bQueen);
		setPiece(4, 7, bKing);
	}

}
