package ml.webUtils.chessClient;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class MagicSelector extends JDialog {
	private int selected = -1;

	public MagicSelector(boolean white) {
		setTitle("Please select a piece");
		setResizable(false);
		setLayout(null);
		setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setSize(380, 150);
		JLabel[] pieces = new JLabel[4];
		if (white) {
			pieces[0] = new JLabel(ml.webUtils.chessClient.Field.wBishop);

			pieces[1] = new JLabel(ml.webUtils.chessClient.Field.wKnight);

			pieces[2] = new JLabel(ml.webUtils.chessClient.Field.wQueen);

			pieces[3] = new JLabel(ml.webUtils.chessClient.Field.wRook);
		} else {
			pieces[0] = new JLabel(ml.webUtils.chessClient.Field.bBishop);

			pieces[1] = new JLabel(ml.webUtils.chessClient.Field.bKnight);

			pieces[2] = new JLabel(ml.webUtils.chessClient.Field.bQueen);

			pieces[3] = new JLabel(ml.webUtils.chessClient.Field.bRook);
		}
		Border border = BorderFactory.createLineBorder(Color.green, 5);

		for (int i = 0; i < pieces.length; i++) {
			pieces[i].setBounds(100 * i + 10, 10, 50, 50);
			pieces[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					((JComponent) e.getSource()).setBorder(border);
					for (int i = 0; i < pieces.length; i++) {
						if (pieces[i].equals(e.getSource())) {
							selected = i;
						} else {
							pieces[i].setBorder(null);
						}
					}

				}
			});
			this.add(pieces[i]);
		}
		JButton ok = new JButton("ok");
		ok.setBounds((this.getWidth() - 50) / 2, this.getHeight() - 60, 50, 20);// (framesize - buttonsize) / 2
		ok.addActionListener(e -> {
			if (selected > -1 && selected < 5) {
				sendMagic(selected); // use enum pieces!!!
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Pleasde select a piece", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		this.add(ok);

		/*
		 * for (int i = 0; i<pieces.length; i++) { add(pieces[i]); }
		 */

	}

	public void sendMagic(int piece) { // override
		System.out.println("Send Magic" + piece);
	}

}
