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
 * AlbumController - handles front-end logic to request all artist related details and display them in each tab of the selected results or scrollpane.
 * @author Thomas P. Kovalchuk
 *
 */
public class AlbumController {
	protected MainView view;
	private final ArtistLogic _artistLogic;
	private final AlbumLogic _albumLogic;
	private final TrackLogic _trackLogic;
	
	/**
	 * Creates a new AlbumController object, has 4 dependencies, the MainView, TrackLogic, ArtistLogic, & AlbumLogic
	 * @param view
	 * @param services
	 * @param trackLogic
	 * @param artistLogic
	 * @param albumLogic
	 */
	public AlbumController(MainView view, TrackLogic trackLogic, ArtistLogic artistLogic, AlbumLogic albumLogic) {
		this._artistLogic = artistLogic;
		this._albumLogic = albumLogic;
		this._trackLogic = trackLogic;
		this.view = view;
	}
	
	/**
	 * albumSearched updates the scrollpane with a given SearchResult by looping results and setting a ArrayList of ListItems
	 * @param sr - SearchResult object containing the results of the search
	 */
	public void albumSearched(SearchResult sr) {
		ArrayList<ListItem> result = new ArrayList<ListItem>();
		ArrayList<Album> albums = sr.getAlbums();
		//make new ListItems to be added to the scrollpane
		for(Album album : albums) {
			result.add(new ListItem(album.getId(), album.getName(), album.getArtist().getName(), "Album"));
		}
		if(result.size() == 0) {
			result.add(new ListItem(null, "No Results Found!", null, "try something else"));
		}
		//add all the album listitems to the scrollpane
		view.getMainPanel().setListResults(result);
	}
	
	/**
	 * albumDetailTabSelected updates the detail tabs with the results from a given id by sending a get to the respective logic controller
	 * @param id
	 */
	public void albumDetailTabSelected(String id) {
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().removeAll();
		//Setup new tabs to display data related to each type (Artist, Album, Track)
		DetailPaneTabArtist artistTab = new DetailPaneTabArtist();
		DetailPaneTabAlbum albumTab = new DetailPaneTabAlbum();
		DetailPaneTabTrack trackTab = new DetailPaneTabTrack();
		
		//get the album data that was selected
		Album album = _albumLogic.Get(id);
		Artist artist = null;
		//get the artist data if applicable
		if(album.getArtist() != null) {			
			artist = _artistLogic.Get(album.getArtist().getId());
		}
		
		//get the track data for the album that was selected
		ArrayList<Track> tracks = _trackLogic.GetAllByAlbumId(id);
		String trackResults = "";
		//add the list of tracks to a string for later use
		for(Track track : tracks) {		
			trackResults += track.toHtmlString();
		}
		//set the artist's tracks for later use
		artist.setTracks(tracks);
		
		//add the artist data to a tab
		if(artist != null) {			
			artistTab.getTextArea().setText(artist.toHtmlString());
		}
		//set the text of the text area to display the html
		albumTab.getTextArea().setText(album.toHtmlString());
		trackTab.getTextArea().setText(trackResults);

		//reset the scrollpanes to start at the top
		artistTab.getScrollPane().getVerticalScrollBar().setValue(0);
		albumTab.getScrollPane().getVerticalScrollBar().setValue(0);
		trackTab.getScrollPane().getVerticalScrollBar().setValue(0);
		
		//add the tabs to the parent to display each result as a separate tab
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Artist", artistTab);
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Album", albumTab);
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Tracks", trackTab);
		
		//default the selected tab to show Album first since that was selected
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().setSelectedIndex(1);
		
	}

}
