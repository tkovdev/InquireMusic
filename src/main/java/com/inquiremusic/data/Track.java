package com.inquiremusic.data;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Track stores track related information; provides all getters & setters, overrides toString, & provides toHtmlString methods.
 * @author Thomas P. Kovalchuk
 *
 */
public class Track{
	private String id;
	private String name;
	private int trackNumber;
	private long duration;
	private boolean isExplicit;
	
	private Album album;
	private Artist artist;
	
	/**
	 * Track object that provides a blank instance of the object, which enables setting various data as needed.
	 */
	public Track() {
		
	}
	
	/**
	 * Track object that provides a filled instance of the object.
	 * @param id
	 * @param name
	 * @param albums
	 * @param tracks
	 * @param trackNumber
	 * @param duration
	 * @param isExplicit
	 * @param album
	 * @param artist
	 */
	public Track(String id, String name, ArrayList<Album> albums, ArrayList<Track> tracks, int trackNumber, long duration, boolean isExplicit, Album album, Artist artist) {
		 this.id = id;
		 this.name = name;
		 this.trackNumber = trackNumber;
		 this.duration = duration;
		 this.isExplicit = isExplicit;
		 this.album = album;
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
		result += "track number: " + this.trackNumber + "\n";
		result += "duration: " + this.duration + "\n";
		result += "is explicit: " + this.isExplicit + "\n";
		result += "album: " + this.album + "\n";
		result += "artist: " + this.artist + "\n";
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
		if(this.album != null) {			
			result += "<h3>On Album: " + this.album.getName() + "</h3>";
		}
		result += "<ul>";
		result += "<li> <b>Duration:</b> " + (this.duration > 0 ? String.format("%01d:%02d:%02d", 
			    TimeUnit.MILLISECONDS.toHours(this.duration),
			    TimeUnit.MILLISECONDS.toMinutes(this.duration) - 
			    TimeUnit.MINUTES.toMinutes(TimeUnit.MILLISECONDS.toHours(this.duration)),
			    TimeUnit.MILLISECONDS.toSeconds(this.duration) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.duration))) : "n/a") + "</li>";
		result += "<li> <b>Track Number:</b> " + (this.trackNumber > 0 ? this.trackNumber : "n/a") + "</li>";
		result += "<li> <b>Explicit:</b> " + (this.isExplicit != false ? "Yes" : "No") + "</li>";
		result += "</ul>";
		return result;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public long getDuration() {
		return duration;
	}

	public boolean isExplicit() {
		return isExplicit;
	}

	public Album getAlbum() {
		return album;
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

	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setExplicit(boolean isExplicit) {
		this.isExplicit = isExplicit;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
