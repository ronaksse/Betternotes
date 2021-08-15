package LPTFOURVTWO.Utils;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import LPTFOURVTWO.Main;

public class SliderChangeListener implements ChangeListener{

	@Override
	public void stateChanged(ChangeEvent e) {
		if(Main.slider.getValue() > 0) {
			int width = (Main.image.getWidth()*(Main.slider.getValue()))/100;
			int height = (Main.image.getHeight()*(Main.slider.getValue()))/100;
			Image resizeimage = Main.image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			Main.label.setIcon(new ImageIcon(resizeimage));
			Main.imagescrollPane.setLocation(0, Main.slider.getHeight());
			Main.BetterNotes.setText("  Better Notes - " + Main.selected.file.getName() + " @ " + Main.slider.getValue() + "%");
		}
	}
}
