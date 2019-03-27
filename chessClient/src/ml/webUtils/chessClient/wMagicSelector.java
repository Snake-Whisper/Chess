package ml.webUtils.chessClient;

import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;import javax.swing.JOptionPane;


public class wMagicSelector extends JDialog{
	private int selected = 0;
	public wMagicSelector() {
		setTitle("Please select a piece");
		setResizable(false);
		setLayout(null);
		this.setSize(380, 150);
		JLabel[] pieces = new JLabel[4];
		pieces[0] = new JLabel(ml.webUtils.chessClient.Field.wBishop);
		
		pieces[1] = new JLabel(ml.webUtils.chessClient.Field.wKnight);
		
		pieces[2] = new JLabel(ml.webUtils.chessClient.Field.wQueen);
		
		pieces[3] = new JLabel(ml.webUtils.chessClient.Field.wRook);
		
		for (int i=0; i<pieces.length; i++) {
			pieces[i].setBounds(100*i,10,50,50);
			this.add(pieces[i]);
		}
		JButton ok = new JButton("ok");
		ok.setBounds((this.getWidth() - 50) /2, this.getHeight() - 60 , 50, 20);// (framesize - buttonsize) / 2
		ok.addActionListener(e -> {
			if (selected > 0 && selected < 5) {
				sendMagic(selected); //use enum pieces!!!
			}
			else {
				JOptionPane.showMessageDialog(this, "Pleasde select a piece", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		this.add(ok);
		
	
	/*for (int i = 0; i<pieces.length; i++) {
		add(pieces[i]);
	}*/
		
		
	}
	
	public void sendMagic(int piece) { //override
		System.out.println("Send Magic" + piece);
	}

}
