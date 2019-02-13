package ml.webUtils.chessClient;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class ChessField extends JPanel{

	public ChessField() {
		setLayout(new GridLayout());
		this.setForeground(Color.red); //TODO set Background
		this.setOpaque(true);
		JPanel labledChess = new ChessFieldLabels();
		add(labledChess);
		
	}

}
