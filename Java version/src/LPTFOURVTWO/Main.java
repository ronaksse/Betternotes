package LPTFOURVTWO;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;

import LPTFOURVTWO.Utils.DragBarButtons;
import LPTFOURVTWO.Utils.DragNDropFrame;
import LPTFOURVTWO.Utils.FileStorage;
import LPTFOURVTWO.Utils.JTreeRenderer;
import LPTFOURVTWO.Utils.SliderChangeListener;
import LPTFOURVTWO.Utils.TopbarListener;
import LPTFOURVTWO.Utils.Video;
import jaco.mp3.player.MP3Player;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main {

	static LookAndFeel defaultlf = UIManager.getLookAndFeel();

	public static JFrame frame = new JFrame("‎‎‎‎‎‎‎‎‎Better Notes");

	private static JPanel dragbar = new JPanel(), topbar = new JPanel(), notes = new JPanel();

	public static JPanel work = new JPanel();

	public static JLabel BetterNotes = new JLabel("  Better Notes");

	public static JButton minimize = new JButton(), fullscreen = new JButton(), close = new JButton();

	private static JMenu file, edit, help;

	public static JMenuItem save, newfolder, newtextdocument, newwhiteboard, refresh;

	public static JMenuItem copy, paste, cut;

	public static JMenuItem website;

	public static File dir;

	public static JTree notetree;

	public static DefaultMutableTreeNode root = new DefaultMutableTreeNode();

	public static LinkedList <FileStorage.Nodes> allnodes = new LinkedList <FileStorage.Nodes>();

	public static FileStorage.Nodes selected;
	public static FileStorage.Nodes selectedParent;

	public static Image whiteboard = new ImageIcon(Main.class.getResource("WhiteBoardIcon.png")).getImage();

	public static String os = "";


	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException, FileNotFoundException, LineUnavailableException {
		initialize();
		frame.setVisible(true);
	}

	public static JTextArea area = new JTextArea();
	private static JScrollPane scrollPane = new JScrollPane(area);

	public static void addTextFile(FileStorage.Nodes selected) throws FileNotFoundException {
		if(playermp3 != null)
			playermp3.stop();
		if(playerother != null)
			playerother.close();

		work.removeAll();
		work.revalidate();
		work.repaint();

		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setSize(work.getWidth(), work.getHeight());
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		work.add(scrollPane);

		File file = selected.file;
		Scanner sc = new Scanner(file);
		String content = "no note";
		boolean first = false;
		while(sc.hasNextLine()) {
			if(!first) {
				content = "";
				first = true;
			}else {
				content += sc.nextLine() + "\n";
			}
		}
		area.setText(content);

		sc.close();
	}

	public static JSlider slider;
	public static JScrollPane imagescrollPane;
	public static JLabel label = new JLabel();
	public static BufferedImage image;

	public static void addImageFile(FileStorage.Nodes selected) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		if(playermp3 != null)
			playermp3 .stop();
		if(playerother != null)
			playerother.close();

		work.removeAll();
		work.revalidate();
		work.repaint();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		slider = new JSlider(JSlider.HORIZONTAL, 200);
		slider.setValue(100);
		slider.setSize(work.getWidth(), 35);
		slider.setFocusable(false);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(20);
		slider.setFont(new Font("Serif", Font.BOLD, 9));
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		SliderChangeListener scl = new SliderChangeListener();
		slider.addChangeListener(scl);

		image = ImageIO.read(selected.file);

		label.setIcon(new ImageIcon(image));
		label.setSize(work.getWidth(), work.getHeight()-slider.getHeight());

		imagescrollPane = new JScrollPane(label);
		imagescrollPane.setSize(work.getWidth(), work.getHeight()-slider.getHeight());
		imagescrollPane.setBorder(null);

		UIManager.setLookAndFeel(defaultlf);

		work.add(slider);

		work.add(imagescrollPane);
		work.repaint();

		BetterNotes.setText("  Better Notes - " + selected.file.getName() + " @ 100%");
	}

	private static MP3Player playermp3;

	public static void addAudioFileMP3(FileStorage.Nodes selected) {
		if(playermp3 != null)
			playermp3.stop();
		if(playerother != null)
			playerother.close();

		work.removeAll();
		work.revalidate();
		work.repaint();

		File mp3 = selected.file;
		playermp3 = new MP3Player(mp3);

		BetterNotes.setText("  Better Notes - " + selected.file.getName() + " (Paused)");

		JButton pauseplay = new JButton("Play");
		pauseplay.setBackground(new Color(200, 200, 200));
		pauseplay.setBorderPainted(false);
		pauseplay.setFocusable(false);
		pauseplay.setSize(100, 100);

		JButton replay = new JButton("Restart");
		replay.setBackground(new Color(200, 200, 200));
		replay.setBorderPainted(false);
		replay.setFocusable(false);
		replay.setLocation(pauseplay.getWidth(), 0);
		replay.setSize(100, 100);


		pauseplay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(playermp3.isPaused()) {
					playermp3.play();
					pauseplay.setText("Pause");
					BetterNotes.setText("  Better Notes - " + selected.file.getName() + " (Playing)");
				}else {
					playermp3.pause();
					pauseplay.setText("Play");
					BetterNotes.setText("  Better Notes - " + selected.file.getName() + " (Paused)");
				}
			}

		});

		replay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addAudioFileMP3(selected);
			}

		});

		work.add(pauseplay);
		work.add(replay);
		work.repaint();
		playermp3.play();
		playermp3.pause();
	}

	private static Clip playerother;

	public static void addAudioFileOther(FileStorage.Nodes selected) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		if(playermp3 != null)
			playermp3.stop();
		if(playerother != null)
			playerother.close();

		work.removeAll();
		work.revalidate();
		work.repaint();

		File mp3 = selected.file;
		AudioInputStream ais = AudioSystem.getAudioInputStream(mp3);
		playerother = AudioSystem.getClip();
		playerother.open(ais);

		BetterNotes.setText("  Better Notes - " + selected.file.getName() + " (Paused)");

		JButton pauseplay = new JButton("Play");
		pauseplay.setBackground(new Color(200, 200, 200));
		pauseplay.setBorderPainted(false);
		pauseplay.setFocusable(false);
		pauseplay.setSize(100, 100);

		JButton replay = new JButton("Restart");
		replay.setBackground(new Color(200, 200, 200));
		replay.setBorderPainted(false);
		replay.setFocusable(false);
		replay.setLocation(pauseplay.getWidth(), 0);
		replay.setSize(100, 100);


		pauseplay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!playerother.isRunning()) {
					playerother.start();
					pauseplay.setText("Pause");
					BetterNotes.setText("  Better Notes - " + selected.file.getName() + " (Playing)");
				}else {
					playerother.stop();;
					pauseplay.setText("Play");
					BetterNotes.setText("  Better Notes - " + selected.file.getName() + " (Paused)");
				}
			}

		});

		replay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerother.setFramePosition(0);
			}

		});

		work.add(pauseplay);
		work.add(replay);
		work.repaint();
	}

	public static void addVideoFile(FileStorage.Nodes selected) throws IOException {
		BetterNotes.setText("  Better Notes - " + selected.file.getName() + " (Paused)");
		Video application = new Video("VideoPlayer");
		application.initialize(); 
		application.setVisible(true);  
		application.loadVideo(selected.file.getPath());
	}

	private static void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, LineUnavailableException {
		FileStorage.whiteboard = whiteboard;

		frame.setIconImage(new ImageIcon(Main.class.getResource("BetterNotesIcon.png")).getImage());
		frame.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
		frame.getContentPane().setLayout(new GroupLayout(frame.getContentPane()));
		frame.setSize(1280, 720);
		frame.setUndecorated(true);

		dragbar.setBackground(new Color(227, 229, 232));
		dragbar.setLayout(new GroupLayout(dragbar));
		dragbar.setSize(frame.getWidth(), 20);

		BetterNotes.setSize(new Dimension(1000, 20));
		minimize.setSize(new Dimension(30, 20));
		fullscreen.setSize(new Dimension(30, 20));
		close.setSize(new Dimension(30, 20));

		ImageIcon minimizeicon = new ImageIcon(Main.class.getResource("MinimizeIcon.png"));

		ImageIcon fullscreenicon = new ImageIcon(Main.class.getResource("FullScreenIcon.png"));

		ImageIcon closeicon = new ImageIcon(Main.class.getResource("ExitIcon.png"));

		minimize.setIcon(minimizeicon);
		fullscreen.setIcon(fullscreenicon);
		close.setIcon(closeicon);

		DragBarButtons dbl = new DragBarButtons();

		minimize.setBackground(new Color(227, 229, 232));
		minimize.setBorderPainted(false);
		minimize.setFocusable(false);
		minimize.addActionListener(dbl);
		minimize.addMouseListener(dbl);

		fullscreen.setBackground(new Color(227, 229, 232));
		fullscreen.setBorderPainted(false);
		fullscreen.setFocusable(false);
		fullscreen.addActionListener(dbl);
		fullscreen.addMouseListener(dbl);

		close.setBackground(new Color(227, 229, 232));
		close.setBorderPainted(false);
		close.setFocusable(false);
		close.addActionListener(dbl);
		close.addMouseListener(dbl);

		close.setLocation(dragbar.getWidth()-close.getWidth(), 0);
		fullscreen.setLocation(dragbar.getWidth()-(fullscreen.getWidth()+close.getWidth()), 0);
		minimize.setLocation(dragbar.getWidth()-(minimize.getWidth()+fullscreen.getWidth()+close.getWidth()), 0);

		DragNDropFrame dnd = new DragNDropFrame();

		dragbar.addMouseListener(dnd);
		dragbar.addMouseMotionListener(dnd);
		dragbar.add(BetterNotes);
		dragbar.add(minimize);
		dragbar.add(fullscreen);
		dragbar.add(close);

		frame.getContentPane().add(dragbar);

		topbar.setBackground(new Color(242, 243, 245));
		topbar.setLayout(new CardLayout());
		topbar.setSize(frame.getWidth(), 20);
		topbar.setLocation(0, dragbar.getHeight());

		Font f = new Font("sans-serif", Font.PLAIN, 12);
		UIManager.put("Menu.font", f);
		UIManager.put("MenuItem.font", f);

		file = new JMenu("File");
		edit = new JMenu("Edit");
		help = new JMenu("Help");

		save = new JMenuItem("Save");
		newfolder = new JMenuItem("New Folder");
		newtextdocument = new JMenuItem("New Text");
		newwhiteboard = new JMenuItem("New Whiteboard");
		refresh = new JMenuItem("Refresh Files");

		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		cut = new JMenuItem("Cut");

		website = new JMenuItem("Website");

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(242, 243, 245));
		menuBar.setBorder(null);

		topbar.add(menuBar);

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);

		file.add(save);
		file.add(newfolder);
		file.add(newtextdocument);
		file.add(newwhiteboard);
		file.add(refresh);

		edit.add(copy);
		edit.add(paste);
		edit.add(cut);

		help.add(website);

		TopbarListener tbl = new TopbarListener();
		save.addActionListener(tbl);
		newfolder.addActionListener(tbl);
		newtextdocument.addActionListener(tbl);
		newwhiteboard.addActionListener(tbl);
		refresh.addActionListener(tbl);

		copy.addActionListener(tbl);
		paste.addActionListener(tbl);
		cut.addActionListener(tbl);

		website.addActionListener(tbl);

		frame.getContentPane().add(topbar);

		notes.setLayout(new CardLayout());
		notes.setSize(200, frame.getHeight()-dragbar.getHeight());
		notes.setLocation(0, dragbar.getHeight()+topbar.getHeight());

		String desktop = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
		dir = new File(desktop + "/Better Notes");

		allnodes.add(new FileStorage.Nodes(root, "???", dir, true));
		selectedParent = new FileStorage.Nodes(root, "???", dir, true);

		if(dir.isDirectory()) {
			addNodes(root, dir);
		}else {
			if(dir.mkdir()) {
				addNodes(root, dir);
			}else {
				System.exit(0);
			}
		}

		notetree = new JTree(root);
		notetree.setBackground(new Color(227, 229, 232));
		notetree.setRootVisible(false);

		JTreeRenderer jtr = new JTreeRenderer();
		notetree.setCellRenderer(jtr);

		JScrollPane notepane = new JScrollPane(notetree);

		notepane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		notepane.setBackground(new Color(227, 229, 232));

		notes.add(notepane);		

		frame.getContentPane().add(notes);

		work.setBackground(new Color(255, 255, 255));
		work.setLayout(new GroupLayout(work));
		work.setSize(frame.getWidth()-notes.getWidth(), frame.getHeight()-(dragbar.getHeight()+topbar.getHeight()));
		work.setLocation(notes.getWidth(), dragbar.getHeight()+topbar.getHeight());
		work.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		frame.getContentPane().add(work);

	}

	public static void addNodes(DefaultMutableTreeNode parentnode, File dir) {
		if(dir.isDirectory()) {
			for (File fileEntry : dir.listFiles()) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileEntry.getName());
				parentnode.add(node);
				if (fileEntry.isDirectory()) {
					allnodes.add(new FileStorage.Nodes(node, getExtension(fileEntry.getName()), fileEntry, true));
					addNodes(node, new File(fileEntry.getPath()));
				}else {
					allnodes.add(new FileStorage.Nodes(node, getExtension(fileEntry.getName()), fileEntry, false));
				}
			}
		}
	}

	private static String getExtension(String filename) {
		int startin = filename.lastIndexOf('.');
		if(startin == -1) {
			return "???";
		}
		return filename.substring(startin, filename.length()).toLowerCase();
	}

}
