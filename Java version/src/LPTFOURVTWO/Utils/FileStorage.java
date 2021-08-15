package LPTFOURVTWO.Utils;

import java.awt.Image;
import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public class FileStorage {
	public static Image whiteboard;
	public static class Nodes {
		public DefaultMutableTreeNode node;
		public String extension;
		public File file;
		public boolean folder;
		
		public Nodes (DefaultMutableTreeNode node, String extension, File file, boolean folder) {
			this.node = node;
			this.extension = extension;
			this.file = file;
			this.folder = folder;
		}
	}
}
