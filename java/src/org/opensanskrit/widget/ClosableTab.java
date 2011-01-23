package org.opensanskrit.widget;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class ClosableTab extends JFrame {
	JTabbedPane pane = new JTabbedPane();
	JLabel label = new JLabel();
	JButton close = new JButton();

	public ClosableTab() {
		label.setName("pippo");
		close.setIcon(new ImageIcon(ClosableTab.class
				.getResource("/org/opensanskrit/resource/CloseIcon.png")));
		close.setPreferredSize(new Dimension(17, 17));
		pane.add(label);
		pane.setTabComponentAt(0, close);
		this.setContentPane(pane);
		setPreferredSize(new Dimension(300, 300));
		setVisible(true);
	}

	public static void main(String args[]) {
		new ClosableTab();
	}
}
