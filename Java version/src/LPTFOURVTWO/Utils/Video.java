package LPTFOURVTWO.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import LPTFOURVTWO.Main;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

@SuppressWarnings("serial")
public class Video extends JFrame {
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private JButton playButton;
	private boolean playing = false;

	public Video(String title) {
		super(title);
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();		
	}

	public void initialize() {
		this.setIconImage(new ImageIcon(Main.class.getResource("BetterNotesIcon.png")).getImage());
		this.setBounds(100, 100, 600, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
			}
		});   
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());   	 
		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

		JPanel controlsPane = new JPanel();
		playButton = new JButton("Play");
		playButton.setBackground(new Color(200, 200, 200));
		playButton.setBorderPainted(false);
		playButton.setFocusable(false);
		controlsPane.add(playButton);    	
		contentPane.add(controlsPane, BorderLayout.SOUTH);
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playing) {
					mediaPlayerComponent.mediaPlayer().controls().pause();
					Main.BetterNotes.setText("  Better Notes - " + Main.selected.file.getName() + " (Paused)");
					playing = false;
					playButton.setText("Play");
				}else {
					mediaPlayerComponent.mediaPlayer().controls().play();
					Main.BetterNotes.setText("  Better Notes - " + Main.selected.file.getName() + " (Playing)");
					playing = true;
					playButton.setText("Pause");
				}
			}
		});
	 
		this.setContentPane(contentPane);
		this.setVisible(true);
	}
	
	public void loadVideo(String path) {
		mediaPlayerComponent.mediaPlayer().media().startPaused(path);   	
	}
}