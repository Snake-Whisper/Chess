package chessClient;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class Clock extends JPanel{
	
	int chessBoarder = 50;

	Clock(Container cp, int y){
		JLabel clock = new JLabel();
		clock.setBounds(8*Field.SIZE+Field.xChessBoarder+Field.chessBkDeep+chessBoarder,
				y, (int) (Field.SIZE*1.5), (int) (Field.SIZE*1.5));
		clock.setBackground(Color.blue);
		clock.setOpaque(true);
		
		cp.add(clock);
		
		
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawOval(8*Field.SIZE*, y, width, height);
	}
}
