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
public class ArtistController {
	protected MainView view;
	private final ArtistLogic _artistLogic;
	private final AlbumLogic _albumLogic;
	private final TrackLogic _trackLogic;
	
	public ArtistController(MainView view, TrackLogic trackLogic, ArtistLogic artistLogic, AlbumLogic albumLogic) {
		this._artistLogic = artistLogic;
		this._albumLogic = albumLogic;
		this._trackLogic = trackLogic;
		this.view = view;
	}
	
	/**
	 * artistSearched updates the scrollpane with a given SearchResult by looping results and setting a ArrayList of ListItems
	 * @param sr - SearchResult object containing the results of the search
	 */
	public void artistSearched(SearchResult sr) {
		ArrayList<ListItem> result = new ArrayList<ListItem>();
		ArrayList<Artist> artists = sr.getArtists();
		//make new ListItems to be added to the scrollpane
		for(Artist artist : artists) {
			result.add(new ListItem(artist.getId(), artist.getName(), null, "Artist"));
		}
		if(result.size() == 0) {
			result.add(new ListItem(null, "No Results Found!", null, "try something else"));
		}
		//add all the artist listitems to the scrollpane
		view.getMainPanel().setListResults(result);
	}
	
	/**
	 * artistDetailTabSelected updates the detail tabs with the results from a given id by sending a get to the respective logic controller
	 * @param id
	 */
	public void artistDetailTabSelected(String id) {
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().removeAll();
		//Setup new tabs to display data related to each type (Artist, Album, Track)
		DetailPaneTabArtist artistTab = new DetailPaneTabArtist();
		DetailPaneTabAlbum albumTab = new DetailPaneTabAlbum();
		DetailPaneTabTrack trackTab = new DetailPaneTabTrack();
		
		Artist artist = _artistLogic.Get(id);
		ArrayList<Album> albums = _albumLogic.GetAllByArtistId(artist.getId());
		
		ArrayList<Track> tracks = new ArrayList<Track>();
		for(Album album : albums) {
			tracks.addAll(_trackLogic.GetAllByAlbumId(album.getId()));
		}
		
		String trackResults = "";
		for(Track track : tracks) {		
			trackResults += track.toHtmlString();
		}
		
		
		//Set each Tab's content to the results
		String albumResults = "";
		for(Album album : albums) {		
			albumResults += album.toHtmlString();
		}
		
		//get the top tracks for the artist
		artist.setTopTracks(_trackLogic.GetTopTracksByArtistId(artist.getId()));
		
		artist.setAlbums(albums);
		artist.setTracks(tracks);
		
		artistTab.getTextArea().setText(artist.toHtmlString());
		albumTab.getTextArea().setText(albumResults);
		trackTab.getTextArea().setText(trackResults);
		
		
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Artist", artistTab);
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Albums", albumTab);
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().add("Tracks", trackTab);
		
		view.getMainPanel().getPanelDetail().getTabbedDetailPaneTab().setSelectedIndex(0);
	}

}
