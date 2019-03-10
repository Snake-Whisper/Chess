package ml.webUtils.chessClient;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Squares extends JPanel{

	public Squares() {
		setLayout(new GridLayout(8, 8));
		makeSquares();		
	}
	
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
	private void makeSquares() {
		JLabel[][] squares = new JLabel[8][8];
		boolean black = true;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				squares[x][y] = new JLabel();
				//test start
				ImageIcon img = new ImageIcon(wQueen);
				//test stop
				if (black) {
					squares[x][y].setBackground(Color.darkGray);
					squares[x][y].setIcon(img);
					squares[x][y].setAlignmentX(JLabel.CENTER);
					squares[x][y].setAlignmentY(JLabel.CENTER);
					black = false;
				} else {
					squares[x][y].setBackground(Color.white);
					black = true;
				}

				squares[x][y].setOpaque(true);
				add(squares[x][y]);
			}

			if (black) {
				black = false;
			} else {
				black = true;
			}
		}

	}

}
