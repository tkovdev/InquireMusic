package com.inquiremusic.data;

import java.util.ArrayList;

/**
 * Artist stores artist related information; provides all getters & setters, overrides toString, & provides toHtmlString methods.
 * @author Thomas P. Kovalchuk
 *
 */
public class Artist {
	private String id;
	private String name;
	private ArrayList<String> genres;
	private double popularity;
	
	private ArrayList<Album> albums;
	private ArrayList<Track> tracks;
	private ArrayList<Track> topTracks;
	
	/**
	 * Artist object that provides a blank instance of the object, which enables setting various data as needed.
	 */
	public Artist() {

	}
	
	/**
	 * Artist object that provides a filled instance of the object.
	 * @param id
	 * @param name
	 * @param genres
	 * @param popularity
	 * @param albums
	 * @param tracks
	 */
	public Artist(String id, String name, ArrayList<String> genres, double popularity, ArrayList<Album> albums, ArrayList<Track> tracks, ArrayList<Track> topTracks) {
		this.id = id;
		this.name = name;
		this.genres = genres;
		this.popularity = popularity;
		this.albums = albums;
		this.tracks = tracks;
		this.topTracks = topTracks;
	}

	/**
	 * toString override to display contents of object.
	 * @return String - provides all data pertaining to the object in a minimally formated way
	 */
	@Override
	public String toString() {
		String result = "";
		result += "id: " + this.id + "\n";
		result += "name: " + this.name + "\n";
		result += "genres: " + this.genres + "\n";
		result += "popularity: " + this.popularity + "\n";
		if(this.albums.size() > 0) {			
			result += "albums: " + this.albums + "\n";
		}
		if(this.tracks.size() > 0) {
			result += "tracks: " + this.tracks + "\n";
		}
		if(this.topTracks.size() > 0) {
			result += "top tracks: " + this.topTracks + "\n";
		}
		return result;
	}
	
	/**
	 * toHtmlString to display contents of object in HTML format.
	 * @return String - provides all data pertaining to the object in a more formatted way. Creates HTML for use in display.
	 */
	public String toHtmlString() {
		String result = "";
		result += "<h1>" + this.name + "</h1>";
		result += "<hr />";
		result += "<ul>";
		result += "<li> <b>Genres:</b> ";
		if(this.genres != null) {
			for(int i = 0; i < this.genres.size(); i++) {
				if(i == this.genres.size()-1) {
					result += this.genres.get(i);		
				}else {
					result += this.genres.get(i) + ", ";					
				}
			}			
		}else {
			result += "n/a";
		}
		result += "</li>";
		result += "<li> <b>Popularity:</b> " + (this.popularity != 0 ? this.popularity : "n/a") + "</li>";
		if(this.albums != null && this.albums.size() > 0) {
			result += "<li> <b>Number of Albums:</b> " + this.albums.size() + "</li>";
		}
		if(this.tracks != null && this.tracks.size() > 0) {
			result += "<li> <b>Number of Tracks:</b> " + this.tracks.size() + "</li>";
		}
		result += "</ul>";
		if(this.topTracks != null) {
			result += "<hr />";
			result += "<h3> Top Tracks </h3>";
			result += "<ul>";
			for(Track topTrack : this.topTracks) {				
				result += "<li>" + topTrack.getName();
				if(topTrack.getAlbum() != null) {
					result += " on album: "+ topTrack.getAlbum().getName();
				}
				result += "</li>";
			}
			result += "</ul>";
		}
		return result;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public double getPopularity() {
		return popularity;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}
	
	public ArrayList<Track> getTopTracks(){
		return topTracks;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	
	public void setTopTracks(ArrayList<Track> topTracks) {
		this.topTracks = topTracks;
	}
}
