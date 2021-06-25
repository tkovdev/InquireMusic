package com.inquiremusic.app.view;

import java.awt.GridLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * DetailPaneTabTrack houses the selected results by using a JEditorPane to display read-only html
 * @author Thomas P. Kovalchuk
 *
 */
public class DetailPaneTabTrack extends JPanel {
	protected JEditorPane textArea;
	protected JScrollPane textScrollable;
	/**
	 * DetailPaneTabArtist creates a new tab with a scrollpane & editorpane to display html.
	 */
	public DetailPaneTabTrack() {
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
	
	public void resetScrollHeight() {
		textScrollable.getVerticalScrollBar().setValue(0);
	}
	
	public JScrollPane getScrollPane() {
		return textScrollable;
	}

}
