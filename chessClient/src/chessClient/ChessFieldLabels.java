package chessClient;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessFieldLabels extends JPanel{
	
	Font ChessLabelFont = new Font("Serif", Font.BOLD, 24);
	GridBagConstraints CONSTRAINTS;

	public ChessFieldLabels() {
		
		setLayout(new GridBagLayout());
		JPanel chess = new Squares();
		GridBagConstraints CONSTRAINTS = new GridBagConstraints();
		CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
		CONSTRAINTS.anchor = GridBagConstraints.FIRST_LINE_START;
		CONSTRAINTS.weightx = 0.5;
		CONSTRAINTS.weighty = 0.5;
		CONSTRAINTS.gridx = 1;
		CONSTRAINTS.gridy = 1;
		CONSTRAINTS.gridwidth = 8;
		CONSTRAINTS.gridheight = 8;
		CONSTRAINTS.ipady = 0;
		CONSTRAINTS.insets = new Insets(0, 0, 0, 0);
		CONSTRAINTS.fill = GridBagConstraints.BOTH;
		add(chess, CONSTRAINTS);
		
	}
	
	void generateChessLabels() {
		JLabel[] xLabels = new JLabel[8];
		String chars = "ABCDEFGH";
		
		CONSTRAINTS.gridwidth = 1;
		CONSTRAINTS.gridheight = 1;
		
		JLabel[] yLabels = new JLabel[8];
		for (int i = 0; i < 8; i++) {
			xLabels[i] = new JLabel();
			xLabels[i].setText(chars.charAt(i) + "");
			xLabels[i].setHorizontalAlignment(JLabel.CENTER);
			xLabels[i].setVerticalAlignment(JLabel.TOP);
			xLabels[i].setFont(ChessLabelFont);

			yLabels[i] = new JLabel();
			yLabels[i].setText(i + 1 + "");
			yLabels[i].setHorizontalAlignment(JLabel.LEFT);
			yLabels[i].setVerticalAlignment(JLabel.CENTER);
			yLabels[i].setFont(ChessLabelFont);

		}
	}

}
