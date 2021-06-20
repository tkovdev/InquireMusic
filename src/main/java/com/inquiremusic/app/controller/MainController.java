package com.inquiremusic.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.inquiremusic.app.model.ListItem;
import com.inquiremusic.app.view.MainView;
import com.inquiremusic.data.SearchResult;
import com.inquiremusic.logic.AlbumLogic;
import com.inquiremusic.logic.ArtistLogic;
import com.inquiremusic.logic.SearchResultLogic;
import com.inquiremusic.logic.TrackLogic;

/**
 * MainController - Primary front-end logic for InquireMusic application. Handles events and controls all views.
 * Each event is tied to a listener and calls separate controllers for handling data loading and calls to Logic library
 * @author Thomas P. Kovalchuk
 * @see ArtistController
 * @see AlbumController
 * @see TrackController
 * @see ArtistLogic
 * @see AlbumLogic
 * @see TrackLogic
 * @see SearchResultLogic
 * @see MainView
 * @see MainModel
 *
 */
public class MainController {
	protected MainView view;
	private final SearchResultLogic _searchResultLogic;
	private final ArtistLogic _artistLogic;
	private final AlbumLogic _albumLogic;
	private final TrackLogic _trackLogic;
	private final ArtistController _artistController;
	private final AlbumController _albumController;
	private final TrackController _trackController;
	
	/**
	 * Creates a new MainController object, has 3 dependencies, a View, a Model, & a HashMap of available services.
	 * @param view
	 * @param services
	 */
	public MainController(MainView view, HashMap<String, Object> services) {
		this.view = view;
		this.view.addListeners(new Listener(), new ListListener(), new KeyboardListener());
		this._searchResultLogic = (SearchResultLogic)services.get("_searchResultLogic");
		this._artistLogic = (ArtistLogic)services.get("_artistLogic");
		this._albumLogic = (AlbumLogic)services.get("_albumLogic");
		this._trackLogic = (TrackLogic)services.get("_trackLogic");
		this._artistController = new ArtistController(view, this._trackLogic, this._artistLogic, this._albumLogic);
		this._albumController = new AlbumController(view, this._trackLogic, this._artistLogic, this._albumLogic);
		this._trackController = new TrackController(view, this._trackLogic, this._artistLogic, this._albumLogic);
	}
	
	/**
	 * Primary event listener for Scrollable list of search results. Calls additional controllers for each action performed.
	 * @author Thomas P. Kovalchuk
	 *
	 */
	public class ListListener implements ListSelectionListener{
		//keep track of which list item was selected
		private ListItem selectedItem = null;
		@Override
		public void valueChanged(ListSelectionEvent e) {
			//On SearchResult item click
			if(e.getSource() == view.getMainPanel().getListResults()) {
				JList<ListItem> list = view.getMainPanel().getListResults();
				//only react when the list selection has changed
				if(list.getSelectedValue() != null && list.getSelectedValue() != selectedItem) {
					//get the type of search that was performed (Artist, Album, Track)
					String type = view.getMainPanel().getCbxSearchType().getSelectedItem().toString();
					String id = "";
					try {					
						//get Id of selected item, gets the actual model's Id to pass to each controller
						id = list.getSelectedValue().getId();
					}catch(NullPointerException ex) {
						view.getErrorDialog().setVisible(true);
					}
					if(id != "" && id != null) {
						//based on selected dropdown, hand over final execution to appropriate controller to display results
						switch(type) {
							case "Artist":
								_artistController.artistDetailTabSelected(id);
								break;
							case "Album":
								_albumController.albumDetailTabSelected(id);
								break;
							case "Track":
								_trackController.trackDetailTabSelected(id);
								break;
						}
						//change the list selection to compare the next list selection against
						this.selectedItem = list.getSelectedValue();
					}
				}
			}
		}
		
	}
	
	public class KeyboardListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			Object source = e.getSource();
			if(source == view.getMainPanel().getTextSearch()) {
				if(view.getMainPanel().getTextSearch().getText().length() > 1) {
					view.getMainPanel().getBtnSearch().setEnabled(true);
				}else {
					view.getMainPanel().getBtnSearch().setEnabled(false);
				}
			}			
		}
		
	}

	public class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == view.getMainPanel().getBtnSearch()){
				view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().removeAll();
				//Get the dropdown selection to determine what the search is for
				String type = view.getMainPanel().getCbxSearchType().getSelectedItem().toString();
				//Get the user entered text to determine what name to look for
				String term = view.getMainPanel().getTextSearch().getText().strip();
				//Call out to SearchResultLogic to get the results
				SearchResult sr = _searchResultLogic.Get(term, type);
				if(sr != null) {
					//check which type of result it was, send to respective controller to update view with results
					switch(type) {
						case "Artist":
							_artistController.artistSearched(sr);
							break;
						case "Album":
							_albumController.albumSearched(sr);
							break;
						case "Track":
							_trackController.trackSearched(sr);
							break;
					}
				}else {
					//search came back null, show error
					view.getErrorDialog().setVisible(true);
				}
			}else if(source == view.getMainPanel().getCbxSearchType()) {
				//if the dropdown changes, remove all current results
				view.getMainPanel().getTextSearch().setText(null);
				view.getMainPanel().setListResults(new ArrayList<ListItem>());
				view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().removeAll();
			}
		}
	}
}
