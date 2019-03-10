package ml.webUtils.chessClient;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Squares extends JPanel{
	
	protected JLabel[][] squares = new JLabel[8][8];

	public Squares() {
		setLayout(new GridLayout(8, 8));
		makeSquares();		
	}
	
	
	
	private void makeSquares() {
		
		boolean black = true;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				squares[x][y] = new JLabel();
				//test start
				//ImageIcon img = new ImageIcon(wQueen);
				//test stop
				if (black) {
					squares[x][y].setBackground(Color.darkGray);
					//squares[x][y].setIcon(img);
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
