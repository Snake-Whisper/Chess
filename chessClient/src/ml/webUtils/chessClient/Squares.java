package ml.webUtils.chessClient;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Squares extends JPanel{
	
	static protected JLabel[][] squares = new JLabel[8][8]; //danger 2 use static, but u need to set icon
	
	
	

	public Squares() {
		setLayout(new GridLayout(8, 8));
		makeSquares();
		this.addMouseListener(new MouseAdapter() {			
			@Override
			public void mousePressed(MouseEvent e) {
				Field.registerFeldClick(e.getPoint().x/(getSize().width/8), e.getPoint().y/(getSize().height/8));
			}			
		});
	}
	
	
	
	
	
	private void makeSquares() {
		
		boolean black = true;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				squares[x][y] = new JLabel();
				//test start
				//ImageIcon img = new ImageIcon(wQueen);
				//test stop
				if (black) {
					squares[x][y].setBackground(Color.darkGray);
					squares[x][y].setIcon(new ImageIcon(""));
					squares[x][y].setHorizontalAlignment(JLabel.CENTER);
					squares[x][y].setVerticalAlignment(JLabel.CENTER);
					black = false;
				} else {
					squares[x][y].setBackground(Color.white);
					squares[x][y].setHorizontalAlignment(JLabel.CENTER);
					squares[x][y].setIcon(new ImageIcon(""));
					squares[x][y].setVerticalAlignment(JLabel.CENTER);
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
