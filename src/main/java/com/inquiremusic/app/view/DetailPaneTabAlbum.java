package com.inquiremusic.app.view;

import java.awt.GridLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DetailPaneTabAlbum extends JPanel {
	protected JEditorPane textArea;
	protected JScrollPane textScrollable;
	/**
	 * Create the panel.
	 */
	public DetailPaneTabAlbum() {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		textScrollable = new JScrollPane();
		textArea = new JEditorPane();
		textArea.setEditable(false);
		textArea.setContentType("text/html");
		textScrollable.setViewportView(textArea);
		add(textScrollable);
	}
	
	public JEditorPane getTextArea() {
		return textArea;
	}
	
	public JScrollPane getScrollPane() {
		return textScrollable;
	}

}
