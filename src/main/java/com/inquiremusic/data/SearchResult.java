package com.inquiremusic.data;

import java.util.ArrayList;

/**
 * SearchResult stores search related information; provides all getters & setters, overrides toString, & provides toHtmlString methods.
 * @author Thomas P. Kovalchuk
 *
 */
public class SearchResult {
	private String type;
	private ArrayList<Album> albums;
	private ArrayList<Artist> artists;
	private ArrayList<Track> tracks;
	
	/**
	 * SearchResult object that provides a blank instance of the object, which enables setting various data as needed.
	 */
	public SearchResult() {
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<Artist>();
		this.tracks = new ArrayList<Track>();
	}
	
	/**
	 * SearchResult object that provides a filled instance of the object.
	 * @param albums
	 * @param artists
	 * @param tracks
	 */
	public SearchResult(ArrayList<Album> albums, ArrayList<Artist> artists, ArrayList<Track> tracks) {
		this.albums = albums;
		this.artists = artists;
		this.tracks = tracks;
	}

	/**
	 * toString override to display contents of object.
	 * @return String - provides all data pertaining to the object in a minimally formated way
	 */
	@Override
	public String toString() {
		String result = "";
		result += "type: " + this.type + "\n";
		result += "albums: " + this.albums + "\n";
		result += "artists: " + this.artists + "\n";
		result += "tracks: " + this.tracks + "\n";
		return result;
	}
	
	public void addAlbum(Album album) {
		this.albums.add(album);
	}
	
	public void addArtist(Artist artist) {
		this.artists.add(artist);
	}
	
	public void addTrack(Track track) {
		this.tracks.add(track);
	}
	
	public String getType() {
		return type;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public ArrayList<Artist> getArtists() {
		return artists;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public void setArtists(ArrayList<Artist> artists) {
		this.artists = artists;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
}
