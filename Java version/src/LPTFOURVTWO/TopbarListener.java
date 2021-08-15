package LPTFOURVTWO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopbarListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Main.newfolder) {
			return;
		}
		
		if(e.getSource() == Main.newtextdocument) {
			return;
		}
		
		if(e.getSource() == Main.newwhiteboard) {
			return;
		}
		
		
		if(e.getSource() == Main.copy) {
			return;
		}
		
		if(e.getSource() == Main.paste) {
			return;
		}
		
		if(e.getSource() == Main.cut) {
			return;
		}
	}

}