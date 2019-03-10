package ml.webUtils.chessClient;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class ChessField extends JPanel{
	ChessFieldLabels labledChess;
	public ChessField() {
		setLayout(new GridLayout());
		this.setBackground(Color.RED); //TODO set Background
		this.setOpaque(true);
		labledChess = new ChessFieldLabels();
		add(labledChess);
		
	}

}
