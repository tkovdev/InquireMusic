package com.inquiremusic.app.controller;

import java.util.ArrayList;

import com.inquiremusic.app.model.ListItem;
import com.inquiremusic.app.view.DetailPaneTabAlbum;
import com.inquiremusic.app.view.DetailPaneTabArtist;
import com.inquiremusic.app.view.DetailPaneTabTrack;
import com.inquiremusic.app.view.MainView;
import com.inquiremusic.data.Album;
import com.inquiremusic.data.Artist;
import com.inquiremusic.data.SearchResult;
import com.inquiremusic.data.Track;
import com.inquiremusic.logic.AlbumLogic;
import com.inquiremusic.logic.ArtistLogic;
import com.inquiremusic.logic.TrackLogic;

/**
 * ArtistController - handles front-end logic to request all artist related details and display them in each tab of the selected results.
 * @author Thomas P. Kovalchuk
 *
 */
public class TrackController {
	protected MainView view;
	private final ArtistLogic _artistLogic;
	private final AlbumLogic _albumLogic;
	private final TrackLogic _trackLogic;
	
	public TrackController(MainView view, TrackLogic trackLogic, ArtistLogic artistLogic, AlbumLogic albumLogic) {
		this._artistLogic = artistLogic;
		this._albumLogic = albumLogic;
		this._trackLogic = trackLogic;
		this.view = view;
	}
	
	/**
	 * trackSearched updates the scrollpane with a given SearchResult by looping results and setting a ArrayList of ListItems
	 * @param sr - SearchResult object containing the results of the search
	 */
	public void trackSearched(SearchResult sr) {
		ArrayList<ListItem> result = new ArrayList<ListItem>();
		ArrayList<Track> tracks = sr.getTracks();
		//make new ListItems to be added to the scrollpane
		for(Track track : tracks) {
			result.add(new ListItem(track.getId(), track.getName(), track.getArtist().getName(), "Track"));
		}
		if(result.size() == 0) {
			result.add(new ListItem(null, "No Results Found!", null, "try something else"));
		}
		//add all the track listitems to the scrollpane
		view.getMainPanel().setListResults(result);
	}
	
	/**
	 * trackDetailTabSelected updates the detail tabs with the results from a given id by sending a get to the respective logic controller
	 * @param id
	 */
	public void trackDetailTabSelected(String id) {
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().removeAll();
		//Setup new tabs to display data related to each type (Artist, Album, Track)
		DetailPaneTabArtist artistTab = new DetailPaneTabArtist();
		DetailPaneTabAlbum albumTab = new DetailPaneTabAlbum();
		DetailPaneTabTrack trackTab = new DetailPaneTabTrack();
		
		Track track = _trackLogic.Get(id);
		Album album = null;
		if(track.getAlbum() != null) {			
			album = _albumLogic.Get(track.getAlbum().getId());
		}
		Artist artist = null;
		if(album.getArtist() != null) {			
			artist = _artistLogic.Get(album.getArtist().getId());
		}
		
		if(artist != null) {			
			artistTab.getTextArea().setText(artist.toHtmlString());
		}

		albumTab.getTextArea().setText(album.toHtmlString());
		trackTab.getTextArea().setText(track.toHtmlString());

		//reset the scrollpanes to start at the top
		artistTab.getScrollPane().getVerticalScrollBar().setValue(0);
		albumTab.getScrollPane().getVerticalScrollBar().setValue(0);
		trackTab.getScrollPane().getVerticalScrollBar().setValue(0);
				
		//add the tabs to the parent to display each result as a separate tab
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Artist", artistTab);
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Album", albumTab);
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Track", trackTab);
		
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().setSelectedIndex(2);
		
		
	}

}
