package com.inquiremusic.app.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.inquiremusic.app.model.ComboItem;
import com.inquiremusic.app.model.ListItem;

/**
 * MainPanel houses all components of the application to display UI.
 * @author Thomas P. Kovalchuk
 *
 */
public class MainPanel extends JPanel{	
	protected JTextField textSearch;
	protected JTextArea textStorage;
	protected JScrollPane textScrollStorage;
	protected JList<ListItem> listResults;	
	protected JLabel lblSearch;
	protected JComboBox<ComboItem> cbxSearchType;
	protected JButton btnSearch;
	protected DetailPane panelDetail;
	
	/**
	 * MainPanel creates a new JPanel that formats the layout and adds all the components of the UI to display
	 */
	public MainPanel(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{29, 100, 0, 0, 0, 33, 29, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 29, 0};
		gridBagLayout.columnWeights = new double[]{1, 1, 1, 1, 1, 1, 1, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblSearch = new JLabel("Search Artist, Album, & Tracks");
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.anchor = GridBagConstraints.WEST;
		gbc_lblSearch.fill = GridBagConstraints.VERTICAL;
		gbc_lblSearch.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearch.gridx = 0;
		gbc_lblSearch.gridy = 0;
		add(lblSearch, gbc_lblSearch);
		
		cbxSearchType = new JComboBox<ComboItem>();
		GridBagConstraints gbc_cbxSearchType = new GridBagConstraints();
		gbc_cbxSearchType.insets = new Insets(0, 0, 5, 5);
		gbc_cbxSearchType.fill = GridBagConstraints.BOTH;
		gbc_cbxSearchType.gridx = 1;
		gbc_cbxSearchType.gridy = 0;
		add(cbxSearchType, gbc_cbxSearchType);
		
		textSearch = new JTextField();
		GridBagConstraints gbc_textSearch = new GridBagConstraints();
		gbc_textSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textSearch.fill = GridBagConstraints.BOTH;
		gbc_textSearch.gridx = 0;
		gbc_textSearch.gridy = 1;
		add(textSearch, gbc_textSearch);
		textSearch.setColumns(10);
		
		btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 1;
		btnSearch.setEnabled(false);
		add(btnSearch, gbc_btnSearch);
		
		GridBagConstraints gbc_listResults_1 = new GridBagConstraints();
		gbc_listResults_1.insets = new Insets(0, 0, 5, 5);
		gbc_listResults_1.gridx = 0;
		gbc_listResults_1.gridy = 2;
		gbc_listResults_1.fill = GridBagConstraints.BOTH;
		listResults = new JList<ListItem>();
		listResults.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listResults.setLayoutOrientation(JList.VERTICAL);
		textScrollStorage = new JScrollPane();
		textScrollStorage.setPreferredSize(new Dimension(250, 200));
		textScrollStorage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textScrollStorage.setViewportView(listResults);
		add(textScrollStorage, gbc_listResults_1);
		
		GridBagConstraints gbc_panelDetail_1 = new GridBagConstraints();
		gbc_panelDetail_1.fill = GridBagConstraints.BOTH;
		gbc_panelDetail_1.gridwidth = 6;
		gbc_panelDetail_1.insets = new Insets(0, 0, 5, 5);
		gbc_panelDetail_1.gridx = 1;
		gbc_panelDetail_1.gridy = 2;
		panelDetail = new DetailPane();
		panelDetail.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panelDetail, gbc_panelDetail_1);
		
		initialize();
	}
	/**
	 * Setup the dropdown
	 */
	private void initialize() {
		ComboItem[] options = new ComboItem[3];
		options[0] = new ComboItem(0, "Artist");
		options[1] = new ComboItem(0, "Album");
		options[2] = new ComboItem(0, "Track");
		this.setCbxSearchType(options);		
	}
	public JButton getBtnSearch() {
		return btnSearch;
	}
	public JTextField getTextSearch() {
		return textSearch;
	}
	public JComboBox<ComboItem> getCbxSearchType() {
		return cbxSearchType;
	}
	public JList<ListItem> getListResults(){
		return listResults;
	}
	public DetailPane getPanelDetail() {
		return panelDetail;
	}
	public void resetTextSearch(){
		textSearch.setText("");
	}
	public void setCbxSearchType(ComboItem[] options) {
		cbxSearchType.removeAllItems();
		for(ComboItem option : options) {
			cbxSearchType.addItem(option);
		}
	}
	public void setListResults(ArrayList<ListItem> e) {
		ListItem[] list = new ListItem[e.size()];
		int i = 0;
		for(ListItem a: e){
			list[i] = a;
			i++;
		}
		this.listResults.setListData(list);
	}
}
