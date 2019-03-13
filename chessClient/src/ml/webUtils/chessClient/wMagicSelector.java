package ml.webUtils.chessClient;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class wMagicSelector extends JDialog{

	public wMagicSelector() {
		setTitle("Please select a piece");
		setResizable(true);
		//setLayout(new BorderLayout());
		JLabel[] pieces = new JLabel[4];
		pieces[0] = new JLabel(ml.webUtils.chessClient.Field.wBishop);
		pieces[0].setPreferredSize(new Dimension(20, 10));
		add(pieces[0]);
		pieces[1] = new JLabel(ml.webUtils.chessClient.Field.wKnight);
		pieces[1].setPreferredSize(new Dimension(20, 10));
		add(pieces[1]);
		pieces[2] = new JLabel(ml.webUtils.chessClient.Field.wQueen);
		pieces[2].setBounds(x, y, width, height);(new Dimension(20, 10));
		add(pieces[2]);
		pieces[3] = new JLabel(ml.webUtils.chessClient.Field.wRook);
		pieces[3].setPreferredSize(new Dimension(20, 10));
		add(pieces[3]);
	
	/*for (int i = 0; i<pieces.length; i++) {
		add(pieces[i]);
	}*/
		
		pack();
		
		
	}

}
