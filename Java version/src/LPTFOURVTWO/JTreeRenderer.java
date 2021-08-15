package LPTFOURVTWO;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class JTreeRenderer extends DefaultTreeCellRenderer{

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		JComponent c = (JComponent) super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value; 
		
		for(FileStorage.Nodes nodes : Main.allnodes) {
			if(nodes.node == node) {
				setIcon(new ImageIcon(Main.class.getResource("ExitIcon.png")));
			}
		}
		
		if(expanded) {
			c.setBackground(new Color(228, 231, 235));
			c.setOpaque(true);
			return c;
		}

		if(isSelected) {
			c.setBackground(new Color(218, 221, 225));
			c.setOpaque(true);
			return c;
		}

		if(!isSelected || !expanded) {
			c.setBackground(new Color(227, 229, 232));
			c.setOpaque(true);
			return c;
		}
		return c; 
	}
}