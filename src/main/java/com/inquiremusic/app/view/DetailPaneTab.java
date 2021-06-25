package com.inquiremusic.app.view;

import javax.swing.JTabbedPane;

/**
 * DetailPaneTab houses each tab of the selected results and is set by each controller (Artist, Album, & Track)
 * @author Thomas P. Kovalchuk
 *
 */
public class DetailPaneTab extends JTabbedPane {
	
	/**
	 * DetailPaneTab creates a new tabbed pane with no special display formatting, houses the individual tabs.
	 */
	public DetailPaneTab() {
	}
	public DetailPaneTab(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
	}

	public DetailPaneTab(int tabPlacement) {
		super(tabPlacement);
	}


}
