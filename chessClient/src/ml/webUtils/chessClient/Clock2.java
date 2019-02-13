package ml.webUtils.chessClient;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Clock2 extends JPanel {

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0, 0, 20, 20);
		repaint(1000);
	}
}