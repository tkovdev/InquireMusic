package com.inquiremusic.logic;


import java.util.ArrayList;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.logging.log4j.Logger;

import com.inquiremusic.data.Album;
import com.inquiremusic.data.Track;
import com.inquiremusic.utilities.APIManager;
import com.inquiremusic.utilities.ConfigurationSettings;

/**
 * TrackLogic handles API requests and provides interfaces to retrieve Track related information
 * @author Thomas P. Kovalchuk
 *
 */
public class TrackLogic {
	private final APIManager _apiManager;
	private final ConfigurationSettings _settings;
	private final Logger _logger;
	private final String _endpoint;
	
	/**
	 * Takes 3 dependencies to create a new TrackLogic object, results in a valid object to run API queries for Tracks
	 * @param apiManager
	 * @param settings
	 * @param logger
	 */
	public TrackLogic(APIManager apiManager, ConfigurationSettings settings, Logger logger) {
		this._logger = logger;
		this._logger.debug(">> new TrackLogic(...)");
		this._apiManager = apiManager;
		this._settings = settings;
		this._endpoint = this._settings.getSetting("APIEndpoint");
		this._logger.debug("<< new TrackLogic(...)");
	}
	/**
	 * Returns the result of the query sent to the API, returns a new Track.
	 * @param id
	 * @return Track - result of the query from the API
	 */
	public Track Get(String id) {
		this._logger.debug(">> TrackLogic.Get("+id+")");
		Track result = null;
		JsonObject res = this._apiManager.sendGet(this._endpoint + "tracks/" + id + "/?market=US");
		
		if(res.isEmpty()) {
			return result;
		}
		
		result = new Track();
		result.setId(res.getString("id"));
		result.setName(res.getString("name"));
		result.setPopularity(Double.parseDouble(res.get("popularity").toString()));
		result.setTrackNumber(res.getInt("track_number"));
		result.setDuration(Long.parseLong(res.get("duration_ms").toString()));
		result.setExplicit(res.getBoolean("explicit"));
		
		Album album = new Album();
		try {			
			JsonObject albumJson = res.get("album").asJsonObject();
			album.setId(albumJson.getString("id"));
			album.setName(albumJson.getString("name"));
			album.setType(albumJson.getString("album_type"));
			result.setAlbum(album);
		}catch(Exception e) {
		}
		
		
		this._logger.debug("<< TrackLogic.Get("+id+")");
		return result;
	}	
	/**
	 * Returns the result of the query sent to the API, returns a new ArrayList<Track>.
	 * @param id - Album Id to return ArrayList of Tracks
	 * @return Track - result of the query from the API
	 */
	public ArrayList<Track> GetAllByAlbumId(String id) {
		this._logger.debug(">> TrackLogic.GetAllByAlbumId("+id+")");
		ArrayList<Track> result = new ArrayList<Track>();
		JsonObject res = this._apiManager.sendGet(this._endpoint + "albums/" + id + "/tracks?market=US&limit=50");
		
		if(res.isEmpty()) {
			return result;
		}
		
		JsonArray jsonResults = null;
		try {			
			jsonResults = res.getJsonArray("items");
		}catch(NullPointerException e) {
			this._logger.error("TrackLogic.GetAllByAlbumId("+id+") results are null");
			return null;
		}catch(ClassCastException e) {
			this._logger.error("TrackLogic.GetAllByAlbumId("+id+") results cast error");
			return null;
		}
		
		for(JsonValue jsonResult : jsonResults) {
			JsonObject item = jsonResult.asJsonObject();
			Track track = new Track();
			track.setId(item.getString("id"));
			track.setName(item.getString("name"));
			track.setTrackNumber(item.getInt("track_number"));
			track.setDuration(Long.parseLong(item.get("duration_ms").toString()));
			track.setExplicit(item.getBoolean("explicit"));
			
			result.add(track);
		}
		

		this._logger.debug("<< TrackLogic.GetAllByAlbumId("+id+")");
		return result;
	}
	
	/**
	 * Returns the result of the query sent to the API, returns a new Artist.
	 * @param id
	 * @return ArraList<Track> - result of the query from the API
	 */
	public ArrayList<Track> GetTopTracksByArtistId(String id) {
		this._logger.debug(">> TrackLogic.GetTopTracksByArtistId("+id+")");
		ArrayList<Track> result = new ArrayList<Track>();
		JsonObject res = this._apiManager.sendGet(this._endpoint + "artists/" + id + "/top-tracks?market=US");
		if(res.isEmpty()) {
			return result;
		}
		
		JsonArray jsonResults = null;
		try {			
			jsonResults = res.getJsonArray("tracks");
		}catch(NullPointerException e) {
			this._logger.error("TrackLogic.GetTopTracksByArtistId("+id+") results are null");
			return null;
		}catch(ClassCastException e) {
			this._logger.error("TrackLogic.GetTopTracksByArtistId("+id+") results cast error");
			return null;
		}
		
		for(JsonValue jsonResult : jsonResults) {
			JsonObject item = jsonResult.asJsonObject();
			Track track = new Track();
			track.setId(item.getString("id"));
			track.setName(item.getString("name"));
			track.setTrackNumber(item.getInt("track_number"));
			track.setDuration(Long.parseLong(item.get("duration_ms").toString()));
			track.setExplicit(item.getBoolean("explicit"));
			if(item.getJsonObject("album") != null) {
				Album album = new Album();
				album.setName(item.getJsonObject("album").getString("name"));
				track.setAlbum(album);
			}
			result.add(track);
		}
		this._logger.debug("<< TrackLogic.GetTopTracksByArtistId("+id+")");
		return result;
	}

}
