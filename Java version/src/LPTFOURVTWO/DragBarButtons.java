package LPTFOURVTWO;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class DragBarButtons implements ActionListener, MouseListener{

	boolean fullscreen = false;

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == Main.minimize) {
			Main.minimize.setBackground(new Color(218, 221, 225));
			return;
		}

		if(e.getSource() == Main.fullscreen) {
			Main.fullscreen.setBackground(new Color(218, 221, 225));
			return;
		}

		if(e.getSource() == Main.close) {
			Main.close.setBackground(new Color(221, 81, 69));
			return;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == Main.minimize) {
			Main.minimize.setBackground(new Color(227, 229, 232));
			return;
		}

		if(e.getSource() == Main.fullscreen) {
			Main.fullscreen.setBackground(new Color(227, 229, 232));
			return;
		}

		if(e.getSource() == Main.close) {
			Main.close.setBackground(new Color(227, 229, 232));
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Main.minimize) {
			Main.frame.setState(JFrame.ICONIFIED);
			return;
		}

		if(e.getSource() == Main.fullscreen) {
			if(fullscreen) {
				Main.frame.setSize(1280, 720);
				fullscreen = false;
			}else {
				Main.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				fullscreen = true;
			}
			return;
		}

		if(e.getSource() == Main.close) {
			System.exit(0);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
