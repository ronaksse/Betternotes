package LPTFOURVTWO;

import javax.swing.tree.DefaultMutableTreeNode;

public class FileStorage {

	public static class Nodes {
		public DefaultMutableTreeNode node;
		public String extension;
		
		public Nodes (DefaultMutableTreeNode node, String extension) {
			this.node = node;
			this.extension = extension;
		}
	}
}
