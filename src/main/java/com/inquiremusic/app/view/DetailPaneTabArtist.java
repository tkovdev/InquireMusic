package com.inquiremusic.app.view;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

/**
 * DetailPaneTabArtist houses the selected results by using a JEditorPane to display read-only html
 * @author Thomas P. Kovalchuk
 *
 */
public class DetailPaneTabArtist extends JPanel {
	protected JEditorPane textArea;
	protected JScrollPane textScrollable;
	/**
	 * DetailPaneTabArtist creates a new tab with a scrollpane & editorpane to display html.
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
	
	public JScrollPane getScrollPane() {
		return textScrollable;
	}

}
