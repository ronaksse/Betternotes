package LPTFOURVTWO.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.tree.DefaultTreeModel;

import LPTFOURVTWO.Main;

public class TopbarListener implements ActionListener{

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == Main.save) {
			try (PrintWriter out = new PrintWriter(Main.selected.file)) {
				out.println(Main.area.getText());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			return;
		}

		if(e.getSource() == Main.newfolder) {
			File newdoc = new File(Main.selectedParent.file.getPath() + "/New Folder");
			int count = 1;
			while(newdoc.exists()) {
				newdoc = new File(Main.selectedParent.file.getPath() + "/New Folder (" + count + ")");
				count++;
			}

			newdoc.mkdir();
			
			Main.root.removeAllChildren();
			Main.addNodes(Main.root, Main.dir);
			DefaultTreeModel model = (DefaultTreeModel)Main.notetree.getModel();
			model.reload(Main.root);
			return;
		}

		if(e.getSource() == Main.newtextdocument) {
			File newdoc = new File(Main.selectedParent.file.getPath() + "/New Document.txt");
			int count = 1;
			while(newdoc.exists()) {
				newdoc = new File(Main.selectedParent.file.getPath() + "/New Document (" + count + ").txt");
				count++;
			}

			try {
				newdoc.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			Main.root.removeAllChildren();
			Main.addNodes(Main.root, Main.dir);
			DefaultTreeModel model = (DefaultTreeModel)Main.notetree.getModel();
			model.reload(Main.root);
			return;
		}

		if(e.getSource() == Main.newwhiteboard) {
			WhiteBoard whiteboard = new WhiteBoard();
			whiteboard.drawPane();
//			File newdoc = new File(Main.selectedParent.file.getPath() + "\\New WhiteBoard.png");
//			int count = 1;
//			while(newdoc.exists()) {
//				newdoc = new File(Main.selectedParent.file.getPath() + "\\New WhiteBoard (" + count + ").png");
//				count++;
//			}
//
//			try {
//				newdoc.createNewFile();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			
//			DefaultMutableTreeNode node = new DefaultMutableTreeNode(newdoc.getName());
//			Main.allnodes.get(Main.allnodes.indexOf(Main.selectedParent)).node.add(node);
//			int index = Main.allnodes.indexOf(Main.selectedParent);
//			Main.allnodes.add(index+1, new FileStorage.Nodes(node, ".png", newdoc, false));
//			DefaultTreeModel model = (DefaultTreeModel)Main.notetree.getModel();
//			model.reload(Main.allnodes.get(Main.allnodes.indexOf(Main.selectedParent)).node);
			return;
		}
		
		if(e.getSource() == Main.refresh) {
			Main.root.removeAllChildren();
			Main.addNodes(Main.root, Main.dir);
			DefaultTreeModel model = (DefaultTreeModel)Main.notetree.getModel();
			model.reload(Main.root);
		}


		if(e.getSource() == Main.copy) {
			Main.area.copy();
			return;
		}

		if(e.getSource() == Main.paste) {
			Main.area.paste();
			return;
		}

		if(e.getSource() == Main.cut) {
			Main.area.cut();
			return;
		}

		if(e.getSource() == Main.website) {
			try {
				URI uri = new URI("https://betternotes.2a02.net/");
				java.awt.Desktop.getDesktop().browse(uri);
			} catch (URISyntaxException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}