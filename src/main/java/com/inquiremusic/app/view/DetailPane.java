package com.inquiremusic.app.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

/**
 * Right-hand panel in MainView that holds the tabbed pane to display selected results
 * @author Thomas P. Kovalchuk
 *
 */
public class DetailPane extends JPanel {

	protected JLabel lblDetail;
	protected JTabbedPane tabbedDetailPaneTab;
	/**
	 * DetailPane creates a new DetailPane to house a label & tabbedpane
	 */
	public DetailPane() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {45, 45, 45};
		gridBagLayout.rowHeights = new int[] {20, 0, 60};
		gridBagLayout.columnWeights = new double[]{1.0, 1, 1};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblDetail = new JLabel("Details");
		lblDetail.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblDetail = new GridBagConstraints();
		gbc_lblDetail.insets = new Insets(0, 0, 5, 5);
		gbc_lblDetail.fill = GridBagConstraints.BOTH;
		gbc_lblDetail.gridx = 0;
		gbc_lblDetail.gridy = 0;
		add(lblDetail, gbc_lblDetail);
		
		tabbedDetailPaneTab = new DetailPaneTab(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 3;
		gbc_tabbedPane.insets = new Insets(0, 0, 0, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		add(tabbedDetailPaneTab, gbc_tabbedPane);

	}
	public JLabel getLblDetail() {
		return lblDetail;
	}
	public JTabbedPane getTabbedDetailPaneTab() {
		return tabbedDetailPaneTab;
	}

}
