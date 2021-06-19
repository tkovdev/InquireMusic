package com.inquiremusic.data;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Album stores album related information; provides all getters & setters, overrides toString, & provides toHtmlString methods.
 * @author Thomas P. Kovalchuk
 *
 */
public class Album {
	private String id;
	private String name;
	private String label;
	private String type;
	private LocalDate releaseDate;
	private double popularity;
	private int totalTracks;
	
	private ArrayList<Track> tracks;
	private Artist artist;
	
	/**
	 * Album object that provides a blank instance of the object, which enables setting various data as needed.
	 */
	public Album() {
		
	}
	
	/**
	 * Album object that provides a filled instance of the object.
	 * @param id
	 * @param name
	 * @param label
	 * @param type
	 * @param releaseDate
	 * @param popularity
	 * @param totalTracks
	 * @param artist
	 * @param tracks
	 */
	public Album(String id, String name, String label, String type, LocalDate releaseDate, double popularity, int totalTracks, Artist artist, ArrayList<Track> tracks) {
		 this.id = id;
		 this.name = name;
		 this.label = label;
		 this.type = type;
		 this.releaseDate = releaseDate;
		 this.popularity = popularity;
		 this.totalTracks = totalTracks;
		 this.tracks = tracks;
		 this.artist = artist;
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
		result += "lable: " + this.label + "\n";
		result += "type: " + this.type + "\n";
		result += "release date: " + this.releaseDate + "\n";
		result += "popularity: " + this.popularity + "\n";
		result += "total tracks: " + this.totalTracks + "\n";
		result += "artist: " + this.artist + "\n";
		result += "tracks: " + this.tracks + "\n";
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
		if(this.artist != null) {			
			result += "<h3>By: " + this.artist.getName() + "</h3>";
		}
		result += "<ul>";
		result += "<li> <b>Released:</b> " + (this.releaseDate != null ? this.releaseDate : "n/a") + "</li>";
		result += "<li> <b>Type:</b> " + (this.type != null ? this.type : "n/a") + "</li>";
		result += "<li> <b>Popularity:</b> " + (this.popularity != 0 ? this.popularity : "n/a") + "</li>";
		result += "<li> <b>Number of Tracks:</b> " + this.totalTracks + "</li>";
		result += "</ul>";
		return result;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getType() {
		return type;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public double getPopularity() {
		return popularity;
	}

	public int getTotalTracks() {
		return totalTracks;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public void setTotalTracks(int totalTracks) {
		this.totalTracks = totalTracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
