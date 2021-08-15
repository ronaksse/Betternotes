package LPTFOURVTWO.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import LPTFOURVTWO.Main;

public class DragNDropFrame implements MouseListener, MouseMotionListener {

	private static int posX = 0, posY = 0;
	public void mousePressed(MouseEvent e) {
		posX = e.getX();
		posY = e.getY();
	}

	public void mouseDragged(MouseEvent e) {
		Main.frame.setLocation(e.getXOnScreen()-posX, e.getYOnScreen()-posY);		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

