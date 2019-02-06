package chessClient;

import java.awt.*;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

//import com.sun.xml.internal.ws.api.server.Container;

public class Field extends JFrame {
	static int SIZE = 80;
	static int xChessBoarder = 50;
	static int yChessBoarder = 50;
	static int ChessLabelDeep = 25;
	static int chessBkDeep = 35;
	Container cp;
	Font ChessLabelFont = new Font("Serif",Font.BOLD, 24);
	Color bk = new Color(132, 24, 0);
	//Color bk = new Color(84, 26, 0);
	
	public Field() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(1300, 800);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);
		setTitle("chess");

		cp = getContentPane();
		cp.setLayout(null);
		makeSquares();
		generateChessLabels();
		makeChessBackground();
		Clock cl = new Clock(cp, 180);
		setVisible(true);
	}
	
	void makeSquares() {
		JLabel[][] squares = new JLabel[8][8];
		boolean black = true;
		for (int x=0; x<8; x++) {
			for (int y=0; y<8; y++) {
				squares[x][y] = new JLabel();
				squares[x][y].setBounds(x*SIZE+xChessBoarder, y*SIZE+yChessBoarder, SIZE, SIZE);
				
				if (black) {
					squares[x][y].setBackground(Color.black);
					black = false;
				}				
				else {
					squares[x][y].setBackground(Color.white);
					black = true;
				}

				squares[x][y].setOpaque(true);
				cp.add(squares[x][y]);
			}
			
			if (black) {
				black = false;
			}				
			else {
				black = true;
			}
		}
		
		
	}
	
	void generateChessLabels() {
		JLabel[] xLabels = new JLabel[8];
		String chars = "ABCDEFGH";
		JLabel[] yLabels = new JLabel[8];
		for (int i=0; i<8; i++) {
			xLabels[i] = new JLabel();
			xLabels[i].setBounds(i*SIZE+xChessBoarder, yChessBoarder-ChessLabelDeep-10, SIZE, ChessLabelDeep);
			//xLabels[i].setBounds(0, yChessBoarder-30, SIZE, 30);
			//xLabels[i].setBackground(Color.green);
			//xLabels[i].setOpaque(true);
			xLabels[i].setText(chars.charAt(i)+"");
			xLabels[i].setHorizontalAlignment(JLabel.CENTER);
			xLabels[i].setVerticalAlignment(JLabel.TOP);
			xLabels[i].setFont(ChessLabelFont);
			//xLabels[i].setForeground(Color.white);
			cp.add(xLabels[i]);
			
			yLabels[i] = new JLabel();
			yLabels[i].setBounds(xChessBoarder-ChessLabelDeep, i*SIZE+yChessBoarder, ChessLabelDeep, SIZE);
			//yLabels[i].setBounds(0, yChessBoarder-30, SIZE, 30);
			//yLabels[i].setBackground(bk);
			//yLabels[i].setOpaque(true);
			yLabels[i].setText(i+1+"");
			yLabels[i].setHorizontalAlignment(JLabel.LEFT);
			yLabels[i].setVerticalAlignment(JLabel.CENTER);
			yLabels[i].setFont(ChessLabelFont);
			//yLabels[i].setForeground(Color.white);
			cp.add(yLabels[i]);
			
		}
	}
	
	void makeChessBackground() {
		JLabel chessBK = new JLabel();
		chessBK.setBounds(xChessBoarder-chessBkDeep, yChessBoarder-chessBkDeep, SIZE*8+chessBkDeep*2, SIZE*8+chessBkDeep*2);
		chessBK.setBackground(bk);
		chessBK.setOpaque(true);
		cp.add(chessBK);
	}
	
	
}
