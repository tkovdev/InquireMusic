package com.inquiremusic.app.view;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

public class DetailPaneTabArtist extends JPanel {
	protected JEditorPane textArea;
	protected JScrollPane textScrollable;
	/**
	 * Create the panel.
	 */
	public DetailPaneTabArtist() {
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

}
