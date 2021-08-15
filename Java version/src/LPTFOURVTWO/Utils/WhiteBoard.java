package LPTFOURVTWO.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class WhiteBoard {

	private static JFrame frame = new JFrame("Whiteboard");

	public static void drawPane() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setIconImage(FileStorage.whiteboard);
				frame.setSize(320, 180);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.add(new DrawPane());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	@SuppressWarnings("serial")
	public static class DrawPane extends JPanel {

		private List<List<Point>> points;

		public DrawPane() {

			setBackground(new Color(255, 255, 255));
			points = new ArrayList<>(25);
			MouseAdapter ma = new MouseAdapter() {

				private List<Point> path;

				@Override
				public void mousePressed(MouseEvent e) {
					path = new ArrayList<>(25);
					path.add(e.getPoint());

					points.add(path);
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					Point dragPoint = e.getPoint();
					path.add(dragPoint);
					repaint();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					path = null;
				}

			};

			addMouseListener(ma);
			addMouseMotionListener(ma);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(frame.getWidth(), frame.getHeight());
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			for (List<Point> path : points) {
				Point from = null;
				for (Point p : path) {
					if (from != null) {
						g2d.drawLine(from.x, from.y, p.x, p.y);
					}
					from = p;
				}
			}
			g2d.dispose();
		}

	}

}