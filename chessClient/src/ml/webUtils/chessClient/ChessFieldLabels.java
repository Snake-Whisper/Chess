package ml.webUtils.chessClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessFieldLabels extends JPanel{
	Squares chess;
	
	Font ChessLabelFont = new Font("Serif", Font.BOLD, 24);
	private GridBagConstraints CONSTRAINTS = new GridBagConstraints();

	public ChessFieldLabels() {
		
		setLayout(new GridBagLayout());
		chess = new Squares();
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
		CONSTRAINTS.gridwidth = 7;
		
		generateChessLabels();
		//test();
		
	}
	
	private void test() {
		CONSTRAINTS.gridx = 0;
		CONSTRAINTS.gridy = 8;
		CONSTRAINTS.gridwidth = 2;
		CONSTRAINTS.gridheight = 2; //TODO use setBackgound to get bk. Disable fill both -> select correct
		
		JLabel testLabel = new JLabel();
		testLabel.setOpaque(true);
		testLabel.setBackground(Color.red);
		
		add(testLabel, CONSTRAINTS);
	}

	private void generateChessLabels() {
		JLabel[] xLabels = new JLabel[8];
		String chars = "ABCDEFGH";
		
		CONSTRAINTS.gridwidth = 1;
		CONSTRAINTS.gridheight = 1;
		
		JLabel[] yLabels = new JLabel[9];
		
		for (int i = 0; i < xLabels.length; i++) {
			CONSTRAINTS.gridy = 0;
			CONSTRAINTS.gridx = i+1;
			CONSTRAINTS.insets = new Insets(0, 0, 5, 0);
			CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
			xLabels[i] = new JLabel();
			xLabels[i].setText(chars.charAt(i) + "");
			xLabels[i].setHorizontalAlignment(JLabel.CENTER);
			xLabels[i].setVerticalAlignment(JLabel.BOTTOM);
			xLabels[i].setFont(ChessLabelFont);
			//xLabels[i].setBackground(Color.red);
			//xLabels[i].setOpaque(true);
			add(xLabels[i], CONSTRAINTS);

			CONSTRAINTS.gridx = 0;
			CONSTRAINTS.gridy = i+1;
			CONSTRAINTS.insets = new Insets(0, 0, 0, 5);
			CONSTRAINTS.fill = GridBagConstraints.VERTICAL;
			yLabels[i] = new JLabel();
			yLabels[i].setText(i + 1 + "");
			yLabels[i].setHorizontalAlignment(JLabel.RIGHT);
			yLabels[i].setVerticalAlignment(JLabel.CENTER);
			yLabels[i].setFont(ChessLabelFont);
			//yLabels[i].setBackground(Color.red);
			//yLabels[i].setOpaque(true);
			add(yLabels[i], CONSTRAINTS);

		}
	}

}
